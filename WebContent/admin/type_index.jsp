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
<title>MyBlog-类别首页</title>
<style type="text/css">
#mubu {
	width: 100%;
	min-height: 1000px;
	background: #eee;
	position: fixed;
	opacity: 0.5;
	z-index: 2;
	display: none;
}

#box {
	width: 400px;
	position: absolute;
	left: 50%;
	top: 20%;
	transform:translateX(-50%);
	z-index: 5;
	display: none;
}
</style>
</head>
<body>
	<div id="mubu"></div>
	<div id="box" class="alert alert-info">
		<p>请输入您要修改的内容:</p><br>
		<input type="hidden" value="">
		<input class="form-control" type="text" value="" id="inp"><br>
		<button class="btn btn-info" onclick="update()">修改</button>
		<button class="btn btn-default" onclick="excs()">取消</button>
	</div>
	
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
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="users.jsp">用户管理</a></li>
					<li class="dropdown"><a href="javascript:void(0);" onclick="toContent();">内容管理</a></li>
					<li class="dropdown"><a href="javascript:void(0);" onclick="toComment();">评论管理</a></li>
					<li class="active"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
						aria-haspopup="true" aria-expanded="false">分类管理 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">分类首页</a></li>
							<li><a href="type_add.jsp">添加分类</a></li>
						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
							role="button" aria-haspopup="true" aria-expanded="false">
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
		<li><a href="#">分类管理</a></li>
		<li>分类首页</li>
	</ol>
	
	<div class="container-fluid">
		<div class="jumbotron">
			<h3>用户列表</h3>
			<table class="table table-striped table-hover">
				<tr>
					<th style="text-align: center;">ID</th>
					<th style="text-align: center;">名称</th>
					<th style="text-align: center;">操作</th>
				</tr>
				<c:forEach var="type" items="${types }" varStatus="status">
					<tr align="center">
						<td>${type.tid }</td>
						<td>${type.tname }</td>
						<td>  <a href="javascript:void(0);" onclick="upt(${type.tid},'${type.tname}');" >修改</a> | 
							<a href="javascript:void(0);" onclick="del(${type.tid})" >删除</a>  </td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	<script>
		function upt( tid,tname ) {
			$("#mubu").css("display","block");
			$("#box").css("display","block");
			
			$("#box input").eq(0).val(tid);
			$("#box #inp").val(tname);
		}
		
		function update() {
			var tname = $("#box #inp").val();
			var tid = $("#box input").eq(0).val();
			if( tname==null || tname=="" ){
				alert("内容不可为空!");
				return;
			}
			//修改类别
			$.post("../typeServlet",{
				op:'updateType',
				tname:tname,
				tid:tid
			},function ( data ) {
				if( data>0 ){
					location.reload();
				}
			});
		}
		
		function del( tid ) {
			
		}
		
		function excs() {
			$("#mubu").css("display","none");
			$("#box").css("display","none");
			
			$("#box #inp").val("");
		}
		
		//更改页面
		function changePage( page ){
			$.post("../userServlet",{
				op:'getAllUser',
				page:page,	//默认第一页
				size:'7'	//每页7条数据
			},function( data ){
				if( data==1 ){
					location.href = "users.jsp";
				}else{
					alert("网络错误!");
				}
			});
		}
	</script>
</body>
</html>