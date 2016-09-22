package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Supply;

public interface ISupplyDao {
	public List<Supply> getAll(Map<String,String> map);
	public Integer getAllCount(Map<String,String> map);
	
	public int deleteByIds(String ids);
	
	public List<Supply> getSupplyExportData(String[] array);
}
