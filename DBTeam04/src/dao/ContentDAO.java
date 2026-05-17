package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBUtil;
import dto.ContentDTO;

public class ContentDAO {

    // 제목으로 콘텐츠 검색
    public ArrayList<ContentDTO> searchByTitle(String keyword) {

        ArrayList<ContentDTO> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 제목 LIKE ? " +
                "ORDER BY 제목 ASC";

        try {

            // DB 연결
            Connection conn =
                    DBUtil.getConnection();
            // SQL 준비
            PreparedStatement pstmt =
                    conn.prepareStatement(sql);
            // ? 값 설정
            pstmt.setString(
                    1,
                    "%" + keyword + "%");
            // SELECT 실행
            ResultSet rs =
                    pstmt.executeQuery();
            // 결과 읽기
            while(rs.next()) {
                ContentDTO dto = new ContentDTO();

                dto.setTitle(rs.getString("제목"));
                dto.setContentType(rs.getString("유형"));

                list.add(dto);
            }

            // 자원 정리
            rs.close();
            pstmt.close();
            conn.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public ArrayList<ContentDTO> searchByGenre(String genre) {

        ArrayList<ContentDTO> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM v_content_detail " +
                "WHERE 장르 = ? " +
                "ORDER BY 플랫폼평점 DESC";

        try {

            // DB 연결
            Connection conn =DBUtil.getConnection();
            // SQL 준비
            PreparedStatement pstmt =conn.prepareStatement(sql);
            // ? 값 설정
            pstmt.setString(1, genre);
            // SELECT 실행
            ResultSet rs =pstmt.executeQuery();
            // 결과 읽기
            while(rs.next()) {
                ContentDTO dto =new ContentDTO();
                
                dto.setTitle(rs.getString("제목"));
                dto.setContentType(rs.getString("유형"));

                list.add(dto);

            }

            // 자원 정리
            rs.close();
            pstmt.close();
            conn.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}