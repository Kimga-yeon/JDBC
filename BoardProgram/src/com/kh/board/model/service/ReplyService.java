package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.board.model.dao.ReplyDAO;
import com.kh.board.model.vo.Reply;

public class ReplyService {

	/** 댓글 삽입용 Service
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertReply(Reply reply) throws Exception {
		Connection conn = getConnection();
		
		int result = new ReplyDAO().insertReply(reply,conn);
		
		if(result > 0) commit(conn);
		else 		   rollback(conn);
		return result;
	}

	/** 댓글 조회용 Service
	 * @param no
	 * @return rList
	 * @throws Exception
	 */
	public List<Reply> selectReply(int no) throws Exception {
		Connection conn = getConnection();
		List<Reply> rList = new ReplyDAO().selectReply(no,conn);
		
		close(conn);
		return rList;
	}

}
