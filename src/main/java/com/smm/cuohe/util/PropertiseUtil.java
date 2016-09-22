package com.smm.cuohe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertiseUtil {
	private final static Logger logger = Logger.getLogger(PropertiseUtil.class);
	
	/**
	 * 获取指定配置文件的指定key值
	 * @param fileName 配置文件名称
	 * @param key
	 * @return
	 * @author 
	 */
	public static String getDataFromPropertiseFile(String fileName,String key){
		Properties prop = new Properties();  
		try {
			if(fileName == null || "".equals(fileName)){
				logger.debug("getDataFromPropertiseFile fileName is null");
				return null;
			}
			if(key == null || "".equals(key)){
				logger.debug("getDataFromPropertiseFile key is null");
				return null;
			}
			 InputStream input = new FileInputStream(new File(fileName));  
			 prop.load(input);
		} catch (Exception e) {
			logger.warn(fileName + "配置文件中不存在"+key);
			return null;
		}
		 return prop.getProperty(key).toString();
	}
	/**
	 * 读取字符串配置项
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getString(String fileName, String key) {
		String val = getDataFromPropertiseFile(fileName, key);
		return val;
	}
	/**
	 * 读取整形配置项
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static Integer getInteger(String fileName, String key) {
		String val = getDataFromPropertiseFile(fileName, key);
		try {
			Integer n = Integer.valueOf(val);
			return n;
		} catch(NumberFormatException e) {
			return null;
		}
	}
	/**
	 * 读取整形配置项
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static Long getLong(String fileName, String key) {
		String val = getDataFromPropertiseFile(fileName, key);
		try {
			Long n = Long.valueOf(val);
			return n;
		} catch(NumberFormatException e) {
			return null;
		}
	}
	/**
	 * 读取布尔型配置项
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static Boolean getBoolean(String fileName, String key) {
		String val = getDataFromPropertiseFile(fileName, key);
		if(val == null){
		    return null;
		}
		if (val.equalsIgnoreCase("true")) {
			return Boolean.valueOf(true);
		}
		if (val.equalsIgnoreCase("false")) {
			return Boolean.valueOf(false);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiseUtil.getDataFromPropertiseFile("jdbc", "url"));
	}
	
}
