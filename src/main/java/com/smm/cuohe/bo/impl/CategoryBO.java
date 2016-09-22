package com.smm.cuohe.bo.impl;

import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.dao.ICategoryDAO;
import com.smm.cuohe.dao.IModulesDAO;
import com.smm.cuohe.domain.Category;
import com.smm.cuohe.domain.Modules;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyutao
 *
 */
@Service
public class CategoryBO implements ICategoryBO{

	@Resource
	private ICategoryDAO iCategoryDAO;
	
	@Resource
	private IModulesDAO iModulesDAO;
	
	@Override
	public List<Category> getByModId(int modId) {
		return iCategoryDAO.getByModId(modId);
	}
	
	/**
	 * @return 表category中的数据
	 */
	public List<Category> getCategoryList() {
		return iCategoryDAO.getAll();
	}
	
	/**
	 * @return 通过id获取category的map
	 */
	public Map<Integer, Category> getCategoryMap() {
		List<Category> categoryList = getCategoryList();
		
		Map<Integer, Category> categoryMap = new HashMap<Integer, Category>();
		for(Category c : categoryList){
			categoryMap.put(c.getId(), c);
		}
		return categoryMap;
	}
	
	/**
	 * @return 通过modulename获取对应category
	 */
	public Map<String, List<Category>> getCategoryByModuleName() {
		List<Category> categoryList = getCategoryList();
		List<Modules> modulesList = iModulesDAO.getAll();
		Map<Integer, String> modulesMap = new HashMap<Integer, String>();
		Map<String, List<Category>> categoryModuleNameMap = new HashMap<String, List<Category>>();
		for(Modules m : modulesList){
			modulesMap.put(m.getId(), m.getName());
		}
		for(Category c : categoryList){
			String moduleName = modulesMap.get(c.getModId());
			List<Category> modIdList;
			if(categoryModuleNameMap.containsKey(moduleName)){
				modIdList = categoryModuleNameMap.get(moduleName);
			} else {
				modIdList = new ArrayList<Category>();
				categoryModuleNameMap.put(moduleName, modIdList);
			}
			modIdList.add(c);
		}
		
		return categoryModuleNameMap;
	}

	@Override
	public Category getOneById(int id) {

		return this.iCategoryDAO.getOneById(id);
	}

}
