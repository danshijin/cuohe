package com.smm.cuohe.bo.impl.users;

import com.smm.cuohe.bo.users.ISellPoolPBO;
import com.smm.cuohe.dao.users.SellPoolPDAO;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.SellPoolPOJO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SellPoolPBoImpl implements ISellPoolPBO{
	
	@Resource
	private SellPoolPDAO sPdao;

	@Override
	public List<SellPoolPOJO> getSellPoolList(String itemsID) {
		return this.sPdao.getSellPoolList(itemsID);
	}

	@Override
	public CommodityAttr getAttrVal(Map<String, Object> map) {
		return this.sPdao.getAttrVal(map);
	}
}
