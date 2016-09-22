package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChItemAttrEntity {
    private Integer id;

    private Integer itemid;

    private String name;

    private Integer fillmode;

    private Integer dataregural;

    private String defaultValue;

    private Integer mainproperty;

    private Integer required;

    private Integer status;

    private Integer listorder;

    private Date createdat;

    private Integer createdby;

    private Date updatedat;

    private Integer updatedby;

    private String options;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getFillmode() {
        return fillmode;
    }

    public void setFillmode(Integer fillmode) {
        this.fillmode = fillmode;
    }

    public Integer getDataregural() {
        return dataregural;
    }

    public void setDataregural(Integer dataregural) {
        this.dataregural = dataregural;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public Integer getMainproperty() {
        return mainproperty;
    }

    public void setMainproperty(Integer mainproperty) {
        this.mainproperty = mainproperty;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getListorder() {
        return listorder;
    }

    public void setListorder(Integer listorder) {
        this.listorder = listorder;
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options == null ? null : options.trim();
    }
}