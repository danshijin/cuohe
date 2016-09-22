package com.smm.cuohe.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author  zhaoyutao
 * @version 2015年11月16日 下午6:47:03
 * 报盘视图类
 */

public class BusinessOffer implements Serializable{
	
	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5162834983780394943L;

	private Integer id;  //报盘ID
	
	private Integer poolType; //报盘类型 1 卖盘 2 买盘
	
	private String poolTypeText;
	
	private Integer customerId; //客户id
	
	private String companyName; //客户公司名
	
	private List<CommodityAttr> attr;  //扩展属性
	
	private Integer priceType; //价格类型 0 实价 1 升贴水
	
	private Integer counterNum; //换盘数
	
	private BigDecimal price; //报盘价
	
	private String priceText; //报价内容
	
	private String wareName; //仓库名
	
	private Date publishTime; //发布时间
	
	//private List<> // 还盘
	
	private BigDecimal lastPrice; //最新还盘价
	
	private Integer lastPriceType; //最新还盘价格类型
	
	private String lastPriceText; //最新还盘价格
	
	private String lastCustomerId; //最后报价会员账号
	
	private String lastCustomerName; //最后还盘客户名称
	
	private Date lastTime; //最后还盘时间
	
	private Integer isConfirm; //是否成交 0 未成交 1 已成交
	
	private String orderId; //订单编号
	
	private BigDecimal quantity; //报盘数量
	
	private List<Contacter> contacters;
	
	private Integer isClose;
	
	private Integer status;
	
	private Integer createSource;//报盘来源

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateSource() {
		return createSource;
	}

	public void setCreateSource(Integer createSource) {
		this.createSource = createSource;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPoolType() {
		return poolType;
	}

	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}

	public String getPoolTypeText() {
		return poolTypeText;
	}

	public void setPoolTypeText(String poolTypeText) {
		this.poolTypeText = poolTypeText;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<CommodityAttr> getAttr() {
		return attr;
	}

	public void setAttr(List<CommodityAttr> attr) {
		this.attr = attr;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPriceText() {
		return priceText;
	}

	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}

	public Integer getCounterNum() {
		return counterNum;
	}

	public void setCounterNum(Integer counterNum) {
		this.counterNum = counterNum;
	}

	public String getLastCustomerName() {
		return lastCustomerName;
	}

	public void setLastCustomerName(String lastCustomerName) {
		this.lastCustomerName = lastCustomerName;
	}

	public String getLastCustomerId() {
		return lastCustomerId;
	}

	public void setLastCustomerId(String lastCustomerId) {
		this.lastCustomerId = lastCustomerId;
	}

	public List<Contacter> getContacters() {
		return contacters;
	}

	public void setContacters(List<Contacter> contacters) {
		this.contacters = contacters;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Integer getLastPriceType() {
		return lastPriceType;
	}

	public void setLastPriceType(Integer lastPriceType) {
		this.lastPriceType = lastPriceType;
	}

	public String getLastPriceText() {
		return lastPriceText;
	}

	public void setLastPriceText(String lastPriceText) {
		this.lastPriceText = lastPriceText;
	}
	
	
}
