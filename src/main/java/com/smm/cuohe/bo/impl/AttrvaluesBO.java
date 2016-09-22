package com.smm.cuohe.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.dealmake.impl.SellPoolBOImpl;
import com.smm.cuohe.dao.IAttrValuesDao;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.dealmake.CommodityEntity;
import com.smm.cuohe.domain.dealmake.ProductEntity;
import com.smm.cuohe.util.URLRequest;
@Service
public class AttrvaluesBO implements IAttrValuesBO {
	
	private Logger logger = Logger.getLogger(AttrvaluesBO.class);
	@Resource
	private IAttrValuesDao attrValuesDao;
	@Value("#{ch['dealdemall.URL']}")
	private String  dealdemall;

	@Override
	public List<AttrValue> getAttrValuesByExtId(Integer extid) {
		List<AttrValue> list = this.attrValuesDao.getAttrValuesByExtId(extid);
		return list;
	}

	@Override
	public List<AttrValue> getSellPoolByExtid(Integer extid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("extid", extid);
		return this.attrValuesDao.getSellPoolByExtid(paramMap);
	}

	@Override
	public void updateAttrvalue(String extid) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("extid", extid);
		this.attrValuesDao.updateAttrvalue(param);
	}

	@Override
	public List<ProductEntity> getproductitemId(Integer itemId) {
		// TODO Auto-generated method stub
		return attrValuesDao.getproductitemId(itemId);
	}

	@Override
	public List<CommodityEntity> getcommodityproductId(Integer prodId) {
		// TODO Auto-generated method stub
		return attrValuesDao.getcommodityproductId(prodId);
	}

	@Override
	public List<AttrValue> getcommodityattr(Integer commodityId) {
		// TODO Auto-generated method stub
		return attrValuesDao.getcommodityattr(commodityId);
	}

	@Override
	public Integer checkcommodity(AttrValue attrValue) {
		// TODO Auto-generated method stub
		return attrValuesDao.checkcommodity(attrValue);
	}

	@Override
	public Integer addCommodity(CommodityEntity commodtity) {
		// TODO Auto-generated method stub
		return attrValuesDao.addCommodity(commodtity);
	}

	@Override
	public void addCommodityAttr(AttrValue attrValue) {
		// TODO Auto-generated method stub
		attrValuesDao.addCommodityAttr(attrValue);
	}

	@Override
	public Integer checkcommodity2(List<AttrValue> attrvalueList) {
		HashMap map = new HashMap<>();
		map.put("attrvalueList", attrvalueList);
		map.put("size", attrvalueList.size());
		return attrValuesDao.checkcommodity2(map);
	}

	/**
	 * 根据商品Id查询产品Id
	 */
	public Integer getProductIdByComId(Integer commodityId) {
		return attrValuesDao.getProductIdByComId(commodityId);
	}

	@Override
	public Integer queryAttrId(AttrValue attrValue) {
		// TODO Auto-generated method stub
		return attrValuesDao.queryAttrId(attrValue);
	}

	@Override
	public String queryproNameProId(Integer prodId) {
		// TODO Auto-generated method stub
		return attrValuesDao.queryproNameProId(prodId);
	}

	
	

}
