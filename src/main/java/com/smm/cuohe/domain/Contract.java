package com.smm.cuohe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyutao
 *
 */
public class Contract implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5563686346155084157L;
	
	private Integer id;    //合同id
	
	private Integer orderId; //订单id
	
	private String orderCode;  //商城订单编号
	
	private String content; //合同内容
	
	private String filePath; //合同文件路径 ，相对路径
	private String fileName;//合同名称
	
	private String mallUrl; // 商城预览地址
	
	private Date confirmedAt; //商城用户确认时间
	
	private Date createdAt; //签订时间
	
	private Integer itemId; // 品目id
	
	private String extAttrOneTitle;
	
	private String itemName;   //产品名称
	
	private String signMarket; //签订地点
	
	private CompanyView sell; //卖方
	
	private CompanyView buy; //买方
	
	private List<ContractCommodity> commodity;  
	
	private List<SubOrder> subOrders;  //子订单项
	
	private String lastPayTime; //最后付款时间
	
	private Integer qualityProblemDay; //质量出现异议，收货后，多少时间内告知供方
	
	private String deliveryAddress; //提货地址
	
	private Float poundDiff; //磅差 的千分数
	
	private Integer payment; //结算方式
	
	private String paymentTxt; 
	
	private Date paymentTime; //结算期限
	
	private Integer delivery; //交货方式
	
	private String deliveryTxt; 
	
	private String ware_name;   //仓库名字
	
	private double sumPrice;
	
	private String sumPriceChs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSignMarket() {
		return signMarket;
	}

	public void setSignMarket(String signMarket) {
		this.signMarket = signMarket;
	}

	public CompanyView getSell() {
		return sell;
	}

	public void setSell(CompanyView sell) {
		this.sell = sell;
	}

	public CompanyView getBuy() {
		return buy;
	}

	public void setBuy(CompanyView buy) {
		this.buy = buy;
	}

	public List<ContractCommodity> getCommodity() {
		return commodity;
	}

	public void setCommodity(List<ContractCommodity> commodity) {
		this.commodity = commodity;
	}

	public List<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

	public String getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(String lastPayTime) {
		this.lastPayTime = lastPayTime;
	}

	public Integer getQualityProblemDay() {
		return qualityProblemDay;
	}

	public void setQualityProblemDay(Integer qualityProblemDay) {
		this.qualityProblemDay = qualityProblemDay;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public String getPaymentTxt() {
		return paymentTxt;
	}

	public void setPaymentTxt(String paymentTxt) {
		this.paymentTxt = paymentTxt;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public String getDeliveryTxt() {
		return deliveryTxt;
	}

	public void setDeliveryTxt(String deliveryTxt) {
		this.deliveryTxt = deliveryTxt;
	}

	public String getWare_name() {
		return ware_name;
	}

	public void setWare_name(String ware_name) {
		this.ware_name = ware_name;
	}
	
	public double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getSumPriceChs() {
		return sumPriceChs;
	}

	public void setSumPriceChs(String sumPriceChs) {
		this.sumPriceChs = sumPriceChs;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getExtAttrOneTitle() {
		return extAttrOneTitle;
	}

	public void setExtAttrOneTitle(String extAttrOneTitle) {
		this.extAttrOneTitle = extAttrOneTitle;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMallUrl() {
		return mallUrl;
	}

	public void setMallUrl(String mallUrl) {
		this.mallUrl = mallUrl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getConfirmedAt() {
		return confirmedAt;
	}

	public void setConfirmedAt(Date confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Float getPoundDiff() {
		return poundDiff;
	}

	public void setPoundDiff(Float poundDiff) {
		this.poundDiff = poundDiff;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


}
