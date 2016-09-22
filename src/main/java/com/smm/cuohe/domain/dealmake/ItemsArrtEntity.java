package com.smm.cuohe.domain.dealmake;


/*
 * Auth tantaigen
 * 商品属性
 * 
 * 
 */

public class ItemsArrtEntity {
	private Integer id;  //ID
	private String name; //属性名称 
	private  String options;//属性选项
	
	
	
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
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}

	
	
	
}
