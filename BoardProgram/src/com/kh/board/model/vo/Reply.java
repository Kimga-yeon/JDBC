package com.kh.board.model.vo;

import java.sql.Date;

public class Reply {
	   private int replyNo;
	   private String replyContent;
	   private Date createDate;
	   private int boardNo;
	   private String replyWriter;
	   
	   public Reply() {
		// TODO Auto-generated constructor stub
	}

	public Reply(int replyNo, String replyContent, Date createDate, int boardNo, String replyWriter) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.createDate = createDate;
		this.boardNo = boardNo;
		this.replyWriter = replyWriter;
	}

	
	
	public Reply(String replyContent, int boardNo, String replyWriter) {
		super();
		this.replyContent = replyContent;
		this.boardNo = boardNo;
		this.replyWriter = replyWriter;
	}

	
	
	public Reply(int replyNo, String replyContent, Date createDate, String replyWriter) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.createDate = createDate;
		this.replyWriter = replyWriter;
	}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getReplyWriter() {
		return replyWriter;
	}

	public void setReplyWriter(String replyWriter) {
		this.replyWriter = replyWriter;
	}

	@Override
	public String toString() {
		return "Reply [replyNo=" + replyNo + ", replyContent=" + replyContent + ", createDate=" + createDate
				+ ", boardNo=" + boardNo + ", replyWriter=" + replyWriter + "]";
	}
	   
	   
	   
}
