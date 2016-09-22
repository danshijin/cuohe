package com.smm.cuohe.domain;

import java.io.Serializable;

/**
 * Created by zhenghao on 2015/7/27.
 *
 */
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

    private String account;
    
    private String name;

    private String email;
    
    private String phone;
    
    private String pwd;
    
    private String  itemId;
    
    private String status;
    
    private Items items;
    
    private Integer online;
    
    private String image;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", name=" + name + ", email=" + email + ", pwd=" + pwd
				+ ", itemId=" + itemId + ", status=" + status + "]";
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}
    
    
}
