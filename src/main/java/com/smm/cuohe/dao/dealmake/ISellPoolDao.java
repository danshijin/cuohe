package com.smm.cuohe.dao.dealmake;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.AttrValues;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Product;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.SellPoolFull;
import com.smm.cuohe.domain.dealmake.Warehouse;

public interface ISellPoolDao {

	void addSellPool(SellPool sellPool) throws RuntimeException;

	List<SellPool> queryAllSellPool();

	int querySellPoolCount(Map<String, Object> parameters);

	List<SellPool> querySellPoolList(Map<String, Object> parameters);

	/**
	 * 获取企业的报价记录
	 * 
	 * @param id
	 * @return
	 */
	List<SellPool> getOfferRecordByCompanyId(
			@Param(value = "companyId") int companyId,
			@Param(value = "start") int start, @Param(value = "len") int len);

	int getOfferRecordCountByCompanyId(int companyId);

	List<SellPool> querySellOrderList(Map<String, Object> parameters);

	void delAll(int id);

	SellPool getSellPoolById(Map<String, Object> parameters);

	public List<Warehouse> getWareInfoByCondition(String condition);

	public List<com.smm.cuohe.domain.AttrValues> getAttrValByCondition(
			String condition);

	public List<SellPool> getListSellPoolById(String[] array);

	public List<ItemAttr> getItemAttrByCondition(String condition);
	
	public void insertItemAttrInfo(ItemAttr itemAttr);
	
	public Integer updateItemAttrInfo(ItemAttr itemAttr);
	
	public void insertAttrValueInfo(AttrValues attrValues);
	
	public Integer updateAttrValueInfo(AttrValues attrValues);
	
	public void insertWareInfo(Warehouse wareHouse);
	
	public Integer updateWareInfo(Warehouse wareHouse);
	
	public List<SellPoolFull> getSellPoolByCondition(String condition);
	
	public void insertSellPoolInfo(SellPoolFull sellPoolFull);

	public Integer updateSellPoolInfo(SellPoolFull sellPoolFull);

	List<SellPool> getListSellForNoRepeat(Map<String, Object> paramMap);

	Warehouse queryWarehouseById(Map<String, Object> param);

	int updateSellPool(SellPool sellPool);

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
	 * 查询品目下产品名称
	 * @param 查询条件
	 * @return
	 */
	public List<Product> getProductByCondition(String condition);
	
	/**
	 * 根据卖盘ID获取卖盘数据
	 * @param poolId
	 * @return
	 */
	SellPool getSellPoolByPoolId(Integer poolId);
	
	/**
	 * 更新数据
	 * @param sellPool
	 */
	void updateSellPoolPrice(SellPool sellPool);
	
	/**
	 * 发布卖盘
	 */
	public void fabuSellpool(SellPool sellPool);

	Integer getSellPool(Map<String, Object> map);

	Integer updateSellPoolCreateSource(SellPool sellPool);

	SellPool getSellByMallId(Integer mallId);

	/**
	 * 卖盘新增“编号”
	 */
	int updateSellPoolSortNum(Map<String, Object> param);

	/**
	 * 根据商城段提供的"挂盘编号"修改报盘
	 */
	void updateSellPoolByMallSaleId(Map<String, Object> paraMap);

	int updateSellPool_moca(SellPool sellPool);

	void updateSellIsCloseAndIsConfirm(Integer id);
}
