package com.kh.jdbc1.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.kh.jdbc1.controller.EmployeeController;
import com.kh.jdbc1.model.vo.Employee;

//사용자 인터페이스(UI) 요소로 사용자의 요청과 응답을 보여주는 화면  
public class EmployeeView {
	private Scanner sc = new Scanner(System.in);
	
	
	public void mainMenu() {
		EmployeeController controller = new EmployeeController();
		
		int sel = 0;
		
		do {
	         System.out.println("====================================");
	         System.out.println("[Main Menu]");
	         System.out.println("1. 전체 사원 정보 조회");
	         System.out.println("2. 사번으로 사원 정보 조회");
	         System.out.println("3. 새로운 사원 정보 추가");
	         /* mainMenu() -> 3번 선택 
	          * == case3 : controller().insertEmployee() 호출
	          * -> controller().insertEmployee() 메소드에서
	          * 	view.insertEmployee(Employee emp) 를 호출하여 정보를 입력 받아옴.
	          * 
	          * -> controller().insertEmployee() 메소드에서
	          * 	dao객체를 생성하여 입력받아온 emp를 전달
	          * 	== dao.insertEmployee(Employee emp)호출
	          * 
	          * -> dao에서 DB 삽입 결과를 controller().insertEmployee()로 반환
	          * 
	          * -> controller().insertEmployee() 메소드에서
	          *    dao 반환 결과에 따라 보여줄 View 연결 처리
	          *    
	          *    1) 삽입 성공 시
	          *      view.displaySuccess(String msg) 호출
	          *      
	          *    2) 삽입 실패 시
	          *    	 view.displayError(String msg) 호출
	          * 
	          * 
	          * 
	          * 
	          * */
	         System.out.println("4. 사번으로 사원 정보 수정");
	         /* mainMenu() -> 4번 선택 
	          * == case4 : controller().updateEmployee() 호출
	          * -> controller().updateEmployee() 메소드에서
	          * 
	          *  	1) view.selectEmpNo() 메소드를 호출하여 사번을 입력받아 반환받음.
	          *  	
	          * 	2) view.updateEmployee(Employee emp) 를 호출하여 수정할 정보를 입력 받아옴.
	          * 	+ Employee VO 클래스에 job, sal,comm 을 매개변수로 갖는 생성자 작성
	          * 
	          * 	3) 2)번 결과로 만들어진 Employee객체에 
	          * 	   1)번 결과(사번)을 세팅 --> emp.setEmpNo(empNo);
	          * 
	          * -> controller().updateEmployee() 메소드에서
	          * 	dao객체를 생성하여 입력받아온 emp를 전달
	          * 	== dao.updateEmployee(Employee emp)호출
	          * 
	          * -> dao에서 데이터 수정 결과를 controller().updateEmployee()로 반환
	          *  + UPDATE SQL 작성
	          *  + 수정 결과에 따라 트랜젝션 처리(commit, rollback)
	          * 
	          * -> controller().updateEmployee() 메소드에서
	          *    dao 반환 결과에 따라 보여줄 View 연결 처리
	          *    
	          *    1) 수정 성공 시
	          *      view.displaySuccess(String msg) 호출
	          *      
	          *    2) 수정 실패 시
	          *    	 view.displayError(String msg) 호출
	          * 
	          * 
	          * 
	          * 
	          * */
	         System.out.println("5. 사번으로 사원 정보 삭제");
	         System.out.println("0. 프로그램 종료");
	         System.out.println("====================================");
	         
	         System.out.print("메뉴 선택 : ");
		
	         try {
	        	 sel = sc.nextInt();
	        	 
	        	 
	        	 // 입력된 번호의 기능을 수행하기 위한 view와 model을 제어하는 controller를 호출
	        	 switch (sel) {
	        	 
	        	 case 1: controller.selectAll(); break;
	        	 case 2: controller.selectEmployee(); break;
	        	 case 3: controller.insertEmployee(); break;
	        	 case 4: controller.updateEmployee(); break;
	        	 case 5: controller.deleteEmployee(); break;
	        	 case 0: System.out.println("프로그램 종료.");break;
	        	 default:
	        		 System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
	        		 break;
	        	 }
	        	 System.out.println();
	        	 
	         }catch(InputMismatchException e){
	        	 System.out.println("정수만 입력해주세요.");
	        	 sel = -1;
	        	 sc.nextLine();
	         }
			
		}while(sel != 0);
		
	
	}

	// Sub Menu 
	// 1_19. 전체 사원 정보 출력용 View 
	public void selectAll(List<Employee> list) {
		System.out.println("사번 / 이름 / 직책 / 직속상사 / 고용일 / 급여 / 커미션 / 부서번호");
		
		// 향상된 for문 으로 출력
		for(Employee emp : list) {
			System.out.println(emp);
		}
		
		// 완성 후 다시 EmployeeController에 selectAll() 메소드로 돌아가
		// 해당 메소드 호출 
	}
	
	
	// 2_3. 사번 입력용 View (2, 4, 5번 메뉴에 필요)
	public int selectEmpNo() {
		System.out.print("사번 입력 : ");
		int empNo = sc.nextInt();
		sc.nextLine();
		
		return empNo;
		
	}
	
	
	// 2_16. 사원 한명 정보 출력용 View
	public void selectEmployee(Employee emp) {
		System.out.println("사번 : " + emp.getEmpNo()
						 +"\n이름 : " + emp.getEmpName()
						 +"\n직책 : " + emp.getJob()
						 +"\n직속상사 : " + emp.getMgr()
						 +"\n고용일 : " + emp.getHireDate()
						 +"\n급여 : " + emp.getSal()
						 +"\n커미션 : " + emp.getComm()
						 +"\n부서번호 : " + emp.getDeptNo()
					);
		
		// 작성 완료 후 메인메뉴에서 EmployeeController.selectEmployee() 호출
	}
	
	// 2_20. 서비스 요청 실패 메세지 출력 View
			public void displayError(String msg) {
				System.out.println("서비스 요청 실패 - " + msg);
			}



			
	// 3_3. 새로운 사원 정보를 입력 받는 View
	public Employee insertEmployee() {
		System.out.println("[새로운 사원 정보 추가]");
		System.out.print("사번 : ");
		int empNo = sc.nextInt();
		sc.nextLine(); // 개행문자 제거
		
		System.out.print("이름 : ");
		String empName = sc.nextLine();
		
		System.out.print("직책 : ");
		String job = sc.nextLine();
		
		System.out.print("직속상사번호 : ");
		int mgr = sc.nextInt();
	
		System.out.print("급여 : ");
		int sal = sc.nextInt();
		
		System.out.print("커미션(인센티브) : ");
		int comm = sc.nextInt();
		
		System.out.print("부서번호 : ");
		int deptNo = sc.nextInt();
		sc.nextLine();
		
		
		// 입력받은 정보를 Employee 객체에 삽입할 때 생성자를 사용하면 유리하나
		// 현재 매개변수가 8개(hireDate)가 포함된 생성자를 사용하는 것은 옳지 않으므로
		// hireDate 필드를 제외한 별도의 매개변수 7개짜리 생성자를 만들어서 사용하자.
		
		return new Employee(empNo, empName, job, mgr, sal, comm, deptNo);
		
	}
			
	// 3_18. 회원 추가, 수정, 삭제 성공 메세지 출력 View
	public void displaySucess(String msg) {
		System.out.println("서비스 요청 성공 : " + msg);
		
	}
		
	
	// 4_4. 사번으로 사원 정보 수정
	   public Employee updateEmployee() {
	      // 입력된 회원 정보를 담을 Employee 변수 선언
	      Employee emp = null;
	      
	      System.out.print("직책 : ");
	      String job = sc.nextLine();
	      
	      System.out.print("급여 : ");
	      int sal = sc.nextInt();
	      
	      System.out.print("커미션(인센티브) : ");
	      int comm = sc.nextInt();
	      
	      emp = new Employee(job, sal, comm);
	      
	      return emp;
	   }
	
	public char deleteEmployee() {
		System.out.println("정말 삭제하시겠습니까? (y/n)");
		return sc.nextLine().charAt(0);
		// 입력받은 문자열 중 0번 인덱스 문자를 반환 
		
	}

	   
	   
}



