package com.hx.bean;

public class Comments {
	private int comid;
	private int cid;
	private int uid;
	private String uname;
	private String comment;
	private String comtime;
	public int getComid() {
		return comid;
	}
	public void setComid(int comid) {
		this.comid = comid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComtime() {
		return comtime;
	}
	public void setComtime(String comtime) {
		this.comtime = comtime;
	}
	public Comments(int comid, int cid, int uid, String uname, String comment, String comtime) {
		super();
		this.comid = comid;
		this.cid = cid;
		this.uid = uid;
		this.uname = uname;
		this.comment = comment;
		this.comtime = comtime;
	}
	public Comments() {
		super();
	}
	
}
