<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<link rel="short icon" href="../images/IMG_0293.jpg" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="../viewport" content="width=device-width, initial-scale=1">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../js/jquery-1.12.4.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<!-- 以上五个是在使用bootstrap时需要导入的 -->
<link href="../css/main.css" rel="stylesheet" />
<script src="../js/admin.js"></script>
<title>MyBlog-用户中心</title>
<script src="../js/showdown.min.js"></script>
<style type="text/css">
textarea {
	resize: none;
	font-size: 16px;
}

#showInp {
	word-break: break-all;
	word-wrap: break-word;
}

<-
-引用样式-- !>blockquote {
	border-left: #eee solid 5px;
	padding-left: 20px;
}

<-
-列表样式-- !>ul li {
	line-height: 20px;
}

<-
-代码样式-- !>code {
	color: #D34B62;
	background: #F6F6F6;
}
</style>
</head>
<body>
	<header>
		<div class="backimg">
			<img src="../images/IMG_0293.jpg">
		</div>
		<div class="logo" id="headimg">
			<span></span><img src="../images/00002637.png" class="img-rounded">
		</div>
	</header>
	<nav>
		<div class="menu">
			<c:forEach var="type" items="${types }" varStatus="status">
				<c:if test="${status.index==0 }">
					<a href="../doIndex.jsp">${type.tname }</a>
				</c:if>
				<c:if test="${status.index!=0 }">
					<a class="menu-class"
						href="doClasses.jsp?tid=${type.tid }&index=${status.index}">${type.tname }</a>
				</c:if>
			</c:forEach>
		</div>
	</nav>
	<div class="container-fluid" style="margin: 0px 20px 0px;">
		<div class="container-fluid">
			<div class="jumbotron">
				<h3>
					<span id="time">2019年10月10日 11:11:11</span>&nbsp;${user.uname }欢迎进入您的创作中心!
					<span style="float: right;"> <a href="myAticle.jsp">我的博客</a>&nbsp;&nbsp;
						<a href="personManage.jsp">修改个人信息</a>&nbsp;&nbsp;
					</span>
				</h3>
			</div>

			<div class="input-group">
				<span class="input-group-addon" id="sizing-addon2">文章标题</span> <input
					id="title" type="text" class="form-control" value="${mainContent.title }" placeholder="请输入文章标题"
					maxlength="50" aria-describedby="sizing-addon2">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon" id="sizing-addon2">文章简介</span> <input
					id="desc" maxlength="100" type="text" class="form-control"
					placeholder="请输入文章简介" value="${mainContent.description }" aria-describedby="sizing-addon2">
			</div>
			<br> 请选择文章分类：
			<div class="btn-group">
				<button id="chose-type" type="button"
					class="btn btn-info dropdown-toggle" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
					<span id="show-type" value="1">暂不分类</span>
				</button>
				<ul class="dropdown-menu">
					<c:forEach var="type" items="${types }" varStatus="status">
						<c:if test="${status.index==0 }">
							<li><a onclick="changeType('${type.tid}','暂不分类');">暂不分类</a></li>
							<li role="separator" class="divider"></li>
						</c:if>
						<c:if test="${status.index!=0 }">
							<li><a onclick="changeType('${type.tid}','${type.tname }');">${type.tname }</a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<button id="publish" class="btn btn-danger" style="float: right;">确定修改文章</button>
		</div>

		<div class="jumbotron" style="width: 50%; float: left;">
			<textarea id="textInp" placeholder="请在此输入内容，支持HTML5编辑，支持markdown语法，不支持脚本语言！！！"
				maxlength="10000" rows="30" cols="50" style="width: 100%;">${mainContent.content }
			</textarea>
		</div>
		<div class="jumbotron" style="width: 50%; float: right;">
			<div id="showInp" placeholder="内容展示" style="width: 98%"></div>
		</div>
	</div>

	<script>
		$(function(){
			if( ${empty mainContent.cid} ){
				location.href = "index.jsp";
			}
			convert();
		});
		function changeType(tid,tname) {
			$("#show-type").attr("value",tid);
			$("#show-type").html(tname);
		}
		
		//确定修改文章的点击事件
		$("#publish").click(function () {
			var tid = $("#show-type").attr("value");
			var title = $("#title").val();
			var desc =$("#desc").val();
			var content = $("#textInp").val();
			if( title==null || title=="" || content==null || content=="" || desc=="" || desc==null ){
				alert("标题,简介和内容不可为空！");
			}else{
				$.post("../contentsServlet",{
					op:'updateContent',
					cid:${mainContent.cid},
					tid:tid,
					title:title,
					content:content,
					desc:desc
				},function( data ){
					if( data>0 ){
						//alert("发表文章成功！");
						if (confirm("发表修改成功! 是否继续创作? 若继续将跳转到创作页面! 取消将跳转到您的个人博客中心!")) {
							location.href = "write.jsp";
						} else {
							location.href = "../doIndex.jsp";
						}
					}else{
						alert("文章修改失败!");
					}
					
				});
			}
		});
		//输入框的同步显示事件
		$("#textInp").on('input propertychange',function(){
			convert();
		});
		
		//输入预览同步事件
		function convert(){
			var text = document.getElementById("textInp").value;
			var converter = new showdown.Converter();
			var html = converter.makeHtml(text);
			document.getElementById("showInp").innerHTML = html;
		}
		
		//定时器,每秒执行一次
		setInterval(function() {
			getTimes();
		}, 1000);
		//获取时间的函数
		var mytime = "";
		function getTimes() {
			var date = new Date();
			mytime += date.getFullYear() + "年";
			mytime += (date.getMonth() + 1) + "月";
			mytime += date.getDate() + "日 ";
			mytime += date.getHours() + ":";
			mytime += date.getMinutes() + ":";
			mytime += date.getSeconds();

			$("#time").html(mytime);
			mytime = "";
		}
	</script>
</body>
</html>