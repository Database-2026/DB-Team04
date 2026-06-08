package DB2026Team04.dto;

import java.sql.Timestamp;

/**
 * 사용자 정보를 저장하고 전달하기 위한 DTO 클래스
 */

public class UserDTO {
    // 사용자 기본 정보
    private int userId;
    private String username;
    private String email;
    private String password;

    // 사용자 권한 및 상태 정보
    private String role;
    private String membership;
    private String status;

    // 가입일
    private Timestamp signupDate;

    // getter / setter
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getMembership() { return membership; }
    public void setMembership(String membership) { this.membership = membership; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getSignupDate() { return signupDate; }
    public void setSignupDate(Timestamp signupDate) { this.signupDate = signupDate; }
}