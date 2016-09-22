package com.smm.cuohe.domain.base;

import java.math.BigDecimal;
import java.util.Date;

public class ChSellPoolEntity {
    private Integer id;

    private Integer itemsid;

    private Integer customerid;

    private Float quantity;

    private Integer unit;

    private Integer wareProvince;

    private Integer wareId;

    private String wareName;

    private BigDecimal price;

    private Integer priority;

    private String commands;

    private Integer status;

    private Date createdat;

    private Integer createdby;

    private Date updatedat;

    private Integer updatedby;

    private String code;

    private Integer volume;

    private Integer commodityId;

    private Integer mallSaleId;

    private String mallUserAccount;

    private Integer paytype;

    private Integer receipttype;

    private Integer delivery;

    private String deliverytime;

    private String moq;

    private String outmoq;

    private String overflow;

    private Integer source;

    private Integer lastpricetype;

    private BigDecimal lastprice;

    private Integer lastcustomerid;

    private String lastmallaccount;

    private String lastcustomername;

    private Integer poolpricecount;

    private String lasttime;

    private Integer isconfirm;

    private String orderid;

    private Integer pricetype;

    private Integer isclose;

    private BigDecimal initprice;

    private String futuresmonth;

    private Integer initpricetype;

    private Integer createsource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemsid() {
        return itemsid;
    }

    public void setItemsid(Integer itemsid) {
        this.itemsid = itemsid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands == null ? null : commands.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getMallSaleId() {
        return mallSaleId;
    }

    public void setMallSaleId(Integer mallSaleId) {
        this.mallSaleId = mallSaleId;
    }

    public String getMallUserAccount() {
        return mallUserAccount;
    }

    public void setMallUserAccount(String mallUserAccount) {
        this.mallUserAccount = mallUserAccount == null ? null : mallUserAccount.trim();
    }

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }

    public Integer getReceipttype() {
        return receipttype;
    }

    public void setReceipttype(Integer receipttype) {
        this.receipttype = receipttype;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime == null ? null : deliverytime.trim();
    }

    public String getMoq() {
        return moq;
    }

    public void setMoq(String moq) {
        this.moq = moq == null ? null : moq.trim();
    }

    public String getOutmoq() {
        return outmoq;
    }

    public void setOutmoq(String outmoq) {
        this.outmoq = outmoq == null ? null : outmoq.trim();
    }

    public String getOverflow() {
        return overflow;
    }

    public void setOverflow(String overflow) {
        this.overflow = overflow == null ? null : overflow.trim();
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getLastpricetype() {
        return lastpricetype;
    }

    public void setLastpricetype(Integer lastpricetype) {
        this.lastpricetype = lastpricetype;
    }

    public BigDecimal getLastprice() {
        return lastprice;
    }

    public void setLastprice(BigDecimal lastprice) {
        this.lastprice = lastprice;
    }

    public Integer getLastcustomerid() {
        return lastcustomerid;
    }

    public void setLastcustomerid(Integer lastcustomerid) {
        this.lastcustomerid = lastcustomerid;
    }

    public String getLastmallaccount() {
        return lastmallaccount;
    }

    public void setLastmallaccount(String lastmallaccount) {
        this.lastmallaccount = lastmallaccount == null ? null : lastmallaccount.trim();
    }

    public String getLastcustomername() {
        return lastcustomername;
    }

    public void setLastcustomername(String lastcustomername) {
        this.lastcustomername = lastcustomername == null ? null : lastcustomername.trim();
    }

    public Integer getPoolpricecount() {
        return poolpricecount;
    }

    public void setPoolpricecount(Integer poolpricecount) {
        this.poolpricecount = poolpricecount;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime == null ? null : lasttime.trim();
    }

    public Integer getIsconfirm() {
        return isconfirm;
    }

    public void setIsconfirm(Integer isconfirm) {
        this.isconfirm = isconfirm;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Integer getPricetype() {
        return pricetype;
    }

    public void setPricetype(Integer pricetype) {
        this.pricetype = pricetype;
    }

    public Integer getIsclose() {
        return isclose;
    }

    public void setIsclose(Integer isclose) {
        this.isclose = isclose;
    }

    public BigDecimal getInitprice() {
        return initprice;
    }

    public void setInitprice(BigDecimal initprice) {
        this.initprice = initprice;
    }

    public String getFuturesmonth() {
        return futuresmonth;
    }

    public void setFuturesmonth(String futuresmonth) {
        this.futuresmonth = futuresmonth == null ? null : futuresmonth.trim();
    }

    public Integer getInitpricetype() {
        return initpricetype;
    }

    public void setInitpricetype(Integer initpricetype) {
        this.initpricetype = initpricetype;
    }

    public Integer getCreatesource() {
        return createsource;
    }

    public void setCreatesource(Integer createsource) {
        this.createsource = createsource;
    }
}