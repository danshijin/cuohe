package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户对象
 *
 * @author lirubin
 */
public class Customer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8670261405788469658L;

	private Integer id;

	//品目编号
	private Integer itemId;

	//负责人编号
	private Integer pic;

	//最后沟通记录
	private Integer lastComId;

	//成交笔数
	private Integer transCount;

	//最后成交时间
	private Date lastTransTime;

	private String companyName;  //企业名称

	private Integer areaId;  //地区ID 父级

	private String address; //地址

	private Integer level;//等级

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

	private Integer categoryCredit; //信誉 0：好;1：中;2：差

	private String companyAss; //关联企业

	private String businessLicense; //营业执照号

	private Date expiryTime;  //营业执照到期时间

	private String commands; //备注

	private Integer status; //0:正常  1:已删除

	private String account; //商城会员账号

	private Date createdAt;

	private Integer createdBy;

	private Date updatedAt;

	private Integer updatedBy;
	private Integer trailId; //线索ID(转换线索)
	
	private Integer createMallAccount;//新增标记


	private List<Contacter> contacters;	//客户联系人列表
	
	private List<Contacter> blacikContacters;//对应黑名单
	
	private String tag;//标签

	private Double price;
	
	private String only;
	
	private String user_name;
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getOnly() {
		return only;
	}

	public void setOnly(String only) {
		this.only = only;
	}

	public List<Contacter> getBlacikContacters() {
		return blacikContacters;
	}

	public void setBlacikContacters(List<Contacter> blacikContacters) {
		this.blacikContacters = blacikContacters;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Contacter> getContacters() {
		return contacters;
	}

	public void setContacters(List<Contacter> contacters) {
		this.contacters = contacters;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCategoryCredit() {
		return categoryCredit;
	}

	public void setCategoryCredit(Integer categoryCredit) {
		this.categoryCredit = categoryCredit;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	public Integer getSalesVolumes() {
		return salesVolumes;
	}

	public void setSalesVolumes(Integer salesVolumes) {
		this.salesVolumes = salesVolumes;
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

	public String getCompanySite() {
		return companySite;
	}

	public void setCompanySite(String companySite) {
		this.companySite = companySite;
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


	public Integer getTransCount() {
		return transCount;
	}

	public void setTransCount(Integer transCount) {
		this.transCount = transCount;
	}

	public Date getLastTransTime() {
		return lastTransTime;
	}

	public void setLastTransTime(Date lastTransTime) {
		this.lastTransTime = lastTransTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPic() {
		return pic;
	}

	public void setPic(Integer pic) {
		this.pic = pic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

//	public Integer getCompanyId() {
//		return companyId;
//	}
//
//	public void setCompanyId(Integer companyId) {
//		this.companyId = companyId;
//	}

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

	public Integer getCategorySource() {
		return categorySource;
	}

	public void setCategorySource(Integer categorySource) {
		this.categorySource = categorySource;
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

	public Integer getLastComId() {
		return lastComId;
	}

	public void setLastComId(Integer lastComId) {
		this.lastComId = lastComId;
	}

	public Integer getTrailId() {
		return trailId;
	}

	public void setTrailId(Integer trailId) {
		this.trailId = trailId;
	}

	public Integer getCreateMallAccount() {
		return createMallAccount;
	}

	public void setCreateMallAccount(Integer createMallAccount) {
		this.createMallAccount = createMallAccount;
	}
	
	
	
}
