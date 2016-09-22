package com.smm.cuohe.enumDef;

/**
 * @author  zhaoyutao
 * @version 2015年11月17日 下午4:55:15
 * 撮合交易管理页面的查询条件
 */

public enum BusinessQueryEnum {
	p0("", ""),
	p1("1", "getCompanyNameById(customerId)"),
	p2("2", "DATE_FORMAT(f.createdAt,'%Y-%m-%d')"),
	p3("3", "lastCustomerName"),
	p4("4", "DATE_FORMAT(lastTime,'%Y-%m-%d')"),
	p5("5", "ware_name"),
	p6("6", "");
	
	private String key;
	private String value;
	
	private BusinessQueryEnum(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String toString(){
		return key + " : " + value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
