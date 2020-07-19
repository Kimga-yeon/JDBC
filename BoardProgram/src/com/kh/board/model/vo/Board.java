package com.kh.board.model.vo;

import java.sql.Date;

// vo는 무조건 DB의 테이블 컬럼을 다 필드로 만들 필요는 없다 -> DB와 데이터 교환 시 필요한 만큼만 필드로 만듦
public class Board {
   private int boardNo; // oracle : BOARD_NO
   private String title;
   private String content;
   private Date createDate;
   private String writer;
   
   public Board() {}

   public Board(int boardNo, String title, String content, Date createDate, String writer) {
      this.boardNo = boardNo;
      this.title = title;
      this.content = content;
      this.createDate = createDate;
      this.writer = writer;
      
      
      
   }
   
   public Board(String title, String content, String writer) {
	   this.title = title;
	   this.content = content;
	   this.writer = writer;
   }
   
   
   
   public Board(int boardNo, String title, Date createDate, String writer) {
	super();
	this.boardNo = boardNo;
	this.title = title;
	this.createDate = createDate;
	this.writer = writer;
}

public int getBoardNo() {
      return boardNo;
   }

   public void setBoardNo(int boardNo) {
      this.boardNo = boardNo;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   public String getWriter() {
      return writer;
   }

   public void setWriter(String writer) {
      this.writer = writer;
   }
   
   

   @Override
   public String toString() {
      return "Board [boardNo=" + boardNo + ", title=" + title + ", content=" + content + ", createDate=" + createDate
            + ", writer=" + writer + "]";
   }
   
   
}
