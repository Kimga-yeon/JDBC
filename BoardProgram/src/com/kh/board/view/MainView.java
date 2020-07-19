package com.kh.board.view;

import java.util.Scanner;

import com.kh.board.controller.BoardController;

/**
 * @author hyeonju
 * Email : gnswi333@naver.com
 */
public class MainView {
   
   public void mainMenu() {
      Scanner sc = new Scanner(System.in);
      BoardController controller = new BoardController();
      
      int sel = 0;
      
      do {
         // 프로그램 시작 시 로그인 필요
         
         if(BoardController.LoginMember == null) { // 로그인이 되어있지 않은 경우
            System.out.println("\n---게시판 프로그램---\n");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 프로그램 종료");
            System.out.print("메뉴 선택 : ");
            sel = sc.nextInt();
            sc.nextLine();
            
            switch(sel) {
            case 1 : controller.login(); break;
            case 2 : controller.insertJoin(); break;
            case 0 : System.out.println("프로그램 종료."); break;
            default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
            
         }else {
            System.out.println("\n===메인 메뉴 ===\n");
               System.out.println("1. 로그아웃");
               System.out.println("2. 글 목록 조회");
               System.out.println("3. 게시글 조회(글번호)");
               System.out.println("4. 글 쓰기");
               System.out.println("5. 글 수정");
               System.out.println("6. 글 삭제");
               System.out.println("0. 프로그램 종료");
               System.out.print("번호 선택 : ");
            
               sel = sc.nextInt();
               
               switch(sel) {
               case 1 : BoardController.LoginMember = null; 
                      System.out.println("[로그아웃 완료]");
                      break;
               case 2 : controller.selectAll(); break;
               case 3 : controller.selectOne(); break;
               case 4 : controller.insertBoard(); break;
               case 5 : controller.updateBoard(); break;
               case 6 : controller.deleteBoard(); break;
               case 0 : System.out.println("프로그램 종료."); break;
               default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
               }
         }
         
      }while(sel !=0);
   }

   
}