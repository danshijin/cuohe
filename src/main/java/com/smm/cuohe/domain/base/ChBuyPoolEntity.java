package com.smm.cuohe.domain.base;

import java.math.BigDecimal;
import java.util.Date;

public class ChBuyPoolEntity {
    private Integer id;

    private Integer itemsid;

    private Integer customerid;

    private Double quantity;

    private Integer unit;

    private String title;

    private String context;

    private Integer status;

    private Date createdat;

    private Integer createdby;

    private Date updatedat;

    private Integer updatedby;

    private Integer prostatus;

    private Integer commodityId;

    private Integer mallBuyId;

    private String mallUserAccount;

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

    private BigDecimal price;

    private Integer wareProvince;

    private String wareId;

    private String wareName;

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
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

    public Integer getProstatus() {
        return prostatus;
    }

    public void setProstatus(Integer prostatus) {
        this.prostatus = prostatus;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getMallBuyId() {
        return mallBuyId;
    }

    public void setMallBuyId(Integer mallBuyId) {
        this.mallBuyId = mallBuyId;
    }

    public String getMallUserAccount() {
        return mallUserAccount;
    }

    public void setMallUserAccount(String mallUserAccount) {
        this.mallUserAccount = mallUserAccount == null ? null : mallUserAccount.trim();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getWareProvince() {
        return wareProvince;
    }

    public void setWareProvince(Integer wareProvince) {
        this.wareProvince = wareProvince;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId == null ? null : wareId.trim();
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName == null ? null : wareName.trim();
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