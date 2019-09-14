package com.hx.biz;

import java.util.List;

import com.hx.bean.User;

public interface UserBiz {
	/**
	 * 
	 * @param pwd 
	 * @param uname 
	 * @return
	 */
	public User login(String uname, String pwd);
	
	/**
	 * ע��
	 * @param uname
	 * @param pwd
	 * @return
	 */
	public int reg(String uname,String pwd);
	
	/**
	 * ��ȡ�����û�
	 * @return
	 */
	public List<User> getAllUser();
	
	/**
	 * ��ҳ��ѯ
	 * @param page  ��ǰҳ��
	 * @param size  ÿҳ����������
	 * @return
	 */
	public List<User> getAllUserByPage(int page,int size);
	
	/**
	 * ����uid�����û��ĸ�����Ϣ
	 * @param uid ID
	 * @param uname �û���
	 * @param pwd ����
	 * @param email ����
	 * @param headimg �û�ͷ��
	 * @return int >0�ɹ�
	 */
	public int userModify(int uid,String uname,String pwd,String email,String headimg);
}
