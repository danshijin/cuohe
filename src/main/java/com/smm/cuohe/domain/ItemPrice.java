package com.smm.cuohe.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemPrice implements  Serializable{

	/**
	 * 品目价格
	 */
	private static final long serialVersionUID = 4756117539406110888L;
	
	private Integer id; //
	
	private Integer itemId; //品目id
	
	private String itemName; //品目名称
	
	private Integer recommendType; // 推荐报盘价格类型 0 实价 1 升水 2 贴水
	
	private BigDecimal recommend; //推荐报盘价
	
	private Integer highType; //最高报盘价格类型
	
	private BigDecimal high; //最高报盘价
	
	private Integer lowType; //最低换盘价格类型
	
	private BigDecimal low; //最低还盘价
	
	private Date createdAt; //创建时间
	
	private Integer createdBy; //创建人

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getRecommend() {
		return recommend;
	}

	public void setRecommend(BigDecimal recommend) {
		this.recommend = recommend;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
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
	
	public Integer getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}

	public Integer getHighType() {
		return highType;
	}

	public void setHighType(Integer highType) {
		this.highType = highType;
	}

	public Integer getLowType() {
		return lowType;
	}

	public void setLowType(Integer lowType) {
		this.lowType = lowType;
	}
	
	
	
}
