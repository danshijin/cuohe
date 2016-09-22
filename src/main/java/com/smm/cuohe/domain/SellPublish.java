package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

/**发布到商城
 * @author zhangnan
 *
 */
public class SellPublish implements Serializable{

    
    
		/**  描述   (@author: Nancy/张楠) */      
	    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1657736545738726992L;

	private Integer id;

    private Integer sellpoolid;//卖盘池ID 

    private String account;//商城用户名
    
    private Integer term;//挂牌有效期  可选项：  当天  本周  本月  三月  半年  指定时间
    
    private Integer terms;

    private Date termdate;//挂牌有效期   当Term为5时，该项必填
    
    private String termdateString;

    private Integer deliverytype;//交货方式    0：卖方送货  1:买方自提

    private Date deliverydate;//交货日期
    
    private String deliverydateString;

    private Integer deliverydatetype;//交货日期方式    0:指定日期  1：指定区间 2：成交后X天

    private Date datebegin;//  开始日期/指定日期
    
    private String datebeginString;

    private Date dateend;// 结束日期 
    
    private String dateendString;

    private Integer days;//  成交后X天

    private Integer pounddiff;//  磅差  除以1000

    private Integer beginordercount;//起订量

    private Integer step;//递增量

    private Integer payment;//结算方式  0：现汇  1：承兑(三月) 2:承兑(六月)

    private Float price;//  价格

    private Integer surrogate;//  是否代收代付

    private Date createdat;//  创建时间

    private Integer createdby;// 创建者

    private Date updatedat;//修改时间

    private Integer updatedby;// 修改者
    
    private String contact;//联系人
    
    private String telephone;//电话
    
    private Integer wareId;//仓库ID
    
    private Integer quantity;//库存
    
    private String gatid;//商城列别   对应items表中的categoryID

	public Integer getWareId() {
		return wareId;
	}

	public void setWareId(Integer wareId) {
		this.wareId = wareId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSellpoolid() {
		return sellpoolid;
	}

	public void setSellpoolid(Integer sellpoolid) {
		this.sellpoolid = sellpoolid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Date getTermdate() {
		return termdate;
	}

	public void setTermdate(Date termdate) {
		this.termdate = termdate;
	}

	public Integer getDeliverytype() {
		return deliverytype;
	}

	public void setDeliverytype(Integer deliverytype) {
		this.deliverytype = deliverytype;
	}

	public Date getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public Integer getDeliverydatetype() {
		return deliverydatetype;
	}

	public void setDeliverydatetype(Integer deliverydatetype) {
		this.deliverydatetype = deliverydatetype;
	}

	public Date getDatebegin() {
		return datebegin;
	}

	public void setDatebegin(Date datebegin) {
		this.datebegin = datebegin;
	}

	public Date getDateend() {
		return dateend;
	}

	public void setDateend(Date dateend) {
		this.dateend = dateend;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getPounddiff() {
		return pounddiff;
	}

	public void setPounddiff(Integer pounddiff) {
		this.pounddiff = pounddiff;
	}

	public Integer getBeginordercount() {
		return beginordercount;
	}

	public void setBeginordercount(Integer beginordercount) {
		this.beginordercount = beginordercount;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getSurrogate() {
		return surrogate;
	}

	public void setSurrogate(Integer surrogate) {
		this.surrogate = surrogate;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	public String getTermdateString() {
		return termdateString;
	}

	public void setTermdateString(String termdateString) {
		this.termdateString = termdateString;
	}

	public String getDeliverydateString() {
		return deliverydateString;
	}

	public void setDeliverydateString(String deliverydateString) {
		this.deliverydateString = deliverydateString;
	}

	public String getDatebeginString() {
		return datebeginString;
	}

	public void setDatebeginString(String datebeginString) {
		this.datebeginString = datebeginString;
	}

	public String getDateendString() {
		return dateendString;
	}

	public void setDateendString(String dateendString) {
		this.dateendString = dateendString;
	}

	public Integer getTerms() {
		return terms;
	}

	public void setTerms(Integer terms) {
		this.terms = terms;
	}

	public String getGatid() {
		return gatid;
	}

	public void setGatid(String gatid) {
		this.gatid = gatid;
	}

}