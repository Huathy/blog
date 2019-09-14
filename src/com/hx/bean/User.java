package com.hx.bean;

public class User {
	private int uid;
	private String uname;
	private String pwd;
	private int isadmin;
	private String email;
	private String headimg;
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public User(int uid, String uname, String pwd, int isadmin, String email, String headimg) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.pwd = pwd;
		this.isadmin = isadmin;
		this.email = email;
		this.headimg = headimg;
	}
	public User() {
		super();
	}
}
