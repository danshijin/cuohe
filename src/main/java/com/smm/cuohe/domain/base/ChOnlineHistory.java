package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChOnlineHistory {
    private Integer id;

    private Integer employeeid;

    private Date createtime;
    
    private Date endTime;
    
    private Date leaveTime;
    
    private Integer onlinetype;

    private Integer logintype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getOnlinetype() {
        return onlinetype;
    }

    public void setOnlinetype(Integer onlinetype) {
        this.onlinetype = onlinetype;
    }

    public Integer getLogintype() {
        return logintype;
    }

    public void setLogintype(Integer logintype) {
        this.logintype = logintype;
    }

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
    
}