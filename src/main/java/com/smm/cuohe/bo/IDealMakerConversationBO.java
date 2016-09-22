package com.smm.cuohe.bo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.User;

public interface IDealMakerConversationBO {

	Map<String, Object> login(String account, String pwd, String rootPath);

	Map<String, Object> getConversations(Integer itemId, Integer employeeId,String flagTime);

	Map<String, Object> getChatForDealMaker(Map<String, Object> parasMap);

	Map<String, Object> sendChatForDealMarker(DiscussChatRecord record, String token);

	Map<String, Object> countConversationByStatusAndCustomIdForDealMaker(Integer customId, Integer status);

	Map<String, Object> countConversationByCustomIdAndItemIdForDealMaker(Integer customId, Integer itemId);

	Map<String, Object> updateConversationStatusForDealMaker(Map<String, Object> parasMap);

	Map<String, Object> getMyTeamInfo(Integer itemId, Integer dealMakerId);

	Map<String, Object> confirmOrderByDealMaker(Map<String, Object> parasMap, HttpServletRequest request);

	Map<String, Object> transferConversationByDealMaker(Integer conversationId, Integer currDealMakerId, Integer destDealMakerId);

	/**
	 * @param chatRecordId   会话记录
	 * @param destStatus     更新后状态
	 * @param overtimeSeconds 超时时间（秒）
	 * @return -1  表示超时， 0表示正常更新 ，>0表示当前会话记录不可更新
	 */
	Map<String, Object> changeRecordStatusByDealMaker(Integer chatRecordId, Integer destStatus, Integer overtimeSeconds);

	Map<String, Object> addWakeUpRecord(Integer chatId, Integer dealMakerId);

	Map<String, Object> getCommodityByIdForDealMaker(int commodityId);

	Map<String, Object> getItemAttrByItemId(int itemId);

	Map<String, Object> updateOnlineStatus(int dealMakerId, int online);

	User getUserForGenerateOrder(int chatId);
	
	Items getItemById(int itemId);

	Map<String, Object> getAllProvince();

	Map<String, Object> getWarehouseByProvince(Integer provinceId, Integer itemId);

	Map<String, Object> hasCustomerByAccount(String account, Integer itemId,String companyName);

	Map<String, Object> countHistoryOrder(String customerName, Integer itemId);

	

	

}
