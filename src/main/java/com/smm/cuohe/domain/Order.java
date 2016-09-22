package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyutao
 *
 */
public class Order implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6812312393008594028L;

	private Integer id;
	
	private String orderCode; //订单编号    商城导入的有订单编号
	
	private Integer itemId;
	
	private Integer sellId;  //卖家企业ID
	
	private Integer buyId;  //买家企业ID
	
	private Integer delivery; //交货方式      0：款到发货  1：货到付款
	
	private Integer payment; //结算方式        0：现汇  1：承兑(三月) 2:承兑(六月)
	
	private Integer receipttype;//付款方式 1款到发货 2货到付款
	
	private Integer ware_province;   //仓库-省份
	
	private String ware_name;   //仓库名字
	
	private Date deliveryDate;   //交货日期
	
	private Float poundDiff;  //磅差 的千分数
	
	private Integer ttype;  //0：卖盘生成订单  1:买盘生成订单
	
	private Integer source; // 订单来源  
	
	private Date createdAt;
	
	private Integer createdBy;
	
	private Date updatedAt;
	
	private Integer updatedBy;
	
	private Integer status;//状态
	
	private List<SubOrder> subOrderList;//订单项
	
	private String sellCompanyNm;//卖方企业名称
	
	private String buyCompanyNm;//买饭企业名称
	
	private String wareNm;//地区（省份）
	
	private String itemNm;//品目名称
	
	private String userNm;//撮合人
	
	private Integer orderStatus;//订单状态
	
	private String createdByName;// 撮合人
	
	private Contract contract; //合同

	
	private Integer confirmStatus;
	
	private Integer wareId;//仓库ID
	
	private String mallSellerAccount;//商城卖方账号
	
	private String mallBuyerAccount;//商城买房账号
	
	private Double totalPrice;
	
	private Integer sourceId;//来源id
	
	private String[] productid;//产品id
	
	private Integer poolType;//报盘类型
	
	private Integer poolId;//报盘ID
	
	private Integer ispledge;//是够抵押订单 0 不是    1是
	
	private String pledgeprice;//质押款
	
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

	private String serviceprice;//服务费
	
	
	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getReceipttype() {
		return receipttype;
	}

	public void setReceipttype(Integer receipttype) {
		this.receipttype = receipttype;
	}

	public String getMallSellerAccount() {
		return mallSellerAccount;
	}

	public void setMallSellerAccount(String mallSellerAccount) {
		this.mallSellerAccount = mallSellerAccount;
	}

	public String getMallBuyerAccount() {
		return mallBuyerAccount;
	}

	public void setMallBuyerAccount(String mallBuyerAccount) {
		this.mallBuyerAccount = mallBuyerAccount;
	}

	public Integer getWareId() {
		return wareId;
	}

	public void setWareId(Integer wareId) {
		this.wareId = wareId;
	}

	public String getSellCompanyNm() {
		return sellCompanyNm;
	}

	public void setSellCompanyNm(String sellCompanyNm) {
		this.sellCompanyNm = sellCompanyNm;
	}

	public String getBuyCompanyNm() {
		return buyCompanyNm;
	}

	public void setBuyCompanyNm(String buyCompanyNm) {
		this.buyCompanyNm = buyCompanyNm;
	}

	public String getWareNm() {
		return wareNm;
	}

	public void setWareNm(String wareNm) {
		this.wareNm = wareNm;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	} 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getSellId() {
		return sellId;
	}

	public void setSellId(Integer sellId) {
		this.sellId = sellId;
	}

	public Integer getBuyId() {
		return buyId;
	}

	public void setBuyId(Integer buyId) {
		this.buyId = buyId;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Integer getWare_province() {
		return ware_province;
	}

	public void setWare_province(Integer ware_province) {
		this.ware_province = ware_province;
	}

	public String getWare_name() {
		return ware_name;
	}

	public void setWare_name(String ware_name) {
		this.ware_name = ware_name;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public Float getPoundDiff() {
		return poundDiff;
	}

	public void setPoundDiff(Float poundDiff) {
		this.poundDiff = poundDiff;
	}

	public Integer getTtype() {
		return ttype;
	}

	public void setTtype(Integer ttype) {
		this.ttype = ttype;
	} 

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public List<SubOrder> getSubOrderList() {
		return subOrderList;
	}

	public void setSubOrderList(List<SubOrder> subOrderList) {
		this.subOrderList = subOrderList;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	} 

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String[] getProductid() {
		return productid;
	}

	public void setProductid(String[] productid) {
		this.productid = productid;
	}

	public Integer getPoolType() {
		return poolType;
	}

	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}

	public Integer getPoolId() {
		return poolId;
	}

	public void setPoolId(Integer poolId) {
		this.poolId = poolId;
	}

	
	
}
