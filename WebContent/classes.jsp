<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MyBlog</title>
<link rel="short icon" href="images/IMG_0293.jpg" />
<link href="css/bootstrap.css" rel="stylesheet" />
<link href="css/main.css" rel="stylesheet" />
<script src="js/jquery.min.js"></script>
<script src="js/index.js"></script>
<script src="js/jq-slideVerify.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header>
		<div class="backimg">
			<img src="images/IMG_0293.jpg">
		</div>
		<div class="logo">
			<span></span><img src="images/00002637.png">
		</div>
	</header>

	<nav>
		<div class="menu">
			<c:forEach var="type" items="${types }" varStatus="status">
				<c:if test="${status.index==0 }">
					<a href="doIndex.jsp">首页</a>
				</c:if>
				<c:if test="${status.index!=0 }">
					<c:if test="${classIndex==status.index }">
						<a class="focus" href="doClasses.jsp?tid=${type.tid }&index=${status.index}">${type.tname }</a>
					</c:if>
					<c:if test="${classIndex!=status.index }">
						<a class="menu-class" href="doClasses.jsp?tid=${type.tid }&index=${status.index}">${type.tname }</a>
					</c:if>
				</c:if>
			</c:forEach>
		</div>
	</nav>

	<main class="clear">
	<div class="mainLeft">
		<c:set var="classcounts" value="0"></c:set>
		<c:forEach var="content" items="${classContents }"
			begin="${classesBegin }" end="${classesBegin+4 }" varStatus="status">
			<c:if test="${tid==content.tid }"></c:if>
			<div class="listBox">
				<h1>${content.title }</h1>
				<p class="colDefault">
					作者：<span class="colInfo">${content.uname }</span> - 时间：<span
						class="colInfo">${content.addtime }</span> - 阅读：<span
						class="colInfo">${content.views }</span> - 评论：<span
						class="colInfo">0</span>
				</p>
				<dfn>
					<p>${content.discription }</p>
				</dfn>
				<div class="function">
					<a onclick="readAll(${content.cid})" href="javascript:void(0);">阅读全文</a>
				</div>
			</div>

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
		<c:if test="${empty user }">
			<div class="rightBox" id="loginBox">
				<div class="title">
					<span>登录</span>
				</div>
				<div class="line">
					<span class="colDark">用户名：</span><input name="username" type="text"><em></em>
				</div>
				<div class="line">
					<span class="colDark">密码：</span><input name="password"
						type="password"><em></em>
				</div>
				<div class="verify-wrap" id="verify-wrap"></div>
				<button type="button" id="resetBtn" hidden="true">重置滑动</button>
				<div class="line">
					<span class="colDark"></span>
					<button id="login" >登 录</button>
				</div>
				<p class="textRight">
					还没注册？<a href="javascript:changeBox('#registerBox','#loginBox');" onclick="res('#verify-wrap2');"
						class="colMint">马上注册</a>
				</p>
				<p class="colWarning textCenter"></p>
			</div>
			<div class="rightBox" id="registerBox" style="display: none">
				<div class="title">
					<span>注册</span>
				</div>
				<div class="line">
					<span class="colDark">用户名：</span><input name="username" type="text">
				</div>
				<div class="line">
					<span class="colDark">密码：</span><input name="password"
						type="password">
				</div>
				<div class="line">
					<span class="colDark">确认：</span><input name="repassword"
						type="password">
				</div>
				<div class="verify-wrap" id="verify-wrap2"></div>
				<button type="button" id="resetBtn2" hidden="true">重置滑动</button>
				<div class="line">
					<span class="colDark"></span>
					<button id="reg" >注 册</button>
				</div>
				<p class="textRight">
					已有账号？<a href="javascript:changeBox('#loginBox','#registerBox');" onclick="res('#verify-wrap');"
						class="colMint">马上登录</a>
				</p>
				<p class="colWarning textCenter"></p>
			</div>
		</c:if>

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
					<a href="user/personManage.jsp">个人中心</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="user/write.jsp">写博客</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="user/myAticle.jsp">我的博客</a>
				</p>
				<p>
					<span class="colDark"><a href="javascript:void(0);"
						id="logout">退出</a></span>
				</p>
			</div>
		</c:if>
		<div class="rightBox" id="serch">
			<div class="title">
				<span>搜索一下</span>
			</div>
			<div class="input-group">
				<input id="serchTxt" type="text" class="form-control" placeholder="请输入要搜索内容">
				<span class="input-group-btn">
					<button id="mySerchTxt" class="btn btn-default" type="button" onclick="mySerch();">站内搜索</button>
				</span>
			</div>
		</div>
		<div class="rightBox">
			<div class="title">
				<span>社区</span>
			</div>
			<p>百度一下</p>
			<div class="input-group">
				<input id="bdserchTxt" type="text" class="form-control" placeholder="请输入要搜索内容">
				<span class="input-group-btn">
					<button id="bdSerchTxt" class="btn btn-default" type="button" onclick="bdSerch();">百度搜索</button>
				</span>
			</div>
		</div>
	</div>
	</main>

	<div class="copyright textCenter">Copyright © huathy.com 版权所有 |
		京xxx备xxxxxxxxx号</div>

	<script src="js/jquery-1.12.4.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script>
		$(function() {
			res('#verify-wrap');	//显示登录的滑动验证条
			if( $(".menu").html().trim().length <= 0 ){
				location.href = "doIndex.jsp";
			}
			var currentPage = $(".pager").find("strong").text().split("/")[0];    //当前是第几页
			if( typeof(currentPage) == "undefined" ){	//如果当前页类型为undefined
				location.href = "doClasses.jsp";		//就跳转页面再进入该页面
			}
		});
		
		//上下跳页
		function changePage(change) {
			$.post("/blog/contentsServlet",{
				op:'getClassesPage',
				change:change
			},function( data ){
				location.reload();
			});
		}
		
		//登录
		$("#login").click(function () {
			var uname = $(this).parent().parent().find("input").eq(0).val();
			var pwd = $(this).parent().parent().find("input").eq(1).val();
			login(uname,pwd);
		});
		
		//注册
		$("#reg").click(function(){
			var uname = $("#registerBox").find("input").eq(0).val();
			var pwd = $("#registerBox").find("input").eq(1).val();
			var repwd = $("#registerBox").find("input").eq(2).val();
			reg(uname,pwd,repwd);
		});
		
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