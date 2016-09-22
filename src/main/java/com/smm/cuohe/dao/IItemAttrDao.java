package com.smm.cuohe.dao;


import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.ItemAttr;






import com.smm.cuohe.domain.Items;

import java.util.List;

import com.smm.cuohe.domain.ItemAttr;


public interface IItemAttrDao {
	
	
	public  List<Map<String,String>> getAll(Map<String, Object> map);
	public   Integer selectItemAttrByname(Map map);
	
	public  Integer insertItemAttr(ItemAttr itemAttr);
	
	/**
	 * 根据ID查询
	 * @param itemAttr
	 * @return
	 */
	public  ItemAttr selectByPrimaryKey(Integer id);
	
	
	/**
	 * 排序+
	 * @param itemAttr
	 * @return
	 */
	public  void updateSorting(java.util.Map map);
	

	/**
	 * 下
	 * @param listOrder
	 * @return
	 */
	public  java.util.Map getidBySorting1(Integer listOrder);
	/**
	 * 上
	 * @param listOrder
	 * @return
	 */
	public  java.util.Map getidBySorting2(Integer listOrder);



	
	public  Integer getmaxlistOrder();
	
	public Integer disable(String... array) ;

	List<ItemAttr> getItemAttrByItemsId(int itemsID);
	public ItemAttr getItemAttrById(Map<String, Object> param);
	
	/** 获取商品信息
	 * @Title: getItemsById 
	 * @Description: TODO
	 * @param result
	 * @return
	 * @author zhangnan/张楠
	 * @return: Items
	 * @createTime 2015年12月18日上午10:06:29
	 */
	public Items getItemsById(Map<String, Object> result);


}
