package com.kh.mmp.view;

import java.util.List;
import java.util.Scanner;

import com.kh.mmp.model.vo.Member;

public class SubView {
	private Scanner sc = new Scanner(System.in);

	/**
	 * 1-2. 회원 정보 입력용 뷰
	 * @return member(입력된 회원 정보가 담겨있음)
	 */
	public Member insertMember() {
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

		//1-3. 입력된 회원 정보를 Member 객체에 담아 반환
		//enrollDate를 제외한 생성자 필요
		return new Member(memberId, memberPwd, memberName, gender, email, phone, age, address);
	}

	//1-27. 요청성공 실패 에러 출력용 View작성
	public void displaySuccess(String msg) {
		System.out.println("서비스 요청 성공: "+msg);
	}
	public void displayFail(String msg) {
		System.out.println("서비스 요청 실패: "+msg);
	}
	public void displayError(String msg) {
		System.out.println("에러 발생: "+msg);
	}
	//회원 정보 출력용 View
	public void displayMember(List<Member> list) {
		System.out.printf("%-10s %-10s %-5s %-5s %-20s %-15s %-4s %-20s %-15s\n",
				"ID", "PWD", "NAME", "GENDER", "EMAIL", 
				"PHONE", "AGE", "ADDRESS", "EROLLDATE");

		for(Member m : list) {
			System.out.printf("%-10s %-10s %-8s %-5c %-20s %-15s %-4d %-20s %-15s\n",
					m.getMemberId(), m.getMemberPwd(), m.getMemberName(),
					m.getGender(), m.getEmail(), m.getPhone(),
					m.getAge(), m.getAddress(), m.getEnrollDate());
		}
	}

	/**  3-2. 검색 조건 입력용 View
	 * @return sel
	 */
	public int selectMember() {
		int sel = 0;
		while(true) {
			try {
				System.out.println("-- 검색 조건--");
				System.out.println("1.특정 단어 포함 아이디 조회");
				System.out.println("2.성별 조회");
				System.out.println("3.주소 조회");
				System.out.println("4. 나이대(10대,20대,30대...) 조회");
				System.out.println("0.메인 메뉴로 돌아가기");
				System.out.print("검색 조건 선택: ");
				sel = sc.nextInt();
				sc.nextLine();

				switch(sel) {
				case 1: case 2: case 3: case 4: case 0: return sel;
				default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요."); 
				}
			} catch (Exception e) {
				System.out.println("정수만 입력해 주세요.");
				sc.nextLine();
			}
		}
	}
	/**
	  3-6. 검색 키워드 입력용 View
	 * @return keyword
	 */
	public String inputKeyword() {
		System.out.print("검색할 키워드 입력: ");
		return sc.nextLine();
	}

	/**
	 * 3-27. 성별 입력용 View
	 * @return gender
	 */
	public char inputGender() {
		char gender = ' ';
		while(true) {
			System.out.print("성별 입력(M/F): ");
			gender = sc.nextLine().toUpperCase().charAt(0);

			if(gender == 'M' || gender == 'F') {
				break;
			}else {
				System.out.println("M 또는 F만 입력해주세요.");
			}
		}
		return gender;
	}
	public int inputAge() {
		System.out.print("나이대(10,20,30...) 입력: ");
		int age = sc.nextInt();
		return age;
	}

	public String inputId() {
		System.out.print("회원 아이디 입력: ");
		return sc.nextLine();
	}
	public char checkDelete() {
		System.out.print("정말로 삭제 하시겠습니까?(Y/N) : ");
		char yn = sc.nextLine().toUpperCase().charAt(0);
		return yn;
	}

	public void displaySuccess1(String msg) {
		System.out.println("서비스 요청 성공: "+msg);
	}
	public void displayError1(String msg) {
		System.out.println("서비스 요청 실패 - "+msg);
	}



	/** 회원 정보 수정용 메뉴 View
	 * @return sel
	 */
	public int updateMember() {

		System.out.println("\n*** ID가 확인되었습니다. ***\n");

		int sel = 0;
		while(true) {
			System.out.println("1. 비밀번호 변경");
			System.out.println("2. 이메일 변경");
			System.out.println("3. 전화번호 변경");
			System.out.println("4. 주소 변경");
			System.out.println("0. 메인 메뉴로 돌아가기");
			System.out.print("번호 선택 : ");
			sel = sc.nextInt();
			sc.nextLine();

			switch(sel) {
			case 1: case 2: case 3: case 4: 
			case 0: return sel;
			default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}

	public String inputUpdate() {
		System.out.println("수정할 값 입력 : ");
		return sc.nextLine();
	}








}
