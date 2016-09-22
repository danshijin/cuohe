package com.smm.cuohe.bo.users;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.BuyPoolPOJO;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.ItemAttr;

public interface IBuyPoolPBO {
	/**
	 * 最新加入客户
	 */
	public List<BuyPoolPOJO> getBuyPoolList(String itemsID);
	/**
	 * 根据id查询买盘信息
	 */
	public BuyPoolPOJO getBuyPool(String bid);
	/**
	 * 查询某个属性的值
	 */
	public CommodityAttr getAttrVal(Map<String, Object> map);
	
	/**
	 * 更新数据
	 * @param buyPoolPOJO
	 */
	public void updateSellPoolPrice(BuyPoolPOJO buyPoolPOJO);
}
