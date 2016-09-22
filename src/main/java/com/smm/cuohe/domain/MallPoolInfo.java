package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.List;

public class MallPoolInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4048683542542523930L;

	private Integer id;//报盘id
	private String productName;//产品名称
	private String brand;//品牌
	private String province;//省份
	private String wareId;//仓库ID
	private String wareName;//仓库名称
	private Double quantity;//数量
	private String unit;//单位
	private String createdAt;//创建时间
	private Double price;//价格
	private Double lastPrice;//最后还盘价格
	private Integer lastPriceType;//最后还盘价格类型 0 实价  1 升水 2 贴水',
	private String lastCustomerName;//最后还盘客户名称
	private Integer poolPriceCount;//还盘总数量 
	private String account;//会员账号
	private Integer poolType;//1 卖盘  2 买盘
	private Double initPrice;//最初报价
	private Integer priceType;//价格类型 0 实价  1 升水 2 贴水
	private Integer initPriceType;//初始价格类型   0 实价  1 升水 2 贴水
	private Integer isConfirm;//是否成交  0 未成交 1 已成交
	private String orderId;//订单编号
	private String commodity_id;//商品编号
	private List<CommodityAttr> commodityAttrList;  //卖盘商品扩展属性
	private String mallBuyerAccount;//商城买方账号
	private String mallSellerAccount;//商城卖方账号
	private String lastMallAccount;//最后还价商城客户编号
	private String FuturesMonth;//期货月
	private String isClose;//是否关闭
	
	
	public String getIsClose() {
		return isClose;
	}
	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}
	public String getMallSellerAccount() {
		return mallSellerAccount;
	}
	public void setMallSellerAccount(String mallSellerAccount) {
		this.mallSellerAccount = mallSellerAccount;
	}
	public String getWareId() {
		return wareId;
	}
	public void setWareId(String wareId) {
		this.wareId = wareId;
	}
	public Integer getLastPriceType() {
		return lastPriceType;
	}
	public void setLastPriceType(Integer lastPriceType) {
		this.lastPriceType = lastPriceType;
	}
	public String getFuturesMonth() {
		return FuturesMonth;
	}
	public void setFuturesMonth(String futuresMonth) {
		FuturesMonth = futuresMonth;
	}
	public String getLastMallAccount() {
		return lastMallAccount;
	}
	public void setLastMallAccount(String lastMallAccount) {
		this.lastMallAccount = lastMallAccount;
	}
	public String getMallBuyerAccount() {
		return mallBuyerAccount;
	}
	public void setMallBuyerAccount(String mallBuyerAccount) {
		this.mallBuyerAccount = mallBuyerAccount;
	}
	public List<CommodityAttr> getCommodityAttrList() {
		return commodityAttrList;
	}
	public void setCommodityAttrList(List<CommodityAttr> commodityAttrList) {
		this.commodityAttrList = commodityAttrList;
	}
	
	public String getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}
	public Integer getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	private String listMall;//详情信息

	public String getListMall() {
		return listMall;
	}
	public void setListMall(String listMall) {
		this.listMall = listMall;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getPoolType() {
		return poolType;
	}
	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}
	public Double getInitPrice() {
		return initPrice;
	}
	public void setInitPrice(Double initPrice) {
		this.initPrice = initPrice;
	}
	public Integer getInitPriceType() {
		return initPriceType;
	}
	public void setInitPriceType(Integer initPriceType) {
		this.initPriceType = initPriceType;
	}

    
	
	
	
}
