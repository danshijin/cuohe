package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

public class CustomsFull implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -852137535316792104L;
	private Integer id;
	private Integer itemID;
	private Integer companyID;
	private Integer PIC;
	private Integer LastComID;
	private Integer TransCount;
	private Date LastTransTime;
	private Integer status;
	private Date createdAt;
	private Integer createdBy;
	private Date updatedAt;
	private Integer updatedBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	public Integer getPIC() {
		return PIC;
	}
	public void setPIC(Integer pIC) {
		PIC = pIC;
	}
	public Integer getLastComID() {
		return LastComID;
	}
	public void setLastComID(Integer lastComID) {
		LastComID = lastComID;
	}
	public Integer getTransCount() {
		return TransCount;
	}
	public void setTransCount(Integer transCount) {
		TransCount = transCount;
	}
	public Date getLastTransTime() {
		return LastTransTime;
	}
	public void setLastTransTime(Date lastTransTime) {
		LastTransTime = lastTransTime;
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
