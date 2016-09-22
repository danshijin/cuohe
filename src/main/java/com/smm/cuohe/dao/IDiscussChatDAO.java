/**
 * 
 */
package com.smm.cuohe.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChChatSEntity;

/**
 * @author zhaoyutao
 *
 */
public interface IDiscussChatDAO {

	/**
	 *  1.根据lastToken查找chats表，如找到则直接返回
	 *	2.商城用户已登录，优先分配熟悉的撮合员
	 *	3.商城用户未登录，随机分配撮合员
	 * @param customerId  商城用户id
	 * @param customerSession 商城用户的浏览器session
	 * @return dealMakerId 撮合人员id， 
	 * 			convStatus 会话状态 -1 表示用户已经创建过会话，1：订单完成 2：未接通（匹配在线撮合员失败）3：订单未完成 4：洽谈中 5：等待洽谈
	 */
	public Map<String, Object> getDealMakerForConversation(@Param(value="customerId")Integer customerId,
			@Param(value="lastToken") String lastToken, @Param(value="customerProduct") Integer customerProduct,
			@Param(value="customerItemId") Integer customerItemId,
			@Param(value="sysTime")Date sysTime);
	
	public int insert(Chats conversation);
	
	public Chats getConversationByToken(String token);
	
	public List<DiscussChatRecord> getChatsForCustomerByToken(Map<String, Object> map); 
	
	public List<Chats> getConversationsForDealMaker(@Param(value="sysDate")Date sysdate,@Param(value="itemId")int itemId, @Param(value="employeeId") int employeeId);
	
	public List<DiscussChatRecord> getChatsForDealMaker(Map<String, Object> map);
	
	/**
	 * @param map
	 * @return 1 数据库事务出错，回滚 0 成功
	 */
	public int mergeConversationForCustomer(Map<String, Object> map);
	
	public int countConversationByStatusAndCustomIdForDealMaker(@Param(value = "customId") int customId, @Param(value = "status") int status);
	
	
	public int countConversationByCustomIdAndItemIdForDealMaker(@Param(value = "customId") int customId, @Param(value = "itemId") int itemId);
	
	public int countHistoryOrder(@Param(value="customerName") String customerName, @Param(value="itemId") Integer itemId);
	
	/**
	 * @param map
	 * @return 如果当前会话被其他撮合人员锁定，则返回该撮合人员的id，否则返回0
	 */
	public int updateConversationStatusForDealMaker(Map<String, Object> map);
	
	public List<Map<String, Object>> getMyTeamInfo(@Param(value="itemId")int itemId, @Param(value="dealMakerId")int dealMakerId);
	
	public int generateOrderByDealMaker(@Param(value="order") Order order, @Param(value="chatId")Integer chatId, @Param(value="customId")Integer customId);
	
	public int updateOrderStatusForDealMaker(Map<String, Object> map);
	
	public int transferConversationByDealMaker(@Param(value="conversationId") int conversationId,@Param(value="currDealMakerId")  int currDealMakerId,@Param(value="destDealMakerId")  int destDealMakerId,@Param(value="sysTime")  Date sysTime);
	
	public int getContractByOrderId(int orderId);
	
	public String getContractMallUrl(@Param(value="customerId")int customerId, @Param(value="token")String token);
	
	public int closeConversationForCustomer(String token);
	
	public int customOnline(@Param(value="token")String token,@Param(value="sysTime")Date sysTime);
	
	public Map<String, Object> getChatByToken(String token);
	
	public int customerConfirmContract(@Param(value="orderNo")String orderNo,@Param(value="sysTime")Date sysTime);
	
	public int updateOnlineStatusByDealMaker(@Param(value="dealMakerId")int dealMakerId, @Param(value="online") int online,@Param(value="sysTime")Date sysTime);
	
	public User getUserForGenerateOrder(int chatId);
	
	public Items getItemById(int itemId);
	
	public Map<String, Object> hasCustomerByAccount(@Param(value="account") String account, @Param(value="itemId") int itemId,@Param(value="companyName")String companyName);
	
	/**
	 * @param sellId
	 * @param url
	 * @param urlName
	 * @param token
	 * @return
	 */
	public int updateChatWhenSellIdChange(@Param(value="sellId") int sellId, @Param(value="url") String url, @Param(value="urlName") String urlName, @Param(value="token") String token,@Param(value="sysTime")Date sysTime);
	
	public int changeChatOrderStatusToFinish(String orderno);
	
	public int updateSellPoolToClose(String orderNo);

	public List<Chats> getChatOnline(@Param(value="sysDate")Date sysdate,@Param(value="itemId")Integer itemId,
			@Param(value="employeeId")Integer employeeId,@Param(value="flagDate")Date flagDate);

	public List<Chats> getChatoffline(@Param(value="sysDate")Date sysdate, @Param(value="itemId")Integer itemId,
			@Param(value="employeeId")Integer employeeId,@Param(value="flagDate")Date flagDate);

	public void updateTimeByToken(@Param(value="token")String token,@Param(value="sysTime")Date sysTime);
}
