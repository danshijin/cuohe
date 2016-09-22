package com.smm.cuohe.controller.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IDealMakerConversationBO;
import com.smm.cuohe.dao.IDiscussChatRecordDAO;
import com.smm.cuohe.controller.BaseController;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.TestFtlExl;

/**
 * @author zhaoyutao
 *
 */
@Controller
@RequestMapping("/dealMakerConversation")
public class DealMakerConversationController extends BaseController {
	
	private Logger logger = Logger.getLogger(DealMakerConversationController.class);
	
	@Resource
	IDealMakerConversationBO iDealMakerBO;
	
	@Resource
	ICustomerBO iCustomerBO;
	
	 @Resource
	 ICompanyBO iCompanyBO;
	
	 @Resource
	 IAreasBO iAreasBO;
	 
	 @Resource
	 IDiscussChatRecordDAO iDiscussChatRecordDao;
	 
	 
	/**
	 * @param userName  撮合人员登录名称
	 * @param userPassword 撮合人员登录密码
	 * @return
	 */
	@RequestMapping(value="login")
	@ResponseBody
	public Map<String, Object> login(String userName, String userPassword, HttpServletRequest request){
		
		String rootPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/";
		return iDealMakerBO.login(userName, userPassword, rootPath);
		
	}
	
	/**
	 * @param itemId  撮合人员所属品目
	 * @return 所有会话
	 */
	@RequestMapping(value="getConversations")
	@ResponseBody
	public Map<String, Object> getConversations(Integer itemId, Integer employeeId,String flagTime){
		
		return iDealMakerBO.getConversations(itemId, employeeId,flagTime);
		
	}
	
	/**
	 * @param recordId 可为空，取大于（不包含）该id的聊天记录
	 * @param chatId   会话id
	 * @param customId 可为空，商城用户id
	 * @return
	 */
	@RequestMapping(value="getChats")
	@ResponseBody
	public Map<String, Object> getChatForDealMaker(Integer recordId, Integer chatId, Integer customId){
		
		Map<String, Object> parasMap = new HashMap<String, Object>();
		
		parasMap.put("recordId", recordId);
		
		parasMap.put("chatId", chatId);
		
		parasMap.put("customId", customId);
				
		return iDealMakerBO.getChatForDealMaker(parasMap);
		
	}
	
	/**
	 * @param record 会话记录信息
	 * @return 
	 */
	@RequestMapping(value="sendChat")
	@ResponseBody
	public Map<String, Object> sendChatForDealMaker(DiscussChatRecord record, String token){
		
		return iDealMakerBO.sendChatForDealMarker(record, token);
	}
	
	@RequestMapping(value="countByStatusAndCustomId")
	@ResponseBody
	public Map<String, Object> countByStatusAndCustomId(Integer customId, Integer status, Integer itemId){
		
		return iDealMakerBO.countConversationByStatusAndCustomIdForDealMaker(customId, status);
	}
	
	@RequestMapping(value="countHistoryOrder")
	@ResponseBody
	public Map<String, Object> countHistoryOrder(String customerName, Integer itemId){
		return iDealMakerBO.countHistoryOrder(customerName, itemId);
	}
	
	
	/** 根据下面的参数统计会话数
	 * @param customId 商城用户id
	 * @param itemId 品目id
	 * @return
	 */
	@RequestMapping(value="countByCustomIdAndItemId")
	@ResponseBody
	public Map<String, Object> countByCustomIdAndItemId(Integer customId, Integer itemId){
		
		return iDealMakerBO.countConversationByCustomIdAndItemIdForDealMaker(customId, itemId);
	}
	
	/** 对会话进行更新
	 * @param chatId 会话id    
	 * @param dealMakerId 撮合人员id
	 * @param status 更新后状态
	 * @return
	 */
	@RequestMapping(value="updateConversationStatus")
	@ResponseBody
	public Map<String, Object> updateConversationStatus(Integer chatId, Integer dealMakerId, Integer status){
		
		Map<String, Object> parasMap = new HashMap<String, Object>();
		
		parasMap.put("chatId", chatId);
		
		parasMap.put("dealMakerId", dealMakerId);
		
		parasMap.put("status", status);
		
		return iDealMakerBO.updateConversationStatusForDealMaker(parasMap);
	}
	
	
	/** 获取统一品目下的所有撮合人员信息
	 * @param itemId 品目id
	 * @return
	 */
	@RequestMapping(value="getMyTeamInfo")
	@ResponseBody
	public Map<String, Object> getMyTeamInfo(Integer itemId, Integer dealMakerId){
		
		return iDealMakerBO.getMyTeamInfo(itemId, dealMakerId);
	}
	
	/** 生成订单并返回订单id
	 * @param order 订单内容
	 * @param chatId 生成该订单的会话
	 * @param costomId 商城用户id
	 * @return
	 */
	@RequestMapping(value="generateOrder", method=RequestMethod.POST)
	public String generateOrder(String orderText, Integer chatId, Integer costomId, Integer productId, HttpServletRequest request, HttpServletResponse response){
		
		return "forward:/buyOrder/generateOrder";
		
	}
	
	/** 确认订单
	 * @param orderId 订单id
	 * @param action 操作  0:确认订单、1：确认订单并生成合同、2：取消订单
	 * @return
	 */
	@RequestMapping(value="confirmOrder")
	@ResponseBody
	public Map<String, Object> confirmOrder(Integer orderId, Integer action, HttpServletRequest request){
		
		Map<String, Object> parasMap = new HashMap<String, Object>();
		
		parasMap.put("orderId", orderId);
		
		parasMap.put("action", action);
		
		return iDealMakerBO.confirmOrderByDealMaker(parasMap, request);
	}
	
	/**
	 * @param conversationId   会话id
	 * @param currDealMakerId  当前撮合人员id
	 * @param destDealMakerId  转移给的撮合人员id
	 * @return 0代表成功，非0表示该会话被其他撮合人员锁定，并返回该撮合人员id
	 */
	@RequestMapping(value="transferConversation")
	@ResponseBody
	public Map<String, Object> transferConversation(Integer conversationId, Integer currDealMakerId, Integer destDealMakerId){
		return iDealMakerBO.transferConversationByDealMaker(conversationId, currDealMakerId, destDealMakerId);
	}
	
	/**
	 * @param chatRecordId  要更新记录的id
	 * @param destStatus   更新后状态
	 * @param overtimeSeconds 超时时间（秒）
	 * @return 返回-1  表示超时， 0表示正常更新 ，>0表示当前会话记录的状态
	 */
	@RequestMapping(value="changeRecordStatus")
	@ResponseBody
	public Map<String, Object> changeRecordStatus(Integer chatRecordId, Integer destStatus, Integer overtimeSeconds){
		
		return iDealMakerBO.changeRecordStatusByDealMaker(chatRecordId, destStatus, overtimeSeconds);
		
	}
	
	@RequestMapping(value="getCommodityById")
	@ResponseBody
	public Map<String, Object> getCommodityById(Integer commodityId){
		
		return iDealMakerBO.getCommodityByIdForDealMaker(commodityId);
		
	}
	
	@RequestMapping(value="getItemAttrByItemId")
	@ResponseBody
	public Map<String, Object> getItemAttrByItemId(Integer itemId){
		
		return iDealMakerBO.getItemAttrByItemId(itemId);
		
	}
	
	@RequestMapping(value="updateOnlineStatus")
	@ResponseBody
	public Map<String, Object> updateOnlineStatus(Integer dealMakerId, Integer online){
		
		return iDealMakerBO.updateOnlineStatus(dealMakerId, online);
		
	}
	
	@RequestMapping(value="exportFtl")
	@ResponseBody
	public Map<String, Object> exportFtl(){
		
		TestFtlExl test = new TestFtlExl();  
        test.createWord();
		
		return new HashMap<String, Object>();
	}
	
	@RequestMapping(value="getAllProvince")
	@ResponseBody
	public Map<String, Object> getAllProvince(){
		
		return iDealMakerBO.getAllProvince();
	}
	
	@RequestMapping(value="getWarehouseByProvince")
	@ResponseBody
	public Map<String, Object> getWarehouseByProvince(Integer provinceId, Integer itemId){
		
		return iDealMakerBO.getWarehouseByProvince(provinceId, itemId);
	}
	
	@RequestMapping(value="hasCustomerByAccount")
	@ResponseBody
	public Map<String, Object> hasCustomerByAccount(String account, Integer itemId){
		String companyName = getRequestParamURLDecoder("companyName");
		
		return iDealMakerBO.hasCustomerByAccount(account, itemId,companyName);
	}
	
 	@RequestMapping(value="addCustomer")
 	@ResponseBody
 	public Map<String, Object> addCustomer(String customer){
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		
		Customer cus= null;
 		 try{
 			cus = mapper.readValue(customer, Customer.class);
 			List<Areas> list = iAreasBO.getChildArea(cus.getAreaId().toString());
 			if(list != null && list.size() > 0){
 				cus.setAreaId(list.get(0).getId());
 			}
             int customerId = iCustomerBO.saveCustomerForDealMaker(cus);

             map.put("status", "ok");
             map.put("msg","新增客户成功");
             map.put("data", map.put("data", customerId));
         }catch (Exception e){

             e.printStackTrace();
             map.put("status", "faild");
             map.put("msg","新增客户失败！"+e.getMessage());
             map.put("data", null);
         }
		return map;
	} 
	
 	@RequestMapping(value="queryCustomerByCompanyName")
 	@ResponseBody
 	public Map<String, Object> queryCustomerByCompanyName(String companyName, Integer itemId){
 		
 		Map<String, Object> map = new HashMap<String, Object>();
        
 		if(itemId == null){
 			map.put("status", "faild");
 	        map.put("msg", "参数异常：品目id为空");
 	        map.put("data", null);
 	        return map;
 		}
 		
        List<Company> companyList = iCompanyBO.getCompanyByNameAndItem(companyName, itemId);
        map.put("status", "ok");
        map.put("msg", "");
        map.put("data", companyList);
        return map;
 	}
 	
 	/**
 	 * 30秒点击事件
 	 * @param employeeId 撮合员ID
 	 * @param itemId,online
 	 * @return
 	 */
 	@RequestMapping(value="insertChatRecord")
 	@ResponseBody
 	public Map<String, Object> insertChatRecord(Integer employeeId,Integer itemId,String online){
 		Map<String, Object> map = new HashMap<String, Object>();
 		String status="ok";
 		String msg="数据插入成功";
 		logger.info("入参:"+employeeId+"--"+itemId+"--"+online);
 		try {
 			Map<String, Object> per=new HashMap<String, Object>();
				per.put("employeeId", employeeId);
				per.put("online", online);
 			if(online.equals("2")){//撮合员状态为离开
 				iCustomerBO.updateById(per);//修改撮合员在线状态
 	 			String name="";//撮合员姓名
 	 			String phone="";//联系方式
 	 			//获取撮合员电话信息
 	 			User user=iCustomerBO.getUserByEmployeeId(employeeId);
 	 			if(user !=null){
 	 				phone=user.getPhone();
 	 				name=user.getName();
 	 			}
 				//根据撮合员ID找出对在线聊天人信息
 	 			Map<String, Object> maper=new HashMap<String, Object>();
 	 			maper.put("employeeId", employeeId);
 	 			maper.put("itemId", itemId);
 	 			maper.put("sysTime", new Date());//开始时间
 	 			List<Chats> listChats=iCustomerBO.getChatsByemployeeId(maper);
 	 			for(Chats chats:listChats){
 	 				//根据洽谈编号获取洽谈对应聊天信息
 	 				DiscussChatRecord discussChatRecord=iCustomerBO.getdiscussChatRecordByChatId(chats.getId());
 	 				if(discussChatRecord !=null){
 	 					String chatFromType=discussChatRecord.getChatFromType();
 	 					if(chatFromType.equals("C")){//C代表客户发送的最后一条消息记录,需呀插入一条消息记录
 	 						DiscussChatRecord record=new DiscussChatRecord();
 	 						record.setChatId(chats.getId());
 	 						record.setType(0);
 	 						record.setContent("不好意思，撮合人员忙碌中，请您将需求回复在这里，或通过电话联系："+name+" "+phone+"。");
 	 						record.setChatFromType("U");
 	 						record.setChatFromId(employeeId);
 	 						record.setStatus(4);
 	 						record.setEmployeeId(employeeId);
 	 						record.setSysTime(new Date());
 	 						iDiscussChatRecordDao.addRecord(record);
 	 					}
 	 				}
 	 			}
 			}else if(online.equals("1")){
 				iCustomerBO.updateById(per);//修改撮合员在线状态
 			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			status="faild";
			msg="数据插入失败";
		}
        map.put("status",status);
        map.put("msg", msg);
        map.put("data", null);
        return map;
 	}
 	
}
