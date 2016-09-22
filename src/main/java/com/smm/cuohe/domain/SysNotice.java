package com.smm.cuohe.domain;

/**
 * 系统通知
 * 
 * @author tantaigen
 *
 */
public class SysNotice {
	private Integer id;// 主键 记录号
	private String noticeText;// 消息类容
	private String createdAt;// 创建时间
	private Integer isRead;// 是否已读 0 已读 1 未读
	private String readTime;// 读取时间
	private String customerId;// 会员账号
	private Integer noticeType;// 事件类型（ 1 订单成交、2 价格失败、 3 交易失败、 4 交易关闭、5 新出价）
	 private PageParameter parameter;//parameter
	// private String updatedAt;//修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNoticeText() {
		return noticeText;
	}

	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	// public String getUpdatedAt() {
	// return updatedAt;
	// }
	// public void setUpdatedAt(String updatedAt) {
	// this.updatedAt = updatedAt;
	// }

	public PageParameter getParameter() {
		return parameter;
	}

	public void setParameter(PageParameter parameter) {
		this.parameter = parameter;
	}

	
}
