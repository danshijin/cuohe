package com.smm.cuohe.domain;

import java.io.Serializable;

/**
 * @author zhaoyutao
 *
 */
public class Modules implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6720391556330209209L;
	
	private Integer id;
	private String name;
	private Integer Status;
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
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	
}
