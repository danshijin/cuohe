package com.smm.cuohe.bo.customermanage;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.TrailContract;
import com.smm.cuohe.domain.TrailPoolAPI;
import com.smm.cuohe.domain.TrailPoolPOJO;

public interface ITrailPoolPojoBO {
	/**
	 *获得所有 线索池列表 
	 * @return
	 */
	public List<TrailPoolPOJO> getTrailPoolList(String type,String tpoolOne,String tpoolTwo,String tpoolThree,Map map);
	/**
	 * 批量删除
	 * @return
	 */
	public Map<String, Object> updateList(String ids);
	
	/**	  
	 * 添加线索池
	 * insert by dongmiaonan 
	 * @param 
	 * @return 
	 * @throws RuntimeException
	 */
	public void addTrailPoolAdd(TrailPoolPOJO trailpoolAdd);
	
	public Map<String, String> addTrailPool(TrailPoolAPI tpApi);

	/**
	 * 根据前台选中信息获得要导出的数据
	 *@return
	 */
	public List<TrailPoolPOJO> getDataByID(String trailIds);
	
	/**
	 * 根据线索池主键查询
	 * 
	 */
	public TrailPoolPOJO  getTrailPoolById(String id);
	
	
	/**
	 * 查询线索联系人
	 * @param trailId
	 * @return
	 */
	public  List<TrailContract> gettrailcontractBytraiId(Integer trailId);
	
	
	/**
	 * 修改线索
	 * @param pojo
	 */
	public  void updateTrail(TrailPoolPOJO pojo);
	
	

	
	
	
	


}
