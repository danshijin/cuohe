package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 线索实体
 * @author tantaigen
 *
 */
public class TrailPoolPOJO  implements Serializable {
	 
	private static final long serialVersionUID = -4620737075621627174L;
	
	
	private Integer itemId;
	 private int id;
	 //公司名  （企业表：企业名称）
	 private String companyName;
	 //来源 （分类表：name） 
	 private Integer  categorySource;
	 private String categorySourceName;
	 private Integer source;
	//地址 （企业表：地址） 
	 private String address;
	 //行业 
	 private String iname; 
	 //负责人 （用户表：用户名字）
	 private Integer userId;
	 private String userName;
	
	 private  Integer  categoryCoo;//合作意向    无、未合作、有过合作、长期合作、意愿强烈
	 private String categoryCooName;
	 private  String salesProducts;//行业
	 private  String corporate;//法人
	 private  String companyPhone;//公司联系电话
	 private  String compaySite;//网址
	 private  String entTypes;
	 private  String entTypesName;//企业类型
	 
	 private Integer categoryEmployee;
	 private String categoryEmployeeName;//员工人数
	 
	 private Integer salesVolume;
	 private String salesVolumeName;//年销售额
	 private String strRegisterDate;//注册时间
	 private Integer companyProperty;
	 private String companyPropertyName;//企业性质
	
	 private String nextTime; //下次联系时间
	
	 private String nextContext;//下次联系内容 
	 
	 private Date createdAt;
	 private Integer companyId;


	 private Integer  status;
	 

	 private Integer city;
	 
	 
	
	private Integer  contractId ;//联系人Id
	 
	 private String  contractName;//联系人名称
	 
	 private String positions; //职位
	 
	 private String mobilePhone;//手机
	
	 private String qqNum; //QQ
	
	 private String email; //	 邮箱 
	 
	private Integer trailPoolId;
	
	private Integer contacterId;
	private Integer contacterId2; 
	
	
	private Integer areaId; //省份	
	private Integer cityId;  //城市ID 子级 
	
	
	private Integer  conId;
	private String contacterName; //联系人
	private Integer sex; //性别
	private String position;//职位
	private String qq; //QQ
	
	private String companySite; //公司网址
	private Date registerDate; //注册时间
	private Integer createdBy;
	private Date updatedAt;
	private Integer updatedBy;
	
	private String account; //公司名	 
	
	private Integer  conId2;
	private String contacterName2; //联系人
	private Integer sex2; //性别
	private String position2;//职位
	private String mobilePhone2; //手机
	private String qq2; //QQ
	private String email2; //邮箱

	public String getCategorySourceName() {
		return categorySourceName;
	}
	public void setCategorySourceName(String categorySourceName) {
		this.categorySourceName = categorySourceName;
	}
	private String strNextTime; //下次联系时间 
	
		
		
		
		
		
		
		
		
		
		
		public Integer getCity() {
			return city;
		}
		public void setCity(Integer city) {
			this.city = city;
		}
		public Integer getConId() {
			return conId;
		}
		public void setConId(Integer conId) {
			this.conId = conId;
		}
		public Integer getConId2() {
			return conId2;
		}
		public void setConId2(Integer conId2) {
			this.conId2 = conId2;
		}
		public String getAccount() {
				return account;
			}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getStrNextTime() {
			return strNextTime;
		}
		public void setStrNextTime(String strNextTime) {
			this.strNextTime = strNextTime;
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
		public Integer getItemId() {
			return itemId;
		}
		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
	
		public Integer getCategorySource() {
			return categorySource;
		}
		public void setCategorySource(Integer categorySource) {
			this.categorySource = categorySource;
		}
		public Integer getSource() {
			return source;
		}
		public void setSource(Integer source) {
			this.source = source;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getIname() {
			return iname;
		}
		public void setIname(String iname) {
			this.iname = iname;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Integer getCategoryCoo() {
			return categoryCoo;
		}
		public void setCategoryCoo(Integer categoryCoo) {
			this.categoryCoo = categoryCoo;
		}
		public String getCategoryCooName() {
			return categoryCooName;
		}
		public void setCategoryCooName(String categoryCooName) {
			this.categoryCooName = categoryCooName;
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
		public String getEntTypesName() {
			return entTypesName;
		}
		public void setEntTypesName(String entTypesName) {
			this.entTypesName = entTypesName;
		}
		public Integer getCategoryEmployee() {
			return categoryEmployee;
		}
		public void setCategoryEmployee(Integer categoryEmployee) {
			this.categoryEmployee = categoryEmployee;
		}
		public String getCategoryEmployeeName() {
			return categoryEmployeeName;
		}
		public void setCategoryEmployeeName(String categoryEmployeeName) {
			this.categoryEmployeeName = categoryEmployeeName;
		}
		public Integer getSalesVolume() {
			return salesVolume;
		}
		public void setSalesVolume(Integer salesVolume) {
			this.salesVolume = salesVolume;
		}
		public String getSalesVolumeName() {
			return salesVolumeName;
		}
		public void setSalesVolumeName(String salesVolumeName) {
			this.salesVolumeName = salesVolumeName;
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
		public String getCompanyPropertyName() {
			return companyPropertyName;
		}
		public void setCompanyPropertyName(String companyPropertyName) {
			this.companyPropertyName = companyPropertyName;
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
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public Integer getCompanyId() {
			return companyId;
		}
		public void setCompanyId(Integer companyId) {
			this.companyId = companyId;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getContractId() {
			return contractId;
		}
		public void setContractId(Integer contractId) {
			this.contractId = contractId;
		}
		public String getContractName() {
			return contractName;
		}
		public void setContractName(String contractName) {
			this.contractName = contractName;
		}
		public String getPositions() {
			return positions;
		}
		public void setPositions(String positions) {
			this.positions = positions;
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
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
		public String getCompanySite() {
			return companySite;
		}
		public void setCompanySite(String companySite) {
			this.companySite = companySite;
		}
		public Date getRegisterDate() {
			return registerDate;
		}
		public void setRegisterDate(Date registerDate) {
			this.registerDate = registerDate;
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
	 
	 
	 
	 
	 
	
	
	
}
