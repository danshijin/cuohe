package com.smm.cuohe.domain.base;

import java.util.Date;

public class MlTotalMallEntity {
    private Long id;

    private String mallno;

    private String name;

    private Integer catid;

    private String catname;

    private Integer ordercount;

    private Integer companycount;

    private Integer createtime;

    private Date createdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMallno() {
        return mallno;
    }

    public void setMallno(String mallno) {
        this.mallno = mallno == null ? null : mallno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname == null ? null : catname.trim();
    }

    public Integer getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(Integer ordercount) {
        this.ordercount = ordercount;
    }

    public Integer getCompanycount() {
        return companycount;
    }

    public void setCompanycount(Integer companycount) {
        this.companycount = companycount;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}