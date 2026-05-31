package DBTeam04.dao;

import DBTeam04.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatisticsDAO {

    // 플랫폼별 콘텐츠 수
    public void printContentCountByPlatform() {

        String sql = """
                SELECT
                    P.platform_name,
                    COUNT(*) AS content_count
                FROM PlatformContent PC
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                GROUP BY P.platform_name
                ORDER BY content_count DESC
                """;

        executeAndPrint(
                sql,
                "\n[플랫폼별 콘텐츠 수]",
                "platform_name",
                "content_count"
        );
    }

    // 콘텐츠 유형별 개수
    public void printContentTypeStatistics() {

        String sql = """
                SELECT
                    content_type,
                    COUNT(*) AS cnt
                FROM Content
                GROUP BY content_type
                ORDER BY cnt DESC
                """;

        executeAndPrint(
                sql,
                "\n[콘텐츠 유형별 개수]",
                "content_type",
                "cnt"
        );
    }

    // 평균 평점 TOP 콘텐츠
    public void printTopRatedContents() {

        String sql = """
                SELECT
                    C.title,
                    ROUND(AVG(R.rating), 2)
                        AS avg_rating,
                    COUNT(*) AS review_count
                FROM Review R
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                GROUP BY C.title
                HAVING COUNT(*) >= 1
                ORDER BY avg_rating DESC
                LIMIT 10
                """;

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement pstmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        pstmt.executeQuery()
        ) {

            System.out.println(
                    "\n[평균 평점 TOP 콘텐츠]"
            );

            System.out.println(
                    "제목 | 평균평점 | 리뷰수"
            );

            while (rs.next()) {

                System.out.printf(
                        "%s | %.2f | %d%n",

                        rs.getString("title"),

                        rs.getDouble("avg_rating"),

                        rs.getInt("review_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 플랫폼별 평균 평점
    public void printAverageRatingByPlatform() {

        String sql = """
                SELECT
                    P.platform_name,
                    ROUND(AVG(R.rating), 2)
                        AS avg_rating
                FROM Review R
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Platform P
                    ON PC.platform_id = P.platform_id
                GROUP BY P.platform_name
                ORDER BY avg_rating DESC
                """;

        executeAndPrint(
                sql,
                "\n[플랫폼별 평균 평점]",
                "platform_name",
                "avg_rating"
        );
    }

    // 가장 활동적인 사용자
    public void printMostActiveUsers() {

        String sql = """
                SELECT
                    U.username,
                    COUNT(*) AS activity_count
                FROM WatchHistory WH
                JOIN Users U
                    ON WH.user_id = U.user_id
                GROUP BY U.username
                ORDER BY activity_count DESC
                LIMIT 10
                """;

        executeAndPrint(
                sql,
                "\n[가장 활동적인 사용자]",
                "username",
                "activity_count"
        );
    }

    // 리뷰 수 TOP 콘텐츠
    public void printMostReviewedContents() {

        String sql = """
                SELECT
                    C.title,
                    COUNT(*) AS review_count
                FROM Review R
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                GROUP BY C.title
                ORDER BY review_count DESC
                LIMIT 10
                """;

        executeAndPrint(
                sql,
                "\n[리뷰 수 TOP 콘텐츠]",
                "title",
                "review_count"
        );
    }

    // 공통 출력용
    private void executeAndPrint(
            String sql,
            String title,
            String col1,
            String col2
    ) {

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement pstmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        pstmt.executeQuery()
        ) {

            System.out.println(title);

            while (rs.next()) {

                System.out.printf(
                        "%s | %s%n",

                        rs.getString(col1),

                        rs.getString(col2)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // 콘텐츠별 평균 평점 조회
    public void printAverageRatingByContent() {

        String sql = """
                SELECT
                    C.title,
                    ROUND(AVG(R.rating), 2)
                        AS avg_rating,
                    COUNT(*) AS review_count
                FROM Review R
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                GROUP BY C.title
                ORDER BY avg_rating DESC
                """;

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement pstmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        pstmt.executeQuery()
        ) {

            System.out.println(
                    "\n[콘텐츠별 평균 평점]"
            );

            System.out.println(
                    "제목 | 평균평점 | 리뷰수"
            );

            while (rs.next()) {

                System.out.printf(
                        "%s | %.2f | %d%n",

                        rs.getString("title"),

                        rs.getDouble("avg_rating"),

                        rs.getInt("review_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 // 인기 콘텐츠 조회
    public void printPopularContents() {

        String sql = """
                SELECT
                    C.title,
                    COUNT(*) AS review_count
                FROM Review R
                JOIN PlatformContent PC
                    ON R.pc_id = PC.pc_id
                JOIN Content C
                    ON PC.content_id = C.content_id
                GROUP BY C.title
                ORDER BY review_count DESC
                LIMIT 10
                """;

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement pstmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        pstmt.executeQuery()
        ) {

            System.out.println(
                    "\n[인기 콘텐츠]"
            );

            System.out.println(
                    "제목 | 리뷰 수"
            );

            while (rs.next()) {

                System.out.printf(
                        "%s | %d%n",

                        rs.getString("title"),

                        rs.getInt("review_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}