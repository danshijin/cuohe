package com.smm.cuohe.bo;

import java.util.List;

import com.smm.cuohe.domain.SubOrder;

/**
 * @author  zhaoyutao
 * @version 2015年9月14日 上午11:10:42
 * 类说明
 */

public interface ISubOrderBO {

	List<SubOrder> getSubOrdersByOrderId(int orderId);

}
