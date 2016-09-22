package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;

public interface IOrdersBO {
	public List<SubOrder> getSubOrdersByUserId(Integer id);

	public List<SubOrder> getSubOrdersByOrderId(Integer orderId);

	public List<Map<String, Object>> getOrderDetailByOrderId(Integer orderId);

	public int cancelOrderByOrderId(Integer orderId);

	public Order getOrderById(Integer valueOf);
	
	
	public List<Order>  getOrderList(Map map);
	
	public Integer getOrderCount(Map map);
	
	public void orderEditor(Order order);
	public void updateOrderStatus(Map map);
	
	public void  updateSubOrders(SubOrder sub);
	
	
	public List<Map<String,Object>> getSubOrderAllById(Integer id);
	
	
	public Map  getKeyContacterByCompanyId(Integer id);

	public int getOrderCountBySellId(Map<String, Object> param);

	public List<Order> queryOrderByBuyId(Integer sellPoolId);

	public List<SubOrder> querySubOrderByOrderId(Integer orderId);

	public int countOrdersBySellId(Map<String, Object> parameters);

	public List<SubOrder> querySubOrderBycompanyId(Map<String, Object> parameters);

	public Map<String, Object> getOrderRecord(Map<String, Object> parasMap);

	int updateContractFilePath(int orderId, String filePath);
	
	public List<Order> getOrderListBySellPoolId(int sellPoolId);
	
	public Integer ifExistContract(int orderId);
	
	public int addContractByFile(int orderId, String filePath);
	
	public Integer updateLastTransTimeByOrderId(int orderId);
}
