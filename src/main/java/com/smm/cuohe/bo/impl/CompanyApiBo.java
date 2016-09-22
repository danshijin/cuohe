package com.smm.cuohe.bo.impl;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.plaf.synth.SynthStyle;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.ICompanyApiBo;
import com.smm.cuohe.bo.dealmake.impl.SellPoolBOImpl;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.CompanyPOJO;
import com.smm.cuohe.domain.TrailPoolAdd;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;
import com.smm.cuohe.util.URLRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * 
 * @author dongmiaonan
 *
 */
 
@Service
public class CompanyApiBo implements ICompanyApiBo {
	private Logger logger=Logger.getLogger(CompanyApiBo.class);
	
	@Resource
    private RestTemplate restTemplate; 
	@Resource 
	private IAreasBO iAreasBO;
    
    @Value("#{ch['queryByName.URL']}")
	private String queryByName;
    
    @Value("#{ch['getCompanyInfo.URL']}")
   	private String getCompanyInfo;
    
    
    @Value("#{ch['updateCompanyInfo.URL']}")
   	private String updateCompanyUrl;
    
	@Override
	public List<Company> GetCompanySimpleInfo(String companyName ,Integer pno) {		
		List<Company> company=new ArrayList<>(); 
		Map<String, String> map=new HashMap<>();
		if(pno==null|| "".equals(pno)){
			pno=1;
		}
		
	     map.put("page",""+pno);
	    
		try {
			if(StringUtils.isNotBlank(companyName))
				 
			{
				String  searchString= URLDecoder.decode(companyName,"utf8");
				 map.put("name",searchString);
				 
				logger.info("开始调用：根据公司名模糊查询公司接口  参数： "+map.toString());
				 
				String jsonStr = URLRequest.post(queryByName, map);  
			
				if(StringUtils.isNotBlank(jsonStr)){
					JSONObject jsonObject= JSONObject.fromObject(jsonStr);

					logger.info("根据公司名模糊查询公司返回结果："+jsonObject.toString());

					if(jsonObject!=null&&jsonObject.containsKey("code")&&1== Integer.valueOf((String)jsonObject.get("code")) ){
						if(jsonObject.containsKey("resultData")){
						
							
						JSONArray	jsonArray=jsonObject.getJSONArray("resultData");
							JSONObject jsonCompanyObject=jsonArray.getJSONObject(0);

							
							JSONArray jsonarry=	jsonCompanyObject.getJSONArray("companylist");
							 company=JSONArray.toList(jsonarry);
					
						}
					}
				}
		   } 
	} catch (Exception e) {
		e.printStackTrace();
		logger.info("企业简略信息接口调用发生异常");
		
	}  
		return company;
	}

	@Override
	public Map<String, Object> GetCompanyInfo(String userId) {
		Map<String, Object> result =new  HashMap<String,Object>(); 
		try {
			if(StringUtils.isNotBlank(userId)){
				String url = getCompanyInfo.replace("{0}", StringUtil.doEncoder(userId));
				String jsonStr = null;
				try {
					logger.info("开始调用：获取企业详细信息接口");
					
					jsonStr = restTemplate.postForObject(url,null,String.class);
					JSONObject jsonObject = JSONObject.fromObject(jsonStr);
					
					logger.info("企业详细信息"+jsonObject);
					
					if(jsonObject!=null&&jsonObject.containsKey("code")&&"success".equals(jsonObject.get("code").toString())){
						JSONObject company = jsonObject.getJSONObject("resultData");
						if(company!=null){
							if(company.containsKey("areaid")&&StringUtils.isNumeric(company.getString("areaid"))){
								Areas area = iAreasBO.getAreaById(company.getString("areaid"));
								company.element("area", area);
							}
							result.put("companyDetail",company.toString());
						}
					}
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
	} catch (Exception e) {
		logger.info("企业详细信息接口调用发生异常");
		result.put("avg", "检测企业信息失败");
	} 
		 
		return result;
	}

	@Override
	public JSONObject updateContacterInfo(TrailPoolAdd trailPool) {
		// TODO Auto-generated method stub
		MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
		Map<String, Object> map=new HashMap<String, Object>(); 
		map.put("company",trailPool.getCompanyName());
		map.put("level","");//
		map.put("mode",trailPool.getEntTypes());
		map.put("buy","");//
		map.put("sell","");//
		map.put("business",trailPool.getItemId());//
		map.put("areaid",trailPool.getAreaId());
		map.put("address",trailPool.getAddress());
		map.put("size",trailPool.getCategoryEmployee());
		map.put("homepage",trailPool.getCompanySite());
		map.put("regyear",trailPool.getRegisterDate());
		map.put("telephone",trailPool.getCompanyPhone());
		
		map.put("truename",trailPool.getContacterName());
		map.put("gender",trailPool.getSex());
		map.put("career",trailPool.getPosition());
		map.put("mobile",trailPool.getMobilePhone());
		map.put("qq",trailPool.getQq());
		map.put("bank","");//
		map.put("account","");//
		map.put("email",trailPool.getEmail());
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

