package com.smm.cuohe.dao;

import com.smm.cuohe.domain.Category;

import java.util.List;

/**
 * @author zhaoyutao
 *
 */
public interface ICategoryDAO {
	/**
	 * @return 全部数据
	 */
	public List<Category> getAll();
	
	public List<Category> getByModId(int modId);


	Category getOneById(int id);
}
