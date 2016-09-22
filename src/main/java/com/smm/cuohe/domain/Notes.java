package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 品目表
 * 
 * @author zengshihua
 *
 */
public class Notes implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 品目ID
	 */
	private String itemsId;
	/**
	 * 记录日期
	 */
	private Date noteDate;
	/**
	 * 类别0:组共享记录 1:个人便签
	 */
	private Integer noteType;
	/**
	 * 内容
	 */
	private String context;
	/**
	 * 状态0:正常 1:废弃
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createdAt;
	/**
	 * 
	 */
	private Integer createdBy;
	/**
	 * 
	 */
	private Date updatedAt;
	/**
	 * 
	 */
	private Integer updatedBy;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemsId() {
		return itemsId;
	}

	public void setItemsId(String itemsId) {
		this.itemsId = itemsId;
	}

	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	public Integer getNoteType() {
		return noteType;
	}

	public void setNoteType(Integer noteType) {
		this.noteType = noteType;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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
