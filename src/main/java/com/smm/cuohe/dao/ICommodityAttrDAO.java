package com.smm.cuohe.dao;

import java.util.List;

import com.smm.cuohe.domain.CommodityAttr;

/** 
 * @author  zhaoyutao
 * @version 2015年9月11日 下午4:44:54 
 * 类说明 
 */
public interface ICommodityAttrDAO {

	public List<CommodityAttr> getCommidtyAttrByCommodityId(int commodityId);
	
}
