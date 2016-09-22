package com.smm.cuohe.domain;

/**
 * 
 * 还盘
 * @author tantaigen
 *
 */
public class CounterOfferEntity {
	private Integer id;//主键
	private Integer poolId;//报盘ID
	private Integer poolType;//报盘类型
	private String  price;//价格
	private Integer status;//状态 
	private String customerId;//客户ID
	private  String customerName;//客户ID
	private Integer isConfirm;//是否成交
	private String  updatedAt;//修改时间
	private String  createdAt;//创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPoolId() {
		return poolId;
	}
	public void setPoolId(Integer poolId) {
		this.poolId = poolId;
	}
	public Integer getPoolType() {
		return poolType;
	}
	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Integer getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	

}
