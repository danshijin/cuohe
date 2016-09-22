package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChTrailPoolEntity {
    private Integer id;

    private Integer itemid;

    private Integer userid;

    private Integer categorysource;

    private String nexttime;

    private String nextcontext;

    private Integer status;

    private Date createdat;

    private Integer createdby;

    private Date updatedat;

    private Integer updatedby;

    private Integer categorycoo;

    private String companyname;

    private String salesproducts;

    private String address;

    private String corporate;

    private String companyphone;

    private String compaysite;

    private String enttypes;

    private Integer categoryemployee;

    private Integer salesvolume;

    private String strregisterdate;

    private Integer companyproperty;

    private Integer areaid;

    private Integer city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCategorysource() {
        return categorysource;
    }

    public void setCategorysource(Integer categorysource) {
        this.categorysource = categorysource;
    }

    public String getNexttime() {
        return nexttime;
    }

    public void setNexttime(String nexttime) {
        this.nexttime = nexttime == null ? null : nexttime.trim();
    }

    public String getNextcontext() {
        return nextcontext;
    }

    public void setNextcontext(String nextcontext) {
        this.nextcontext = nextcontext == null ? null : nextcontext.trim();
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

    public Integer getCategorycoo() {
        return categorycoo;
    }

    public void setCategorycoo(Integer categorycoo) {
        this.categorycoo = categorycoo;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    public String getSalesproducts() {
        return salesproducts;
    }

    public void setSalesproducts(String salesproducts) {
        this.salesproducts = salesproducts == null ? null : salesproducts.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate == null ? null : corporate.trim();
    }

    public String getCompanyphone() {
        return companyphone;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone == null ? null : companyphone.trim();
    }

    public String getCompaysite() {
        return compaysite;
    }

    public void setCompaysite(String compaysite) {
        this.compaysite = compaysite == null ? null : compaysite.trim();
    }

    public String getEnttypes() {
        return enttypes;
    }

    public void setEnttypes(String enttypes) {
        this.enttypes = enttypes == null ? null : enttypes.trim();
    }

    public Integer getCategoryemployee() {
        return categoryemployee;
    }

    public void setCategoryemployee(Integer categoryemployee) {
        this.categoryemployee = categoryemployee;
    }

    public Integer getSalesvolume() {
        return salesvolume;
    }

    public void setSalesvolume(Integer salesvolume) {
        this.salesvolume = salesvolume;
    }

    public String getStrregisterdate() {
        return strregisterdate;
    }

    public void setStrregisterdate(String strregisterdate) {
        this.strregisterdate = strregisterdate == null ? null : strregisterdate.trim();
    }

    public Integer getCompanyproperty() {
        return companyproperty;
    }

    public void setCompanyproperty(Integer companyproperty) {
        this.companyproperty = companyproperty;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }
}