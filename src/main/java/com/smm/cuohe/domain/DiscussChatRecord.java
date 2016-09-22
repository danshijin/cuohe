package com.smm.cuohe.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DiscussChatRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7576167981948102336L;

	private Integer id;
	
	private Integer chatId;
	
	private Integer type; //0：普通信息；1：议价；2：确认订单；3：系统消息
	
	private String typeText;
	
	private String content;
	
	private BigDecimal price;
	
	private BigDecimal volumn;
	
	private String chatFromType;
	
	private Integer chatFromId;
	
	private Integer status; //分为两种情况1：议价 0等待确认、1接收、2拒绝、3超时、4撤回；2：确认订单 0等待确认、1接收、2拒绝、3接收并生成合同、4超时、5撤回
	
	private Integer employeeId;
	
	private Date createdAt;
	
	private Date sysTime;
	
	private Date maxCreateTime;
	
	private Date minCreateTime;
	
	
	public Date getSysTime() {
		return sysTime;
	}

	public void setSysTime(Date sysTime) {
		this.sysTime = sysTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}

	public String getChatFromType() {
		return chatFromType;
	}

	public void setChatFromType(String chatFromType) {
		this.chatFromType = chatFromType;
	}

	public Integer getChatFromId() {
		return chatFromId;
	}

	public void setChatFromId(Integer chatFromId) {
		this.chatFromId = chatFromId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	public Date getMaxCreateTime() {
		return maxCreateTime;
	}

	public void setMaxCreateTime(Date maxCreateTime) {
		this.maxCreateTime = maxCreateTime;
	}

	public Date getMinCreateTime() {
		return minCreateTime;
	}

	public void setMinCreateTime(Date minCreateTime) {
		this.minCreateTime = minCreateTime;
	}
	
	
}
