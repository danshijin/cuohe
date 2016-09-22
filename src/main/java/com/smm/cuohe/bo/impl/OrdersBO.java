package com.smm.cuohe.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.IOrdersBO;
import com.smm.cuohe.dao.IOrdersDAO;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;
@Service
public class OrdersBO implements IOrdersBO{
	
	private Logger logger = Logger.getLogger(OrdersBO.class);
	@Resource
	private IOrdersDAO iOrdersDAO;

	

	public List<SubOrder> getSubOrdersByUserId(Integer id) {
		return iOrdersDAO.getSubOrdersByUserId(id);

	}


	public List<SubOrder> getSubOrdersByOrderId(Integer orderId) {
		return iOrdersDAO.getSubOrdersByOrderId(orderId);
	}

	@Override
	public List<Map<String, Object>> getOrderDetailByOrderId(Integer orderId) {
		return iOrdersDAO.getOrderDetailByOrderId(orderId);
	}

	@Override
	public int cancelOrderByOrderId(Integer orderId) {
		
		Map<String, Object> map = mallCancelOrder(iOrdersDAO.getOrderCodeByOrderId(orderId));
		int errno = (int) map.get("errno"); // 0 正常 1 无法删除 2 订单不存在 
		
		if(errno > 1){
			return -1;
		}
		
		return iOrdersDAO.cancelOrderByOrderId(orderId);
	}
	
	@Value("#{ch['mallCancelOrder.URL']}")
	private String mallCancelOrderUrl;
	
	private Map<String, Object> mallCancelOrder(String orderno){
		
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		
		param.add("orderno", orderno);
		
		String jsonString = "{}";
		try{
			logger.info("开始调用：商城撤销订单       参数："+param.toString());
			jsonString = new RestTemplate().postForObject(mallCancelOrderUrl, param, String.class);
			
			if (jsonString != null) {
				logger.info("商城撤销订单返回数据"+JSONUtil.doConvertJson2Map(jsonString).toString());
			}
			
		} catch(Exception ex){
			logger.error("调用商城撤销订单接口出错");
			System.out.println(ex.getMessage());
			
		}
		return JSONUtil.doConvertJson2Map(jsonString);
	}
	
	
	@Override
	public Order getOrderById(Integer orderId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("id", orderId);
		return iOrdersDAO.getOrderById(param);
	}

	@Override
	public List<Order>  getOrderList(Map map) {
		// TODO Auto-generated method stub
		return iOrdersDAO.getOrderList(map);
	}

	@Override
	public void orderEditor(Order order) {
		// TODO Auto-generated method stub
		
		iOrdersDAO.orderEditor(order);
	}

	@Override
	public int updateContractFilePath(int orderId, String filePath){
		
		return iOrdersDAO.updateContractFilePath(orderId, filePath);
		
		
	}
	
	@Override
	public Integer getOrderCount(Map map) {
		// TODO Auto-generated method stub
		return iOrdersDAO.getOrderCount(map);
	}

	@Override
	public void updateOrderStatus(Map map) {
		// TODO Auto-generated method stub
		iOrdersDAO.updateOrderStatus(map);
	}

	@Override
	public void updateSubOrders(SubOrder sub) {
		// TODO Auto-generated method stub
		
		iOrdersDAO.updateSubOrders(sub);
	}

	@Override
	public List<Map<String,Object>> getSubOrderAllById(Integer id) {
		// TODO Auto-generated method stub
		return iOrdersDAO.getSubOrderAllById(id);
	}

	@Override
	public Map getKeyContacterByCompanyId(Integer id) {
		// TODO Auto-generated method stub
		return iOrdersDAO.getKeyContacterByCompanyId(id);
	}


	@Override
	public int getOrderCountBySellId(Map<String, Object> param) {
		return iOrdersDAO.getOrderCountBySellId(param);
	}


	@Override
	public List<Order> queryOrderByBuyId(Integer sellId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("sellId", sellId);
		return this.iOrdersDAO.queryOrderByBuyId(param);
	}


	@Override
	public List<SubOrder> querySubOrderByOrderId(Integer companyId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		return this.iOrdersDAO.querySubOrderByOrderId(param);
	}


	@Override
	public int countOrdersBySellId(Map<String, Object> parameters) {
		return this.iOrdersDAO.countOrdersBySellId(parameters);
	}


	@Override
	public List<SubOrder> querySubOrderBycompanyId(Map<String, Object> parameters) {
		return this.iOrdersDAO.querySubOrderBycompanyId(parameters);
	}
	
	@Override
	public Map<String, Object> getOrderRecord(Map<String, Object> parasMap){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Order> list = iOrdersDAO.getOrderRecord(parasMap);
		int total = iOrdersDAO.getCountOfOrderRecord((int)parasMap.get("customerId"));
		if(!list.isEmpty()){
			List<Map<String, Object>> attrView = iOrdersDAO.selectItemAttr(list.get(0).getItemId());
			
			map.put("msg", "success");
			
			map.put("total", total);
			
			map.put("list", list);
			
			map.put("attrView", attrView);
		} else {
			
			map.put("msg", "success");
			
			map.put("total", total);
			
		}
		
		return map;
	}


	@Override
	public List<Order> getOrderListBySellPoolId(int sellPoolId) {
		return iOrdersDAO.getOrderListBySellPoolId(sellPoolId);
	}


	@Override
	public Integer ifExistContract(int orderId) {
		return iOrdersDAO.ifExistContract(orderId);
	}
	
	@Override
	public int addContractByFile(int orderId, String filePath){
		
		return iOrdersDAO.addContractByFile(orderId, filePath);
		
	}
	@Override
	public Integer updateLastTransTimeByOrderId(int orderId){
		return iOrdersDAO.updateLastTransTimeByOrderId(orderId);
	}
}
