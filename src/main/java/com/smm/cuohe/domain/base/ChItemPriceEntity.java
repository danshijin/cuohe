package com.smm.cuohe.domain.base;

import java.math.BigDecimal;
import java.util.Date;

public class ChItemPriceEntity {
    private Integer id;

    private Integer itemid;

    private String itemname;

    private Integer recommendtype;

    private BigDecimal recommend;

    private Integer hightype;

    private BigDecimal high;

    private Integer lowtype;

    private BigDecimal low;

    private Date cretedat;

    private Integer createdby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname == null ? null : itemname.trim();
    }

    public Integer getRecommendtype() {
        return recommendtype;
    }

    public void setRecommendtype(Integer recommendtype) {
        this.recommendtype = recommendtype;
    }

    public BigDecimal getRecommend() {
        return recommend;
    }

    public void setRecommend(BigDecimal recommend) {
        this.recommend = recommend;
    }

    public Integer getHightype() {
        return hightype;
    }

    public void setHightype(Integer hightype) {
        this.hightype = hightype;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public Integer getLowtype() {
        return lowtype;
    }

    public void setLowtype(Integer lowtype) {
        this.lowtype = lowtype;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public Date getCretedat() {
        return cretedat;
    }

    public void setCretedat(Date cretedat) {
        this.cretedat = cretedat;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }
}