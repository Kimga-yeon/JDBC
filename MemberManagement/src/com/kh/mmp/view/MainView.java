package com.kh.mmp.view;

import java.util.Scanner;

import com.kh.mmp.controller.MemberController;

public class MainView {
	
	public void mainMenu() {
		Scanner sc = new Scanner(System.in);
		MemberController controller = new MemberController();
		int sel = 0;
		do {
			 System.out.println("\n *** 회원 관리 프로그램 *** \n");
	         System.out.println("1. 새 회원 등록");
	         System.out.println("2. 모든 회원 조회");
	         System.out.println("3. 특정 조건 회원 조회");
	         System.out.println("4. 회원 정보 수정");
	         System.out.println("5. 회원탈퇴");
	         System.out.println("0. 프로그램 종료");
	         System.out.print("번호 선택 : ");
	         
	         try {
				sel = sc.nextInt();
				sc.nextLine();
				switch(sel) {
				case 1: controller.insertMember(); break;
				case 2: controller.selectAll(); break;
				case 3: controller.selectMember(); break;
				case 4: controller.updateMember(); break;
				case 5: controller.deleteMember(); break;
				case 0: System.out.println("프로그램을 종료합니다.");break;
				default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				}
			} catch (Exception e) {
				System.out.println("정수만 입력해주세요.");
				sel  = -1;
				sc.nextLine();
			}
		}while(sel != 0);
	}
}
