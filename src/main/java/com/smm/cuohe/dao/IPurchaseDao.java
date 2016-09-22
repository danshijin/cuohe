package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.ComRecords;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.Contract;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.CustomsFull;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.Purchase;
import com.smm.cuohe.domain.User;

public interface IPurchaseDao {
	public List<Purchase> getAll(Map<String,String> map);
	
	public int deleteByIds(String ids);
	
	public Integer getAllCount(Map<String,String> map);
	
	//根据客户Id得到主要联系人电话
	public List<String> selPhoneBycusId(String ids);
	
	
	//根据条件获得地区信息
	public List<Areas> getAreasInfoByCondition(String condition);
	
	//根据查询条件获得联系人信息
	public List<java.util.Map<String, Object>> getContacterByCondition(String condition);
	
	//根据查询条件获得企业信息
	public List<Company> getCompanyInfoByCondition(String condition);
	
	//根据查询条件获得品目信息
	public List<Items> getItemsInfoByCondition(String condition);
	
	//根据撮合员工姓名获得员工信息
	public List<User> getUserInfoByCondition(String condition);
	
	//根据企业和品目条件获得客户表信息
	public List<Customer> getCustomsInfoByCondition(String condition);
	
	//根据企业和品目条件获得客户表信息
	public List<ComRecords> getComRecordsByCondition(String condition);
	
	//插入企业表数据
	public void insertCompanyInfo(com.smm.cuohe.domain.Company companyInfo);
	
	//更新企业表数据
	public Integer updateCompanyInfo(com.smm.cuohe.domain.Company companyInfo);
	
	//插入企业联系人表数据
	public void insertContacterInfo(com.smm.cuohe.domain.Contacter contacterInfo);
	
	//更新企业联系人表数据
	public Integer updateContacterInfo(com.smm.cuohe.domain.Contacter contacterInfo);
	
	//插入沟通记录数据
	public void insertComRecordInfo(com.smm.cuohe.domain.ComRecords comRecordInfo);
	
	//更新沟通记录数据
	public Integer updateComRecordInfo(com.smm.cuohe.domain.ComRecords comRecordInfo);
	
	//插入客户信息数据
	public void insertCustomInfo(com.smm.cuohe.domain.Customer customInfo);
	
	//更新客户信息数据
	public Integer updateCustomInfo(com.smm.cuohe.domain.Customer customInfo);
	
	//获得根据id获得导出的数据
	public List<Purchase> getExpotDataByID(String[] array);
}
