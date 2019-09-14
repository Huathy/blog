数据加载中....     请稍作等待....
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.getSession().setAttribute("begin",0); %>
<%request.getSession().setAttribute("page", 1); %>
<script src="js/jquery-1.12.4.min.js"></script>
<script>
	
	var flag1;
	var flag2;
	//同步请求
	$.ajaxSettings.async = false;
	
	$.post("/blog/typeServlet",{
		op:'getAllType'
	},function( data ){
		flag1 = data;
	});
	
	$.post("/blog/contentsServlet",{
		op:'getAllContent'
	},function( data ){
		flag2 = data;
	});
	
	//获取评论计数   评论为0也是可以进入页面的
	$.post("../blog/commentsServlet",{
		op:'getCommentsCount'
	},function(data){
		console.log(data);
	});
	//异步请求
	$.ajaxSettings.async = true;
	
	
	
	if( flag1>0 && flag2>0 ){
		location.href="index.jsp"
	}else{
		console.log( flag1 +"-"+ flag2 );
		//location.href="error.html"
	}
	
	
</script>