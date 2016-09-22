package com.smm.cuohe.tools;

public class CommonException extends RuntimeException {

	private String retMsg;
	private String retCode;

	public CommonException(String retCode, String retMsg) {
		super(retCode + ":" + retMsg);
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

}
