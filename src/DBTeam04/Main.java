package DBTeam04;

import DBTeam04.menu.AdminMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdminMenu adminMenu = new AdminMenu(sc);

        while (true) {
            System.out.println("\n=== OTT 플랫폼 통합 관리 시스템 ===");
            System.out.println("1. 사용자 모드");
            System.out.println("2. 관리자 모드");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> System.out.println("사용자 모드는 팀원 구현 부분입니다.");
                case 2 -> adminMenu.showAdminMenu();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }
}