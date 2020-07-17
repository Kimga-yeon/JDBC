package com.kh.jdbc1.model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc1.model.vo.Employee;

// DAO (Data Access Object)
// DBMS에 접속하여 실제 데이터를 전송하거나 결과 값을 받는 클래스가 있는 패키지
public class EmployeeDAO {
	
	// 1_3. 모든 사원 정보 조회용 DAO 
	public List<Employee> selectAll(){
		
		// 1_4. JDBC 및 결과를 저장할 변수 선언
		
		// JDBC 변수 선언(java.sql 패키지)
		Connection conn = null;
		// DB의 연결 정보를 담은 객체
		// JDBC 드라이버와 DB사이를 연결해주는 일종의 통로 또는 길 
		
		Statement stmt = null;
		// Connection 객체를 통해 DB에 SQl문을 전달하여 실행시키고
		// 결과값을 반환 받는 역할을 하는 객체
		
		ResultSet rset = null;
		// SELECT문을 사용한 질의 성공 시 반환되는 값을 저장하는 객체
		// - SELECT SQL문에 의해 생성된 결과(Result Set)을 담고 있으며
		//  '커서(CURSOR)'을 이용해서 특정 행에 대한 참조를 조작함
		
		List<Employee> list = null;
		// DB에서 조회한 결과를 저장할 임시 List
		
		try { 
			// 1_5. 해당 DB에 대한 라이브러리(JDBC 드라이버) 등록 작업 진행
			// --> JDBC 드라이버의 클래스를 한번 호출하여 메모리에 로드
			// Class.forName("클래스명") // ClassNotFoundException 예외 처리 필요
			
			// 오라클 드라이버의 클래스명 : "oracle.jdbc.driver.OracleDriver"
			// 드라이버 클래스명은 DBMS 종류에 따라 다름.
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 1_6. DBMS와 연결 작업
			/*
			 * 연결 처리를 하기 위한 DriverManager 클래스 필요
			 * 연결 정보를 담을 Connection 인터페이스가 필요
			 * 
			 * */
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","scott","tiger");
			// --> SQLException 예외 처리 필요
			//  --> DB관련된 모든 에러 -->catch문에서 잡아주고있음 
			
			// jdbc:oracle:thin -> JDBC 드라이버가 thin 타입임을 의미
			// @127.0.0.1 : 오라클 DB가 설치된 서버의 ip
			//  --> 자신의 컴퓨터(local PC)인 경우 고유 ip는 @127.0.0.1이다.
			//	  --> @localhost로 대체 가능
			
			// 1521 : 오라클 listener 포트 번호 
			// xe : 접속할 오라클 DB이름 (Express 버전의 이름이 xe)
			// scott : 접속할 사용자 명
			// tiger : 접속할 사용자 비밀번호 
			
			
	         // 1_7. DB 접속 테스트 --> Connection을 성공적으로 얻어왔다면 conn 출력 시 정보를 확인할 수 있음
	         // System.out.println(conn); (view 외에는 print 구문 쓰지 말 것)
	         
	         // 1_8. DB 접속 테스트 성공 시, 전달할 SQL 구문 작성
	         String query = "SELECT * FROM EMP "; // SQL 작성 시 주의 사항 : 절대 SQL 구문 마지막에 세미콜론(;) 작성하지 말 것
	         query += "WHERE 1=1";
	         String a = "";
	         if(a != null && !"".equals(a)) {
	        	 query += "AND EMPNO = " + a ;
	         }
	         // 1_9. SQL을 DB에 전달하고 결과를 반환 받아올 객체 Statement를 Connetction을 이용하여 생성하기
	         stmt = conn.createStatement();
	         
	         // 1_10. 작성한 SQL문을 Statement 객체를 이용하여 DB 전달 및 실행 후 반환된 결과(Result Set)를 rest(ResultSet 변수)에 대입
	         rset = stmt.executeQuery(query); 
	         // executeQuery(String query) : SELECT문 전달 및 결과 반환용 메소드
	         // excuteUpdate(String query) : DML구문 전달 및 결과 반환용 메소드
	         
	         // 1_11. rset에 있는 SElECT 결과를 처리
	         // -> Java에서는 ResultSet을 그대로 보여줄 수 없으므로
	         // 한 행 씩 접근하여 데이터를 꺼낸 후 List에 저장
	         // --> ArrayList객체 생성 
	         list = new ArrayList<Employee>();
	         
	         // ResultSet에서 꺼낸 한 행의 데이터를 임시 저장할 변수 선언
	         Employee emp = null;
	         
	         // 1_12. while문을 이용해서 조회결과(ResultSet)의 각 행에 반복 접근하여 
	         // 더 이상 읽을 값(행)이 없을 때까지 반복
	         while(rset.next()) {
	        	 // ResultSet.next() : 반환 받은 조회 결과를
	        	 // 커서를 이용해 한 행씩 접근하여
	        	 // 행이 존재하면 true, 아니면 false를 반환하는 메소드
	        	 
	        	 // get[Type] ("컬럼명") : 해당하는 컬럼의 값을 얻어오는 메소드
	        	 //						[Type] 은 가져온 값의  자바 자료형 기입 
	        	 
	        	 int empNo = rset.getInt("EMPNO");
	        	 // 현재 커서가 접근한 행에서 EMPNO 컬럼 값을 얻어와 empNo 변수에 저장
	        	 String empName = rset.getString("ENAME");
	        	 String job = rset.getString("JOB");
	        	 int mgr = rset.getInt("MGR");
	        	 Date hireDate = rset.getDate("HIREDATE");
	        	 int sal = rset.getInt("SAL");
	        	 int comm = rset.getInt("COMM");
	        	 int deptNo = rset.getInt("DEPTNO");
	        	 
	        	 
	        	 // 1_13. 행에서 얻어온 데이터를 Employee 객체에 저장한 후 list에 추가
	        	 emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
	        	 
	        	 list.add(emp);
	         }
	         
	         
	         
	      } catch (Exception e) {
	          	e.printStackTrace();
	      }finally {
	    	  // 1_14. 조회된 모든 결과를 list에 저장한 후
	    	  // 더이상 DB연결이 필요 없으므로 DB연결에 사용 되었던 모든 객체 반환
	    	  // 나중에 생성된 객체부터 반환(close)를 진행 함.
	    	  try {
	    		  if(rset != null) { rset.close();}
	    		  if(stmt != null) { stmt.close();}
	    		  if(conn != null) { conn.close();}
	    		  
	    	  }catch(SQLException e){
	    		  e.printStackTrace();
	    	  }
	      }
		
			// 1_15. 조회된 직원 정보가 담긴 list를 반환
	      return list;
	   }

	// 2_6. 사번을 전달받아 해당 사번의 사원 정보를 조회하여 반환하는 DAO
	public Employee selectEmployee(int empNo ) {
		
		
		
		// 2_7. JDBC 드라이버 등록 및 DB 연결 , 조회결과 저장을 위한 JDBC 변수 선언.
		Connection conn = null;
		//Statement stmt = null;
		
		PreparedStatement pstmt = null;
		// Statement의 자식으로
		// SQL문장에서 인수가 작성되는 공간을 미리 위치 홀더(기호 :?)를 이용하여 확보하고
		// 코드 실행 중 홀더에 알맞은 값을 대입하여 수행하는 객체.
		
		ResultSet rset = null;
		
		
		// 조회된 사원 정보를 저장할 Employee 참조변수 선언
		Employee emp = null;
		
		
		try {
			// 2_8. JDBC 드라이버 등록 및 DB 연결 작업
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// --> 오타 작성 시 ClassNotFoundException 발생 
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott","tiger");
			// --> 오타나면 SQLException 오류 
			
			
			
			//----------------------------------------------------------------
			// 2_9. Statement 객체를 이용할 때 query 작성
			//String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo;
			
			
			// 2_10. 커넥션을 이용해 Statement객체를 생성하고
			// 이를 DB에 전달, 실행한 후 결과를 반환받아 rset에 저장
			//stmt = conn.createStatement();
			//rset = stmt.executeQuery(query);
			
			//-----------------------------------------------------------
			
			// 2_22. PreparedStatement 객체를 이용할 때 query 작성
			String query = "SELECT * FROM EMP WHERE EMPNO = ?";
			
			
			// 2_23. 커넥션 위에 preparedStatement 객체를 올려놓고
			// 홀더(?)에 들어올 값을 위한 공간을 확보한 채로 대기
			pstmt = conn.prepareStatement(query);
			
			// 2_24. 위치 홀더에 알맞은 값 세팅
			// pstmt.set[Type](홀더 위치, 전달할 값);
			pstmt.setInt(1, empNo);
			
			// 2_25. PreparedStatement 객체에 있는 SQL문을 수행하고 결과를 반환받아옴
			rset = pstmt.executeQuery(); // 미리옮겨놨기 때문에 매개변수 없음
										// 만들고-셋팅-추가
			
			
			// 2_11. 조회된 결과가 있을 경우 emp 참조변수에 대입 
			// -> 조회 결과가 한 행 이므로 while문으로 반복하지 않고
			//   if문을 이용해 한번만 검사를 진행하면 됨.
			if(rset.next()) {
				 //int empNo = rset.getInt("EMPNO"); // 매개변수로 전달받은 번호와 같음 
				
	        	 // 현재 커서가 접근한 행에서 EMPNO 컬럼 값을 얻어와 empNo 변수에 저장
	        	 String empName = rset.getString("ENAME");
	        	 String job = rset.getString("JOB");
	        	 int mgr = rset.getInt("MGR");
	        	 Date hireDate = rset.getDate("HIREDATE");
	        	 int sal = rset.getInt("SAL");
	        	 int comm = rset.getInt("COMM");
	        	 int deptNo = rset.getInt("DEPTNO");
	        	 
	        	 emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
	        	 
			}
				
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			// 2_12. DB 연결에 사용되었던 객체 반환
			try {
				if(rset != null) {rset.close();}
				//if(stmt != null) {stmt.close();}
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 2_13. 조회 정보 반환 
		return emp;
	}
	
	
	// 3_6. 새로운 사원 추가 DAO
	public int insertEmployee (Employee emp) {
		// insert/update/delete와 같은 DML구문을
		// JDBC 를 이용하여 수행한 경우 
		// DMl 구문이 성공된 행의 개수가 반환되어 돌아옴
		// ex) 1행 삽입 성공 시 DB에서 1이 반환됨.
		// ex) 삽입 실패 시 DB에서 0이 반환됨.
		
		// 3_7. JDBC드라이버 등록 및 DB연결 , 삽입 결과 저장을 위한 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result = 0; // 삽입 결과를 저장할 변수  
		
		try {
			// 3_8. JDBC 드라이버 등록 작업
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// --> 오타 작성 시 ClassNotFoundException 발생 
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott","tiger");
			
			// 3_9. PreparedStatement 를 이용한 사원 정보 삽입 SQL 작성
			String query = "INSERT INTO EMP VALUES(?, ?, ?, ?, SYSDATE,?, ?, ?)";
			
			// 3_10. SQL문 실행을 위한 위치폴더(?) 자리에 알맞는 값을 대입
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getJob());
			pstmt.setInt(4, emp.getMgr());
			pstmt.setInt(5, emp.getSal());
			pstmt.setInt(6, emp.getComm());
			pstmt.setInt(7, emp.getDeptNo());
			
			
			// 3_11. SQl문을 수행하고 해당 결과를 반환 받아옴
			result = pstmt.executeUpdate();
			// executeUpdate() : DML 구문을 수행하고 성공된 행의 개수 반환

			
			// 3_12. DML 구문은 DBMS에서 수행한다고 해도 
			// 실제 DB에 반영되지 않고 트랜잭션에 담겨있음.
			// 삽입 성공 시 commit, 실패 시 rollback 지정
			if(result > 0) { //삽입 성공 시
				conn.commit();
			}else {
				conn.rollback();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 3_13. 사용한 DB자원 반환
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
				
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		// 3_14. insert 결과 반환
		return result;
	}
	
	
	// 4_8. 사원 정보 수정 DAO
	public int updateEmployee(Employee emp) {
		
		// 4_9. JDBC 드라이버 등록 및 DB연결, 수정 결과 저장 변수 선언 
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		int result = 0;
		
		
		try {
			// 4_10. JDBC 드라이버 등록 작업
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott","tiger");
			
			
		// 4_11. PreparedStatement 이용한 사원 정보 수정 SQL작성 
		String query = "UPDATE EMP SET JOB = ?, SAL = ?, COMM =? WHERE EMPNO = ?";
		
		
			
		
		// 4_12. SQL 전달을 위한 객체 생성 후 위치홀더에 알맞은 값 대입
		pstmt = conn.prepareStatement(query);
		
		pstmt.setString(1, emp.getJob());
		pstmt.setInt(2, emp.getSal());
		pstmt.setInt(3, emp.getComm());
		pstmt.setInt(4, emp.getEmpNo());
		
		
		// 4_13. SQL문을 수행하고 해당 결과를 반환 받아옴.
		result = pstmt.executeUpdate();
		
		
		
		// 4_14. DML 수행 결과에 따라 트랜잭션 처리
		if(result > 0) { //삽입 성공 시
			conn.commit();
		}else {
			conn.rollback();
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}finally {
		// 3_13. 사용한 DB자원 반환
		try {
			if(pstmt != null) {pstmt.close();}
			if(conn != null) {conn.close();}
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		}	
		return result;
	}

	public int deleteEmployee(int empNo) {
	      Connection conn = null;
	      
	      PreparedStatement pstmt = null;
	      
	      int result = 0;  
	      
	      try {
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
	        
	         String query = "DELETE FROM EMP WHERE EMPNO=? ";
	         
	         pstmt = conn.prepareStatement(query);
	         pstmt.setInt(1, empNo);
	         result = pstmt.executeUpdate();
	         
	         if(result > 0) {
	            conn.commit();
	         }else {
	            conn.rollback();
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {
	            if(pstmt != null) {pstmt.close();}
	            if(conn != null) {conn.close();}
	         }catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return result;
	   }
}	