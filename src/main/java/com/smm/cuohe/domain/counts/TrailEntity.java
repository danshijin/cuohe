package com.smm.cuohe.domain.counts;

import java.sql.Date;

/**
 * 
 * auth taantaigen
 * 
 * table 
 * 
 */
public class TrailEntity {
	
	private Integer id; //  主键  ID
	private Integer  userId  ;  //负责人ID
	private Date    createAt;  //创建时间
	private Integer createdBy; //创建者ID
	private String  name; //姓名
	private Integer companyID;

	private Integer status; //状态   0 正常    1 已删除 
	private Integer itemsId;//品目id
	private String itemsName ;//品目名称
	private Integer addcount; //添加线索
	private Integer fuzecount;//负责线索
	private Integer zhcount;//转换线索
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getItemsId() {
		return itemsId;
	}
	public void setItemsId(Integer itemsId) {
		this.itemsId = itemsId;
	}
	public String getItemsName() {
		return itemsName;
	}
	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}
	public Integer getAddcount() {
		return addcount;
	}
	public void setAddcount(Integer addcount) {
		this.addcount = addcount;
	}
	public Integer getFuzecount() {
		return fuzecount;
	}
	public void setFuzecount(Integer fuzecount) {
		this.fuzecount = fuzecount;
	}
	public Integer getZhcount() {
		return zhcount;
	}
	public void setZhcount(Integer zhcount) {
		this.zhcount = zhcount;
	}
	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}

	
	
}
