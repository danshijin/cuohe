package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChNotesEntity {
    private Integer id;

    private Integer itemsid;

    private Date notedate;

    private Integer notetype;

    private String context;

    private Integer status;

    private Date createdat;

    private Integer createdby;

    private Date updatedat;

    private Integer updatedby;

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

    public Date getNotedate() {
        return notedate;
    }

    public void setNotedate(Date notedate) {
        this.notedate = notedate;
    }

    public Integer getNotetype() {
        return notetype;
    }

    public void setNotetype(Integer notetype) {
        this.notetype = notetype;
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
}