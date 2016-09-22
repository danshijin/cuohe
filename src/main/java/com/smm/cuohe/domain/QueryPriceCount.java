package com.smm.cuohe.domain;

public class QueryPriceCount {
	
	private Integer pooId;//挂牌号
	private Integer poolId;//挂牌号
	private Integer isClose;//0 未关闭 1 已关闭
	private Integer isConfirm;//是否已成交 0 未成交 1 已成交
	private Integer poolPriceCount;//还盘总量
	public Integer getPooId() {
		return pooId;
	}
	public void setPooId(Integer pooId) {
		this.pooId = pooId;
	}
	public Integer getIsClose() {
		return isClose;
	}
	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}
	public Integer getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Integer getPoolPriceCount() {
		return poolPriceCount;
	}
	public void setPoolPriceCount(Integer poolPriceCount) {
		this.poolPriceCount = poolPriceCount;
	}
	public Integer getPoolId() {
		return poolId;
	}
	public void setPoolId(Integer poolId) {
		this.poolId = poolId;
	}
	
	

}
