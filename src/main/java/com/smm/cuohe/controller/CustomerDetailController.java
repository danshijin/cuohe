package com.smm.cuohe.controller;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.cuohe.bo.IBlacklistBO;
import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.bo.IComRecordsBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.IContacterBo;
import com.smm.cuohe.bo.IOrdersBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.domain.ComRecords;
import com.smm.cuohe.domain.CompanyView;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.CustomsBlackList;
import com.smm.cuohe.domain.CustomsBlackListDto;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.User;


/**
 * @author zhaoyutao
 *
 */
@Controller
@RequestMapping(value="/customerDetail")
public class CustomerDetailController {

	private Logger logger= Logger.getLogger(CustomerDetailController.class);
	
	@Resource
	ICompanyBO companyBO;
	
	@Resource
	IComRecordsBO comRecordsBO;
	
	@Resource
	IContacterBo iContacterBO;
	
	@Resource
	IOrdersBO iOrdersBO;
	
	@Resource
	ISellPoolBO iSellPoolBO;
	
	@Resource
	ICategoryBO iCategoryBO;
	
	@Autowired
	IBlacklistBO iBlacklistBO;
	

	//主页
	@RequestMapping(value="index")
	public ModelAndView index(Integer id, String initPage,HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView("customerDetail/index");

        if(id==0){

            mv.setViewName("customerDetail/notFound");
            mv.addObject("message","客户不存在");

            return mv;
        }

		mv.addObject("id", id);
		mv.addObject("initPage", initPage);
		String selItems = JSONObject.fromObject(iCategoryBO.getCategoryByModuleName()).toString();
		mv.addObject("selItems", selItems);
		mv.addObject("companyName", companyBO.getCompanyNameByCustomerId(id));
		
		return mv;
	}
	//企业信息
	@RequestMapping(value="companyView")
	public ModelAndView companyView(Integer customerId){
		ModelAndView mv = new ModelAndView("customerDetail/companyView");
		
		CompanyView companyView = companyBO.getCompanyViewByCustomId(customerId);
		
		try {
			mv.addObject("customerJson", new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).writeValueAsString(companyView));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="updateCompanyInfo")
	@ResponseBody
	public String updateCompanyInfo(HttpServletRequest request ,CompanyView companyView){
		return companyBO.updateCompanyView(companyView, request);
	}
	
	@RequestMapping(value="deleteCompany")
	@ResponseBody
	public String deleteCompany(Integer id, HttpServletRequest request){
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
		
		return companyBO.deleteCompany(id, user.getId());
	}
	
	//沟通记录
	@RequestMapping(value="comRecords")
	public ModelAndView communicationRecord(Integer customerId){
		ModelAndView mv = new ModelAndView("customerDetail/comRecords");
		return mv;
	}
	
	@RequestMapping(value="getPagingComRecord")
	@ResponseBody
	public Map<String, Object> getPagingComRecord(Integer customerId, Integer start, Integer len){
		Map<String, Object> map = comRecordsBO.getPagingRecordsByCustomerId(customerId, start, len);
		return map;
	}
	
	
	@RequestMapping(value="showAddComRecordDialog")
	public ModelAndView showAddComRecordDialog(){
		
		ModelAndView mv = new ModelAndView("customerDetail/addComRecordDialog");
		
		return mv;
	}
	
	@RequestMapping(value="addComRecord")
	@ResponseBody
	public String addComRecord(String newRecordTitle, String newRecordContext, Integer customerId, HttpServletRequest request){
		
		return comRecordsBO.addComRecord(newRecordTitle, newRecordContext, (User)request.getSession().getAttribute("userInfo"), customerId);
	}
	
	@RequestMapping(value="updateComRecord")
	@ResponseBody
	public String updateComRecord(ComRecords record, HttpServletRequest request){
		return comRecordsBO.updateComRecord(record, (User)request.getSession().getAttribute("userInfo"));
	}
	
	@RequestMapping(value="deleteComRecord")
	@ResponseBody
	public String deleteComRecord(Integer id){
		return comRecordsBO.deleteComRecord(id);
	}
	
	//联系人
	@RequestMapping(value="contacters")
	public ModelAndView contacts(Integer customerId){
		ModelAndView mv = new ModelAndView("customerDetail/contacters");
		
		List<Contacter> list = iContacterBO.getContactersByCustomerId(customerId);
		try {
			mv.addObject("contactersJson", new ObjectMapper().writeValueAsString(list));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	//黑名单
		@RequestMapping(value="blackList")
		public ModelAndView blackList(Integer customerId){
			ModelAndView mv = new ModelAndView("customerDetail/blackList");
			
//			List<Contacter> list = iContacterBO.getContactersByCustomerId(customerId);
			
			List<CustomsBlackListDto> list = iBlacklistBO.queryBlackListByCustomerID(customerId);
			try {
				mv.addObject("contactersJson", new ObjectMapper().writeValueAsString(list));
				mv.addObject("customerId", customerId);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return mv;
		}
	//添加黑名单
		@RequestMapping(value="addBlackList")
		@ResponseBody
		public Map<String, Object> addBlackList(String customerId,String blackCustomerId,HttpServletRequest request){
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//获取登陆信息
			User user = (User) request.getSession().getAttribute("userInfo");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			CustomsBlackList cuscomsBlackList = new CustomsBlackList();
			cuscomsBlackList.setCreatedat(new Date());
			cuscomsBlackList.setCreatedby(user.getId());
			cuscomsBlackList.setCustomerid(Integer.parseInt(customerId));
			cuscomsBlackList.setBlackcustomerid(Integer.parseInt(blackCustomerId));
			cuscomsBlackList.setStatus(0);
			//该公司是否添加
			if(!iBlacklistBO.checkBlack(cuscomsBlackList)){
			cuscomsBlackList = iBlacklistBO.addBlack(cuscomsBlackList);
			if(cuscomsBlackList.getId()!=null){
				CustomsBlackListDto dto = iBlacklistBO.queryBlackListByKey(cuscomsBlackList.getId());
				returnMap.put("msg","添加成功");
				returnMap.put("status","true");
				returnMap.put("data", dto);
			}else{
				returnMap.put("msg","添加失败");
				returnMap.put("status","false");
			}
			}else{
				returnMap.put("msg","不能重复拉黑");
				returnMap.put("status","false");
			}
			return returnMap;
		}
		//删除黑名单
		@RequestMapping(value="delBlackList")
		@ResponseBody
		public Map<String, Object> delBlackList(String blackId,HttpServletRequest request){
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			if(iBlacklistBO.deleteBlackListById(Integer.parseInt(blackId))){
				returnMap.put("msg","成功");
				returnMap.put("status","true");
			}else{
				returnMap.put("msg","失败");
				returnMap.put("status","false");
			}
			return returnMap;
		}
	
	@RequestMapping(value="showAddContacterView")
	public ModelAndView showAddContacterView(Integer customerId){
		ModelAndView mv = new ModelAndView("customerDetail/addContacter");
		mv.addObject("customerId", customerId);
		return mv;
	}
	
	@RequestMapping(value="showEditContacterView")
	public ModelAndView showEditContacterView(Integer contacterId){
		ModelAndView mv = new ModelAndView("customerDetail/addContacter");
		Contacter contacter = iContacterBO.getContacterById(contacterId);
		mv.addObject("contacter", contacter);
		return mv;
	}
	//为企业添加联系人
	@RequestMapping(value="addContacterForCompany")
	public ModelAndView addContacterForCompany(Contacter contacter, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("customerDetail/index");
		iContacterBO.addContacterForCompany(contacter, (User)request.getSession().getAttribute("userInfo"));
		return mv;
	}
	
	//为企业添加联系人
	@RequestMapping(value="addContacterForCompanyAndShowAddView")
	public ModelAndView addContacterForCompanyAndShowAddView(Contacter contacter, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("customerDetail/addContacter");
		request.setAttribute("contacter", null);
		iContacterBO.addContacterForCompany(contacter, (User)request.getSession().getAttribute("userInfo"));
		return mv;
	}
		
	//联系人更新
	@RequestMapping(value="updateContacter")
	public ModelAndView updateContacter(Contacter contacter, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("customerDetail/index");
		iContacterBO.updateContacter(contacter, (User)request.getSession().getAttribute("userInfo"));
		return mv;
	}
	//联系人更新后显示添加页面
	@RequestMapping(value="updateContacterAndShowAddView")
	public ModelAndView updateContacterAndShowAddView(Contacter contacter, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("customerDetail/addContacter");
		iContacterBO.updateContacter(contacter, (User) request.getSession().getAttribute("userInfo"));
//		mv.addObject("companyId", contacter.getCompany().getId());
		return mv;
	}
	
	//根据id删除联系人
	@RequestMapping(value="deleteContacterById")
	@ResponseBody
	public String deleteContacterById(Integer id){
		
		iContacterBO.deleteContacterById(id);
		
		return "success";
	}
	
	
	//订单记录
	@RequestMapping(value="orderRecord")
	public ModelAndView orderRecord(){
		ModelAndView mv = new ModelAndView("customerDetail/orderRecord");
		
		return mv;
	}
	
	
	@RequestMapping(value="orderRecord/cancelOrder")
	@ResponseBody
	public Map<String,Object> orderRecordCancelOrder(Integer orderId){
		Map<String,Object> map = new HashMap<String, Object>(1);
		map.put("code", 0);
		int k = iOrdersBO.cancelOrderByOrderId(orderId);
		
		if(k>0){
			map.put("code", 1);
		}
		return map;
	}
	
	
	@RequestMapping(value="orderRecord/getList")
	@ResponseBody
	public Map<String, Object> getOrderRecord(Integer customerId, Integer start, Integer len){
		
		
		
		Map<String, Object> parasMap = new HashMap<String, Object>();
		
		parasMap.put("customerId", customerId);
		
		parasMap.put("start", start);
		
		parasMap.put("len", len);
		
		return iOrdersBO.getOrderRecord(parasMap);
		
	}

	@RequestMapping(value="orderRecord/orderDetail")
	public ModelAndView orderDetail(HttpServletRequest request){
		String orderId = request.getParameter("orderId");
		ModelAndView mv = new ModelAndView("customerDetail/orderDetail");
		Order order = iOrdersBO.getOrderById(Integer.valueOf(orderId));
		List<Map<String,Object>> orderDetail = iOrdersBO.getOrderDetailByOrderId(Integer.valueOf(orderId));
		List<SubOrder> list = iOrdersBO.getSubOrdersByOrderId(Integer.valueOf(orderId));
		mv.addObject("order", order);
		try {
			if(orderDetail!=null&&orderDetail.size()>0){
				mv.addObject("sell",orderDetail.get(0));
			}
			if(orderDetail!=null&&orderDetail.size()>1){
				mv.addObject("buy",orderDetail.get(1));
			}
			if(list!=null&&!list.isEmpty()){
				String contract = null;
				for(int i=0;i<list.size();i++){
					contract = list.get(i).getContract();
					if(StringUtils.isNotBlank(contract)){
						 list.get(i).setContract(URLEncoder.encode(URLEncoder.encode(contract, "UTF-8"),"UTF-8"));
					}
				}
			}
			mv.addObject("ifExistContract", iOrdersBO.ifExistContract(Integer.valueOf(orderId)));
			mv.addObject("list",new ObjectMapper().writeValueAsString(list));
			mv.addObject("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="orderRecord/orderDetail2")
	public ModelAndView orderDetail2(HttpServletRequest request){
		String orderId = request.getParameter("orderId");
		ModelAndView mv = new ModelAndView("customerDetail/orderDetail2");
		Order order = iOrdersBO.getOrderById(Integer.valueOf(orderId));
		List<Map<String,Object>> orderDetail = iOrdersBO.getOrderDetailByOrderId(Integer.valueOf(orderId));
		List<SubOrder> list = iOrdersBO.getSubOrdersByOrderId(Integer.valueOf(orderId));
		mv.addObject("order", order);
		try {
			if(orderDetail!=null&&orderDetail.size()>0){
				mv.addObject("sell",orderDetail.get(0));
			}
			if(orderDetail!=null&&orderDetail.size()>1){
				mv.addObject("buy",orderDetail.get(1));
			}
			if(list!=null&&!list.isEmpty()){
				String contract = null;
				for(int i=0;i<list.size();i++){
					contract = list.get(i).getContract();
					if(StringUtils.isNotBlank(contract)){
						 list.get(i).setContract(URLEncoder.encode(URLEncoder.encode(contract, "UTF-8"),"UTF-8"));
					}
				}
			}
			mv.addObject("ifExistContract", iOrdersBO.ifExistContract(Integer.valueOf(orderId)));
			mv.addObject("list",new ObjectMapper().writeValueAsString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	//报价记录
	@RequestMapping(value="offerRecord")
	public ModelAndView offerRecord(){
		
		ModelAndView mv = new ModelAndView("customerDetail/offerRecord");
		return mv;
	}
	
	//报价记录展示
	@RequestMapping(value="getPagingOfferRecord")
	@ResponseBody
	public Map<String, Object> getPagingOfferRecord(Integer customerId, Integer start, Integer len, HttpServletRequest request){
		
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
		
		return iSellPoolBO.getPagingOfferRecord(customerId, user, start, len);
	}
	
	@RequestMapping(value="entTypesDialog")
	public ModelAndView entTypesDialog(HttpServletRequest request){
		
		ModelAndView mv = new ModelAndView("customerDetail/entTypesDialog");
		
		mv.addObject("entTypes", request.getParameter("entTypes"));
		
		return mv;
	}
	
	@RequestMapping(value="getParentArea")
	@ResponseBody
	public Map<String, Object> getParentArea(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentAreas", companyBO.getParentArea());
	
		return map;
	}
	
}
