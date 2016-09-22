package com.smm.cuohe.domain;
 
import java.util.Date; 
import java.io.Serializable;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
 

public class TrailPoolAdd implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1449909477434581894L;
 
	private Integer id;
	private Integer trailPoolId;
	private Integer contacterId;
	private Integer contacterId2; 
	private Integer userId; //负责人ID		
	private Integer companyId; //公司ID	
	private String companyName; //公司名
	private String account; //公司名	 
	private Integer categorySource; //来源	
	private Integer itemId; //品目 
	private Integer areaId; //省份	
	private Integer cityId;  //城市ID 子级 
	private String address; //地址 
	private Date nextTime; //下次联系时间
	
	private String strNextTime; //下次联系时间 
	
	private String nextContext; //下次联系内容 
	
	private List<Contacter> list;  //联系人集合	
	private String contacterName; //联系人
	private Integer sex; //性别
	private String position;//职位
	private String mobilePhone; //手机
	private String qq; //QQ
	private String email; //邮箱	
	 
	private String salesProducts; //行业 
	private String corporate; //法人
	private String companyPhone;// 公司电话
	private String companySite; //公司网址
	private String entTypes; //企业类型ID	
	private Integer categoryEmployee; //员工人数(类别) 0:0到10人;1:10到100人;2:100到500人;3:500到1000人;4:1000人以上
	private String salesVolume; //年销售额 
	private Date registerDate; //注册时间
	private String strRegisterDate; //注册时间
	
	private Integer companyProperty; //企业性质
	private  String  categoryCoo;//合作意向
	
	private String contacterName2; //联系人
	private Integer sex2; //性别
	private String position2;//职位
	private String mobilePhone2; //手机
	private String qq2; //QQ
	private String email2; //邮箱
	
	//其他
	private Integer status; //状态	
	private Date createdAt;
	private Integer createdBy;
	private Date updatedAt;
	private Integer updatedBy;
	public Integer getTrailPoolId() {
		return trailPoolId;
	}
	public void setTrailPoolId(Integer trailPoolId) {
		this.trailPoolId = trailPoolId;
	}
	public Integer getContacterId() {
		return contacterId;
	}
	public void setContacterId(Integer contacterId) {
		this.contacterId = contacterId;
	}
	public Integer getContacterId2() {
		return contacterId2;
	}
	public void setContacterId2(Integer contacterId2) {
		this.contacterId2 = contacterId2;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getCategorySource() {
		return categorySource;
	}
	public void setCategorySource(Integer categorySource) {
		this.categorySource = categorySource;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
	public String getStrNextTime() {
		return strNextTime;
	}
	public void setStrNextTime(String strNextTime) {
		this.strNextTime = strNextTime;
	}
	public String getNextContext() {
		return nextContext;
	}
	public void setNextContext(String nextContext) {
		this.nextContext = nextContext;
	}
	public List<Contacter> getList() {
		return list;
	}
	public void setList(List<Contacter> list) {
		this.list = list;
	}
	public String getContacterName() {
		return contacterName;
	}
	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSalesProducts() {
		return salesProducts;
	}
	public void setSalesProducts(String salesProducts) {
		this.salesProducts = salesProducts;
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
	public String getCompanySite() {
		return companySite;
	}
	public void setCompanySite(String companySite) {
		this.companySite = companySite;
	}
	public String getEntTypes() {
		return entTypes;
	}
	public void setEntTypes(String entTypes) {
		this.entTypes = entTypes;
	}
	public Integer getCategoryEmployee() {
		return categoryEmployee;
	}
	public void setCategoryEmployee(Integer categoryEmployee) {
		this.categoryEmployee = categoryEmployee;
	}
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getStrRegisterDate() {
		return strRegisterDate;
	}
	public void setStrRegisterDate(String strRegisterDate) {
		this.strRegisterDate = strRegisterDate;
	}
	public Integer getCompanyProperty() {
		return companyProperty;
	}
	public void setCompanyProperty(Integer companyProperty) {
		this.companyProperty = companyProperty;
	}
	public String getContacterName2() {
		return contacterName2;
	}
	public void setContacterName2(String contacterName2) {
		this.contacterName2 = contacterName2;
	}
	public Integer getSex2() {
		return sex2;
	}
	public void setSex2(Integer sex2) {
		this.sex2 = sex2;
	}
	public String getPosition2() {
		return position2;
	}
	public void setPosition2(String position2) {
		this.position2 = position2;
	}
	public String getMobilePhone2() {
		return mobilePhone2;
	}
	public void setMobilePhone2(String mobilePhone2) {
		this.mobilePhone2 = mobilePhone2;
	}
	public String getQq2() {
		return qq2;
	}
	public void setQq2(String qq2) {
		this.qq2 = qq2;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryCoo() {
		return categoryCoo;
	}
	public void setCategoryCoo(String categoryCoo) {
		this.categoryCoo = categoryCoo;
	}
	
	
}
