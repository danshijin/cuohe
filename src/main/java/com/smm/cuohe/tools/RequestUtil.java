package com.smm.cuohe.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	
	/***
	 * Get request query string
	 * @param request
	 * @return   byte[]
	 * @throws IOException 
	 */
	public static String getRequestStr(HttpServletRequest request) throws IOException{
		
		int len = 1000; // buffer的初始长度，必须>1，否则无法扩展容量
		int readLen = 0;
		int num = 0;
		byte[] buffer = new byte[len];
		
		InputStream is = request.getInputStream();
		while((num = is.read(buffer, readLen, buffer.length - readLen)) != -1){
			
			buffer = Arrays.copyOf(buffer, buffer.length + (buffer.length >> 1));  //扩展容量
			readLen += num;
			
		}
		return new String(buffer,0, readLen, Charset.defaultCharset());
	}
}
