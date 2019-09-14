package com.hx.bean;

public class Contents {
	private int cid;
	private int tid;
	private int uid;
	private String title;
	private String addtime;
	private String description;
	private String content;
	private int views;
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public Contents(int cid, int tid, int uid, String title, String addtime, String description, String content,
			int views) {
		super();
		this.cid = cid;
		this.tid = tid;
		this.uid = uid;
		this.title = title;
		this.addtime = addtime;
		this.description = description;
		this.content = content;
		this.views = views;
	}
	public Contents() {
		super();
	}

}
