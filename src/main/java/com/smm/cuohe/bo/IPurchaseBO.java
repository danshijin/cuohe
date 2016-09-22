package com.smm.cuohe.bo;

import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.Purchase;
import com.smm.cuohe.domain.User;

import java.util.List;
import java.util.Map;


/**
 * 采购商客户管理
 */
public interface IPurchaseBO {
	/**
	 * 查询页面
	 * 
	 * @param map
	 * @return
	 */
	public List<Purchase> getAll(Map<String, String> map);
	public Integer getAllCount(Map<String,String> map);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public int deleteByIds(String ids);

	/**
	 * 根据客户Id得到主要联系人电话
	 * @param ids
	 * @return
	 */
	public List<String> selPhoneBycusId(String ids);
	
	/**
	 * excel导入
	 * 
	 * @param dataInfo
	 * @param loginUser
	 */
	public String importExcel(List<Map<String, String>> dataInfo, User loginUser);

	/**
	 * excel导出
	 * 
	 * @param ids
	 */
	public List<Purchase> getExpotDataByID(String[] array);
	
 
	public List<Items> getItemsInfoByCondition(String condition);
}
