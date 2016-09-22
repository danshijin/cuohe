package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.base.ChCustomsEntity;


public interface IPoolPriceBo {
	
	
	/**
	 * 根据报盘类型获取数据
	 * @param map
	 * @return
	 */
	List<PoolPrice> getPoolPrice(Map<String, Object> map);
	
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
	PoolPrice getPoolPriceById (Integer id);
	
	/**
	 * 根据报盘ID和报盘类型获取报盘最新金额
	 * @param poolId,poolType
	 * @return
	 */
	PoolPrice getPoolPriceByPoolId(Map<String, Object> map);
	
	/**
	 * 根据ID修改数据
	 * @param map
	 */
	void updatePool(PoolPrice poolPrice);
	
	/**
	 * 根据报盘ID和报盘类型获取是否是最新一条记录
	 * @param map
	 * @return
	 */
	void getPoolPriceByMap(Map<String, Object> map);
	
	PoolPrice getPoolPriceByMap_one(Map<String, Object> map);

	/** 查询客户
	 * @Title: queryChCustomsEntityMapperById 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: ChCustomsEntity
	 * @createTime 2015年12月31日下午2:22:48
	 */
	ChCustomsEntity queryChCustomsEntityById(Map<String, Object> param);
	
}
