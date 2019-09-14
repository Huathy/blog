package com.hx.biz;

import java.util.List;

import com.hx.bean.Comments;

public interface CommentsBiz {
	
	/**
	 * ��ҳͳ����������
	 * @return ÿ�����µ���������list
	 */
	public List getCommentsCount();
	
	/**
	 * �Ķ�ȫ��ҳ����� cid��ȡ��������
	 * @param cid ����id
	 * @return ���ۼ���
	 */
	public List<Comments> getComments(int cid);
	
	/**
	 * ��������
	 * @param cid ����id
	 * @param uid �û�id
	 * @param text ��������
	 * @param time ����ʱ��
	 * @return int ����0 ���۳ɹ�
	 */
	public int addComment(int cid,int uid,String text, String time);
	
	/**
	 * ��ȡ������������
	 * @return int����
	 */
	public int getAllCmtCount();
	
	/**
	 * ��ȡ���е���������
	 *  ����Ա���۹���ҳ��
	 * @return �����������۵ļ���
	 */
	public List<Comments> getAllCmtByPage(int page,int pagesize);
	
	/**
	 * ����cmtid����idɾ������
	 * @param cmtid	����id
	 * @return	int >0ɾ���ɹ�
	 */
	public int AdminDelComment(int comid);
	
	/**
	 * 
	 * @param txt
	 * @return �����йص����۵ļ���
	 */
	public List<Comments> adminCommentSerch(String txt);

}
