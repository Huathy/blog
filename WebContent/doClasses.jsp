数据加载中....     请稍等....
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.getSession().setAttribute("classesBegin",0); %>
<%request.getSession().setAttribute("classesPage", 1); %>
${classContents}
<script src="js/jquery-1.12.4.min.js"></script>
<script>
	var flag1;
	var flag2;
	var href = location.href;
	var tid = href.split("tid=")[1].split("&")[0];
	var classIndex = href.split("index=")[1];
	
	//console.log( href );
	console.log( "id:"+tid +"-"+ "index:"+classIndex);

	if( typeof(tid)==undefined || tid=="" || tid==null || tid<0 ){
		location.href = "doIndex.jsp";
	}
	console.log(tid);
	$.ajaxSettings.async = false;

	$.post("/blog/typeServlet", {
		op : 'getAllType'
	}, function(data) {
		flag1 = data;
	});
	
	$.post("/blog/contentsServlet", {
		op : 'getContentsByClass',
		tid:tid,
		classIndex:classIndex
	}, function(data) {
		flag2 = data;
	});
	
	$.ajaxSettings.async = true;
	console.info(flag1 + "-" +flag2 );
	if (flag1 > 0 && flag2 >= 0) {
		location.href = "classes.jsp?tid="+tid;
	} else {
		location.href = "error.html"
	}
</script>