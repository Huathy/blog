package com.hx.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class UploadUtil {
	//规定上传路径
	private String path = "upload";
	//规定上传格式
	private String allow = "jpg,png,gif,bmp,jepg";
	//总大小
	private int totalSize = 1024*1024*20;		//20M
	//单个大小
	private int singSize = 1024*1024*2;
	
	//文件上传
	public Map<String,String> uploadFiles(PageContext pagecontext) {
		Map<String,String> map = new HashMap<String,String>();
		//实例化SmartUpload对象
		SmartUpload su = new SmartUpload();
		try {
			//初始化		网页上下文环境
			su.initialize(pagecontext);
			//允许上传的文件类型
			su.setAllowedFilesList(allow);
			//最大大小
			su.setTotalMaxFileSize(totalSize);
			//单个文件大小
			su.setMaxFileSize(singSize);
			//设置编码集
			su.setCharset("utf-8");
			//开始上传
			su.upload();
			
			//从SmartUpload中得到request
			Request request = su.getRequest();
			Enumeration et = request.getParameterNames();
			//循环得到键
			while( et.hasMoreElements() ) {
				String temp = String.valueOf( et.nextElement() );
				map.put( temp, request.getParameter(temp) );
			}
			//用于存放上传的文件名
			String str = "";
			//获得上传的文件
			if( su.getFiles()!=null && su.getFiles().getCount()>0 ) {
				//得到上传的文件
				Files fs = su.getFiles();
				for( int i=0; i<fs.getCount(); i++ ) {
					File file = fs.getFile(i);
					String filepath = path+"/";
					filepath += new Date().getTime() +"." +file.getFileExt();
					//将文件名拼接
					str += filepath + ",";
					//文件另存为
					file.saveAs(filepath,SmartUpload.SAVE_VIRTUAL);
				}
				//去掉最后的标点符号,
				str = str.substring(0,str.length()-1);
				//存起来
				map.put("pic",str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//构造函数
	public UploadUtil() {
		super();
	}
	public UploadUtil(String path, String allow, int totalSize, int singSize) {
		super();
		this.path = path;
		this.allow = allow;
		this.totalSize = totalSize;
		this.singSize = singSize;
	}
	
}
