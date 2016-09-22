package com.smm.cuohe.bo;

import java.util.Map;

import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.SellPool;

/**
 * 提供商城接口
 * @author tantaigen
 *
 */
public interface MallPoolAPIBo {
	
	/**
	 * 发布卖盘
	 * @param sellPool
	 * @return
	 */
	public Map<String, Object>  addsellpool(SellPool sellPool);
	
	
	/**
	 * 
	 * 发布买盘
	 * @param buyPoolEntity
	 * @return
	 */
	public Map<String, Object>  addbuypool(BuyPoolEntity buyPoolEntity);

}
