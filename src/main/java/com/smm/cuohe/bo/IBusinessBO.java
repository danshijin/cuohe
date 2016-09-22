package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.SerachParam;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChBuyPoolEntity;
import com.smm.cuohe.domain.base.ChCustomsEntity;
import com.smm.cuohe.domain.base.ChPoolPriceEntity;
import com.smm.cuohe.domain.base.ChUsersEntity;
import com.smm.cuohe.domain.base.ChWareHouse;

/**
 * @author  zhaoyutao
 * @version 2015年11月16日 下午8:23:41
 * 报盘
 */

public interface IBusinessBO {
	
	/** 获取报盘
	 * @param itemId
	 * @return
	 */
	Map<String, Object> getPoolBusiness(int itemId, SerachParam sp);
	
	Map<String, Object> getExportOffer(int itemId, SerachParam sp);

	Map<String, Object> closeOffer(int poolId, int poolType, int counterNum);
	
	Map<String, Object> addItemPrice(ItemPrice itemPrice);

	Map<String, Object> addCounter(PoolPrice poolPrice, int itemId);
	Map<String, Object> restoreOffer(int poolId, int poolType, int counterNum);

	/** 检查黑名单
	 * @Title: checkBlacklist 
	 * @Description: TODO
	 * @param map
	 * @return 
	 * @return: String
	 */
	String checkBlacklist(Map<String, Object> map,Integer poolType);

	
	/**
	 * 销售、采购生成“撮合订单”的时候检验黑名单关系
	 */
	String checkOrderList(Map<String, Object> map);

	/**
	 * 交易指导价
	 */
	ItemPrice getItemPrice(User user);

	/** 修改报盘价格时，实价、升、贴价的校验
	 * @Title: updatePoolBlur 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2015年12月10日
	 */
	String updatePoolBlur(Map<String, Object> param);


	/** 新增还盘，撮合第二版加入  升贴价
	 * @Title: addCounterNew 
	 * @Description: TODO
	 * @param poolPrice
	 * @param id
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2015年12月11日下午3:20:02
	 */
	String addCounterNew(PoolPrice poolPrice, int id);

	/**
	 * 判断价格是否符合今天的还盘价
	 * @param mapper
	 * @return
	 */
	Map<String, Object> compare(Map<String, Object> mapper);

	String checkOrderListBySell(Map<String, Object> balckParam);

	/** 检查报盘是否已经生成订单，生成订单报盘就下架
	 * @Title: CheckPoolStatus 
	 * @Description: TODO
	 * @param poolId
	 * @param poolType
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2015年12月21日上午9:40:01
	 */
	Integer CheckPoolStatus(int poolId, int poolType);

	/** 每次新增还盘之前，查询前一条的换盘信息
	 * @Title: queryCurrentMaxPoolPrice 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @author zhangnan/张楠
	 * @return: PoolPrice
	 * @createTime 2015年12月31日上午9:22:58
	 */
	PoolPrice queryCurrentMaxPoolPrice(Map<String, Object> map);

	/** 模糊查询报价员(撮合人员)
	 * @Title: queryUserList 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<ChUsersEntity>
	 * @createTime 2016年1月4日下午1:50:15
	 */
	List<ChUsersEntity> queryUserList(Map<String, Object> map);

	/** 撮合交易管理选择单个报盘生成订单入口：“撮合订单”方式；生成订单后，报盘要关闭。2016年1月11日下午4:13:19
	 * @Title: closeSellPool 
	 * @Description: TODO
	 * @param params
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2016年1月11日下午4:31:54
	 */
	void closeSellPool(Map<String, Object> params);

	void closeBuyPool(Map<String, Object> params);

	/**
	 * 1、 需要撮合人员同当前最高还盘用户确认后，撤消该用户（A）最高还盘，并增加C用户还盘（还盘价格与报盘相同）；系统将会判定为成交，由撮合人员生成订单
	 * 2、 需要为当前用户新增一笔与报盘相同价格的还盘；系统将会判定为成交，而后生成订单
	 * @return 
	 */
	Map<String, Object> additionalCounter(Map<String, Object> params);

	ChBuyPoolEntity getChBuyPoolEntityById(Integer chBuyId);

	ChWareHouse getChWareHouse(String wareId);

	ChCustomsEntity getChCustomsEntity(int customsId);

	void updateBuyPoolById(ChBuyPoolEntity buyPool);

	void addPoolPrice(ChPoolPriceEntity poolPrice);
	
}

