package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.MallPoolInfo;
import com.smm.cuohe.domain.PubQueryEntity;
import com.smm.cuohe.domain.counts.TrailEntity;

public interface PubQueryBO {

	List<PubQueryEntity> queryModel(String name);

	TrailEntity querytrail(Integer id);

	PubQueryEntity queryCategary(PubQueryEntity pubQueryEntity);

	List<Contacter> contacterCompanyID(Integer id);

	Integer countKeyPerson(Integer companyId);

	void deltrail(Map<String, Integer> map);

	void updatecompany(Company company);

	Integer queryzhcustoms(Map<String, Object> map);

}
