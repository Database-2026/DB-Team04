package db;

import java.sql.*;

public class DBConnectionTest {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/dbteam04";

        String user = "root";
        String password = "";

        try {

            Connection conn =
                DriverManager.getConnection(
                    url, user, password);

            System.out.println(
                "DB 연결 성공");

            conn.close();

        } catch(Exception e) {

            System.out.println(
                "DB 연결 실패");

            e.printStackTrace();
        }
    }
}