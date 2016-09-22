package com.smm.cuohe.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IChatsBO;
import com.smm.cuohe.dao.IChatsDAO;
import com.smm.cuohe.dao.IContacterDAO;
import com.smm.cuohe.dao.base.ChChatSEntityMapper;
import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChChatSEntity;
@Service
public class ChatsBO implements IChatsBO{

	
	
	@Autowired
	IChatsDAO  iChatsDAO;
	@Autowired
	ChChatSEntityMapper chatDao;
	@Override
	public Map<String,String> getChatsById(Integer id) {
		return iChatsDAO.getChatsById(id);
	}

	@Override
	public void removeChats(String... array) {
		iChatsDAO.removeChats(array);
		
	}

	@Override
	public List<Map<String, Object>> getChatsList(Map map) {
		return iChatsDAO.getChatsList(map);
	}

	@Override
	public Integer getChatsCount(Map map) {
		return iChatsDAO.getChatsCount(map);
	}
	
	@Override
	public int updateOrderStatus(int itemId){
		return iChatsDAO.updateOrderStatus(itemId);
	}

	@Override
	public void delChats(String... array) {
		iChatsDAO.delChats(array);
	}

	@Override
	public void updateStatusById(Map map) {
		iChatsDAO.updateStatusById(map);
	}

	@Override
	public Map<String, Object> getAllRecord(int chatId, int start, int len) {
		
		Map<String, Object> map = new HashMap<String, Object>();
    	
    	map.put("status", "ok");
    	
    	map.put("msg", "");
    	
    	map.put("total", iChatsDAO.getAllRecordCount(chatId));
    	
    	map.put("rows", iChatsDAO.getAllRecord(chatId, start, len));
		
		return map;
	}

	@Override
	public int countByMallAccount(String account) {
		return iChatsDAO.countByMallAccount(account);
	}

	@Override
	public int getSellIdById(Integer id) {
		// TODO Auto-generated method stub
		return iChatsDAO.getSellIdById(id);
	}

	@Override
	public ChChatSEntity findChatById(Integer id) {
		// TODO Auto-generated method stub
		return chatDao.selectByPrimaryKey(id);
	}
	
	
	
    
}
