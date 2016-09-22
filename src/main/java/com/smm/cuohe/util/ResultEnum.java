package com.smm.cuohe.util;

public enum ResultEnum {

	SUCC("success", "OK"),
	FAILD("failed", "调用失败"),
	EXE("exe", "调用成功，但程序发生异常"),
	HAS("has", "已此线索池.");

	private String code;
	private String message;

	private ResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}