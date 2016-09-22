package com.smm.cuohe.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.ICustomerConversationBO;
import com.smm.cuohe.util.StringUtil;

/**
 * @author zhaoyutao
 *
 */
@Controller
@RequestMapping("/customerConversation")
public class CustomerConversationController {
	
	@Resource
	ICustomerConversationBO iDiscussChatBO;
	
	private Logger logger = Logger.getLogger(CustomerConversationController.class);
	
	/**  跳转到洽谈接口测试页面
	 * @return
	 */
	@RequestMapping(value="index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("/customerDetail/test"); 
		
		return mv;
		
	}
	
	
	/**
	 * @param uid
	 * @param item_id
	 * @param lastToken
	 * @param product_id
	 * @param ip
	 * @param url
	 * @param url_name
	 * @param customerName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="createConversation")
	@ResponseBody
	public Map<String, Object> createConversation(Integer uid, Integer item_id, String lastToken, Integer product_id, Integer sell_id,
			String ip, String url, String url_name, String customerName, Integer companyId, String companyName,Integer source,  HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		if(lastToken == null && ip == null && url == null && url_name == null && customerName == null && companyName == null){
			map.put("status", "faild");
			map.put("msg", "参数错误");
			map.put("data", null);
		}else{
			try {
				Map<String, Object> parasMap = new HashMap<String, Object>();
				parasMap.put("uid", uid);
				parasMap.put("item_id", item_id);
				parasMap.put("lastToken", lastToken);
				parasMap.put("product_id", product_id);
				parasMap.put("ip", ip);
				parasMap.put("url", url);
				parasMap.put("url_name", url_name);
				parasMap.put("customerName", customerName);
				
				parasMap.put("companyId", companyId);
				parasMap.put("companyName", companyName);
				parasMap.put("sellId", sell_id);
				parasMap.put("source", source);
				
				logger.info(parasMap);
				map=iDiscussChatBO.createConversation(parasMap, request);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value="getChatsByToken")
	@ResponseBody
	public Map<String, Object> getChatsForCustomerByToken(Integer lastid, String token){
		
		Map<String, Object> parasMap = new HashMap<String, Object>();
		
		parasMap.put("lastid", lastid);
		
		parasMap.put("token", token);
		
		return iDiscussChatBO.getChatsForCustomerByToken(parasMap);
		
	}
	
	/**
	 * @param uid 商城用户id，用来登录
	 * @param token 
	 * @param customerName 用户名称
	 * @param companyId 商城公司id
	 * @param companyName 商城公司名称
	 * @return
	 */
	@RequestMapping(value="mergeConversation")
	@ResponseBody
	public Map<String, Object> mergeConversation(Integer uid, String token, String customerName, Integer companyId, String companyName){
		Map<String, Object> map = new HashMap<String, Object>();
		if(uid == null){
			map.put("status", "faild");
			
			map.put("msg", "参数异常：商城用户id不能为空");
			
			map.put("data", "");
			
			return map;
		}
		
		if(StringUtils.isBlank(token)){
			map.put("status", "faild");
			
			map.put("msg", "参数异常：会话token不能为空");
			
			map.put("data", "");
			
			return map;
		}
		
		
		Map<String, Object> parasMap = new HashMap<String, Object>();
		
		parasMap.put("uid", uid);
		
		parasMap.put("token", token);
		
		parasMap.put("customerName", customerName);
		
		parasMap.put("companyId", companyId);
		
		companyName = StringUtil.doDecoder(companyName);
		
		parasMap.put("companyName", companyName);
		
		parasMap.put("sysTime", new Date());
		
		return iDiscussChatBO.mergeConversationForCustomer(parasMap);
	}
	
	@RequestMapping(value="getContractHtml")
	@ResponseBody
	public Map<String, Object> getContractHtml(Integer uid, String token){
		
		return iDiscussChatBO.getContractHtml(uid, token);
		
	}
	
	@RequestMapping(value="closeConversation")
	@ResponseBody
	public Map<String, Object> closeConversation(String token){
		
		return iDiscussChatBO.closeConversationForCustomer(token);
		
	}
	
	@RequestMapping(value="customOnline")
	@ResponseBody
	public Map<String, Object> customOnline(String token){
		
		return iDiscussChatBO.customOnline(token);
		
	}
	
	@RequestMapping(value="confirmContract")
	@ResponseBody
	public Map<String, Object> confirmContract(String orderNo){
		
		return iDiscussChatBO.confirmContract(orderNo);
	}
	
	@RequestMapping(value="test")
	public ModelAndView test(){
		
		ModelAndView mv = new ModelAndView("customerDetail/SystemLog");
		
		return mv;
	}
}
