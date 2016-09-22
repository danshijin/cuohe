package com.smm.cuohe.domain;

import java.io.Serializable;

public class SellPoolPOJO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5478976813447367877L;
	private int id;//卖盘池表主键
	private String itemsName;//产品
	private String itemAttrOptions;//主要属性值
	private String itemAttrName;//主要属性名称
	private String price;//价格
	private String extID;//extID
	private String quantity;//库存
	private String wareName;//仓库
	private String companyName;//企业表 企业名称
	private Integer commodityId;
	private String commodityName;
	private Integer createSource;
	
	
	public Integer getCreateSource() {
		return createSource;
	}
	public void setCreateSource(Integer createSource) {
		this.createSource = createSource;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemsName() {
		return itemsName;
	}
	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}
	public String getItemAttrOptions() {
		return itemAttrOptions;
	}
	public void setItemAttrOptions(String itemAttrOptions) {
		this.itemAttrOptions = itemAttrOptions;
	}
	public String getItemAttrName() {
		return itemAttrName;
	}
	public void setItemAttrName(String itemAttrName) {
		this.itemAttrName = itemAttrName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getExtID() {
		return extID;
	}
	public void setExtID(String extID) {
		this.extID = extID;
	}
	public Integer getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	
}
