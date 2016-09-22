package com.smm.cuohe.bo.impl.users;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.users.IBuyPoolPBO;
import com.smm.cuohe.dao.users.BuyPoolPDAO;
import com.smm.cuohe.domain.BuyPoolPOJO;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.ItemAttr;

@Service
public class BuyPoolPBoImpl implements IBuyPoolPBO{
	
	@Resource
	private BuyPoolPDAO pDao;

	@Override
	public List<BuyPoolPOJO> getBuyPoolList(String itemsID) {
		List<BuyPoolPOJO> blist=this.pDao.getBuyPoolList(itemsID);
		return blist;
	}

	@Override
	public BuyPoolPOJO getBuyPool(String bid) {
		BuyPoolPOJO bPojo=this.pDao.getBuyPool(bid);
		return bPojo;
	}

	@Override
	public CommodityAttr getAttrVal(Map<String, Object> map) {
		return pDao.getAttrVal(map);
	}

	@Override
	public void updateSellPoolPrice(BuyPoolPOJO buyPoolPOJO) {
		try {
			pDao.updateSellPoolPrice(buyPoolPOJO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
