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
<title>MyBlog-内容管理</title>
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
					<li class="dropdown"><a href="javascript:void(0);" onclick="toUsers();" >用户管理</a></li>
					<li class="active"><a href="#">内容管理</a></li>
					<li class="dropdown"><a href="javascript:void(0);" onclick="toComment();">评论管理</a></li>
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
		<li><a href="content.jsp">内容管理</a></li>
		<li>内容搜索</li>
	</ol>
	
	<!-- 搜索框 -->
	<div class="row">
		<div class="col-lg-4">
			<div class="input-group">
				<input id="serchTxt" type="text" class="form-control" placeholder="请输入要搜索内容">
				<span class="input-group-btn">
					<button id="serch" class="btn btn-default" type="button">搜索</button>
				</span>
			</div>
		</div>
	</div>
	<br>
	
	<div class="container-fluid">
		<div class="jumbotron">
			<h3>内容列表</h3>
			<table class="table table-striped table-hover">
				<tr>
					<th style="text-align: center;">ID</th>
					<th style="text-align: center;">类型id</th>
					<th style="text-align: center;">作者id</th>
					<th style="text-align: center;">标题</th>
					<th style="text-align: center;">简介</th>
					<th style="text-align: center;">时间</th>
					<th style="text-align: center;">操作</th>
				</tr>
				<c:if test="${empty contBegin }">
					<c:set var="contBegin" value="0"></c:set>
					<c:set var="contPage" value="1"></c:set>
				</c:if>
				<c:forEach var="cont" items="${SerchContent }" varStatus="status">
				<!-- 存一下tname -->
					<c:forEach var="type" items="${types }">
							<c:if test="${type.tid==cont.tid }">
								<c:set var="tname" value="${type.tname }"></c:set>
							</c:if>
						<c:if test="${type.tid==1 }">
							<c:set var="tname" value="暂不分类"></c:set>
						</c:if>
					</c:forEach>
					<tr align="center">
						<td>${cont.cid }</td>
						<td>${tname }</td>
						<td>${cont.uid }</td>
						<td style="width: 30%;">${cont.title }</td>
						<td style="width: 30%;">${cont.description }</td>
						<td>${cont.addtime }</td>
						<td> <a href="javascript:void(0);" onclick="readAll(${cont.cid })" >查看</a> | <a href="javascript:void(0);" onclick="del(${cont.cid })" >删除</a>  </td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	<script>
		
		//搜索
		$("#serch").click(function () {
			var serchTxt = $("#serchTxt").val();
			$.post("../contentsServlet",{
				op:'adminContentSerch',
				txt:serchTxt
			},function(data){
				if( data==-1 ){
					alert('没有找到您所查询的数据!');
				}else{
					location.href = "contentsSerch.jsp";
				}
			});
		});
		
		//阅读所有的点击事件
		function readAll(cid){
			var flag1;
			var flag2;
			//同步请求
			$.ajaxSettings.async = false;
			//发送请求获取文章内容
			$.post("../contentsServlet",{
				op:'readAll',
				cid:cid
			},function(data){    //若成功返回1
				flag1 = data;
			});
			$.post("../commentsServlet",{
				op:'getComments',
				cid:cid
			},function(data){
				console.log( data );
				flag2 = data;
			});
			
			//异步请求
			$.ajaxSettings.async = true;
			if( flag1>0 ){
				location.href = "../showMain.jsp";
			}

		}
		//删除
		function del( cid ) {
			$.post("../contentsServlet",{
				op:'DelContent',
				cid:cid
			},function( data ){
				if( data>0 ){
					location.reload();
				}else{
					alert("删除失败!");
				}
			});
		}
	</script>
</body>
</html>