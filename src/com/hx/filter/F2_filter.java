package com.hx.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hx.bean.User;

@WebFilter(filterName="Filter2",urlPatterns="/admin/*")
public class F2_filter implements Filter {
//tomcat 空指针异常是由于urlPatterns路径错误，将/admin/*写成了admin/*
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//没有登录就跳转主页
		User user = (User) request.getSession().getAttribute("user");
		if( user==null ) {
			PrintWriter out =response.getWriter();
			out.print("<script>location.href='../index.jsp'</script>");
		}else {
			//有值继续
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
