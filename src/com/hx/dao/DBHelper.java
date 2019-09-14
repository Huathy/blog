package com.hx.dao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/**
 * 
 * @author Huathy
 * @version 2.1.2   2019.8.15
 */
public class DBHelper {
	// 封装 先简单封装
	// 连接数据库 一定是第一个运行的且运行一次就够
	static Properties p = new Properties();
	static {
		try {
			InputStream is = DBHelper.class.getClassLoader().getResourceAsStream("db.properties");
			p.load(is);
			//1.加载驱动
			Class.forName(p.getProperty("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 封装一下获取连接
	public Connection getCon() {
		Connection conn = null;
//		try {
//			//2.建立连接
//			conn = DriverManager.getConnection( p.getProperty("url"),p.getProperty("user"),p.getProperty("password") );
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/blog");
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	// 开始封装方法 增删改是一个方法
	public int doUpdate(String sql,List<Object> params) {
		int result = -1;
		try {
			//上面已经加载了驱动,故现只需直接获取连接即可
			Connection con = getCon();
			//开始创建预处理,SQL语句是参数,不需要管
			PreparedStatement ps = con.prepareStatement(sql);
			//要不要设置参数?	难点:1.参数的数量可以用集合	2.参数的数据类型
			doParams(ps, params);
			//先不要设参数
			result = ps.executeUpdate();
			closeAll(con, null, ps, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 注入参数的封装函数
	private void doParams(PreparedStatement ps, List<Object> params) {
		//先判断是否有参数
		try {
			if (params != null && params.size() > 0) {
				//循环集合中的所有值
				for (int i = 0; i < params.size(); i++) {
					//取出集合中的值
					Object o = params.get(i);
					//判断这个值,是不是后面那个数据类型
					if (o instanceof Integer) {
						Integer t = Integer.parseInt(o.toString());
						//注意参数从1开始
						ps.setInt(i + 1, t);
					} else if (o instanceof String) {
						ps.setString(i + 1, o.toString());
					} else if (o instanceof Double) {
						Double t = Double.parseDouble(o.toString());
						ps.setDouble(i + 1, t);
					}else{
						ps.setBytes(i+1, (byte[]) o);
					}
					//TODO 现在只封装了 int string double,以后还需要继续
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 关闭所有连接
	public void closeAll(Connection con, Statement stmt, PreparedStatement ps, ResultSet rs) {
		// 封装就要封装好,要考虑所有情况,包括特殊情况
		try {
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//查询的封装
	//查询和增删改   参数相同; 
	//不同: 执行的方法不同  excuteQuery	executeUpdate
	//最大的区别: 返回值  ResultSet	int
	
	//map的学以致用
	public List<Map<String,String>> findAll(String sql,List<Object> params){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			//前一部分与增删改相同
			//加载驱动前边已经做了,故只需获取连接即可
			Connection con = getCon();
			//开始创建预处理,sql语句是参数,我们不需要管
			PreparedStatement ps = con.prepareStatement(sql);
			//要不要设置参数?	难度:参数的数量可以用集合	2.参数的数据类型
			doParams(ps,params);
			ResultSet rs = ps.executeQuery();
			//至此,查询不一样了
			//首先,返回类型		表的一个字段,对应一个值
			ResultSetMetaData rsmd = rs.getMetaData();
			//这个数组用来存放,表中的列名	作用:获取所有列名
			String[] columnName = new String[ rsmd.getColumnCount() ];	//rsmd.getColumnCount():列名长度,有多少列
			for(int i=0;i<columnName.length;i++){
				//为什么要加1?		因为i从0开始,而这里 ,是从1开始的
//				System.out.println( rsmd.getColumnLabel(1) );
				columnName[i]=rsmd.getColumnName(i+1).toLowerCase();	//转为小写
//				System.out.println( columnName[i] );
			}
			//至此,键有了,开始取值
			while( rs.next() ){
				//Map与HashMap  类似于  List与ArrayList
				Map<String,String> map = new HashMap<String,String>();
				//循环取值		循环列名
				for(int i=0;i<columnName.length;i++){
					//System.out.println( rs.getString(columnName[i]) );
					//String cn = columnName[i];	//内存占用的多	
					//String value = rs.getString(cn);
					//map.put(cn, value);
					//map存值		键值对	键:列名	值:列名对应的值
					map.put(columnName[i], rs.getString(columnName[i]));
				}
				//必须记得:map要加到集合中
				list.add(map);
			}
			closeAll(con, null, ps, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<Map<String,Object>> findAllByByte(String sql,List<Object> params){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			//前一部分与增删改相同
			//加载驱动前边已经做了,故只需获取连接即可
			Connection con = getCon();
			//开始创建预处理,sql语句是参数,我们不需要管
			PreparedStatement ps = con.prepareStatement(sql);
			//要不要设置参数?	难度:参数的数量可以用集合	2.参数的数据类型
			doParams(ps,params);
			ResultSet rs = ps.executeQuery();
			//至此,查询不一样了
			//首先,返回类型		表的一个字段,对应一个值
			ResultSetMetaData rsmd = rs.getMetaData();
			//这个数组用来存放,表中的列名	作用:获取所有列名
			String[] columnName = new String[ rsmd.getColumnCount() ];	//rsmd.getColumnCount():列名长度,有多少列
			for(int i=0;i<columnName.length;i++){
				//为什么要加1?		因为i从0开始,而这里 ,是从1开始的
//				System.out.println( rsmd.getColumnLabel(1) );
				columnName[i]=rsmd.getColumnName(i+1).toLowerCase();	//转为小写
//				System.out.println( columnName[i] );
			}
			//至此,键有了,开始取值
			while( rs.next() ){
				//Map与HashMap  类似于  List与ArrayList
				Map<String,Object> map = new HashMap<String,Object>();
				//循环取值		循环列名
				for(int i=0;i<columnName.length;i++){
					//System.out.println( rs.getString(columnName[i]) );
					//String cn = columnName[i];	//内存占用的多	
					//String value = rs.getString(cn);
					//map.put(cn, value);
					//map存值		键值对	键:列名	值:列名对应的值
					try {
						if(rs.getString(columnName[i])==null || "".equals( rs.getString( columnName[i] ) )  ){
							map.put(columnName[i], rs.getBytes(columnName[i]));
						}else{
							map.put(columnName[i], rs.getString(columnName[i]));
						}
					} catch (Exception e) {
						map.put(columnName[i], rs.getBytes(columnName[i]));
					}
				}
				//必须记得:map要加到集合中
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据对象来查找数据并返回一个对象的集合
	 * @param sql		sql语句
	 * @param params	参数
	 * @param c			对象		你传一个对象给我,我得到该对象中的属性,且返回什么对象给你
	 * @return			返回数据库查到的数据
	 */
	public <T> List<T> find(String sql,List<Object> params,Class<T> c){
		//定义一个泛型的集合
		List<T> list = new ArrayList<T>();
		try {
			//里面的东西，前一部分，和之前相同
			//加载驱动上面已经写了，现在仅需直接获取连接即可
			Connection con = getCon();
			//开始创建预处理，sql语句是参数，我们不需要管
			PreparedStatement ps = con.prepareStatement(sql);
			//要不要设参数？		1.参数的数量     可以用集合      2.参数的类型
			doParams(ps,params);
			ResultSet rs = ps.executeQuery();
			//首先，我们的返回类型     表的一个字段     对应的一个值
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//这个数组，是用来存放，表里的列明的
			//通过对象,来得到该对象中的所有方法
			Method[] ms = c.getMethods();
			String[] columnName = new String[ rsmd.getColumnCount() ];	
			//columnName   获取列的名字
			for(int i=0;i<columnName.length;i++){
				//为什么要+1？   因为我们的i是从0开始的，而这里是从1开始的
				columnName[i]=rsmd.getColumnName(i+1).toLowerCase();	//转为小写
			}
			
			//这里开始不同
			T t;
			String mname;		//方法名
			String cname;		//列名
			String ctypeName;	//类型名称
			while( rs.next() ) {
				//通过反射类,来创建一个对象
				t= c.newInstance();
				for(int i=0;i<columnName.length;i++) {
					cname = columnName[i];		//pid	pname	price
					//我们要设值,pname私有化了,那么我们只有调用setPname这个fangfa
					// pname 	-> 	setPname
					cname = "set" + cname.substring(0, 1).toUpperCase() + cname.substring(1).toLowerCase();
					if( ms != null && ms.length>0 ) {
						for(Method m : ms) {
							mname = m.getName();	//该对象的方法名
							//方法名找到了  并且  这个字段中的值不为空
							if( cname.equals(mname) && rs.getObject(columnName[i])!=null ) {
								//判断类型
								ctypeName = rs.getObject( columnName[i] ).getClass().getName();
								//System.out.println( ctypeName );
								if( "java.lang.Integer".equals(ctypeName) ) {
									m.invoke(t, rs.getInt(columnName[i]));
								}else if("java.lang.String".equals(ctypeName)) {
									m.invoke(t, rs.getString(columnName[i]));
								}else if("java.lang.BigDecimal".equals(ctypeName) || "java.math.BigDecimal".equals(ctypeName)) {
									try {
										m.invoke(t, rs.getInt(columnName[i]));
									} catch (Exception e) {
										m.invoke(t, rs.getDouble(columnName[i]));
									}
								}else if( "java.sql.Date".equals(ctypeName) ) {
									m.invoke(t, rs.getString(columnName[i]));
								}else if( "oracle.sql.BLOB".equals(ctypeName) ) {
									BufferedInputStream bis = null;
									byte[] bytes = null;
									Blob blob = rs.getBlob(columnName[i]);
									try {
										bis = new BufferedInputStream(blob.getBinaryStream());
										bytes = new byte[ (int)blob.length() ];
										bis.read(bytes);
									} catch (IOException e) {
										e.printStackTrace();
									}
									m.invoke(t, bytes);
								}else {
									//默认为String
									m.invoke(t, rs.getString(columnName[i]));
								}
							}
						}
					}
				}
				list.add(t);
			}
			closeAll(con,null,ps,rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}