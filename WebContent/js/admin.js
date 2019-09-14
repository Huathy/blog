/**
 * 存放管理页面都需要用到的js函数使用外部引入
 */

//跳转到内容管理content页面
//跳转到内容管理页面
function toContent() {
	location.href = "content.jsp";
}
//跳转到评论管理页面
function toComment() {
	$.post("../commentsServlet",{
		op:'getAllCmtByPage',
		page:1,   //第一次进网页默认第一页
		pagesize:10    //每页展示10条数据
	},function( data ){
		location.href = "comment.jsp";
	});
}

//跳转到users.jsp页面
function toUsers(){
	$.post("../userServlet",{
		op:'getAllUser',
		page:'1',	//默认第一页
		size:'7'	//每页7条数据
	},function( data ){
		if( data==1 ){
			location.href = "users.jsp";
		}else{
			alert("网络错误!");
		}
	});
}
//跳转分类首页type_index.jsp
function toTypeIndex(){
	$.post("../typeServlet",{
		op:'getAllType'
	},function( data ){
		if( data>0 ){
			location.href = "type_index.jsp";
		}else{
			alert("网络错误!");
		}
	});
}
//退出登录
function logout() {
	console.log(66);
	$.post("/blog/userServlet",{
		op:'logout'
	},function(data){
		location.reload();
	});
}