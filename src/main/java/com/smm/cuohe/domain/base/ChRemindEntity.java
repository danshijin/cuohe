package com.smm.cuohe.domain.base;

import java.util.Date;

public class ChRemindEntity {
    private Integer id;

    private Integer remindid;

    private Integer remindtype;

    private Date reminddate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRemindid() {
        return remindid;
    }

    public void setRemindid(Integer remindid) {
        this.remindid = remindid;
    }

    public Integer getRemindtype() {
        return remindtype;
    }

    public void setRemindtype(Integer remindtype) {
        this.remindtype = remindtype;
    }

    public Date getReminddate() {
        return reminddate;
    }

    public void setReminddate(Date reminddate) {
        this.reminddate = reminddate;
    }
}