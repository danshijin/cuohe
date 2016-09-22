package com.smm.cuohe.dao.dealmake;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.ToOrder;
import com.smm.cuohe.domain.dealmake.Warehouse;

public interface IBuyOrderDao {
	public void insertBuyOrder(Order o);
	public void insertBuySubOrder(SubOrder so);
	
	public List<Warehouse> selWarehouseByArea(@Param(value="companyName") String companyName,@Param(value="areaId") Integer areaId,@Param(value = "itemId") String itemId);
	
	public List<ItemAttr> getMainItemAttr(Integer itemId);
	
	public void insAttrValues(AttrValue a);
	
	public ToOrder selCustomById(Integer cusId);//根据客户Id得到生成订单数据
	
	public String selProNameById(Integer id);//根据产品Id得到产品名称
	
	//更新买盘关闭状态
	public void updateBuyIsClose(Integer id);
	//更新买盘关闭状态和成交状态
	public void updateBuyIsCloseAndIsConfirm(Integer id);
	//更新卖盘关闭状态
	public void updateSellIsClose(Integer id);
	//删除卖盘
	public void updateSellIsStatus(Integer id);
	//报盘id查询报盘编号
	public Integer queryMallSaleIdByPoolId(Integer poolId);
	
	//还盘id得到公司信息
	public Customer queryCompanyInfo(Integer id);
	
	//更新还盘信息
	public int updateSell(SellPool sell);
	/**  根据省份来查询仓库用于搜索下拉框 select2
	 * @Title: selWarehouseByAreaBlur 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<Warehouse>
	 */
	public List<Warehouse> selWarehouseByAreaBlur(Map<String, Object> param);
	
	public Integer getSellPoolIdByChatIdForConversation(int chatId);
	
	public int updateSellPoolToClose(int sellPoolId);
	public void updateBuyPoolCreateSource(BuyPoolEntity buyPool);
}
