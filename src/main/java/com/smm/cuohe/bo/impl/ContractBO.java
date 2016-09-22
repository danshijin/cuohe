package com.smm.cuohe.bo.impl;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.IContractBO;
import com.smm.cuohe.dao.IContractDao;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.Contract;
import com.smm.cuohe.domain.ContractCommodity;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.tools.mail.UtilMail;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.Num2RMB;
import com.smm.cuohe.util.StringUtil;
import com.smm.cuohe.util.Texting;


@Service
public class ContractBO implements IContractBO{

	private Logger logger = Logger.getLogger(ContractBO.class);
	
	@Resource
	public Texting texting;
	
	 
	
	
	@Resource
	private IContractDao iContractDao;
	
	@Override
	public Map<String, Object> getContractContentAndOrderCodeByOrderId(int orderId){
		
		Map<String, Object> map = iContractDao.getContractContentAndOrderCodeByOrderId(orderId);
		
		return map == null ? new HashMap<String, Object>() : map;
		
	}
	
	@Override
	public Contract getContractByOrderId(Integer orderId) {
		
		Contract contract = iContractDao.getContractByOrderId(orderId);
		return buildContract(contract);
	}

	@Value("#{ch['mallContractUrlPrefix']}")
	private String mallContractUrlPrefix;
	
	/** 确认并提交合同
	 * @Title: addContractWithHtml 
	 * @Description: TODO
	 * @param orderId
	 * @param orderCode
	 * @param contract
	 * @return 
	 * @author zhangnan/张楠
	 * @createTime 2016年3月30日上午10:27:12
	 */
	@Override
	public String addContractWithHtml(Integer orderId, String orderCode, String contract) {

		Map<String, String> buyAndSellMap = iContractDao.getAccountByOrderId(orderId);
		
		Map<String, Object> rltMap = getContractUrlFromMall(orderCode, buyAndSellMap.get("buyer"), buyAndSellMap.get("seller"), contract);

		String contractMallUrl = "";
		
		Integer errno = (Integer)rltMap.get("errno");
		if(errno == null || errno > 0){
			
			String msg = (String)rltMap.get("msg");
			return msg == null ? "商城接口调用失败" : msg;
			
		} else {
			contractMallUrl = mallContractUrlPrefix + (String)rltMap.get("url");
			
			logger.info("开始调用：mallContractUrlPrefix接口");
			
			iContractDao.addContractWithHtml(orderId, contract, contractMallUrl);
			
			return "success";
			
		}
		
	}
	@Override
	public String getContractMallUrl(int orderId){
		
		return iContractDao.getContractMallUrl(orderId);
		
	}

	@Override
	public List<Contacter> getBuyContactersByOrderId(int orderId){
		return iContractDao.getBuyContactersByOrderId(orderId);
	}
	
	@Value("#{ch['getContractUrlFromMall.URL']}")
	private String mallURL;
	private Map<String, Object> getContractUrlFromMall(String orderno, String buyer, String seller, String text){
		
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		
		param.add("orderno", orderno);
		
		param.add("buyer", buyer);
		
		param.add("seller", seller);
		
		param.add("text", StringUtil.doEncoder(text));
		String jsonString = "{}";
		try{
			logger.info("开始调用：获取合同接口  参数："+param.toString());
			jsonString = new RestTemplate().postForObject(mallURL, param, String.class);
			
			if (jsonString != null) {
				logger.info("获取合同返回数据"+JSONUtil.doConvertJson2Map(jsonString).toString());
			}
			
		} catch(Exception ex){
			
			System.out.println(ex.getMessage());
			
		}
		return JSONUtil.doConvertJson2Map(jsonString);
		
	}

	@Override
	public Map<String, Object> sendContract(Integer orderId, String sendType,
			HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String mallUrl = iContractDao.getContractMallUrl(orderId);
		
		if(mallUrl == null || mallUrl.equals("")){
			map.put("status", "-1");
			map.put("msg", "请先确认并提交合同");
			
			return map;
		}
		
		if(sendType.equals("url")){
			
			map.put("status", "3");
			map.put("msg", mallUrl);
			
			return map;
		}
		
		List<Contacter> contacterList = getBuyContactersByOrderId(orderId);
		
		if(contacterList.size() == 0){
			map.put("status", "0");
			map.put("msg", "不存在任何联系人");
		} else if (contacterList.size() == 1){
			map.put("status", "1");
			map.put("msg", "");
			if(sendType.equals("sms")){
				String msg = sendContractUrlBySms(orderId, contacterList.get(0).getMobilePhone(), request);
				map.put("msg", msg);
			} else if (sendType.equals("email")){
				String msg = sendContractUrlByEmail(orderId, contacterList.get(0).getEmail(), contacterList.get(0).getName(), request);
				map.put("msg", msg);
			}
		} else {
			map.put("status", "2");
			map.put("msg", "");
			map.put("data", contacterList);
		}
		
		return map;
	}
	
	/**
	 * @param orderId
	 * @param buyMobile
	 * @param request
	 * @return
	 */
	public String sendContractUrlBySms(Integer orderId, String buyMobile, HttpServletRequest request){
		
		Contract contract = getContractByOrderId(orderId);
		List<ContractCommodity> commodityList = contract.getCommodity();
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
		user.getName();
		String message = "撮合员" + user.getName() + "已为您生成一笔新合同（";
		for(ContractCommodity commodity : commodityList){
			
			if(contract.getExtAttrOneTitle() != null && !contract.getExtAttrOneTitle().equals("")){
				message += commodity.getExtAttrOne() + commodity.getName() + " " + commodity.getQuantity() + commodity.getUnit() + " ";
			}
			
		}
		message += "）请尽快登录有色商城确认！";
		
		if(buyMobile != null && ! buyMobile.equals("")){
			String resultMsg = texting.sendsmm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), message, buyMobile);
			resultMsg = texting.sendsmm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), contract.getMallUrl(), buyMobile);
			String smsSendResult = Texting.checkResultCode(resultMsg);
			if(smsSendResult.equals("成功")){
				return "合同网址已通过短信形式发送给对方，请务必通知对方确认";
			} else {
				return smsSendResult;
			}
		}
		return "买方采购联系人不存在";
	}
	
	/**
	 * @param orderId
	 * @param email
	 * @param contacterName
	 * @param request
	 * @return
	 */
	public String sendContractUrlByEmail(Integer orderId, String email, String contacterName, HttpServletRequest request){
		
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
		
		String msg = "合同网址已通过邮件形式发送给对方，请务必通知对方确认";
		
		Contract contract = getContractByOrderId(orderId);
		String content = (String) contract.getContent();
		int begin = content.indexOf("<!--SubOrderBegin-->");
		int end = content.indexOf("<!--SubOrderEnd-->");
		String emailTable = content.substring(begin + "<!--SubOrderBegin-->".length(), end);
		
		begin = content.indexOf("<!--totalPriceBegin");
		end = content.indexOf("totalPriceEnd-->");
		String totalPrice = content.substring(begin + "<!--totalPriceBegin".length(), end);
		
		String style = "<style>#ckTblBorderOne{text-align:center;border:1px solid;border-collapse:collapse;width:900px;}#ckTblBorderOne tr th{border:1px solid;padding:.5em 0;}#ckTblBorderOne tr td{border:1px solid;padding:.5em 0;}</style>";
		try {
			UtilMail se = new UtilMail(false);
			File templateFile = new File(this.getClass().getClassLoader().getResource("/emailTemplate").getFile());
			FileInputStream fis = new FileInputStream(templateFile);
			byte[] b = new byte[1100];
			int len = fis.read(b, 0, 1100);
			String html = new String(b, 0, len, Charset.forName("utf-8"));
			html = html.replace("@username", contacterName);
			html = html.replace("@mallContractUrl", contract.getMallUrl());
			html = html.replace("@productList", style + emailTable + totalPrice);
			html = html.replace("@dealMakerName", user.getName());
			html = html.replace("@copyright", "2000-" + new SimpleDateFormat("yyyy").format(new Date()));
			se.doSendHtmlEmail(contacterName +"您好，【有色网】合同确认提醒", html, email);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		return msg;
	}

	@Override
	public String sendContractBySelectedContacters(String customerName,
			String phoneOrEmail, String sendType, Integer orderId,
			HttpServletRequest request) {
		String msg= "";
		if(sendType.equals("sms")){
			msg = sendContractUrlBySms(orderId, phoneOrEmail, request);
		} else if (sendType.equals("email")){
			msg = sendContractUrlByEmail(orderId, phoneOrEmail, customerName, request);
		}
		
		return msg;
	}
	
	
	public Contract buildContract(Contract contract){
		
		//构造合同名称
		String contractName = contract.getItemName();
		String[] contractNameArr = contractName.split("");
		String name = "";
		for(String str : contractNameArr){
			name += str + " ";
		}
		contract.setItemName(name + "购 销 合 同");
		
		//构造合同中的商品列表
		List<ContractCommodity> commodityList = new ArrayList<ContractCommodity>();
		
		List<ItemAttr> itemAttr = iContractDao.selectItemAttr(contract.getItemId());
		
		
		for(int i = 0; i< itemAttr.size(); i++){
			if(itemAttr.get(i).getMainproperty() == 1){
				contract.setExtAttrOneTitle(itemAttr.get(i).getName());
				break;
			}
		}
		if(contract.getExtAttrOneTitle() == null && itemAttr != null && itemAttr.size() > 0){
			contract.setExtAttrOneTitle(itemAttr.get(0).getName());
		}
		
		
		contract.setCommodity(commodityList);
		double sumPrice = 0.0f;
		
		for(SubOrder so : contract.getSubOrders()){
			
			ContractCommodity commodity = new ContractCommodity();
			commodity.setName(so.getItemNm());
			commodity.setPrice(so.getPrice());
			commodity.setQuantity(so.getQuantity());
			commodity.setTotalPrice(so.getPrice() * so.getQuantity());
			
			if(so.getUnit() == 0){
				commodity.setUnit("吨");
			} else {
				commodity.setUnit("千克");
			}
			commodity.setTotolPriceChs(Num2RMB.toRMB(so.getPrice() * so.getQuantity()));
			commodityList.add(commodity);
			sumPrice += so.getPrice() * so.getQuantity();
			List<CommodityAttr> list = so.getCommodityAttrList();
			if(list != null){
				for(CommodityAttr av : list){
					if(av.getAttrName().equals(contract.getExtAttrOneTitle())){
						commodity.setExtAttrOne(av.getAttrValue());
						break;
					}
				}
			}
		}
		
		contract.setSumPrice(sumPrice);
		contract.setSumPriceChs(Num2RMB.toRMB(sumPrice));
		
		String[] paymentArr = {"", "现款现汇", "银行承兑", "安全支付"};
		contract.setPaymentTxt(paymentArr[contract.getPayment()]);
				
		String[] deliveryArr = {"","款到发货 ", "货到付款"};
		contract.setDeliveryTxt(deliveryArr[contract.getDelivery()]);
				
		contract.setSignMarket("");
		contract.setQualityProblemDay(3);
		contract.setPaymentTime(new Date());
		
		return contract;
	}
}
