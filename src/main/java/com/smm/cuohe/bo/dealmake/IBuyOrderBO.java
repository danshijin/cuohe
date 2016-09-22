package com.smm.cuohe.bo.dealmake;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.ToOrder;
import com.smm.cuohe.domain.dealmake.Warehouse;

public interface IBuyOrderBO {

	// 生成订单
	public void insertBuyOrder(Order o);
	
	// 订单项
	public void insertSubOrder(SubOrder so);
	
	public List<Warehouse> selWarehouseByArea(String companyName,Integer areaId,String itemId);
	
	//得到品目的主要属性
	public List<ItemAttr> getMainItemAttr(Integer itemId);
	
	//添加扩展属性值
	public void insAttrValues(AttrValue a);
	
	public ToOrder selCustomById(Integer cusId);//根据客户Id得到生成订单数据
	
	public String selProNameById(Integer id);//根据产品Id得到产品名称
	
	//更新买盘关闭状态
	public void updateBuyIsClose(Integer id);
	//更新买盘关闭状态和成交状态
	public void updateBuyIsCloseAndIsConfirm(Integer id);
	//更新卖盘关闭状态
	public void updateSellIsClose(Integer id);
	//更新卖盘为删除状态
	public void updateSellIsStatus(Integer id);
	//报盘id查询报盘编号
	public Integer queryMallSaleIdByPoolId(Integer poolId);
	
	//还盘id得到公司信息
	public Customer queryCompanyInfo(Integer id);
	
	//更新报盘信息（同步商城修改）
	public boolean updatePool(SellPool sell, Object mailsaleId);

	/** 根据省份来查询仓库用于搜索下拉框 select2
	 * @Title: selWarehouseByAreaBlur 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<Warehouse>
	 */
	public List<Warehouse> selWarehouseByAreaBlur(Map<String, Object> param);

	Integer getSellPoolIdByChatIdForConversation(Integer chatId);

	int updateSellPoolToClose(int sellPoolId);

	public void updateBuyPoolCreateSource(BuyPoolEntity buyPool);
	
	//如果是撮合交易管理发送至卖盘的 生成订单关闭卖盘
	public void sellPoolClose(int poolid);

	public void updateSellIsCloseAndIsConfirm(Integer id);
}
