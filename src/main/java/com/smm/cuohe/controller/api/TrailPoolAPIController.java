package com.smm.cuohe.controller.api;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.bo.IPurchaseBO;
import com.smm.cuohe.bo.customermanage.ITrailPoolPojoBO;
import com.smm.cuohe.dao.customermanage.ITrailPoolPojoDAO;
import com.smm.cuohe.domain.TrailPoolAPI;
import com.smm.cuohe.util.ResultData;
import com.smm.cuohe.util.ResultEnum;

 
@Controller
@RequestMapping("/trailPoolApi")
public class TrailPoolAPIController {
	private static Logger logger= Logger.getLogger(TrailPoolAPIController.class.getName());
	@Resource
	IPurchaseBO iPurchaseBO;
	@Resource
	ICategoryBO iCategoryBO; 
	@Resource
	IAreasBO iAreasBO;
    @Resource
	private ITrailPoolPojoBO iTrailPool;
    @Resource
    private ITrailPoolPojoDAO iTrailPoolPojoDao;
    
    @Resource
    private RestTemplate restTemplate; 
    
    
	/**
	 * 添加线索池接口
	 * @param request
	 * @param TrailPoolAPI tpApi
	 * @return
	 */ 
    @RequestMapping(value = "addTrailPool") 
	@ResponseBody
	public ResultData addTrailPool(HttpServletRequest request, @RequestBody TrailPoolAPI tpApi) { 
    	
		Map<String, String> resultMap=this.iTrailPool.addTrailPool(tpApi);
		
		ResultData resultData = new ResultData();
		
		if("succ".equals(resultMap.get("code"))){
			
			resultData.setCode(ResultEnum.SUCC.getCode());
			resultData.setMessage(resultMap.get("msg"));
			resultData.setResultData(resultMap.get("resultData"));
			
		}else if("has".equals(resultMap.get("code"))){
			
			resultData.setCode(ResultEnum.HAS.getCode());
			resultData.setMessage(ResultEnum.HAS.getMessage());
			
		}else{
			
			resultData.setCode(ResultEnum.FAILD.getCode());
			resultData.setMessage(resultMap.get("msg"));
		}
		
		logger.info("["+getDateWidthFormat(null)+"]结束调用添加线索池接口，返回结果：resultData："+resultData);
		
		return resultData;
	}
	
	
	// 格式化当前系统日期
		public String getDateWidthFormat(String format) {
			if (format == null || format.equals("")) {
				format = "yyyy-MM-dd HH:mm:ss";
			}

			SimpleDateFormat dateFm = new SimpleDateFormat(format);
			String dateTime = dateFm.format(new java.util.Date());
			return dateTime;
		}
}
