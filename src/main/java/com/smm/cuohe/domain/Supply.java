package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

public class Supply implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -162675613242183571L;
	private Integer id;//企业ID
	private String name;//企业名称
	private Date updatedAtByRecord;//沟通时间
	private String entTypes;//企业类型
	private String salesVolume;//年销售额
	private String nameByContacter;//联系人
	private String mobilePhone;//电话
	private String nameByArea;//省份
	private Date updatedAtByOrder;//上次成交时间
	private Integer categoryemployee;//员工人数(类别)
	private String buyBrand;//采购品牌
	private String nameByUser;//负责人
	private Date createdAt;//创建时间
	private String status; //客户状态
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String title;//标题
	private String context;//内容
	
	private String level;//客户级别
	private String levelString;
	private String employeeString;
	
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public Integer getCategoryemployee() {
		return categoryemployee;
	}
	public void setCategoryemployee(Integer categoryemployee) {
		this.categoryemployee = categoryemployee;
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
	public String getLevelString() {
		return levelString;
	}
	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}
	public String getEmployeeString() {
		return employeeString;
	}
	public void setEmployeeString(String employeeString) {
		this.employeeString = employeeString;
	}
	
	
}
