package com.smm.cuohe.bo;

import java.util.List;

import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.dealmake.CommodityEntity;
import com.smm.cuohe.domain.dealmake.ProductEntity;

public interface IAttrValuesBO {

	List<AttrValue> getAttrValuesByExtId(Integer extid);

	List<AttrValue> getSellPoolByExtid(Integer extid);

	void updateAttrvalue(String extid);
	/**
	 * 查询品目下的产品
	 * @param itemId
	 * @return
	 */
	public List<ProductEntity> getproductitemId(Integer itemId);
	
	/**
	 * 查询品目下的所有商品
	 * @param prodId
	 * @return
	 */
	public List<CommodityEntity> getcommodityproductId(Integer prodId);
	
	/**
	 * 查询所有商品
	 * @param commodityId
	 * @return
	 */
	public List<AttrValue> getcommodityattr(Integer commodityId);
	
	/**
	 * 验证商品是否存在
	 * @param attrValue
	 * @return
	 */
	public  Integer checkcommodity(AttrValue attrValue);
	
	
	/**
	 * 添加商品
	 * 
	 * @param commodtity
	 * @return
	 */
	public Integer addCommodity(CommodityEntity commodtity);
	
	
	/**
	 * 添加商品属性
	 * 
	 * @param attrValue
	 */
	public void addCommodityAttr(AttrValue attrValue);

	/**
	 * 验证商品是否存在
	 * @param attrValue
	 * @return
	 */
	Integer checkcommodity2(List<AttrValue> attrvalueList);
	
	/**
	 * 根据商品Id查询产品Id
	 */
	public Integer getProductIdByComId(Integer commodityId);
	/**
	 * 查询属性编号
	 * @param attrValue
	 * @return
	 */
	public Integer  queryAttrId(AttrValue attrValue);
	/**
	 * 查询产品名称
	 * @param prodId
	 * @return
	 */
	public String queryproNameProId(Integer prodId);
	
}
