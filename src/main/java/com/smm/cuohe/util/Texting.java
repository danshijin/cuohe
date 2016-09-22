package com.smm.cuohe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class Texting {
	
	
	
	@Value("#{ch['sendSMS.URL']}")
	 private String sendSMS;
	/**
	 * 发短信  
	 *  time 时间
	 *  message 内容
	 *  mobiles 手机号
	 **/
	/**
	 * @param time
	 * @param message
	 * @param mobiles
	 * @return
	 */
	public  String sendsmm(String time,String message,String mobiles) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果
		try {

			 String  regEx="[^0-9]";   
			 Pattern p = Pattern.compile(regEx);   
			 Matcher m = p.matcher(time);   

			String url = sendSMS;
			String paras = "command=SMS20_REQUEST&poster=Cuohe&time="+ m.replaceAll("").trim() + "000&message="+message+"&mobiles="+mobiles;
			// 创建url
			URL realurl = new URL(url);
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) realurl
					.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(3000);
			connection.setDoOutput(Boolean.TRUE);
			connection.getOutputStream().write(paras.getBytes());
			// 建立连接
			connection.connect();

			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
	
	public static String checkResultCode(String msg){
		
		String resultMsg = "成功";
		if(msg != null && !msg.equals("")){
			String[] arr = msg.split("&");
			String status = arr[1].split("=")[1];
			String code = arr[2].split("=")[1];
			if(status.equalsIgnoreCase("ACCEPTD") && code.equals("000")){
				resultMsg = "成功";
			} else if (status.equalsIgnoreCase("REJECTD")){
				if(code.equals("101")){
					resultMsg ="缺少command命令，或者命令不正确";
				} else if (code.equals("103")){
					resultMsg ="发送时间的参数不正确";
				} else if (code.equals("104")){
					resultMsg ="发送内容为空或者大于52个字符";
				} else if (code.equals("105")){
					resultMsg ="手机号码为空或者格式不正确";
				}
			}
		}
		return resultMsg;
	}
	
}
