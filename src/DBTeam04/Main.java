package DBTeam04;

import DBTeam04.dto.UserDTO;
import DBTeam04.menu.AdminMenu;
import DBTeam04.menu.AuthMenu;
import DBTeam04.menu.UserMenu;

import java.util.Scanner;

/**
 * OTT 플랫폼 통합 관리 시스템 - Main (통합: 지호)
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        AuthMenu authMenu = new AuthMenu(sc);
        UserMenu userMenu = new UserMenu(sc);
        AdminMenu adminMenu = new AdminMenu(sc);

        while (true) {
            // 로그인 / 회원가입
            UserDTO loginUser = authMenu.showAuthMenu();

            // 종료 선택 시
            if (loginUser == null) {
                System.out.println("프로그램을 종료합니다.");
                sc.close();
                return;
            }

            // 관리자 vs 사용자 분기
            if ("ADMIN".equals(loginUser.getRole())) {
                System.out.println("👑 관리자 계정으로 로그인되었습니다.");
                adminMenu.showAdminMenu();
            } else {
                userMenu.showUserMenu(loginUser.getUserId());
            }
        }
    }
}
