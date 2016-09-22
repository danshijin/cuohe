package com.smm.cuohe.bo;

import java.util.List;
 
import java.util.Map;

import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.CompanyView;
import com.smm.cuohe.domain.dealmake.Warehouse;

/**
 * 
 * @author dongmiaonan
 *
 */
public interface IAreasBO {
	/** 
	 * @param 获取一级省份 Parent=0
	 * @return
	 */
	public abstract List<Areas> getParentAreas();
	
	/**  
	 * @param 根据父级ID获取子级城市
	 * @return
	 */
	public abstract List<Areas> getChildArea(String parentId);
	
	 /**
	  * 
	  * @param 根据主键查询
	  * @return
	  */
    public abstract Areas getAreaById(String id);

	/** 仓库
	 * @Title: getWarehouseById 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @return: Warehouse
	 * @createTime 2015年12月15日下午2:37:52
	 */
	public abstract Warehouse getWarehouseById(Map<String, Object> param);
    
    
}
