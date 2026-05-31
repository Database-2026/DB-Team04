package DBTeam04.menu;

import java.util.ArrayList;
import java.util.Scanner;

import DBTeam04.dao.UserActivityDAO;
import DBTeam04.dao.UserContentDAO;
import DBTeam04.dto.ActivityDTO;

/**
 * 사용자 모드 메뉴 (통합: 지호)
 * - 콘텐츠 검색/추천 → UserContentDAO (지우)
 * - 시청기록/리뷰 → UserActivityDAO (유진)
 * - 입력값 검증 포함
 */
public class UserMenu {

    private final Scanner sc;
    private final UserContentDAO contentDAO = new UserContentDAO();
    private final UserActivityDAO userActivityDAO = new UserActivityDAO();
    private int currentUserId;

    public UserMenu(Scanner sc) {
        this.sc = sc;
    }

    public void showUserMenu(int userId) {
        this.currentUserId = userId;

        while (true) {
            System.out.println("\n========= [ 사용자 모드 ] =========");
            System.out.println("1. 콘텐츠 검색 및 조회");
            System.out.println("2. 내 시청 기록 관리");
            System.out.println("3. 내 리뷰 및 평점 관리");
            System.out.println("4. 인기 콘텐츠 및 추천 조회");
            System.out.println("0. 로그아웃");
            System.out.print("선택: ");

            switch (readInt()) {
                case 1 -> showSearchMenu();
                case 2 -> showHistoryMenu();
                case 3 -> showReviewMenu();
                case 4 -> showRecommendMenu();
                case 0 -> { return; }
                default -> System.out.println("❌ 잘못된 입력입니다.");
            }
        }
    }

    // ─────────────────────────────────────────────
    // 1. 콘텐츠 검색 및 조회 (지우)
    // ─────────────────────────────────────────────
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

            switch (readInt()) {
                case 1 -> { System.out.print("검색할 제목 키워드: "); contentDAO.searchByTitle(sc.nextLine()); }
                case 2 -> { System.out.print("검색할 장르명: "); contentDAO.searchByGenre(sc.nextLine()); }
                case 3 -> { System.out.print("유형 입력 (영화/드라마/예능/애니메이션): "); contentDAO.searchByType(sc.nextLine()); }
                case 4 -> { System.out.print("플랫폼명 입력: "); contentDAO.searchByPlatform(sc.nextLine()); }
                case 5 -> { System.out.print("상세 조회할 제목 입력: "); contentDAO.printUserContentDetail(sc.nextLine()); }
                case 0 -> { return; }
                default -> System.out.println("❌ 잘못된 입력입니다.");
            }
        }
    }

    // ─────────────────────────────────────────────
    // 2. 내 시청 기록 관리 (유진)
    // ─────────────────────────────────────────────
    private void showHistoryMenu() {
        while (true) {
            System.out.println("\n--- [ 내 시청 기록 관리 ] ---");
            System.out.println("1. 본 콘텐츠 등록");
            System.out.println("2. 내 시청 기록 조회");
            System.out.println("3. 시청 상태 수정");
            System.out.println("4. 시청 기록 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.print("PC_ID 입력: ");
                    int pcId = readInt();
                    System.out.print("상태 입력 (WATCHING / COMPLETED / ON_HOLD): ");
                    String status = sc.nextLine();
                    int result = userActivityDAO.addWatchHistory(currentUserId, pcId, status);
                    System.out.println(result > 0 ? "✅ 시청 기록 추가 완료." : "❌ 추가 실패.");
                }
                case 2 -> printWatchHistoryList(userActivityDAO.getWatchHistoryByUser(currentUserId));
                case 3 -> {
                    System.out.print("수정할 기록 ID: ");
                    int historyId = readInt();
                    System.out.print("새 상태 (WATCHING / COMPLETED / ON_HOLD): ");
                    String status = sc.nextLine();
                    int result = userActivityDAO.updateWatchStatus(historyId, currentUserId, status);
                    System.out.println(result > 0 ? "✅ 수정 완료." : "❌ 수정 실패.");
                }
                case 4 -> {
                    System.out.print("삭제할 기록 ID: ");
                    int historyId = readInt();
                    int result = userActivityDAO.deleteWatchHistory(historyId, currentUserId);
                    System.out.println(result > 0 ? "✅ 삭제 완료." : "❌ 삭제 실패.");
                }
                case 0 -> { return; }
                default -> System.out.println("❌ 잘못된 입력입니다.");
            }
        }
    }

    private void printWatchHistoryList(ArrayList<ActivityDTO> list) {
        if (list.isEmpty()) { System.out.println("시청 기록이 없습니다."); return; }
        System.out.println("\n[내 시청 기록]");
        System.out.println("기록ID | 제목 | 플랫폼 | 상태 | 시청일");
        System.out.println("------------------------------------------------------");
        for (ActivityDTO dto : list)
            System.out.printf("%d | %s | %s | %s | %s%n",
                    dto.getHistoryId(), dto.getTitle(),
                    dto.getPlatformName(), dto.getWatchStatus(), dto.getWatchedDate());
    }

    // ─────────────────────────────────────────────
    // 3. 내 리뷰 및 평점 관리 (유진 + 지우)
    // ─────────────────────────────────────────────
    private void showReviewMenu() {
        while (true) {
            System.out.println("\n--- [ 내 리뷰 및 평점 관리 ] ---");
            System.out.println("1. 리뷰 및 평점 작성");
            System.out.println("2. 내 리뷰 조회");
            System.out.println("3. 콘텐츠별 리뷰 조회");
            System.out.println("4. 리뷰 수정");
            System.out.println("5. 리뷰 삭제");
            System.out.println("6. 시청완료 + 리뷰 동시 작성 (트랜잭션)");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.print("PC_ID 입력: ");
                    int pcId = readInt();
                    System.out.print("평점 (1~5): ");
                    int rating = readInt();
                    System.out.print("리뷰 내용: ");
                    String text = sc.nextLine();
                    System.out.print("스포일러 포함? (y/n): ");
                    boolean spoiler = sc.nextLine().trim().equalsIgnoreCase("y");
                    int result = userActivityDAO.addReview(currentUserId, pcId, rating, text, spoiler);
                    System.out.println(result > 0 ? "✅ 리뷰 작성 완료." : "❌ 작성 실패.");
                }
                case 2 -> printReviewList(userActivityDAO.getReviewsByUser(currentUserId));
                case 3 -> { System.out.print("리뷰를 확인할 콘텐츠 제목: "); contentDAO.printReviewsByContent(sc.nextLine()); }
                case 4 -> {
                    System.out.print("수정할 리뷰 ID: ");
                    int reviewId = readInt();
                    System.out.print("새 평점 (1~5): ");
                    int rating = readInt();
                    System.out.print("새 리뷰 내용: ");
                    String text = sc.nextLine();
                    System.out.print("스포일러 포함? (y/n): ");
                    boolean spoiler = sc.nextLine().trim().equalsIgnoreCase("y");
                    int result = userActivityDAO.updateReview(reviewId, currentUserId, rating, text, spoiler);
                    System.out.println(result > 0 ? "✅ 수정 완료." : "❌ 수정 실패.");
                }
                case 5 -> {
                    System.out.print("삭제할 리뷰 ID: ");
                    int reviewId = readInt();
                    int result = userActivityDAO.deleteReview(reviewId, currentUserId);
                    System.out.println(result > 0 ? "✅ 삭제 완료." : "❌ 삭제 실패.");
                }
                case 6 -> {
                    System.out.print("PC_ID 입력: ");
                    int pcId = readInt();
                    System.out.print("평점 (1~5): ");
                    int rating = readInt();
                    System.out.print("리뷰 내용: ");
                    String text = sc.nextLine();
                    System.out.print("스포일러 포함? (y/n): ");
                    boolean spoiler = sc.nextLine().trim().equalsIgnoreCase("y");
                    boolean result = userActivityDAO.completeWatchAndAddReview(currentUserId, pcId, rating, text, spoiler);
                    System.out.println(result ? "✅ 시청완료 + 리뷰 작성 성공!" : "❌ 트랜잭션 실패.");
                }
                case 0 -> { return; }
                default -> System.out.println("❌ 잘못된 입력입니다.");
            }
        }
    }

    private void printReviewList(ArrayList<ActivityDTO> list) {
        if (list.isEmpty()) { System.out.println("작성한 리뷰가 없습니다."); return; }
        System.out.println("\n[내 리뷰 목록]");
        System.out.println("리뷰ID | 제목 | 플랫폼 | 평점 | 스포 | 내용");
        System.out.println("------------------------------------------------------");
        for (ActivityDTO dto : list)
            System.out.printf("%d | %s | %s | %d점 | %s | %s%n",
                    dto.getReviewId(), dto.getTitle(), dto.getPlatformName(),
                    dto.getRating(), dto.isSpoiler() ? "스포" : "일반", dto.getReviewText());
    }

    // ─────────────────────────────────────────────
    // 4. 인기 콘텐츠 및 추천 조회 (지우)
    // ─────────────────────────────────────────────
    private void showRecommendMenu() {
        while (true) {
            System.out.println("\n--- [ 인기 콘텐츠 및 추천 조회 ] ---");
            System.out.println("1. 전체 인기 콘텐츠 조회");
            System.out.println("2. 높은 평점 콘텐츠 조회");
            System.out.println("3. 내가 본 장르 기반 추천");
            System.out.println("4. 플랫폼별 추천 콘텐츠 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            switch (readInt()) {
                case 1 -> contentDAO.printTopPopularContents();
                case 2 -> contentDAO.printHighRatedContents();
                case 3 -> contentDAO.printRecommendedByGenre(currentUserId);
                case 4 -> contentDAO.printRecommendedBySubscription(currentUserId);
                case 0 -> { return; }
                default -> System.out.println("❌ 잘못된 입력입니다.");
            }
        }
    }

    // ─────────────────────────────────────────────
    // 입력값 검증 - 숫자 입력
    // ─────────────────────────────────────────────
    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ 숫자를 입력해주세요: ");
            }
        }
    }
}
