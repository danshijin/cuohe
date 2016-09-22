package com.smm.cuohe.domain;

import java.io.Serializable;

public class WebPartsPOJO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290895030595724767L;
	private int id;//界面组件表id
	private String name;//界面组件名称
	private String status;//界面组件状态
	private int listOrder;//排序序号
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
}
