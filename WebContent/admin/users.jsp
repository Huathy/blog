<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../js/jquery-1.12.4.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/admin.js"></script>
<title>MyBlog-用户管理</title>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">后台管理</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">用户管理</a></li>
					<li class="dropdown"><a href="javascript:void(0);" onclick="toContent();">内容管理</a></li>
					<li class="dropdown"><a href="javascript:void(0);" onclick="toComment();">评论管理</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
							aria-haspopup="true" aria-expanded="false">分类管理 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="type_index.jsp">分类首页</a></li>
							<li><a href="type_add.jsp">添加分类</a></li>
						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"> ${user.uname } 的用户中心 <span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="../user/personManage.jsp">个人中心</a></li>
							<li><a href="javascript:void(0);" onclick="logout();">退出登录</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- 路徑導航 -->
	<ol class="breadcrumb">
		<li><a href="../admin/">后台首页</a></li>
		<li><a href="#">用户管理</a></li>
		<li class="active">用户列表</li>
	</ol>

	<div class="container-fluid">
		<div class="jumbotron">
			<h3>用户列表</h3>
			<table class="table table-striped table-hover">
				<tr>
					<th style="text-align: center;">ID</th>
					<th style="text-align: center;">用户名</th>
					<th style="text-align: center;">密码</th>
					<th style="text-align: center;">是否为管理员</th>
				</tr>
				<c:forEach var="myuser" items="${users }" varStatus="status">
					<tr align="center">
						<td>${myuser.uid }</td>
						<td>${myuser.uname }</td>
						<td>${myuser.pwd }</td>
						<c:if test="${myuser.isadmin==0 }">
							<td>否</td>
						</c:if>
						<c:if test="${myuser.isadmin==1 }">
							<td>是</td>
						</c:if>
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
				int pages = (int) request.getSession().getAttribute("usersPages");
				for (int i = 0; i < pages; i++) {
			%>
			<c:set var="i" value="<%=i%>"></c:set>
			<li><a href="javascript:void(0);"
				onclick="changePage(<%=i + 1%>);"><%=i + 1%></a></li>
			<%
				}
			%>
			<li><a href="javascript:void(0);" onclick="changePage(-2);"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>

	<script>
		//更改页面
		function changePage( page ){
			$.post("../userServlet",{
				op:'getAllUser',
				page:page,	//默认第一页
				size:'7'	//每页7条数据
			},function( data ){
				if( data>0 ){
					location.href = "users.jsp";
				}else{
					alert("网络错误!");
				}
			});
		}
	</script>
</body>
</html>