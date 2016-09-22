package com.smm.cuohe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.SubOrder;

/**
 * @author zhaoyutao
 *
 */
public interface ISubOrdersDAO {
	
	public List<SubOrder> getSubOrdersByOrderId(@Param(value="orderId") int orderId);
	
}
