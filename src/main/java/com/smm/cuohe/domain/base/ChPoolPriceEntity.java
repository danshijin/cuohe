package com.smm.cuohe.domain.base;

import java.math.BigDecimal;
import java.util.Date;

public class ChPoolPriceEntity {
    private Integer id;

    private Integer poolid;

    private Integer pooltype;

    private Date createdat;

    private BigDecimal price;

    private Integer customerid;

    private Integer status;

    private Date updatedat;

    private Integer pricetype;

    private String account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoolid() {
        return poolid;
    }

    public void setPoolid(Integer poolid) {
        this.poolid = poolid;
    }

    public Integer getPooltype() {
        return pooltype;
    }

    public void setPooltype(Integer pooltype) {
        this.pooltype = pooltype;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getPricetype() {
        return pricetype;
    }

    public void setPricetype(Integer pricetype) {
        this.pricetype = pricetype;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}