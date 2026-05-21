package main;


import dao.ContentDAO;
import dto.ContentDTO;

public class Main {

    public static void main(String[] args) {
    	
    	// [콘텐츠 검색 및 조회] >> 1. 제목으로 콘텐츠 검색
        // Main.java 예시
    	/*
    	ContentDAO dao = new ContentDAO();
    	dao.searchByTitle("골드");
    */
    	
    	// [콘텐츠 검색 및 조회] >> 2. 장르별 콘텐츠 검색
		/*
		 * ContentDAO dao2 = new ContentDAO();
		 * 
		 * dao2.searchByGenre("코미디");
		 */
        
        
      
    	//[콘텐츠 검색 및 조회] >> 3. 유형별 콘텐츠 검색 
		/*
		 * ContentDAO dao=new ContentDAO(); dao.searchByType("예능");
		 */
    	
    	
    	//[콘텐츠 검색 및 조회] >> 4. 플랫폼별 콘텐츠 검색 
    	
		/*
		 * ContentDAO dao = new ContentDAO();
		 * 
		 * dao.searchByPlatform("Netflix");
		 */
        
    	
    	//[콘텐츠 검색 및 조회] >> 5. 콘텐츠 상세 정보 조회
    	/*
    	ContentDAO dao = new ContentDAO();

        dao.printUserContentDetail("좀비");
*/

		
		//[인기 콘텐츠 및 추천 조회] >> 1. 전체 인기 콘텐츠 조회
		/*
		 * ContentDAO dao = new ContentDAO(); dao.printTopPopularContents();
		 */
    	
    	//[인기 콘텐츠 및 추천 조회] >> 2. 높은 평점 콘텐츠 조회
//    	ContentDAO dao = new ContentDAO();
//    	dao.printHighRatedContents();
    	
    	
    	//[인기 콘텐츠 및 추천 조회] >> 3. 내가 본 장르 기반 추천
		/*
		 * ContentDAO dao = new ContentDAO(); dao.printRecommendedByGenre(23);
		 */
    	
    	 // [인기 콘텐츠 및 추천 조회] >> 4. 플랫폼별 추천 콘텐츠 조회
			/*
			 * ContentDAO dao = new ContentDAO(); dao.printRecommendedBySubscription(24);
			 */

       
    }
}
