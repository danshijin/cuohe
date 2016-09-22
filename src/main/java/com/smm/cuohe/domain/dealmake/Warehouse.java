package com.smm.cuohe.domain.dealmake;

import java.io.Serializable;
import java.util.Date;

public class Warehouse implements Serializable {

	/**
	 * 仓库
	 */
	private static final long serialVersionUID = 4757087990774093709L;

	private Integer id;
	private String name; // 仓库名
	private String catid;//品目ID
	private Integer ItemId;// 品目
	private String company;// 企业名称
	private Integer areaid;// 仓库地区
	private String address;// 地址
	private Integer status;// 状态
	private Date createdAt;// 创建时间
	private Integer createdBy;// 创建者
	private Date updateAt;// 更新时间
	private Integer updateBy;// 更新者
	private Integer issupervise;//是否监管 1是0否
	private Integer  iswhole;//是否监管 1是0否
	private Integer  iscooperate;//是否是合作库 1是0不是
	private String  createuser;//创建人 所属人
	private Integer  isself;//是否自营 1自营 0第三方
	
	public Integer getIssupervise() {
		return issupervise;
	}
	public void setIssupervise(Integer issupervise) {
		this.issupervise = issupervise;
	}
	public Integer getIswhole() {
		return iswhole;
	}
	public void setIswhole(Integer iswhole) {
		this.iswhole = iswhole;
	}
	public Integer getIscooperate() {
		return iscooperate;
	}
	public void setIscooperate(Integer iscooperate) {
		this.iscooperate = iscooperate;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Integer getIsself() {
		return isself;
	}
	public void setIsself(Integer isself) {
		this.isself = isself;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatid() {
		return catid;
	}
	public void setCatid(String catid) {
		this.catid = catid;
	}
	public Integer getItemId() {
		return ItemId;
	}
	public void setItemId(Integer itemId) {
		ItemId = itemId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updatedAt) {
		this.updateAt = updatedAt;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updatedBy) {
		this.updateBy = updatedBy;
	}

}
