package com.hx.biz;

import java.util.List;

import com.hx.bean.Type;

public interface TypeBiz {
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	public List<Type> getTypeInfo();
	
	/**
	 * �������
	 * @param tname
	 * @param tid
	 * @return
	 */
	public int updateType(String tname,int tid);
	
	/**
	 * ��ӷ���
	 * @param tname
	 * @return
	 */
	public int addType(String tname);
}
