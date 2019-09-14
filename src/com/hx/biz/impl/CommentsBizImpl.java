package com.hx.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.hx.bean.Comments;
import com.hx.biz.CommentsBiz;
import com.hx.dao.DBHelper;

public class CommentsBizImpl implements CommentsBiz {
	
	DBHelper db = new DBHelper();
	
	@Override
	public List getCommentsCount() {
		String sql = "select count(*) as count,contents.cid,content from contents,comments where "
						+"contents.cid=comments.cid group by contents.cid;";
		return db.findAll(sql, null);
	}

	@Override
	public List<Comments> getComments(int cid) {
		String sql = "select comid,cid,user.uid,uname,comment,comtime from comments,user "
							+"where comments.uid=user.uid and cid="+cid;
		return db.find(sql, null, Comments.class);
	}

	@Override
	public int addComment(int cid, int uid, String text,String time) {
		String sql = "insert into comments values(null,"+cid+","+uid+",'"+text+"','"+time+"')";
		return db.doUpdate(sql, null);
	}

	@Override
	public List<Comments> getAllCmtByPage(int page,int pagesize) {
		int begin = (page-1) * pagesize;	//定义开始的条数
		String sql = "select comid,cid,user.uid,uname,comment,comtime from comments,user "
				+"where user.uid=comments.uid order by comid limit "+begin+","+pagesize;
		return db.find(sql, null, Comments.class);
	}
	
	/*
	 * import java.util.regex.Pattern;
		Sting str="e1f4t"
		Integer number=Integer.parseInt(Pattern.compile("[^0-9]").matcher(str).replaceAll("").trim());
		输出number,结果为14
	 * */
	@Override
	public int getAllCmtCount() {
		String sql = "select count(*) from comments";
		String result = db.findAll(sql, null).get(0).toString() ;
		result = Pattern.compile("[^0-9]").matcher(result).replaceAll("").trim();
		int count = Integer.parseInt(result);
		return count;
	}

	@Override
	public int AdminDelComment(int comid) {
		String sql = "delete from comments where comid="+comid;
		int result = db.doUpdate(sql, null);
		return result;
	}
	
	@Override
	public List<Comments> adminCommentSerch(String txt){
		String sql = "select * from comments where comment like '%"+txt+"%'";
		return db.find(sql, null, Comments.class);
	}
	
}
