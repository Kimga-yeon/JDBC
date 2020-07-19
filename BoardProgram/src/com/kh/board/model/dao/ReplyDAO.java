package com.kh.board.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.board.model.vo.Reply;

public class ReplyDAO {
	private Properties prop = null;
	public ReplyDAO() throws Exception{
		prop = new Properties();
		prop.load(new FileReader("reply_query.properties"));
		
	}
	/** 댓글 삽입용 DAO
	 * @param reply
	 * @param conn
	 * @return result
	 * @throws Exception
	 */
	public int insertReply(Reply reply, Connection conn)throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertReply");
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setInt(2, reply.getBoardNo());
			pstmt.setString(3, reply.getReplyWriter());
			
			result = pstmt.executeUpdate();
			
		}finally{
			close(pstmt);
			
		}
		return result;
	}
	/**댓글 조회용 DAO
	 * @param no
	 * @param conn
	 * @return rList
	 * @throws Exception
	 */
	public List<Reply> selectReply(int no, Connection conn) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Reply> rList = null;
		
		String query = prop.getProperty("selectReply");
		
		String query2 = "SELECT * FROM REPLY WHERE BOARD_Writer = ' " + no + "' ";
		// 스테이트먼트로 쓸때 스트링이면 작은따옴표 붙이기
		
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			rset = pstmt.executeQuery();
			
			rList = new ArrayList<Reply>();
			Reply r = null;
			
			while(rset.next()) {
				r = new Reply(rset.getInt(1), 
							  rset.getString(2), 
							  rset.getDate(3), 
							  rset.getString(4));
				
				rList.add(r);
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return rList;
	}
}
