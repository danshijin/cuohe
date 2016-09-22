package com.smm.cuohe.domain.counts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.smm.cuohe.domain.PageParameter;

public class ChatCount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer employeeId;  //撮合员id
	public String employeeName; //撮合员名
	public String onlineTime;   //撮合员在线时长
	public Integer chatTotal;   //收到的会话请求
	public Integer chatValid;   //有效的沟通次数
	public String  chatTime;    //沟通总时长
	public Integer createCustomerTotal; //生成用户总数
	public Integer createOrderTotal; //生成订单数
	public BigDecimal orderAmount;  //订单总金额
	public PageParameter parameter;//分页对象
	public Date currentTime;//当前时间
	public Date startDate;//起始时间
	public Date endDate;//截止时间
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Integer getChatTotal() {
		return chatTotal;
	}
	public void setChatTotal(Integer chatTotal) {
		this.chatTotal = chatTotal;
	}
	public Integer getChatValid() {
		return chatValid;
	}
	public void setChatValid(Integer chatValid) {
		this.chatValid = chatValid;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	public Integer getCreateCustomerTotal() {
		return createCustomerTotal;
	}
	public void setCreateCustomerTotal(Integer createCustomerTotal) {
		this.createCustomerTotal = createCustomerTotal;
	}
	public Integer getCreateOrderTotal() {
		return createOrderTotal;
	}
	public void setCreateOrderTotal(Integer createOrderTotal) {
		this.createOrderTotal = createOrderTotal;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Date getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	public PageParameter getParameter() {
		return parameter;
	}
	public void setParameter(PageParameter parameter) {
		this.parameter = parameter;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
