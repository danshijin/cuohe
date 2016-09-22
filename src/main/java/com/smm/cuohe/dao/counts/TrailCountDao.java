package com.smm.cuohe.dao.counts;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.counts.TrailEntity;
import com.smm.cuohe.domain.dealmake.OrdercodeEntity;


public interface TrailCountDao {
	
	/**
	 * 统计所有线索信息
	 * @param map
	 * @return
	 */
	public List<TrailEntity> trailLists(Map<String, Object> map);
		
	/**
	 * 查询所有用户	
	 * @return
	 */
	public List<TrailEntity>  userlists();
	
	/**
	 * 查询所有品目
	 * @return
	 */
	public  List<TrailEntity> itemslists();
	
	
	
	/**
	 * 订单统计
	 * @param map
	 * @return
	 */
	public List<OrdercodeEntity> queryordercounts(Map<String, Object> map);
	
	/**
	 * 报价统计
	 * @param map
	 * @return
	 */
	public List<OrdercodeEntity> querybaojiacounts(Map<String, Object> map);
	
	/**
	 * 采购统计
	 * @param map
	 * @return
	 */
	
	public List<OrdercodeEntity> querycaigoujiacounts(Map<String, Object> map);
	
	/**
	 * 查询企业
	 * @return
	 */
	public List<Customer> selectcompanyName(String itemId);
	
}
