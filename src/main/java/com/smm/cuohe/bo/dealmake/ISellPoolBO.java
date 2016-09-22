package com.smm.cuohe.bo.dealmake;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.CompanyView;
import com.smm.cuohe.domain.SellPublish;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChCommodityAttrEntity;
import com.smm.cuohe.domain.base.ChCommodityEntity;
import com.smm.cuohe.domain.base.ChWareHouse;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.Warehouse;

public interface ISellPoolBO {

	void addSellPool(SellPool sellPool, User user)throws RuntimeException;

	List<SellPool> queryAllSellPool()throws RuntimeException;

	int querySellPoolCount(Map<String, Object> parameters)throws RuntimeException;

	List<SellPool> querySellPoolList(Map<String, Object> parameters)throws RuntimeException;
	
	Map<String, Object> getPagingOfferRecord(int customerId, User user, int start, int len); 

	List<SellPool> querySellOrderList(Map<String, Object> parameters)throws RuntimeException;
	
	void delpool(String id);

	void delAll(String ids)throws RuntimeException;

	List<SellPool> querySellOrderList(Map<String, Object> parameters,
			String type, String parameter1, String parameter2,
			String parameter3);

	Map<String, Object> getPriceProduct(User user);

	SellPool getSellPoolById(Integer sellPoolId);

	void addSellPublish(SellPublish sellPublish, User user);
	
	public String sellPoolImport(List<Map<String, String>> dataInfo,User user);
	
	public Map<String,Object> getListSellPoolById(Map<String, Object> parameters,String type,String parameter1, String parameter2, String parameter3);

	public String sellPoolPublish(SellPublish sellPublish,User user);

	List<SellPool> getListSellForNoRepeat(SellPool sellPool, User user);

	Warehouse queryWarehouseById(Integer wareId);

	void updateSellPool(SellPool sellPool, User user);
	
	/**
	 * 根据商品编号  价格 数量 查询挂盘id是否存在
	 * @return
	 */
	public Map selMallSaleIdBy(SellPool sellPool);
	/**
	 * 根据ID查询企业名称
	 * @param id
	 * @return
	 */
	String  getcompanyName(Integer id);
	
	/**
	 * 根据ID查询企业会员账号
	 * @param id
	 * @return
	 */
	String  getcompanyAccount(Integer id);
	/**
	 * 检查卖盘吃是否存在重复记录 根据  卖家 ，商品 库存  价格
	 * @param sellPool
	 * @return
	 */
	Integer  countSellpool(SellPool sellPool);	
	
	/**
	 * 根据卖盘ID获取卖盘数据
	 * @param poolId
	 * @return
	 */
	SellPool getSellPoolByPoolId (Integer poolId);
	
	/**
	 * 更新数据
	 * @param sellPool
	 */
	void updateSellPoolPrice(SellPool sellPool);
	
	/**
	 * 发布卖盘
	 */
	public void fabuSellpool(SellPool sellPool);
	
	/**
	 * 检查会员支付账号是否存在
	 * @param account
	 * @return
	 */
	public Integer checkUserPay(String account);
	
	/**
	 * 商城报盘下架操作
	 * @param mallId
	 * @return
	 */
	public boolean dealdemall(Integer mallId);

	Integer updateSellPoolCreateSource(SellPool sellPool);

	SellPool getSellByMallId(Integer mallId);

	/** 给每条卖盘新增一个“编号”字段，便于商城排序
	 * @Title: updateSellPoolSortNum 
	 * @Description: TODO
	 * @param sortNumArray
	 * @param poolIdArray
	 * @param request
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2016年1月6日下午4:16:44
	 */
	Map<String,Object> updateSellPoolSortNum(String[] sortNumArray,String[] poolIdArray,HttpServletRequest request);

	/** 商城端修改卖盘编号
	 * @Title: updatePoolsortNum 
	 * @Description: TODO
	 * @param paraMap
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2016年1月8日上午10:38:52
	 */
	void updatePoolsortNum(Map<String, Object> paraMap);

	/** id查询仓库
	 * @Title: initWareHouse 
	 * @Description: TODO
	 * @param areaId
	 * @return
	 * @author zhangnan/张楠
	 * @return: ChWareHouse
	 * @createTime 2016年1月22日上午9:36:45
	 */
	ChWareHouse initWareHouse(Integer wareId);

	/** 本报盘选定的产品属性 
	 * @Title: queryCommodityAttrList 
	 * @Description: TODO
	 * @param params
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<ChCommodityAttrEntity>
	 * @createTime 2016年1月22日上午10:16:44
	 */
	List<ChCommodityAttrEntity> queryCommodityAttrList(
			Map<String, Object> params);

	/** 修改同步到商城
	 * @Title: updateSellPoolMall 
	 * @Description: TODO
	 * @param sellPool
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2016年1月25日上午9:51:00
	 */
	Map<String,Object> updateSellPoolMall(SellPool sellPool);

	ChCommodityEntity queryChCommodityEntityById(Integer commodityId);

	void updateSellPool_moca(SellPool sell, User user);
}
