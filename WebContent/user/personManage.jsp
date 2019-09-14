<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<title>MyBlog-用户中心</title>
<link rel="short icon" href="../images/IMG_0293.jpg" />
<style type="text/css">
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
					<a href="../doIndex.jsp" class="focus">${type.tname }</a>
				</c:if>
				<c:if test="${status.index!=0 }">
					<a class="menu-class" href="../doClasses.jsp?tid=${type.tid }&index=${status.index}">${type.tname }</a>
				</c:if>
			</c:forEach>
		</div>
	</nav>
	<div class="container-fluid" style="margin: 0px 20px 0px;">
		<div class="container-fluid">
			<div class="jumbotron">
				<h3>
					<span id="time">2019年10月10日 11:11:11</span>&nbsp;${user.uname }欢迎进入您的个人中心!
					<c:if test="${user.isadmin==1 }">
						<a href="../admin" style="float: right;">&nbsp;进入管理页面&nbsp;</a>
					</c:if>
					<span style="float: right;">
						<a href="myAticle.jsp">我的博客</a>&nbsp;&nbsp;
						<a href="write.jsp" >写博客</a>&nbsp;&nbsp;
					</span>
					
				</h3>
			</div>
		</div>


		<div  class="container-fluid">

			<div id="con-left" class="jumbotron" style="float: left; width: 48%;">
				<div class="input-group">
					<span class="input-group-addon" id="sizing-addon2">用户id:</span> <input
						id="uid" type="text" class="form-control" readonly="readonly"
						value="${user.uid }">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon" id="sizing-addon2">用户名:</span> <input
						autocomplete="off" id="uname" type="text" class="form-control"
						placeholder="${user.uname }" aria-describedby="sizing-addon2">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon" id="sizing-addon2">旧密码:</span> <input
						autocomplete="off" οnfοcus="this.type='password'" id="oldpwd"
						type="password" class="form-control" placeholder="请在此输入您的旧密码"
						aria-describedby="sizing-addon2">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon" id="sizing-addon2">新密码:</span> <input
						id="pwd" type="password" class="form-control"
						placeholder="请在此输入您的密码" aria-describedby="sizing-addon2">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon" id="sizing-addon2">邮箱:</span>
					<c:if test="${empty user.email }">
						<input id="email" type="text" class="form-control"
							placeholder="请在此输入您的邮箱" aria-describedby="sizing-addon2">
					</c:if>
					<c:if test="${not empty user.email }">
						<input id="email" type="text" class="form-control"
							placeholder="请在此输入您的邮箱" value="${user.email }"
							aria-describedby="sizing-addon2">
					</c:if>
				</div>
				<br>
				<div style="display: flex; justify-content: space-around;">
					<button type="submit" class="btn btn-danger" id="determine">确认修改</button>
					<button type="button" class="btn btn-default" id="cancel">取消</button>
					
				</div>
			</div>
			<div id="con-right" class="jumbotron"
				style="float: right; width: 48%; height: 100px;">
				<form action="doUpload.jsp"  method="post" enctype="multipart/form-data" >
					<input id="headimgUid" name="headimgUid" hidden="true" value="${user.uid }">
					<button class="btn btn-danger" type="submit" >修改头像</button>
					<input type="file" class="btn btn-info" value="修改头像"
							name="headpic" id="headpic" accept="image/jpg,image/jpeg"
							onchange="showPic()">
					<div id="myimgs"> <img src="../${user.headimg }" width="200" height="200"  /> </div>
				</form>
			</div>
			<br>
		</div>
	</div>

	<script>
		function showPic(){
			//得到选择的文件
			var files = document.getElementById("headpic").files;
			document.getElementById("myimgs").innerHTML="";
			var str = '';
			for( var i=0;i<1;i++ ){
				//创建文件读取对象
				var reader = new FileReader();
				reader.readAsDataURL( files[i] );
				reader.onload = function( event ) {
					//当文件读取完毕,自动触发事件
					str = '<img src="'+event.target.result+'" width="200" height="200"  />';
					document.getElementById("myimgs").innerHTML += str;
				}
			}
		}
		
		//确认修改
		$("#determine").click( function() {
			var uname = $("#uname").val();
			var oldpwd = $("#oldpwd").val();
			var pwd = $("#pwd").val();
			var email = $("#email").val();
			var pic = null;
			if (pwd == null || pwd == "") {
				pwd = oldpwd; //若用户不修改密码则自动默认为旧密码
			}
			if (uname==null || uname=="" || oldpwd==null || oldpwd=="" || email==null || email=="") {
				alert("用户名、密码和邮箱不可为空！");
			} else{
				$.post("../userServlet",{
					op:'userModify',
					uid:${user.uid},
					uname:uname,
					pwd:pwd,
					email:email
				},function(data){
					if( data>0 ){
						alert("修改个人信息成功！");
						location.href = "../doIndex.jsp";
					}else{
						alert("修改个人信息失败！");
					}
				});
			}
		});
		//取消修改
		$("#cancel").click(function() {
			location.href = "../index.jsp";
		});
		
		
		/*
		//修改头像
		function showPic() {
			//得到选择的文件
			var files = document.getElementById("pic").files;
			var str = '';
			for (var i = 0; i < files.length; i++) {
				//创建文件读取对象
				var reader = new FileReader();
				reader.readAsDataURL(files[i]);
				reader.onload = function(event) {
					//当文件读取完毕,自动触发事件
					console.log(files); //event.target.result
					str = '<span></span><img src="'+event.target.result+'"  class="img-rounded">';
					document.getElementById("headimg").innerHTML = str;
				}
			}
		}*/
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