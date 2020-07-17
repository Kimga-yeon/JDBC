package com.kh.mmp.controller;

import java.sql.SQLException;
import java.util.List;

import com.kh.mmp.model.dao.MemberDAO;
import com.kh.mmp.model.service.MemberService;
import com.kh.mmp.model.vo.Member;
import com.kh.mmp.view.SubView;

public class MemberController {

	private SubView sub = new SubView();  //요청에 대한 입력/출력을 담당하는 서브메뉴 객체
	private MemberService service = new MemberService();
	/**
	 *  1. 새로운 회원 추가
	 */
	public void insertMember() {
		//1-1. 새로운 회원 정보를 입력받기 위한 서브메뉴
		//SubView.insertMember() 작성
		
		//1-4. 새로운 회원 정보를 입력받아 반환된 값을 변수에 저장
		 Member mem = sub.insertMember();
		 
		 //1-5. 입력받은 회원 정보를 DB에 전달하고 비지니스 로직처리를 하는
		//MemberService.insertMember(mem)작성
		 
		//1-24.MemberService.insertMember()를 호출하여 결과를 반환 받아옴
		 try {
			int result = service.insertMember(mem);
			//1-25. DB삽입 결과에 따른 VIEW 연결 처리
			if(result>0) {
				//1-26. 서비스 요청 성공 내용을 출력할 View메소드인
				//displaySuccess(msg)와 실패 내용출력용 view
				//displayFail(msg)
				//에러내용 출력용 View  displayError(msg)3개 메소드 작성
				
				//1-28. DB처리 결과에 따라 알맞은 MSG를 View로 전달
				sub.displaySuccess(result+"명의 회원이 추가되었습니다.");
			}else {
				sub.displayFail("회원추가에 실패하였습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sub.displayError("회원 추가 과정에서 오류 발생");
		}
	}
	
	/**
	 *  2. 모든 회원 정보 조회
	 */
	public void selectAll() {
		// 2-1. MemberService.selectAll() 작성
		
		//2-16.MemberService.selectAll() 호출하여 반환값 저장
		try {
			List<Member> list = service.selectAll();
			
			//2-17. 조회 결과가 있을 경우 출력할 view
			//displayMember(list)작성
			if(!list.isEmpty()) {
				sub.displayMember(list);
			}else {
				//2-18. 조회 결과가 없을 경우 displayFail(msg) 호출
				sub.displayFail("조회 결과가 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//2-19. 조회 과정에서 에러 발생시 displayError(msg)호출
			sub.displayError("조회 과정에서 오류 발생");
		}
		
	}

	/**
	 *  3.특정 조건 회원 조회
	 */
	public void selectMember() {
		//3-1. 검색 조건을 입력받기 위한 View
		//SubView.selectMember() 작성
		
		//3-3. SubView.slectMember() 호출해서 반환값 저장
		int sel = sub.selectMember();
		
		//3-4.  검색 조건에 따라서 알맞은 Service를 호출하도록 switch문 작성
		
		//3-22. 조회 결과를 참조할 List 변수 선언
		List<Member> list = null;
		try {
		switch(sel) {
		case 1: 
			//3-5. 검색할 키워드를 입력받을 SubView.inputKeyword() 작성
			String keyword = sub.inputKeyword();
			
			//3-7.  조건에 맞는 검색을 수행할 MemberService.selectMemberId(keyword) 작성 및 호출
			
			//3-23.
			list  = service.selectMemberId(keyword); 
			break;
		case 2: 
			//3-26. 성별 입력용 SubView.inputGender() 작성
			
			//3-28. 성별을 입력받아와 저장
			char gender = sub.inputGender();
			
			//3-29. 입력된 성별을 가진 회원을 조회하기 위한 MemberService.selectGender(gender) 작성
			
			//3-45. DB에서 성별 조회 결과를 반환 받아와 list에 저장
			list = service.selectGender(gender);
			break;
			
		case 3:
			list = service.selectAddress(sub.inputKeyword());
			break;
		case 4:
			list = service.selectAge(sub.inputAge());
			break;
		case 0: return;  //0번 입력시 호출부(메인 메뉴)로 돌아감.
		} 
		
		}catch (Exception e) {
			//3-24. 조회 과정에서 발생하는 예외를 출력할 View 연결 처리
		e.printStackTrace();
		sub.displayError("조회 과정에서 오류 발생.");
		return;  // 오류가 발생한 경우 이후 코드 처리가 필요 없으므로 리턴
		}
		//3-25. 조회 결과에 따라 View연결 처리
		if(!list.isEmpty()) {
			sub.displayMember(list);
		}else {
			sub.displayFail("조회 결과가 없습니다.");
		}
	}

	/**
	 *  5. 회원 탈퇴
	 */
	public void deleteMember() {
		// 1) 회원 아이디 입력 받아오기
			String id = sub.inputId();
		// 2) 정말 삭제할 것인지 확인하는 View 호출
			char yn = sub.checkDelete();
		// 3) service 호출하여 반환값에 따라View 연결 처리
			if(yn=='Y') { 
				try {
					int result = service.deleteMember(id);
					sub.displaySuccess(result+"명이 삭제되었습니다.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				}else {
				sub.displayError1("데이터 삭제 실패");
				}

}

	/**
	 * 4. 회원 정보 수정
	 */
	public void updateMember() {
		
		// 4_1. 수정 할 회원의 ID를 입력 받아옴.
		String memberId = sub.inputId();
		
		// 4_2. 입력받은 ID와 일치하는 회원이 존재하는지 확인 작업
		// MemberService.checkMember(memberId) 작성
		
		
		try {
			// 4_17. 회원 존재 여부에 따라 처리방법 제어 
			if(service.checkMember(memberId) < 1 ) {
				// --> 회원이 존재하지 않으면 
				
				sub.displayFail("해당 ID를 가진 회원이 없습니다.");
				return;
			}else { // 해당 회원이 존재하는 경우
				// 4_18. 수정할 내용을 선택할 메뉴 
				// SubView.updateMember() 작성
				
				// 4_20. SubView.updateMember() 호출 후 반환 값 저장
				int sel = sub.updateMember();
				
				
				// 4_21. 입력받은 서브메뉴 번호가 0(돌아가기)인 경우
				if(sel ==0) return;
				// if문, for문에서 {}중괄호가 생략된 경우 
				// 해당 구문 바로 다음 한 줄(세미콜론이 찍힐 때 까지) 에 대해서 동작을 함
				
				
				// 수정할 값 입력을 받은 SubView.InputUpdate() 작성 및 호출
				String input = sub.inputUpdate();
				
				
				// 4_22. 별도의 switch문을 작성하여 service를 호출하지 않고
				// 입력된 id와 선택된 메뉴 번호 sel을 매개변수로 한 service 메소드 호출
				// --> MemberService.updateMember(memberId, sel, input) 작성
				
				// 4_38. MemberService.updateMember(memberId, sel, input)
				// 호출 후 반환 값 저장
				int result = service.updateMember(memberId, sel, input);
				
				// 4.39. 수정 결과에 따른 view 연결처리
				if(result > 0 ) {
					sub.displaySuccess(result + "명의 정보가 수정되었습니다.");
				}else {
					sub.displayFail("회원 정보 수정 실패");
				}
				
			}
				
		}catch (Exception e) {
			e.printStackTrace();
			sub.displayError("데이터 수정 과정에서 오류 발생");
		}
		
		
		
		
		
	}
}
