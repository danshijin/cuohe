package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

public class Chats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1807821824566074160L;
	private Integer id;
	private Integer itemId;
	private Integer companyId;
	private String companyName;
	private Integer employeeId;
	private Integer inPool;
	private String ip;
	private Integer area;
	private Integer sourceType;
	private Integer sourceId;
	private Integer orderStatus;
	private Integer status;
	private Date 	createdAt;
	private Integer createdBy;
	private Date 	updatedAt;
	private Integer updatedBy;
	private Date onlineTime;
	private String token;
	private Integer customId;
	private String customerName;
	private Integer productId;
	private String urlName;
	private String url;
	private String sessionId;
	private Integer sellId;
	private Integer source;
	private Integer onlineType;
	
	private Integer recordId;
	private Integer chatId;
	private String lrType;
	private String lrContent;
	private String lrPrpice;
	private String lrVolumn;
	private String lrChatFromType;
	private String lrChatFromId;
	private String lrStatus;
	private String lrEmployeeId;
	private String lrCreatedAt;
	
	
	
	//mysql服务器时间
	private String serverTime;
	
	//合并之前的chatid
	private Integer oid;
	
	
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getInPool() {
		return inPool;
	}
	public void setInPool(Integer inPool) {
		this.inPool = inPool;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getCustomId() {
		return customId;
	}
	public void setCustomId(Integer customId) {
		this.customId = customId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getSellId() {
		return sellId;
	}
	public void setSellId(Integer sellId) {
		this.sellId = sellId;
	}
	public Integer getOnlineType() {
		return onlineType;
	}
	public void setOnlineType(Integer onlineType) {
		this.onlineType = onlineType;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getChatId() {
		return chatId;
	}
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}
	public String getLrType() {
		return lrType;
	}
	public void setLrType(String lrType) {
		this.lrType = lrType;
	}
	public String getLrContent() {
		return lrContent;
	}
	public void setLrContent(String lrContent) {
		this.lrContent = lrContent;
	}
	public String getLrPrpice() {
		return lrPrpice;
	}
	public void setLrPrpice(String lrPrpice) {
		this.lrPrpice = lrPrpice;
	}
	public String getLrVolumn() {
		return lrVolumn;
	}
	public void setLrVolumn(String lrVolumn) {
		this.lrVolumn = lrVolumn;
	}
	public String getLrChatFromType() {
		return lrChatFromType;
	}
	public void setLrChatFromType(String lrChatFromType) {
		this.lrChatFromType = lrChatFromType;
	}
	public String getLrChatFromId() {
		return lrChatFromId;
	}
	public void setLrChatFromId(String lrChatFromId) {
		this.lrChatFromId = lrChatFromId;
	}
	public String getLrStatus() {
		return lrStatus;
	}
	public void setLrStatus(String lrStatus) {
		this.lrStatus = lrStatus;
	}
	public String getLrEmployeeId() {
		return lrEmployeeId;
	}
	public void setLrEmployeeId(String lrEmployeeId) {
		this.lrEmployeeId = lrEmployeeId;
	}
	public String getLrCreatedAt() {
		return lrCreatedAt;
	}
	public void setLrCreatedAt(String lrCreatedAt) {
		this.lrCreatedAt = lrCreatedAt;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Date getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	
}
