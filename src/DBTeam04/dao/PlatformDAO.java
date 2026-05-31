package DBTeam04.dao;

import DBTeam04.db.DBConnection;
import DBTeam04.dto.PlatformDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlatformDAO {

    // 플랫폼 목록 조회
    public List<PlatformDTO> getAllPlatforms() {
        List<PlatformDTO> platformList = new ArrayList<>();

        String sql = """
                SELECT platform_id, platform_name, platform_price
                FROM Platform
                ORDER BY platform_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                PlatformDTO platform = mapResultSetToPlatform(rs);
                platformList.add(platform);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return platformList;
    }
    
 // 플랫폼 등록
    public boolean insertPlatform(PlatformDTO platform) {
        String sql = """
                INSERT INTO Platform (platform_name, platform_price)
                VALUES (?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, platform.getPlatformName());
            pstmt.setDouble(2, platform.getPlatformPrice());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 플랫폼 이름 검색
    public List<PlatformDTO> searchPlatforms(String keyword) {
        List<PlatformDTO> platformList = new ArrayList<>();

        String sql = """
                SELECT platform_id, platform_name, platform_price
                FROM Platform
                WHERE platform_name LIKE ?
                ORDER BY platform_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PlatformDTO platform = mapResultSetToPlatform(rs);
                    platformList.add(platform);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return platformList;
    }
    
 // 플랫폼 수정
    public boolean updatePlatform(PlatformDTO platform) {
        String sql = """
                UPDATE Platform
                SET platform_name = ?, platform_price = ?
                WHERE platform_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, platform.getPlatformName());
            pstmt.setDouble(2, platform.getPlatformPrice());
            pstmt.setInt(3, platform.getPlatformId());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 플랫폼 삭제
    public boolean deletePlatform(int platformId) {
        String sql = """
                DELETE FROM Platform
                WHERE platform_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, platformId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 플랫폼별 제공 콘텐츠 조회
    public void printPlatformContents(int platformId) {

        String sql = """
                SELECT
                    P.platform_name,
                    C.content_id,
                    C.title,
                    C.content_type,
                    C.release_year,
                    PC.platform_rating,
                    PC.is_available
                FROM PlatformContent PC
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                WHERE P.platform_id = ?
                ORDER BY C.content_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, platformId);

            try (ResultSet rs = pstmt.executeQuery()) {

                boolean first = true;

                while (rs.next()) {

                    if (first) {

                        System.out.println(
                                "\n[" +
                                rs.getString("platform_name") +
                                " 제공 콘텐츠]"
                        );

                        System.out.println(
                                "ID | 제목 | 유형 | 연도 | 평점 | 제공여부"
                        );

                        System.out.println(
                                "------------------------------------------------"
                        );

                        first = false;
                    }

                    System.out.printf(
                            "%d | %s | %s | %d | %.1f | %s%n",

                            rs.getInt("content_id"),

                            rs.getString("title"),

                            rs.getString("content_type"),

                            rs.getInt("release_year"),

                            rs.getDouble("platform_rating"),

                            rs.getBoolean("is_available")
                                    ? "Y" : "N"
                    );
                }

                if (first) {
                    System.out.println(
                            "해당 플랫폼 콘텐츠가 없습니다."
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ResultSet 한 행을 PlatformDTO로 변환
    private PlatformDTO mapResultSetToPlatform(ResultSet rs) throws Exception {
        PlatformDTO platform = new PlatformDTO();

        platform.setPlatformId(rs.getInt("platform_id"));
        platform.setPlatformName(rs.getString("platform_name"));
        platform.setPlatformPrice(rs.getDouble("platform_price"));

        return platform;
    }
}