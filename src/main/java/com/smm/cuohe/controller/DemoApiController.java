package com.smm.cuohe.controller;

import com.smm.cuohe.bo.IDemoApiBO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 
 * @author zengshihua
 *
 */
@Controller
@RequestMapping("/user")
public class DemoApiController {

	private static Logger logger = Logger.getLogger(DemoApiController.class.getName());

	@Resource
	private IDemoApiBO iDemoApiBO;
	
	@RequestMapping("/demoApi")
	@ResponseBody
	public String all() {
		String jsonStr = iDemoApiBO.GetCompanySimpleInfo();
		return jsonStr;
	}

}
