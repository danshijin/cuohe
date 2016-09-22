package com.smm.cuohe.bo;


import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.ItemAttr;




import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.User;

import java.util.List;

import com.smm.cuohe.domain.ItemAttr;


public interface IItemAttrBO {
	
	public  List<Map<String,String>> getAll(Map<String, Object> map);
	
	public  Integer ItemAttrAdd(ItemAttr itemattr,User user);


	public   Integer selectItemAttrByname(java.util.Map map);
	
	
	
	
	
	/**
	 * 上移动
	 * @param itemAttr
	 * @return
	 */
	public  void updateSortingOn(Integer id,Integer sorting);
	
	
	/**
	 * 下移动
	 * @param itemAttr
	 * @return
	 */
	public  void updateSortingUnder(Integer id,Integer sorting);
	
	
	
	public  Integer getmaxlistOrder();
	
	
	public Integer disable(String ...ids);
	
	
	

	List<ItemAttr> getItemAttrByItemsId(int itemsID);

	public ItemAttr getItemAttrById(Integer attrId);

	/** 获取商品
	 * @Title: getItemsById 
	 * @Description: TODO
	 * @param result
	 * @return
	 * @author zhangnan/张楠
	 * @return: Items
	 * @createTime 2015年12月18日上午10:05:19
	 */
	public Items getItemsById(Map<String, Object> result);


}
