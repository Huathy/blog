package com.hx.biz;

import java.util.List;
import java.util.Map;

import com.hx.bean.Contents;

public interface ContentsBiz {
	
	/**
	 * 获取所有文章
	 * @return
	 */
	public List<Map<String, String>> getAllContent();
	
	/**
	 * 阅读全文,获取全文的详细内容
	 * @param cid 文章id
	 * @return
	 */
	public Contents readAll(int cid);
	
	/**
	 * 根据cid 删除文章
	 * @param cid 文章id
	 * @return
	 */
	public int AdminDelContent(int cid);
	
	/**
	 * 写博客添加文章
	 * @param tid
	 * @param uid
	 * @param title
	 * @param desc
	 * @param content
	 * @return
	 */
	public int addContent(int tid,int uid,String title,String desc,String content);
	
	/**
	 * 阅读全文的点击就更新浏览人数,将 这个单独列出来发请求,即使不成功也不影响用户使用
	 * @param cid
	 * @return
	 */
	public int updateViews(int cid);
	
	/**
	 * 管理员页面的文章搜索
	 * @param txt
	 * @return
	 */
	public List<Map<String,String>> adminContentSerch(String txt);
	
	/**
	 * 
	 * @param tid
	 * @param uid
	 * @param title
	 * @param desc
	 * @param content
	 * @return
	 */
	public int updateContent(int cid,int tid, String title, String desc, String content);
}
