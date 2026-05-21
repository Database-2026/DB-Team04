package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBUtil;
import dto.ContentDTO;

public class ContentDAO {

    // [콘텐츠 검색 및 조회] >> 1.제목으로 콘텐츠 검색
    public void searchByTitle(String keyword) {

        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 제목 LIKE ? " +
                "ORDER BY 제목 ASC";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            	pstmt.setString(1, "%" + keyword + "%");
            // SELECT 실행
            try(ResultSet rs =pstmt.executeQuery()){
            	System.out.println("\n🔍 '" + keyword + "' 검색 결과");
                System.out.println("========================================================================================");
                // 헤더 출력 (좌측 정렬 및 간격 조정)
                System.out.printf("%-20s | %-8s | %-15s | %-12s | %-5s\n", 
                                  "제목", "유형", "장르", "플랫폼", "평점");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean hasResult = false;
                while (rs.next()) {
                    hasResult = true;
                    
                    // 데이터 추출
                    String title = rs.getString("제목");
                    String type = rs.getString("유형");
                    String genre = rs.getString("장르");
                    String platform = rs.getString("플랫폼");
                    double rating = rs.getDouble("플랫폼평점");

                    // 한 줄씩 포맷에 맞춰 출력
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n", 
                                      title, type, genre, platform, rating);
                }

                if (!hasResult) {
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 키워드로 검색해 보세요.");
                }
                
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
    //[콘텐츠 검색 및 조회] >> 2.장르별 콘텐츠 검색
    public void searchByGenre(String genre) {

        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 장르 LIKE ? " +
                "ORDER BY 플랫폼평점 DESC";

        try (Connection conn =DBUtil.getConnection();
            PreparedStatement pstmt =conn.prepareStatement(sql)){

            pstmt.setString(1,"%" + genre + "%");
            try(ResultSet rs =pstmt.executeQuery()){
            // 결과 읽기
            	System.out.println("\n🔍 '" + genre + "' 검색 결과");
                System.out.println("========================================================================================");
                // 헤더 출력 (좌측 정렬 및 간격 조정)
                System.out.printf("%-20s | %-8s | %-15s | %-12s | %-5s\n", 
                                  "제목", "유형", "장르", "플랫폼", "평점");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean hasResult = false;
                while (rs.next()) {
                    hasResult = true;
                    
                    // 데이터 추출
                    String title = rs.getString("제목");
                    String type = rs.getString("유형");
                    String genre_set = rs.getString("장르");
                    String platform = rs.getString("플랫폼");
                    double rating = rs.getDouble("플랫폼평점");

                    // 한 줄씩 포맷에 맞춰 출력
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n", 
                                      title, type, genre_set, platform, rating);
                }

                if (!hasResult) {
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 장르로 검색해 보세요.");
                }
                
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
 // [콘텐츠 검색 및 조회] >> 3.콘텐츠 유형별 검색
    public void searchByType(String type) {

        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 유형 = ? " +
                "ORDER BY 제목 ASC";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, type);
            try(ResultSet rs = pstmt.executeQuery()){
            	System.out.println("\n🔍 '" + type + "' 검색 결과");
                System.out.println("========================================================================================");
                // 헤더 출력 (좌측 정렬 및 간격 조정)
                System.out.printf("%-20s | %-8s | %-15s | %-12s | %-5s\n", 
                                  "제목", "유형", "장르", "플랫폼", "평점");
                System.out.println("----------------------------------------------------------------------------------------");

                boolean hasResult = false;
                while (rs.next()) {
                    hasResult = true;
                    
                    // 데이터 추출
                    String title = rs.getString("제목");
                    String s_type = rs.getString("유형");
                    String genre_set = rs.getString("장르");
                    String platform = rs.getString("플랫폼");
                    double rating = rs.getDouble("플랫폼평점");

                    // 한 줄씩 포맷에 맞춰 출력
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n", 
                                      title, s_type, genre_set, platform, rating);
                }

                if (!hasResult) {
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 유형으로 검색해 보세요.");
                }
                
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
 // [콘텐츠 검색 및 조회] >> 4. 플랫폼별 콘텐츠 조회
    public void searchByPlatform(String platformName) {

        String sql = "SELECT * FROM v_content_detail " +
                     "WHERE 플랫폼 LIKE ? " +
                     "ORDER BY 제목 ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
           pstmt.setString(1, "%" + platformName + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
            	boolean hasResult = false;
            	while (rs.next()) {
                    hasResult = true;
                    
                    // 데이터 추출
                    String title = rs.getString("제목");
                    String s_type = rs.getString("유형");
                    String genre_set = rs.getString("장르");
                    String platform = rs.getString("플랫폼");
                    double rating = rs.getDouble("플랫폼평점");

                    // 한 줄씩 포맷에 맞춰 출력
                    System.out.printf("%-20s | %-8s | %-15s | %-12s | %.1f\n", 
                                      title, s_type, genre_set, platform, rating);
                }

                if (!hasResult) {
                    System.out.println("   ❌ 검색 결과가 없습니다. 다른 유형으로 검색해 보세요.");
                }
                
                System.out.println("========================================================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 검색 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
 // [콘텐츠 검색 및 조회] >> 5. 콘텐츠 상세 정보 조회
    public void printUserContentDetail(String keyword) {
        // 뷰(v)와 기본 테이블(c)을 조인하여 뷰에 없는 정보(id, 개봉연도, 줄거리 등)를 가져옴
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

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
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

                    // 플랫폼 정보 출력
                    String platform = rs.getString("플랫폼");
                    if (platform != null) {
                        System.out.printf("  - %s (평점: %.1f)\n", platform, rs.getDouble("플랫폼평점"));
                    }
                }

                if (isFirst) {
                    System.out.println("❌ 해당 제목의 콘텐츠 정보를 찾을 수 없습니다.");
                } else {
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
//[인기 콘텐츠 및 추천 조회]

 // [인기 콘텐츠 및 추천 조회] >> 1. 전체 인기 콘텐츠 조회
    public void printTopPopularContents() {
        // 순위 표시를 위해 조회수를 함께 가져옵니다.
        String sql = """
                SELECT c.title, c.content_type, COUNT(wh.history_id) AS view_count
                FROM Content c
                JOIN PlatformContent pc ON c.content_id = pc.content_id
                JOIN WatchHistory wh ON pc.pc_id = wh.pc_id
                GROUP BY c.title, c.content_type
                ORDER BY view_count DESC
                LIMIT 10
                """; // 상위 10개만 표시 (필요에 따라 조절)

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            System.out.println("\n🔥 [실시간 전체 인기 콘텐츠 TOP 10] 🔥");
            System.out.println("==================================================");
            System.out.printf("%-5s | %-20s | %-10s | %-5s\n", "순위", "제목", "유형", "시청수");
            System.out.println("--------------------------------------------------");

            int rank = 1;
            boolean hasData = false;
            
            while (rs.next()) {
                hasData = true;
                String title = rs.getString("title");
                String type = rs.getString("content_type");
                int views = rs.getInt("view_count");

                // 순위와 함께 출력
                System.out.printf("%-5d | %-20s | %-10s | %-5d 회\n", 
                                  rank++, title, type, views);
            }

            if (!hasData) {
                System.out.println("   아직 집계된 시청 기록이 없습니다.");
            }
            System.out.println("==================================================");

        } catch (Exception e) {
            System.out.println("⚠️ 인기 콘텐츠 조회 중 오류가 발생했습니다.");
            e.printStackTrace();  
        }
    }
    
 // [인기 콘텐츠 및 추천 조회] >> 2. 높은 평점 콘텐츠 조회
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

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            System.out.println("\n⭐ [평점 4.0 이상! 유저들의 인생작 TOP 10] ⭐");
            System.out.println("==========================================================");
            System.out.printf("%-20s | %-8s | %-8s\n", "콘텐츠 제목", "평균 평점", "리뷰 수");
            System.out.println("----------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String title = rs.getString("콘텐츠제목");
                double avgRating = rs.getDouble("평균평점");
                int reviewCount = rs.getInt("리뷰수");

                System.out.printf("%-20s | %-8.2f | %d건\n", 
                                  title, avgRating, reviewCount);
            }

            if (!hasData) {
                System.out.println("   평점 4.0 이상의 콘텐츠가 아직 없습니다.");
            }
            System.out.println("==========================================================");

        } catch (Exception e) {
            System.out.println("⚠️ 고평점 콘텐츠 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
 // [인기 콘텐츠 및 추천 조회] >> 3. 내가 본 장르 기반 추천
    public void printRecommendedByGenre(int userId) {
        // 장르 정렬을 추가했습니다.
    	// 현재 로그인한 userId를 입력값으로 받아 출력을 수행합니다.
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

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n🎁 [회원님의 시청 취향 저격! 추천 콘텐츠] 🎁");
                System.out.println("==================================================");
                System.out.printf("%-20s | %-15s\n", "추천 콘텐츠 제목", "장르");
                System.out.println("--------------------------------------------------");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    String title = rs.getString("title");
                    String genre = rs.getString("genre_name");

                    System.out.printf("%-20s | %-15s\n", title, genre);
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
    
 // [인기 콘텐츠 및 추천 조회] >> 4. 플랫폼별 추천 콘텐츠 조회
    public void printRecommendedBySubscription(int userId) {
    	// 현재 로그인한 userId를 입력값으로 받아 출력을 수행합니다.
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
                WHERE u.user_id = ? and pc.platform_rating > 3.5
                ORDER BY p.platform_name ASC, pc.platform_rating DESC
                """;

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n📺 [구독 중인 플랫폼별 추천 콘텐츠] 📺");
                System.out.println("==========================================================");
                System.out.printf("%-10s | %-15s | %-20s | %-5s\n", "사용자", "플랫폼", "콘텐츠 제목", "평점");
                System.out.println("----------------------------------------------------------");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    String username = rs.getString("username");
                    String platform = rs.getString("platform_name");
                    String title = rs.getString("title");
                    double rating = rs.getDouble("platform_rating");

                    System.out.printf("%-10s | %-15s | %-20s | %.1f\n", 
                                      username, platform, title, rating);
                }

                if (!hasData) {
                    System.out.println("   구독 중인 플랫폼이 없거나 추천할 만한 고평점 콘텐츠가 없습니다.");
                }
                System.out.println("==========================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 플랫폼별 추천 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    
// [내 리뷰 및 평점 관리] >> 3. 콘텐츠별 리뷰 조회
    public void printReviewsByContent(String title) {
        // 뷰(v_review_detail)를 사용하는 쿼리입니다.
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

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, title);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n💬 [" + title + "] 에 대한 유저 리뷰");
                System.out.println("==========================================================");
                
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    String user = rs.getString("username");
                    double rating = rs.getDouble("rating");
                    String text = rs.getString("review_text");
                    String date = rs.getString("review_date");
                    String isSpoiler = rs.getString("is_spoiler");

                    // 스포일러 여부에 따른 아이콘 표시
                    String spoilerTag = "Y".equalsIgnoreCase(isSpoiler) ? "[⚠️스포주의]" : "[Clean]";
                    
                    // 별점 시각화 (5점 만점 기준)
                    String stars = "⭐".repeat((int) rating);

                    System.out.printf("%s %-10s | 평점: %.1f %s | 작성일: %s\n", 
                                      spoilerTag, user, rating, stars, date);
                    System.out.println("리뷰 내용: " + text);
                    System.out.println("----------------------------------------------------------");
                }

                if (!hasData) {
                    System.out.println("   아직 작성된 리뷰가 없습니다. 첫 번째 리뷰를 남겨보세요!");
                }
                System.out.println("==========================================================");
            }
        } catch (Exception e) {
            System.out.println("⚠️ 리뷰 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
}
