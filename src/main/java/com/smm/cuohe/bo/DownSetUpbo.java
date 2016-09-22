package com.smm.cuohe.bo;

import java.util.List;

import com.smm.cuohe.domain.DownSetUpEntity;

public interface DownSetUpbo {
	public  List<DownSetUpEntity> queryItemsProd(Integer itemsId);
	public void addTtemsProd(DownSetUpEntity setup);
	public  void delItemsProd(Integer id );
	public  DownSetUpEntity queryItemsProdname(DownSetUpEntity prodname);


}
