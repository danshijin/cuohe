package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChOrdersEntity {
    private Integer id;

    private String ordercode;

    private Integer itemid;

    private Integer sellid;

    private Integer buyid;

    private Integer delivery;

    private Integer receipttype;

    private Integer payment;

    private Integer wareProvince;

    private Integer wareId;

    private String wareName;

    private Date deliverydate;

    private Float pounddiff;

    private Integer ttype;

    private Integer orderstatus;

    private Integer status;

    private Integer source;

    private Date createdat;

    private Integer createdby;

    private Date updatedat;

    private Integer updatedby;

    private String mallSellerAccount;

    private String mallBuyerAccount;

    private Integer sourceId;

    private Integer confirmstatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode == null ? null : ordercode.trim();
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public Integer getSellid() {
        return sellid;
    }

    public void setSellid(Integer sellid) {
        this.sellid = sellid;
    }

    public Integer getBuyid() {
        return buyid;
    }

    public void setBuyid(Integer buyid) {
        this.buyid = buyid;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public Integer getReceipttype() {
        return receipttype;
    }

    public void setReceipttype(Integer receipttype) {
        this.receipttype = receipttype;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getWareProvince() {
        return wareProvince;
    }

    public void setWareProvince(Integer wareProvince) {
        this.wareProvince = wareProvince;
    }

    public Integer getWareId() {
        return wareId;
    }

    public void setWareId(Integer wareId) {
        this.wareId = wareId;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName == null ? null : wareName.trim();
    }

    public Date getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    public Float getPounddiff() {
        return pounddiff;
    }

    public void setPounddiff(Float pounddiff) {
        this.pounddiff = pounddiff;
    }

    public Integer getTtype() {
        return ttype;
    }

    public void setTtype(Integer ttype) {
        this.ttype = ttype;
    }

    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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

    public String getMallSellerAccount() {
        return mallSellerAccount;
    }

    public void setMallSellerAccount(String mallSellerAccount) {
        this.mallSellerAccount = mallSellerAccount == null ? null : mallSellerAccount.trim();
    }

    public String getMallBuyerAccount() {
        return mallBuyerAccount;
    }

    public void setMallBuyerAccount(String mallBuyerAccount) {
        this.mallBuyerAccount = mallBuyerAccount == null ? null : mallBuyerAccount.trim();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getConfirmstatus() {
        return confirmstatus;
    }

    public void setConfirmstatus(Integer confirmstatus) {
        this.confirmstatus = confirmstatus;
    }
}