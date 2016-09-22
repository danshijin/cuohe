package com.smm.cuohe.bo;

import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created by hanfeihu on 2015/7/27.
 *
 */
public interface IContacterBo {

    /**
    *获取所有联系人
    * 
    */
    public List<Contacter> getAll();
    public Integer selectCountByName(String name) ;

    /**
     * 添加联系人
     * @param contacter
     */
    public void addContacter(Contacter contacter);
    
    /**
     * 添加联系人
     * @param contacter
     */
    public Integer addContacterToId(Contacter contacter);
    
    public void  modifyContacter(Contacter contacter);
    /**
     * 根据手机查询
     */
    public List<Contacter> getContacterByMobile(Map map);
    /**
     * 根据条件获取联系人数量
     * @param parameters
     * @return 联系人数量
     */
    public int getContacterCount(Map<String, Object> parameters);

    
    
    /**
     * 根据条件获取联系人集合
     * @param parameters
     * @return 联系人集合
     */
    public List<Map> getContacterList(Map<String, Object> parameters);
    
    
    
    /**
     * 根据ID获取联系人
     * @param parameters
     * @return 联系人
     */
    public Contacter getContacterById(Integer id);

    
    /** 获取企业相关联系人
     * @param id
     * @return
     */
    public List<Contacter> getContactersByCustomerId(Integer customerId);
    

    /**
     * 更新联系人删除状态
     * @param parameters
     * @return 联系人
     */
    public void removeContacter(String[]  ids);
    
    public void updateContacter(Contacter contacter);

    
    /** 更新联系人
     * @param contacter
     * @return
     */
    public String updateContacter(Contacter contacter, User user);
    
    /** 为企业添加联系人
     * @param contacter
     * @return
     */
    public String addContacterForCompany(Contacter contacter, User user);
    
    /** 删除联系人
     * @param id
     * @return
     */
    public String deleteContacterById(int id);
    /**
     * 批量删除联系人
     * @param ids
     * @return
     */
    public Boolean batchDleContacterById(String... ids);
    
    

    
    
    
	/**
	 * 根据企业ID查询是关键人的联系人的数量
	 * @param companyId
	 * @return
	 */
	public Integer getCountByCK(Integer customerId);
    
	public List<Map> getAllCompany(Map map);
	
	
	
	public Integer  getAllCompanyCount(Map map);
	
	public void addContacterBatch(List<Contacter> contacters);
	
	
	public List<Contacter> getContacterBycustomerId(Map<String, Object> map);

}
