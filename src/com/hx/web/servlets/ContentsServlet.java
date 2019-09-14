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
		//��Ϊ�Ѿ��ڹ��������ñ��뼯,�����迼��
		String op = req.getParameter("op");
		PrintWriter out = resp.getWriter();
		
		if( "getAllContent".equals(op) ) {		
			getAllContent(req,resp,out);			//��ȡ���е����ݣ���������ϸ�����⣩��ŵ�session��
		}else if( "getContentByPage".equals(op) ) {
			getContentByPage(req,resp,out);			//����ҳ��
		}else if( "getContentsByClass".equals(op) ) {
			getContentsByClass(req,resp,out);		//���������ʾ��ҳ��
		}else if( "getClassesPage".equals(op) ) {
			getClassesPage(req,resp,out);			//�����ʾҳ��ķ�ҳ
		}else if( "readAll".equals(op) ) {
			readAll(req,resp,out);					//�Ķ�ȫ��
		}else if( "changeAdminContentPage".equals(op) ) {
			changeAdminContentPage(req,resp,out);	//���Ĺ���Աҳ��
		}else if( "DelContent".equals(op) ) {
			DelContent(req,resp,out);				//����Աɾ������
		}else if( "addContent".equals(op) ) {
			addContent(req,resp,out);				//�û�д�����������
		}else if( "updateViews".equals(op) ) {
			updateViews(req,resp,out);				//�û�д�����������
		}else if( "adminContentSerch".equals(op) ) {
			adminContentSerch(req,resp,out);		//����Ա����
		}else if( "updateContent".equals(op) ) {
			updateContent(req,resp,out);		//�޸�����
		}
		
	}
	
	//�޸�����
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
			out.print("<script>alert('��������Ƿ��ַ�!��֧��script�ű�');</script>");
		}else {
			int result = cb.updateContent(cid,tid, title, desc, content);
			out.print( result );
		}
	}

	//����Ա����
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

	//�Ķ�ȫ�ĵĵ���͸����������,�� ��������г���������,��ʹ���ɹ�Ҳ��Ӱ���û�ʹ��
	private void updateViews(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		int cid = 0;
		if( req.getParameter("cid")!=null && req.getParameter("cid")!="" ) {
			cid = Integer.parseInt( req.getParameter("cid") );
		}
		int result = cb.updateViews(cid);
		out.print( result );
	}

	//�û�д�����������
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
			out.print("<script>alert('��������Ƿ��ַ�!��֧��script');</script>");
		}else {
			int result = cb.addContent(tid, uid, title, desc, content);
			out.print( result );
		}
	}
	
	//����Աɾ������
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

	//���Ĺ���Ա����ҳ���������Ӧ
	private void changeAdminContentPage(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		
		String page = req.getParameter("page");				//��ȡ������ҳ������
		int counts = (int) req.getSession().getAttribute("counts");		//��ȡ������
		int pages = counts%10==0 ? counts/10 : counts/10+1;			//��ȡ��ҳ��
		int contBegin = 0;	//��ȡѭ����ʼ
		if( req.getParameter("contBegin") != null || req.getParameter("contBegin") != ""  ) {
			contBegin = Integer.parseInt( req.getParameter("contBegin") );
		}
		int contPage = 1 ;	//��ȡ��ǰҳ
		if( req.getParameter("contPage") != null || req.getParameter("contPage") != "" ) {
			contPage = Integer.parseInt( req.getParameter("contPage") );
		}
		
		if( "-1".equals(page) ) {	//��һҳ
			contPage--;
			contBegin -= 10;
		}else if( "-2".equals(page) ) {	//��һҳ
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

	//�Ķ�ȫ�ĵ�������Ӧ
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
	
	//����ҳ�����תҳ��������Ӧ
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

		begin = Math.max(0, Math.min(begin, counts-5));	//begin��С��0��ʼ
		page = Math.max(1, Math.min(page, pages));

		req.getSession().setAttribute("page", page);
		req.getSession().setAttribute("classesBegin", begin);
		out.print( begin );
	}
	
	//����ҳ�������������Ӧ
	private void getContentsByClass(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String cindex =  req.getParameter("classIndex");
		req.getSession().setAttribute("classIndex",cindex );
		
		String tid = "1";
		if( req.getSession().getAttribute("tid")!=null ) {
			tid =  (String)req.getParameter("tid") ;
		}
		req.getSession().setAttribute("tid", tid);
		
		List classContents = new ArrayList();	//���弯��,���ڴ洢�µİ�����鵽������
		List<Map<String,String>> list = null;
		if( req.getSession().getAttribute("contents")==null ) {	//���session�е�contentΪ��,��ô��ת��ҳ���·��������Ի������
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
	
	//��ҳ������ҳ����ת		//����ҳ�������ҳ��ת����
	private void getContentByPage(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		String change =req.getParameter("change") ;
		Integer begin =(Integer) req.getSession().getAttribute("begin");	//��ȡ��ʼ����
		Integer pages = (Integer) req.getSession().getAttribute("pages");	//��ȡ��ҳ��
		Integer counts = (Integer) req.getSession().getAttribute("classContentsSize");	//��ȡ������
		Integer page = (Integer) req.getSession().getAttribute("page");		//��ȡ��ǰҳ��
		
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
	
	//��ȡ��ҳ�����������ݲ��浽session��
	private void getAllContent(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
		List<Map<String, String>> list = cb.getAllContent();
		req.getSession().setAttribute("contents", list);
		req.getSession().setAttribute("pages", list.size()%5==0 ? list.size()/5 : list.size()/5+1 );
		req.getSession().setAttribute("counts", list.size());
		
		out.print( list.size() );
	}
	
}
