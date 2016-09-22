package com.smm.cuohe.dao;

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
 *
 */
public interface IMallQueryDao {
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
	 * 当天所有的报盘（卖盘、买盘）列表。
	 * @return
	 */
	public List<MallPoolInfo> queryTodayPoolBy(MallQueryPar model);
	/**
	 * 当天所有的报盘（卖盘、买盘）列表。
	 * @return
	 */
	public List<MallPoolInfo> queryTodayPoolByAll(MallQueryPar model);
	public Integer queryTodayPoolBycount(MallQueryPar model);
	
	/**
	 * 每个报盘对应查询： 1，首次还盘记录。 2，最新2条还盘记录， 3，当前用户的还盘记录
	 * @return
	 */
	public List<MallPoolInfo> queryPoolPriceInfo(MallQueryPar model);
	
	/**
	 * 1，首次还盘记录。 
	 */
	public List<MallPoolInfo> queryPoolPriceFirst(MallQueryPar model);
	
	
	/**
	 * 2，最新2条还盘记录， 
	 */
	public List<MallPoolInfo> queryPoolPriceNew2(MallQueryPar model);
	
	/**
	 * 3，当前用户的还盘记录
	 */
	public List<MallPoolInfo> queryPoolPriceCurrentCustomer(MallQueryPar model);
	
	/**
	 * 查询报盘还盘数
	 * @param id
	 * @return
	 */
	public List<QueryPriceCount> queryPriceCount(String[] array  );
	
	
	/**
	 * 查看订单列表
	 * @param model
	 * @return
	 */
	public List<MallQueryOrder> queryOrder(MallQueryPar model);
	public List<MallQueryOrder> queryOrderCount(MallQueryPar model);
	
	/** 获取所有品类的推荐，最高，最低报盘价
	 * @return
	 */
	public List<ItemPrice> getAllItemPrice();
	
	/**
	 * 判断商城用户是否已存在
	 * @param model
	 * @return
	 */
	public Integer isExistAccount(MallQueryPar model);

	/**
	 * 根据poolid查询当前报盘的信息
	 * @param model
	 * @return
	 */
	public MallQueryPar queryPoolById(MallQueryPar model);
	
	/**
	 * 根据buying_id获取买盘信息
	 * @param id
	 * @return
	 */
	public List<MallQueryOrder> getBuyingAttrsByBuyingId(int id);
	
	/**
	 * 根据commodity_id获取卖盘信息
	 * @param id
	 * @return
	 */
	public List<MallQueryOrder> getCommodityAttrByBuyingId(int id);

	/**
	 * 报盘还盘记录总数
	 * @param model
	 * @return
	 */
	public Integer queryCountPool(MallQueryPar model);
	
	public List<CommodityAttr> getCommodityAttrList(Map<String, Object> map);
	
	public List<CommodityAttr> getBuyingAttrsList(Map<String, Object> map);
	
	public List<PoolPrice> getPoolPrice(Map<String, Object> map);

}
