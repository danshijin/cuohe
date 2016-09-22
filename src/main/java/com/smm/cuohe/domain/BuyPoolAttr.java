package com.smm.cuohe.domain;

import java.io.Serializable;

/**买盘池属性实体类
 * @author wangyu
 *
 */
public class BuyPoolAttr  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -870024333446589661L;
	private Integer id;
    private Integer buying_id;//买盘编号
    private String item_id;//品目编号
    private Integer attr_id;//属性编号
    private String attr_name;//属性名称
    private String attr_value;//属性值
    private String create_time;//创建时间
    private Integer proid;//产品ID
    private String pro_name;//产品名称
    
    
	public Integer getProid() {
		return proid;
	}
	public void setProid(Integer proid) {
		this.proid = proid;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBuying_id() {
		return buying_id;
	}
	public void setBuying_id(Integer buying_id) {
		this.buying_id = buying_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public Integer getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(Integer attr_id) {
		this.attr_id = attr_id;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getAttr_value() {
		return attr_value;
	}
	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}