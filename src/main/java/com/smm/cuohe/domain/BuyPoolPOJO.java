package com.smm.cuohe.domain;

import java.io.Serializable;


public class BuyPoolPOJO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -673903561823138099L;
	private Integer bid;//买盘池表主键id
	private String title;//采购标题
	private String context;//采购内容
	private String companyName;//企业表 企业名称
	private String createdAt;//创建时间
	
	private Integer commodityId;   //商品id
	
	private String commodityName; //商品名称
	
	private String extID;
	private String quantity;//数量
	private String account;//企业账号
	private String itemAttrOptions;//主要属性值
	private String itemAttrName;//主要属性名称
	
	private float price;//价格
	
	private Integer priceType;
	
	/**
	 * 主键ID
	 */
	private Integer id;
   /**
	 * 价格类型 0 实价  1 升水 2 贴水
	 */
	private Integer lastPriceType;
	/**
	 * 最后还盘报价
	 */
	private double lastPrice;
	/**
	 * 最后报价客户编号
	 */
	private Integer lastCustomerId;
	/**
	 * 最后还价商城会员账号
	 */
	  private String lastMallAccount;
	/**
	 * 客户名称
	 */
	private String lastCustomerName;
	/**
	 * 还盘总数量
	 */
	private Integer poolPriceCount;
	/**
	 * 最后还盘时间
	 */
	private String  lastTime;
	/**
	 * 是否已成交 0 未成交 1 已成交
	 */
	private Integer isConfirm;
	
	
	public String getLastMallAccount() {
		return lastMallAccount;
	}
	public void setLastMallAccount(String lastMallAccount) {
		this.lastMallAccount = lastMallAccount;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLastPriceType() {
		return lastPriceType;
	}
	public void setLastPriceType(Integer lastPriceType) {
		this.lastPriceType = lastPriceType;
	}
	public double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
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
	public Integer getPoolPriceCount() {
		return poolPriceCount;
	}
	public void setPoolPriceCount(Integer poolPriceCount) {
		this.poolPriceCount = poolPriceCount;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public Integer getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
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

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getItemAttrOptions() {
		return itemAttrOptions;
	}
	public void setItemAttrOptions(String itemAttrOptions) {
		this.itemAttrOptions = itemAttrOptions;
	}
	public String getItemAttrName() {
		return itemAttrName;
	}
	public void setItemAttrName(String itemAttrName) {
		this.itemAttrName = itemAttrName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getExtID() {
		return extID;
	}
	public void setExtID(String extID) {
		this.extID = extID;
	}
	public Integer getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
}
