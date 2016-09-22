package com.smm.cuohe.dao.users;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.BuyPoolPOJO;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.ItemAttr;

public interface BuyPoolPDAO {
	/**
	 * 最新加入客户
	 */
	public  List<BuyPoolPOJO> getBuyPoolList(String itemsID);
	/**
	 * 根据id查询买盘信息
	 */
	public BuyPoolPOJO getBuyPool(String bid);
	/**
	 * 查询某个属性的名称，值
	 */
	public CommodityAttr getAttrVal(Map<String, Object> map);
	
	/**
	 * 更新数据
	 * @param buyPoolPOJO
	 */
	public void updateSellPoolPrice(BuyPoolPOJO buyPoolPOJO);
}
