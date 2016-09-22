package com.smm.cuohe.bo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.PubQueryBO;
import com.smm.cuohe.dao.counts.Coutsdao;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.PubQueryEntity;
import com.smm.cuohe.domain.counts.TrailEntity;

@Service
public class PubQueryImpl implements PubQueryBO {

	@Resource Coutsdao Coutsdao;
	
	
	@Override
	public List<PubQueryEntity> queryModel(String name) {
		List<PubQueryEntity> list=Coutsdao.querymodel(name);
		return list;
	}


	@Override
	public TrailEntity querytrail(Integer id) {
	
		return Coutsdao.querytrail(id);
	}


	@Override
	public PubQueryEntity queryCategary(PubQueryEntity pubQueryEntity) {
		return Coutsdao.queryCategary(pubQueryEntity);
	}


	@Override
	public List<Contacter> contacterCompanyID(Integer id) {
		// TODO Auto-generated method stub
		return Coutsdao.contacterCompanyID(id);
	}


	@Override
	public Integer countKeyPerson(Integer companyId) {
		
		return Coutsdao.countKeyPerson(companyId);
	}


	@Override
	public void deltrail(Map<String, Integer> map) {
		Coutsdao.deltrail(map);
		
	}


	@Override
	public void updatecompany(Company company) {
		Coutsdao.updatecompany(company);
		
	}
	
	public Integer queryzhcustoms(Map<String, Object> map){
		return Coutsdao.queryzhcustoms(map);
		
	}
}
