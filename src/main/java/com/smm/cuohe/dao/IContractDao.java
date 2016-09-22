package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.AttrValues;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.Contract;
import com.smm.cuohe.domain.ItemAttr;

public interface IContractDao {

	public Contract getContractByOrderId(Integer orderId);
	
	public Integer addContractWithHtml(@Param(value="orderId")Integer orderId, @Param(value="contract")String contract, @Param(value="contractMallUrl")String contractMallUrl);
	
	public List<ItemAttr> selectItemAttr(int itemId);
	
	public List<Contacter> getBuyContactersByOrderId(int orderId);
	
	public Integer getConfirmStatus(int orderId);
	
	public Map<String, Object> getContractContentAndOrderCodeByOrderId(int orderId);
	
	public String getContractMallUrl(int orderId);
	
	public Map<String, String> getAccountByOrderId(int orderId);
	
}
