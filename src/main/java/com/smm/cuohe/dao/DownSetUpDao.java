package com.smm.cuohe.dao;

import java.util.List;

import com.smm.cuohe.domain.DownSetUpEntity;
/**
 * 下游行业设置
 * @author tantaigen
 *
 */
public interface DownSetUpDao {
	
	
	
	public  List<DownSetUpEntity> queryItemsProd(Integer itemsId);
	public void addTtemsProd(DownSetUpEntity setup);
	public  void delItemsProd(Integer id );
	public  DownSetUpEntity queryItemsProdname(DownSetUpEntity prodname);

}
