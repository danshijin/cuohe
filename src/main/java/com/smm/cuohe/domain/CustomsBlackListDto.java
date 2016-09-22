package com.smm.cuohe.domain;

import java.util.Date;

public class CustomsBlackListDto {
    private Integer id;

    private Integer customerid;

    private Integer blackcustomerid;

    private Date updatedat;

    private Date createdat;

    private Integer status;

    private Integer createdby;

    private Integer updatedby;
    //公司名称(客户)
    private String companyName;
    //公司名称(黑名单)
    private String blackCompanyName;
    //企业类型CODE
    private String entTypeCode;
    //企业类型
    private String entTypes;
    //采购额
    private String salesVolume;
    //采购品牌
    private String buybrand;
    //联系人
    private String contacterName;
    //联系电话
    private String contacterPhone;
    //创建人
    private String createName;
    //创建时间
    private String createTime;
    

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBlackCompanyName() {
		return blackCompanyName;
	}

	public void setBlackCompanyName(String blackCompanyName) {
		this.blackCompanyName = blackCompanyName;
	}

	public String getEntTypeCode() {
		return entTypeCode;
	}

	public void setEntTypeCode(String entTypeCode) {
		this.entTypeCode = entTypeCode;
	}

	public String getEntTypes() {
		return entTypes;
	}

	public void setEntTypes(String entTypes) {
		this.entTypes = entTypes;
	}

	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}

	public String getBuybrand() {
		return buybrand;
	}

	public void setBuybrand(String buybrand) {
		this.buybrand = buybrand;
	}

	public String getContacterName() {
		return contacterName;
	}

	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	}

	public String getContacterPhone() {
		return contacterPhone;
	}

	public void setContacterPhone(String contacterPhone) {
		this.contacterPhone = contacterPhone;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getBlackcustomerid() {
        return blackcustomerid;
    }

    public void setBlackcustomerid(Integer blackcustomerid) {
        this.blackcustomerid = blackcustomerid;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Integer updatedby) {
        this.updatedby = updatedby;
    }
}