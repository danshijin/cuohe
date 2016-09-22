package com.smm.cuohe.dao;

import com.smm.cuohe.domain.Contacter;

import java.util.List;
import java.util.Map;

public interface IContacterDAO {
	
	/** 通过客户id获取相关联系人
	 * @param id
	 * @return
	 */
	public List<Contacter> getContactersByCustomerId(Integer customerId);
	
	/** 获取企业相关的首要关键联系人
	 * @param id
	 * @return
	 */
	public Contacter getPrimaryPurchaseContacter(Integer id);
	
	/** 获取所有联系人信息
	 * @return
	 */
	public List<Contacter> getAll();
	
	/**
	 * 根据ID获取联系人
	 * @param id
	 */
	public Contacter getContacterById(Integer id);
	
	
	/**
	 * 添加联系人
	 * @param contacter
	 */
    public void addContacter(Contacter contacter);
    
    /**
     * 添加联系人 insert by dongmiaonan
     */
    public Integer addContacterToId(Contacter contacter);
   /**
    * 
    */
    public void  modifyContacter(Contacter contacter);
    /**
     * 根据条件获取联系人数量
     * @param parameters
     * @return
     */
    public int getContacterCount(Map<String, Object> parameters);

    
    
    /**
     * 根据条件获取联系人集合
     * @param parameters
     * @return
     */
    public List<Map> getContacterList(Map<String, Object> parameters);
    
    /**更新联系人
     * @param contacter
     */
    public void updateContacter(Contacter contacter);
    
    
	/** 添加联系人
	 * @param contacter
	 */
	public void addContacterForCompany(Contacter contacter);
	
	/** 根据id删除联系人信息
	 * @param id
	 */
	public void deleteContacterById(int id);
	/**
	 * 根据手机号查询联系人
	 */
	public List<Contacter> getContacterByMobile(Map map);
	
	/**
	 * 批量移除
	 * @param idarr
	 */
	public void removeContacter(String  ...idarr); 
	
	
	/**
	 * 查询企业信息分页
	 * @param map
	 * @return
	 */
	public List<Map> getAllCompany(Map map);



	/**
	 * 获取企业数量
	 * @param map
	 * @return
	 */
	public Integer getAllCompanyCount(Map map) ;
	
	public Integer selectCountByName(String name) ;

	public Integer addContacterBatch(List<Contacter> contacters);
	
	
	/**
	 * 根据企业ID查询是关键人的联系人的数量
	 * @param companyId
	 * @return
	 */
	public Integer getCountByCK(Integer customerId);
	
	/**
	 * 根据客户ID获取联系人信息
	 * @param customerId
	 * @return
	 */
	public List<Contacter> getContacterBycustomerId(Map<String, Object> map);
}
