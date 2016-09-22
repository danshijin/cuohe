package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.MallPoolInfo;
import com.smm.cuohe.domain.MallQueryOrder;
import com.smm.cuohe.domain.MallQueryPar;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.QueryPriceCount;

/**
 * 
 * @author haokang
 *撮合2.0提供数据查询接口
 */
public interface IMallQueryAPIBO {
	/**
	 * 指定客户所有买盘列表
	 * @return
	 */
	public List<MallPoolInfo> queryAllBuyPoolBy(MallQueryPar model);
	
	/**
	 * 指定客户参与的买盘列表
	 * @return
	 */
	public List<MallPoolInfo> queryInBuyPoolBy(MallQueryPar model);
	
	/**
	 * 指定客户所有卖盘列表
	 * @return
	 */
	public List<MallPoolInfo> queryAllSellPoolBy(MallQueryPar model);
	
	/**
	 * 指定客户参与的卖盘列表
	 * @return
	 */
	public List<MallPoolInfo> queryInSellPoolBy(MallQueryPar model);
	
	/**
	 * 每日报价接口： 参考价格、 最高价、最低价
	 * @return
	 */
	public ItemPrice queryTodayPriceBy(MallQueryPar model);
	
	/**
	 * 当天除指定客户外所有的报盘（卖盘、买盘）列表。
	 * @return
	 */
	public List<MallPoolInfo> queryTodayPoolBy(MallQueryPar model);
	
	/**
	 * 指定客户所有买盘列表
	 * @return
	 */
	
	public List<MallPoolInfo> queryPoolPriceInfo(MallQueryPar model);
	
	/**
	 * 每个报盘对应查询： 1，首次还盘记录。 2，最新2条还盘记录， 3，当前用户的还盘记录
	 * @return
	 */
	public String queryPoolPriceInfo2(MallQueryPar model);
	
	/**
	 * 当前报盘的还盘记录数
	 * @param model
	 * @return
	 */
	public Integer queryCountPool(MallQueryPar model);
	
	
	
	public Integer queryTodayPoolBycount(MallQueryPar model);
	/**
	 * 当天所有的报盘（卖盘、买盘）列表。
	 * @return
	 */
	public List<MallPoolInfo> queryTodayPoolByAll(MallQueryPar model);
	
	/**
	 * 查询报盘还盘数
	 * @param id
	 * @return
	 */
	public List<QueryPriceCount> queryPriceCount(String[] array);
	/**
	 * 查询订单
	 * @param model
	 * @return
	 */
	
	public List<MallQueryOrder> queryOrder(MallQueryPar model);
	
	
	public List<MallQueryOrder> queryOrderCount(MallQueryPar model);

	
	/** 获取所有品类的推荐，最高，最低报盘价
	 * @return
	 */
	public Map<String, Object> getAllItemPrice();
	
	/**
	 * 判断商城用户是否已存在
	 * @param model
	 * @return
	 */
	public Integer isExistAccount(MallQueryPar model);
	
	/**
	 * 根据buying_id获取买盘信息
	 * @param id
	 * @return
	 */
	public List<MallQueryOrder>  getBuyingAttrsByBuyingId(int id);
	
	/**
	 * 根据commodity_id获取卖盘信息
	 * @param id
	 * @return
	 */
	public List<MallQueryOrder> getCommodityAttrByBuyingId(int id);
	
	/**
	 * 获取属性
	 * @param map
	 * @return
	 */
	public List<CommodityAttr> getCommodityAttrList(Map<String, Object> map);
	
	/**
	 * 获取属性
	 * @param map
	 * @return
	 */
	public List<CommodityAttr> getBuyingAttrsList(Map<String, Object> map);
	
	/**
	 * 获取还盘信息
	 * @param map
	 * @return
	 */
	public List<PoolPrice> getPoolPrice(Map<String, Object> map);
	
	
}
