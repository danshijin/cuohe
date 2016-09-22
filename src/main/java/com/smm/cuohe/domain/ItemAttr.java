package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品属性实体类
 *
 * @author zhangnan
 */
public class ItemAttr implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -870024333446589661L;
    private Integer id;
    private Integer itemid;
    private String name;
    private Integer fillmode;//填写方式
    private Integer dataregural;//数据规范
    private String defaulta;//默认值
    private String options;//可选值
    private int mainproperty;//主要属性
    private int required;//是否必填
    private Integer status;
    private Integer listorder;//排序
    private Date createdat;
    private Integer createdby;
    private Date updatedat;
    private Integer updatedby;
    private List<String> optionsValue;
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
		this.name = name;
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
	public String getDefaulta() {
		return defaulta;
	}
	public void setDefaulta(String defaulta) {
		this.defaulta = defaulta;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public int getMainproperty() {
		return mainproperty;
	}
	public void setMainproperty(int mainproperty) {
		this.mainproperty = mainproperty;
	}
	public int getRequired() {
		return required;
	}
	public void setRequired(int required) {
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
	public List<String> getOptionsValue() {
		return optionsValue;
	}
	public void setOptionsValue(List<String> optionsValue) {
		this.optionsValue = optionsValue;
	}

    
    
}