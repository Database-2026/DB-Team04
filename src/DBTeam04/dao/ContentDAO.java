package DBTeam04.dao;

import DBTeam04.db.DBConnection;
import DBTeam04.dto.ContentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContentDAO {

    // 콘텐츠 목록 조회
    public List<ContentDTO> getAllContents() {
        List<ContentDTO> contentList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM Content
                ORDER BY content_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                ContentDTO content = mapResultSetToContent(rs);
                contentList.add(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }
    // 콘텐츠 등록
    // 콘텐츠 등록 후 생성된 content_id 반환
    public int insertContent(ContentDTO content) {

        String sql = """
                INSERT INTO Content
                (title, content_type, release_year,
                 age_rating, description)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(
                                sql,
                                PreparedStatement.RETURN_GENERATED_KEYS
                        )
        ) {

            pstmt.setString(1, content.getTitle());
            pstmt.setString(2, content.getContentType());
            pstmt.setInt(3, content.getReleaseYear());
            pstmt.setString(4, content.getAgeRating());
            pstmt.setString(5, content.getDescription());

            int result = pstmt.executeUpdate();

            if (result > 0) {

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

 // 콘텐츠 검색
    public List<ContentDTO> searchContents(String type, String keyword) {
        List<ContentDTO> contentList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM Content
                WHERE
                """;

        switch (type) {
            case "title":
                sql += " title LIKE ? ";
                break;

            case "content_type":
                sql += " content_type LIKE ? ";
                break;

            case "release_year":
                sql += " release_year = ? ";
                break;

            case "age_rating":
                sql += " age_rating LIKE ? ";
                break;

            default:
                return contentList;
        }

        sql += " ORDER BY content_id";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            if (type.equals("release_year")) {
                pstmt.setInt(1, Integer.parseInt(keyword));
            } else {
                pstmt.setString(1, "%" + keyword + "%");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ContentDTO content = mapResultSetToContent(rs);
                    contentList.add(content);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }
    // 콘텐츠 수정
    public boolean updateContent(ContentDTO content) {
        String sql = """
                UPDATE Content
                SET title = ?, content_type = ?,
                    release_year = ?, age_rating = ?,
                    description = ?
                WHERE content_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, content.getTitle());
            pstmt.setString(2, content.getContentType());
            pstmt.setInt(3, content.getReleaseYear());
            pstmt.setString(4, content.getAgeRating());
            pstmt.setString(5, content.getDescription());
            pstmt.setInt(6, content.getContentId());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 콘텐츠 삭제
    public boolean deleteContent(int contentId) {
        String sql = """
                DELETE FROM Content
                WHERE content_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, contentId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 콘텐츠 상세 정보 조회
    public void printContentDetail(int contentId) {

        String sql = """
                SELECT
                    C.content_id,
                    C.title,
                    C.content_type,
                    C.release_year,
                    C.age_rating,
                    C.description,
                    P.platform_name,
                    PC.platform_rating
                FROM Content C
                LEFT JOIN PlatformContent PC
                    ON C.content_id = PC.content_id
                LEFT JOIN Platform P
                    ON PC.platform_id = P.platform_id
                WHERE C.content_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, contentId);

            try (ResultSet rs = pstmt.executeQuery()) {

                boolean first = true;

                while (rs.next()) {

                    if (first) {

                        System.out.println("\n[콘텐츠 상세 정보]");
                        System.out.println("ID: " +
                                rs.getInt("content_id"));

                        System.out.println("제목: " +
                                rs.getString("title"));

                        System.out.println("유형: " +
                                rs.getString("content_type"));

                        System.out.println("공개연도: " +
                                rs.getInt("release_year"));

                        System.out.println("연령등급: " +
                                rs.getString("age_rating"));

                        System.out.println("설명: " +
                                rs.getString("description"));

                        System.out.println("\n[제공 플랫폼]");

                        first = false;
                    }

                    String platformName =
                            rs.getString("platform_name");

                    if (platformName != null) {

                        System.out.printf(
                                "%s | 평점 %.1f%n",
                                platformName,
                                rs.getDouble("platform_rating")
                        );
                    }
                }

                if (first) {
                    System.out.println("해당 콘텐츠가 존재하지 않습니다.");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ResultSet → DTO 변환
    private ContentDTO mapResultSetToContent(ResultSet rs) throws Exception {
        ContentDTO content = new ContentDTO();

        content.setContentId(rs.getInt("content_id"));
        content.setTitle(rs.getString("title"));
        content.setContentType(rs.getString("content_type"));
        content.setReleaseYear(rs.getInt("release_year"));
        content.setAgeRating(rs.getString("age_rating"));
        content.setDescription(rs.getString("description"));

        return content;
    }
}