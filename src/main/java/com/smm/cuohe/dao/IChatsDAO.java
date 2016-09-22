package com.smm.cuohe.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.User;

public interface IChatsDAO {
	Map<String,String> getChatsById(Integer id);
	
	void removeChats(String ...array);

	List<Map<String, Object>> getChatsList(Map map);
    Integer getChatsCount(Map map);
    
    public int updateOrderStatus(int itemId);

	void delChats(String ...array);
	
	public void updateStatusById(Map map);
	
	public int getAllRecordCount(int chatId);
	
	public List<DiscussChatRecord> getAllRecord(@Param(value="chatId") int chatId, @Param(value="start") int start, @Param(value="len") int len);
	
	public int countByMallAccount(String account);

	int getSellIdById(Integer id);

	int updateChatsById(Map<String, Object> params);
	
	public List<Chats> getChatsByemployeeId(Map<String, Object> map);
	
	public DiscussChatRecord getdiscussChatRecordByChatId(int chatID);
	
	public User getUserByEmployeeId(int employeeId);
	
	void updateById(Map<String, Object> map);
	
	public Integer getChatByEmpToday(@Param(value="empId")int empId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);//当天的会话总数
	
	public List<DiscussChatRecord> getValidChats(@Param(value="empId")int empId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);//有效沟通列表
	
	public List<DiscussChatRecord> getChatTimes(@Param(value="empId")int empId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);//有效沟通列表
	
}
