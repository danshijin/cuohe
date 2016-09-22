package com.smm.cuohe.dao;


import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.DiscussChatRecord;

public interface IDiscussChatRecordDAO {
	
	public Integer addRecord(DiscussChatRecord record);
	
	/** 使用chatId 和 token 对会话信息进行验证 
	 * @param record
	 * @param token
	 * @return -1 表示验证失败，1表示验证成功并新增会话记录 
	 */
	public Integer addRecordWithCheck(@Param(value="record") DiscussChatRecord record, @Param(value="token")String token);
	
	public Integer changeRecordStatusByDealMaker(@Param(value="chatRecordId") Integer chatRecordId, @Param(value="destStatus") Integer destStatus, @Param(value="overtimeSeconds")Integer overtimeSeconds,@Param(value="sysTime")Date sysTime);
	
	public Integer isWakeUp(@Param(value="chatId")int chatId, @Param(value="employeeId")int employeeId);
	
	public int hasItemIdForDealMaker(int itemId);
}
