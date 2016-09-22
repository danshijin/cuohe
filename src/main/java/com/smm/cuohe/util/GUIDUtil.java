package com.smm.cuohe.util;

import java.util.UUID;

/**
 *  生成GUID 
 * @author haokang
 *
 */
public class GUIDUtil {
	public static String getGUID(){
		UUID uuid =UUID.randomUUID();
		String guid = uuid.toString();
		guid = guid.toUpperCase();
		return guid;
	}


}
