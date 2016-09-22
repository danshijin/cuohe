package com.smm.cuohe.domain.base;

import java.math.BigDecimal;
import java.util.Date;

public class ChDiscussChatRecordEntity {
    private Integer id;

    private Integer chatid;

    private Integer type;

    private String content;

    private BigDecimal price;

    private BigDecimal volumn;

    private String chatfromtype;

    private Integer chatfromid;

    private Integer status;

    private Integer employeeid;

    private Date createdat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChatid() {
        return chatid;
    }

    public void setChatid(Integer chatid) {
        this.chatid = chatid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolumn() {
        return volumn;
    }

    public void setVolumn(BigDecimal volumn) {
        this.volumn = volumn;
    }

    public String getChatfromtype() {
        return chatfromtype;
    }

    public void setChatfromtype(String chatfromtype) {
        this.chatfromtype = chatfromtype == null ? null : chatfromtype.trim();
    }

    public Integer getChatfromid() {
        return chatfromid;
    }

    public void setChatfromid(Integer chatfromid) {
        this.chatfromid = chatfromid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }
}