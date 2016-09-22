package com.smm.cuohe.domain;

import java.io.Serializable;

public class PoolPriceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8002174798557642160L;
	
	private String companyName;//客户名称
	private String date;//还盘时间
	private Double price;//还盘价格
	private Integer priceType;//价格类型
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	
	
}
