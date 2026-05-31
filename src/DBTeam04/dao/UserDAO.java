package DBTeam04.dao;

import DBTeam04.db.DBConnection;
import DBTeam04.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 회원 목록 조회
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userList = new ArrayList<>();

        String sql = """
                SELECT user_id, username, email, role, membership, status, signup_date
                FROM Users
                ORDER BY user_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                UserDTO user = mapResultSetToUser(rs);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    // 회원 이름으로 검색
    public List<UserDTO> searchUsersByName(String keyword) {
        List<UserDTO> userList = new ArrayList<>();

        String sql = """
                SELECT user_id, username, email, role, membership, status, signup_date
                FROM Users
                WHERE username LIKE ?
                ORDER BY user_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserDTO user = mapResultSetToUser(rs);
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
    
 // 회원 검색: 이름, 이메일, 역할, 멤버십, 상태 기준
    public List<UserDTO> searchUsers(String type, String keyword) {
        List<UserDTO> userList = new ArrayList<>();

        String sql = """
                SELECT user_id, username, email, role, membership, status, signup_date
                FROM Users
                WHERE 
                """;

        switch (type) {
            case "name":
                sql += "username LIKE ?";
                break;
            case "email":
                sql += "email LIKE ?";
                break;
            case "role":
                sql += "role = ?";
                break;
            case "membership":
                sql += "membership = ?";
                break;
            case "status":
                sql += "status = ?";
                break;
            default:
                return userList;
        }

        sql += " ORDER BY user_id";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            if (type.equals("name") || type.equals("email")) {
                pstmt.setString(1, "%" + keyword + "%");
            } else {
                pstmt.setString(1, keyword);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserDTO user = mapResultSetToUser(rs);
                    userList.add(user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
    
 // 회원 등록
    public boolean insertUser(UserDTO user) {
        String sql = """
                INSERT INTO Users (username, email, password, role, membership, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getMembership());
            pstmt.setString(6, user.getStatus());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 회원 정보 수정
    public boolean updateUser(UserDTO user) {
        String sql = """
                UPDATE Users
                SET username = ?, email = ?, membership = ?, status = ?
                WHERE user_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getMembership());
            pstmt.setString(4, user.getStatus());
            pstmt.setInt(5, user.getUserId());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 회원 탈퇴 처리: 실제 삭제 대신 상태를 INACTIVE로 변경
    public boolean deactivateUser(int userId) {
        String sql = """
                UPDATE Users
                SET status = 'INACTIVE'
                WHERE user_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 회원 완전 삭제
    public boolean deleteUser(int userId) {
        String sql = """
                DELETE FROM Users
                WHERE user_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // 로그인 (이메일 또는 user_id + 비밀번호)
    public UserDTO login(String loginId, String password) {
        String sql = """
                SELECT user_id, username, email, role, membership, status, signup_date
                FROM Users
                WHERE (email = ? OR CAST(user_id AS CHAR) = ?)
                AND password = ? AND status = 'ACTIVE'
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, loginId);
            pstmt.setString(3, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapResultSetToUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 이메일 중복 확인
    public boolean isEmailDuplicate(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 회원가입
    public boolean register(String username, String email, String password) {
        if (isEmailDuplicate(email)) return false;

        String sql = """
                INSERT INTO Users (username, email, password, role, membership, status)
                VALUES (?, ?, ?, 'USER', 'BASIC', 'ACTIVE')
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ResultSet 한 행을 UserDTO로 변환
    private UserDTO mapResultSetToUser(ResultSet rs) throws Exception {
        UserDTO user = new UserDTO();

        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setMembership(rs.getString("membership"));
        user.setStatus(rs.getString("status"));
        user.setSignupDate(rs.getTimestamp("signup_date"));

        return user;
    }
}// 이 파일에 내용 추가 필요 - str_replace로 처리
