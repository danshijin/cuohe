package com.smm.cuohe.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 远程接口请求辅助类 Created by zhenghao on 2015/9/15.
 */
public class URLRequest {

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param parameters
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> parameters)
			throws Exception {

		url = url + "?1=1";

		// 处理请求参数
		for (Map.Entry<String, String> entry : parameters.entrySet()) {

			url += "&" + entry.getKey() + "="
					+ URLEncoder.encode(entry.getValue(), "UTF-8");

		}

		// 发送请求
		URL remote = new URL(url);

		HttpURLConnection connection = (HttpURLConnection) remote
				.openConnection();

		connection.setRequestProperty("contentType", "UTF-8");

		// 设置超时时间
		connection.setConnectTimeout(5 * 1000);

		// 设置请求方式
		connection.setRequestMethod("GET");

		// 接收返回值
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));
		String line;

		String result = "";

		while ((line = in.readLine()) != null) {
			result += line;
		}

		return result;

	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> parameters)
			throws Exception {

		URL remote = new URL(url);

		HttpURLConnection connection = (HttpURLConnection) remote
				.openConnection();

		connection.setRequestProperty("contentType", "UTF-8");

		connection.setRequestMethod("POST");// 提交模式
		// 设置超时时间
		connection.setConnectTimeout(5 * 1000);
		
		connection.setReadTimeout(300000);

		connection.setDoOutput(true);// 是否输入参数

		StringBuilder params = new StringBuilder();

		params.append("1=1");

		// 处理请求参数
		for (Map.Entry<String, String> entry : parameters.entrySet()) {

			String temp = "&" + entry.getKey() + "="
					+ URLEncoder.encode(entry.getValue(), "UTF-8");

			params.append(temp);

		}

		byte[] bypes = params.toString().getBytes();

		connection.getOutputStream().write(bypes);// 输入参数

		// 读取返回值
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));

		String line;

		String result = "";

		while ((line = in.readLine()) != null) {

			result += line;
		}

		return result;

	}
	
	/**
	 * 通用方法
	 * @param params
	 * @param Url
	 * @return
	 * @throws Exception 
	 */
	public static JSONObject getPost(Map<String,String> params,String Url) throws Exception{
		JSONObject result = null;
		String result_str = "";
			result_str = URLRequest.get(Url, params);
			result = JSONObject.fromObject(result_str);
			System.out.println(result_str);
		return result;
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();

//		params.put("poolId", "513");
//		params.put("poolType", "1");
//		params.put("price", "112");
//		params.put("priceType", "0");
//		params.put("account", "18922222223");
//		params.put("itemId", "26276");
//		params.put("customerName", "小小强");
////		params.put("telephone", "17721027273");
//		JSONObject result = null;
//
//		String result_str = "";
//		try {
//			result_str = URLRequest.post(
//					"http://172.16.9.62:8080/cuohe/mallPoolAPI/addCounterOffer.do", params);
////			result = JSONObject.fromObject(result_str);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		params.put("mallid", "3725");
//		params.put("deliverytime", "1449478946");
//		params.put("warehouseid", "1");
//		params.put("receipttype", "2");
//		params.put("overflow", "0.2");
//		params.put("paytype", "2");
//		params.put("delivery", "2");
//		params.put("poolId", "606");
//		params.put("poolType", "1");
//		params.put("pricetype", "0");
//		params.put("catid", "26280");
//		params.put("title", "上海有色网-孙雨薇（安全支付）买盘发布测试");
//		params.put("introduce", StringUtil.doEncoder("上海有色网-孙雨薇（安全支付）买盘发布测试"));
//		params.put("amount", "13578436784");
//		params.put("price", "100");
//		params.put("areaid", "1");
//		params.put("unit", StringUtil.doEncoder("吨"));
//		params.put("name", "13578436784");
//		params.put("poolId", "1");
//		params.put("poolType", "1");
//		params.put("mallUserName", "huangfengfortest");
//		String a = String.valueOf(new Date().getTime());
//		params.put("date", a);
//		params.put("md5", MD5Util.md5("huangfengfortest"+a));
		
		
//		params.put("price", "1");
//		params.put("type", "2");
//		params.put("amount", "3");
//		{mallid=[4205], price=[10000.0], type=[2], amount=[11.0]}
		
		params.put("mallid", "4312");
		params.put("count", "1000");
		params.put("price", "9000");
		params.put("warehouseid", "86");
		params.put("type", "2");
		params.put("buyer", "test01");
		params.put("seller", "17721027272");
		JSONObject result = null;
		String result_str = "";
		try {
			result_str = URLRequest.post("http://testmall.smm.cn/Interface/order", params);
//			result = JSONObject.fromObject(result_str);
			System.out.println(result_str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(result.get("data"));
		
		
		
		
	}
}
