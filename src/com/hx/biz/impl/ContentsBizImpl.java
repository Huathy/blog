package com.hx.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hx.bean.Contents;
import com.hx.biz.ContentsBiz;
import com.hx.dao.DBHelper;

public class ContentsBizImpl implements ContentsBiz {
	
	DBHelper db = new DBHelper();
	
	//��ҳ�����ݲ�ѯ
	@Override
	public List<Map<String, String>> getAllContent() {
//		long d1 = System.currentTimeMillis();
		String sql = "select cid,contents.tid,user.uid,title,addtime,description,views from contents,type,user "
						+"where contents.uid = user.uid and contents.tid = type.tid order by addTime desc;";
		//���ﲻ������е�����,��Ϊ���ݹ���ķ�ʱ��,(?�����ݹ���ʱ�Ƿ����ɳ�ʱ!!! �뷨:�Է���ѯ���ݹ��������ҳ�޷���ʱ��Ӧ,��ɿ�������)
//		String sql = "select * from contents,type,user where contents.uid = user.uid and contents.tid = type.tid order by cid";
		List<Map<String, String>> list = db.findAll(sql, null);
//		long d2 =System.currentTimeMillis();
//		System.out.println( "t:"+(d2-d1) );
		return db.findAll(sql, null);
	}
	
	//�Ķ�ȫ�ĵ����ݲ�ѯ
	@Override
	public Contents readAll(int cid) {
		String sql = "select * from contents,type,user where contents.uid = user.uid and contents.tid = type.tid and cid="+cid;
		List<Contents> list = db.find(sql, null, Contents.class);
		return list.get(0);
	}
	
	@Override
	public int AdminDelContent(int cid) {
		String sql = "delete from comments where cid ="+cid;//��ɾ���������µ���������,Ȼ����ɾ��������
		db.doUpdate(sql, null);
		sql = "delete from contents where cid ="+cid;
		
		return db.doUpdate(sql, null);
	}

	@Override
	public int addContent(int tid, int uid, String title, String desc, String content) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = sdf.format(date);
		String sql = "INSERT INTO contents VALUES(NULL,"+tid+","+uid+",'"+title+"','"+time+"','"+desc+"','"+content+"',0)";
		return db.doUpdate(sql, null);
	}

	@Override
	public int updateViews(int cid) {
		String sql = "UPDATE contents SET views=views+1 WHERE cid="+cid;
		return db.doUpdate(sql, null);
	}
	
	@Override
	public List<Map<String,String>> adminContentSerch(String txt) {
		List<Map<String, String>> list = new ArrayList();
		String sql = "select * from contents,type,user "
				+"where contents.uid = user.uid and contents.tid = type.tid and content like '%"+txt+"%'";
		
		List<Map<String, String>> temp = null;
		
		temp = db.findAll(sql, null);
		if( !temp.isEmpty() ) {
			for( int i=0;i<temp.size();i++ ) {
				list.add( temp.get(i) );
			}
		}

		sql = "select * from contents,type,user "
				+"where contents.uid = user.uid and contents.tid = type.tid and  description like '%"+txt+"%'";
		temp = db.findAll(sql, null);
		if( !temp.isEmpty() ) {
			for( int i=0;i<temp.size();i++ ) {
				list.add( temp.get(i) );
			}
		}
		
		sql = "select * from contents,type,user "
				+"where contents.uid = user.uid and contents.tid = type.tid and title like '%"+txt+"%'";
		temp = db.findAll(sql, null);
		if( !temp.isEmpty() ) {
			for( int i=0;i<temp.size();i++ ) {
				list.add( temp.get(i) );
			}
		}
		return list;
	}

	@Override
	public int updateContent(int cid,int tid, String title, String desc, String content) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = sdf.format(date);
		String sql = "update contents set tid=?,title=?,addtime=?,description=?,content=? where cid=?";
		//String sql = "update contents set tid='"+tid+"',title='"+title+"',time='"+time+"',description='"+desc+"',content='"+content+"' where cid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(tid);
		params.add(title);
		params.add(time);
		params.add(desc);
		params.add(content);
		params.add(cid);
		
		return db.doUpdate(sql, params);
	}

}
