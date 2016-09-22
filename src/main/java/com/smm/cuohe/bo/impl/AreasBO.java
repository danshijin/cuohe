package com.smm.cuohe.bo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.dao.IAreasDao;
import com.smm.cuohe.dao.ICompanyDAO;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.dealmake.Warehouse;
/**
 * 
 * @author dongmiaonan
 *
 */

@Service
public class AreasBO implements IAreasBO {

	@Resource
	IAreasDao iAreasDao;
	
	
	@Override
	public List<Areas> getParentAreas() { 
		return 	iAreasDao.getParentAreas();
	}

	@Override
	public List<Areas> getChildArea(String parentId) { 
		return iAreasDao.getChildArea(parentId);
	}

	@Override
	public Areas getAreaById(String id) { 
		return iAreasDao.getAreaById(id);
	}

	@Override
	public Warehouse getWarehouseById(Map<String, Object> param) {
		return this.iAreasDao.getWarehouseById(param);
	}



}
