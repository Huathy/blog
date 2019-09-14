package com.hx.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.hx.bean.User;
import com.hx.biz.UserBiz;
import com.hx.biz.impl.UserBizImpl;
import com.hx.dao.DBHelper;
import com.hx.utils.UploadUtil;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet{
	private UserBiz ub = new UserBizImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String op = req.getParameter("op");
		PrintWriter out  = resp.getWriter();
		if( "login".equals(op) ) {
			login(req,resp,out);
		}else if( "reg".equals(op) ) {
			reg(req,resp,out);
		}else if( "logout".equals(op) ) {
			logout(req,resp,out);
		}else if( "getAllUser".equals(op) ) {
			getAllUser(req,resp,out);
		}else if( "userModify".equals(op) ) {
			userModify(req,resp,out);
		}
	}
	
	//�û��������ĵ���Ϣ�޸�
	private void userModify(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		
//		UploadUtil uu = new UploadUtil();
//		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, req, resp, null, true, 8192, true);
//		Map<String,String> map = uu.uploadFiles(  pageContext );
//		String pic = map.get("pic");
		String pic = null;
		
		int uid = 0;
		if( req.getParameter("uid")!=null && req.getParameter("uid")!="" ) {
			uid = Integer.parseInt( req.getParameter("uid") );
		}
		String uname = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		String email = req.getParameter("email");
		String headimg = req.getParameter("headimg");	//ͷ��
		
		int result = ub.userModify(uid, uname, pwd, email, pic);
		out.print(result);
	}
	
	//��ҳ��ѯ:�ص�������Ҫ�õ���ǰҳ��,ÿһҳ������
	private void getAllUser(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		List<User> list = ub.getAllUser();
		
		//�õ�ÿһҳ�м�������
		int size = 7;	
		if( req.getParameter("size")!=null ) {
			size = Integer.parseInt( req.getParameter("size") );
		}
		//�õ���ҳ��
		int totalpage = list.size()%size==0 ? list.size()/size : list.size()/size+1;	
		//ǿ�л�ȡ��ǰҳ��
		String mypage = req.getParameter("page");
		
		//��ȡ���ı�ǰ��ҳ��
		int page = 0;
		if( req.getSession().getAttribute("userspPage")!=null ) {
			page = (int)req.getSession().getAttribute("userspPage");
		}
		
		if( mypage==null || "".equals(mypage) ) {
			page = 1;    //default page 1
		}else if( "-1".equals(mypage) ) {
			//��һҳ
			page--;
		}else if( "-2".equals(mypage) ) {
			//��һҳ
			page++;
		}else {
			page = Integer.parseInt( mypage );
		}
		
		//����ҳ��
		page = Math.min(page, totalpage);	//��֤���ҳ����������ҳ��	//�Ƚ����ߵõ����е���Сֵ
		page = Math.max(page, 1);		//��֤��Сҳ����С��1	//�Ƚ����ߵõ����е����ֵ
		//like
//		if(page<=0){
//			page=1;
//		}
//		if(page>totalpage) {
//			page=totalpage;
//		}
		
		
		req.getSession().setAttribute("usersTotal", list.size());
		//��Java�����ͳ������ͻ�������
		req.getSession().setAttribute("usersPages", totalpage );
		req.getSession().setAttribute("userspPage", page);
		List<User> myuser = ub.getAllUserByPage(page, size);
		req.getSession().setAttribute("users", myuser);

		out.print(1);
	}
	
	//�˳���¼
	private void logout(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		req.getSession().removeAttribute("user");
		out.print(0);
	}
	
	//ע��
	private void reg(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String uname = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		
		int result = ub.reg(uname,pwd);
		out.print( result );
	}
	
	//��¼
	private void login(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String uname = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		
		User user = ub.login(uname,pwd);
		if( user==null ) {
			out.print(0);
		}else {
			req.getSession().setAttribute("user", user);
			out.print(1);
		}
	}

}
