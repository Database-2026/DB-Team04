package menu;

import java.util.Scanner;
import dao.ContentDAO;

public class UserMenu {
    private Scanner sc;
    private ContentDAO dao;
    private int currentUserId; // 추천 기능을 위한 사용자 ID

    public UserMenu(Scanner sc) {
        this.sc = sc;
        this.dao = new ContentDAO();
    }

    public void showUserMenu() {
        System.out.print("사용자 ID를 입력하세요: ");
        this.currentUserId = sc.nextInt();
        sc.nextLine();

        while (true) {
            System.out.println("\n========= [ 사용자 모드 ] =========");
            System.out.println("1. 콘텐츠 검색 및 조회");
            System.out.println("2. 내 시청 기록 관리");
            System.out.println("3. 내 리뷰 및 평점 관리");
            System.out.println("4. 인기 콘텐츠 및 추천 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showSearchMenu();
                case 2 -> showHistoryMenu();
                case 3 -> showReviewMenu();
                case 4 -> showRecommendMenu();
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // --- [1. 콘텐츠 검색 및 조회] ---
    private void showSearchMenu() {
        while (true) {
            System.out.println("\n--- [ 콘텐츠 검색 및 조회 ] ---");
            System.out.println("1. 제목으로 콘텐츠 검색");
            System.out.println("2. 장르별 콘텐츠 검색");
            System.out.println("3. 콘텐츠 유형별 검색 (영화/드라마 등)");
            System.out.println("4. 플랫폼별 콘텐츠 조회");
            System.out.println("5. 콘텐츠 상세 정보 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("검색할 제목 키워드: ");
                    String keyword = sc.nextLine();
                    dao.searchByTitle(keyword); // 지우님 구현 메서드
                }
                case 2 -> {
                    System.out.print("검색할 장르명: ");
                    String genre = sc.nextLine();
                    dao.searchByGenre(genre); // 지우님 구현 메서드
                }
                case 3 -> {
                    System.out.print("유형 입력 (영화/드라마/예능/애니): ");
                    String type = sc.nextLine();
                    dao.searchByType(type); // 지우님 구현 예정
                }
                case 4 -> {
                    System.out.print("플랫폼명 입력: ");
                    String platform = sc.nextLine();
                    dao.searchByPlatform(platform); // 지우님 구현 예정
                }
                case 5 -> {
                    System.out.print("상세 조회할 제목 입력: ");
                    String title = sc.nextLine();
                    dao.printUserContentDetail(title); // 지우님 구현 메서드
                }
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // --- [2. 내 시청 기록 관리] ---
    private void showHistoryMenu() {
        while (true) {
            System.out.println("\n--- [ 내 시청 기록 관리 ] ---");
            System.out.println("1. 본 콘텐츠 등록");
            System.out.println("2. 내 시청 기록 조회");
            System.out.println("3. 시청 상태 수정");
            System.out.println("4. 시청 기록 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> { /* TODO: 팀원 구현 (본 콘텐츠 등록) */ }
                case 2 -> { /* TODO: 팀원 구현 (내 시청 기록 조회) */ }
                case 3 -> { /* TODO: 팀원 구현 (시청 상태 수정) */ }
                case 4 -> { /* TODO: 팀원 구현 (시청 기록 삭제) */ }
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // --- [3. 내 리뷰 및 평점 관리] ---
    private void showReviewMenu() {
        while (true) {
            System.out.println("\n--- [ 내 리뷰 및 평점 관리 ] ---");
            System.out.println("1. 리뷰 및 평점 작성");
            System.out.println("2. 내 리뷰 조회");
            System.out.println("3. 콘텐츠별 리뷰 조회");
            System.out.println("4. 리뷰 수정");
            System.out.println("5. 리뷰 삭제");
            System.out.println("6. 시청 완료 및 리뷰 작성");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> { /* TODO: 팀원 구현 */ }
                case 2 -> { /* TODO: 팀원 구현 */ }
                case 3 -> {
                    System.out.print("리뷰를 확인할 콘텐츠 제목: ");
                    String title = sc.nextLine();
                    dao.printReviewsByContent(title); // 지우님 구현 메서드
                }
                case 4 -> { /* TODO: 팀원 구현 */ }
                case 5 -> { /* TODO: 팀원 구현 */ }
                case 6 -> { /* TODO: 팀원 구현 */ }
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // --- [4. 인기 콘텐츠 및 추천 조회] ---
    private void showRecommendMenu() {
        while (true) {
            System.out.println("\n--- [ 인기 콘텐츠 및 추천 조회 ] ---");
            System.out.println("1. 전체 인기 콘텐츠 조회");
            System.out.println("2. 높은 평점 콘텐츠 조회");
            System.out.println("3. 내가 본 장르 기반 추천");
            System.out.println("4. 플랫폼별 추천 콘텐츠 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> dao.printTopPopularContents();   // 지우님 구현 메서드
                case 2 -> dao.printHighRatedContents();    // 지우님 구현 메서드
                case 3 -> dao.printRecommendedByGenre(currentUserId); // 지우님 구현 메서드
                case 4 -> dao.printRecommendedBySubscription(currentUserId); // 지우님 구현 메서드
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }
}