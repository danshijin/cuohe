package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyutao
 *
 */
public class CompanyView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2215474021719910708L;
	
	private Integer id;  //企业ID
	
	private String name;//企业名称
	private String companyName;  //企业名称
	
	private String account; //商城用户名
	
	private String status; //用户状态  0 正常 1 删除
	
	private Integer areaId;
	
	private Areas area;
	
	private String province;  //省份
	
	private String address; //地址
		
	private String entTypes; //企业类型ID
	
	private String pic;     //负责人
	
	private String entTypesText;
	
	private String buyBrand;  //采购品牌
	
	private String salesVolume; //年销售额
	private String salesVolumeName;
	
	private String buyProducts;  //采购商品
	
	private Integer buyVolume;   //采购量
	
	private String salesProducts;  //行业 主要销售商品
	
	private Integer categorySource; //网站注册、QQ群、网络营销、公开媒体、合作伙伴
	
	private String source;
	
	private Integer categoryEmployee; //员工人数(类别) 0:0到10人;1:10到100人;2:100到500人;3:500到1000人;4:1000人以上
	
	private String employee;
	
	private Integer categoryFreq;  //采购周期(类别) 一周一次、一周两次、一月一次、不定时
	
	private String freq;
	
	private Integer categoryBusiness;  //上下家(类别) 供应商、采购方（多选）
	
	private String business;
	
	private Integer categoryCoo;   //合作意向(类别) 无、未合作、有过合作、长期合作、意愿强烈
	
	private String coo;
	
	private String corporate;  //法人
	
	private Date registerDate; //注册时间
	
	private String companyPhone; //公司电话
	
	private Integer companyProperty; //公司性质 合资、独资、国有、私营、全民所有制、集体所有制、股份制、有限责任
	
	private String property;
	
	private String companySite; //公司网址
	
	private String taxNo; //税务登记号
	
	private String bank; //开户行
	
	private String bankAccount; //开户行账号
	
	private Integer categoryCredit; //信誉 0：好;1：中;2：差
	
	private String credit;
	
	private String companyAss; //关联企业
	
	private String businessLicense; //营业执照号
	
	private Date expiryTime;  //营业执照到期时间
	
	private String commands; //备注
	
	private Date createdAt; //创建时间
	
	private String createdBy; //创建人名称
	
	private Date updatedAt; //修改时间
	
	private String updatedBy; // 修改人名称
	
	private Integer userId;
	private String userName;
	
	private List<Contacter> contacters; //联系人
	
	private Contacter primaryPurchaseContacts; //首要采购联系人
	
	private Date lastPurchaseTime; //上次采购时间
	private Integer level; //客户级别
	private String levelName;//客户级别名称

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public String getEntTypesText() {
		return entTypesText;
	}

	public void setEntTypesText(String entTypesText) {
		this.entTypesText = entTypesText;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getCategoryEmployee() {
		return categoryEmployee;
	}

	public void setCategoryEmployee(Integer categoryEmployee) {
		this.categoryEmployee = categoryEmployee;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public Integer getCategoryFreq() {
		return categoryFreq;
	}

	public void setCategoryFreq(Integer categoryFreq) {
		this.categoryFreq = categoryFreq;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public Integer getCategoryBusiness() {
		return categoryBusiness;
	}

	public void setCategoryBusiness(Integer categoryBusiness) {
		this.categoryBusiness = categoryBusiness;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public Integer getCategoryCoo() {
		return categoryCoo;
	}

	public void setCategoryCoo(Integer categoryCoo) {
		this.categoryCoo = categoryCoo;
	}

	public String getCoo() {
		return coo;
	}

	public void setCoo(String coo) {
		this.coo = coo;
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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
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

	public Integer getCategoryCredit() {
		return categoryCredit;
	}

	public void setCategoryCredit(Integer categoryCredit) {
		this.categoryCredit = categoryCredit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<Contacter> getContacters() {
		return contacters;
	}

	public void setContacters(List<Contacter> contacters) {
		this.contacters = contacters;
	}

	public Contacter getPrimaryPurchaseContacts() {
		return primaryPurchaseContacts;
	}

	public void setPrimaryPurchaseContacts(Contacter primaryPurchaseContacts) {
		this.primaryPurchaseContacts = primaryPurchaseContacts;
	}

	public Date getLastPurchaseTime() {
		return lastPurchaseTime;
	}

	public void setLastPurchaseTime(Date lastPurchaseTime) {
		this.lastPurchaseTime = lastPurchaseTime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCompanySite() {
		return companySite;
	}

	public void setCompanySite(String companySite) {
		this.companySite = companySite;
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

	public String getSalesVolumeName() {
		return salesVolumeName;
	}

	public void setSalesVolumeName(String salesVolumeName) {
		this.salesVolumeName = salesVolumeName;
	}

	public Areas getArea() {
		return area;
	}

	public void setArea(Areas area) {
		this.area = area;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
