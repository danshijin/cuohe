package com.smm.cuohe.dao.users;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.WebPartsPOJO;
public interface WebPartsPDAO {
	/**
	 * 最新加入客户
	 */
	public  List<WebPartsPOJO> getWebPartsPList(int userId);
	/**
	 * 删除组件
	 * @param map
	 */
	public Integer addOrDelWpById(Map<String, Object> map);
	/**
	 * 根据组件id和品目id查询
	 * @param map
	 * @return
	 */
	public WebPartsPOJO getWpById(Map<String, Object> map);
	
	public Integer insertWp(Map<String, Object> map);
	
}
