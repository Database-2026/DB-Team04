package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // 1. 데이터베이스 접속 정보 (본인의 DB 설정에 맞게 수정)
    private static final String URL = "jdbc:mysql://localhost:3306/DBTeam04";
    private static final String USER = "root";       // MySQL 아이디
    private static final String PASSWORD = "root"; // MySQL 비밀번호

    // 2. 드라이버 로딩 (클래스가 로드될 때 한 번 실행)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        }
    }

    // 3. 커넥션 객체를 가져오는 메서드
    public static Connection getConnection() throws SQLException {
    	Connection conn = null;

        try {

            conn =
                DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
                );

            System.out.println(
                "DB 연결 성공");

        } catch(Exception e) {

            System.out.println(
                "DB 연결 실패");

            e.printStackTrace();
        }

        return conn;
    }
}