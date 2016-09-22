package com.smm.cuohe.bo.dealmake;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.BuyPoolAttr;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.BuypublishEntity;
import com.smm.cuohe.domain.dealmake.ItemsArrtEntity;

/*
 * auth tantaigen
 * bo  买盘池
 */

public interface BuyPoolBo {
	
	/**
	 * 买盘池列表
	 * 
	 */
	public List<BuyPoolEntity> buypoollist(Map<String, Object> map);
	
	/**
	 * 企业列表
	 * 
	 */
	public List<Customer>  companylist();
	/**
	 * 删除买盘
	 * 
	 */
	public int delbuypool(Integer id);
	
	/**
	 * 添加买盘
	 * 
	 * 
	 */
	public int addbuyPool(BuyPoolEntity buy);
	
	
	/**
	 * 发布买盘
	 * 
	 * 
	 */
	
	public void buypublish(BuypublishEntity buypub);
	
	/**
	 * 
	 * 查询商品属性
	 * 
	 * 
	 */
	
	public  List<ItemsArrtEntity> queryItemsArrt(Map<String, String> map);
	
	public BuyPoolEntity getbuypoolID(Integer id);
	
	public BuyPoolEntity getbuypoolById(Integer id);
	
	public List<BuyPoolEntity> getBuyPoolExportData(String[] array);
	
	
	public void addArrtvalue(AttrValue attr);
	
	public void addBuyPoolAttr(BuyPoolAttr attr);
	
	public  List<AttrValue> queryAttrvalue(String guid);
	public Integer countbuypool( Map<String , Object> map);
	/**
	 * 处理
	 * @param id
	 * @return
	 */
	public  int  probuypool(Integer id);

	public int getMaxIdfromBuypool();
	
	/**
	 * 查询买盘属性
	 */
	public List<AttrValue> selbuyAttrValueByBuyId(Integer buyId);
	
	
	
	/**
	 * 修改买盘
	 * @param buy
	 */
	public  void updateBuyPool( BuyPoolEntity buy);
	
	
	
   public Map<String, Object> updateMallPrice(Integer SortNum,double price ,Integer poolType ,String poolId,String quantity);
	
  
}
