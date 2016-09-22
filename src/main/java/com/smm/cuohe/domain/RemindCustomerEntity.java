package com.smm.cuohe.domain;

public class RemindCustomerEntity {
	private  Integer customerId;
	private String companyName;
	private  String  remindTime; //下次采购时间
	private  String  categoryfreqName;
	private Integer categoryFreq;
	private  String  salesVolumeName;
	private Integer salesVolume;
	private String picName;
	private Integer pic;
	private Integer itemId;
	private String buybrand;
	
	public String getBuybrand() {
		return buybrand;
	}
	public void setBuybrand(String buybrand) {
		this.buybrand = buybrand;
	}
	public String getCategoryfreqName() {
		return categoryfreqName;
	}
	public void setCategoryfreqName(String categoryfreqName) {
		this.categoryfreqName = categoryfreqName;
	}

	public Integer getCategoryFreq() {
		return categoryFreq;
	}
	public void setCategoryFreq(Integer categoryFreq) {
		this.categoryFreq = categoryFreq;
	}
	public String getSalesVolumeName() {
		return salesVolumeName;
	}
	public void setSalesVolumeName(String salesVolumeName) {
		this.salesVolumeName = salesVolumeName;
	}
	public Integer getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public Integer getPic() {
		return pic;
	}
	public void setPic(Integer pic) {
		this.pic = pic;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	
	
	
	
}
