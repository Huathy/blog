<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>MyBlog-后台管理</title>
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
	<div class="container-fluid">
		<div class="jumbotron">
			<h1>Hello, ${user.uname }!</h1>
			<p>欢迎进入后台管理</p>
			<p>当前时间:<span>2019年10月10日      11:11:11</span></p>
			<h3><a href="../doIndex.jsp">回到首页</a></h3>
		</div>
	</div>
	
	<script>
		//定时器,每秒执行一次
		setInterval(function(){ getTimes(); }, 1000);
		//获取时间的函数
		var mytime = "";
		function getTimes() {
			var date = new Date();
			mytime += date.getFullYear() + "年" ;
			mytime += ( date.getMonth() + 1) + "月" ;
			mytime += date.getDate() + "日 " ;
			mytime += date.getHours() + ":" ;
			mytime += date.getMinutes() + ":" ;
			mytime += date.getSeconds() ;
			
			$(".jumbotron span").html( mytime );
			mytime = "";
		}
		
	</script>
</body>
</html>