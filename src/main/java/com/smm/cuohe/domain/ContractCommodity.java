package com.smm.cuohe.domain;

import java.io.Serializable;

//合同中的商品列表
/**
 * @author zhaoyutao
 *
 */
public class ContractCommodity  implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 701495636479807868L;

	private String name;  //产品名称
	
	private String extAttrOne; //品牌
	
	private String unit; //计量单位
	
	private Double quantity; //数量
	
	private Double price; //单位/元
	
	private Double totalPrice;//总金额/元
	
	private String totolPriceChs; //大写金额

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTotolPriceChs() {
		return totolPriceChs;
	}

	public void setTotolPriceChs(String totolPriceChs) {
		this.totolPriceChs = totolPriceChs;
	}

	public String getExtAttrOne() {
		return extAttrOne;
	}

	public void setExtAttrOne(String extAttrOne) {
		this.extAttrOne = extAttrOne;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
