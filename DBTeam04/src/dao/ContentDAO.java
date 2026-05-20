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
}
