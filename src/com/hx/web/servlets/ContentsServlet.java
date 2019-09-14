package com.hx.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hx.bean.Contents;
import com.hx.biz.ContentsBiz;
import com.hx.biz.impl.ContentsBizImpl;
import com.hx.utils.SensitiveWord;

@WebServlet("/contentsServlet")
public class ContentsServlet extends HttpServlet{
	
	private ContentsBiz cb = new ContentsBizImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//因为已经在过滤器设置编码集,故无需考虑
		String op = req.getParameter("op");
		PrintWriter out = resp.getWriter();
		
		if( "getAllContent".equals(op) ) {		
			getAllContent(req,resp,out);			//获取所有的内容（除文章详细内容外）存放到session中
		}else if( "getContentByPage".equals(op) ) {
			getContentByPage(req,resp,out);			//根据页面
		}else if( "getContentsByClass".equals(op) ) {
			getContentsByClass(req,resp,out);		//根据类别显示的页面
		}else if( "getClassesPage".equals(op) ) {
			getClassesPage(req,resp,out);			//类别显示页面的分页
		}else if( "readAll".equals(op) ) {
			readAll(req,resp,out);					//阅读全文
		}else if( "changeAdminContentPage".equals(op) ) {
			changeAdminContentPage(req,resp,out);	//更改管理员页面
		}else if( "DelContent".equals(op) ) {
			DelContent(req,resp,out);				//管理员删除内容
		}else if( "addContent".equals(op) ) {
			addContent(req,resp,out);				//用户写博客添加内容
		}else if( "updateViews".equals(op) ) {
			updateViews(req,resp,out);				//用户写博客添加内容
		}else if( "adminContentSerch".equals(op) ) {
			adminContentSerch(req,resp,out);		//管理员搜索
		}else if( "updateContent".equals(op) ) {
			updateContent(req,resp,out);		//修改文章
		}
		
	}
	
	//修改文章
	private void updateContent(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		int cid = 0;
		if( req.getParameter("cid")!=null && req.getParameter("cid")!="" ) {
			cid = Integer.parseInt( req.getParameter("cid") );
		}
		int tid = 0;
		if( req.getParameter("tid")!=null && req.getParameter("tid")!="" ) {
			tid = Integer.parseInt( req.getParameter("tid") );
		}
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String desc = req.getParameter("desc");
		
		SensitiveWord sw = new SensitiveWord();
		
		int swr = sw.cheak(title) + sw.cheak(content) + sw.cheak(desc);
		
		if( swr>1 ) {
			out.print("<script>alert('请勿输入非法字符!不支持script脚本');</script>");
		}else {
			int result = cb.updateContent(cid,tid, title, desc, content);
			out.print( result );
		}
	}

	//管理员搜索
	private void adminContentSerch(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String txt = req.getParameter("txt");
		List<Map<String, String>> list = cb.adminContentSerch(txt);
		if( list==null ) {
			out.print(-1);
		}else {
			out.print(list.size());
			req.getSession().setAttribute("SerchContent", list);
		}
	}

	//阅读全文的点击就更新浏览人数,将 这个单独列出来发请求,即使不成功也不影响用户使用
	private void updateViews(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		int cid = 0;
		if( req.getParameter("cid")!=null && req.getParameter("cid")!="" ) {
			cid = Integer.parseInt( req.getParameter("cid") );
		}
		int result = cb.updateViews(cid);
		out.print( result );
	}

	//用户写博客添加内容
	private void addContent(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		int tid = 0;
		if( req.getParameter("tid")!=null && req.getParameter("tid")!="" ) {
			tid = Integer.parseInt( req.getParameter("tid") );
		}
		int uid = 0;
		if( req.getParameter("uid")!=null && req.getParameter("uid")!="" ) {
			uid = Integer.parseInt( req.getParameter("uid") );
		}
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String desc = req.getParameter("desc");
		
		SensitiveWord sw = new SensitiveWord();
		
		int swr = sw.cheak(title) + sw.cheak(content) + sw.cheak(desc);
		
		if( swr>1 ) {
			out.print("<script>alert('请勿输入非法字符!不支持script');</script>");
		}else {
			int result = cb.addContent(tid, uid, title, desc, content);
			out.print( result );
		}
	}
	
	//管理员删除内容
	private void DelContent(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		int cid = 0;	
		if( req.getParameter("cid") != null || req.getParameter("cid") != "" ) {
			cid = Integer.parseInt( req.getParameter("cid") );
		}
		int result = cb.AdminDelContent(cid);
		//List contents = (List) req.getSession().getAttribute("contents");
		
		
//		for( int i=0;i<contents.size();i++ ) {
//			System.out.println( contents.get(i) );
//		}
		getAllContent(req,resp,out);
		out.print(result);
		
	}

	//更改管理员内容页面的请求响应
	private void changeAdminContentPage(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		String page = req.getParameter("page");				//获取上下跳页操作数
		int counts = (int) req.getSession().getAttribute("counts");		//获取总条数
		int pages = counts%10==0 ? counts/10 : counts/10+1;			//获取总页数
		int contBegin = 0;	//获取循环开始
		if( req.getParameter("contBegin") != null || req.getParameter("contBegin") != ""  ) {
			contBegin = Integer.parseInt( req.getParameter("contBegin") );
		}
		int contPage = 1 ;	//获取当前页
		if( req.getParameter("contPage") != null || req.getParameter("contPage") != "" ) {
			contPage = Integer.parseInt( req.getParameter("contPage") );
		}
		
		if( "-1".equals(page) ) {	//上一页
			contPage--;
			contBegin -= 10;
		}else if( "-2".equals(page) ) {	//下一页
			contPage++;
			contBegin += 10;
		}else {
			contPage = Integer.parseInt( page );
			contBegin = 10*(contPage-1);
		}
		contPage = Math.max(0, Math.min(contPage, (pages-1) ) );
		contBegin = Math.max(0, Math.min(contBegin, (counts-10) ) );
		
		req.getSession().setAttribute("contPage", contPage);
		req.getSession().setAttribute("contBegin", contBegin);
		
		out.print( 1 );
	}

	//阅读全文的请求响应
	private void readAll(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		if( req.getParameter("cid")==null ) {
			out.print("<script> location.href='index.jsp' </script>");
		}else {
			int cid = Integer.parseInt( req.getParameter("cid") );
			Contents content = cb.readAll(cid);
			req.getSession().setAttribute("mainContent", content);
			out.print( 1 );
		}
		
	}
	
	//分类页面的跳转页面请求响应
	private void getClassesPage(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String change =req.getParameter("change") ;
		Integer begin =(Integer) req.getSession().getAttribute("classesBegin");
		Integer pages = (Integer) req.getSession().getAttribute("pages");
		Integer counts = (Integer) req.getSession().getAttribute("classContentsSize");
		Integer page = (Integer) req.getSession().getAttribute("page");
		
		if( "1".equals(change) ) {
			begin += 5;
			page++;
		}else {
			begin -= 5;
			page--;
		}

		begin = Math.max(0, Math.min(begin, counts-5));	//begin最小从0开始
		page = Math.max(1, Math.min(page, pages));

		req.getSession().setAttribute("page", page);
		req.getSession().setAttribute("classesBegin", begin);
		out.print( begin );
	}
	
	//分类页面的数据请求响应
	private void getContentsByClass(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String cindex =  req.getParameter("classIndex");
		req.getSession().setAttribute("classIndex",cindex );
		
		String tid = "1";
		if( req.getSession().getAttribute("tid")!=null ) {
			tid =  (String)req.getParameter("tid") ;
		}
		req.getSession().setAttribute("tid", tid);
		
		List classContents = new ArrayList();	//定义集合,用于存储新的按分类查到的数据
		List<Map<String,String>> list = null;
		if( req.getSession().getAttribute("contents")==null ) {	//如果session中的content为空,那么跳转网页重新发送请求以获得数据
			out.print("<script>location.href='../index.jsp'</script>");
		}else {
			list = (List) req.getSession().getAttribute("contents");
			for( int i=0;i<list.size();i++ ) {
				String typeid = list.get(i).get("tid");

				if( typeid.equals(tid) ) {
					classContents.add(list.get(i));
				}
			}
		}
		
		//System.out.println( classContents.size() +"-"+ classContents.size() / 5 );
		int pages = classContents.size() % 5==0 ?  classContents.size() / 5 : classContents.size() / 5 + 1;
		req.getSession().setAttribute("classPages", pages);
		if( pages<=0 ) {
			req.getSession().setAttribute("classPage", 0);
		}else {
			req.getSession().setAttribute("classPage", 1);
		}
		req.getSession().setAttribute("classContents", classContents);		
		req.getSession().setAttribute("classContentsSize", classContents.size());
		//List contentlist = (List) req.getSession().getAttribute("contents");
		
		//System.out.println(classContents + "-"+ pages + "-"+ contentlist);
		out.print( classContents.size() );
	}
	
	//首页的上下页面跳转		//分类页面的上下页跳转控制
	private void getContentByPage(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String change =req.getParameter("change") ;
		Integer begin =(Integer) req.getSession().getAttribute("begin");	//获取开始索引
		Integer pages = (Integer) req.getSession().getAttribute("pages");	//获取总页数
		Integer counts = (Integer) req.getSession().getAttribute("classContentsSize");	//获取总条数
		Integer page = (Integer) req.getSession().getAttribute("page");		//获取当前页数
		
		if( "1".equals(change) ) {
			begin += 5;
			page++;
		}else {
			begin -= 5;
			page--;
		}
		
		begin = Math.max(0, Math.min(begin, counts-5));
		page = Math.max(1, Math.min(page, pages));
		req.getSession().setAttribute("page", page);
		req.getSession().setAttribute("begin", begin);
		out.print( begin );
	}
	
	//获取主页所有数据内容并存到session中
	private void getAllContent(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		List<Map<String, String>> list = cb.getAllContent();
		req.getSession().setAttribute("contents", list);
		req.getSession().setAttribute("pages", list.size()%5==0 ? list.size()/5 : list.size()/5+1 );
		req.getSession().setAttribute("counts", list.size());
		
		out.print( list.size() );
	}
	
}
