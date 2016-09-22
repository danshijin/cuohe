package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.dealmake.Warehouse;

/**
 * 
 * @author dongmiaonan
 *
 */
public interface IAreasDao {
   public List<Areas> getParentAreas();
   public List<Areas> getChildArea(String parentId);
   public Areas getAreaById(String id);
   
   public Warehouse getWarehouseById(Map<String, Object> param);
   
}
