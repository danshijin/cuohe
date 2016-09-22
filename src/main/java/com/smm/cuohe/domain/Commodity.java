package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyutao
 *
 */
public class Commodity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2949072152112250774L;

	private Integer id;
	
	private String name;
	
	private Integer prodId;
	
	private Integer catId;
	
	private String catName;
	
	private String createTime;
	
	private List<CommodityAttr> attrList;

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

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<CommodityAttr> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<CommodityAttr> attrList) {
		this.attrList = attrList;
	}
	
}
