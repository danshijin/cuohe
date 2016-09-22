package com.smm.cuohe.dao.customermanage;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.TrailContract;
import com.smm.cuohe.domain.TrailPoolPOJO;

public interface ITrailPoolPojoDAO {
	
	/**
	 * 获取线索
	 * @param map
	 * @return
	 */
	public List<TrailPoolPOJO> getTrailPoolList(Map<String, Object> map);
	/**
	 * 添加线索
	 * @param trailpoolAdd
	 */
	public void addTrailPool(TrailPoolPOJO trailpoolAdd);
	/**
	 * 添加线索联系人
	 * @param contract
	 */
	public   void addTrailContacer(TrailContract contract);
	
	
	/**
	 * 
	 * 查询线索联系人
	 * @param value
	 * @return
	 */
	
	public  List<TrailContract> gettrailcontractBytraiId(Integer trailId);
	
	/**
	 * 修改线索
	 * @param pojo
	 */
	public  void updateTrail(TrailPoolPOJO pojo);
	
	
	/**
	 * 修改线索联系人
	 * @param contract
	 */
	public void updateTrailCon(TrailContract contract);
	
	
	
	/**
	 * 逻辑删除线索
	 * @param value
	 * @return
	 */
	public Integer updateList(String value);
	

	 
	public List<TrailPoolPOJO> getTrailpoolById(@Param(value="trailIds") String trailIds);
	
	/**
	 * 根据ID查询线索
	 * @param id
	 * @return
	 */
	public TrailPoolPOJO getTrailPoolById(String id);

	public List<String> getAllProductsByItemId(int itemId);
	
}
