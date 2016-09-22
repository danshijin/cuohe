package com.smm.cuohe.domain;

import java.io.Serializable;

/**
 * @author zhaoyutao
 *
 */
public class Category  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 224310180478757980L;

	private Integer id;
	
	private Integer modId; //模块表ID
	
	private String name;
	
	private Integer listOrder;  //列表序
	
	private Integer status;  //0:正常 1：废弃

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModId() {
		return modId;
	}

	public void setModId(Integer modId) {
		this.modId = modId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
