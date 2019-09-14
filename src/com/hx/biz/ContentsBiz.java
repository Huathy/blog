package com.hx.biz;

import java.util.List;
import java.util.Map;

import com.hx.bean.Contents;

public interface ContentsBiz {
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public List<Map<String, String>> getAllContent();
	
	/**
	 * �Ķ�ȫ��,��ȡȫ�ĵ���ϸ����
	 * @param cid ����id
	 * @return
	 */
	public Contents readAll(int cid);
	
	/**
	 * ����cid ɾ������
	 * @param cid ����id
	 * @return
	 */
	public int AdminDelContent(int cid);
	
	/**
	 * д�����������
	 * @param tid
	 * @param uid
	 * @param title
	 * @param desc
	 * @param content
	 * @return
	 */
	public int addContent(int tid,int uid,String title,String desc,String content);
	
	/**
	 * �Ķ�ȫ�ĵĵ���͸����������,�� ��������г���������,��ʹ���ɹ�Ҳ��Ӱ���û�ʹ��
	 * @param cid
	 * @return
	 */
	public int updateViews(int cid);
	
	/**
	 * ����Աҳ�����������
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
