package DB2026Team04.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DB2026Team04.db.DBConnection;
import DB2026Team04.dto.ActivityDTO;

/**
 * 사용자 모드 - 시청 기록 & 리뷰 DAO (작성: 유진)
 * 트랜잭션: 시청완료 + 리뷰 동시 작성 포함
 */
public class UserActivityDAO {

    // ─────────────────────────────────────────────
    // 시청 기록 조회
    // ─────────────────────────────────────────────

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
            Connection conn = DBConnection.getConnection();
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
                    if (rs.getTimestamp("watched_date") != null)
                        dto.setWatchedDate(rs.getTimestamp("watched_date").toLocalDateTime());
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println("시청 기록 조회 중 오류 발생");
            e.printStackTrace();
        }

        return list;
    }

    // ─────────────────────────────────────────────
    // 리뷰 조회
    // ─────────────────────────────────────────────

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
            Connection conn = DBConnection.getConnection();
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
                    if (rs.getTimestamp("review_date") != null)
                        dto.setReviewDate(rs.getTimestamp("review_date").toLocalDateTime());
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println("리뷰 조회 중 오류 발생");
            e.printStackTrace();
        }

        return list;
    }

    // ─────────────────────────────────────────────
    // 리뷰 중복 확인
    // ─────────────────────────────────────────────

    public boolean hasReview(int userId, int pcId) {
        String sql = """
                SELECT COUNT(*) AS cnt FROM Review
                WHERE user_id = ? AND pc_id = ?
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, pcId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt") > 0;
            }
        } catch (Exception e) {
            System.out.println("리뷰 중복 확인 중 오류 발생");
            e.printStackTrace();
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // 시청 기록 CRUD
    // ─────────────────────────────────────────────

    public int addWatchHistory(int userId, int pcId, String watchStatus) {
        String sql = """
                INSERT INTO WatchHistory (user_id, pc_id, watch_status, watched_date)
                VALUES (?, ?, ?, NOW())
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, pcId);
            pstmt.setString(3, watchStatus);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("시청 기록 추가 중 오류 발생");
            e.printStackTrace();
        }
        return 0;
    }

    public int updateWatchStatus(int historyId, int userId, String watchStatus) {
        String sql = """
                UPDATE WatchHistory
                SET watch_status = ?, watched_date = NOW()
                WHERE history_id = ? AND user_id = ?
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, watchStatus);
            pstmt.setInt(2, historyId);
            pstmt.setInt(3, userId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("시청 상태 수정 중 오류 발생");
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteWatchHistory(int historyId, int userId) {
        String sql = """
                DELETE FROM WatchHistory
                WHERE history_id = ? AND user_id = ?
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, historyId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("시청 기록 삭제 중 오류 발생");
            e.printStackTrace();
        }
        return 0;
    }

    // ─────────────────────────────────────────────
    // 리뷰 CRUD
    // ─────────────────────────────────────────────

    public int addReview(int userId, int pcId, int rating, String reviewText, boolean isSpoiler) {
        if (rating < 1 || rating > 5) {
            System.out.println("평점은 1~5점만 입력할 수 있습니다.");
            return 0;
        }
        if (hasReview(userId, pcId)) {
            System.out.println("이미 해당 콘텐츠에 작성한 리뷰가 있습니다.");
            return 0;
        }

        String sql = """
                INSERT INTO Review (user_id, pc_id, rating, review_text, is_spoiler)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, pcId);
            pstmt.setInt(3, rating);
            pstmt.setString(4, reviewText);
            pstmt.setBoolean(5, isSpoiler);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("리뷰 작성 중 오류 발생");
            e.printStackTrace();
        }
        return 0;
    }

    public int updateReview(int reviewId, int userId, int rating, String reviewText, boolean isSpoiler) {
        if (rating < 1 || rating > 5) {
            System.out.println("평점은 1~5점만 입력할 수 있습니다.");
            return 0;
        }

        String sql = """
                UPDATE Review
                SET rating = ?, review_text = ?, is_spoiler = ?, review_date = NOW()
                WHERE review_id = ? AND user_id = ?
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, rating);
            pstmt.setString(2, reviewText);
            pstmt.setBoolean(3, isSpoiler);
            pstmt.setInt(4, reviewId);
            pstmt.setInt(5, userId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("리뷰 수정 중 오류 발생");
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteReview(int reviewId, int userId) {
        String sql = """
                DELETE FROM Review WHERE review_id = ? AND user_id = ?
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, reviewId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("리뷰 삭제 중 오류 발생");
            e.printStackTrace();
        }
        return 0;
    }

    // ─────────────────────────────────────────────
    // 트랜잭션: 시청완료 + 리뷰 동시 작성 (유진 구현)
    // ─────────────────────────────────────────────

    public boolean completeWatchAndAddReview(int userId, int pcId, int rating,
                                              String reviewText, boolean isSpoiler) {
        if (rating < 1 || rating > 5) {
            System.out.println("평점은 1~5점만 입력할 수 있습니다.");
            return false;
        }
        if (hasReview(userId, pcId)) {
            System.out.println("이미 해당 콘텐츠에 작성한 리뷰가 있어 트랜잭션을 진행할 수 없습니다.");
            return false;
        }

        String watchSql = """
                INSERT INTO WatchHistory (user_id, pc_id, watch_status, watched_date)
                VALUES (?, ?, 'COMPLETED', NOW())
                """;
        String reviewSql = """
                INSERT INTO Review (user_id, pc_id, rating, review_text, is_spoiler)
                VALUES (?, ?, ?, ?, ?)
                """;

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (
                PreparedStatement watchPstmt = conn.prepareStatement(watchSql);
                PreparedStatement reviewPstmt = conn.prepareStatement(reviewSql)
            ) {
                watchPstmt.setInt(1, userId);
                watchPstmt.setInt(2, pcId);
                watchPstmt.executeUpdate();

                reviewPstmt.setInt(1, userId);
                reviewPstmt.setInt(2, pcId);
                reviewPstmt.setInt(3, rating);
                reviewPstmt.setString(4, reviewText);
                reviewPstmt.setBoolean(5, isSpoiler);
                reviewPstmt.executeUpdate();

                conn.commit();
                return true;
            }
        } catch (Exception e) {
            System.out.println("트랜잭션 중 오류 발생 - rollback 실행");
            try { if (conn != null) conn.rollback(); }
            catch (Exception re) { re.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (Exception ce) { ce.printStackTrace(); }
        }
        return false;
    }
}
