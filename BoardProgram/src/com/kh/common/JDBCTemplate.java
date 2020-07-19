package com.kh.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	/*
	 * 	기존 DB 처리작업 시 마다 
	 * 	새로운 Connection 생성, 사용 자원 반환
	 * 	
	 * 	JDBCTemplate은 DB관련 작업에서 공통적으로 사용되어지고
	 * 	추가적은 객체 생성 없이 하나만 있어도 충분히 역할을 해낼 수 있음.
	 * 	-->추가적인 객체를 생성할 수 없도록 하는 싱글톤(SingleTone) 패턴을 적용하여 디자인
	 * 	
	 *     싱글톤 패턴: 프로그램 구동 시 메모리에 객체를 딱 한개만 기록되게 하는 디자인 패턴
	 *     싱글톤 패턴이 적용된 객체를 생성하는 방법: 
	 *     1)모든 필드,모든 메소드를 static으로 선언
	 *     2)new 연산자를 사용한 직접적인 객체 생성을 막는 방법
	 *     	-->생성자의 접근제한자를 private으로 만듦
	 *     	-->대신 객체를 내부적으로 만들어 외부로 반한해주는 별도 메소드를 작성
	 * */
	private static Connection conn = null;
	//공용으로 사용하지만 필드에 직접접근은 차단
	
	//외부에서 DB 연결을 위한 Connection객체를 반환하는 메소드
	public static Connection getConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				//커넥션 객체가 없거나 커넥션 객체가 반환된 상태이면
				//-->새로운 커넥션 객체를 생성하여 반환함
				
				//JDBC 드라이버 로드 관련된 설정,DB 연결 설정은 바뀔 가능성이 높음.
				//이러한 정보를 Class에 직접 작성하는것은 유지보수에 좋지 않음
				//->별도 파일 또는 DB에 설정 정보들을 작성을 함.
				//-->Properties를 사용하여 설정 정보 관리
				
				//Properties == Collection 중 Map의 일종으로 Key,Value가 모두 String인 Map
				//-->Properties 클래스는 쉽게 파일 입출력을 할 수 있는 메소드가 존재
				//외부에서 정보를 읽어와 저장할 Properties 객체 생성
				Properties prop = new Properties();
				
				//driver.properties 파일을 읽어와 prop에 저장
				prop.load(new FileReader("driver.properties"));
				
				//driver.properties에서 읽어온 JDBC 드라이버 등록 정보 및 DB연결 정보를 이용하여
				//JDBC 드라이버 로드 및 DB연결 진행
				Class.forName(prop.getProperty("driver"));
				conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("user"),prop.getProperty("password"));
				
				//AutoCommit 비활성화
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;

	}
	//DB연결 시 반환해야되는 자원들
	//Connection,Statement,PrepareStatement,ResultSet
	public static void close(Connection conn) {
		//반환하려는 커넥션 객체의 주소를 전달받아옴.
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		//반환하려는 커넥션 객체의 주소를 전달받아옴.
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//PreparedStatement는 Statement의 자식으로 별도의 close() 메소드를 작성할 필요 없이
	//매개변수의 다형성 성질을 이용함
	public static void close(ResultSet rset) {
		//반환하려는 커넥션 객체의 주소를 전달받아옴.
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 //트랜잭션 처리도 공통적으로 진행되는 작업이기 때문에 JDBCTemplate에 작성
	public static void commit(Connection conn) {
		//반환하려는 커넥션 객체의 주소를 전달받아옴.
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		//반환하려는 커넥션 객체의 주소를 전달받아옴.
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
