package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.BusinessOffer;
import com.smm.cuohe.domain.ChBuyPool;
import com.smm.cuohe.domain.ChSellPool;
import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.SerachParam;
import com.smm.cuohe.domain.base.ChCommodityEntity;

/**
 * @author  zhaoyutao
 * @version 2015年11月16日 下午7:14:58
 * 
 */

public interface IBusinessDAO {
	
	/** 获取买盘部分
	 * @param itemId
	 * @param sp
	 * @return
	 */
	public List<BusinessOffer> getBuyPoolBusiness(@Param(value="itemId") int itemId, @Param(value="sp") SerachParam sp);
	
	/** 获取卖盘部分
	 * @param itemId
	 * @param sp
	 * @return
	 */
	public List<BusinessOffer> getSellPoolBusiness(@Param(value="itemId") int itemId, @Param(value="sp") SerachParam sp);
	
	/** 关闭报盘，同时关闭相关的还盘
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	public int closeOffer(@Param(value="poolId") int poolId, @Param(value="poolType") int poolType);
	
	
	/** 关闭报盘
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	public int closeOfferNoCounter(@Param(value="poolId") int poolId, @Param(value="poolType") int poolType);
	
	
	/** 添加推荐，最高，最低报价
	 * @param itemPrice
	 * @return
	 */
	public int addItemPrice(ItemPrice itemPrice);
	
	/** 添加还盘
	 * @param poolPrice
	 * @return 处理结果 rltStatus 0 失败 1 成功 rltMsg 处理结果 
	 */
	public Map<String, Object> addCounter(@Param(value="counter") PoolPrice poolPrice, @Param(value="itemId") int itemId);
	
	/** 新增还盘后，对应报盘信息更改
	 * @param poolPrice
	 * @return 
	 */
	public int updateOffer(@Param(value="poolPrice") PoolPrice poolPrice);
	
	/** 还原报盘
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	public int restoreOffer(@Param(value="poolId") int poolId, @Param(value="poolType") int poolType);
	
	/**
	 * 还原还盘
	 * @param poolId
	 * @return
	 */
	public int restoreOfferPoolPrice(@Param(value="poolId") int poolId);
	
	/** 还原报盘
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	public int restoreOfferNoCounter(@Param(value="poolId") int poolId, @Param(value="poolType") int poolType);

	
	/** 交易知道价格
	 * @Title: getItemPrice 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: ItemPrice
	 * @createTime 2015年12月11日上午11:08:17
	 */
	public ItemPrice getItemPrice(Map<String, Object> param);

	/** 获取还盘信息，用于修改还盘的校验
	 * @Title: getPoolList 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<PoolPrice>
	 * @createTime 2015年12月11日上午11:07:47
	 */
	public List<PoolPrice> getPoolList(Map<String, Object> param);

	/** 修改还盘价格
	 * @Title: updPoolPrice 
	 * @Description: TODO
	 * @param param
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2015年12月11日上午11:29:26
	 */
	public int updPoolPrice(Map<String, Object> param);

	/** 获取商城账号
	 * @Title: getAccount 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2015年12月11日下午4:07:54
	 */
	public String getAccount(Map<String, Object> param);

	/** 新增还盘，撮合第二版加入  升贴价
	 * @Title: addCounterNew 
	 * @Description: TODO
	 * @param param
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2015年12月11日下午4:15:13
	 */
	public void addCounterNew(Map<String, Object> param);

	/** 卖盘，每次还盘成功都要修改买卖盘的最后一次报价（设计何意？）
	 * @Title: updateSellLastPrice 
	 * @Description: TODO
	 * @param map
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2015年12月14日上午11:00:41
	 */
	public void updateSellLastPrice(Map<String, Object> map);

	/** 买盘，每次还盘成功都要修改买卖盘的最后一次报价（设计何意？）
	 * @Title: updateBuyLastPrice 
	 * @Description: TODO
	 * @param map
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2015年12月14日上午11:01:21
	 */
	public void updateBuyLastPrice(Map<String, Object> map);

	public ChSellPool getSellPoolPrice(Map<String, Object> map);

	public ChBuyPool getBuyPoolPrice(Map<String, Object> map);

	/** 每次新增还盘之前，查询前一条的换盘信息
	 * @Title: queryCurrentMaxPoolPrice 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<PoolPrice>
	 * @createTime 2015年12月31日上午9:24:08
	 */
	public List<PoolPrice> queryCurrentMaxPoolPrice(Map<String, Object> map);

	/** 查询商品 卖盘
	 * @Title: queryChCommodityEntity 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @author zhangnan/张楠
	 * @return: ChCommodityEntity
	 * @createTime 2015年12月31日上午10:33:45
	 */
	public ChCommodityEntity queryChCommodityEntity(Map<String, Object> map);

	/** 查询商品 买盘
	 * @Title: queryChCommodityEntity_BuyPool 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @author zhangnan/张楠
	 * @return: ChCommodityEntity
	 * @createTime 2015年12月31日上午10:46:02
	 */
	public ChCommodityEntity queryChCommodityEntity_BuyPool(
			Map<String, Object> map);

	/** 查询一家公司是否已经对某一还盘还盘
	 * @Title: getPoolListThisCompany 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<PoolPrice>
	 * @createTime 2016年1月7日上午10:33:57
	 */
	public List<PoolPrice> getPoolListThisCompany(Map<String, Object> param);

	/** 同一个报盘，同一家公司第一次还盘是新增，后面的还盘都是更新
	 * @Title: updCounter 
	 * @Description: TODO
	 * @param param
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2016年1月7日上午10:39:11
	 */
	public void updCounter(Map<String, Object> param);

	/** 撮合交易管理选择单个报盘生成订单入口：“撮合订单”方式；生成订单后，报盘要关闭。2016年1月11日下午4:13:19
	 * @Title: closeSellPool 
	 * @Description: TODO
	 * @param params
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2016年1月11日下午4:33:05
	 */
	public void closeSellPool(Map<String, Object> params);

	public void closeBuyPool(Map<String, Object> params);

	public void updateBuyLastPrice_buyer(Map<String, Object> param);
}
