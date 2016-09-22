package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.CompanyPOJO;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.RemindCustomerEntity;
import com.smm.cuohe.domain.User;

public interface ICustomerBO {

	/**
	 * 新增客户
	 * 
	 * @param customer
	 * @return 客户编号
	 * @throws Exception
	 */
	Integer saveCustomer(Customer customer) throws Exception;

	String saveCompanyPOJO(CompanyPOJO companyPOJO, User user) throws Exception;

	/**
	 * 通过id获取一个客户对象
	 * 
	 * @param id
	 * @return
	 */
	Customer getOneById(int id);

	List<String> serachBrand(String name, int itemId);

	Integer saveCustomerForDealMaker(Customer customer) throws Exception;

	/**
	 * 将已删除（误操作删除）的客户还原回客户池
	 * 
	 * @param customerId
	 * @return
	 */
	public Map<String, Object> restoreCustomer(int userId, int customerId);

	/**
	 * 更新用户下次采购时间
	 * 
	 * @param customerId
	 * @return
	 */
	public Integer updateremindDate(int customerId);

	/**
	 * 查询推荐采购商
	 * 
	 * @param remindCustomerEntity
	 * @return
	 */
	public  List<RemindCustomerEntity>  queryRemindCustomer(RemindCustomerEntity remindCustomerEntity);
	
	/**
	 * 推荐用户跳过
	 * @param remindCustomerEntity
	 */
	public  void tiaoguo(RemindCustomerEntity remindCustomerEntity);
	
	/**
	 * 检查手机号码在商城注册用户次数
	 * @param 联系人手机号码
	 * @throws Exception 
	 */
	public JSONObject checkCustomer(String phone) throws Exception;
	
	public Boolean checkCusByComNameAndItem(Customer customer);
	
	/**
	 * 根据account、itemID获取客户信息
	 * @param cust
	 * @return
	 */
	public List<Customer> getCustomerByAccountAndItemId(Customer cust);

	/** 用户标签
	 * @Title: queryCustomerTag 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<CustomerTag>
	 * @createTime 2015年12月15日上午9:35:00
	 */
	List<Customer> queryCustomerTag(Map<String, Object> param);
	
	/**
	 * 根据撮合员ID获取对应聊天人信息
	 * @param employeeId
	 * @return
	 */
	public List<Chats> getChatsByemployeeId(Map<String, Object> map);
	
	/**
	 * 根据洽谈编号获取洽谈对应聊天信息
	 * @param chatID
	 * @return
	 */
	public DiscussChatRecord getdiscussChatRecordByChatId(int chatID);
	
	public User getUserByEmployeeId(int employeeId);
	
	/**
	 * 根据ID修改撮合员在线状态
	 * @param map
	 */
	public void updateById(Map<String, Object> map);
	
}
