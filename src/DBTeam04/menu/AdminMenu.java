package DBTeam04.menu;

import DBTeam04.dao.UserDAO;
import DBTeam04.dto.UserDTO;
import DBTeam04.dao.PlatformDAO;
import DBTeam04.dto.PlatformDTO;
import DBTeam04.dao.ContentDAO;
import DBTeam04.dto.ContentDTO;
import DBTeam04.dao.PlatformContentDAO;
import DBTeam04.dao.ActivityDAO;
import DBTeam04.dao.StatisticsDAO;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner sc;
    private final UserDAO userDAO = new UserDAO();
    private final PlatformDAO platformDAO = new PlatformDAO();
    private final ContentDAO contentDAO = new ContentDAO();
    private final PlatformContentDAO platformContentDAO = new PlatformContentDAO();
    private final ActivityDAO activityDAO = new ActivityDAO();
    private final StatisticsDAO statisticsDAO = new StatisticsDAO();
    public AdminMenu(Scanner sc) {
        this.sc = sc;
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n[관리자 모드]");
            System.out.println("1. 회원 관리");
            System.out.println("2. 콘텐츠 관리");
            System.out.println("3. 플랫폼 관리");
            System.out.println("4. 사용자 활동 관리");
            System.out.println("5. 통계 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("메뉴 선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showUserManageMenu();
                case 2 -> showContentManageMenu();
                case 3 -> showPlatformManageMenu();
                case 4 -> showActivityMenu();
                case 5 -> showStatisticsMenu();
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showUserManageMenu() {
        while (true) {
            System.out.println("\n[회원 관리]");
            System.out.println("1. 회원 등록");
            System.out.println("2. 회원 목록 조회");
            System.out.println("3. 회원 검색");
            System.out.println("4. 회원 정보 수정");
            System.out.println("5. 회원 삭제/탈퇴 처리");
            System.out.println("0. 뒤로가기");
            System.out.print("메뉴 선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> insertUserMenu();
                case 2 -> printUserList();
                case 3 -> searchUserMenu();
                case 4 -> updateUserMenu();
                case 5 -> deleteUserMenu();
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void printUserList() {
        List<UserDTO> users = userDAO.getAllUsers();

        System.out.println("\n[회원 목록]");
        System.out.println("ID | 이름 | 이메일 | 역할 | 멤버십 | 상태 | 가입일");
        System.out.println("------------------------------------------------------------");

        for (UserDTO user : users) {
            System.out.printf(
                    "%d | %s | %s | %s | %s | %s | %s%n",
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole(),
                    user.getMembership(),
                    user.getStatus(),
                    user.getSignupDate()
            );
        }
    }
    
    private void searchUserMenu() {
        System.out.println("\n[회원 검색]");
        System.out.println("1. 이름으로 검색");
        System.out.println("2. 이메일로 검색");
        System.out.println("3. 역할로 검색 (USER / ADMIN)");
        System.out.println("4. 멤버십으로 검색 (FREE / BASIC / STANDARD / PREMIUM)");
        System.out.println("5. 상태로 검색 (ACTIVE / INACTIVE / BANNED)");
        System.out.println("0. 뒤로가기");
        System.out.print("검색 기준 선택: ");

        int typeChoice = sc.nextInt();
        sc.nextLine();

        if (typeChoice == 0) {
            return;
        }

        String type;

        switch (typeChoice) {
            case 1 -> type = "name";
            case 2 -> type = "email";
            case 3 -> type = "role";
            case 4 -> type = "membership";
            case 5 -> type = "status";
            default -> {
                System.out.println("잘못된 입력입니다.");
                return;
            }
        }

        System.out.print("검색어 입력: ");
        String keyword = sc.nextLine();

        List<UserDTO> users = userDAO.searchUsers(type, keyword);

        System.out.println("\n[회원 검색 결과]");
        System.out.println("ID | 이름 | 이메일 | 역할 | 멤버십 | 상태 | 가입일");
        System.out.println("------------------------------------------------------------");

        if (users.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        for (UserDTO user : users) {
            System.out.printf(
                    "%d | %s | %s | %s | %s | %s | %s%n",
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole(),
                    user.getMembership(),
                    user.getStatus(),
                    user.getSignupDate()
            );
        }
    }
    
    private void insertUserMenu() {
        System.out.println("\n[회원 등록]");

        System.out.print("이름: ");
        String username = sc.nextLine();

        System.out.print("이메일: ");
        String email = sc.nextLine();

        System.out.print("비밀번호: ");
        String password = sc.nextLine();

        System.out.print("역할(USER / ADMIN): ");
        String role = sc.nextLine();

        System.out.print("멤버십(FREE / BASIC / STANDARD / PREMIUM): ");
        String membership = sc.nextLine();

        System.out.print("상태(ACTIVE / INACTIVE / BANNED): ");
        String status = sc.nextLine();

        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setMembership(membership);
        user.setStatus(status);

        boolean success = userDAO.insertUser(user);

        if (success) {
            System.out.println("회원 등록이 완료되었습니다.");
        } else {
            System.out.println("회원 등록에 실패했습니다.");
        }
    }
    
    private void updateUserMenu() {
        System.out.println("\n[회원 정보 수정]");

        System.out.print("수정할 회원 ID: ");
        int userId = sc.nextInt();
        sc.nextLine();

        System.out.print("새 이름: ");
        String username = sc.nextLine();

        System.out.print("새 이메일: ");
        String email = sc.nextLine();

        System.out.print("새 멤버십(FREE / BASIC / STANDARD / PREMIUM): ");
        String membership = sc.nextLine();

        System.out.print("새 상태(ACTIVE / INACTIVE / BANNED): ");
        String status = sc.nextLine();

        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setMembership(membership);
        user.setStatus(status);

        boolean success = userDAO.updateUser(user);

        if (success) {
            System.out.println("회원 정보가 수정되었습니다.");
        } else {
            System.out.println("회원 정보 수정 실패.");
        }
    }
    
    private void deleteUserMenu() {
        System.out.println("\n[회원 삭제/탈퇴 처리]");
        System.out.println("1. 탈퇴 처리(INACTIVE)");
        System.out.println("2. 회원 완전 삭제(DELETE)");
        System.out.println("0. 뒤로가기");
        System.out.print("선택: ");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) {
            return;
        }

        System.out.print("대상 회원 ID: ");
        int userId = sc.nextInt();
        sc.nextLine();

        boolean success = false;

        switch (choice) {
            case 1 -> success = userDAO.deactivateUser(userId);
            case 2 -> {
                System.out.print("정말 삭제하시겠습니까? (Y/N): ");
                String confirm = sc.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {
                    success = userDAO.deleteUser(userId);
                } else {
                    System.out.println("삭제가 취소되었습니다.");
                    return;
                }
            }
            default -> {
                System.out.println("잘못된 입력입니다.");
                return;
            }
        }

        if (success) {
            System.out.println("처리가 완료되었습니다.");
        } else {
            System.out.println("처리에 실패했습니다.");
        }
    }
    
    private void showPlatformManageMenu() {
        while (true) {
            System.out.println("\n[플랫폼 관리]");
            System.out.println("1. 플랫폼 등록");
            System.out.println("2. 플랫폼 목록 조회");
            System.out.println("3. 플랫폼 검색");
            System.out.println("4. 플랫폼 정보 수정");
            System.out.println("5. 플랫폼 삭제");
            System.out.println("6. 플랫폼별 제공 콘텐츠 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("메뉴 선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            	case 1 -> insertPlatformMenu();
                case 2 -> printPlatformList();
                case 3 -> searchPlatformMenu();
                case 4 -> updatePlatformMenu();
                case 5 -> deletePlatformMenu();
                case 6 -> printPlatformContentsMenu();
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void printPlatformList() {
        List<PlatformDTO> platforms = platformDAO.getAllPlatforms();

        System.out.println("\n[플랫폼 목록]");
        System.out.println("ID | 플랫폼명 | 월 구독료");
        System.out.println("--------------------------------");

        if (platforms.isEmpty()) {
            System.out.println("등록된 플랫폼이 없습니다.");
            return;
        }

        for (PlatformDTO platform : platforms) {
            System.out.printf(
                    "%d | %s | %.0f원%n",
                    platform.getPlatformId(),
                    platform.getPlatformName(),
                    platform.getPlatformPrice()
            );
        }
    }
    
    private void insertPlatformMenu() {
        System.out.println("\n[플랫폼 등록]");

        System.out.print("플랫폼 이름: ");
        String name = sc.nextLine();

        System.out.print("월 구독료: ");
        double price = sc.nextDouble();
        sc.nextLine();

        PlatformDTO platform = new PlatformDTO();
        platform.setPlatformName(name);
        platform.setPlatformPrice(price);

        boolean success = platformDAO.insertPlatform(platform);

        if (success) {
            System.out.println("플랫폼 등록 완료.");
        } else {
            System.out.println("플랫폼 등록 실패.");
        }
    }
    
    private void searchPlatformMenu() {
        System.out.print("검색할 플랫폼 이름: ");
        String keyword = sc.nextLine();

        List<PlatformDTO> platforms = platformDAO.searchPlatforms(keyword);

        System.out.println("\n[플랫폼 검색 결과]");
        System.out.println("ID | 플랫폼명 | 월 구독료");
        System.out.println("--------------------------------");

        if (platforms.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        for (PlatformDTO platform : platforms) {
            System.out.printf(
                    "%d | %s | %.0f원%n",
                    platform.getPlatformId(),
                    platform.getPlatformName(),
                    platform.getPlatformPrice()
            );
        }
    }
    
    private void updatePlatformMenu() {
        System.out.println("\n[플랫폼 수정]");

        System.out.print("수정할 플랫폼 ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("새 플랫폼 이름: ");
        String name = sc.nextLine();

        System.out.print("새 월 구독료: ");
        double price = sc.nextDouble();
        sc.nextLine();

        PlatformDTO platform = new PlatformDTO();
        platform.setPlatformId(id);
        platform.setPlatformName(name);
        platform.setPlatformPrice(price);

        boolean success = platformDAO.updatePlatform(platform);

        if (success) {
            System.out.println("플랫폼 수정 완료.");
        } else {
            System.out.println("플랫폼 수정 실패.");
        }
    }
    
    private void deletePlatformMenu() {
        System.out.println("\n[플랫폼 삭제]");

        System.out.print("삭제할 플랫폼 ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("정말 삭제하시겠습니까? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("삭제 취소.");
            return;
        }

        boolean success = platformDAO.deletePlatform(id);

        if (success) {
            System.out.println("플랫폼 삭제 완료.");
        } else {
            System.out.println("플랫폼 삭제 실패.");
        }
    }
    
    private void showContentManageMenu() {
        while (true) {
            System.out.println("\n[콘텐츠 관리]");
            System.out.println("1. 콘텐츠 등록 및 제공 플랫폼 연결");
            System.out.println("2. 콘텐츠 목록 조회");
            System.out.println("3. 콘텐츠 검색");
            System.out.println("4. 콘텐츠 상세 정보 조회");
            System.out.println("5. 콘텐츠 정보 수정");
            System.out.println("6. 플랫폼 연결 수정/삭제");
            System.out.println("7. 콘텐츠 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("메뉴 선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> insertContentMenu();
                case 2 -> printContentList();
                case 3 -> searchContentMenu();
                case 4 -> printContentDetailMenu();
                case 5 -> updateContentMenu();
                case 6 -> managePlatformConnectionMenu();
                case 7 -> deleteContentMenu();
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }
    
    private void printContentList() {
        List<ContentDTO> contents = contentDAO.getAllContents();

        System.out.println("\n[콘텐츠 목록]");
        System.out.println("ID | 제목 | 유형 | 공개연도 | 연령등급 | 설명");
        System.out.println("------------------------------------------------------------");

        for (ContentDTO c : contents) {
            System.out.printf(
                    "%d | %s | %s | %d | %s | %s%n",
                    c.getContentId(),
                    c.getTitle(),
                    c.getContentType(),
                    c.getReleaseYear(),
                    c.getAgeRating(),
                    c.getDescription()
            );
        }
    }
    
    private void insertContentMenu() {

        ContentDTO content = new ContentDTO();

        System.out.print("제목: ");
        content.setTitle(sc.nextLine());

        System.out.print("유형: ");
        content.setContentType(sc.nextLine());

        System.out.print("공개 연도: ");
        content.setReleaseYear(sc.nextInt());
        sc.nextLine();

        System.out.print("연령 등급: ");
        content.setAgeRating(sc.nextLine());

        System.out.print("설명: ");
        content.setDescription(sc.nextLine());

        // 콘텐츠 INSERT
        int contentId = contentDAO.insertContent(content);

        if (contentId == -1) {
            System.out.println("콘텐츠 등록 실패.");
            return;
        }

        System.out.println("콘텐츠 등록 완료.");
        System.out.println("생성된 content_id: " + contentId);

        // 플랫폼 연결 여부
        System.out.print("플랫폼 연결을 추가하시겠습니까? (Y/N): ");
        String connect = sc.nextLine();

        if (!connect.equalsIgnoreCase("Y")) {
            return;
        }

        while (true) {

            System.out.print("플랫폼 ID 입력: ");
            int platformId = sc.nextInt();

            System.out.print("플랫폼 평점 입력: ");
            double rating = sc.nextDouble();
            sc.nextLine();

            boolean success =
                    platformContentDAO.connectContentPlatform(
                            contentId,
                            platformId,
                            rating
                    );

            if (success) {
                System.out.println("플랫폼 연결 완료.");
            } else {
                System.out.println("플랫폼 연결 실패.");
            }

            System.out.print("다른 플랫폼도 연결하시겠습니까? (Y/N): ");
            String more = sc.nextLine();

            if (!more.equalsIgnoreCase("Y")) {
                break;
            }
        }
    }
    
    private void searchContentMenu() {

        System.out.println("\n[콘텐츠 검색]");
        System.out.println("1. 제목 검색");
        System.out.println("2. 유형 검색 (영화/드라마/예능/애니메이션)");
        System.out.println("3. 공개연도 검색");
        System.out.println("4. 연령등급 검색 (7세/12세/15세/19세/전체관람가)");
        System.out.println("0. 뒤로가기");
        System.out.print("검색 기준 선택: ");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) {
            return;
        }

        String type;

        switch (choice) {
            case 1 -> type = "title";
            case 2 -> type = "content_type";
            case 3 -> type = "release_year";
            case 4 -> type = "age_rating";

            default -> {
                System.out.println("잘못된 입력입니다.");
                return;
            }
        }

        System.out.print("검색어 입력: ");
        String keyword = sc.nextLine();

        List<ContentDTO> contents =
                contentDAO.searchContents(type, keyword);

        System.out.println("\n[콘텐츠 검색 결과]");
        System.out.println("ID | 제목 | 유형 | 공개연도 | 연령등급 | 설명");
        System.out.println("------------------------------------------------------------");

        if (contents.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        for (ContentDTO c : contents) {
            System.out.printf(
                    "%d | %s | %s | %d | %s | %s%n",
                    c.getContentId(),
                    c.getTitle(),
                    c.getContentType(),
                    c.getReleaseYear(),
                    c.getAgeRating(),
                    c.getDescription()
            );
        }
    }
    
    private void updateContentMenu() {
        ContentDTO content = new ContentDTO();

        System.out.print("수정할 콘텐츠 ID: ");
        content.setContentId(sc.nextInt());
        sc.nextLine();

        System.out.print("새 제목: ");
        content.setTitle(sc.nextLine());

        System.out.print("새 유형: ");
        content.setContentType(sc.nextLine());

        System.out.print("새 공개 연도: ");
        content.setReleaseYear(sc.nextInt());
        sc.nextLine();

        System.out.print("새 연령 등급: ");
        content.setAgeRating(sc.nextLine());

        System.out.print("새 설명: ");
        content.setDescription(sc.nextLine());

        boolean success = contentDAO.updateContent(content);

        System.out.println(success ?
                "콘텐츠 수정 완료." :
                "콘텐츠 수정 실패.");
    }
    
    private void deleteContentMenu() {
        System.out.print("삭제할 콘텐츠 ID: ");

        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("정말 삭제하시겠습니까? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("삭제 취소.");
            return;
        }

        boolean success = contentDAO.deleteContent(id);

        System.out.println(success ?
                "콘텐츠 삭제 완료." :
                "콘텐츠 삭제 실패.");
    }
    
    private void printContentDetailMenu() {

        System.out.print("조회할 콘텐츠 ID: ");

        int contentId = sc.nextInt();
        sc.nextLine();

        contentDAO.printContentDetail(contentId);
    }
    
    private void managePlatformConnectionMenu() {

        System.out.print("콘텐츠 ID 입력: ");

        int contentId = sc.nextInt();
        sc.nextLine();

        platformContentDAO
                .printConnectedPlatforms(contentId);

        System.out.println("\n1. 플랫폼 연결 수정");
        System.out.println("2. 플랫폼 연결 삭제");
        System.out.println("0. 뒤로가기");
        System.out.print("선택: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1 -> {

                System.out.print("수정할 PC_ID: ");
                int pcId = sc.nextInt();

                System.out.print("새 플랫폼 평점: ");
                double rating = sc.nextDouble();

                System.out.print("제공 여부(true/false): ");
                boolean available = sc.nextBoolean();
                sc.nextLine();

                boolean success =
                        platformContentDAO
                                .updatePlatformConnection(
                                        pcId,
                                        rating,
                                        available
                                );

                System.out.println(success ?
                        "수정 완료." :
                        "수정 실패.");
            }

            case 2 -> {

                System.out.print("삭제할 PC_ID: ");
                int pcId = sc.nextInt();
                sc.nextLine();

                boolean success =
                        platformContentDAO
                                .deletePlatformConnection(pcId);

                System.out.println(success ?
                        "삭제 완료." :
                        "삭제 실패.");
            }

            case 0 -> {
                return;
            }

            default ->
                    System.out.println("잘못된 입력입니다.");
        }
    }
    
    private void printPlatformContentsMenu() {

        System.out.print("플랫폼 ID 입력: ");

        int platformId = sc.nextInt();
        sc.nextLine();

        platformDAO.printPlatformContents(platformId);
    }
    
    private void showActivityMenu() {

        while (true) {

            System.out.println("\n[사용자 활동 관리]");
            System.out.println("1. 전체 감상 기록 조회");
            System.out.println("2. 사용자별 감상 기록 조회");
            System.out.println("3. 콘텐츠별 감상 기록 조회");
            System.out.println("4. 전체 리뷰 조회");
            System.out.println("5. 사용자별 리뷰 조회");
            System.out.println("6. 콘텐츠별 리뷰 조회");
            System.out.println("7. 부적절한 리뷰 삭제");
            System.out.println("0. 뒤로가기");

            System.out.print("메뉴 선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 ->
                        activityDAO
                                .printAllWatchHistory();

                case 2 -> {

                    System.out.print(
                            "사용자 이름 검색: "
                    );

                    String keyword =
                            sc.nextLine();

                    activityDAO
                            .printWatchHistoryByUser(
                                    keyword
                            );
                }

                case 3 -> {

                    System.out.print(
                            "콘텐츠 제목 검색: "
                    );

                    String keyword =
                            sc.nextLine();

                    activityDAO
                            .printWatchHistoryByContent(
                                    keyword
                            );
                }

                case 4 ->
                        activityDAO
                                .printAllReviews();

                case 5 -> {

                    System.out.print(
                            "사용자 이름 검색: "
                    );

                    String keyword =
                            sc.nextLine();

                    activityDAO
                            .printReviewsByUser(
                                    keyword
                            );
                }

                case 6 -> {

                    System.out.print(
                            "콘텐츠 제목 검색: "
                    );

                    String keyword =
                            sc.nextLine();

                    activityDAO
                            .printReviewsByContent(
                                    keyword
                            );
                }

                case 7 -> {

                    System.out.print(
                            "삭제할 리뷰 ID: "
                    );

                    int reviewId =
                            sc.nextInt();

                    sc.nextLine();

                    boolean success =
                            activityDAO
                                    .deleteReview(
                                            reviewId
                                    );

                    System.out.println(
                            success ?
                            "리뷰 삭제 완료." :
                            "리뷰 삭제 실패."
                    );
                }

                case 0 -> {
                    return;
                }

                default ->
                        System.out.println(
                                "잘못된 입력입니다."
                        );
            }
        }
    }
    
    private void showStatisticsMenu() {

        while (true) {

            System.out.println(
                    "\n[통계 조회]"
            );

            System.out.println(
                    "1. 플랫폼별 콘텐츠 수 조회"
            );

            System.out.println(
                    "2. 콘텐츠별 평균 평점 조회"
            );

            System.out.println(
                    "3. 콘텐츠 유형별 콘텐츠 수 조회"
            );

            System.out.println(
                    "4. 인기 콘텐츠 조회"
            );

            System.out.println(
                    "5. 높은 평점 콘텐츠 조회"
            );

            System.out.println(
                    "6. 플랫폼별 평균 평점 조회"
            );

            System.out.println(
                    "7. 가장 활동적인 사용자 조회"
            );

            System.out.println(
                    "0. 뒤로가기"
            );

            System.out.print("메뉴 선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 ->
                        statisticsDAO
                                .printContentCountByPlatform();

                case 2 ->
                        statisticsDAO
                                .printAverageRatingByContent();

                case 3 ->
                        statisticsDAO
                                .printContentTypeStatistics();

                case 4 ->
                        statisticsDAO
                                .printPopularContents();

                case 5 ->
                        statisticsDAO
                                .printTopRatedContents();

                case 6 ->
                        statisticsDAO
                                .printAverageRatingByPlatform();

                case 7 ->
                        statisticsDAO
                                .printMostActiveUsers();

                case 0 -> {
                    return;
                }

                default ->
                        System.out.println(
                                "잘못된 입력입니다."
                        );
            }
        }
    }
}