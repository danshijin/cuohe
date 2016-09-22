package com.smm.cuohe.domain;

import java.io.Serializable;

/**
 * 
 * @author dongmiaonan
 *
 */
public class Areas implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 303676871648546049L;

	private Integer id; 
    
	private String name;	

	private String parentID; //父级ID
	
	private String childID;  //子级ID
	
	private Integer listOrder;  //序号
	
	private String parentName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getChildID() {
		return childID;
	}

	public void setChildID(String childID) {
		this.childID = childID;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	 
}
