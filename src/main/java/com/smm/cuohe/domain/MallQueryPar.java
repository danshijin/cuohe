package com.smm.cuohe.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MallQueryPar implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6701349845119132114L;
	private Integer itemId;//品目id
	private String account;//商城会员账号
	private String date;//日期
	
	private String[] brands;//品牌
	
	private String brand;//品牌
	private String attrName;//属性名称
	private String attrValue;//属性值
	private String warehouseId;//仓库id
	private String proName;//产品名称
	
	private String  poolId;//报盘Id
	private String poolType;//报盘Type （1 卖盘  2 买盘）
	private String productName;
	private String[] poolIds;//报盘字串集
	private Integer poolPriceCount;//还盘数
	private String mallBuyAccount;
	private String mallSellAccount;
	
	//当前报盘的信息
	private String orderId;
	private String isConfirm;
	private String isClose;
	private Integer priceType;
	private BigDecimal price;
	private String orderCode;
	
	private String minPrice;//最低价
	private String maxPrice;//最高价
	private String maxQuantity; //最低数量
	private String minQuantity;//最高数量
	
	
	private Integer dateOrderby;//时间排序 0  倒序 1正序
	private Integer priceOrderBy;//价格  0  倒序   1正序
	private Integer  startNum;//开始条数
	private Integer endNum;//结束条数
	private Date startTime;
	private Date endTime;
	private Integer buyStartNum;//买盘开始
	private Integer sellStartNum;//卖盘开始
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPoolId() {
		return poolId;
	}
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	public String getPoolType() {
		return poolType;
	}
	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String[] getBrands() {
		return brands;
	}
	public void setBrands(String[] brands) {
		this.brands = brands;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(String maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public String getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(String minQuantity) {
		this.minQuantity = minQuantity;
	}
	public Integer getDateOrderby() {
		return dateOrderby;
	}
	public void setDateOrderby(Integer dateOrderby) {
		this.dateOrderby = dateOrderby;
	}

	public Integer getStartNum() {
		return startNum;
	}
	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	
	public Integer getEndNum() {
		return endNum;
	}
	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}
	public Integer getPriceOrderBy() {
		return priceOrderBy;
	}
	public void setPriceOrderBy(Integer priceOrderBy) {
		this.priceOrderBy = priceOrderBy;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	public String getIsClose() {
		return isClose;
	}
	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}
	public String[] getPoolIds() {
		return poolIds;
	}
	public void setPoolIds(String[] poolIds) {
		this.poolIds = poolIds;
	}
	public Integer getPoolPriceCount() {
		return poolPriceCount;
	}
	public void setPoolPriceCount(Integer poolPriceCount) {
		this.poolPriceCount = poolPriceCount;
	}
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getMallBuyAccount() {
		return mallBuyAccount;
	}
	public void setMallBuyAccount(String mallBuyAccount) {
		this.mallBuyAccount = mallBuyAccount;
	}
	public String getMallSellAccount() {
		return mallSellAccount;
	}
	public void setMallSellAccount(String mallSellAccount) {
		this.mallSellAccount = mallSellAccount;
	}
	public Integer getBuyStartNum() {
		return buyStartNum;
	}
	public void setBuyStartNum(Integer buyStartNum) {
		this.buyStartNum = buyStartNum;
	}
	public Integer getSellStartNum() {
		return sellStartNum;
	}
	public void setSellStartNum(Integer sellStartNum) {
		this.sellStartNum = sellStartNum;
	}

	
	
}
