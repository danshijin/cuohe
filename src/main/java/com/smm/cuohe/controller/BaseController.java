package com.smm.cuohe.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;

/**
 * controller 基础类
 * 
 * @author youxiaoshuang
 * 
 */
@Controller
public class BaseController {
	@Autowired
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	@Autowired
	private ServletContext servletContext;
	private static Logger logger = Logger.getLogger(BaseController.class
			.getName());

	@ModelAttribute
	public void setReqAndRes(HttpServletResponse response) {
		this.response = response;
		this.session = request.getSession();
		String path = request.getContextPath();
		this.session.setAttribute("ctx", this.request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/");
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return session;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getRealyPath(String path) {
		return servletContext.getRealPath(path);
	}

	// 输出字符串
	public void outString(String str) {
		try {
			this.response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = getResponse().getWriter();
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 输出json
	public void outJsonString(String jsonString) {
		try {
			this.response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = this.response.getWriter();
			logger.info("####JSON String is:" + jsonString);
			writer.write(jsonString);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 输出对象
	public void outObj(Object obj) {
		String json = JSON.toJSONStringWithDateFormat(obj,
				"yyyy-MM-dd HH:mm:ss");
		logger.info("####JSON String is:" + json);
		outJsonString(json);

	}

	// 获取参数
	public String getRequestParam(String paramName) {
		String param = this.request.getParameter(paramName);
		if (StringUtils.isNotEmpty(param)) {
			param = param.trim();
		}
		return param;
	}

	/**
	 * request parameter取得
	 * URLDecoder.decode(req.getParameter("person"),"UTF-8");
	 * 参数中含有汉字,日期,全角字符的时候使用
	 */
	public String getRequestParamURLDecoder(String paramName) {
		String requestParamter = getRequestParam(paramName);
		requestParamter.replaceAll("\"\"", "''");
		if (StringUtils.isNotEmpty(requestParamter)) {
			try {
				requestParamter = URLDecoder.decode(requestParamter, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}// End try...catch
		}// End if
		return requestParamter;
	}// End Method

	/**
	 * 生成唯一的uuid存入session并返回 重复提交功能
	 * 
	 * @return
	 */
	public String setToken() {
		UUID uuid = UUID.randomUUID();
		session.setAttribute(uuid.toString(), uuid);
		return uuid.toString();
	}

	/**
	 * 验证是否为重复提交 不重复为true 重复为false
	 * 
	 * @return
	 */
	public boolean checkToken() {
		String token = getRequestParam("token");
		Object uObj = session.getAttribute(token);
		if (uObj != null) {
			this.session.removeAttribute(token);
			return true;
		} else {
			return false;
		}
	}
}