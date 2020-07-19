package com.kh.board.view;

import java.util.List;
import java.util.Scanner;

import com.kh.board.controller.BoardController;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Member;
import com.kh.board.model.vo.Reply;

public class SubView {

   private Scanner sc = new Scanner(System.in);
   
   /** 로그인 정보 입력용 View
    * @return inputLogin
    */
   public Member inputLogin() {
      System.out.println("\n---로그인---\n");
      System.out.print("ID : ");
      String memberId = sc.nextLine();

      System.out.print("PW : ");
      String memberPwd = sc.nextLine();
      
      return new Member(memberId, memberPwd);
   }

   /** 서비스 요청 성공 내용 출력용 View
    * @param string
    */
   public void displaySuccess(String msg) {
      System.out.println("서비스 요청 성공 : " + msg);
   }

   public void displayFail(String msg) {   
      System.out.println("서비스 요청 실패 : " + msg);
   }

   public void displayError(String msg) {
      System.out.println("서비스 요청 에러 : "+msg);
   }

   public Board insertBoard() {
      System.out.println("\n---게시글 작성---\n");
      System.out.println("글 제목 : ");
      String title = sc.nextLine();
      
      System.out.println("---- 글 내용 입력(종료 시 exit)입력) ---- ");
      StringBuffer content = new StringBuffer();
      
      String str = null;
      while(true) {
         str = sc.nextLine();
         
         if(str.equals("exit")) break;
         
         content.append(str + "\n");
         
      }
      
      // 입력받은 title, content, static에 있는 로그인 정보 중 ID를 Board 객체에 담아서 반환
      return new Board(title, content.toString(), BoardController.LoginMember.getMemberId());
   }

   public void selectAll(List<Board> list) {
	      System.out.printf("%-3s %-15s %-10s %-15s\n",
	                     "BNO", "TITLE", "WRITER", "CREATE_DATE");
	      System.out.println("------------------------------------------");
	      for(Board b : list) {
	         System.out.printf("%-3d %-15s %-10s %-15s\n",
	                  b.getBoardNo(), b.getTitle(), b.getWriter(), b.getCreateDate());
	      }
	   }

/** 글 번호 입력 View
 * @return no
 */
public int inputBoardNo() {
	System.out.print("글 번호 입력 : ");
	int no = sc.nextInt();
	sc.nextLine();
	
	
	
	return no;
}



/** 게시글 상세 조회 View
 * @param board
 */
public void selectOne(Board board) {
    System.out.println();
    System.out.println("-------------------------------------------------");
    System.out.println("글번호 : " + board.getBoardNo());
    System.out.println("제목 : " + board.getTitle());
    System.out.printf("작성자 : %-10s 작성일 %-15s\n",board.getWriter(), board.getCreateDate());
    System.out.println("-------------------------------------------------");
    System.out.println(board.getContent());
    System.out.println("-------------------------------------------------");
    
 }


/** 게시글 수정 메뉴 View
 * @return sel
 */
public int updateMenu() {
	int sel = 0;
	while(true) {
		System.out.println("1. 제목 수정");
		System.out.println("2. 내용 수정");
		System.out.println("0. 메인으로 이동");
		System.out.print("메뉴 선택 :");
		sel = sc.nextInt();
		sc.nextLine();
		
		switch(sel) {
		case 1: case 2: case 0: return sel;
		default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
		}
	}
	
}





/** 수정 제목을 입력받는 View
 * @return upStr
 */
public String updateTitle() {
	System.out.print("수정할 제목 입력 : ");
	return sc.nextLine();
}

/** 수정할 글 내용 입력 View
 * @return upStr
 */
public String updateContent() {
	 System.out.println("---- 글 내용 입력(종료 시 exit)입력) ---- ");
     StringBuffer content = new StringBuffer();
     
     String str = null;
     while(true) {
        str = sc.nextLine();
        
        if(str.equals("exit")) break;
        
        content.append(str + "\n");
        
     }
	return content.toString();
	
}


   
/** 삭제 여부 입력용 View
 * @return del
 */
public char deleteBoard() {
	System.out.print("정말로 삭제 하시겠습니까?(Y/N) : ");
	return sc.nextLine().toUpperCase().charAt(0);
}


/** 댓글 메뉴 출력용 View
 * @return sel
 */
public int replyMenu() {
    int sel = 0;
    while(true) {
       System.out.println("1. 댓글 작성");
       System.out.println("2. 댓글 수정");
       System.out.println("3. 댓글 삭제");
       System.out.println("0. 메인 메뉴로 이동");
       System.out.print("메뉴 선택 : ");
       sel = sc.nextInt();
       sc.nextLine();
       
       switch(sel) {
       case 1: case 2: case 3: case 0: return sel;
       default : System.out.println("잘못 입력하셨습니다 .다시 입력해주세요.");
       }
    }
	
}
   
/** 댓글 내용 입력용 View
 * @return replyContent
 */
public String insertReply() {
    StringBuffer content = new StringBuffer();
    String str = null;
    System.out.println("---------- 댓글 입력(종료 시 exit 입력) ----------");
    while(true) {
       str = sc.nextLine();
       
       if(str.equals("exit")) break;
       
       content.append(str + "\n");
    }
    return content.toString();
 }

	/** 댓글 출력용 View
	 * @param rList
	 */
	public void selectReply(List<Reply> rList) {
	    
	    // 댓글 번호   내용     작성자    작성일
	    System.out.printf("%-4s %-10s %-15s\n", 
	                   "댓글번호", "작성자", "작성일");
	    System.out.println("-------------------------------------------------");
	    for(Reply r : rList) {
	       System.out.printf("%-4d %-10s %-15s\n %-50s\n", 
	             r.getReplyNo(), r.getReplyWriter(), 
	             r.getCreateDate(), r.getReplyContent());
	    }
	    
 
	}
	
	public Member insertJoin() {
		System.out.print("회원 아이디 : ");
		String memberId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String memberPwd = sc.nextLine();
		
		System.out.print("이름 : ");
		String memberName = sc.nextLine();
		
		 System.out.print("성별(남:M/여:F) : ");
	      char gender = sc.nextLine().toUpperCase().charAt(0);
	      // 대문자로 변경하여 저장
	      
	      System.out.print("이메일 : ");
	      String email = sc.nextLine();
	      
	      System.out.print("전화번호(-포함) : ");
	      String phone = sc.nextLine();
	      
	      System.out.print("나이 : ");
	      int age = sc.nextInt();
	      sc.nextLine(); // 개행문자 제거
	      
	      System.out.print("주소 : ");
	      String address = sc.nextLine();
	      
	      return new Member(memberId, memberPwd, memberName, gender, email, phone, age, address);
	}
	      
//	  	//회원 정보 출력용 View
//	  	   public void displayMember(List<Member> list) {
//	  		      System.out.printf("%-10s %-10s %-5s %-5s %-20s %-15s %-4s %-20s \n",
//	  		            "ID", "PWD", "NAME", "GENDER", "EMAIL", 
//	  		            "PHONE", "AGE", "ADDRESS");
//	  		      
//	  		      for(Member m : list) {
//	  		         System.out.printf("%-10s %-10s %-8s %-5c %-20s %-15s %-4d %-20s \n",
//	  		            m.getMemberId(), m.getMemberPwd(), m.getMemberName(),
//	  		            m.getGender(), m.getEmail(), m.getPhone(),
//	  		            m.getAge(), m.getAddress());
//	  		      }
		
//	  	   }
	   
}
