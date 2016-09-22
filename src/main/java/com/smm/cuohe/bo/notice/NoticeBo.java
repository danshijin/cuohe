package com.smm.cuohe.bo.notice;

import java.util.Map;

import com.smm.cuohe.domain.PoolPrice;

public interface NoticeBo {
	/**
	 * 1订单成交
	 * @param poolid
	 * @param poolType
	 */
	public void orderSubNotify(Integer poolid,Integer poolType);
	/**
	 * 2价格失效
	 * @param poolid
	 * @param poolType
	 */
	public void priceInvalidNotify(Map<String,Object> map);
	/**
	 * 3.交易失败
	 * @param poolid
	 * @param poolType
	 */
	public void transactionFail(Integer poolid,Integer poolType);
	
	/**
	 * 4.交易关闭
	 * @param poolid
	 * @param poolType
	 */
	public void transactionClose(Integer poolid,Integer poolType);
	
	/**
	 * 5.新出价
	 * @param poolid
	 * @param poolType
	 * @throws Exception 
	 */
	public void newOffer(PoolPrice poolPrice) throws Exception;
	

}
