package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.CustomerTag;
import com.smm.cuohe.domain.RemindCustomerEntity;



public interface ICustomerDao {

	Integer saveCustomer(Customer customer);
	
	Customer getCustomer(Customer customer);
	
	List<Customer> getCustomerByAccountAndItemId(Customer cust);

	Integer queryIdByNameAndItem(String companyName, Integer itemId);
	
	Integer queryIdByAccountAndItem(String account, int itemId);

	Customer getOneById(int id);
	
	public String queryBrandFromCommodityAttr(int itemId);
	
	public int restoreCustomer(@Param(value="userId") int userId, @Param(value="customerId") int customerId);
	public int updateremindDate( RemindCustomerEntity remindCustomerEntity);//修改下次采购时间
	/**
	 * 查询推荐采购商
	 * @param remindCustomerEntity
	 * @return
	 */
	public  List<RemindCustomerEntity>  queryRemindCustomer(RemindCustomerEntity remindCustomerEntity);

	/** 用户标签
	 * @Title: queryCustomerTag 
	 * @Description: TODO
	 * @return
	 * @author zhangnan/张楠
	 * @param param 
	 * @return: List<Customer>
	 * @createTime 2015年12月15日上午9:42:50
	 */
	List<Customer> queryCustomerTag(Map<String, Object> param);

}
