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
<title>MyBlog-类别添加</title>
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
					<li class="dropdown"><a href="users.jsp">用户管理</a></li>
					<li class="dropdown"><a href="javascript:void(0);" onclick="toContent();">内容管理</a></li>
					<li class="dropdown"><a href="javascript:void(0);" onclick="toComment();">评论管理</a></li>
					<li class="active"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
						aria-haspopup="true" aria-expanded="false">分类管理 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="type_index.jsp">分类首页</a></li>
							<li><a href="#">添加分类</a></li>
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
		<li>分类添加</li>
	</ol>
	
	<div class="container-fluid">
		<div class="jumbotron">
		
			<h4 style="margin-top: 20px;">请输入分类名:</h4>
			<input placeholder="请输入分类名,如果想插入多个分类,请用'|'隔开" type="text" 
				class="form-control" id="tname" style="width: 60%;margin:20px 0;" >
			<button type="button" class="btn btn-info">添加</button>
		</div>
	</div>
	
	<script>
		$("button").click(function () {
			var tname = $("#tname").val();
			$.post("../typeServlet",{
				op:'addType',
				tname:tname
			},function(data){
				if( data>0 ){
					alert("添加成功!");
				}else{
					alert("添加失败!");
				}
			});
		});
	</script>
</body>
</html>