package DB2026Team04.dao;

import java.sql.ResultSet;

import DB2026Team04.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PlatformContentDAO {

    // 콘텐츠-플랫폼 연결 등록
    public boolean connectContentPlatform(
            int contentId,
            int platformId,
            double rating
    ) {

        String sql = """
                INSERT INTO PlatformContent
                (content_id, platform_id,
                 platform_rating, is_available)
                VALUES (?, ?, ?, true)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, contentId);
            pstmt.setInt(2, platformId);
            pstmt.setDouble(3, rating);

            int result = pstmt.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 특정 콘텐츠의 플랫폼 연결 조회
    public void printConnectedPlatforms(int contentId) {

        String sql = """
                SELECT
                    PC.pc_id,
                    P.platform_name,
                    PC.platform_rating,
                    PC.is_available
                FROM PlatformContent PC
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                WHERE PC.content_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, contentId);

            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("\n[연결된 플랫폼]");
                System.out.println(
                        "PC_ID | 플랫폼 | 평점 | 제공여부"
                );

                while (rs.next()) {

                    System.out.printf(
                            "%d | %s | %.1f | %s%n",
                            rs.getInt("pc_id"),
                            rs.getString("platform_name"),
                            rs.getDouble("platform_rating"),
                            rs.getBoolean("is_available")
                                    ? "Y" : "N"
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 // 플랫폼 연결 수정
    public boolean updatePlatformConnection(
            int pcId,
            double rating,
            boolean isAvailable
    ) {

        String sql = """
                UPDATE PlatformContent
                SET platform_rating = ?,
                    is_available = ?
                WHERE pc_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setDouble(1, rating);
            pstmt.setBoolean(2, isAvailable);
            pstmt.setInt(3, pcId);

            int result = pstmt.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 플랫폼 연결 삭제
    public boolean deletePlatformConnection(int pcId) {

        String sql = """
                DELETE FROM PlatformContent
                WHERE pc_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, pcId);

            int result = pstmt.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}