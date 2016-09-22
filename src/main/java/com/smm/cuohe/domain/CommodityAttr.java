package com.smm.cuohe.domain;

import java.io.Serializable;

/** 
 * @author  zhaoyutao
 * @version 2015年9月11日 下午5:05:04 
 * 商品扩展属性表
 */

public class CommodityAttr implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5084244663292989458L;

	private Integer id;
	
	private Integer commodityId;
	
	private Integer attrId;
	
	private String attrName;
	
	private String attrValue;
	
	private String commodityName;
	
	private String createTime;
	
	private String editTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
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

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
}
