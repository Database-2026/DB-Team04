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
                WHERE U.user_id = ? -- 첫번째 ? (ID 일치)
                	or U.username LIKE ? -- 두번째 ? (이름)
        			or U.email LIKE ? -- 세 번째? (이메일 포함)

                ORDER BY WH.watched_date DESC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, keyword);
            pstmt.setString(2,  "%"+ keyword+ "%");
            pstmt.setString(3,  "%"+ keyword+ "%");


            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println(
                        "\n[사용자별 감상 기록]"
              
                );
                
                boolean hasData = false;

                while (rs.next()) {

                	hasData = true; 

                    // 상단 헤더는 데이터가 있을 때 딱 한 번만 출력되도록 처리
                    if (rs.isFirst()) {
                        System.out.println("기록ID | 이름 | 콘텐츠 제목 | 플랫폼 | 상태 | 감상일");
                        System.out.println("-----------------------------------------------------------------------");
                    }

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
                
                if (!hasData) {
                    System.out.println("해당 사용자는 존재하지 않거나 감상 기록이 없습니다!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 // 콘텐츠별 감상 기록 조회 (ID 또는 제목 통합 검색 + 예외 처리 추가)
    public void printWatchHistoryByContent(String keyword) {

        // 1. WHERE 절을 통합 검색이 가능하도록 수정 (ID 일치 OR 제목 포함)
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
                WHERE C.content_id = ?    -- 첫 번째 ? (콘텐츠 ID 검색)
                   OR C.title LIKE ?      -- 두 번째 ? (콘텐츠 제목 검색)
                ORDER BY WH.watched_date ASC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            // 2. 뚫어놓은 두 개의 물음표에 값을 순서대로 세팅
            pstmt.setString(1, keyword); 
            pstmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("\n[콘텐츠별 감상 기록]");
                
                // 💡 존재 여부를 체크할 깃발 변수 생성
                boolean hasData = false;

                while (rs.next()) {
                    hasData = true; // 데이터가 한 줄이라도 있으면 true로 변경
                    
                    // 💡 첫 번째 행일 때만 깔끔하게 상단 헤더 출력
                    if (rs.isFirst()) {
                        System.out.println("기록ID | 콘텐츠 제목 | 플랫폼 | 사용자 이름 | 감상 상태 | 감상일");
                        System.out.println("-------------------------------------------------------------------------------------");
                    }

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
                
                // 💡 없는 검색어를 치거나 데이터가 없을 때 경고 문구 출력!
                if (!hasData) {
                    System.out.println("해당 조건(ID 또는 제목)에 맞는 콘텐츠 정보나 감상 기록이 존재하지 않습니다!");
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

 // 사용자별 리뷰 조회 (ID 또는 이름 통합 검색)
    public void printReviewsByUser(String keyword) {

        // WHERE 절을 통합 검색이 가능하도록 수정 (ID 일치 OR 이름 포함)
        String sql = """
                SELECT
                    R.review_id,
                    U.user_id,
                    U.username,
                    C.content_id,
                    C.title,
                    P.platform_name,
                    R.rating,
                    R.review_text,
                    R.review_date,
                    R.is_spoiler
                FROM Review R
                JOIN Users U ON R.user_id = U.user_id
                JOIN PlatformContent PC ON R.pc_id = PC.pc_id
                JOIN Content C ON PC.content_id = C.content_id
                JOIN Platform P ON PC.platform_id = P.platform_id
                WHERE U.user_id = ?        -- 첫 번째 ? (회원 고유 ID 검색)
                   OR U.username LIKE ?    -- 두 번째 ? (회원 이름 검색)
                ORDER BY R.review_date DESC
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            // 물음표 2개에 값 순서대로 매핑
            pstmt.setString(1, keyword); 
            pstmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("\n[사용자별 리뷰 조회]");
                
                // 존재 여부를 체크할 깃발 변수
                boolean hasData = false;

                while (rs.next()) {
                    hasData = true; // 데이터가 있으면 true로 변경
                    
                    // 첫 번째 행일 때만 상단 헤더 출력
                    if (rs.isFirst()) {
                        System.out.println("리뷰ID | 회원ID | 작성자 | 콘텐츠 제목 | 플랫폼 | 평점 | 리뷰 내용 | 스포일러 | 작성일");
                        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                    }

                    // 스포일러 여부를 가독성 좋게 'Y / N' 또는 '스포 / 일반'으로 변환 처리해 주면 센스 만점!
                    String spoilerText = rs.getBoolean("is_spoiler") ? "스포주의" : "일반";

                    System.out.printf(
                            "%d | %s | %s | %s | %s | %.1f | %s | %s | %s%n",
                            rs.getInt("review_id"),
                            rs.getString("user_id"),
                            rs.getString("username"),
                            rs.getString("title"),
                            rs.getString("platform_name"),
                            rs.getDouble("rating"),
                            rs.getString("review_text"),
                            spoilerText, // 변환한 스포일러 텍스트
                            rs.getTimestamp("review_date")
                    );
                }
                
                // 이상한 검색어를 치거나 데이터가 없을 때 경고 문구 작동!
                if (!hasData) {
                    System.out.println("해당 조건(ID 또는 이름)에 맞는 사용자 정보나 작성된 리뷰가 존재하지 않습니다!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 // 부적절한 리뷰 삭제
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
    
 // 콘텐츠별 리뷰 및 통계 조회 (ID 또는 제목 통합 검색)
    public void printReviewsByContent(String keyword) {
        
        // 1. 통계용 SQL
        String summarySql = """
                SELECT
                    C.content_id,
                    C.title,
                    ROUND(AVG(R.rating), 2) AS avg_rating,
                    COUNT(R.review_id) AS review_count
                FROM Content C
                LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
                LEFT JOIN Review R ON PC.pc_id = R.pc_id
                WHERE C.content_id = ? 
                   OR C.title LIKE ?
                GROUP BY C.content_id, C.title
                """;

        // 2. 목록용 SQL
        String listSql = """
                SELECT
                    R.review_id,
                    C.content_id,
                    C.title,
                    P.platform_name,
                    U.user_id,
                    U.username,
                    R.rating,
                    R.review_text,
                    R.review_date,
                    R.is_spoiler
                FROM Review R
                JOIN Users U ON R.user_id = U.user_id
                JOIN PlatformContent PC ON R.pc_id = PC.pc_id
                JOIN Content C ON PC.content_id = C.content_id
                JOIN Platform P ON PC.platform_id = P.platform_id
                WHERE C.content_id = ?
                   OR C.title LIKE ?
                ORDER BY R.review_date DESC
                """;

        try (Connection conn = DBConnection.getConnection()) {
            
            System.out.println("\n[콘텐츠별 리뷰 및 통계 조회]");
            boolean hasContent = false;

            // [콘텐츠별 리뷰 조회 6-1. 통계용] 콘텐츠별 평균 평점 및 리뷰 수 조회
            try (PreparedStatement pstmt1 = conn.prepareStatement(summarySql)) {
                pstmt1.setString(1, keyword);
                pstmt1.setString(2, "%" + keyword + "%");

                try (ResultSet rs1 = pstmt1.executeQuery()) {
                    if (rs1.next()) {
                        hasContent = true;
                        System.out.println("=========================================================================================================================");
                        System.out.printf("검색된 콘텐츠: [%d] %s%n", rs1.getInt("content_id"), rs1.getString("title"));
                        System.out.printf("평균 평점: %.2f점  | 총 리뷰 수: %d개%n", rs1.getDouble("avg_rating"), rs1.getInt("review_count"));
                        System.out.println("=========================================================================================================================");
                    }
                }
            }

            // 해당 콘텐츠 자체가 존재하지 않으면 종료
            if (!hasContent) {
                System.out.println("해당 조건(ID 또는 제목)에 맞는 콘텐츠 정보가 존재하지 않습니다!");
                return;
            }

            // [콘텐츠별 리뷰 조회 6-2. 목록용] 콘텐츠별 상세 리뷰 및 목록 조회
            try (PreparedStatement pstmt2 = conn.prepareStatement(listSql)) {
                pstmt2.setString(1, keyword);
                pstmt2.setString(2, "%" + keyword + "%");

                try (ResultSet rs2 = pstmt2.executeQuery()) {
                    boolean hasReviews = false;

                    while (rs2.next()) {
                        hasReviews = true;

                        if (rs2.isFirst()) {
                            System.out.println("\n[상세 리뷰 목록]");
                            System.out.println("리뷰ID | 작성자ID(이름) | 플랫폼 | 평점 | 스포일러 | 리뷰 내용 | 작성일");
                            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                        }

                        String spoilerText = rs2.getBoolean("is_spoiler") ? "스포주의" : "일반";

                        System.out.printf(
                                "%d | %s(%s) | %s | %.1f | %s | %s | %s%n",
                                rs2.getInt("review_id"),
                                rs2.getString("user_id"),
                                rs2.getString("username"),
                                rs2.getString("platform_name"),
                                rs2.getDouble("rating"),
                                spoilerText,
                                rs2.getString("review_text"),
                                rs2.getTimestamp("review_date")
                        );
                    }

                    if (!hasReviews) {
                        System.out.println("\n해당 콘텐츠에 작성된 리뷰가 아직 없습니다.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }}