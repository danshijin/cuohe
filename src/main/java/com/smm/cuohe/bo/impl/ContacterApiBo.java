package com.smm.cuohe.bo.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.IContacterApiBo;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.JSONUtil;
@Service
public class ContacterApiBo implements IContacterApiBo {
	
	private Logger logger=Logger.getLogger(ContacterApiBo.class);
	
	@Resource
    private RestTemplate restTemplate;
    
    @Value("#{ch['updateCompanyInfo.URL']}")
   	private String updateCompanyUrl;
	@Override
	public JSONObject updateContacterInfo(Company c,Contacter contacter,User user) {
		// TODO Auto-generated method stub
		MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId",user.getId());
		map.put("company",c.getName());
		map.put("level","0");
		map.put("mode",c.getEntTypes());
		map.put("buy",c.getBuyProducts());
		map.put("sell",c.getSalesProducts());
		map.put("business",user.getItemId());
		map.put("areaId",c.getAreaId());
		map.put("address",c.getAddress());
		map.put("size",c.getCategoryEmployee());
		map.put("homepage",c.getCompanySite());
		map.put("regyear",c.getRegisterDate());
		map.put("telephone",contacter.getMobilePhone());
		map.put("truename",contacter.getName());
		map.put("gender",contacter.getSex());
		map.put("career",contacter.getPosition());
		map.put("mobile",contacter.getMobilePhone());
		map.put("qq",contacter.getQq());
		map.put("bank",c.getBank());
		map.put("account",c.getAccount());
		map.put("email",contacter.getEmail());
		map.put("admin","1");
		String jsonCompanyMap=JSONUtil.doConvertObject2Json(map);
		param.add("jsonCompanyMap",jsonCompanyMap);
		
		logger.info("开始调用：更新企业信息接口");
		
		String jsonStr=restTemplate.postForObject(updateCompanyUrl,param,String.class);
		JSONObject job=JSONObject.fromObject(jsonStr);
		
		logger.info("更新企业信息返回信息："+job);
		
		return job;
	}

}
