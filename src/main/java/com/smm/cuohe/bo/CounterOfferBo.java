package com.smm.cuohe.bo;

import java.util.List;

import com.smm.cuohe.domain.CounterOfferEntity;
import com.smm.cuohe.domain.SysNotice;

public interface CounterOfferBo {
	/**
	 * 查看指定卖盘最高还盘金额
	 * 
	 * @param poolId
	 * @return
	 */
	public String querySellMaxPrice(Integer poolId);

	/**
	 * 查看指定买盘最高还盘金额
	 * 
	 * @param poolId
	 * @return
	 */
	public String queryBuyMinPrice(Integer poolId);

	/**
	 * 查询买盘是否存在
	 * 
	 * @param poolId
	 * @return price
	 */
	public Float isBuypoolId(Integer poolId);

	/**
	 * 查询卖盘是否存在
	 * 
	 * @param poolId
	 * @return price
	 */
	public Float isSellpoolId(Integer poolId);

	/**
	 * 更新买盘
	 * 
	 * @param counterOfferEntity
	 */
	public void updateBuypool(CounterOfferEntity counterOfferEntity);

	/**
	 * 更新卖盘
	 * 
	 * @param counterOfferEntity
	 */
	public void updateSellpool(CounterOfferEntity counterOfferEntity);

	/**
	 * 新建还盘
	 * 
	 * @param counterOfferEntity
	 */
	public void addCounterOffer(CounterOfferEntity counterOfferEntity);

	/**
	 * 修改客户指定报盘还价
	 * 
	 * @param counterOfferEntity
	 */
	public void updateCounterOffer(CounterOfferEntity counterOfferEntity);

	/**
	 * 查询指定客户是否对指定报盘还价
	 * 
	 * @param counterOfferEntity
	 * @return
	 */
	public Integer queryCounterOfferId(CounterOfferEntity counterOfferEntity);

	/**
	 * 撤销买盘
	 * 
	 * @param counterOfferEntity
	 */
	public void closeBuyPool(CounterOfferEntity counterOfferEntity);

	/**
	 * 撤销卖盘
	 * 
	 * @param counterOfferEntity
	 */
	public void closeSellPool(CounterOfferEntity counterOfferEntity);

	/**
	 * 查询系统通知
	 * 
	 * @param sysNotice
	 * @return
	 */
	public List<SysNotice> querySysNotice(SysNotice sysNotice);

	/**
	 * 删除指定系统消息
	 * 
	 * @param NoticeId
	 */
	public void delSysNotice(Integer NoticeId);
	
	/**
	 * 消息已读
	 */
	public boolean updateNoticeIsRead(int array[]);

	/**
	 * 修改买盘
	 * 
	 * @param counterOfferEntity
	 */
	public void updateBuyPrice(CounterOfferEntity counterOfferEntity);

	/**
	 * 修改卖盘
	 * 
	 * @param counterOfferEntity
	 */
	public void updateSellPrice(CounterOfferEntity counterOfferEntity);

	public Integer querySysNoticeNotRead(SysNotice sysNotice);
}
