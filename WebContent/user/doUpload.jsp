<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.jspsmart.upload.*,java.util.*,com.hx.utils.*,com.hx.dao.*,com.hx.bean.*"%>
<%
	
	//实例化SmartUpload对象
	SmartUpload su = new SmartUpload();
	//初始化		网页上下文环境
	su.initialize(pageContext);
	//设置编码集
	su.setCharset("utf-8");
	su.upload();
	//获取到单个上传的文件
	com.jspsmart.upload.File file = su.getFiles().getFile(0);
	String filepath = "upload/";
	//Date date = new Date();
	filepath += new Date().getTime() + "." + file.getFileExt();
	
	
	//文件另存为
	file.saveAs(filepath,SmartUpload.SAVE_VIRTUAL);
	
	
	DBHelper db = new DBHelper();
	User user=(User)request.getSession().getAttribute("user");
	String sql = "UPDATE user SET headimg='"+filepath+"' WHERE uid="+user.getUid();
	
	System.out.println( filepath+"-"+sql );
	
	int result = db.doUpdate(sql, null);
	if(result>0){
		out.println("<script>alert('修改成功!');</script>");
		request.getSession().removeAttribute("user");
	%>

		<script languate="javascript">
			window.location.replace("/blog/user/personManage.jsp");
		</script>
<%
	}else{
		out.println("<script>alert('修改失败!');</script>");
	}
	
%>