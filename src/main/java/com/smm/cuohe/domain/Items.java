package com.smm.cuohe.domain;

import java.io.Serializable;

/**
 * 品目表
 * 
 * @author zengshihua
 *
 */
public class Items implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id;

	/**
	 * 品目名称
	 */
	private String name;
	
	/**
	 * SHFT实时价格代码
	 */
	private String SHFEType;
	
	/**
	 * 商城类别ID
	 */
	private String categoryId;
	
	/**
	 * 走势图ID
	 */
	private String chartId;
	
	/**
	 * 序号
	 */
	
	private int ListOrder;
	
	/**
	 * 状态 0 正常 1 删除
	 */
	private int Status;

	
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

	public int getListOrder() {
		return ListOrder;
	}

	public void setListOrder(int listOrder) {
		ListOrder = listOrder;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getSHFEType() {
		return SHFEType;
	}

	public void setSHFEType(String sHFEType) {
		SHFEType = sHFEType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getChartId() {
		return chartId;
	}

	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	

}
