package com.smm.cuohe.domain.counts;

import java.util.Date;

public class OrderCountEntity {
	
	
	private String userName;   //用户名称
	private Integer userId;   //用户ID
	private Integer itemsID;  //产品ID
	private Integer qiatancount;//洽谈数
	private Integer builcount; //生成订单数
	private Integer firmcount; //确定订单数
	private Integer companyID; //企业ID
	private String  companyName;//企业名称
	
	private Integer gooscount; //成交数
	private Integer offercount;//报价数
	private Double chenjiaocount ;//成交量
	private Double baofrrmcount ;//商品数
	
	private Integer progooscount; //采购笔数
	private Double prooffercount;//采购量
	private Double profrrmcount ;//成交量
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getItemsID() {
		return itemsID;
	}
	public void setItemsID(Integer itemsID) {
		this.itemsID = itemsID;
	}
	public Integer getQiatancount() {
		return qiatancount;
	}
	public void setQiatancount(Integer qiatancount) {
		this.qiatancount = qiatancount;
	}

	public Integer getBuilcount() {
		return builcount;
	}
	public void setBuilcount(Integer builcount) {
		this.builcount = builcount;
	}
	public Integer getFirmcount() {
		return firmcount;
	}
	public void setFirmcount(Integer firmcount) {
		this.firmcount = firmcount;
	}
	
	public Date getCreateAts() {
		return createAts;
	}
	public void setCreateAts(Date createAts) {
		this.createAts = createAts;
	}
	public Date getCreateAte() {
		return createAte;
	}
	public void setCreateAte(Date createAte) {
		this.createAte = createAte;
	}
	private Date  createAts;      //起止时间
	private Date  createAte;    //最后时间

	public Integer getGooscount() {
		return gooscount;
	}
	public void setGooscount(Integer gooscount) {
		this.gooscount = gooscount;
	}
	public Integer getOffercount() {
		return offercount;
	}
	public void setOffercount(Integer offercount) {
		this.offercount = offercount;
	}

	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getProgooscount() {
		return progooscount;
	}
	public void setProgooscount(Integer progooscount) {
		this.progooscount = progooscount;
	}
	public Double getChenjiaocount() {
		return chenjiaocount;
	}
	public void setChenjiaocount(Double chenjiaocount) {
		this.chenjiaocount = chenjiaocount;
	}
	public Double getBaofrrmcount() {
		return baofrrmcount;
	}
	public void setBaofrrmcount(Double baofrrmcount) {
		this.baofrrmcount = baofrrmcount;
	}
	public Double getProoffercount() {
		return prooffercount;
	}
	public void setProoffercount(Double prooffercount) {
		this.prooffercount = prooffercount;
	}
	public Double getProfrrmcount() {
		return profrrmcount;
	}
	public void setProfrrmcount(Double profrrmcount) {
		this.profrrmcount = profrrmcount;
	}
	
	
	
	
	
}
