package DBTeam04.dao;

import DBTeam04.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActivityDAO {

    // 전체 감상 기록 조회
    public void printAllWatchHistory() {

        String sql = """
                SELECT
                    WH.history_id,
                    U.username,
                    C.title,
                    P.platform_name,
                    WH.watch_status,
                    WH.watched_date
                FROM WatchHistory WH
                JOIN Users U
                    ON WH.user_id = U.user_id
                JOIN PlatformContent PC
                    ON WH.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                ORDER BY WH.watched_date DESC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            System.out.println("\n[전체 감상 기록]");
            System.out.println(
                    "ID | 사용자 | 콘텐츠 | 플랫폼 | 상태 | 시청일"
            );

            while (rs.next()) {

                System.out.printf(
                        "%d | %s | %s | %s | %s | %s%n",

                        rs.getInt("history_id"),

                        rs.getString("username"),

                        rs.getString("title"),

                        rs.getString("platform_name"),

                        rs.getString("watch_status"),

                        rs.getTimestamp("watched_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 사용자별 감상 기록 조회
    public void printWatchHistoryByUser(String keyword) {

        String sql = """
                SELECT
                    WH.history_id,
                    U.username,
                    U.email,
                    C.title,
                    P.platform_name,
                    WH.watch_status,
                    WH.watched_date
                FROM WatchHistory WH
                JOIN Users U
                    ON WH.user_id = U.user_id
                JOIN PlatformContent PC
                    ON WH.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                WHERE U.username LIKE ?
                ORDER BY WH.watched_date DESC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println(
                        "\n[사용자별 감상 기록]"
                );

                while (rs.next()) {

                    System.out.printf(
                            "%d | %s | %s | %s | %s | %s%n",

                            rs.getInt("history_id"),

                            rs.getString("username"),

                            rs.getString("title"),

                            rs.getString("platform_name"),

                            rs.getString("watch_status"),

                            rs.getTimestamp("watched_date")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 콘텐츠별 감상 기록 조회
    public void printWatchHistoryByContent(
            String keyword
    ) {

        String sql = """
                SELECT
                    WH.history_id,
                    C.title,
                    P.platform_name,
                    U.username,
                    WH.watch_status,
                    WH.watched_date
                FROM WatchHistory WH
                JOIN Users U
                    ON WH.user_id = U.user_id
                JOIN PlatformContent PC
                    ON WH.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                WHERE C.title LIKE ?
                ORDER BY WH.watched_date ASC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println(
                        "\n[콘텐츠별 감상 기록]"
                );

                while (rs.next()) {

                    System.out.printf(
                            "%d | %s | %s | %s | %s | %s%n",

                            rs.getInt("history_id"),

                            rs.getString("title"),

                            rs.getString("platform_name"),

                            rs.getString("username"),

                            rs.getString("watch_status"),

                            rs.getTimestamp("watched_date")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 전체 리뷰 조회
    public void printAllReviews() {

        String sql = """
                SELECT
                    R.review_id,
                    U.username,
                    C.title,
                    P.platform_name,
                    R.rating,
                    R.review_text,
                    R.review_date
                FROM Review R
                JOIN Users U
                    ON R.user_id = U.user_id
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                ORDER BY R.review_id ASC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            System.out.println("\n[전체 리뷰]");

            while (rs.next()) {

                System.out.printf(
                        "%d | %s | %s | %.1f | %s%n",

                        rs.getInt("review_id"),

                        rs.getString("username"),

                        rs.getString("title"),

                        rs.getDouble("rating"),

                        rs.getString("review_text")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 사용자별 리뷰 조회
    public void printReviewsByUser(
            String keyword
    ) {

        String sql = """
                SELECT
                    R.review_id,
                    U.username,
                    C.title,
                    P.platform_name,
                    R.rating,
                    R.review_text
                FROM Review R
                JOIN Users U
                    ON R.user_id = U.user_id
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                WHERE U.username LIKE ?
                ORDER BY R.review_id ASC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    System.out.printf(
                            "%d | %s | %s | %.1f | %s%n",

                            rs.getInt("review_id"),

                            rs.getString("username"),

                            rs.getString("title"),

                            rs.getDouble("rating"),

                            rs.getString("review_text")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 리뷰 삭제
    public boolean deleteReview(int reviewId) {

        String sql = """
                DELETE FROM Review
                WHERE review_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, reviewId);

            int result = pstmt.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 콘텐츠별 리뷰 조회
    public void printReviewsByContent(
            String keyword
    ) {

        String sql = """
                SELECT
                    R.review_id,
                    C.title,
                    U.username,
                    P.platform_name,
                    R.rating,
                    R.review_text,
                    R.review_date
                FROM Review R
                JOIN Users U
                    ON R.user_id = U.user_id
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                WHERE C.title LIKE ?
                ORDER BY R.review_id ASC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println(
                        "\n[콘텐츠별 리뷰 조회]"
                );

                while (rs.next()) {

                    System.out.printf(
                            "%d | %s | %s | %s | %.1f | %s%n",

                            rs.getInt("review_id"),

                            rs.getString("title"),

                            rs.getString("username"),

                            rs.getString("platform_name"),

                            rs.getDouble("rating"),

                            rs.getString("review_text")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}