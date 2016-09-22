package com.smm.cuohe.bo.counts.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.counts.TrialcountBo;
import com.smm.cuohe.dao.IChatsDAO;
import com.smm.cuohe.dao.IOrdersDAO;
import com.smm.cuohe.dao.base.ChOnlineHistoryMapper;
import com.smm.cuohe.dao.base.ChUsersEntityMapper;
import com.smm.cuohe.dao.counts.TrailCountDao;
import com.smm.cuohe.dao.users.UserInfoDAO;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.base.ChOnlineHistory;
import com.smm.cuohe.domain.base.ChSubOrdersEntity;
import com.smm.cuohe.domain.base.ChUsersEntity;
import com.smm.cuohe.domain.counts.ChatCount;
import com.smm.cuohe.domain.counts.TrailEntity;
import com.smm.cuohe.domain.dealmake.OrdercodeEntity;
import com.smm.cuohe.util.DateUtil;

@Service
public class TrailcountImpl implements TrialcountBo {
	
	@Resource TrailCountDao trailcountDao;
	@Autowired
	ChUsersEntityMapper chUserDao;
	@Autowired
	ChOnlineHistoryMapper chOnlineDao;
	@Autowired
	IChatsDAO chatDao;
	@Autowired
	UserInfoDAO userInfoDao;
	@Autowired
	IOrdersDAO orderDao;
	
	@Override
	public List<TrailEntity> trialquery(Map< String, Object> map) {
	
		List<TrailEntity> list=null;
		list=trailcountDao.trailLists(map);
		return list;
	}

	@Override
	public List<TrailEntity> userlist() {
		List<TrailEntity> list=null;
		list=trailcountDao.userlists();
		
		return list;
	}

	@Override
	public List<TrailEntity> itemslist() {
		List<TrailEntity> list=null;
		list=trailcountDao.itemslists();
		
		return list;
		
	}

	@Override
	public List<TrailEntity> trailListlike(Map<Object, Object> map) {
		List<TrailEntity> list=null;
	//	list=trailcountDao.trailListlikess(map);
		return list;
	}

	@Override
	public List<OrdercodeEntity> queryordercount(Map<String, Object> map) {
		List<OrdercodeEntity> list=trailcountDao.queryordercounts(map);
		return list;
	}

	@Override
	public List<OrdercodeEntity> querybaojiacount(Map<String, Object> map) {
		List<OrdercodeEntity> list=trailcountDao.querybaojiacounts(map);
		return list;
	}

	@Override
	public List<OrdercodeEntity> querycaigoujiacounts(Map<String, Object> map) {
	List<OrdercodeEntity> list=trailcountDao.querycaigoujiacounts( map );
		return list;
	}

	@Override
	public List<Customer> selectcompanyName(String itemId) {
		// TODO Auto-generated method stub
		return trailcountDao.selectcompanyName(itemId);
	}

	/**
	 * 聊天报表
	 */
	@Override
	public List<ChatCount> chatCountManage(ChatCount chatCount) {
		// TODO Auto-generated method stub
		List<ChatCount> chatCountList = new ArrayList<ChatCount>();
		//获取撮合人员列表
		List<ChUsersEntity> userList = chUserDao.queryUserListPage(chatCount);
		for (ChUsersEntity chUsersEntity : userList) {
			ChatCount chatC = new ChatCount();
			chatC.setEmployeeId(chUsersEntity.getId());//用户id
			chatC.setEmployeeName(chUsersEntity.getName());//用户名
			chatC.setCurrentTime(chatCount.getCurrentTime());
			/**
			 * 查询时间段内的在线时长
			 */
			List<ChOnlineHistory> historyList = chOnlineDao.queryOnlineTime(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			long onlineTime = this.getOnlinTimesClient(historyList);
			String onlineTimeStr = DateUtil.getIntervalUpdateTime(onlineTime);
			chatC.setOnlineTime(onlineTimeStr);//在线时长
			/**
			 * 收到的会话请求
			 */
			if (onlineTime == 0) {
				chatC.setChatTotal(0);//会话请求数
			}else {
				int chatSize = chatDao.getChatByEmpToday(chUsersEntity.getId(),chatCount.getStartDate(),chatCount.getEndDate());
				chatC.setChatTotal(chatSize);//会话请求数
			}
			
			/**
			 * 有效沟通次数
			 */
			if (onlineTime == 0) {
				chatC.setChatValid(0);
			}else {
				List<DiscussChatRecord> dcrlist = chatDao.getValidChats(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
	            Integer valids = this.getValidChats(dcrlist);
	            chatC.setChatValid(valids);//有效沟通次数
			}
			
            /**
             * 沟通总时长
             */
            if (onlineTime == 0) {
            	chatC.setChatTime("0");//沟通总时长
			}else {
				List<DiscussChatRecord> chatTimeList = chatDao.getChatTimes(chUsersEntity.getId(),chatCount.getStartDate(),chatCount.getEndDate());
				long times = this.getChatTimes(chatTimeList);
				String totalTimes = DateUtil.getIntervalUpdateTime(times);
				chatC.setChatTime(totalTimes);//沟通总时长
			}
            
			
			/**
			 * 生成用户数
			 */
			int users = userInfoDao.generateUsers(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			chatC.setCreateCustomerTotal(users);//生成用户数
			
			
			/**
			 * 生成订单数
			 */
			int orders = orderDao.getOrdersByEmpToday(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			chatC.setCreateOrderTotal(orders);
			
			
	        /**
	         * 订单总金额
	         */
			List<ChSubOrdersEntity> sublsit = orderDao.getOrderAmountByEmpToday(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			BigDecimal amount = this.getAmount(sublsit);
			chatC.setOrderAmount(amount);
			
			
			
			chatCountList.add(chatC);
			
		}
		
		return chatCountList;
	}
	/**
	 * excel所需结果集
	 */

	@Override
	public List<Object[]> chatExportExcel(ChatCount chatCount, String[] rowsName) {
		// TODO Auto-generated method stub
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		//获取撮合人员列表
		List<ChUsersEntity> userList = chUserDao.queryUserListOrderByOff();
		for (int i = 0; i < userList.size(); i++) {
			ChUsersEntity chUsersEntity = userList.get(i);
			objs = new Object[rowsName.length];
			objs[0]=i;
			objs[1]=(chUsersEntity.getName()!=null)?chUsersEntity.getName():"";//用户名
			
			/**
			 * 查询时间段内的在线时长
			 */
			List<ChOnlineHistory> historyList = chOnlineDao.queryOnlineTime(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			long onlineTime = this.getOnlinTimesClient(historyList);
			String onlineTimeStr = DateUtil.getIntervalUpdateTime(onlineTime);
			objs[2]=(onlineTimeStr!=null&&!"".equals(onlineTimeStr))?onlineTimeStr:"0秒";//客户端在线时长
			
			/**
			 * 收到的会话请求
			 */
			Integer chatSize = 0;
			if (onlineTime == 0) {
				chatSize = 0;
			}else {
				chatSize = chatDao.getChatByEmpToday(chUsersEntity.getId(),chatCount.getStartDate(),chatCount.getEndDate());
			}
			
			objs[3]=(chatSize!=null)?chatSize:"";//收到会话请求(次)
			
			
			/**
			 * 有效沟通次数
			 */
			
			List<DiscussChatRecord> dcrlist = chatDao.getValidChats(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			Integer valids = 0;
			if (onlineTime == 0) {
				valids = 0;
			}else {
				valids = this.getValidChats(dcrlist);
			}
            
            objs[4]= (valids!=null)?valids:"";//有效沟通次数
           
            /**
             * 沟通总时长
             */
//            List<DiscussChatRecord> chatTimeList = chatDao.getChatTimes(chUsersEntity.getId(),chatCount.getStartDate(),chatCount.getEndDate());
//			long times = this.getChatTimes(chatTimeList);
//			String totalTimes = DateUtil.getIntervalUpdateTime(times);
//			objs[5]=(totalTimes!=null&&!"".equals(totalTimes))?totalTimes:"0秒";//沟通总时长
			 
			
			/**
			 * 生成用户数
			 */
			int users = userInfoDao.generateUsers(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			objs[5]=users;//生成用户数
			
			/**
			 * 生成订单数
			 */
			int orders = orderDao.getOrdersByEmpToday(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			objs[6]=orders;//生成订单数
			
	        /**
	         * 订单总金额
	         */
			List<ChSubOrdersEntity> sublsit = orderDao.getOrderAmountByEmpToday(chUsersEntity.getId(), chatCount.getStartDate(),chatCount.getEndDate());
			BigDecimal amount = this.getAmount(sublsit);
			objs[7]=(amount!=null)?amount:"";//订单总金额(元)
			dataList.add(objs);
			
		}
		return dataList;
	}
	/**
	 * 获取客户端在线在线时间和
	 * @param historyList
	 * @return
	 */
	@Override
	public Long getClientOnlineTime(List<ChOnlineHistory> historyList){

		return this.getCommonTime(historyList, 2, 0);
	}
	

	@Override
	public Long getClientOfflineTime(List<ChOnlineHistory> historyList) {
		// TODO Auto-generated method stub
		return this.getCommonTime(historyList, 2, 1);
	}

	@Override
	public Long getClientLeaveTime(List<ChOnlineHistory> historyList) {
		// TODO Auto-generated method stub
		return this.getCommonTime(historyList, 2, 2);
	}
	/**
	 * 时间求和公共方法
	 * @param historyList
	 * @param loginType
	 * @param onlineType
	 * @return
	 */
	public Long getCommonTime(List<ChOnlineHistory> historyList,Integer loginType,Integer onlineType){
		long  countTime = 0L;
		for (ChOnlineHistory chOnlineHistory : historyList) {
			//若果是客户端登录且离开
			if(chOnlineHistory!=null && chOnlineHistory.getLogintype()==loginType&&chOnlineHistory.getOnlinetype()==onlineType){
				countTime += chOnlineHistory.getCreatetime().getTime();
			}
		}
		return countTime;
		
	}
	
	/**
	 * 计算撮合员客户端在线时长
	 * @param historyList
	 * @return
	 */
	public Long getOnlinTimesClient(List<ChOnlineHistory> historyList){
		long countTime = 0L;
		for (ChOnlineHistory chOnlineHistory : historyList) {
			if(chOnlineHistory.getLogintype()==2){//只计算客户端在线的
				if( chOnlineHistory.getEndTime()!=null){
				countTime += chOnlineHistory.getEndTime().getTime()-chOnlineHistory.getCreatetime().getTime();
				}
			}
			
		}
		return countTime;
	}
	
	/**
	 * 获取有效会话数
	 * @param dcrlist
	 * @return
	 */
	public Integer getValidChats(List<DiscussChatRecord> dcrlist){
		Map<Integer,Object> map = new HashMap<Integer, Object>();
		Map<Integer,String> validMap = new HashMap<Integer, String>();
		for (DiscussChatRecord discussChatRecord : dcrlist) {//循环某日的所有聊天
			if(!"3".equals(discussChatRecord.getType())){//过滤系统消息
			if(map.get(discussChatRecord.getChatId())!=null){
				if(!"true".equals(discussChatRecord.getChatId())){//过滤已经效验的chat
				if(!map.get(discussChatRecord.getChatId()).equals(discussChatRecord.getChatFromType())){//过滤同一方一直说话
				map.put(discussChatRecord.getChatId(), "true");
				validMap.put(discussChatRecord.getChatId(), "true");//效验chat的结果集
				}
				}
			}else{
				map.put(discussChatRecord.getChatId(), discussChatRecord.getChatFromType());
			}
			}
		}
		
		return validMap.size();
	}
	/**
	 * 沟通时总长
	 * @param dcrlist
	 * @return
	 */
	public Long getChatTimes(List<DiscussChatRecord> dcrlist){
		long times = 0L;
		for (DiscussChatRecord discussChatRecord : dcrlist) {
			long maxtime = discussChatRecord.getMaxCreateTime().getTime();
			long mintime = discussChatRecord.getMinCreateTime().getTime();
			times += maxtime-mintime;
		}
		return times;
	}
	
	public BigDecimal getAmount(List<ChSubOrdersEntity> orderlist){
		BigDecimal amount = new BigDecimal(0d);
		for (ChSubOrdersEntity chSubOrdersEntity : orderlist) {
			if(chSubOrdersEntity.getPrice()!=null&&chSubOrdersEntity.getQuantity()!=null){				
				amount = amount.add(chSubOrdersEntity.getPrice().multiply(chSubOrdersEntity.getQuantity()));
			}
		}
		return amount;
	}

	@Override
	public List<ChUsersEntity> onlineCount(ChatCount chatCount) {
		// TODO Auto-generated method stub
		List<ChUsersEntity> userList = chUserDao.queryUserListPage(chatCount);
		
		return userList;
	}


}
