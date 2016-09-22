package com.smm.cuohe.domain;

import java.util.Map;

public class PoolApiEntity {
	private Integer poolId;

	private Integer poolType;
	private Integer itemId;
	private String itemName;
	private Integer productId ;
	private String productName;
	private Float  price;
	private Double count;
	private Integer unit;
	private Map<String, Object> commodityAttr;
	private String attr;
	private Integer areaId ;
	private String warehouseId;
	private String warehouse;
	private Integer delivery;
	private String  deliverytime;
	private Float moq;
	private Float outmoq;
	private Float overflow;
	private String username;
	private String   title;
	private String context;
	private Integer receiptType;
	private Integer priceType;
	private String futuresMonth;//期货月
	private String payType;
	private String receipttype;
	private String quantity;
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getReceipttype() {
		return receipttype;
	}
	public void setReceipttype(String receipttype) {
		this.receipttype = receipttype;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public Integer getPoolType() {
		return poolType;
	}
	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	
	public Map<String, Object> getCommodityAttr() {
		return commodityAttr;
	}
	public void setCommodityAttr(Map<String, Object> commodityAttr) {
		this.commodityAttr = commodityAttr;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public Integer getDelivery() {
		return delivery;
	}
	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}
	public String getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}
	public Float getMoq() {
		return moq;
	}
	public void setMoq(Float moq) {
		this.moq = moq;
	}
	public Float getOutmoq() {
		return outmoq;
	}
	public void setOutmoq(Float outmoq) {
		this.outmoq = outmoq;
	}
	public Float getOverflow() {
		return overflow;
	}
	public void setOverflow(Float overflow) {
		this.overflow = overflow;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Integer getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(Integer receiptType) {
		this.receiptType = receiptType;
	}
	public Double getCount() {
		return count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	public Integer getPoolId() {
		return poolId;
	}
	public void setPoolId(Integer poolId) {
		this.poolId = poolId;
	}
	public String getFuturesMonth() {
		return futuresMonth;
	}
	public void setFuturesMonth(String futuresMonth) {
		this.futuresMonth = futuresMonth;
	}
	
}
