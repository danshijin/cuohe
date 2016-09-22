package com.smm.cuohe.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
/**
 * 
 * @author zengshihua
 *
 */
public class StringUtil {

	/**
	 * 对字符串进行解码
	 * 
	 * @param coder
	 * @return
	 */
	public static String doDecoder(String coder) {
		String ret = "";
		try {
			ret = URLDecoder.decode(coder, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 对字符串进行编码
	 * 
	 * @param coder
	 * @return
	 */
	public static String doEncoder(String coder) {
		String ret = "";
		try {
			ret = URLEncoder.encode(coder, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 按照length指定长度分割字符串
	 * 
	 * @param list
	 * @param length
	 * @return
	 */
	public static ArrayList<String> doSplit(String[] list, int length) {
		ArrayList<String> _retList = new ArrayList<String>();
		String _newStr = "";
		for (int i = 0; i < list.length; i++) {
			_newStr += list[i] + ";";
			if (_newStr.length() >= length) {
				_retList.add(_newStr);
				_newStr = "";
			} else {
				if (i == list.length - 1) {
					_retList.add(_newStr);
					_newStr = "";
				}
			}
		}
		return _retList;
	}

	public static String doConvertISOToCode(String param, String code) {
		String ret = "";
		if(code.equals("")){
			code="UTF-8";
		}
		try {
			ret = new String(param.getBytes("ISO-8859-1"), code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String doConvertUTFToCode(String param, String code) {
		String ret = "";
		if(code.equals("")){
			code="ISO-8859-1";
		}
		try {
			ret = new String(param.getBytes("UTF-8"), code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
