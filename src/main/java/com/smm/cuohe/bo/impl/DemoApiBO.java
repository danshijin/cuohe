package com.smm.cuohe.bo.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.IDemoApiBO;
import com.smm.cuohe.util.StringUtil;

/**
 * 
 * @author zengshihua
 *
 */
@Service
public class DemoApiBO implements IDemoApiBO {

	
	private Logger logger = Logger.getLogger(DemoApiBO.class);
	
	@Resource
    private RestTemplate restTemplate;
    
    @Value("#{ch['getCompanySimpleInfo.URL']}")
	private String getCompanyUrl;
    
	@Override
	public String GetCompanySimpleInfo() {
	    
		//单个参数
		String url =getCompanyUrl.replace("{0}", StringUtil.doEncoder("有色网"));
		logger.info("开始调用：获取企业简略信息接口");
		String jsonStr=restTemplate.postForObject(url,null,String.class);
		return jsonStr;
	}

}
