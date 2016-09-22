package com.smm.cuohe.domain.base;

import java.util.Date;

public class MlMallAttrExtendEntity {
    private Integer id;

    private Integer attrid;

    private String attrname;

    private Long totalmallid;

    private Integer catid;

    private String name;

    private Boolean status;

    private Integer createtime;

    private Date createdate;

    private Integer edittime;

    private Date editdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttrid() {
        return attrid;
    }

    public void setAttrid(Integer attrid) {
        this.attrid = attrid;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname == null ? null : attrname.trim();
    }

    public Long getTotalmallid() {
        return totalmallid;
    }

    public void setTotalmallid(Long totalmallid) {
        this.totalmallid = totalmallid;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Integer getEdittime() {
        return edittime;
    }

    public void setEdittime(Integer edittime) {
        this.edittime = edittime;
    }

    public Date getEditdate() {
        return editdate;
    }

    public void setEditdate(Date editdate) {
        this.editdate = editdate;
    }
}