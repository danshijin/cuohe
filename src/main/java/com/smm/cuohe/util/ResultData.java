package com.smm.cuohe.util;

public class ResultData {
	/**
	 * 返回CODE
	 */
	private String code;
	
	/**
	 * 返回数据
	 */
	private Object resultData;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 总记录数
	 * @return
	 */
	
	private Integer totalRecords;


	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getResultData() {
		return resultData;
	}
	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	@Override
	public String toString() {
		return "ResultData [code=" + code + ", resultData=" + resultData + ", message=" + message + "]";
	}
}
