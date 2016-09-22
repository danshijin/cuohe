package com.smm.cuohe.dao.counts;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.PubQueryEntity;
import com.smm.cuohe.domain.counts.TrailEntity;

/**
 * 线索转换
 * @author tantaigen
 *
 */

public interface Coutsdao {
	public List<PubQueryEntity> querymodel(String name);
	
	/**
	 * 根据ID查询线索
	 * @param id
	 * @return
	 */
	public TrailEntity   querytrail(Integer id);
	
	/**
	 * 查询分类表
	 * @param pubQueryEntity
	 * @return
	 */
	public  PubQueryEntity   queryCategary(PubQueryEntity pubQueryEntity);	
	/**
	 * 根据企业ID查询联系人
	 * @param id
	 * @return
	 */
	public  List<Contacter> contacterCompanyID(Integer id);
	/**
	 * 根据企业ID查询关键联系人
	 * @param id
	 * @return
	 */
	public Integer countKeyPerson(Integer companyId);
	/**
	 * 转线索 
	 * @param map 线索ID  转换人
	 */
	
	public void deltrail(Map<String, Integer> map);
	/**
	 * 修改企业信息
	 * 
	 * @param company
	 */
	public  void updatecompany(Company company);
	
	/**
	 * 根据企业ID和品目查询客户
	 * 
	 * @param map itemID ,companyId
	 * @return
	 */
	public Integer queryzhcustoms(Map<String, Object> map);
}
