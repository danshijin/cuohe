package com.smm.cuohe.domain.dealmake;

import java.util.Date;

public class OrdercodeEntity {
	
	   private Integer iD;  					//订单ID					
	   private Integer orderCode;				//订单编号
	   private Integer itemID;					//产品ID
	   private Integer sellID;					//卖家企业ID
	   private Integer buyID;					//买家企业ID
	   private Integer delivery;				//交货方式
	   private Integer payment;					//结算方式
	   private Integer wareProvince;			// 仓库-省份
	   private String areName;					//仓库名称
	   private Date deliveryDate;				//交货日期
	   private Integer poundDiff; 				//磅差
	   private Integer ttype;					//交易类型
	   private String contract;					//合同
	   private Integer  source;					//订单来源
	   private Date createdAt;					//创建时间
	   private Integer createdBy;				// 创建者
	  private Date updatedAt;					//修改时间
	  private Integer updatedBy;					// 修改者
	public Integer getiD() {
		return iD;
	}
	public void setiD(Integer iD) {
		this.iD = iD;
	}
	public Integer getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public Integer getSellID() {
		return sellID;
	}
	public void setSellID(Integer sellID) {
		this.sellID = sellID;
	}
	public Integer getBuyID() {
		return buyID;
	}
	public void setBuyID(Integer buyID) {
		this.buyID = buyID;
	}
	public Integer getDelivery() {
		return delivery;
	}
	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public Integer getWareProvince() {
		return wareProvince;
	}
	public void setWareProvince(Integer wareProvince) {
		this.wareProvince = wareProvince;
	}
	public String getAreName() {
		return areName;
	}
	public void setAreName(String areName) {
		this.areName = areName;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Integer getPoundDiff() {
		return poundDiff;
	}
	public void setPoundDiff(Integer poundDiff) {
		this.poundDiff = poundDiff;
	}
	public Integer getTtype() {
		return ttype;
	}
	public void setTtype(Integer ttype) {
		this.ttype = ttype;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
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

	
	
	
	

}
