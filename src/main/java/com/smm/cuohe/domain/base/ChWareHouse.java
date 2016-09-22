package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChWareHouse {
    private Integer id;

    private String catid;

    private Integer itemid;

    private String name;

    private String company;

    private String address;

    private Integer areaid;

    private Integer status;

    private Date createdat;

    private Integer createdby;

    private Date updateat;

    private Integer updateby;

    private Boolean issupervise;

    private Boolean iswhole;

    private Boolean iscooperate;

    private String createuser;

    private Boolean isself;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid == null ? null : catid.trim();
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
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

    public Date getUpdateat() {
        return updateat;
    }

    public void setUpdateat(Date updateat) {
        this.updateat = updateat;
    }

    public Integer getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Integer updateby) {
        this.updateby = updateby;
    }

    public Boolean getIssupervise() {
        return issupervise;
    }

    public void setIssupervise(Boolean issupervise) {
        this.issupervise = issupervise;
    }

    public Boolean getIswhole() {
        return iswhole;
    }

    public void setIswhole(Boolean iswhole) {
        this.iswhole = iswhole;
    }

    public Boolean getIscooperate() {
        return iscooperate;
    }

    public void setIscooperate(Boolean iscooperate) {
        this.iscooperate = iscooperate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Boolean getIsself() {
        return isself;
    }

    public void setIsself(Boolean isself) {
        this.isself = isself;
    }
}