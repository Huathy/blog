<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="../viewport" content="width=device-width, initial-scale=1">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../js/jquery-1.12.4.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<!-- 以上五个是在使用bootstrap时需要导入的 -->
<link href="../css/main.css" rel="stylesheet" />
<script src="../js/admin.js"></script>
<script src="../js/index.js"></script>
<title>MyBlog-用户中心</title>
<link rel="short icon" href="../images/IMG_0293.jpg" />

</head>
<body>
	<header>
		<div class="backimg">
			<img src="../images/IMG_0293.jpg">
		</div>
		<div class="logo">
			<span></span><img src="../images/00002637.png">
		</div>
	</header>

	<nav>
		<div class="menu">
			<c:forEach var="type" items="${types }" varStatus="status">
				<c:if test="${status.index==0 }">
					<a href="../doIndex.jsp">${type.tname }</a>
				</c:if>
				<c:if test="${status.index!=0 }">
					<a class="menu-class" href="../doClasses.jsp?tid=${type.tid }&index=${status.index}">${type.tname }</a>
				</c:if>
			</c:forEach>
		</div>
	</nav>

	<main class="clear">
	<div class="mainLeft">
		<c:forEach var="content" items="${contents }" varStatus="status">
			<c:if test="${user.uid==content.uid }">
			<div class="listBox">
				<h1>${content.title }-${content.tid }</h1>
				<p class="colDefault">
					作者：<span class="colInfo">${user.uname }</span> - 时间：<span
						class="colInfo">${content.addtime }</span> - 阅读：<span
						class="colInfo">${content.views }</span> - 评论：<span
						class="colInfo">0</span>
				</p>
				<dfn>
					<p>${content.discription }</p>
				</dfn>
				<div class="function">
					<a onclick="readAllmyBlog(${content.cid})" href="javascript:void(0);">阅读全文</a>
					<a onclick="delAticle(${content.cid})" href="javascript:void(0);" style="float: right;">删除</a>
					<a onclick="changeAticle(${content.cid})" href="javascript:void(0);" style="float:right;margin-right: 20px;">修改</a>
					
				</div>
			</div>
</c:if>
		</c:forEach>
		<div class="pager">
			<ul class="clear">
				<li class="previous"><a href="javascript:void(0);"
					onclick="changePage(-1);">上一页</a></li>
				<li><strong> ${classPage } / ${classPages } </strong></li>
				<li class="next"><a href="javascript:void(0);"
					onclick="changePage(1);">下一页</a></li>
			</ul>
		</div>
	</div>
	<div class="mainRight">
		
		<c:if test="${not empty user }">
			<div class="rightBox" id="userInfo">
				<div class="title">
					<span>用户信息</span>
				</div>
				<img alt="加载失败" src="${user.headimg }" style="float:right; width: 40px;height: 40px;">
				<c:if test="${user.isadmin==0 }">
					<p class="userName">
						<span class="colDark">用户:${user.uname } 您好!</span>
					</p>
				</c:if>
				<c:if test="${user.isadmin==1 }">
					<p class="adminInfo">
						<span class="colDanger">您好，管理员！${user.uname }</span> <a
							href="admin">进入管理界面</a>
					</p>
				</c:if>
				<p>
					<a href="personManage.jsp">个人中心</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="write.jsp">写博客</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="location.reload();" style="border-bottom: 1px solid #e67e22;" >我的博客</a>
				</p>
				<p>
					<span class="colDark"><a href="javascript:void(0);"
						id="logout">退出</a></span>
				</p>
			</div>
		</c:if>
		<div class="rightBox">
			<div class="title">
				<span>社区</span>
			</div>
			<p>
				<a href="" target="_blank" class="colDanger">百度一下</a>
			</p>
			<p>
				<a href="" target="_blank" class="colDanger">你就知道</a>
			</p>
		</div>
	</div>
	</main>

	<div class="copyright textCenter">Copyright © huathy.com 版权所有 |
		京xxx备xxxxxxxxx号</div>
	<script>
		function changeAticle(cid){
			$.post("/blog/contentsServlet",{
				op:'readAll',
				cid:cid
			},function(data){    //若成功返回1
				console.log(data);
				if( data<=0 ){
					alert("文章内容获取失败");
				}else{
					location.href = "updateContent.jsp";
				}
			});
		}
		//阅读所有的点击事件
		function readAllmyBlog(cid){
			var flag1;
			var flag2;
			
			//同步请求
			$.ajaxSettings.async = false;
			//发送请求获取文章内容
			$.post("/blog/contentsServlet",{
				op:'readAll',
				cid:cid
			},function(data){    //若成功返回1
				flag1 = data;
			});
			$.post("/blog/commentsServlet",{
				op:'getComments',
				cid:cid
			},function(data){
				console.log( data );
			});
			
			//异步请求
			$.ajaxSettings.async = true;
			if( flag1>0 ){
				location.href = "../showMain.jsp";
			}
	
		}
		
		function delAticle( cid ) {
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
		/*
		//上下跳页
		function changePage(change) {
			$.post("/blog/contentsServlet",{
				op:'getClassesPage',
				change:change
			},function( data ){
				console.log(data);
				location.reload();
			});
		}
		*/
		//退出登录
		$("#logout").click(function() {
			$.post("/blog/userServlet",{
				op:'logout'
			},function(data){
				location.reload();
			});
		});
	</script>
</body>
</html>