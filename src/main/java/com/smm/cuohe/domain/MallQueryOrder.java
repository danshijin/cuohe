package com.smm.cuohe.domain;

public class MallQueryOrder {
	
		private Integer id;//报盘ID
		private String orderId;//订单编号
        private Float quantity;//数量
        private Integer  unit;//单位  0 吨  1 千克
        private Float price;//价格
        private String createdAt;//创建时间
        private  Integer commodityId;//商品编号 
        private String proName;//产品名称
        private  Integer poolType;//1  销售  2 采购
		private Integer priceType;//0 实价 1 升水 2 贴水
		private String attrName;//属性名称
		private String attrValue;//属性值
		private String attr;//属性
		private String mallSellerAccount;//商城卖方账号,
		private String mallBuyerAccount;//商城买方账号,
		private String mallUserAccount;//商城会员账号
		
		public String getMallUserAccount() {
			return mallUserAccount;
		}
		public void setMallUserAccount(String mallUserAccount) {
			this.mallUserAccount = mallUserAccount;
		}
		public String getMallSellerAccount() {
			return mallSellerAccount;
		}
		public void setMallSellerAccount(String mallSellerAccount) {
			this.mallSellerAccount = mallSellerAccount;
		}
		public String getMallBuyerAccount() {
			return mallBuyerAccount;
		}
		public void setMallBuyerAccount(String mallBuyerAccount) {
			this.mallBuyerAccount = mallBuyerAccount;
		}
		public String getProName() {
			return proName;
		}
		public void setProName(String proName) {
			this.proName = proName;
		}
		public String getAttr() {
			return attr;
		}
		public void setAttr(String attr) {
			this.attr = attr;
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
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public Float getQuantity() {
			return quantity;
		}
		public void setQuantity(Float quantity) {
			this.quantity = quantity;
		}
		public Integer getUnit() {
			return unit;
		}
		public void setUnit(Integer unit) {
			this.unit = unit;
		}
		public Float getPrice() {
			return price;
		}
		public void setPrice(Float price) {
			this.price = price;
		}
		public String getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}
		public Integer getCommodityId() {
			return commodityId;
		}
		public void setCommodityId(Integer commodityId) {
			this.commodityId = commodityId;
		}
		public Integer getPoolType() {
			return poolType;
		}
		public void setPoolType(Integer poolType) {
			this.poolType = poolType;
		}
		public Integer getPriceType() {
			return priceType;
		}
		public void setPriceType(Integer priceType) {
			this.priceType = priceType;
		}

		
		
		
}
