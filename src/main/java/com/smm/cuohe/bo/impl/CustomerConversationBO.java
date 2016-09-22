package com.smm.cuohe.bo.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;

import com.smm.cuohe.bo.ICustomerConversationBO;
import com.smm.cuohe.bo.IUserBO;
import com.smm.cuohe.dao.IDiscussChatDAO;
import com.smm.cuohe.dao.IDiscussChatRecordDAO;
import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.StringUtil;

@Service
public class CustomerConversationBO implements ICustomerConversationBO {

	Logger logger = Logger.getLogger(CustomerConversationBO.class);
	
	@Resource
	IDiscussChatDAO iDiscussChatDAO;

	@Resource
	IUserBO iUserBO;
	
	@Resource
	IDiscussChatRecordDAO iDiscussChatRecordDAO;
	
	@Override
	public Map<String, Object> createConversation(Map<String, Object> parasMap, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		Chats conversation = new Chats();
		
		/* 1.根据lastToken查找chats表，如找到则直接返回
		   2.商城用户已登录，优先分配熟悉的撮合员
		   3.商城用户未登录，随机分配撮合员
		 */
		Map<String, Object> rltMap = iDiscussChatDAO.getDealMakerForConversation((Integer)parasMap.get("uid"), (String)parasMap.get("lastToken"), (Integer) parasMap.get("product_id"), (Integer) parasMap.get("item_id"),new Date());
		//根据token更新老会话的时间
		if((String)parasMap.get("lastToken")!=null||!"".equals((String)parasMap.get("lastToken"))){
		iDiscussChatDAO.updateTimeByToken((String)parasMap.get("lastToken"),new Date());
		}
		Integer dealMakerId = (Integer)rltMap.get("dealMakerId");
		Integer convStatus = (Integer)rltMap.get("convStatus");
		logger.info(convStatus);
		//用户已经创建过会话
		if(convStatus == -1){
			
			Integer lastCustomId = (Integer) rltMap.get("lastCustomId");
			
			Integer uid = (Integer) parasMap.get("uid");
			
			// 同一个浏览器下，换用户登录，创建新的会话
			if(uid != null && uid != 0  && lastCustomId != null && lastCustomId.compareTo((Integer)parasMap.get("uid")) != 0){
				
				// 是否分配到撮合员
				if (dealMakerId == null) {
					convStatus = 2;
				} else {
					convStatus = 4;
				}
			} else {
				
				logger.info("用户刷新页面");
				User user = iUserBO.getOne(dealMakerId);
				// 用户切换卖盘时更新卖盘编号
				
				// 要新增一个页面url更新，对应数据库字段是  urlName  和 url，需在商城确认在用户重新选择商品，点击进去撮合时，会把这两个值带给我
				int updateChatWhenSellIdChangeCount = iDiscussChatDAO.updateChatWhenSellIdChange((Integer)parasMap.get("sellId"), (String)parasMap.get("url"), (String)parasMap.get("url_name"), (String)rltMap.get("convToken"),new Date());
				logger.info("姚加静 ：： updateChatWhenSellIdChangeCount" + updateChatWhenSellIdChangeCount + parasMap.get("url"));
				String imgPath = user.getImage();
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("negotiator_name", user.getName());
				dataMap.put("negotiator_img", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath() + imgPath);
				dataMap.put("token", (String)rltMap.get("convToken"));
				Map<String, Object> chatMap = iDiscussChatDAO.getChatByToken((String)rltMap.get("convToken"));
				
				// 返回以前使用的会话中的商城用户信息
				dataMap.put("chatId", chatMap.get("id"));
				dataMap.put("customerName", chatMap.get("customerName"));
				dataMap.put("companyName", chatMap.get("companyName"));
				dataMap.put("lastId", chatMap.get("lastId"));
				
				map.put("status", "ok");
				map.put("msg", "用户刷新页面");
				map.put("data", dataMap);
				
				return map;
			}

		}
		
		String token = UUID.randomUUID().toString();
		conversation.setItemId((Integer)parasMap.get("item_id"));
		conversation.setToken(token);
		conversation.setCustomId((Integer)parasMap.get("uid"));
		conversation.setCustomerName((String)parasMap.get("customerName"));
		conversation.setEmployeeId(dealMakerId);
		conversation.setSessionId((String)parasMap.get("session_id"));
		conversation.setProductId((Integer)parasMap.get("product_id"));
		conversation.setIp((String)parasMap.get("ip"));
		conversation.setUrl((String)parasMap.get("url"));
		conversation.setUrlName((String)parasMap.get("url_name"));
		conversation.setStatus(convStatus);
		conversation.setCompanyId((Integer)parasMap.get("companyId"));
		conversation.setCompanyName((String)parasMap.get("companyName"));
		conversation.setSellId((Integer) parasMap.get("sellId"));
		conversation.setSource((Integer) parasMap.get("source"));
		conversation.setCreatedAt(new Date());
		conversation.setUpdatedAt(new Date());
		conversation.setOnlineTime(new Date());
		iDiscussChatDAO.insert(conversation);
		
		
		// 用户已经在商城登录，点击进入撮合后，要触发合并会话事件
		if(conversation.getCustomId() != null && conversation.getCustomId() != 0){
			Map<String, Object> mergesMap = new HashMap<String, Object>();
			
			mergesMap.put("uid", conversation.getCustomId());
			
			mergesMap.put("token", token);
			
			mergesMap.put("customerName", conversation.getCustomerName());
			
			mergesMap.put("companyId", conversation.getCompanyId());
			
			String mergeCompanyName = StringUtil.doDecoder(conversation.getCompanyName());
			
			mergesMap.put("companyName", mergeCompanyName);
			
			iDiscussChatDAO.mergeConversationForCustomer(mergesMap);
		}
		
		if (dealMakerId == null) {
			map.put("status", "faild");
			
			int itemId = (Integer)parasMap.get("item_id");
			FileInputStream fis = null;
			String phone = "";
			try {
				String path = System.getProperty("smm.cuohe") + "/WEB-INF/classes/config.properties";

				Properties pros = new Properties();
				fis = new FileInputStream(path);
				pros.load(fis);
				phone = pros.getProperty(Integer.toString(itemId));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(phone == null || phone.equals("")){
					phone = "15618610721";
				}
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			map.put("msg", "撮合人员现在正忙，请稍候，或直接拨打 " + phone);
			map.put("data", null);
			
		} else {
			User user = iUserBO.getOne(dealMakerId);
			
			String imgPath = user.getImage();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("negotiator_name", user.getName());
			dataMap.put("negotiator_img", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath() + imgPath);
			dataMap.put("token", token);
			dataMap.put("chatId", iDiscussChatDAO.getChatByToken(token).get("id"));
			map.put("status", "ok");
			map.put("msg", "");
			map.put("data", dataMap);
		}
		
		return map;
	}
	
	
	@Override
	public Map<String, Object> getChatsForCustomerByToken(Map<String, Object> parasMap){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<DiscussChatRecord> list = iDiscussChatDAO.getChatsForCustomerByToken(parasMap);
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		map.put("data", list);
		
		return map;
	}
	
	@Override
	public Map<String, Object> mergeConversationForCustomer(Map<String, Object> parasMap){
		
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info(parasMap);
		int errCode = iDiscussChatDAO.mergeConversationForCustomer(parasMap);
		logger.info(errCode);
		if(errCode == 1){
			
			map.put("status", "faild");
			
			map.put("msg", "合并失败");
			
			map.put("data", "");
			
		} else {
			
			map.put("status", "ok");
			
			map.put("msg", "合并会话成功");
			
			map.put("data", "");
		}
		
		return map;
		
	}

	@Override
	public Map<String, Object> getContractHtml(int customerId, String token) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String mallUrl = iDiscussChatDAO.getContractMallUrl(customerId, token);
		if(mallUrl == null || mallUrl.equals("")){
			map.put("status", "faild");
			
			map.put("msg", "该会话未生成订单");
		} else {
			map.put("status", "ok");
			
			map.put("msg", "");
		}
		map.put("data", mallUrl);
		
		return map;
	}
	
	@Override
	public Map<String, Object> closeConversationForCustomer(String token) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//iDiscussChatDAO.closeConversationForCustomer(token);
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		map.put("data", null);
		
		return map;
	}
	
	@Override
	public Map<String, Object> customOnline(String token){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		iDiscussChatDAO.customOnline(token,new Date());
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		map.put("data", null);
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> confirmContract(String orderNo){
		
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("买家确认合同开始 orderNo : " + orderNo);
		int updateNum = iDiscussChatDAO.customerConfirmContract(orderNo,new Date());
		
		if(updateNum < 1){
			
			map.put("status", "faild");
			
			map.put("msg", "订单不存在");
			
		} else {
			
			map.put("status", "ok");
			
			map.put("msg", "");
			
		}
		logger.info("买家确认合同结束 updateNum : " + updateNum);
		
		iDiscussChatDAO.changeChatOrderStatusToFinish(orderNo);
		iDiscussChatDAO.updateSellPoolToClose(orderNo);
		
		map.put("data", null);
		
		return map;
	}
	
}
