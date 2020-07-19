package com.kh.board.controller;

import java.sql.SQLException;
import java.util.List;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.service.ReplyService;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Member;
import com.kh.board.model.vo.Reply;
import com.kh.board.view.SubView;

public class BoardController {
   public static Member LoginMember = null;
   // 로그인 된 회원의 정보를 저장할 static 변수 -> 각 클래스, 객체에서 필요시마다 호출할 수 있음

   private SubView sub = new SubView();
   private BoardService service = new BoardService();
   private ReplyService rService = new ReplyService();
   
   
   /**
    * 회원 로그인
    */
   public void login() {
      // 서브메뉴에서 id, pwd 입력 받아옴
      Member inputLogin = sub.inputLogin();
      
      try {
         BoardController.LoginMember = service.login(inputLogin); 
         // 입력받은 로그인 정보를 조회해 로그인 정보를 저장하는 static 변수 BoardController.LoginMember에 저장
         
         // 조회 결과에 따라 view 연결 처리
         if(BoardController.LoginMember != null ) { // 로그인이 된 경우
            sub.displaySuccess("로그인 성공");
         }else {
            sub.displayFail("아이디  또는 비밀번호를 확인하세요.");
         }
         
      } catch (Exception e) {
         e.printStackTrace();
         sub.displayError("로그인 과정에서 오류 발생");
      }
   }

   /**
    * 게시글 DB 삽입
    */
   public void insertBoard() {
      
      // 제목, 글을 입력받을 View 작성 후 호출   
      Board board = sub.insertBoard();
      
      try {
         int result = service.insertBoard(board);
         
         if(result>0) {
            sub.displaySuccess("게시글 등록 성공");
         }else {
            sub.displayFail("게시글 등록 실패");
         }
      } catch (Exception e) {
         e.printStackTrace();
         sub.displayError("게시글 삽입 과정에서 오류 발생");
      }
   }

	/**
	 * 전체 게시글 조회
	 */
	public void selectAll() {
		
		try {
			List<Board> list = service.selectAll();
			
			if(!list.isEmpty()) { //조회 결과가 있다면
				sub.selectAll(list);
				
			}else {
				sub.displayFail("조회 결과가 없습니다.");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			sub.displayError("게시글 목록 조회 과정에서 오류 발생.");
		}
		
	}

	/**
	 * 게시글 상세 조회
	 */
	public void selectOne() {
		
		// 글 번호(PK) 입력받을 View 호출 
		int no = sub.inputBoardNo();
		
		try {
			Board board = service.selectOne(no);
		
				
				// 댓글 기능 추가
				// -댓글은 게시글에 붙어있는 또다른 기능
				// --> 별도의 controller, service, dao 따로 존재해야함.
				// --> 자바 프로그램의 한계로 인해 controller 분리가 힘듦.
			
					
			if(board != null) { //조회된 글이 있을 경우
				sub.selectOne(board); // 게시글 출력 
				
				// ---------- 댓글 기능 추가  --------------
				// 1) 해당 게시글에 작성된 댓글 조회
				List<Reply> rList = rService.selectReply(no);
				
				if(!rList.isEmpty()) {
					sub.selectReply(rList);
				}else {
					sub.displayFail("작성된 댓글이 없습니다.");
				}
				
				
				// 2) 댓글 조작 메뉴
				int sel = sub.replyMenu();
				int result = 0; // 삽입, 수정, 삭제 결과 저장용 변수 
								// DML이기때문에 다 int로 옴
				String msg = null; // 메세지 출력용 변수
				
				try {
					switch(sel) {
					case 1: // - 삽입
						
						// DB에 삽입 할 댓글 내용 입력
						String replyContent = sub.insertReply();
						result = rService.insertReply(new Reply(replyContent, no,BoardController.LoginMember.getMemberId()));
						msg = "삽입";
						break;
						
					case 2: // - 수정
						break;
						
					case 3: // - 삭제
						break;
					}
					if(result > 0) {
		                  sub.displaySuccess("댓글" + msg + "성공");
		               }else {
		                  sub.displayFail("댓글" + msg + "실패");
		               }
				
				
				}catch (Exception e) {
					e.printStackTrace();
					sub.displayError("댓글" + msg + "과정에서 오류 발생");
				}
				
			}else {
				sub.displayFail("해당 번호의 글이 존재하지 않습니다.");
			}
		}catch (Exception e) {
			e.printStackTrace();
			sub.displayError("상세 조회 과정에서 오류 발생");
		}
	}

	
	/** 
	 * 게시글 수정
	 */
	public void updateBoard() {
		// 수정 글 번호 입력 받아오기
		int no = sub.inputBoardNo();
		
		try {
		// 수정하려는 글이 존재하는 지 확인
		Board board = service.selectOne(no);
		//					    게시글 하나 검색
		
		if(board != null) { // 해당 게시글 존재 하면
			
			// 수정하려는 글의 작성자와, 로그인한 회원 ID가 같은지 확인
			if(board.getWriter().equals(BoardController.LoginMember.getMemberId())) {
				int sel = sub.updateMenu();
				
				String upStr = null; // 수정할 글제목 또는 내용을 저장할 변수 
				switch(sel) {
				case 1: 
					upStr = sub.updateTitle();
					break;
				case 2: 
					upStr = sub.updateContent();
					break;
				case 0: return;
					
				}
				int result = service.updateBoard(no ,sel ,upStr);
				
				if(result > 0) {
					sub.displaySuccess("글이 수정되었습니다.");
				}else {
					sub.displayFail("글 수정 실패");
				}
			}else {
				sub.displayFail("해당 글을 수정할 수 없습니다.");
			}
			
		}else {
			sub.displayFail("해당글이 존재하지 않습니다.");
		}
		
		
		}catch (Exception e) {
			e.printStackTrace();
			sub.displayError("게시글 수정 과정에서 오류 발생");
		}
		
	}
	
	
	
	
	
	
	public void deleteBoard() {
		// 1) 삭제 하려는 글 번호 입력받기 
		int no = sub.inputBoardNo();

		try {
			// 2) 삭제하려는 글이 존재하는지 확인
			int check = service.checkBoard(no);

			if(check > 0) { // 삭제할 글이 존재

				// 3) 삭제하려는 글의 작성자가 현재 로그인한 회원인지 확인
				String writer = service.selectWriter(no);

				if(writer.equals(BoardController.LoginMember.getMemberId())) {
					// writer 와 로그인한  회원의 id가 같다면

					// 4) 정말 삭제? Y/N 입력
					char del = sub.deleteBoard();

					if(del == 'N') {
						return; //삭제 취소
					}else if(del == 'Y'){ // 삭제 진행
						try {
							int result = service.deleteBoard(no);
							if(result > 0) {
								sub.displaySuccess("해당 글이 삭제되었습니다.");
							}
						}catch (SQLException e) {
							e.printStackTrace();
						}
					}else {
						sub.displayFail("Y 또는 N 만 입력해 주세요.");
					}
				}else {
					sub.displayFail("해당 글을 삭제할 수 없습니다.");
				}


			} else {
				sub.displayFail("해당하는 글이 존재하지 않습니다.");
			}

		}catch (Exception e) {
			e.printStackTrace();
			sub.displayError("게시글 삭제 과정에서 오류 발생.");
		}

	}

	/**
	 * 회원가입용
	 */
	public void insertJoin() {
		Member mem = sub.insertJoin();
		
		 try {
				int result = service.insertJoin(mem);
				if(result>0) {
					sub.displaySuccess(result+"명의 회원이 추가되었습니다.");
				}else {
					sub.displayFail("회원가입에 실패하였습니다.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				sub.displayError("화원가입 과정에서 오류 발생");
			}
		
	}
}
