package com.hx.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hx.bean.User;
import com.hx.biz.UserBiz;
import com.hx.dao.DBHelper;

public class UserBizImpl implements UserBiz {
	private DBHelper db = new DBHelper();
	
	@Override
	public User login(String uname, String pwd) {
		String sql = "select * from user where uname=? and pwd=?";
		List<Object> params = new ArrayList<Object>();;
		params.add(uname);
		params.add(pwd);
		List<User> list = db.find(sql, params, User.class);
		/*注释处代码解决isadmin*/
//		String sql1 = "select isadmin from user where uname='"+uname+"' and pwd='"+pwd+"'";
//		List<Map<String, String>> result = db.findAll(sql1, null);
//		int isadmin =  Integer.parseInt(  result.toString().split("=")[1].split("}")[0]  );
//		list.get(0).setIsAdmin(isadmin);
		
		
		if( list.size()>0 ) {
			return list.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public int reg(String uname,String pwd) {
		String sql = "insert into user values(null,?,?,0,null,null)";
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		params.add(pwd);
		return db.doUpdate(sql, params);
	}

	@Override
	public List<User> getAllUser() {
		String sql = "select * from user order by uid";
		return db.find(sql, null, User.class);
	}

	@Override
	public List<User> getAllUserByPage(int page, int size) {
		String sql = "select * from user order by uid limit "+(page-1)*size+","+size;
		return db.find(sql, null, User.class);
	}

	@Override
	public int userModify(int uid, String uname, String pwd, String email, String headimg) {
		String sql = "update user set uname=?,pwd=?,email=?,headimg=? where uid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		params.add(pwd);
		params.add(email);
		params.add(headimg);
		params.add(uid);
		
		int result = db.doUpdate(sql, params);
		return result;
	}

}
