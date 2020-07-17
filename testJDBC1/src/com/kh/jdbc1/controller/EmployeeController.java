package com.kh.jdbc1.controller;

import java.util.List;

import com.kh.jdbc1.model.DAO.EmployeeDAO;
import com.kh.jdbc1.model.vo.Employee;
import com.kh.jdbc1.view.EmployeeView;

// Controller
// - View를 통해 받은 클라이언트(사용자)의 요청에 대해 알맞은 Model을 선택하여
// 비즈니스 로직을 처리하고 로직 처리 결과에 따라 출력할 View를 결정하여 응답 데이터를 전달.
public class EmployeeController {

	// DB처리 결과에 따른 View연결 처리를 위한 EmployeeView 객체 생성
	// (새로운뷰) 뷰가 controller에 연결 , controller가 뷰에 연결 되면 무한 반복되기 때문에 
	private EmployeeView view = new EmployeeView();
	
	
	
	/**
	 * 1. 전체 사원 정보 조회
	 */
	public void selectAll() {
		// 1_1. View에서 입력한 클라이언트의 요청을 
		// 알맞은 DAO로 전달하고 응답 결과를 반환받기 위한
		// EmployeeDAO 객체 생성.
		EmployeeDAO dao = new EmployeeDAO();
		
		// 1_2. EmployeeDAO 클래스로 이동하여
		// DBMS 접속 및 전체 사원 정보 조회 결과를 반환할 메소드
		// EmployeeDAO.selectAll() 작성 
		
		// DB 연결 테스트
		//dao.selectAll();
		
		// 1_16. EmployeeDAO의 selectAll() 메소드를 호출하여
		// 반환되는 조회결과를 list에 저장 
		List<Employee> list = dao.selectAll();
		
		// 1_17. 조회 결과에 따른 View 연결 처리 
		// - 반환된 list가 비어 있다면 == 조회 결과가 없다
		// - 반환된 list가 비어있지 않다면 == 조회 결과가 있음
		if(!list.isEmpty()) {
			
			// 1_18. 조회 결과를 출력할 View와 연결 처리 
			// --> 출력할 메소드 작성
			// EmployeeView.selectAll(list) 작성
			
			// 1_20. 전체 사원 정보를 출력하는 view 호출 
			view.selectAll(list);
		}else {
			// 1_21. 조회 결과가 없거나, DB조회 중 문제 발생 시
			// 서비스 요청이 실패했다는 구문을 출력하기 위해
			// EmployeeView.displayError(msg)를 작성 
			
			
			// 1_22. 조회결과 없음 메세지 출력 view 호출
			view.displayError("조회 결과 없음.");
		}
		
	}

	/**
	 * 2. 사번으로 사원 정보 조회
	 */
	public void selectEmployee() {
		
		// 2_1. EmployeeDao 객체 생성
		EmployeeDAO dao = new EmployeeDAO();
		// ** DAO 객체를 지역 변수로 선언하는 이유
		// 1) DB 연결은 데이터 입출력 시에만 필요하므로 지속적인 연결을 하면 안됨.
		//   --> 메모리 낭비, DB 커넥션 수 제한 때문
		
		// 2_2. 사번을 입력받기 위한 view 메소드를 생성해야됨.
		// --> 사번을 입력 받는 기능이 공통적으로 사용됨 --> 별도의 메소드로 작성
		// --> EmployeeView 클래스에 selectEmpNo() 메소드 작성
		
		
		// 2_4. 사번 입력 View 를 이용하여 번호를 입력받아 반환받기 
		int empNo = view.selectEmpNo();
		
		// 2_5. 사번을 매개변수로 전달하여 해당 사번의 사원 정보를 조회하여 반환할 메소드 
		// EmployeeDAO.selectEmployee(empNo) 작성
		
		
		// 2_14. 사번을 전달하여 DB 조회 결과를 반환 받아 emp에 저장
		Employee emp = dao.selectEmployee(empNo);
		
		// 2_15. 조회 결과 유무에 따른 view 연결 처리
		if(emp != null) { // 조회 결과가 있을 경우
			// 2_16. 사원 한명의 정보를 출력하는
			// EmployeeView.selectEmployee(emp) 작성
			
			// 2_18. 사원 한명 정보 출력 view 호출 
			view.selectEmployee(emp);
			
		}else {
			// 2_19. 조회 결과가 없거나, DB조회 중 문제 발생 시
			// 서비스 요청이 실패했다는 구문을 출력하기 위해
			// EmployeeView.displayError(msg)를 작성 
			
			
			// 2_21. 조회결과 없음 메세지 출력 view 호출
			view.displayError("조회 결과 없음.");
		}
		
	}

	
	
	/**
	 *  3. 새로운 사원 정보 추가 
	 */
	public void insertEmployee() {
		
		// 3_1. EmployeeDAO 객체 생성
		EmployeeDAO dao = new EmployeeDAO();
		
		// 3_2. 사원 정보를 입력 받을 수 있는 view
		// EmployeeView.InsertEmployee()를 작성
		
		// 3_4. 사원 정보를 입력받아와 저장
		Employee emp = view.insertEmployee();
		
		// 3_5. 입력 받은 사원 정보를 DB에 삽입하고 결과를 반환하기 위한
		// EmployeeDAO.insertEmployee (emp)작성
		
		// 3_15. DB 삽입 결과를 저장 
		int result = dao.insertEmployee(emp);
		
		// 3_16. DB 삽입 결과에 따른 View 연결 처리
		if(result > 0) { // 삽입 성공
			
			// 3_17. DML 성공 메세지를 출력할 
			// EmployeeView.displaySuccess(msg) 작성
			
			// 3_19. DML 성공 메세지 출력 View 호출
			view.displaySucess(result + "명의 사원 정보가 추가되었습니다.");
		}else {
			// 3_20. 삽입 실패 메세지 출력 View 호출
			view.displayError("데이터 삽입 실패");
		}

		
	}

	/**
	 * 4. 사번으로 사원 정보 수정
	 */
	public void updateEmployee() {
		// 4_1. EmployeeDAO 객체 생성
		EmployeeDAO dao = new EmployeeDAO();
		
		// 4_2. 사원 번호 입력 View 호출
		int empNo = view.selectEmpNo();
		
		// 4_3. 수정할 사원 정보를 입력받을 
		// EMployeeView.updateEmployee() 작성.
		
		
		// 4_5. 수정할 사원 정보 입력 View 호출
		Employee emp = view.updateEmployee();
		
		// 4_6. 입력받은 사번을 emp에 세팅
		emp.setEmpNo(empNo);
		
		// 4_7. 입력받은 사번과 일치하는 사원의 정보를 
		// DB에서 수정할 EmployeeDAO.updateEmployee(emp)작성
		
		
		// 4_17. DB 수정 결과를 저장
		int result = dao.updateEmployee(emp);
		
		// 4_18. DB 수정 결과에 따른 View 연결 처리
		if(result > 0) {
			view.displaySucess(result + "명의 사원 정보가 수정되었습니다.");
		}else {
			view.displayError("데이터 수정 실패");
		}
		
	
	}

	/**
	 * 5. 사번으로 사원 정보 삭제
	 */
	public void deleteEmployee() {
		
		EmployeeDAO dao = new EmployeeDAO();
		
		// 사번 입력
		int empNo = view.selectEmpNo();
		
		char check = view.deleteEmployee();
		
		// chect가 y인 경우
		if('y' == Character.toLowerCase(check)) {
			//  check를 소문자로 변경하는 Character 래퍼클래스 메소드
			
			int result = 0;
			
			result = dao.deleteEmployee(empNo);
			
			if(result > 0 ) {
				view.displaySucess(result + "명의 사원 정보가 삭제되었습니다.");
			}else {
				view.displayError("데이터 삭제 실패");
			}
		
		}else {
			
		}
		
		
	}
}
