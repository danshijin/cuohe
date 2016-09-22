package com.smm.cuohe.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.base.ChSubOrdersEntity;

public interface IOrdersDAO {
	public List<SubOrder> getSubOrdersByUserId(Integer id);
	
	public List<SubOrder> getSubOrdersByOrderId(Integer id);
	
	public List<Map<String, Object>> getOrderDetailByOrderId(Integer orderId);

	public int cancelOrderByOrderId(Integer orderId);

	public Order getOrderById(Map<String, Object> param);
	
	public List<Order>   getOrderList(Map map);
	
	public void orderEditor(Order order);
	
	public Integer getOrderCount(Map map);
	
	public void updateOrderStatus(Map map);
	
	public Integer  updateSubOrders(SubOrder sub);
	
	
	public int updateContractFilePath(@Param(value="orderId")int orderId, @Param(value="filePath")String filePath);
	
	public Map  getKeyContacterByCompanyId(Integer id);
	public List<Map<String,Object>> getSubOrderAllById(Integer id);

	public int getOrderCountBySellId(Map<String, Object> param);

	public List<Order> queryOrderByBuyId(Map<String, Object> param);

	public List<SubOrder> querySubOrderByOrderId(Map<String, Object> param);

	public int countOrdersBySellId(Map<String, Object> parameters);

	public List<SubOrder> querySubOrderBycompanyId(
			Map<String, Object> parameters);
	
	
	public List<Order> getOrderRecord(Map<String, Object> parasMap);
	
	public List<SubOrder>  ttGetSubOrders(Integer id);
	
	public List<Map<String, Object>> selectItemAttr(int itemId);
	
	public int getCountOfOrderRecord(int companyId);
	
	public List<Order> getOrderListBySellPoolId(int sellPoolId);
	
	public Integer ifExistContract(int orderId);
	
	public int addContractByFile(@Param(value="orderId")int orderId, @Param(value="filePath")String filePath);
	
	public Integer updateLastTransTimeByOrderId(int orderId);
	
	public String getOrderCodeByOrderId(int orderId);
	
	public Integer getOrdersByEmpToday(@Param(value="empId")int empId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);

	public List<ChSubOrdersEntity> getOrderAmountByEmpToday(@Param(value="empId")int empId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);
}
