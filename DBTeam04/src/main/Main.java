package main;

import java.util.ArrayList;

import dao.ContentDAO;
import dto.ContentDTO;

public class Main {

    public static void main(String[] args) {
/*// [콘텐츠 검색 및 조회] >> 1. 제목으로 콘텐츠 검색
        ContentDAO dao1 = new ContentDAO();

        ArrayList<ContentDTO> list = dao1.searchByTitle("대군부인");

        for(ContentDTO dto : list) {
            System.out.println(dto);
        }
  */      
    	// [콘텐츠 검색 및 조회] >> 2. 장르별 콘텐츠 검색
        ContentDAO dao2 = new ContentDAO();

        ArrayList<ContentDTO> list2 =dao2.searchByGenre("드라마");

        for(ContentDTO dto : list2) {
            System.out.println(dto);
        }
    }
}