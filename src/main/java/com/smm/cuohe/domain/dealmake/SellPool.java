package com.smm.cuohe.domain.dealmake;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.ItemAttr;

/**
 * Title: SellPool.java 卖盘池实体类 Description: 描述
 * 
 * @author Nancy/张楠
 * @created 2015年8月4日 下午3:49:17
 */

public class SellPool implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	
	//新增“编号”字段，用于商城排序
	private Integer sortNum;
	
	//新增“编号”字段，用于商城排序 
	private String timeSort;//时间编号
	// 品目ID
	private Integer itemsid;
	// 品目名称
	private String itemsNm;
	private Integer source;
	private Integer companyid;
	private String companyNm;

	private String quantity;// 库存
	private Integer unit;
	private Integer wareProvince;
	
	private Integer wareId;// 仓库ID
	private String wareNm;
	private String wareName;// 仓库名
	private Double price;// 价格
	private Integer priority;// 优先级
	private String commands;// 交易备注
	// private String extid;//拓展属性
	private Integer status;
	private Date createdat;
	private String createdNm;
	private Integer createdby;
	private Date updatedat;
	private Integer updatedby;

	private String code; // 编号 ，只有在商城同步过来时才会有

	private String brand;// 品牌

	private Integer volume; // 成交量

	private int orderNum;// 订单笔数、
	
	private String orderId;//订单编号

	private List<ItemAttr> itemAttrList;// 商品属性

	private List<AttrValue> attrValues;

	private String createdAtStr;

	private String updatedAtStr;

	private String gatid;// 商城列别 对应items表中的categoryID

	private Integer orderStatus;// 订单状态
	
	private Integer isClose;

	/**
	 * 同步商城需要数据
	 */
	private Integer totalmallid; // 商城编号
	private Integer commodityId;// 商品ID
	private Integer mallSaleId; // 商城挂牌号
	private String mallUserAccount; // 商城会员账号
	private Integer paytype; // 结算方式 1现款现汇 2银行承兑 3有色网代收代付
	private Integer receipttype; // 付款方式 1款到发货 2货到付款
	private Integer delivery; // 交货方式 运输方式 1 卖方送货 2 买方自提
	private String deliverytime;// 交货日期(时间戳)
	private String moq;// 起订量
	private String outmoq;// 超出部分按n递增
	private String overflow; // 磅差
	private String username; // 发布者的名字

	private Integer errno;// 0 正常 1 参数错误 255 未知错误
	private String msg;// 返回信息
	private String priceType;
	private String priceTypeName;
	private String futuresMonth;// 期货月
	private Integer createSource;// 订单来源
	
	private Integer customerId;//挂盘公司ID
	 
	private Integer ispledge;//是够抵押订单 0 不是    1是
	
	private String pledgeprice;//质押款
	
	private String serviceprice;//服务费


	/**
	 * 价格类型 0 实价 1 升水 2 贴水
	 */
	private Integer lastPriceType;
	/**
	 * 最后还盘报价
	 */
	private Double lastPrice;
	/**
	 * 最后报价客户编号
	 */
	private Integer lastCustomerId;
	/**
	 * 最后还价商城客户编号
	 */
	private String lastMallAccount;
	/**
	 * 客户名称
	 */
	private String lastCustomerName;
	/**
	 * 还盘总数量
	 */
	private Integer poolPriceCount;
	/**
	 * 最后还盘时间
	 */
	private String lastTime;
	/**
	 * 是否已成交 0 未成交 1 已成交
	 */
	private Integer isConfirm;
	/**
	 * 最后报价客户id
	 */
	private Integer buyCompanyId;
	/**
	 * 最后报价客户名称
	 */
	private String buyCompanyNm;
	
	private String buyCompanyNms;
	
	/*
	 * 初始报价
	 */
	private Double initprice;
	

	private Integer initPriceType;
	

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getTimeSort() {
		return timeSort;
	}

	public void setTimeSort(String timeSort) {
		this.timeSort = timeSort;
	}

	public Integer getItemsid() {
		return itemsid;
	}

	public void setItemsid(Integer itemsid) {
		this.itemsid = itemsid;
	}

	public String getItemsNm() {
		return itemsNm;
	}

	public void setItemsNm(String itemsNm) {
		this.itemsNm = itemsNm;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getCompanyNm() {
		return companyNm;
	}

	public void setCompanyNm(String companyNm) {
		this.companyNm = companyNm;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getWareProvince() {
		return wareProvince;
	}

	public void setWareProvince(Integer wareProvince) {
		this.wareProvince = wareProvince;
	}

	public Integer getWareId() {
		return wareId;
	}

	public void setWareId(Integer wareId) {
		this.wareId = wareId;
	}

	public String getWareNm() {
		return wareNm;
	}

	public void setWareNm(String wareNm) {
		this.wareNm = wareNm;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public String getCreatedNm() {
		return createdNm;
	}

	public void setCreatedNm(String createdNm) {
		this.createdNm = createdNm;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<ItemAttr> getItemAttrList() {
		return itemAttrList;
	}

	public void setItemAttrList(List<ItemAttr> itemAttrList) {
		this.itemAttrList = itemAttrList;
	}

	public List<AttrValue> getAttrValues() {
		return attrValues;
	}

	public void setAttrValues(List<AttrValue> attrValues) {
		this.attrValues = attrValues;
	}

	public String getCreatedAtStr() {
		return createdAtStr;
	}

	public void setCreatedAtStr(String createdAtStr) {
		this.createdAtStr = createdAtStr;
	}

	public String getUpdatedAtStr() {
		return updatedAtStr;
	}

	public void setUpdatedAtStr(String updatedAtStr) {
		this.updatedAtStr = updatedAtStr;
	}

	public String getGatid() {
		return gatid;
	}

	public void setGatid(String gatid) {
		this.gatid = gatid;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	public Integer getTotalmallid() {
		return totalmallid;
	}

	public void setTotalmallid(Integer totalmallid) {
		this.totalmallid = totalmallid;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getMallSaleId() {
		return mallSaleId;
	}

	public void setMallSaleId(Integer mallSaleId) {
		this.mallSaleId = mallSaleId;
	}

	public String getMallUserAccount() {
		return mallUserAccount;
	}

	public void setMallUserAccount(String mallUserAccount) {
		this.mallUserAccount = mallUserAccount;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public Integer getReceipttype() {
		return receipttype;
	}

	public void setReceipttype(Integer receipttype) {
		this.receipttype = receipttype;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public String getDeliverytime() {
		return deliverytime;
	}

	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}

	public String getMoq() {
		return moq;
	}

	public void setMoq(String moq) {
		this.moq = moq;
	}

	public String getOutmoq() {
		return outmoq;
	}

	public void setOutmoq(String outmoq) {
		this.outmoq = outmoq;
	}

	public String getOverflow() {
		return overflow;
	}

	public void setOverflow(String overflow) {
		this.overflow = overflow;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getErrno() {
		return errno;
	}

	public void setErrno(Integer errno) {
		this.errno = errno;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getPriceTypeName() {
		return priceTypeName;
	}

	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}

	public String getFuturesMonth() {
		return futuresMonth;
	}

	public void setFuturesMonth(String futuresMonth) {
		this.futuresMonth = futuresMonth;
	}

	public Integer getCreateSource() {
		return createSource;
	}

	public void setCreateSource(Integer createSource) {
		this.createSource = createSource;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getLastPriceType() {
		return lastPriceType;
	}

	public void setLastPriceType(Integer lastPriceType) {
		this.lastPriceType = lastPriceType;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Integer getLastCustomerId() {
		return lastCustomerId;
	}

	public void setLastCustomerId(Integer lastCustomerId) {
		this.lastCustomerId = lastCustomerId;
	}

	public String getLastMallAccount() {
		return lastMallAccount;
	}

	public void setLastMallAccount(String lastMallAccount) {
		this.lastMallAccount = lastMallAccount;
	}

	public String getLastCustomerName() {
		return lastCustomerName;
	}

	public void setLastCustomerName(String lastCustomerName) {
		this.lastCustomerName = lastCustomerName;
	}

	public Integer getPoolPriceCount() {
		return poolPriceCount;
	}

	public void setPoolPriceCount(Integer poolPriceCount) {
		this.poolPriceCount = poolPriceCount;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}

	public Integer getBuyCompanyId() {
		return buyCompanyId;
	}

	public void setBuyCompanyId(Integer buyCompanyId) {
		this.buyCompanyId = buyCompanyId;
	}

	public String getBuyCompanyNm() {
		return buyCompanyNm;
	}

	public void setBuyCompanyNm(String buyCompanyNm) {
		this.buyCompanyNm = buyCompanyNm;
	}

	public String getBuyCompanyNms() {
		return buyCompanyNms;
	}

	public void setBuyCompanyNms(String buyCompanyNms) {
		this.buyCompanyNms = buyCompanyNms;
	}

	public Double getInitprice() {
		return initprice;
	}

	public void setInitprice(Double initprice) {
		this.initprice = initprice;
	}

	public Integer getInitPriceType() {
		return initPriceType;
	}

	public void setInitPriceType(Integer initPriceType) {
		this.initPriceType = initPriceType;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName == null ? null : wareName.trim();
	}

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands == null ? null : commands.trim();
	}

	public Integer getIspledge() {
		return ispledge;
	}

	public void setIspledge(Integer ispledge) {
		this.ispledge = ispledge;
	}

	public String getPledgeprice() {
		return pledgeprice;
	}

	public void setPledgeprice(String pledgeprice) {
		this.pledgeprice = pledgeprice;
	}

	public String getServiceprice() {
		return serviceprice;
	}

	public void setServiceprice(String serviceprice) {
		this.serviceprice = serviceprice;
	}

	
}