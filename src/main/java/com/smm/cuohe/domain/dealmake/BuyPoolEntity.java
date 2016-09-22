package com.smm.cuohe.domain.dealmake;

import java.util.Date;

/*auth  tantaigen
 * 买盘池
 * 
 */

public class BuyPoolEntity {

	private Integer id; // ID
	private Integer itemsID; // 品目ID
	private String companyname; // 客户企业名称
	private Double quantit; // 库存量
	private String unit; // 数量单位 0吨 1 千克
	private String title; // 采购标题
	private String context; // 采购类容
	private Integer status; // 状态 0 正常 1 删除
	private Date createdAt; // 创建时间
	private Integer createdBy; // 创建人
	private String createbyName; // 创建人名称
	private String areaName; // 地区
	private Integer keyPerson; // 是否是关键联系人
	private String mobilePhone;
	private String telephone;
	private String proStatus; // 处理状态

	private String price; // 价格
	private Integer area; // 地区ID

	private Date updatedAt; // 修改时间
	private Integer updatedBy; // 修改人
	private String updateByName; // 修改人名称
	private Integer contacterID; // 联系人
	private String contacterName; // 联系人
	private Integer commodityID; // 商品编号
	private String mallid; // 商城挂牌编号

	private String mallUserAccount; // 商城会员账号

	private Integer customerId; // 客户编号

	private Integer categoryId;

	// 2015-09-28 wangqingfei
	private String isCustomer;
	private Float lastPrice;
	
    private Integer createSource;//订单来源
    
    private Integer isClose;//是否关闭 0 未关闭  1 已关闭
    private Integer isConfirm;//是否已成交 0 未成交 1 已成交'
    
    private Integer mallbuyId;//商城挂牌号
    
    private String buyCompanyNms;
	
	
	public Integer getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Integer getCreateSource() {
		return createSource;
	}
	public void setCreateSource(Integer createSource) {
		this.createSource = createSource;
	}

	public Float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}

	// 调用接口返回信息
	private String errno;
	private String msg;
	private String wareId;// 仓库ID
	private String wareName;// 仓库名称

	private Integer priceType; // 0  实价  1 升水  2 贴水
	
	private Integer lastCustomerId;//最后报价客户Id
	private String lastCustomerName;//最后还盘客户名称
	private String futuresMonth;//期货月
	

	public String getFuturesMonth() {
		return futuresMonth;
	}

	public void setFuturesMonth(String futuresMonth) {
		this.futuresMonth = futuresMonth;
	}

	public Integer getLastCustomerId() {
		return lastCustomerId;
	}

	public void setLastCustomerId(Integer lastCustomerId) {
		this.lastCustomerId = lastCustomerId;
	}

	public String getLastCustomerName() {
		return lastCustomerName;
	}

	public void setLastCustomerName(String lastCustomerName) {
		this.lastCustomerName = lastCustomerName;
	}

	public String getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemsID() {
		return itemsID;
	}

	public void setItemsID(Integer itemsID) {
		this.itemsID = itemsID;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Double getQuantit() {
		return quantit;
	}

	public void setQuantit(Double quantit) {
		this.quantit = quantit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getCreatebyName() {
		return createbyName;
	}

	public void setCreatebyName(String createbyName) {
		this.createbyName = createbyName;
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

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public Integer getContacterID() {
		return contacterID;
	}

	public void setContacterID(Integer contacterID) {
		this.contacterID = contacterID;
	}

	public String getContacterName() {
		return contacterName;
	}

	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getKeyPerson() {
		return keyPerson;
	}

	public void setKeyPerson(Integer keyPerson) {
		this.keyPerson = keyPerson;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getMallUserAccount() {
		return mallUserAccount;
	}

	public void setMallUserAccount(String mallUserAccount) {
		this.mallUserAccount = mallUserAccount;
	}

	public Integer getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(Integer commodityID) {
		this.commodityID = commodityID;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getMallid() {
		return mallid;
	}

	public void setMallid(String mallid) {
		this.mallid = mallid;
	}

	public String getErrno() {
		return errno;
	}

	public void setErrno(String errno) {
		this.errno = errno;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWareId() {
		return wareId;
	}

	public void setWareId(String wareId) {
		this.wareId = wareId;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public Integer getIsClose() {
		return isClose;
	}
	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}
	public Integer getMallbuyId() {
		return mallbuyId;
	}
	public void setMallbuyId(Integer mallbuyId) {
		this.mallbuyId = mallbuyId;
	}
	public String getBuyCompanyNms() {
		return buyCompanyNms;
	}
	public void setBuyCompanyNms(String buyCompanyNms) {
		this.buyCompanyNms = buyCompanyNms;
	}

}
