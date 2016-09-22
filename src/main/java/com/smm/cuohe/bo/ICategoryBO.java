package com.smm.cuohe.bo;

import com.smm.cuohe.domain.Category;

import java.util.List;
import java.util.Map;

public interface ICategoryBO {
	
	List<Category> getByModId(int modId);
	
	/**
	 * @return 表category中的数据
	 */
	List<Category> getCategoryList();
	
	/**
	 * @return 通过id获取category的map
	 */
	Map<Integer, Category> getCategoryMap();
	
	/**
	 * @return 通过modulename获取对应category
	 */
	Map<String, List<Category>> getCategoryByModuleName();


	/**
	 * 通过id获取一个category 对象
	 * @param id
	 * @return
	 */
	Category getOneById(int id);
}
