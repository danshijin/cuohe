package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 联系人
 *
 * @author hanfeihu
 */
public class Contacter implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1656083595614564078L;

    private Integer id;                    //联系人ID
    private Integer customerId;           //客户id
    private String name;                //用户名
    private String position;            //职位
    private Integer sex;                 //性别
    private String mobilePhone;         //手机
    private String telephone;           //固话
    private String qq;                  //QQ
    private String email;               //邮箱
    private Integer keyPerson;           //是否关键人
    private Integer tidentity;           //交易类别
    private Integer status;              //状态
    private Date createdAt;           //创建时间
    private Integer createdBy;           //创建者
    private Date updatedAt;           //修改时间
    private Integer updatedBy;            //修改者
    private Customer customer;
    private User createdUser;
    private User updatedUser;
    private String updatedUserName;

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getKeyPerson() {
        return keyPerson;
    }

    public void setKeyPerson(Integer keyPerson) {
        this.keyPerson = keyPerson;
    }


    public Integer getTidentity() {
        return tidentity;
    }

    public void setTidentity(Integer tidentity) {
        this.tidentity = tidentity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public User getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getUpdatedUserName() {
		return updatedUserName;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}




}
