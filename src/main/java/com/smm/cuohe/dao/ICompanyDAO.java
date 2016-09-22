package com.smm.cuohe.dao;

import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.CompanyView;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @author zhaoyutao
 *
 */
public interface ICompanyDAO {
	public Company getCompanyById(Integer id) ;
	/**
	 * @param id 企业id
	 * @return 企业全部信息
	 */
	public CompanyView getCompanyViewByCustomId(Integer id);
	
	/**
	 * @param info companyView 要更新的内容
	 */
	public void updateCompanyView(CompanyView info);
	
	/** 将企业信息状态置为删除
	 * @param id
	 */
	public void deleteCompany(@Param(value="customerId") int customerId, @Param(value="userId") Integer userId);

	  
	    /**     
	     * @discription 
	     * @author Nancy/张楠       
	     * @created 2015年8月7日 上午9:56:57     
	     * @param map 公司名字，模糊查询
	     * @return     
	     */
	public List<Company> getCompanyByParam(Map<String, Object> map);
	
	public List<Company> getCompanyByNameAndItem(@Param(value="companyName")String companyName, @Param(value="itemId")int itemId);
	
	/**
	 * insert by dongmiaonan
	 * @param company 添加企业 返回主键
	 * @return
	 */
	public Integer addCompanyToId(Company company);
	
	public void modifyCompany(Company company);
	
	/*
	 * 根据id查询企业
	 * 
	 */
	public CompanyView getCompanyViewById(Integer companyId);
	/**
	 * insert by dongmiaonan
	 * @param map 是否存在该公司
	 * @return
	 */
	public List<Company> getCompanyByName(Map<String,Object> map);
	
	/**
	 * insert by dongmiaonan
	 * @param id
	 * @return
	 */
	public Company getCompanyById(String id);
	
	Company getCompanyByCompanyName(String companyName);

	
	public String getCompanyNameByCustomerId(int customerId);
	
	public String getCompanyAccountByCustomerId(int customerId);
	
	public Integer getCustomerIdByItemIdAndCompanyId(Map<String, Integer> map);


	public int countCompanyByName(String companyName);
	
}
