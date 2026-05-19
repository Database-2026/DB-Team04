package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBUtil;
import dto.ActivityDTO;

public class ActivityDAO {

    // 내 시청 기록 조회
    public ArrayList<ActivityDTO> getWatchHistoryByUser(int userId) {
        ArrayList<ActivityDTO> list = new ArrayList<>();

        String sql = """
                SELECT 
                    wh.history_id,
                    wh.user_id,
                    wh.pc_id,
                    u.username,
                    c.title,
                    p.platform_name,
                    wh.watch_status,
                    wh.watched_date
                FROM WatchHistory wh
                JOIN Users u ON wh.user_id = u.user_id
                JOIN PlatformContent pc ON wh.pc_id = pc.pc_id
                JOIN Content c ON pc.content_id = c.content_id
                JOIN Platform p ON pc.platform_id = p.platform_id
                WHERE wh.user_id = ?
                ORDER BY wh.watched_date DESC
                """;

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ActivityDTO dto = new ActivityDTO();

                    dto.setHistoryId(rs.getInt("history_id"));
                    dto.setUserId(rs.getInt("user_id"));
                    dto.setPcId(rs.getInt("pc_id"));
                    dto.setUsername(rs.getString("username"));
                    dto.setTitle(rs.getString("title"));
                    dto.setPlatformName(rs.getString("platform_name"));
                    dto.setWatchStatus(rs.getString("watch_status"));

                    if (rs.getTimestamp("watched_date") != null) {
                        dto.setWatchedDate(rs.getTimestamp("watched_date").toLocalDateTime());
                    }

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            System.out.println("시청 기록 조회 중 오류 발생");
            e.printStackTrace();
        }

        return list;
    }

    // 내 리뷰 조회
    public ArrayList<ActivityDTO> getReviewsByUser(int userId) {
        ArrayList<ActivityDTO> list = new ArrayList<>();

        String sql = """
                SELECT 
                    r.review_id,
                    r.user_id,
                    r.pc_id,
                    u.username,
                    c.title,
                    p.platform_name,
                    r.rating,
                    r.review_text,
                    r.review_date,
                    r.is_spoiler
                FROM Review r
                JOIN Users u ON r.user_id = u.user_id
                JOIN PlatformContent pc ON r.pc_id = pc.pc_id
                JOIN Content c ON pc.content_id = c.content_id
                JOIN Platform p ON pc.platform_id = p.platform_id
                WHERE r.user_id = ?
                ORDER BY r.review_date DESC
                """;

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ActivityDTO dto = new ActivityDTO();

                    dto.setReviewId(rs.getInt("review_id"));
                    dto.setUserId(rs.getInt("user_id"));
                    dto.setPcId(rs.getInt("pc_id"));
                    dto.setUsername(rs.getString("username"));
                    dto.setTitle(rs.getString("title"));
                    dto.setPlatformName(rs.getString("platform_name"));
                    dto.setRating(rs.getInt("rating"));
                    dto.setReviewText(rs.getString("review_text"));
                    dto.setSpoiler(rs.getBoolean("is_spoiler"));

                    if (rs.getTimestamp("review_date") != null) {
                        dto.setReviewDate(rs.getTimestamp("review_date").toLocalDateTime());
                    }

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            System.out.println("리뷰 조회 중 오류 발생");
            e.printStackTrace();
        }

        return list;
    }

    // 시청 기록 추가
    public int addWatchHistory(int userId, int pcId, String watchStatus) {
        // 추후 INSERT 쿼리 연결 예정
        return 0;
    }

    // 시청 상태 수정
    public int updateWatchStatus(int historyId, int userId, String watchStatus) {
        // 추후 UPDATE 쿼리 연결 예정
        return 0;
    }

    // 시청 기록 삭제
    public int deleteWatchHistory(int historyId, int userId) {
        // 추후 DELETE 쿼리 연결 예정
        return 0;
    }

    // 리뷰 작성
    public int addReview(int userId, int pcId, int rating, String reviewText, boolean isSpoiler) {
        // 추후 INSERT 쿼리 연결 예정
        return 0;
    }

    // 리뷰 수정
    public int updateReview(int reviewId, int userId, int rating, String reviewText, boolean isSpoiler) {
        // 추후 UPDATE 쿼리 연결 예정
        return 0;
    }

    // 리뷰 삭제
    public int deleteReview(int reviewId, int userId) {
        // 추후 DELETE 쿼리 연결 예정
        return 0;
    }

    // 시청 완료 + 리뷰 작성 트랜잭션
    public boolean completeWatchAndAddReview(int userId, int pcId, int rating, String reviewText, boolean isSpoiler) {
        // DBUtil.java 연결 후 conn.setAutoCommit(false)로 구현 예정
        return false;
    }
}