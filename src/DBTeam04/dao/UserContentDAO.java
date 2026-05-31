package DBTeam04.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBTeam04.db.DBConnection;

/**
 * 사용자 모드 - 콘텐츠 검색 및 추천 DAO (작성: 지우)
 * v_content_detail, v_review_detail 뷰 사용
 */
public class UserContentDAO {

    // ─────────────────────────────────────────────
    // [콘텐츠 검색 및 조회]
    // ─────────────────────────────────────────────

    // 1. 제목으로 콘텐츠 검색
    public void searchByTitle(String keyword) {
        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 제목 LIKE ? " +
                "ORDER BY 제목 ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n🔍 '" + keyword + "' 검색 결과");
                System.out.println("========================================================================================");
                System.out.printf("%-20s | %-8s | %-15s | %-12s | %-5s\n",
                                  "제목", "유형", "장르", "플랫폼", "평점");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean hasResult = false;
                while (rs.next()) {
                    hasResult = true;
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n",
                            rs.getString("제목"), rs.getString("유형"),
                            rs.getString("장르"), rs.getString("플랫폼"),
                            rs.getDouble("플랫폼평점"));
                }
                if (!hasResult)
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 키워드로 검색해 보세요.");
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 2. 장르별 콘텐츠 검색
    public void searchByGenre(String genre) {
        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 장르 LIKE ? " +
                "ORDER BY 플랫폼평점 DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + genre + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n🔍 '" + genre + "' 장르 검색 결과");
                System.out.println("========================================================================================");
                System.out.printf("%-20s | %-8s | %-15s | %-12s | %-5s\n",
                                  "제목", "유형", "장르", "플랫폼", "평점");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean hasResult = false;
                while (rs.next()) {
                    hasResult = true;
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n",
                            rs.getString("제목"), rs.getString("유형"),
                            rs.getString("장르"), rs.getString("플랫폼"),
                            rs.getDouble("플랫폼평점"));
                }
                if (!hasResult)
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 장르로 검색해 보세요.");
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 3. 콘텐츠 유형별 검색
    public void searchByType(String type) {
        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 유형 = ? " +
                "ORDER BY 제목 ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n🔍 '" + type + "' 유형 검색 결과");
                System.out.println("========================================================================================");
                System.out.printf("%-20s | %-8s | %-15s | %-12s | %-5s\n",
                                  "제목", "유형", "장르", "플랫폼", "평점");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean hasResult = false;
                while (rs.next()) {
                    hasResult = true;
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n",
                            rs.getString("제목"), rs.getString("유형"),
                            rs.getString("장르"), rs.getString("플랫폼"),
                            rs.getDouble("플랫폼평점"));
                }
                if (!hasResult)
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 유형으로 검색해 보세요.");
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 4. 플랫폼별 콘텐츠 조회
    public void searchByPlatform(String platformName) {
        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 플랫폼 LIKE ? " +
                "ORDER BY 제목 ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + platformName + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n🔍 '" + platformName + "' 플랫폼 검색 결과");
                System.out.println("========================================================================================");
                System.out.printf("%-20s | %-8s | %-15s | %-12s | %-5s\n",
                                  "제목", "유형", "장르", "플랫폼", "평점");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean hasResult = false;
                while (rs.next()) {
                    hasResult = true;
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n",
                            rs.getString("제목"), rs.getString("유형"),
                            rs.getString("장르"), rs.getString("플랫폼"),
                            rs.getDouble("플랫폼평점"));
                }
                if (!hasResult)
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 플랫폼명으로 검색해 보세요.");
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 5. 콘텐츠 상세 정보 조회
    public void printUserContentDetail(String keyword) {
        String sql = """
                SELECT
                    c.content_id,
                    c.release_year,
                    c.age_rating,
                    c.description,
                    v.제목,
                    v.유형,
                    v.장르,
                    v.플랫폼,
                    v.플랫폼평점
                FROM Content c
                JOIN v_content_detail v ON c.title = v.제목 AND c.content_type = v.유형
                WHERE 제목 LIKE ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean isFirst = true;
                while (rs.next()) {
                    if (isFirst) {
                        System.out.println("\n🎬 [" + rs.getString("제목") + "] 상세 정보");
                        System.out.println("--------------------------------------------------");
                        System.out.printf("ID: %d | 유형: %s | 장르: %s\n",
                                rs.getInt("content_id"), rs.getString("유형"), rs.getString("장르"));
                        System.out.printf("개봉: %d년 | 연령등급: %s\n",
                                rs.getInt("release_year"), rs.getString("age_rating"));
                        System.out.println("줄거리: " + rs.getString("description"));
                        System.out.println("\n📍 시청 가능 플랫폼:");
                        isFirst = false;
                    }
                    String platform = rs.getString("플랫폼");
                    if (platform != null)
                        System.out.printf("  - %s (평점: %.1f)\n", platform, rs.getDouble("플랫폼평점"));
                }
                if (isFirst)
                    System.out.println("❌ 해당 제목의 콘텐츠 정보를 찾을 수 없습니다.");
                else
                    System.out.println("--------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // ─────────────────────────────────────────────
    // [인기 콘텐츠 및 추천 조회]
    // ─────────────────────────────────────────────

    // 1. 전체 인기 콘텐츠 TOP 10
    public void printTopPopularContents() {
        String sql = """
                SELECT c.title, c.content_type, COUNT(wh.history_id) AS view_count
                FROM Content c
                JOIN PlatformContent pc ON c.content_id = pc.content_id
                JOIN WatchHistory wh ON pc.pc_id = wh.pc_id
                GROUP BY c.title, c.content_type
                ORDER BY view_count DESC
                LIMIT 10
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n🔥 [실시간 전체 인기 콘텐츠 TOP 10] 🔥");
            System.out.println("==================================================");
            System.out.printf("%-5s | %-20s | %-10s | %-5s\n", "순위", "제목", "유형", "시청수");
            System.out.println("--------------------------------------------------");

            int rank = 1;
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-5d | %-20s | %-10s | %-5d 회\n",
                        rank++, rs.getString("title"),
                        rs.getString("content_type"), rs.getInt("view_count"));
            }
            if (!hasData) System.out.println("   아직 집계된 시청 기록이 없습니다.");
            System.out.println("==================================================");

        } catch (Exception e) {
            System.out.println("⚠️ 인기 콘텐츠 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 2. 평점 높은 콘텐츠 TOP 10
    public void printHighRatedContents() {
        String sql = """
                SELECT
                    c.title AS '콘텐츠제목',
                    ROUND(AVG(r.rating), 2) AS '평균평점',
                    COUNT(r.review_id) AS '리뷰수'
                FROM Review r
                JOIN PlatformContent pc ON r.pc_id = pc.pc_id
                JOIN Content c ON pc.content_id = c.content_id
                GROUP BY c.content_id, c.title
                HAVING AVG(r.rating) >= 4.0
                ORDER BY AVG(r.rating) DESC, COUNT(r.review_id) DESC
                LIMIT 10
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n⭐ [평점 4.0 이상! 유저들의 인생작 TOP 10] ⭐");
            System.out.println("==========================================================");
            System.out.printf("%-20s | %-8s | %-8s\n", "콘텐츠 제목", "평균 평점", "리뷰 수");
            System.out.println("----------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-20s | %-8.2f | %d건\n",
                        rs.getString("콘텐츠제목"),
                        rs.getDouble("평균평점"),
                        rs.getInt("리뷰수"));
            }
            if (!hasData) System.out.println("   평점 4.0 이상의 콘텐츠가 아직 없습니다.");
            System.out.println("==========================================================");

        } catch (Exception e) {
            System.out.println("⚠️ 고평점 콘텐츠 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 3. 내가 본 장르 기반 추천
    public void printRecommendedByGenre(int userId) {
        String sql = """
                SELECT DISTINCT c.title, g.genre_name
                FROM Content c
                JOIN ContentGenre cg ON c.content_id = cg.content_id
                JOIN Genre g ON cg.genre_id = g.genre_id
                WHERE g.genre_id IN (
                    SELECT cg2.genre_id
                    FROM WatchHistory wh
                    JOIN PlatformContent pc ON wh.pc_id = pc.pc_id
                    JOIN ContentGenre cg2 ON pc.content_id = cg2.content_id
                    WHERE wh.user_id = ?
                )
                LIMIT 15
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n🎁 [회원님의 시청 취향 저격! 추천 콘텐츠] 🎁");
                System.out.println("==================================================");
                System.out.printf("%-20s | %-15s\n", "추천 콘텐츠 제목", "장르");
                System.out.println("--------------------------------------------------");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    System.out.printf("%-20s | %-15s\n",
                            rs.getString("title"), rs.getString("genre_name"));
                }
                if (!hasData) {
                    System.out.println("   시청 기록이 부족하여 추천을 생성할 수 없습니다.");
                    System.out.println("   다양한 콘텐츠를 시청해 보세요!");
                }
                System.out.println("==================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 취향 추천 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 4. 구독 플랫폼별 추천
    public void printRecommendedBySubscription(int userId) {
        String sql = """
                SELECT
                    u.username,
                    p.platform_name,
                    c.title,
                    pc.platform_rating
                FROM Users u
                JOIN UserSubscription us ON u.user_id = us.user_id
                JOIN Platform p ON us.platform_id = p.platform_id
                JOIN PlatformContent pc ON p.platform_id = pc.platform_id
                JOIN Content c ON pc.content_id = c.content_id
                WHERE u.user_id = ? AND pc.platform_rating > 3.5
                ORDER BY p.platform_name ASC, pc.platform_rating DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n📺 [구독 중인 플랫폼별 추천 콘텐츠] 📺");
                System.out.println("==========================================================");
                System.out.printf("%-10s | %-15s | %-20s | %-5s\n", "사용자", "플랫폼", "콘텐츠 제목", "평점");
                System.out.println("----------------------------------------------------------");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    System.out.printf("%-10s | %-15s | %-20s | %.1f\n",
                            rs.getString("username"), rs.getString("platform_name"),
                            rs.getString("title"), rs.getDouble("platform_rating"));
                }
                if (!hasData)
                    System.out.println("   구독 중인 플랫폼이 없거나 추천할 만한 고평점 콘텐츠가 없습니다.");
                System.out.println("==========================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 플랫폼별 추천 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // ─────────────────────────────────────────────
    // [리뷰] 콘텐츠별 리뷰 조회
    // ─────────────────────────────────────────────

    public void printReviewsByContent(String title) {
        String sql = """
                SELECT
                    username,
                    rating,
                    review_text,
                    review_date,
                    is_spoiler
                FROM v_review_detail
                WHERE title = ?
                ORDER BY review_date DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n💬 [" + title + "] 에 대한 유저 리뷰");
                System.out.println("==========================================================");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    String spoilerTag = rs.getBoolean("is_spoiler") ? "[⚠️스포주의]" : "[Clean]   ";
                    String stars = "⭐".repeat(rs.getInt("rating"));
                    System.out.printf("%s %-10s | 평점: %d %s | 작성일: %s\n",
                            spoilerTag, rs.getString("username"),
                            rs.getInt("rating"), stars, rs.getString("review_date"));
                    System.out.println("리뷰 내용: " + rs.getString("review_text"));
                    System.out.println("----------------------------------------------------------");
                }
                if (!hasData)
                    System.out.println("   아직 작성된 리뷰가 없습니다. 첫 번째 리뷰를 남겨보세요!");
                System.out.println("==========================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 리뷰 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
