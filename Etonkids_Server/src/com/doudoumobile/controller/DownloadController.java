package com.doudoumobile.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class DownloadController extends MultiActionController {
	
	private String downloadRootPath;
	
	public void setDownloadRootPath(String downloadRootPath) {
		this.downloadRootPath = downloadRootPath;
	}
	
	public void download(final HttpServletRequest request,	final HttpServletResponse response){
		try{
			String filePath = ServletRequestUtils.getStringParameter(request, "filePath" , "D:\\TDDOWNLOAD\\test.rmvb");
			filePath = downloadRootPath + filePath;
			response.setCharacterEncoding("GBK");  
	        String requestUrl = new String(URLDecoder.decode(request.getRequestURI(), "UTF-8"));  
	        System.out.println("请求地址" + requestUrl);  
	        // 获取文件  
	        System.out.println("文件路径：" + filePath);  
	        File file = new File(filePath);  
	        if (!file.exists()) {  
	            System.out.println("文件“" + file.getAbsolutePath() + "”不存在!");  
	            return;  
	        }  
	        OutputStream os = response.getOutputStream();  
	        long start = 0;  
	        String range = request.getHeader("range");  
	        if (range != null) {  
	            System.out.println("range=" + range);  
	            String rg = range.split("=")[1];  
	            start = Long.parseLong(rg.split("-")[0]);  
	            response.setStatus(206);  
	        }  
	        response.setContentType("application/octet-stream;charset=UTF-8");  
	        response.setHeader("Accept-Ranges", "bytes");  
	        response.setHeader("Content-Range", "bytes  " + start + "-" + (file.length() - 1) + "/" + file.length());  
	        response.setHeader("Content-Length", " " + file.length());  
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(file.getName().getBytes(), "ISO-8859-1") + "\"");  
	        InputStream is = new FileInputStream(file);  
	        System.out.println("start=" + start);  
	        is.skip(start);  
	        byte[] buffer = new byte[1024 * 64];  
	        int len = 0;  
	        while ((len = is.read(buffer, 0, buffer.length)) != -1) {  
	            os.write(buffer, 0, len);  
	        }  
	        os.flush();  
	        os.close();  
	        is.close();  
	        System.out.println("下载完成!"); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
