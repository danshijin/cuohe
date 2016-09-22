package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.Contract;

public interface IContractBO {

	public Contract getContractByOrderId(Integer orderId);
	
	public List<Contacter> getBuyContactersByOrderId(int orderId);
	
	public String addContractWithHtml(Integer orderId, String orderCode, String contract);
	
	/**
	 * @param orderId
	 * @param sendType
	 * @param request
	 * @return status -1 未确认并提交合同 0 没有采购联系人  1 有一个 2 有多个 3 直接显示合同链接
	 */
	public Map<String, Object> sendContract(Integer orderId, String sendType, HttpServletRequest request);
	
	public String sendContractBySelectedContacters(String customerName, String phoneOrEmail, String sendType, Integer orderId, HttpServletRequest request);

	public Map<String, Object> getContractContentAndOrderCodeByOrderId(int orderId);

	public String getContractMallUrl(int orderId);
}
