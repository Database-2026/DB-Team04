package DB2026Team04.dto;

import java.sql.Timestamp;

public class UserDTO {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String role;
    private String membership;
    private String status;
    private Timestamp signupDate;

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