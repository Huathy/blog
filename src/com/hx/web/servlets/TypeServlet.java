package com.hx.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hx.bean.Type;
import com.hx.biz.TypeBiz;
import com.hx.biz.impl.TypeBizImpl;

@WebServlet("/typeServlet")
public class TypeServlet extends HttpServlet{
	
	private TypeBiz tb = new TypeBizImpl(); 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//因为已经在过滤器设置编码集,故无需考虑
		String op = req.getParameter("op");
		
		PrintWriter out = resp.getWriter();
		
		if( "getAllType".equals(op) ) {
			getAllType(req,resp,out);
		}else if( "updateType".equals(op) ) {
			updateType(req,resp,out);
		}else if( "addType".equals(op) ) {
			addType(req,resp,out);
		}
		
	}
	
	private void addType(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String tname = req.getParameter("tname");
		int result = tb.addType(tname);
		out.print(result);
	}

	//修改
	private void updateType(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String tname = req.getParameter("tname");
		int tid = Integer.parseInt( req.getParameter("tid") );
		int result = tb.updateType(tname, tid);
		List<Type> list = tb.getTypeInfo();
		req.getSession().setAttribute("types", list);
		out.print(result);
	}

	private void getAllType(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		List<Type> list = tb.getTypeInfo();
		
		req.getSession().setAttribute("types", list);
		
		out.print( list.size() );
	}
	
}
