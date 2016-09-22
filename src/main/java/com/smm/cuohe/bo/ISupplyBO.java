package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Purchase;
import com.smm.cuohe.domain.Supply;
import com.smm.cuohe.domain.User;

public interface ISupplyBO {
	/**
	 * 查询页面
	 * 
	 * @param map
	 * @return
	 */
	public List<Supply> getAll(Map<String, String> map);
	public Integer getAllCount(Map<String,String> map);
	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public int deleteByIds(String ids);

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
	public List<Supply> getSupplyExportData(String[] array);
}
