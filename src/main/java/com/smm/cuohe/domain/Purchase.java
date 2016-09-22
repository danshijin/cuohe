package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 采购商客户
 */
public class Purchase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3602692204470347556L;
	private Integer id;//客户表主键id
	private String companyName;//企业名称
	private Date updatedAtByRecord;//沟通时间
	private String entTypes;//企业类型
	private String salesVolume;//年销售额
	private String nameByContacter;//联系人
	private String mobilePhone;//电话
	private String nameByArea;//省份
	private Date updatedAtByOrder;//上次成交时间
	private Integer categoryFreq;//采购周期(类别)
	private String freqString;
	private String buyBrand;//采购品牌
	private String nameByUser;//负责人
	private Date createdAt;//创建时间
	private String tag;
	
	private String title;//标题
	private String context;//内容
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getUpdatedAtByRecord() {
		return updatedAtByRecord;
	}
	public void setUpdatedAtByRecord(Date updatedAtByRecord) {
		this.updatedAtByRecord = updatedAtByRecord;
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
	public String getNameByContacter() {
		return nameByContacter;
	}
	public void setNameByContacter(String nameByContacter) {
		this.nameByContacter = nameByContacter;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getNameByArea() {
		return nameByArea;
	}
	public void setNameByArea(String nameByArea) {
		this.nameByArea = nameByArea;
	}
	public Date getUpdatedAtByOrder() {
		return updatedAtByOrder;
	}
	public void setUpdatedAtByOrder(Date updatedAtByOrder) {
		this.updatedAtByOrder = updatedAtByOrder;
	}
	public Integer getCategoryFreq() {
		return categoryFreq;
	}
	public void setCategoryFreq(Integer categoryFreq) {
		this.categoryFreq = categoryFreq;
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFreqString() {
		return freqString;
	}
	public void setFreqString(String freqString) {
		this.freqString = freqString;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
