package com.smm.cuohe.util;

import org.apache.commons.lang.StringUtils;

public class Json implements java.io.Serializable {

	private static final long serialVersionUID = 8805003578410202527L;

//	private boolean success = false;


//	private String value = StringUtils.EMPTY;
//
//	private String msg = StringUtils.EMPTY;
//	
//	private Integer state = null;
	
	private String returnCode = StringUtils.EMPTY;
	
	private String returnMsg = StringUtils.EMPTY;

	private Object data = null;
	
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

//	public boolean isSuccess() {
//		return success;
//	}
//
//	public void setSuccess(boolean success) {
//		this.success = success;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

//	public String getValue() {
//		return value;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}
//
//	public Integer getState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}
	
	

}
