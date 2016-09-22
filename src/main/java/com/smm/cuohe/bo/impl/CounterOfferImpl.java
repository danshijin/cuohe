package com.smm.cuohe.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.CounterOfferBo;
import com.smm.cuohe.dao.ICounterOfferDao;
import com.smm.cuohe.domain.CounterOfferEntity;
import com.smm.cuohe.domain.SysNotice;

@Service
public class CounterOfferImpl implements CounterOfferBo {

	@Resource
	private ICounterOfferDao dao;

	@Override
	public String querySellMaxPrice(Integer poolId) {
		// TODO Auto-generated method stub
		return dao.querySellMaxPrice(poolId);
	}

	@Override
	public String queryBuyMinPrice(Integer poolId) {
		// TODO Auto-generated method stub
		return dao.queryBuyMinPrice(poolId);
	}

	@Override
	public Float isBuypoolId(Integer poolId) {
		// TODO Auto-generated method stub
		return dao.isBuypoolId(poolId);
	}

	@Override
	public Float isSellpoolId(Integer poolId) {
		// TODO Auto-generated method stub
		return dao.isSellpoolId(poolId);
	}

	@Override
	public void updateBuypool(CounterOfferEntity counterOfferEntity) {
		// TODO Auto-generated method stub
		dao.updateBuypool(counterOfferEntity);
	}

	@Override
	public void updateSellpool(CounterOfferEntity counterOfferEntity) {
		// TODO Auto-generated method stub
		dao.updateSellpool(counterOfferEntity);
	}

	@Override
	public void addCounterOffer(CounterOfferEntity counterOfferEntity) {
		dao.addCounterOffer(counterOfferEntity);

	}

	@Override
	public void updateCounterOffer(CounterOfferEntity counterOfferEntity) {
		dao.updateCounterOffer(counterOfferEntity);

	}

	@Override
	public Integer queryCounterOfferId(CounterOfferEntity counterOfferEntity) {
		// TODO Auto-generated method stub
		return dao.queryCounterOfferId(counterOfferEntity);
	}

	@Override
	public void closeBuyPool(CounterOfferEntity counterOfferEntity) {
		dao.closeBuyPool(counterOfferEntity);

	}

	@Override
	public void closeSellPool(CounterOfferEntity counterOfferEntity) {
		dao.closeSellPool(counterOfferEntity);

	}

	@Override
	public List<SysNotice> querySysNotice(SysNotice sysNotice) {

		return dao.querySysNoticePage(sysNotice);
	}

	@Override
	public void delSysNotice(Integer NoticeId) {
		dao.delSysNotice(NoticeId);

	}

	@Override
	public void updateBuyPrice(CounterOfferEntity counterOfferEntity) {
		// TODO Auto-generated method stub
		dao.updateBuyPrice(counterOfferEntity);
	}

	@Override
	public void updateSellPrice(CounterOfferEntity counterOfferEntity) {
		dao.updateSellPrice(counterOfferEntity);

	}

	@Override
	public boolean updateNoticeIsRead(int array[]) {
		// TODO Auto-generated method stub
		return dao.updateNoticeIsRead(array)>0;
	}

	@Override
	public Integer querySysNoticeNotRead(SysNotice sysNotice) {
		// TODO Auto-generated method stub
		return dao.querySysNoticeNotRead(sysNotice);
	}

}
