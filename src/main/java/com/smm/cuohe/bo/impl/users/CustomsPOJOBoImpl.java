package com.smm.cuohe.bo.impl.users;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.users.ICustomsPojoBO;
import com.smm.cuohe.dao.users.CustomsPOJODAO;
import com.smm.cuohe.domain.CustomsPOJO;

@Service
public class CustomsPOJOBoImpl implements ICustomsPojoBO {

	@Resource
	private CustomsPOJODAO pDao;

	@Override
	public List<CustomsPOJO> getCustomsList(String itemsID) {
		List<CustomsPOJO> clist = pDao.getCustomsList(itemsID);
	
		for (CustomsPOJO c : clist) {
			if (StringUtils.isNotBlank(c.getEntTypes())) {
				List<Map<String, Object>> listMap = pDao.getCategList(c.getEntTypes());
				String enType="";
				for(int i=0;i<listMap.size();i++){
					enType=enType+listMap.get(i).get("name")+",";
				}
				
				c.setEntTypes(enType.substring(0, enType.length()-1));
			} else {
				c.setEntTypes("");
			}
		}
		return clist;
	}
}
