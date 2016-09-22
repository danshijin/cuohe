package com.smm.cuohe.domain.dealmake;

import java.util.List;

import com.smm.cuohe.domain.AttrValue;

/**
 * 
 * 商品
 * @author tantaigen
 *
 */
public class CommodityEntity {
	private Integer id ;//ID
	private String name ;//商品名称
	private String prodId;//产品ID
	private Integer catId ;//品类编号
	private String  catName ; //品类名称
	private  String  createTime; //创建时间
	private List<AttrValue> attrValuelist;
	public List<AttrValue> getAttrValuelist() {
		return attrValuelist;
	}
	public void setAttrValuelist(List<AttrValue> attrValuelist) {
		this.attrValuelist = attrValuelist;
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

	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
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

}
