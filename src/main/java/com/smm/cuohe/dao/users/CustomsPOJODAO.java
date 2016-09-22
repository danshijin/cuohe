package com.smm.cuohe.dao.users;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.CustomsPOJO;

public interface CustomsPOJODAO {
	/**
	 * 最新加入客户
	 */
	public  List<CustomsPOJO> getCustomsList(String itemsID);
	/**
	 * 根据企业类型id查询名称
	 */
	
	public  List<Map<String, Object>> getCategList(String cateId);
}
