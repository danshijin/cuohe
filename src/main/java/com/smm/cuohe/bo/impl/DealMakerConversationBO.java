package com.smm.cuohe.bo.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smm.cuohe.bo.IDealMakerConversationBO;
import com.smm.cuohe.dao.IAreasDao;
import com.smm.cuohe.dao.IChatsDAO;
import com.smm.cuohe.dao.ICommodityDAO;
import com.smm.cuohe.dao.IDiscussChatDAO;
import com.smm.cuohe.dao.IDiscussChatRecordDAO;
import com.smm.cuohe.dao.base.ChChatHistoryMapper;
import com.smm.cuohe.dao.base.ChOnlineHistoryMapper;
import com.smm.cuohe.dao.dealmake.IBuyOrderDao;
import com.smm.cuohe.dao.users.UserInfoDAO;
import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChChatHistory;
import com.smm.cuohe.domain.base.ChOnlineHistory;

@Service
public class DealMakerConversationBO implements IDealMakerConversationBO{
	
	@Resource
	UserInfoDAO iUserInfoDao;
	
	@Resource
	IDiscussChatDAO iDiscussChatDao;
	
	@Autowired
	IDiscussChatRecordDAO iDiscussChatRecordDao;
	
	@Resource
	ICommodityDAO iCommodityDAO;
	
	@Resource
	IAreasDao iAreasDao;
	
	@Resource
	IBuyOrderDao iBuyOrderDao;
	
	@Resource
	private IChatsDAO chatsDao;
	
	@Autowired
	private ChChatHistoryMapper hisDao;
	
	@Autowired
	ChOnlineHistoryMapper chOnlineDao;
	
	private Logger logger = Logger.getLogger(DealMakerConversationBO.class);
	
	@Override
	@Transactional
	public Map<String, Object> login(String account, String pwd, String rootPath){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = iUserInfoDao.dealMakerLogin(account);
		
		if(list != null && list.size() > 0){
			String dbPass = (String) list.get(0).get("pwd");
			if(dbPass!=null && dbPass.equalsIgnoreCase(pwd)){
				
				iUserInfoDao.updateOnline(1, (Integer)list.get(0).get("id"));
				list.get(0).put("image", rootPath + list.get(0).get("image"));
				list.get(0).put("pwd", "");
				ChOnlineHistory record = new ChOnlineHistory();
				record.setCreatetime(new Date());
				record.setEndTime(new Date());
				record.setEmployeeid((Integer) list.get(0).get("id"));
				record.setLogintype(2);
				chOnlineDao.insert(record);
				map.put("data", list.get(0));
				map.put("status", "ok");
				map.put("msg", "");
				
			} else {
				
				map.put("status", "faild");
				map.put("msg", "登录密码错误");
				map.put("data", null);
				
			}
			
		} else {
			map.put("status", "faild");
			map.put("msg", "不存在此用户");
			map.put("data", null);
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> getConversations(Integer itemId,Integer employeeId, String flagTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemDate = sdf.format(new Date());
			Map<String, Object> map = new HashMap<String, Object>();
			List<Chats> chatlist = null;
			List<Chats> offline = null;
			/**
			 * @author youxiaoshuang 撮合2.1改版 start
			 */
			if (flagTime != null && !flagTime.trim().equals("")) {
				Date flagDate = null;
				try {
					flagDate = sdf.parse(flagTime);
				}catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 1.查找时间段内活动且在线的会话 且如果为合并会话则返回合并之前的id
				// ①先查询更新时间在时间区间之内的所有会话
				chatlist = iDiscussChatDao.getChatOnline(new Date(),itemId, employeeId,
						flagDate);
				// ②查找时间段以内合并会话的并加上oldchatid
				for (Chats chat : chatlist) {
					// 查询合并会话历史表
					ChChatHistory his = hisDao.selectByNid(chat.getId());
					if (his != null)
						chat.setOid(his.getOid());
				}
				// 查询在时间段内离线的会话 flagDate-30s<onlineTime<SysTime-30s
				if (flagDate != null) {
					offline = iDiscussChatDao.getChatoffline(new Date(), itemId,
							employeeId, flagDate);
				}
				if(offline !=null){
				chatlist.addAll(offline); //合并结果集
				}
				/**
				 * end
				 */
			} else {
				chatlist = iDiscussChatDao.getConversationsForDealMaker(new Date(),itemId,
						employeeId);
			}
			  //记录撮合端在线心跳
			ChOnlineHistory history = chOnlineDao.queryLastHistoryClient(employeeId);
			ChOnlineHistory record = new ChOnlineHistory();
			record.setId(history.getId());
			record.setEndTime(new Date());//更新离线时间
			chOnlineDao.updateByPrimaryKeySelective(record);
			
			map.put("status", "ok");
			map.put("msg", systemDate);
			map.put("data", chatlist);
			// map.put("online", chatlist);
			// map.put("offline",offline);
			return map;
	}
	
	@Override
	public Map<String, Object> getChatForDealMaker(Map<String, Object> parasMap){
		
		
		Map<String, Object> map = new HashMap<String, Object>();

		List<DiscussChatRecord> list = iDiscussChatDao.getChatsForDealMaker(parasMap);
		
		map.put("status", "ok");
		map.put("msg", "");
		map.put("data", list);
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> sendChatForDealMarker(DiscussChatRecord record, String token){
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		record.setSysTime(new Date());
		if(record.getChatFromType().equals("C")){
			Integer rlt = iDiscussChatRecordDao.addRecordWithCheck(record, token);
			if(rlt == -1){
				map.put("status", "faild");
				map.put("msg", "客户发送聊天：chatId和token验证失败");
				map.put("data", null);
			} else if (rlt == 1){
				params.put("chatID", record.getChatId());
				params.put("updatedAt", new Date());
				this.chatsDao.updateChatsById(params);
				
				map.put("status", "ok");
				map.put("msg", "");
				map.put("data", null);
				
			}
		} else if(record.getChatFromType().equals("U")){
			iDiscussChatRecordDao.addRecord(record);
			
			params.put("chatID", record.getChatId());
			params.put("updatedAt", new Date());
			this.chatsDao.updateChatsById(params);
			
			map.put("status", "ok");
			map.put("msg", "");
			map.put("data", null);
			
		}
		return map;
		
	}
	
	
	@Override
	public Map<String, Object> countConversationByStatusAndCustomIdForDealMaker(Integer customId, Integer status){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(customId == null){
			map.put("status", "faild");
			map.put("msg", "参数异常：customId为" + customId);
			map.put("data", null);
		} else if(status == null) {
			map.put("status", "faild");
			map.put("msg", "参数异常：status为" + status);
			map.put("data", null);
		} else {
			Integer count = iDiscussChatDao.countConversationByStatusAndCustomIdForDealMaker(customId, status);
			map.put("status", "ok");
			map.put("msg", "");
			map.put("data", count);
		}
		return map;
	}
	
	@Override
	public Map<String, Object> countHistoryOrder(String customerName, Integer itemId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(customerName == null){
			map.put("status", "faild");
			map.put("msg", "参数异常：customerName为" + customerName);
			map.put("data", null);
		}  else if (itemId != null && iDiscussChatRecordDao.hasItemIdForDealMaker(itemId) == 0) {
			map.put("status", "faild");
			map.put("msg", "参数异常：品目id:"+itemId+",不存在");
			map.put("data", null);
		} else {
			Integer count = iDiscussChatDao.countHistoryOrder(customerName, itemId);
			map.put("status", "ok");
			if(itemId == null){
				map.put("msg", "itemId为"+itemId+"，已获取所有品目的订单数。");
			} else {
				map.put("msg", "");
			}
			map.put("data", count);
		}
		return map;
	}
	
	@Override
	public Map<String, Object> countConversationByCustomIdAndItemIdForDealMaker(Integer customId, Integer itemId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(customId == null){
			map.put("status", "faild");
			map.put("msg", "参数异常：customId为" + customId);
			map.put("data", null);
		} else if (itemId == null) {
			map.put("status", "faild");
			map.put("msg", "参数异常：itemId为" + itemId);
			map.put("data", null);
		} else {
			int count = iDiscussChatDao.countConversationByCustomIdAndItemIdForDealMaker(customId, itemId);
			map.put("status", "ok");
			map.put("msg", "");
			map.put("data", count);
		}
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> updateConversationStatusForDealMaker(Map<String, Object> parasMap){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int dealMakerId = iDiscussChatDao.updateConversationStatusForDealMaker(parasMap);
		if(dealMakerId == 0){
			
			map.put("status", "ok");
			map.put("msg", "更新成功");
			
		} else {
			map.put("status", "faild");
			map.put("msg", "该会话已被其他撮合人员锁定");
		}
		
		map.put("data", dealMakerId);
		
		return map;
	}
	
	@Override
	public Map<String, Object> getMyTeamInfo(Integer itemId, Integer dealMakerId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = iDiscussChatDao.getMyTeamInfo(itemId, dealMakerId);
		
		map.put("status", "ok");
		map.put("msg", "");
		map.put("data", list);
				
		return map;
		
	}
	
	@Override
	public Map<String, Object> confirmOrderByDealMaker(Map<String, Object> parasMap, HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int action = (Integer) parasMap.get("action");
		
		//0:确认订单、1：确认订单并生成合同、2：取消订单
		if(action == 0){
			
			parasMap.put("status", 1);
			
			map.put("data", null);
			
		} else if (action == 1){
			
			parasMap.put("status", 1);
			
			map.put("data", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/vc.do?id=" + parasMap.get("orderId"));
			
		} else if (action == 2){
			
			parasMap.put("status", 2);
			
			map.put("data", null);
			
		}
		
		iDiscussChatDao.updateOrderStatusForDealMaker(parasMap);
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> transferConversationByDealMaker(Integer conversationId, Integer currDealMakerId, Integer destDealMakerId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		int dealMaker = iDiscussChatDao.transferConversationByDealMaker(conversationId, currDealMakerId, destDealMakerId,new Date());
		
		if(dealMaker == 0){
			map.put("status", "ok");
			
			map.put("msg", "会话转移成功");
		} else {
			map.put("status", "faild");
			
			map.put("msg", "该会话已被其他撮合人员锁定");
		}
		
		map.put("data", dealMaker);
		
		return map;
	}
	
	@Override
	public Map<String, Object> changeRecordStatusByDealMaker(Integer chatRecordId, Integer destStatus, Integer overtimeSeconds){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Integer currStatus = iDiscussChatRecordDao.changeRecordStatusByDealMaker(chatRecordId, destStatus, overtimeSeconds,new Date());
		
		if(currStatus == null){

			map.put("status", "faild");
			
			map.put("msg", "该会话记录不存在");
			
		} else if(currStatus == -1) {
			
			map.put("status", "faild");
			
			map.put("msg", "议价信息已超时");
			
		} else if(currStatus == 0){
			
			map.put("status", "ok");
			
			map.put("msg", "更新成功");
			
		} else if (currStatus > 0){
			
			map.put("status", "faild");
			
			map.put("msg", "该会话记录无法被更新");
			
		}
		
		map.put("data", currStatus);
		
		return map;
	}
	
	@Override
	public Map<String, Object> addWakeUpRecord(Integer chatId, Integer dealMakerId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Integer isWakeUp = iDiscussChatRecordDao.isWakeUp(chatId, dealMakerId);
		
		if(isWakeUp > 0){
			
			map.put("status", "1");
			map.put("msg", "已添加洽谈提醒");
			map.put("data", null);
			return map;
			
		}
		
		DiscussChatRecord record = new DiscussChatRecord();
		
		record.setChatId(chatId);
		record.setType(3);
		record.setChatFromType("U");
		record.setChatFromId(dealMakerId);
		record.setStatus(4);
		record.setEmployeeId(dealMakerId);
		int num = iDiscussChatRecordDao.addRecord(record);
		
		if(num == 1){
			map.put("status", "2");
			map.put("msg", "添加洽谈提醒成功");
		} else {
			map.put("status", "3");
			map.put("msg", "添加洽谈提醒失败");
		}
		
		map.put("data", null);
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> getCommodityByIdForDealMaker(int commodityId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		map.put("data", iCommodityDAO.getCommodityByIdForDealMaker(commodityId));
		
		return map;
	}

	@Override
	public Map<String, Object> getItemAttrByItemId(int itemId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		map.put("data", iCommodityDAO.getItemAttrByItemId(itemId));
		
		return map;
	}

	@Override
	public Map<String, Object> updateOnlineStatus(int dealMakerId, int online) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		iDiscussChatDao.updateOnlineStatusByDealMaker(dealMakerId, online,new Date());
		ChOnlineHistory history = chOnlineDao.queryLastHistoryClient(dealMakerId);
		ChOnlineHistory record = new ChOnlineHistory();
		switch (online) {
		case 1:
			record.setCreatetime(new Date());
			record.setEndTime(new Date());
			record.setEmployeeid(dealMakerId);
			record.setLogintype(2);
			record.setOnlinetype(0);//在线
			chOnlineDao.insert(record);//插入上线状态记录表
			break;
		case 0:
			record.setId(history.getId());
			record.setEndTime(new Date());
			record.setOnlinetype(1);//离线
			chOnlineDao.updateByPrimaryKeySelective(record);
			break;
		case 2:
			record.setId(history.getId());
			record.setLeaveTime(new Date());
			record.setOnlinetype(1);//离开
			chOnlineDao.updateByPrimaryKeySelective(record);
		default:
			break;
		}
		
		
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		map.put("data", null);
		
		return map;
	}
	
	@Override
	public User getUserForGenerateOrder(int chatId){
		return iDiscussChatDao.getUserForGenerateOrder(chatId);
	}

	@Override
	public Items getItemById(int itemId) {
		return iDiscussChatDao.getItemById(itemId);
	}
	
	@Override
	public Map<String, Object> getAllProvince() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", "ok");
		
		map.put("msg", "");
		
		map.put("data", iAreasDao.getParentAreas());
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> getWarehouseByProvince(Integer provinceId, Integer itemId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(provinceId == null){
			map.put("status", "faild");
			map.put("msg", "参数异常：provinceId为"+ provinceId);
			map.put("data", null);
		} else if(itemId == null){
			map.put("status", "faild");
			map.put("msg", "参数异常：品目id是空");
			map.put("data", null);
		} else if(iDiscussChatRecordDao.hasItemIdForDealMaker(itemId) == 0){
			map.put("status", "faild");
			map.put("msg", "参数异常：品目id不存在");
			map.put("data", null);
		} else {
			map.put("status", "ok");
			map.put("msg", "");
			map.put("data", iBuyOrderDao.selWarehouseByArea(null,provinceId, itemId.toString()));
		}
		return map;
		
	}
	
	@Override
	public Map<String, Object> hasCustomerByAccount(String account, Integer itemId,String companyName){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(account)){
			map.put("status", "faild");
			map.put("msg", "参数异常：account为"+account);
			map.put("data", null);
		} else if(itemId == null){
			map.put("status", "faild");
			map.put("msg", "参数异常：品目id为" + itemId);
			map.put("data", null);
		} else if (iDiscussChatRecordDao.hasItemIdForDealMaker(itemId) == 0) {
			map.put("status", "faild");
			map.put("msg", "参数异常：品目id:"+itemId+",不存在");
			map.put("data", null);
		} else {
			Map<String, Object> rltMap = iDiscussChatDao.hasCustomerByAccount(account,itemId,companyName);
			if(rltMap == null || rltMap.get("id") == null) {
				map.put("status", "faild");
				map.put("msg", "商城客户没有在撮合系统中注册");
				map.put("data", null);
			} else {
				map.put("status", "ok");
				map.put("msg", "");
				map.put("data", rltMap);
			}
		}
		return map;
	}
	
}
