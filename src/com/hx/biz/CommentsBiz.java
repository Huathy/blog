package com.hx.biz;

import java.util.List;

import com.hx.bean.Comments;

public interface CommentsBiz {
	
	/**
	 * 首页统计评论条数
	 * @return 每个文章的评论数的list
	 */
	public List getCommentsCount();
	
	/**
	 * 阅读全文页面根据 cid获取所有评论
	 * @param cid 文章id
	 * @return 评论集合
	 */
	public List<Comments> getComments(int cid);
	
	/**
	 * 发表评论
	 * @param cid 文章id
	 * @param uid 用户id
	 * @param text 评论内容
	 * @param time 评论时间
	 * @return int 大于0 评论成功
	 */
	public int addComment(int cid,int uid,String text, String time);
	
	/**
	 * 获取所有评论总数
	 * @return int总数
	 */
	public int getAllCmtCount();
	
	/**
	 * 获取所有的评论内容
	 *  管理员评论管理页面
	 * @return 包含所有评论的集合
	 */
	public List<Comments> getAllCmtByPage(int page,int pagesize);
	
	/**
	 * 根据cmtid评论id删除评论
	 * @param cmtid	评论id
	 * @return	int >0删除成功
	 */
	public int AdminDelComment(int comid);
	
	/**
	 * 
	 * @param txt
	 * @return 包含有关的评论的集合
	 */
	public List<Comments> adminCommentSerch(String txt);

}
