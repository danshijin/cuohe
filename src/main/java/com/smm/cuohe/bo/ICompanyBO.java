package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.CompanyView;

/**
 * @author zhaoyutao
 *
 */
public interface ICompanyBO {

	/** 获取企业信息展示所需的全部信息
	 * @param 企业表的id
	 * @return
	 */
	public abstract CompanyView getCompanyViewByCustomId(Integer customId);
	
	/** 更新企业表的信息
	 * @param info 
	 * @return
	 */
	public abstract String updateCompanyView(CompanyView info, HttpServletRequest request);
	
	
	/** 删除企业信息
	 * @param id
	 * @return
	 */
	public abstract String deleteCompany(Integer id, int userId);

	  
	    /**    根据条件查询公司
	     * @discription 
	     * @author Nancy/张楠       
	     * @created 2015年8月7日 上午9:13:53     
	     * @return     
	     */
	public abstract List<Company> getCompanyByParam(String companyName);

	/**
	 * insert by dongmiaonan
	 * @param 添加企业 返回主键
	 * @return
	 */
	public abstract Integer addCompanyToId(Company company);
	 
	public abstract void modifyCompany(Company company);

	/**
	 * insert by dongmiaonan
	 * @param 根据公司名查询
	 * @return
	 */
	public abstract List<Company> getCompanyByName(Map<String,Object> map);
	
	//
	public abstract Company getCompanyByCompanyName(String name);
	
	 
	
	Company  getCompanyById(Integer id);
	
	/*
	 * 根据企业查询Id
	 * 
	 */
	public CompanyView getCompanyViewById(Integer companyId);

	public List<Company> queryCompanyByNm(Map<String, Object> map) ;
	
	public List<Company> getCompanyByNameAndItem(String companyName, int itemId) ; 
	
	public String getCompanyNameByCustomerId(int companyId);
	
	
	public int getCustomerIdByItemIdAndCompanyId(Map<String, Integer> map);

	public List<Areas> getParentArea();
	
	public int countCompanyByName(String companyName);
	
	
	
}