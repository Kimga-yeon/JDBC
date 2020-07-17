package com.kh.mmp.model.service;
import static com.kh.mmp.common.JDBCTemplate.*;

import java.sql.Connection;
//JDBCTemplate 내부에 있는 static 필드,메소드를 호출 할 때 클래스명 생략 가능
import java.sql.SQLException;
import java.util.List;

import com.kh.mmp.model.dao.MemberDAO;
import com.kh.mmp.model.vo.Member;

public class MemberService {
	//Service
	//- 데이터 가공처리,DB 접속
	//--> 비지니스 로직
	
	//여러 DAO를 호출하여 한 Service에서 여러번의 데이터 CRUD를 진행하여 이 때 필요에 따라
	//데이터의 형태를 가공 처리하고 한 Service에서 수행된 DML 구문을 하나의 트랜잭션으로 묶어 처리한다.
	
	/*
	 *  	Service 클래스에서 메소드 작성하는 방법
	 *  1)controller로부터 매개변수를 전달 받음(단순 조회에서는 얻을 수 없음)
	 *  2)Connection 객체 생성
	 *  3)DAO 객체를 생성
	 *  4)DAO 메소드에 Connection객체와 Controller매개변수를 전달함.
	 *      ex) dao.insertMember(conn.mem);
	 *  5)DAO 수행 결과를 가지고 비지니스 로직 처리 및 트랜잭션 처리 진행
	 * 
	 * */
	
	/**
	 * 1-6. 새로운 회원 추가용 Service
	 * @param mem
	 * @return result
	 * @throws SQLException
	 */
	public int insertMember(Member mem) throws SQLException{
		//1-7. Connection 객체 생성
		//-->DB연결을 위한 여러 내용을 반복적으로 사용하는 것은 비효율적이다.
		//-->JDBCTemplate이라는 클래스를 이용하여 묶어서 사용
		
		//1-8. import static 작성
		//-->static 필드,메소드를 호출 할 때 앞에 클래스명을 생략 가능하게 하는 import
		Connection conn = getConnection();
		
		//1-9. DAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		//1-21. 요청을 처리할 알맞은 DAO 메소드를 호출하여 반환값 저장
		int result = dao.insertMember(mem, conn);
		
		//1-22. DB 처리 결과에 따라 트랜잭션 처리 진행
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		//1-23. DB 처리 결과를 Controller을 닫아주고 DB처리결과를 컨트롤로 반환
		close(conn);
		
		return result;
	}

	/**
	 * 2-2.모든 회원 정보 조회용 Service
	 * @return list (모든 회원 정보)
	 */
	public List<Member> selectAll() throws SQLException{
		//2-3. Connection 객체 생성(얻어오기)
		Connection conn= getConnection();
		
		//2-4. MemberDAO.selectAll(conn)작성
		
		//2-14. MemberDAO 객체를 생성하여 selectAll(conn)메소드
		//호출 결과를 반환 받아 저장
		List<Member> list = new MemberDAO().selectAll(conn);
		
		//2-15. select는 별도의 트랜잭션 처리가 필요 없으므로 바로 Connection 반환 후 Controller로 반환
		close(conn);
		return list;
	}

	/**
	 *  3-8. 입력받은 키워드가 포함된 아이디를 가진 회원 정보 조회용 Service
	 * @param keyword
	 * @return list
	 * @throws SQLException
	 */
	public List<Member> selectMemberId(String keyword) throws SQLException{
		//3-9. Connection 객체 생성(얻어오기)
		Connection conn = getConnection();
		
		//3-10. Connection 객체와 키워드를 전달받아 DB에서 조회하는 
		//MemberDAO.selectMemberId(keyword,conn) 작성 및 호출
		List<Member> list = new MemberDAO().selectMemberId(keyword, conn);
		
		//3-21. 트랜잭션 처리 없이 조회 결과 반환
		close(conn);
		return list;
	}
	
	/**
	 * 3-30. 성별 조회용 Service
	 * @param gender
	 * @return list
	 * @throws SQLException
	 */
	public List<Member> selectGender(char gender) throws SQLException{
		//3-31. 커넥션 생성
		Connection conn = getConnection();
		
		//3-32. 커넥션 객체와 입력받은 성별을 이용하여 DB에 회원 정보를 조회할 
		//MemberDAO.selectGender(gender,conn)작성
		
		//3-42. DB에서 성별 조회 결과 반환받아옴,
		 List<Member> list = new MemberDAO().selectGender(gender, conn);
		 
		 //3-43. 커넥션 반환
		 close(conn);
		 
		 //3-44. 트랜잭션 처리 없이 조회 결과 반환
		 return list;
		
	}


	/**주소 조회용 Service
	 * @param inputKeyword
	 * @return list
	 * @throws SQLException
	 */
	public List<Member> selectAddress(String keyword) throws SQLException{
		Connection conn = getConnection();
		List<Member> list = new MemberDAO().selectAddress(keyword,conn);
		close(conn);
		return list;
	}


	/**
	 *  나이대 조회용 Service
	 * @param inputAge
	 * @return list
	 */
	public List<Member> selectAge(int age) throws SQLException {
		Connection conn = getConnection();
		List<Member> list  =new MemberDAO().selectAge(age,conn);
		close(conn);
		return list;
	}
	
	
	
	/**
	 * 회원 탈퇴용 Service
	 * @param id
	 * @return 
	 * @throws SQLException
	 */
	public int deleteMember(String id)throws SQLException{
	      Connection conn = getConnection();
	      int result  =new MemberDAO().deleteMember(id,conn);
	      if(result>0) {
	         commit(conn);
	      }else {
	         rollback(conn);
	      }
	      
	      close(conn);
	      return result;
	      
	   }
	
	
	/** 4_3. 아이디를 이용한 회원 존재여부 확인 Service
	 * @param memberId
	 * @return check (0 : 없음, 1 : 있음)
	 * @throws SQLException
	 */
	public int checkMember(String memberId) throws SQLException {
		
		// 4_4. 커넥션 객체 생성
		Connection conn = getConnection();
		
		// 4_5. 커넥션과 입력받은 ID를 매개변수로 해서 
		// 회원 존재 여부를 DB에서 조회하는 
		// MemberDAO.checkMember(memberId, conn)작성
		
		// 4_15.  MemberDAO.checkMember(memberId, conn) 호출 후 반환값 저장
		int check = new  MemberDAO().checkMember(memberId, conn);
		
		// 4_16. 조회이므로 별도의 트랜잭션 처리 없이 
		// 커넥션 객채를 닫고(반환), 조회 결과도 반환
		close(conn);
		return check;
		
	}
	
	/** 4_23. 회원 정보 수정용 Service
	 * @param memberId
	 * @param sel
	 * @return result
	 * @throws SQLException
	 */
	public int updateMember(String memberId, int sel, String input) throws SQLException{
		// 4_24. 커넥션 객체 생성
		Connection conn = getConnection();
		
		// 4_25. sel값에 따른 문자열 선택 
		String selStr = null;
		switch(sel) {
		case 1: selStr = "Password";  break;
		case 2: selStr = "Email";  break;
		case 3: selStr = "Phone"; break;
		case 4: selStr = "Address"; break;
		}
		
		// 4_26. ID, selStr, 커넥션을 매개변수로 하여 
		// DB데이터를 수정하는 
		// MemberDAO.updateMember(memberId, selStr, input, conn)작성
		
		// 4_35.  MemberDAO.updateMember(memberId, selStr, input, conn) 
		// 호출 후 반환 값 저장
		int result = new MemberDAO().updateMember(memberId, selStr, input, conn);
		
		// 4_36. 수정 결과에 따라 트랜잭션 처리
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		
		// 4_37. 커넥션 닫고, 결과 반환
		close(conn);
		return result;
	}
	
	
	
	
	
	
	
	
}
