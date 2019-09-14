/**
 * index.jsp
 * classes.jsp
 */

//登录与注册的显示
function changeBox(show,hidden) {
	$(show).css("display","block");
	$(hidden).css("display","none");
}

//登录
function login(uname,pwd) {
	if( resflag == false ){	//验证是否通过
		$(".colWarning").html("请先通过验证再登录!");
	}else{
		if( uname==null || uname=="" || pwd=="" ||pwd==null ){
			$(".colWarning").html("用户名和密码不可为空!");
		}else{
			$.post("/blog/userServlet",{
				op:'login',
				uname:uname,
				pwd:pwd
			},function( data ){
				console.log("logindata:"+data);
				if( data<=0 ){
					$(".colWarning").html("用户名或密码错误!");
				}else {
					//var time2 = myDate.getTime();
					//console.log("login-time:"+ time2-time1 );
					$(".colWarning").html("登录成功,即将跳转...");
					setTimeout(() => {
						location.reload();
					}, 1000);
				}
			});
		}
	}
}


//注册
function reg(uname,pwd,repwd){
	if( resflag == false ){	//验证是否通过
		$(".colWarning").html("请先通过验证再登录!");
	}else{
		if(  uname==null || uname=="" || pwd=="" ||pwd==null  ){
			$(".colWarning").html("用户名和密码不可为空!");
		}else if( pwd!=repwd ){
			$(".colWarning").html("两次密码不一致!请重新输入!");
		}else{
			$.post("/blog/userServlet",{
				op:'reg',
				uname:uname,
				pwd:pwd
			},function(data){
				if( data>0 ){
					$(".colWarning").html("注册成功!跳转登录...");
					setTimeout(() => {
						changeBox('#loginBox','#registerBox');
						$(".colWarning").html("");
					}, 1000);
				}else{
					$(".colWarning").html("注册失败!");
				}
			});
		}
	}
}

//滑动验证
var resflag = false;	//默认验证通过的状态为否
function res(resId) {
	resflag  = false;	//验证通过的状态为否
	$("#resetBtn").click();$("#resetBtn2").click();
	console.log(parseFloat('1px'))
	var SlideVerifyPlug = window.slideVerifyPlug;
	var slideVerify = new SlideVerifyPlug(resId,{
		wrapWidth:'280',//设置 容器的宽度 ，默认为 350 ，也可不用设，你自己css 定义好也可以，插件里面会取一次这个 容器的宽度
		initText:'请按住滑块拖动到右边以通过验证',  //设置  初始的 显示文字
		sucessText:'验证通过',//设置 验证通过 显示的文字
		getSucessState:function(res){
			//当验证完成的时候 会 返回 res 值 true，只留了这个应该够用了 
			//console.log(res);
			resflag =  res;
		}
	});
	$("#resetBtn").on('click',function(){
		slideVerify.resetVerify();//可以重置 插件 回到初始状态 
	})
	$("#resetBtn2").on('click',function(){
		slideVerify.resetVerify();//可以重置 插件 回到初始状态 
	})
}


//阅读所有的点击事件
function readAll(cid){
	var flag1;
	var flag2;
	
	//同步请求
	$.ajaxSettings.async = false;
	//发送请求获取文章内容
	$.post("../blog/contentsServlet",{
		op:'readAll',
		cid:cid
	},function(data){    //若成功返回1
		console.log( data );
		flag1 = data;
	});
	$.post("../blog/commentsServlet",{
		op:'getComments',
		cid:cid
	},function(data){
		console.log( data );
	});
	
	//异步请求
	$.ajaxSettings.async = true;
	if( flag1>0 ){
		location.href = "showMain.jsp";
	}

}

//站内搜索
function mySerch() {
	var serchTxt = $("#serchTxt").val();
	console.log(serchTxt);
	$.post("../blog/contentsServlet",{
		op:'adminContentSerch',
		txt:serchTxt
	},function(data){
		console.log(data);
		if( data<=0 ){
			alert('没有找到您所查询的数据!');
		}else{
			location.href = "serched.jsp";
		}
	});
}

function bdSerch() {
	var bdserchTxt = $("#bdserchTxt").val();
	console.log(bdserchTxt);
	if(window.confirm('即将离开本网站!')){
		//alert("确定");
		location.href = "https://www.baidu.com/s?ie=UTF-8&wd="+bdserchTxt;
	}else{
		 //alert("取消");
		//return false;
	}
}
