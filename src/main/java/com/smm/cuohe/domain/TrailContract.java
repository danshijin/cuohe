package com.smm.cuohe.domain;

/**
 * 线索联系人
 * @author tantaigen
 *
 */
public class TrailContract {
	
	private Integer id;
	private Integer trailId;//线索ID
	//职位 
	 private String positions;
	 //联系人 名称
	 private String contactName; 
	 //手机
	 private String mobilePhone;
	 //QQ
	 private String qqNum;
	 //	 邮箱
	 private String email; 
	 private Integer status;//0 正常 1 删除
	 //第一联系人
	 private Integer first;   //0 为第一联系人
	 
	
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	private Integer sex; //性别
	 
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTrailId() {
		return trailId;
	}
	public void setTrailId(Integer trailId) {
		this.trailId = trailId;
	}
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

	 
	 
	 
}
