package com.smm.cuohe.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.DownSetUpbo;
import com.smm.cuohe.dao.DownSetUpDao;
import com.smm.cuohe.domain.DownSetUpEntity;


@Service

public class DownSetUpboImpl implements DownSetUpbo {
	@Resource DownSetUpDao dao;
	
	@Override
	public List<DownSetUpEntity> queryItemsProd(Integer itemsId) {
		return dao.queryItemsProd(itemsId);
	}

	@Override
	public void addTtemsProd(DownSetUpEntity setup) {
		dao.addTtemsProd(setup);
	}

	@Override
	public void delItemsProd(Integer id) {
		dao.delItemsProd(id);
	}
	public  DownSetUpEntity queryItemsProdname(DownSetUpEntity prodname){
		
		
		return dao.queryItemsProdname(prodname);
	}

}
