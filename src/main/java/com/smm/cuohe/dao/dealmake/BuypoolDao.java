package com.smm.cuohe.dao.dealmake;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.BuyPoolAttr;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.BuypublishEntity;
import com.smm.cuohe.domain.dealmake.ItemsArrtEntity;

/**
 * atuh tanataigen
 * dao 买盘池
 * 
 */
@Repository
public interface BuypoolDao {
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
	
	 public  int    delbuypool(Integer id);
	
	/**
	 * 添加买盘
	 * 
	 * 
	 */
	 public int addbuyPool(BuyPoolEntity buy);
	
	/**
	 * 
	 * 发布买盘
	 * 
	 */
	 public void buypublish(BuypublishEntity buypub);
	
	/**
	 * 
	 *查询产品属性信息 
	 * 
	 */
	public List<ItemsArrtEntity> queryItemsArrt(Map<String, String> map);
	
	/**
	 * 
	 * 根据ID查询买盘信息
	 * 
	 */
	
	public BuyPoolEntity getbuypoolID(Integer id );
	
	public BuyPoolEntity getbuypoolById(Integer id);
	
	/**
	 * 根据id获得导出文件数据
	 */
	public List<BuyPoolEntity> getBuyPoolExportData(String[] array);
	
	/**
	 * 
	 * 添加扩展属性
	 * 
	 */
	public void addArrtvalue(AttrValue attr);
	/**
	 * 查询扩展属性
	 * 
	 */
	
	public  List<AttrValue> queryAttrvalue(String guid);
	
	public Integer countbuypool( Map<String , Object> map);
	/**
	 * 处理买盘池
	 * 
	 */
	public  int  probuypool(Integer id);
	/**
	 * 
	 * 添加买盘属性
	 * 
	 */
	public void addBuyPoolAttr(BuyPoolAttr attr);
	
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
	/** 获取客户ID
	 * @Title: getBuyPoolCompanyid 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: Integer
	 */
	public Integer getBuyPoolCompanyid(Map<String, Object> map);
	
	
}
