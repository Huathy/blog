package com.hx.biz;

import java.util.List;

import com.hx.bean.Type;

public interface TypeBiz {
	
	/**
	 * 获取所有类别
	 * @return
	 */
	public List<Type> getTypeInfo();
	
	/**
	 * 更改类别
	 * @param tname
	 * @param tid
	 * @return
	 */
	public int updateType(String tname,int tid);
	
	/**
	 * 添加分类
	 * @param tname
	 * @return
	 */
	public int addType(String tname);
}
