package com.smm.cuohe.dao;

import java.util.List;

import com.smm.cuohe.domain.Commodity;
import com.smm.cuohe.domain.ItemAttr;

/** 
 * @author  zhaoyutao
 * @version 2015年9月11日 下午4:44:54 
 * 类说明 
 */
public interface ICommodityDAO {
	
	public List<Commodity> getCommodityByIdForDealMaker(int commodityId);
	
	public List<ItemAttr> getItemAttrByItemId(int itemId);
	
}
