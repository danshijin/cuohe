package com.smm.cuohe.bo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaoyutao
 *
 */
public interface ICustomerConversationBO {
	
	public Map<String, Object> createConversation(Map<String, Object> parasMap, HttpServletRequest request);
	
	Map<String, Object> getChatsForCustomerByToken(Map<String, Object> parasMap);

	Map<String, Object> mergeConversationForCustomer(Map<String, Object> parasMap);
	
	Map<String, Object> getContractHtml(int customerId, String token);

	Map<String, Object> closeConversationForCustomer(String token);

	Map<String, Object> customOnline(String token);

	Map<String, Object> confirmContract(String orderNo);
	

}
