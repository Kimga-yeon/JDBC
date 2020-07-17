package com.kh.jdbc1.model.vo;

import java.sql.Date;

/* VO (Value Object) : 값을 취급하는 객체
 * -> DB에 있는 테이블의 한 행의 정보를 기록하는데 사용하는 객체
 * 
 * - VO와 같거나 비슷한 의미를 가지는 단어
 * row(한 행) , record(DB에저장) , entity(객체), do(도메인), dto(data transfer Object)
 * 
 * 
 * 작성방법
 * 1) 반드시 캡슐화 적용 (모든 필드는 private)
 * 2) 기본 생성자, 매개변수 생성자 필수
 * 3) 모든 필드에 대한 get/set작성 필수
 * 
 * 
 * */
public class Employee {
	
	private int empNo; //사번
	private String empName; 
	private String job;
	private int mgr; //직속 상사 번호
	private Date hireDate; //고용일
	private int sal; //급여
	private int comm; //커미션(인센티브,보너스)
	private int deptNo; //부서번호
	
	
	
	
	
	public Employee() {	}

	
	public Employee(int empNo, String empName, String job, int mgr, Date hireDate, int sal, int comm, int deptNo) {
		super();
		this.empNo = empNo;
		this.empName = empName;
		this.job = job;
		this.mgr = mgr;
		this.hireDate = hireDate;
		this.sal = sal;
		this.comm = comm;
		this.deptNo = deptNo;
	}

	
	
	
	
	
	public Employee(String job, int sal, int comm) {
		super();
		this.job = job;
		this.sal = sal;
		this.comm = comm;
	}


	public Employee(int empNo, String empName, String job, int mgr, int sal, int comm, int deptNo) {
		super();
		this.empNo = empNo;
		this.empName = empName;
		this.job = job;
		this.mgr = mgr;
		this.sal = sal;
		this.comm = comm;
		this.deptNo = deptNo;
	}


	
	
	public int getEmpNo() {
		return empNo;
	}


	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public int getMgr() {
		return mgr;
	}


	public void setMgr(int mgr) {
		this.mgr = mgr;
	}


	public Date getHireDate() {
		return hireDate;
	}


	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}


	public int getSal() {
		return sal;
	}


	public void setSal(int sal) {
		this.sal = sal;
	}


	public int getComm() {
		return comm;
	}


	public void setComm(int comm) {
		this.comm = comm;
	}


	public int getDeptNo() {
		return deptNo;
	}


	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}


	@Override
	public String toString() {
		return empNo + " / " + empName + " / " + job + " / " + mgr + " / "
				+ hireDate + " / " + sal + " / " + comm + " / " + deptNo;

	}
	
	
	
	
	
	
	
	
	
	

}
