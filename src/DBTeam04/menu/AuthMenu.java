package DBTeam04.menu;

import DBTeam04.dao.UserDAO;
import DBTeam04.dto.UserDTO;

import java.util.Scanner;

/**
 * 로그인 / 회원가입 메뉴 (작성: 지호)
 * - 입력값 검증 포함
 * - 로그인 성공 시 UserDTO 반환
 */
public class AuthMenu {

    private final Scanner sc;
    private final UserDAO userDAO = new UserDAO();

    public AuthMenu(Scanner sc) {
        this.sc = sc;
    }

    // 로그인/회원가입 선택 화면
    // 반환값: 로그인한 UserDTO (로그인 성공 시), null (종료 시)
    public UserDTO showAuthMenu() {
        while (true) {
            System.out.println("\n=== OTT 플랫폼 통합 관리 시스템 ===");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    UserDTO user = login();
                    if (user != null) return user;
                }
                case 2 -> register();
                case 0 -> { return null; }
                default -> System.out.println("❌ 잘못된 입력입니다.");
            }
        }
    }

    // ─────────────────────────────────────────────
    // 로그인
    // ─────────────────────────────────────────────
    private UserDTO login() {
        System.out.println("\n--- [ 로그인 ] ---");

        System.out.print("이메일 또는 사용자 ID: ");
        String email = sc.nextLine().trim();

        System.out.print("비밀번호: ");
        String password = sc.nextLine().trim();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ 이메일과 비밀번호를 모두 입력해주세요.");
            return null;
        }

        UserDTO user = userDAO.login(email, password);

        if (user == null) {
            System.out.println("❌ 이메일 또는 비밀번호가 올바르지 않거나 비활성화된 계정입니다.");
            return null;
        }

        System.out.printf("✅ 환영합니다, %s님! (%s) [회원번호: %d]\n", user.getUsername(), user.getMembership(), user.getUserId());
        return user;
    }

    // ─────────────────────────────────────────────
    // 회원가입
    // ─────────────────────────────────────────────
    private void register() {
        System.out.println("\n--- [ 회원가입 ] ---");

        System.out.print("이름: ");
        String username = sc.nextLine().trim();

        System.out.print("이메일 또는 사용자 ID: ");
        String email = sc.nextLine().trim();

        if (!email.contains("@")) {
            System.out.println("❌ 올바른 이메일 형식이 아닙니다.");
            return;
        }

        System.out.print("비밀번호: ");
        String password = sc.nextLine().trim();

        System.out.print("비밀번호 확인: ");
        String passwordCheck = sc.nextLine().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ 모든 항목을 입력해주세요.");
            return;
        }

        if (!password.equals(passwordCheck)) {
            System.out.println("❌ 비밀번호가 일치하지 않습니다.");
            return;
        }

        if (userDAO.isEmailDuplicate(email)) {
            System.out.println("❌ 이미 사용 중인 이메일입니다.");
            return;
        }

        boolean result = userDAO.register(username, email, password);
        if (result)
            System.out.println("✅ 회원가입이 완료되었습니다! 로그인해주세요.");
        else
            System.out.println("❌ 회원가입에 실패했습니다. 다시 시도해주세요.");
    }

    // ─────────────────────────────────────────────
    // 입력값 검증 - 숫자 입력
    // ─────────────────────────────────────────────
    public int readInt() {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("❌ 숫자를 입력해주세요: ");
            }
        }
    }
}
