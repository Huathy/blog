package com.hx.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hx.bean.Comments;
import com.hx.biz.CommentsBiz;
import com.hx.biz.impl.CommentsBizImpl;
import com.hx.utils.SensitiveWord;

@WebServlet("/commentsServlet")
public class CommentsServlet extends HttpServlet {
	
	CommentsBiz cob = new CommentsBizImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		String op = req.getParameter("op");
		if( "getCommentsCount".equals(op) ) {
			getCommentsCount(req,resp,out);		//��ȡ��ҳ������
		}else if( "getComments".equals(op) ) {
			getComments(req,resp,out);		//����ҳ���ȡ��������
		}else if( "addComment".equals(op) ) {
			addComment(req,resp,out);		//����ҳ��ķ�������
		}else if( "getAllCmtByPage".equals(op) ) {
			getAllCmtByPage(req,resp,out);		//����Աҳ���ȡ��������
		}else if( "AdminDelComment".equals(op) ) {
			AdminDelComment(req,resp,out);		//����Աҳ��ɾ������
		}else if( "adminCommentSerch".equals(op) ) {
			adminCommentSerch(req,resp,out);		//����Աҳ���������۹���
		}
		
	}

	private void adminCommentSerch(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		String txt = req.getParameter("txt");
		List<Comments> list = cob.adminCommentSerch(txt);
		if(list.size()>0) {
			out.print(list);
			req.getSession().setAttribute("adminCmts", list);
		}else {
			out.print(-1);
		}
	}

	private void AdminDelComment(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		int comid = 0;
		if( req.getParameter("comid") != null && req.getParameter("comid") != "" ) {
			comid = Integer.parseInt( req.getParameter("comid") );
		}
		int result = cob.AdminDelComment(comid);
		out.print(result);
		
	}

	private void getAllCmtByPage(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		int adcmtcount = cob.getAllCmtCount();		//����������
		int adcmtpages = adcmtcount%10==0 ? adcmtcount/10 : adcmtcount/10+1;	//������ҳ��
		int adcmtPage = 1;
		if( req.getSession().getAttribute("adcmtPage") != null  &&  req.getSession().getAttribute("adcmtPage") !="" ) {
			adcmtPage = Integer.parseInt(  req.getSession().getAttribute("adcmtPage").toString() );
		}
		
		String page = req.getParameter("page");	//��ȡ��ת����ҳ��
		if( req.getParameter("page") != null && req.getParameter("page") != "" ) {
			if( "-1".equals(page) ) {	//��һҳ
				adcmtPage--;
			}else if( "-2".equals(page) ) {	//��һҳ
				adcmtPage++;
			}else {	
				adcmtPage = Integer.parseInt( page );
			}
		}
		adcmtPage = Math.max(0, Math.min( adcmtPage, adcmtpages ));
		
		int pagesize = 10;		//��ȡÿҳչʾ����,Ĭ��10��
		if( req.getParameter("pagesize") != null && req.getParameter("pagesize") != "" ) {
			pagesize = Integer.parseInt( req.getParameter("pagesize") );
		}
		pagesize = Math.max(5, pagesize);//��������,�����
		
		List<Comments> list = cob.getAllCmtByPage(adcmtPage,pagesize);
		
		req.getSession().setAttribute("adcmtcount", adcmtcount);
		req.getSession().setAttribute("adcmtpages", adcmtpages);
		req.getSession().setAttribute("adminCmts", list);
		req.getSession().setAttribute("adcmtPage", adcmtPage);
		
		out.print( list.size() );
		
	}

	private void addComment(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		int cid = 1;
		if( req.getParameter("cid")!=null ) {
			cid = Integer.parseInt( req.getParameter("cid") );
		}
		int uid = 1;
		if( req.getParameter("uid") != null  ) {
			uid = Integer.parseInt( req.getParameter("uid") );
		}
		String text = req.getParameter("text");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = sdf.format(date);
		
		SensitiveWord sw = new SensitiveWord();
		int swr = sw.cheak(text);
		if( swr>0 ) {
			out.print("<script>alert('�������ۺ��Ƿ��ַ�!��ע����������!');</script>");
		}else {
			int result = cob.addComment(cid, uid, text,time);
			if( result<=0 ) {
				out.print(-1);
			}else {
				out.print( time );
			}
		}
	}

	private void getComments(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		int cid = 0;
		if( req.getParameter("cid")!=null && req.getParameter("cid")!="" ) {
			cid = Integer.parseInt( req.getParameter("cid") );
		}
		List<Comments> list = cob.getComments(cid);
		req.getSession().setAttribute("comments", list);
		out.print( list );
	}

	private void getCommentsCount(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		List list = cob.getCommentsCount();
		req.getSession().setAttribute("cmtsCount", list);
		out.print( list );
	}

}
