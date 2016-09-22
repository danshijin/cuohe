package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhaoyutao
 *
 */
public class Company implements Serializable{
	
	private static final long serialVersionUID = 9012742394286037127L;
	
	private Integer id;  //企业ID
	
	private Integer company_id;
	private String username;
	private String account; //账号
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	private String name;  //企业名称
	
	private Integer areaId;  //地区ID 父级 
	
	
	private String address; //地址
	
	private Integer level;//等级
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	private String entTypes; //企业类型ID
	
	private String buyBrand;  //采购品牌
	
	private String salesVolume; //年销售额
	
	private Integer salesVolumes; //年销售额
	
	private String buyProducts;  //采购商品
	
	private Integer buyVolume;   //采购量
	
	private String salesProducts;  //行业 主要销售商品
	
	private Integer categorySource; //网站注册、QQ群、网络营销、公开媒体、合作伙伴
	
	private Integer categoryEmployee; //员工人数(类别) 0:0到10人;1:10到100人;2:100到500人;3:500到1000人;4:1000人以上
	
	private Integer categoryFreq;  //采购周期(类别) 一周一次、一周两次、一月一次、不定时
	
	private Integer categoryBusiness;  //上下家(类别) 供应商、采购方（多选）
	
	private Integer categoryCoo;   //合作意向(类别) 无、未合作、有过合作、长期合作、意愿强烈
	
	private String corporate;  //法人
	
	private Date registerDate; //注册时间
	
	private String companyPhone; //公司电话
	
	private Integer companyProperty; //公司性质 合资、独资、国有、私营、全民所有制、集体所有制、股份制、有限责任
	
	private String companySite; //公司网址
	
	private String taxNo; //税务登记号
	
	private String bank; //开户行
	
	private String bankAccount; //开户行账号
	
	private Integer credit; //信誉 0：好;1：中;2：差
	
	private String companyAss; //关联企业
	
	private String businessLicense; //营业执照号
	
	private Date expiryTime;  //营业执照到期时间
	
	private String commands; //备注
	
	private Integer status; //0:正常  1:已删除
	
	private Date createdAt; //创建时间
	
	private Integer createdBy; //创建人id
	
	private Date updatedAt; //修改时间
	
	private Integer updatedBy; // 修改人id
	
	private Integer userid;
	
	private String truename;

	private String company;
	
	private String contractor;
	
	private String mobile;
	
	

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	 
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEntTypes() {
		return entTypes;
	}

	public void setEntTypes(String entTypes) {
		this.entTypes = entTypes;
	}

	public String getBuyBrand() {
		return buyBrand;
	}

	public void setBuyBrand(String buyBrand) {
		this.buyBrand = buyBrand;
	}

	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}

	public String getBuyProducts() {
		return buyProducts;
	}

	public void setBuyProducts(String buyProducts) {
		this.buyProducts = buyProducts;
	}

	public Integer getBuyVolume() {
		return buyVolume;
	}

	public void setBuyVolume(Integer buyVolume) {
		this.buyVolume = buyVolume;
	}

	public String getSalesProducts() {
		return salesProducts;
	}

	public void setSalesProducts(String salesProducts) {
		this.salesProducts = salesProducts;
	}

	public Integer getCategorySource() {
		return categorySource;
	}

	public void setCategorySource(Integer categorySource) {
		this.categorySource = categorySource;
	}

	public Integer getCategoryEmployee() {
		return categoryEmployee;
	}

	public void setCategoryEmployee(Integer categoryEmployee) {
		this.categoryEmployee = categoryEmployee;
	}

	public Integer getCategoryFreq() {
		return categoryFreq;
	}

	public void setCategoryFreq(Integer categoryFreq) {
		this.categoryFreq = categoryFreq;
	}

	public Integer getCategoryBusiness() {
		return categoryBusiness;
	}

	public void setCategoryBusiness(Integer categoryBusiness) {
		this.categoryBusiness = categoryBusiness;
	}

	public Integer getCategoryCoo() {
		return categoryCoo;
	}

	public void setCategoryCoo(Integer categoryCoo) {
		this.categoryCoo = categoryCoo;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getSalesVolumes() {
		return salesVolumes;
	}

	public void setSalesVolumes(Integer salesVolumes) {
		this.salesVolumes = salesVolumes;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public Integer getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(Integer companyProperty) {
		this.companyProperty = companyProperty;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public String getCompanyAss() {
		return companyAss;
	}

	public void setCompanyAss(String companyAss) {
		this.companyAss = companyAss;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
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

	public String getCompanySite() {
		return companySite;
	}

	public void setCompanySite(String companySite) {
		this.companySite = companySite;
	}


	
}
