package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChCustomsBlackListEntity {
    private Integer id;

    private Integer customerid;

    private Integer blackcustomerid;

    private Date updatedat;

    private Date createdat;

    private Integer status;

    private Integer createdby;

    private Integer updatedby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getBlackcustomerid() {
        return blackcustomerid;
    }

    public void setBlackcustomerid(Integer blackcustomerid) {
        this.blackcustomerid = blackcustomerid;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Integer updatedby) {
        this.updatedby = updatedby;
    }
}