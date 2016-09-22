package com.smm.cuohe.domain.dealmake;



/**
 * 产品
 * @author tantaigen
 *
 */
public class ProductEntity {

	
	private  Integer id; //ID
	private  Integer catId; //品目ID
	private  String  name; //产品名称
	private  String  catName;//品目名称
	private String createTime;//创建时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
	
	
	
	
}
