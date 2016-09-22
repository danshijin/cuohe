package com.smm.cuohe.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.ISubOrderBO;
import com.smm.cuohe.dao.ISubOrdersDAO;
import com.smm.cuohe.domain.SubOrder;

/**
 * @author  zhaoyutao
 * @version 2015年9月14日 上午11:07:57
 * 类说明
 */

@Service
public class SubOrderBO implements ISubOrderBO {
	
	@Resource
	private ISubOrdersDAO iSubOrdersDao;
	
	@Override
	public List<SubOrder> getSubOrdersByOrderId(int orderId){
		
		return iSubOrdersDao.getSubOrdersByOrderId(orderId);
		
	}
	
}
