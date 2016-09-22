package com.smm.cuohe.bo.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.ArrayList;
import java.util.List;









import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IItemAttrBO;
import com.smm.cuohe.dao.IItemAttrDao;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.User;
/**商品属性
 * @author zhangnan
 *
 */
@Service
public class ItemAttrBO implements IItemAttrBO{
	@Resource
	private IItemAttrDao itemAttrDao;
	
	
	public  List<Map<String,String>> getAll(Map<String, Object> map){
		return itemAttrDao.getAll(map);
	}

	public   Integer selectItemAttrByname(Map map){
		
		return itemAttrDao.selectItemAttrByname(map);
	}

	@Override
	public Integer ItemAttrAdd(ItemAttr itemattr,User user) {

    	itemattr.setUpdatedby(user.getId());
    	itemattr.setCreatedby(user.getId());
    	itemattr.setItemid(Integer.parseInt(user.getItemId()));
    	itemattr.setStatus(0);
		return itemAttrDao.insertItemAttr(itemattr);
	}


	
	
	/**
	 * 上移动
	 */
	@Override
	public void updateSortingOn(Integer id,Integer sorting) {
		Map<String, String> mapc=new HashMap<String, String>();
		
		
		Map map=itemAttrDao.getidBySorting2(id);
		if(map!=null){
		// TODO Auto-generated method stub
		Map map1=new HashMap();
		map1.put("id", id);
		map1.put("listOrder", map.get("listOrder"));
		Map map2=new HashMap();
		map2.put("id", map.get("id"));
		map2.put("listOrder", sorting);
		itemAttrDao.updateSorting(map1);
		itemAttrDao.updateSorting(map2);
		}
	}

	/**
	 * 下移动
	 */
	@Override
	public void updateSortingUnder(Integer id,Integer sorting) {
		// TODO Auto-generated method stub
		Map map=itemAttrDao.getidBySorting1(id);
		if(map!=null){
			Map map1=new HashMap();
			map1.put("id", id);
			map1.put("listOrder", map.get("listOrder"));
			Map map2=new HashMap();
			map2.put("id", map.get("id"));
			map2.put("listOrder", sorting);
			itemAttrDao.updateSorting(map1);
			itemAttrDao.updateSorting(map2);
		}
	}

	/**
	 * 获取配需属性的最大值
	 */
	@Override
	public Integer getmaxlistOrder() {
		// TODO Auto-generated method stub
		return itemAttrDao.getmaxlistOrder();
	}

	/**
	 * 禁用
	 */
	@Override
	public Integer disable(String... ids) {
		// TODO Auto-generated method stub
		return itemAttrDao.disable(ids);
	}


	  
    /** 
     * @discription 根据品目ID查询品目拓展属性
     * @author Nancy/张楠       
     * @created 2015年8月14日 下午4:37:35      
     * @param itemsID
     * @return     
     * @see com.smm.cuohe.bo.IItemAttrBO#getItemAttrByItemsId(int)     
     */  
	    
	@Override
	public List<ItemAttr> getItemAttrByItemsId(int itemsID) {
		List<ItemAttr> itemAttrList = itemAttrDao.getItemAttrByItemsId(itemsID);
		return itemAttrList;
	}

	@Override
	public ItemAttr getItemAttrById(Integer attrId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("id",attrId);
		return this.itemAttrDao.getItemAttrById(param);
	}

	@Override
	public Items getItemsById(Map<String, Object> result) {
		return this.itemAttrDao.getItemsById(result);
	}

}
