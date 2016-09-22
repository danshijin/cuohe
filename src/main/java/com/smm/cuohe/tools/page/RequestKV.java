package com.smm.cuohe.tools.page;


import java.io.Serializable;


/**
 * @author zhangnan
 *
 */
public class RequestKV implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1359671272627376899L;
	private String name;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public RequestKV(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	
}
