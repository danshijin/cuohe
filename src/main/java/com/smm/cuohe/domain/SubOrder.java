package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SubOrder  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4320631255622050280L;

	private Integer id;//订单id
	
	private String name;//产品名称
	
	private Integer tType;//订单类别（销售/购买）
	
	private Double quantity;//数量
	
	private Integer unit;//单位
	
	private Double price;//单价
	
	private String contract;//合同
	
	private String contractPath;
	
	private Date createdAt;//成交时间
	
	private Integer createdBy;
	
	private Integer status;//状态
	
	private String ext;
	
	private List<AttrValue> attrValues;//属性
	
	private List<CommodityAttr> commodityAttrList;
	
	private Integer orderId;//订单Id
	
	private Date updatedAt;
	
	private Integer updatedBy;
	
	private String sellCompanyNm;//卖方企业名称
	
	private String buyCompanyNm;//买饭企业名称
	
	private String wareNm;//地区（省份）
	
	private String itemNm;//品目名称
	
	private String userNm;//撮合人
	
	private String orderCode;//订单编号
	
	private Integer confirmStatus;//合同状态
	
	private Integer orderStatus;//订单状态
	
	private String orderContract;//订单合同
	
	private Integer sellId;//卖盘编号
	
	private Integer commodityId;//商品编号
	
	private Integer mallSaleId;//商城挂牌编号
	
	
	
	public Integer getSellId() {
		return sellId;
	}

	public void setSellId(Integer sellId) {
		this.sellId = sellId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getMallSaleId() {
		return mallSaleId;
	}

	public void setMallSaleId(Integer mallSaleId) {
		this.mallSaleId = mallSaleId;
	}

	
	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSellCompanyNm() {
		return sellCompanyNm;
	}

	public void setSellCompanyNm(String sellCompanyNm) {
		this.sellCompanyNm = sellCompanyNm;
	}

	public String getBuyCompanyNm() {
		return buyCompanyNm;
	}

	public void setBuyCompanyNm(String buyCompanyNm) {
		this.buyCompanyNm = buyCompanyNm;
	}

	public String getWareNm() {
		return wareNm;
	}

	public void setWareNm(String wareNm) {
		this.wareNm = wareNm;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer gettType() {
		return tType;
	}

	public void settType(Integer tType) {
		this.tType = tType;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public List<AttrValue> getAttrValues() {
		return attrValues;
	}

	public void setAttrValues(List<AttrValue> attrValues) {
		this.attrValues = attrValues;
	}

	public String getContractPath() {
		return contractPath;
	}

	public void setContractPath(String contractPath) {
		this.contractPath = contractPath;
	}

	public String getOrderContract() {
		return orderContract;
	}

	public void setOrderContract(String orderContract) {
		this.orderContract = orderContract;
	}


	public List<CommodityAttr> getCommodityAttrList() {
		return commodityAttrList;
	}

	public void setCommodityAttrList(List<CommodityAttr> commodityAttrList) {
		this.commodityAttrList = commodityAttrList;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
