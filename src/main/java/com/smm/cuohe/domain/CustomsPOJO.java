package com.smm.cuohe.domain;

import java.io.Serializable;


public class CustomsPOJO  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6104230792299624600L;
	private Integer id;//客户表主键id
	private String companyName;//公司名称
	private String entTypes;//企业类型
	private String salesVolume;//年销售额
	private String nameByArea;//省份
	private String buyBrand;//采购品牌
	private String nameByUser;//负责人
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConpanyName() {
		return companyName;
	}
	public void setConpanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEntTypes() {
		return entTypes;
	}
	public void setEntTypes(String entTypes) {
		this.entTypes = entTypes;
	}
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getNameByArea() {
		return nameByArea;
	}
	public void setNameByArea(String nameByArea) {
		this.nameByArea = nameByArea;
	}
	public String getBuyBrand() {
		return buyBrand;
	}
	public void setBuyBrand(String buyBrand) {
		this.buyBrand = buyBrand;
	}
	public String getNameByUser() {
		return nameByUser;
	}
	public void setNameByUser(String nameByUser) {
		this.nameByUser = nameByUser;
	}
	
}
