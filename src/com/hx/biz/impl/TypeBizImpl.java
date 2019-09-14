package com.hx.biz.impl;

import java.util.List;

import com.hx.bean.Type;
import com.hx.biz.TypeBiz;
import com.hx.dao.DBHelper;

public class TypeBizImpl implements TypeBiz{
	
	DBHelper db = new DBHelper();
	
	@Override
	public List<Type> getTypeInfo() {
		String sql = "select * from type order by tid";
		List<Type> list = db.find(sql,null,Type.class);
		return list;
	}

	@Override
	public int updateType(String tname, int tid) {
		String sql = "update type set tname='"+tname+"' where tid="+tid;
		return db.doUpdate(sql, null);
	}

	@Override
	public int addType(String tname) {
		int result = 0;
		if( tname.split("|").length>0 ){
			for( int i=0;i<tname.split("|").length;i++ ){
				String sql = "insert into type values(null,'"+tname.split("|")[i]+"',0)";
				result = db.doUpdate(sql, null);
			}
		}else{
			String sql = "insert into type values(null,'"+tname+"',0)";
			result = db.doUpdate(sql, null);
		}
		return result;
	}
	
}
