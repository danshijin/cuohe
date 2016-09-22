package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

public class TrailPoolAPI implements Serializable  {
	private static final long serialVersionUID = -1449909477434581894L;
	 
	private String itemname; //品目名称
	private Integer itemId;//====品目ID	
	//private String username;  //撮合负责人	
	private String categorySource; //来源
	private Integer categorySourceId; //====来源ID

	private String nextTime; //下次联系时间
	private String nextContext; //下次联系内容
	
	private String categoryCoo;  //合作意向
	private Integer categoryCooId;  //====合作意向ID
	private String companyName;  //企业名称
	private String salesProducts; //主要销售		
	private String address; //地址  	 
	private String corporate; //法人	  
	private String companyPhone; //公司电话	
	private String compaySite; //公司网址
	private String entTypes;  //企业类型
	private String categoryEmployee; //员工人数
	private Integer categoryEmployeeId; //=====员工人数Id
	private String salesVolume; //年销售额
	private Integer salesVolumeId; //====年销售额
	private String strRegisterDate; //注册时间
	private String companyProperty; //公司性质
	private Integer companyPropertyId; //====公司性质Id
	private String area; //省份
	private Integer areaId; //====省份Id
	private String city; //城市
	private Integer cityId; //====城市Id
	private String position; //联系人职位
	private String contacterName; //联系人姓名
	private String mobilePhone; //联系人手机
	private String qqNum; //QQ
	private String email; //Email
	private Integer sex; //性别   0男、1女
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
	public String getCategorySource() {
		return categorySource;
	}
	public void setCategorySource(String categorySource) {
		this.categorySource = categorySource;
	}
	public Integer getCategorySourceId() {
		return categorySourceId;
	}
	public void setCategorySourceId(Integer categorySourceId) {
		this.categorySourceId = categorySourceId;
	}
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	public String getNextContext() {
		return nextContext;
	}
	public void setNextContext(String nextContext) {
		this.nextContext = nextContext;
	}
	public String getCategoryCoo() {
		return categoryCoo;
	}
	public void setCategoryCoo(String categoryCoo) {
		this.categoryCoo = categoryCoo;
	}
	public Integer getCategoryCooId() {
		return categoryCooId;
	}
	public void setCategoryCooId(Integer categoryCooId) {
		this.categoryCooId = categoryCooId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSalesProducts() {
		return salesProducts;
	}
	public void setSalesProducts(String salesProducts) {
		this.salesProducts = salesProducts;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCorporate() {
		return corporate;
	}
	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompaySite() {
		return compaySite;
	}
	public void setCompaySite(String compaySite) {
		this.compaySite = compaySite;
	}
	public String getEntTypes() {
		return entTypes;
	}
	public void setEntTypes(String entTypes) {
		this.entTypes = entTypes;
	}
	public String getCategoryEmployee() {
		return categoryEmployee;
	}
	public void setCategoryEmployee(String categoryEmployee) {
		this.categoryEmployee = categoryEmployee;
	}
	public Integer getCategoryEmployeeId() {
		return categoryEmployeeId;
	}
	public void setCategoryEmployeeId(Integer categoryEmployeeId) {
		this.categoryEmployeeId = categoryEmployeeId;
	}
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public Integer getSalesVolumeId() {
		return salesVolumeId;
	}
	public void setSalesVolumeId(Integer salesVolumeId) {
		this.salesVolumeId = salesVolumeId;
	}
	public String getStrRegisterDate() {
		return strRegisterDate;
	}
	public void setStrRegisterDate(String strRegisterDate) {
		this.strRegisterDate = strRegisterDate;
	}
	public String getCompanyProperty() {
		return companyProperty;
	}
	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}
	public Integer getCompanyPropertyId() {
		return companyPropertyId;
	}
	public void setCompanyPropertyId(Integer companyPropertyId) {
		this.companyPropertyId = companyPropertyId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getContacterName() {
		return contacterName;
	}
	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
