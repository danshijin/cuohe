package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.base.ChPoolPriceEntity;



public interface IPoolPriceDao {
	
	/**
	 * 获取数据
	 * @return
	 */
	public List<PoolPrice>  queryIPoolPrice(Map<String, Object> map);
	
	/**
	 * 查询当前报盘正常的还盘列表 
	 * @param map
	 * @return
	 */
	public List<ChPoolPriceEntity> queryPoolPriceByPoolId(Map<String,Object> map);
	
	/**
	 * 根据Id关闭数据
	 * @param id
	 */
	void deleteById(Integer id);
	
	/**
	 * 根据Id获取数据
	 * @param id
	 * @return
	 */
	PoolPrice getPoolPriceById(Integer id);
	
	/**
	 * 根据报盘ID获取报盘最新金额
	 * @param poolId
	 * @return
	 */
	PoolPrice getPoolPriceByPoolId(Map<String, Object> map);
	
	/**
	 * 根据ID修改数据
	 * @param poolPrice
	 */
	void updatePool(PoolPrice poolPrice);
	
	/**
	 * 根据报盘ID和报盘类型获取是否是最新一条记录
	 * @param map
	 * @return
	 */
	PoolPrice getPoolPriceByMap(Map<String, Object> map);
	
	PoolPrice getPoolPriceByMap_one(Map<String, Object> map);
	
	PoolPrice getPoolPrice(Map<String, Object> map);
	
	List<PoolPrice> getPoolPriceList(Map<String, Object> map);
	
	
	/**
	 * 获取 日志信息
	 * @param map
	 * @return
	 */
	int insertnoticeList(Map<String, Object> map);
	
	
}
