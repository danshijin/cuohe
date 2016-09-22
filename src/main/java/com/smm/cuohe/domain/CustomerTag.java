package com.smm.cuohe.domain;

import java.io.Serializable;

/** 用户标签
 * @ClassName: Tag 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年12月15日 上午9:26:28  
 */
public class CustomerTag implements Serializable{
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String tagname;//标签名

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
}
