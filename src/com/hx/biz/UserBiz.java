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
	 * 注册
	 * @param uname
	 * @param pwd
	 * @return
	 */
	public int reg(String uname,String pwd);
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<User> getAllUser();
	
	/**
	 * 分页查询
	 * @param page  当前页数
	 * @param size  每页多少条数据
	 * @return
	 */
	public List<User> getAllUserByPage(int page,int size);
	
	/**
	 * 根据uid更改用户的个人信息
	 * @param uid ID
	 * @param uname 用户名
	 * @param pwd 密码
	 * @param email 邮箱
	 * @param headimg 用户头像
	 * @return int >0成功
	 */
	public int userModify(int uid,String uname,String pwd,String email,String headimg);
}
