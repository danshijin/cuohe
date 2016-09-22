package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.base.ChChatSEntity;

public interface IChatsBO {
	
	 Map<String,String>  getChatsById(Integer id);
	
	 void removeChats(String ...array);

	 List<Map<String, Object>> getChatsList(Map map);
	 Integer getChatsCount(Map map);

	 int updateOrderStatus(int itemId);

	void delChats(String ...array);

	public void updateStatusById(Map map);
	
	public Map<String, Object> getAllRecord(int chatId, int start, int len);
	
	public int countByMallAccount(String account);
	
	public int getSellIdById(Integer id);
	
	
	public ChChatSEntity findChatById(Integer id);
}
