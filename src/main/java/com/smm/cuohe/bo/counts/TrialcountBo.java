package com.smm.cuohe.bo.counts;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.base.ChOnlineHistory;
import com.smm.cuohe.domain.base.ChUsersEntity;
import com.smm.cuohe.domain.counts.ChatCount;
import com.smm.cuohe.domain.counts.TrailEntity;
import com.smm.cuohe.domain.dealmake.OrdercodeEntity;

public interface TrialcountBo {

	List<TrailEntity>  trialquery(Map< String, Object> map);//所有线索信息
	List<TrailEntity> userlist();//所有用户信息
	List<TrailEntity> itemslist();//所有企业信息
	List<TrailEntity> trailListlike(Map<Object ,Object> map); //模糊查询线索
    List<OrdercodeEntity> queryordercount(Map<String, Object> map); //订单统计
    List<OrdercodeEntity> querybaojiacount(Map<String, Object> map);//报价统计
	public List<OrdercodeEntity> querycaigoujiacounts(Map<String, Object> map);//采购统计
	public List<Customer> selectcompanyName(String itemId);//查询企业
	public List<ChatCount> chatCountManage(ChatCount chatCount);//客户端在线统计
	public List<Object[]> chatExportExcel(ChatCount chatCount, String[] rowsName);//返回excel结果集
	public Long getClientOnlineTime(List<ChOnlineHistory> historyList);//当前用户在线时间总和
	public Long getClientOfflineTime(List<ChOnlineHistory> historyList);//当前用户离线时间总和
	public Long getClientLeaveTime(List<ChOnlineHistory> historyList);//当前用户离开时间总和
	public List<ChUsersEntity> onlineCount(ChatCount chatCount);
	
}
