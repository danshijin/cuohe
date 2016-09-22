package com.smm.cuohe.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
	        'A', 'B', 'C', 'D', 'E', 'F' }; 
		/**
		 * 加密
		 * 
		 * @param inputText
		 * @return
		 */
		public static String e(String inputText) {
			return md5(inputText);
		}

		/**
		 * 二次加密，应该破解不了了吧？
		 * 
		 * @param inputText
		 * @return
		 */
		public static String md5AndSha(String inputText) {
			return sha(md5(inputText));
		}

		/**
		 * md5加密
		 * 
		 * @param inputText
		 * @return
		 */
		public static String md5(String inputText) {
			return encrypt(inputText, "md5");
		}

		/**
		 * sha加密
		 * 
		 * @param inputText
		 * @return
		 */
		public static String sha(String inputText) {
			return encrypt(inputText, "sha-1");
		}

		/**
		 * md5或�?sha-1加密
		 * 
		 * @param inputText
		 *            要加密的内容
		 * @param algorithmName
		 *            加密算法名称：md5或�?sha-1，不区分大小�?
		 * @return
		 */
		private static String encrypt(String inputText, String algorithmName) {
			if (inputText == null || "".equals(inputText.trim())) {
				throw new IllegalArgumentException("请输?");
			}
			if (algorithmName == null || "".equals(algorithmName.trim())) {
				algorithmName = "md5";
			}
			String encryptText = null;
			try {
				MessageDigest m = MessageDigest.getInstance(algorithmName);
				m.update(inputText.getBytes("UTF8"));
				byte s[] = m.digest();
				// m.digest(inputText.getBytes("UTF8"));
				return hex(s);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return encryptText;
		}

		/**
		 * 返回十六进制字符�?
		 * 
		 * @param arr
		 * @return
		 */
		private static String hex(byte[] arr) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; ++i) {
				sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		}
		
		private static String toHexString(byte[] b) {  
		    //String to  byte  
		    StringBuilder sb = new StringBuilder(b.length * 2);    
		    for (int i = 0; i < b.length; i++) {    
		        sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);    
		        sb.append(HEX_DIGITS[b[i] & 0x0f]);    
		    }    
		    return sb.toString();    
		}  
		
		/**MD5大写32位加�?
		 * @param s
		 * @return
		 */
		public static String mmd5(String s) {  
		    try {  
		        // Create MD5 Hash  
		        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");  
		        digest.update(s.getBytes());  
		        byte messageDigest[] = digest.digest();  
		                                  
		        return toHexString(messageDigest);  
		    } catch (NoSuchAlgorithmException e) {  
		        e.printStackTrace();  
		    }  
		                          
		    return "";  
		}  
		
}
