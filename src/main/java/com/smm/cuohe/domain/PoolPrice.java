package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

public class PoolPrice  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 还盘ID
	 */
	private Integer id;
	
	/**
	 * 报盘ID
	 */
	private Integer poolId;
	
	/**
	 * 报盘类型 1 卖盘 2 买盘
	 */
	private Integer poolType;
	
	/**
	 * 创建时间
	 */
	private Date createdAt;
	
	/**
	 * 价格
	 */
	private Double price;
	
	/**
	 * 价格类型
	 */
	private Integer priceType;
	
	/**
	 * 价格展示
	 */
	private String typeAmt;
	
	/**
	 * 客户账号
	 */
	private Integer customerId;
	
	/**
	 * 状态 0 正常 1 删除
	 */
	private Integer status;
	
	/**
	 * 修改时间 
	 */
	private Date updatedAt;
	
	/**
	 * 客户
	 */
	private String customerName;
	
	/**
	 * 联系人
	 */
	private String lxrName;
	
	/**
	 * 电话
	 */
	private String mobilePhone;
	
	/**
	 * 商城会员账号
	 */
	private String account;
	
	//拓展属性
	private Integer itemId;//品目ID
	
	private String lastCustomerName;
	
	private String productName;
	
	public String getTypeAmt() {
		return typeAmt;
	}

	public void setTypeAmt(String typeAmt) {
		this.typeAmt = typeAmt;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLxrName() {
		return lxrName;
	}

	public void setLxrName(String lxrName) {
		this.lxrName = lxrName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getLastCustomerName() {
		return lastCustomerName;
	}

	public void setLastCustomerName(String lastCustomerName) {
		this.lastCustomerName = lastCustomerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
