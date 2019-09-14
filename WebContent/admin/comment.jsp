<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../js/jquery-1.12.4.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<!-- 以上五个是在使用bootstrap时需要导入的 -->
<script src="../js/admin.js"></script>
<title>MyBlog-评论管理</title>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" 
					data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="icon-bar"></span> <span class="icon-bar"></span> 
				</button>
				<a class="navbar-brand" href="#">后台管理</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="javascript:void(0);" onclick="toUsers()" >用户管理</a></li>
					<li class="dropdown"><a href="javascript:void(0)" onclick="toContent()">内容管理</a></li>
					<li class="active"><a href="javascript:void(0)" onclick="toComment()" >评论管理</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
						aria-haspopup="true" aria-expanded="false">分类管理 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:void(0);" onclick="toTypeIndex();">分类首页</a></li>
							<li><a href="type_add.jsp">添加分类</a></li>
						</ul>
					</li>
				</ul>
				
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
							aria-haspopup="true" aria-expanded="false">
						${user.uname } 的用户中心 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="../user/personManage.jsp">个人中心</a></li>
							<li><a href="javascript:void(0);" onclick="logout();" >退出登录</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<!-- 路徑導航 -->
	<ol class="breadcrumb">
		<li><a href="../admin/">后台首页</a></li>
		<li>评论管理</li>
	</ol>
	
	<!-- 搜索框 -->
	<div class="row">
		<div class="col-lg-4">
			<div class="input-group">
				<input id="serchTxt" type="text" class="form-control" placeholder="请输入要搜索内容">
				<span class="input-group-btn">
					<button class="btn btn-default" id="serch" type="button">搜索</button>
				</span>
			</div>
		</div>
	</div>
	<br>
	
	<div class="container-fluid">
		<div class="jumbotron">
			<h3>评论列表</h3>
			<table class="table table-striped table-hover">
				<tr>
					<th style="text-align: center;">ID</th>
					<th style="text-align: center;">文章ID</th>
					<th style="text-align: center;">用户ID</th>
					<th style="text-align: center;">用户名</th>
					<th style="text-align: center;">内容</th>
					<th style="text-align: center;">时间</th>
					<th style="text-align: center;">操作</th>
				</tr>
				<c:forEach var="cmt" items="${adminCmts }" varStatus="status">
					<tr align="center">
						<td>${cmt.comid }</td>
						<td>${cmt.cid }</td>
						<td>${cmt.uid }</td>
						<td>${cmt.uname }</td>
						<td style="width: 40%;">${cmt.comment }</td>
						<td>${cmt.comtime }</td>
						<td> <a href="javascript:void(0);" onclick="del(${cmt.comid })" >删除</a>  </td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	<nav aria-label="Page navigation"
		style="display: flex; justify-content: center;">
		<ul class="pagination">
			<li><a href="javascript:void(0);" onclick="changePage(-1);"
				aria-label="Previous"> <spanaria-hidden="true">&laquo;</span></a></li>
			<%
				int pages = (int)request.getSession().getAttribute("adcmtpages");  
				for (int i = 0; i < pages; i++) {
			%>
				<c:set var="i" value="<%=i%>"></c:set>
				<li><a href="javascript:void(0);"
					onclick="changePage(<%=i + 1%>);"><%=i + 1%></a></li>
			<%
				}
			%>
			<li><a href="javascript:void(0);" onclick="changePage(-2);" aria-label="Next"> 
				<span aria-hidden="true">&raquo;</span></a></li>
		</ul>
	</nav>
	
	<script>
		var page = 1;
		if( ${not empty adcmtPage} ){
			page = ${adcmtPage};
		}
		//改变页面
		function changePage( page ) {
			$.post("../commentsServlet",{
				op:'getAllCmtByPage',
				page:page,   //第一次进网页默认第一页
				pagesize:10    //每页展示10条数据
			},function( data ){
				location.href = "comment.jsp";
			});
		}
		//删除
		function del( comid ) {
			$.post("../commentsServlet",{
				op:'AdminDelComment',
				comid:comid
			},function( data ){
				if( data>0 ){
					changePage( page );
				}else{
					alert("删除失败!");
				}
			});
		}
		//搜索
		$("#serch").click(function () {
			var serchTxt = $("#serchTxt").val();
			$.post("../commentsServlet",{
				op:'adminCommentSerch',
				txt:serchTxt
			},function(data){
				if( data==-1 ){
					alert('没有找到您所查询的数据!');
				}else{
					location.href = "commentSerch.jsp";
				}
			});
		});
	</script>
</body>
</html>