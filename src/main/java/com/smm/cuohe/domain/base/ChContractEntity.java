package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChContractEntity {
    private Integer id;

    private Integer orderid;

    private String filepath;

    private String mallurl;

    private Date createdat;

    private Date confirmedat;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getMallurl() {
        return mallurl;
    }

    public void setMallurl(String mallurl) {
        this.mallurl = mallurl == null ? null : mallurl.trim();
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getConfirmedat() {
        return confirmedat;
    }

    public void setConfirmedat(Date confirmedat) {
        this.confirmedat = confirmedat;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}