<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link href="css/readAll.css" rel="stylesheet" />
<script src="js/jquery.min.js"></script>
<script src="js/index.js"></script>
<script src="js/jq-slideVerify.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
    blockquote {
        border-left:#eee solid 5px;
        padding-left:20px;
    }
    ul li {
        line-height: 20px;
    }
    code {
        color:#D34B62;
        background: #F6F6F6;
    }
</style>
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
					<a href="doIndex.jsp">${type.tname }</a>
				</c:if>
				<c:if test="${status.index!=0 }">
					<a class="menu-class" href="doClasses.jsp?tid=${type.tid }&index=${status.index}">${type.tname }</a>
				</c:if>
			</c:forEach>
		</div>
	</nav>

	<main class="clear">
	<div class="mainLeft">
		<span hidden="true" class="main-contentId" id="main-contentId">${mainContent.cid }</span>
		<h1 class="main-title">${mainContent.title }</h1>
		<div class="main-content">
			<div class="content">${mainContent.content }</div>
		</div>
		<div class="main-writeComment" tabindex="1">
			<textarea id="commentInp" rows="10" cols="1" maxlength="300"
				placeholder="请在此输入评论"></textarea>
			<nobr id="MW-control">
				<span>评论区暂不支持html</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span>您还可以输入<span
					class="characters">500</span>个字符
				</span>
				<button id="MW-btn-submit" type="button" class="btn btn-info">发表评论</button>
				<button id="MW-btn-cancel" type="button" class="btn btn-warning">取消回复</button>
			</nobr>
		</div>
		<div class="main-comments">
			<h3>全部评论:</h3>
			<table id="allComment" class="table">
				<c:forEach var="comment" items="${comments }" varStatus="status">
					<tr style="border-top: #ccc 2px solid;">
						<td class="cmtUid" hidden>${comment.uid }</td>
						<td class="floor">${status.count }楼</td>
						<td class="cmtUser" style="width: 60%;word-wrap: break-word;word-break: break-all;overflow: hidden;">${comment.uname }：${comment.comment }</td>
						<td class="cmTime" style="float: right;">评论时间：${comment.comtime }</td>
					</tr>
					<c:if test="${status.last }">
						<c:set var="floor" value="${status.count }"></c:set>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div> 
	<div class="mainRight" style="margin-left: 20px;">
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
					<button id="login">登 录</button>
				</div>
				<p class="textRight">
					还没注册？<a href="javascript:changeBox('#registerBox','#loginBox');"  onclick="res('#verify-wrap2');" 
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
					<button id="reg">注 册</button>
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
				<uid hidden>${user.uid }</uid>
				<div class="title">
					<span>用户信息</span>
				</div>
				<img alt="加载失败" src="${user.headimg }" style="float:right; width: 40px;height: 40px;">
				<c:if test="${user.isadmin==0 }">
					<p class="userName">
						<span class="colDark">用户:<uname>${user.uname }</uname> 您好!
						</span>
					</p>
				</c:if>
				<c:if test="${user.isadmin==1 }">
					<p class="adminInfo">
						<span class="colDanger">您好，管理员！<uname>${user.uname }</uname></span>
						<a href="admin">进入管理界面</a>
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

	<div class="copyright textCenter">Copyright © baidu.com 版权所有 |
		京xxx备xxxxxxxxx号</div>

	<script src="js/jquery-1.12.4.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script>
		//判断menu中是否有内容,没有就跳转网页重新发送请求
		$(function() {
			res('#verify-wrap');	//显示登录的滑动验证条
			if( $(".menu").html().trim().length <= 0 ){
				location.href = "doIndex.jsp";
			}
			//进入该页面,阅读全文的点击就更新浏览人数,将 这个单独列出来发请求,即使不成功也不影响用户使用
			$.post("../blog/contentsServlet",{
				op:'updateViews',
				cid:'${mainContent.cid }'	//如果${mainContent.cid }是空的,直接这样写就会报错,但是用引号包起来就不会报错了,如:'${mainContent.cid }'
			},function(data){
				console.log( "updateviews:"+data );
			});
		});
		
		//commentInp获焦
		$("#commentInp").focus(function() {
			$("#MW-control").css("display","inline-block");
			$("#commentInp").css("height","100px");
			if( $("#userInfo").find("uid").html()==undefined ){
				alert( "请登录后再评论!" );
				$("#commentInp").blur();
			}
			//获焦时的键盘按下事件,在鼠标按下时计算可输入的字符数
			$(document).keydown(function(event){
				var characters = 300 - $("#commentInp").val().length;
				$(".characters").html(characters);
			});
			//$(".main-writeComment").focus();
		});
		
		var times;
		//commentInp的失焦事件
		$("#commentInp").blur(function () {
			times=setTimeout(function(){
				$("#MW-control").css("display","none");
				$("#commentInp").css("height","34px");
			},100);
		});
		
		//MW-btn-submit发表评论的点击事件
		$("#MW-btn-submit").click(function () {
			clearTimeout(times);
			
			var text = $("#commentInp").val();
			var uid = $("#userInfo").find("uid").html();
			var cid = $(".main-contentId").html();
			
			//let str =  "sdfsfwwxxxfsfsxxdsdfwsdfxxxxa3sdswexxxxsdfsxxxxfa"
			//let newStr = str.replace(new RegExp(/xxx/g), "YYY") 
			//console.log(newStr)     //"sdfsfwwYYYfsfsxxdsdfwsdfYYYxa3sdsweYYYxsdfsYYYxfa"
			
			var newtext = text.replace(new RegExp(/<script>/g),"");
			
			if( newtext!="" && newtext!=null && uid!="" && uid != null && cid != null && cid!="" ){
				$.post("../blog/commentsServlet",{
					op:'addComment',
					text:text,
					uid:uid,
					cid:cid
				},function( data ){
					if( data!=-1 ){
						$.post("../blog/commentsServlet",{
							op:'getComments',
							cid:cid
						},function(data){
							location.reload();
						});
						$("#MW-btn-cancel").click();//评论成功后清除文本域的内容
					}
				});
			}
			$("#commentInp").blur();
		});
		//MW-btn-cancel取消发表的点击事件
		$("#MW-btn-cancel").click(function() {
			clearTimeout(times);
			$("#commentInp").focus();
			$("#commentInp").val("");
		});
		
		
		//上下跳页
		function changePage(change) {
			$.post("/blog/contentsServlet",{
				op:'getContentByPage',
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