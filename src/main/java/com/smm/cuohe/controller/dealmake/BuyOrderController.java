package com.smm.cuohe.controller.dealmake;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.IBusinessBO;
import com.smm.cuohe.bo.IChatsBO;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IDealMakerConversationBO;
import com.smm.cuohe.bo.IOrdersBO;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.IBuyOrderBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.dao.base.ChDiscussChatRecordEntityMapper;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChBuyPoolEntity;
import com.smm.cuohe.domain.base.ChChatSEntity;
import com.smm.cuohe.domain.base.ChCustomsEntity;
import com.smm.cuohe.domain.base.ChDiscussChatRecordEntity;
import com.smm.cuohe.domain.base.ChWareHouse;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.CommodityEntity;
import com.smm.cuohe.domain.dealmake.ProductEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.Warehouse;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;
import com.smm.cuohe.util.URLRequest;

/**
 * 生成订单
 * @author haokang
 *
 */
@Controller
@RequestMapping(value="/buyOrder")
public class BuyOrderController {

	@Resource
	private IBuyOrderBO iBuyOrderBO;
	@Resource
	private ISellPoolBO iSellPoolBO;
	@Resource
	private RestTemplate restTemplate;
	@Resource
	private IAttrValuesBO attrValuesBO;
	@Resource
	IDealMakerConversationBO iDealMakerBO;
	@Resource
    private IOrdersBO iOrdersBO;
	@Resource
	private IChatsBO iChatsBO;
	@Resource
	private IBusinessBO iBusinessBO;
	@Autowired
	private IChatsBO ichatBo;
	@Autowired
	private ChDiscussChatRecordEntityMapper recordDao;
	@Resource
	BuyPoolBo buypoolbo;
	@Resource
	IAreasBO iAreasBO;
	@Resource
	ICustomerBO customerBO;
	
	@Value("#{ch['publicitem.URL']}")
	private String publicitem;
	
	@Value("#{ch['order.URL']}")
	private String order;
	
	@Value("#{ch['producter.URL']}")
	private String producter;
	
	@Value("#{ch['checkSeller.URL']}")
	private String checkSeller;
	
	@Value("#{ch['dealdemall.URL']}")
	private String dealmlmall;
	
	
	private Logger logger = Logger.getLogger(BuyOrderController.class);
	
	@RequestMapping(value="/selWarehouseByArea")
	@ResponseBody
	public JSONObject selWarehouseByArea(Integer areaId,HttpServletRequest req,String companyName){
		User user = (User) req.getSession().getAttribute("userInfo");
		List<Warehouse> list  = iBuyOrderBO.selWarehouseByArea(companyName,areaId,user.getItemId());
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		for(Warehouse w :list){
			w.setCreatedAt(new Date());
			w.setUpdateAt(new Date());
			array.add(w);
		}
		json.put("list", array);
		return json;
	}
	
	/** 撮合客户端调用，生成订单并返回订单id
	 * @param order 订单内容
	 * @param chatId 生成该订单的会话
	 * @param costomId 商城用户id
	 * @return
	 */
	@RequestMapping(value="generateOrder")
	@ResponseBody
	public Map<String, Object> generateOrder(String orderText, Integer chatId, Integer costomId, Integer productId, String moq, 
			String isCloseSell, String outmoq,Integer hasContract, HttpServletRequest request, HttpServletResponse response){
		
		logger.info("参数 orderText : " + orderText +", chatId："+chatId +"， customId：" +costomId +"， priductId: " + productId +"， moq：" + moq +"， outmoq："+outmoq+"  ,  hasContract :" + hasContract+",isCloseSell"+isCloseSell);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Order order= null;
		Map<String, Object> rltMap = new HashMap<String, Object>();
		
		
		try {
			order = mapper.readValue(orderText, Order.class);
			
			
			
			/** 
			 * 生成订单之前再次校验买卖家两方是否存在黑名单关系 2015年12月18日上午11:08:26   zhangnan/张楠
			 */
			//卖家买家
			Integer sellID = order.getSellId();
			Integer buyID = order.getBuyId();
			
			
			//检验黑名单关系
			Map<String,Object> balckParam = new HashMap<String, Object>();
			
			balckParam.put("customerID",sellID);
			balckParam.put("blackCustomerID", buyID);
			
			String flag1 = this.iBusinessBO.checkOrderListBySell(balckParam);
			
			
			balckParam.put("customerID",buyID);
			balckParam.put("blackCustomerID", sellID);
			
			String flag2 = this.iBusinessBO.checkOrderListBySell(balckParam);
			
			if (flag1.equals("ERROR") || flag1.equals("ERROR") || flag1.equals("ERROR") || flag2.equals("ERROR") || flag1.equals("ERROR") || flag2.equals("ERROR")) {
				rltMap.put("status", "faild");
				rltMap.put("msg", "双方存在黑名单关系，不能生成订单");
				return rltMap;
			}
			
			//根据chatid查到撮合系统卖盘
			Integer mallId = iChatsBO.getSellIdById(chatId);//根据chatId获取报盘id
			SellPool sell = iSellPoolBO.getSellByMallId(mallId);
			
			//order设置sourceId
			order.setSourceId(chatId);

//			//验证是否重复生成订单 2015年12月22日上午09:51:23   
//			//报盘ID
//			Integer poolId = ichatBo.getSellIdById(chatId);
//			
//			//报盘类型
//			Integer poolType = 1;
//			
//
//			//订单是否已经生成
//			Integer poolStatus = this.iBusinessBO.CheckPoolStatus(poolId,poolType);
//			if (poolStatus == 1) {
//				rltMap.put("status", "faild");
//				rltMap.put("msg", "订单已经生成或者报盘已经下架，本次订单生成失败！");
//				return rltMap;
//
//			}
					
			
			if(order!=null&&order.getPayment()==3){
				String sellAccount = iSellPoolBO.getcompanyAccount(order.getSellId());
				String buyAccount = iSellPoolBO.getcompanyAccount(order.getBuyId());
				Integer se = iSellPoolBO.checkUserPay(sellAccount);
				if(se==1){//没有支付账号
					rltMap.put("status", "faild");
					
					rltMap.put("msg", "卖方没有支付账号");
					
					rltMap.put("data", null);
					return rltMap;
				}
				Integer bu = iSellPoolBO.checkUserPay(buyAccount);
				if(bu==1){//没有支付账号
					rltMap.put("status", "faild");
					
					rltMap.put("msg", "买方没有支付账号");
					
					rltMap.put("data", null);
					return rltMap;
				}
			}
			
			
			List<CommodityAttr> commodityAttrList = order.getSubOrderList().get(0).getCommodityAttrList();
			
			List<AttrValue> attrValueList = new ArrayList<AttrValue>();
			
			for(CommodityAttr commodity : commodityAttrList){
				
				AttrValue attr = new AttrValue();
				
				attr.setAttrId(commodity.getAttrId());
				
				attr.setAttrName(commodity.getAttrName());
				
				attr.setAttrValue(commodity.getAttrValue());
				
				attrValueList.add(attr);
			}
			
			User user = iDealMakerBO.getUserForGenerateOrder(chatId);
			user.setItems(iDealMakerBO.getItemById(order.getItemId()));
			Map<String, Object> tempMap =  this.insertOrder(attrValueList, productId, user, order, moq, outmoq,Integer.parseInt(isCloseSell),hasContract, request,mallId);
			
			if(tempMap != null  && tempMap.get("success") != null && tempMap.get("success").equals("新增成功")){
				
				rltMap.put("status", "ok");
				
				rltMap.put("msg", "生成订单成功");
				/**
				 * 给客户发送系统消息 去卖家中心查看订单
				 */
				//根据chatid查询chat信息
				ChChatSEntity ChatEntity =  iChatsBO.findChatById(chatId);
				if(ChatEntity!=null){
				ChDiscussChatRecordEntity chRecord = new ChDiscussChatRecordEntity();
				chRecord.setChatid(chatId);
				chRecord.setType(4);//提示用户订单已经生产去商城查看
				chRecord.setContent(tempMap.get("orderCode").toString());
				chRecord.setChatfromtype("U");
				chRecord.setChatfromid(ChatEntity.getCustomerid());
				if(hasContract==0){//不生成合同  
					chRecord.setStatus(12);
				}else if(hasContract==1){//生成合同
					chRecord.setStatus(11);
				}
				
				chRecord.setEmployeeid(ChatEntity.getEmployeeid());
				chRecord.setCreatedat(new Date());
				recordDao.insert(chRecord);
				}else{
					rltMap.put("status", "faild");
					rltMap.put("msg", "会话不存在");
					rltMap.put("data", null);
					return rltMap;
				}
				customerBO.updateremindDate(order.getBuyId());//更新客户下次采购时间
				Map<String, Object> mp = new HashMap<String, Object>();
				mp.put("id", chatId);
				mp.put("orderId", order.getId());
				if(hasContract==0){//不生成合同  状态改为1：订单完成
					mp.put("status", 1);
				}else if(hasContract==1){//生成合同 状态改为3：订单未完成
					mp.put("status", 3);
				}
				iChatsBO.updateStatusById(mp);//更新洽谈订单状态
				if("1".equals(isCloseSell)){
 					if(sell!=null){
 						iBuyOrderBO.updateSellIsClose(sell.getId());//更新报盘为已提交状态				
 						iBuyOrderBO.updateSellIsStatus(sell.getId());//更新报盘为关闭状态
 					}
				}
				rltMap.put("data", tempMap.get("contractUrl"));
				
			} else {
				
				rltMap.put("status", "faild");
				
				rltMap.put("msg", tempMap.get("message") == null ? "" : tempMap.get("message"));
				
				rltMap.put("data", null);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rltMap;
	}
		
	/**
	 * 对外生成订单接口
	 */
	public Map<String, Object> insertOrder(List<AttrValue> attrlist,int productid,User user, Order order, String moq, String outmoq,Integer isClose,Integer hasContract,HttpServletRequest request,Integer mallid){
		Map<String, Object> map = new HashMap<String, Object>();
		//确认商品是否存在
		Integer commodityId=null;
		//整理商品属性 判断本库是否存在   不存在调接口同步给商城新增
		Integer comid =check(attrlist, productid);
		if(comid==-1){
			map.put("message", "检测商品异常！");
			return map;
		 }else if (comid==0) {
			 commodityId=null;
		}else {
			commodityId=comid;
		}
		
		//同时本库也新增
		String proName=iBuyOrderBO.selProNameById(productid);
		if (commodityId == null) {// 商品不存在
			String sellAccount = iSellPoolBO.getcompanyAccount(order.getSellId());
			Integer mallcommodityID=producter(attrlist,proName,user,sellAccount);
			if(mallcommodityID==-1){
				map.put("message", "商城添加商品失败！");
				return map;
			}
			commodityId=mallcommodityID;
		}
		Integer coid=  attrValuesBO.getProductIdByComId(commodityId);//商城存在本地不存在
		if(coid==null){
			CommodityEntity commodity = new CommodityEntity();
			commodity.setId(commodityId);// 商品标号商城同步
			commodity.setProdId(String.valueOf(productid));
			commodity.setCatId(user.getItems().getId());
			commodity.setCatName(user.getItems().getName());
			commodity.setName(proName);
			commodity.setCreateTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
			attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
			for(int j=0;j<attrlist.size();j++){//列
				String attrValue = attrlist.get(j).getAttrValue();
				String attrName=attrlist.get(j).getAttrName();
				Integer attrId = attrlist.get(j).getAttrId();
			
						if (attrValue == null || "".equals(attrValue)) {

							continue;
						}
						AttrValue attr =new AttrValue();
						attr.setAttrId(attrId);
						attr.setAttrName(attrName);
						attr.setAttrValue(attrValue);
						attr.setCommodityId(commodityId);
						attr.setCommodityName(proName);
						attr.setCreaTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
						attr.setEditTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));

						attrValuesBO.addCommodityAttr(attr);// 添加商品属性
			}
		}
		
		//商品新增ok   (确认是否存在挂盘id)  没有调接口新建挂盘
		SellPool sell = new SellPool();
		sell.setCommodityId(commodityId);
		sell.setPrice(Double.parseDouble(order.getSubOrderList().get(0).getPrice().toString()));
		sell.setQuantity(order.getSubOrderList().get(0).getQuantity().toString());
		sell.setItemsid(Integer.parseInt(user.getItemId()));
		sell.setCompanyid(order.getSellId());//卖方企业Id
		sell.setUnit(order.getSubOrderList().get(0).getUnit());
		sell.setWareProvince(order.getWare_province());
		sell.setWareId(order.getWareId());
		sell.setStatus(0);
		sell.setCreatedat(new Date());
		sell.setCreatedby(user.getId());
		sell.setUpdatedat(new Date());
		sell.setUpdatedby(user.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sell.setDeliverytime(sdf.format(order.getDeliveryDate()));//交货日期
		sell.setPaytype(order.getPayment());//结算方式  1现款现汇 2银行承兑 3有色网代收代付
		sell.setReceipttype(order.getReceipttype());//付款方式
		sell.setDelivery(order.getDelivery());//交货方式
		sell.setOverflow(order.getPoundDiff().toString());//磅差
		sell.setPriority(0);//优先级
		
		sell.setMoq(moq);
		sell.setOutmoq(outmoq);
		
		Object mailsaleId=null;
		
		if(mailsaleId!=null){
		} else {
			//新建挂盘
			//同时本库也新增卖盘池记录
			String sellAccount = iSellPoolBO.getcompanyAccount(order.getSellId());
			sell.setUsername(sellAccount);
			sell=addsellpoolmall(sell,map);//调用接口  卖盘同步商城 得到挂牌号
			if("1".equals(String.valueOf(sell.getErrno()))||"255".equals(String.valueOf(sell.getErrno()))){
//				map.put("message", "同步卖盘失败");
				return map;
				
			}
			
			sell.setMallUserAccount(sellAccount);//注入商城会员账号
			sell.setSource(1);
			iSellPoolBO.addSellPool(sell, user);
			
		}
		//循环完毕  组装数据 调用接口 生成订单 返回订单Code
				MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
				JSONArray array1 = new JSONArray(); 
			     JSONArray array2 = new JSONArray(); 
			     JSONArray array3 = new JSONArray(); 
			     array1.add(sell.getMallSaleId());
		    	 array2.add(order.getSubOrderList().get(0).getQuantity());
		    	 array3.add(order.getSubOrderList().get(0).getPrice());
			     param.add("mallid",array1.toString());
			     param.add("count", array2.toString());
				param.add("price", array3.toString());
				param.add("warehouseid", order.getWareId());
				String sellAccount = iSellPoolBO.getcompanyAccount(order.getSellId());
				String buyAccount = iSellPoolBO.getcompanyAccount(order.getBuyId());
				param.add("buyer", StringUtil.doEncoder(buyAccount));
				param.add("seller", StringUtil.doEncoder(sellAccount));
				
				if(hasContract==0){//不生成合同
					param.add("type", 1);
					order.setConfirmStatus(0);
					if(isClose==1){
						//提交订单之后关闭新挂盘
						param.add("outsale", 1);
						//并关闭老订单关闭老挂盘
						Map<String, String> params = new HashMap<String, String>();
						params.put("mallid", String.valueOf(mallid));
						params.put("status", "1");
						JSONObject result = null;
						String result_str = "";
						try {
							logger.info("开始调用：撮合挂单下架提交商城接口   参数： "+params.toString());
							
							result_str = URLRequest.post(dealmlmall, params);
							//logger.error("调用商城接口结果=====》" + result_str);
							result = JSONObject.fromObject(result_str);
							if (result != null) {
								logger.info("撮合挂单下架提交商城返回数据"+result.toString());
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							logger.error("调用撮合挂单下架提交商城接口失败");
							e.printStackTrace();
							map.put("msessage", "调用撮合挂单下架提交商城接口失败");
							return map;
						}
						if (result != null) {
							int errno = result.getInt("errno");
							String msg = result.getString("msg");
							if(errno==0){
								logger.error("撮合挂单修改提交商城成功" + msg);
							}else{
								map.put("msessage", "调用撮合挂单下架提交商城接口失败");
								return map;
							}
						}
						
					}else{
						param.add("outsale", 0);
					};	
				}else if(hasContract==1){//生成合同
					param.add("type", 2);		
					order.setConfirmStatus(1);
				}
				
				String code = addOrder(param,map);//调用接口  订单同步到商城  返回订单code
				if("".equals(code)||"0".equals(code)){
					return map;
				}
				
				//使用返回的订单Code 入库到本地库 操作结束
				order.setOrderCode(code);
				order.setTtype(0);//卖盘生成订单
				order.setStatus(0);
				order.setOrderStatus(0);
				order.setCreatedAt(new Date());
				order.setCreatedBy(user.getId());
				order.setUpdatedAt(new Date());
				order.setUpdatedBy(user.getId());
				order.setMallBuyerAccount(buyAccount);
				order.setMallSellerAccount(sellAccount);
				order.setSource(4);//订单来源
				//调用商城接口 下架报盘
//				iSellPoolBO.dealdemall(sell.getMallSaleId());
				
				order.setPoolType(1);
				
				iBuyOrderBO.insertBuyOrder(order);//添加订单
				String contractUrl ="";
				if(hasContract==0){//不生成合同
					contractUrl = "/customerDetail/orderRecord/orderDetail2.do?orderId=" + order.getId();
				}else if(hasContract==1){//生成合同
					contractUrl = "/contract/generateContractByTemplate.do?orderId=" + order.getId();					
				}
				//生成订单时更新客户表里的成交时间
		    	iOrdersBO.updateLastTransTimeByOrderId(Integer.valueOf(order.getId()));
				
				//准备订单项数据
				SubOrder so = order.getSubOrderList().get(0);
				so.setOrderId(order.getId());//加入订单Id
				so.setStatus(0);
				so.setCreatedAt(new Date());
				so.setCreatedBy(user.getId());
				so.setUpdatedAt(new Date());
				so.setUpdatedBy(user.getId());
				
				so.setSellId(sell.getId());//卖盘Id
				so.setCommodityId(commodityId);//商品Id
				so.setMallSaleId(sell.getMallSaleId());//商城挂盘Id
				iBuyOrderBO.insertSubOrder(so);
				map.put("success", "新增成功");
				map.put("contractUrl", contractUrl);
				map.put("orderCode", order.getOrderCode());
				return map;
	}
	
	
	/** 卖盘/买盘生成订单   撮合交易管理选择单个报盘生成订单入口：“撮合订单”方式；生成订单后，报盘要关闭。2016年1月11日下午4:13:19
	 * @Title: insertSellOrder 
	 * @Description: TODO
	 * @param req
	 * @return
	 * @throws ParseException
	 * @author zhangnan/张楠
	 * @return: Map<String,Object>
	 * @createTime 2015年12月21日上午9:10:17
	 * 优化 生成订单之前做黑名单的校验
	 */
	@RequestMapping(value="/insSellOrder")
	@ResponseBody
	public Map<String,Object> insertSellOrder(HttpServletRequest req) throws ParseException{
		
		User user = (User) req.getSession().getAttribute("userInfo");
		String flag = req.getParameter("flag");
		
		String[] productid = req.getParameterValues("productid");
		String[] unit = req.getParameterValues("unit");
		String[] quantitys = req.getParameterValues("quantity");
		String[] prices = req.getParameterValues("price");
		
		String confirmStatus = req.getParameter("confirmStatus");
		String buyId = req.getParameter("buyId");
		String sellId = req.getParameter("sellId");
		String deliveryDate = req.getParameter("deliveryDate");
		
		String wareProvince = req.getParameter("wareProvince");
		String wareId = req.getParameter("wareId");
		String delivery = req.getParameter("delivery");//交货方式
		String poundDiff = req.getParameter("poundDiff");
		String payment = req.getParameter("payment");//结算方式
		String  receipttype=req.getParameter("receipttype");//付款方式
		
		String  sourceId=req.getParameter("sourceId");//来源id
		String  source=req.getParameter("source");//来源
		
		
		String[] attrNames = req.getParameterValues("attrNames");
		String[] attrIds = req.getParameterValues("attrIds");
		Map<String, Object> map = new HashMap<String, Object>();
		
		//检验黑名单关系、、
		Map<String,Object> balckParam = new HashMap<String, Object>();
		
		balckParam.put("customerID",Integer.parseInt(sellId));
		balckParam.put("blackCustomerID",Integer.parseInt(buyId));
		
		String flag1 = this.iBusinessBO.checkOrderListBySell(balckParam);
		
		
		balckParam.put("customerID",Integer.parseInt(buyId));
		balckParam.put("blackCustomerID", Integer.parseInt(sellId));
		
		Integer poolType = Integer.parseInt(req.getParameter("poolType"));
		
		
		String flag2 = this.iBusinessBO.checkOrderListBySell(balckParam);
		
		if (flag1.equals("ERROR") || flag1.equals("ERROR") || flag1.equals("ERROR") || flag2.equals("ERROR") || flag1.equals("ERROR") || flag2.equals("ERROR")) {
			map.put("message", "双方存在黑名单关系，不能生成订单！");
			return map;
		}
		
	//暂时舍弃	
//		//报盘ID
//		String poolId = req.getParameter("poolId");
//		//报盘类型
//		String poolType = req.getParameter("poolType");
//		
//
//		//订单是否已经生成
//		Integer poolStatus = this.iBusinessBO.CheckPoolStatus(Integer.parseInt(poolId),Integer.parseInt(poolType));
//		if (poolStatus == 1) {
//			map.put("message", "本报盘已经生成订单，不能再次生成订单！");
//			return map;
//		}
		SellPool sell = new SellPool();	
		
		List<String[]> list = new ArrayList<String[]>();//存入属性值
		if(attrIds!=null && attrIds.length>0){
			for(int i=0;i<attrIds.length;i++){
			String[] attrValues = req.getParameterValues("attrValues"+attrIds[i]);
			list.add(attrValues);
			}
		}
		
		List<Integer> mailList = new ArrayList<Integer>();//挂盘id
		List<Integer> commodityList = new ArrayList<Integer>();//商品id
		List<Integer> sellList = new ArrayList<Integer>();//卖盘id
		List<List<AttrValue>> attrllist = new ArrayList<List<AttrValue>>();//存入每条商品属性
		//确认商品是否存在
		Integer commodityId=null;
		for(int i=0;i<productid.length;i++){//行
			List<AttrValue> attrlist = new ArrayList<AttrValue>();
			for(int j=0;j<list.size();j++){//列
				AttrValue a = new AttrValue();
				a.setAttrName(attrNames[j]);
				a.setAttrValue( list.get(j)[i]);
				attrlist.add(a);
			}
			attrllist.add(attrlist);
		}
		for(int i=0;i<productid.length;i++){//行
			
			//整理商品属性 判断本库是否存在   不存在调接口同步给商城新增
			Integer comid =check(attrllist.get(i), Integer.valueOf(productid[i]));
			if(comid==-1){
				map.put("message", "检测商品异常！");
				return map;
			 }else if (comid==0) {
				 commodityId=null;
			}else {
				commodityId=comid;
			}
			
			String proName=iBuyOrderBO.selProNameById(Integer.parseInt(productid[i]));
			//同时本库也新增
			if (commodityId == null) {// 商品不存在
				String sellAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(sellId));
				Integer mallcommodityID=producter(attrllist.get(i),proName,user,sellAccount);
				if(mallcommodityID==-1){
					map.put("message", "商城添加商品失败！");
					return map;
				}
				commodityId=mallcommodityID;
			}
			Integer coid=  attrValuesBO.getProductIdByComId(commodityId);//商城存在本地不存在
			if(coid==null){
				CommodityEntity commodity = new CommodityEntity();
				commodity.setId(commodityId);// 商品标号商城同步
				commodity.setProdId(productid[i]);
				commodity.setCatId(user.getItems().getId());
				commodity.setCatName(user.getItems().getName());
				commodity.setName(proName);
				commodity.setCreateTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
				attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
				
				commodityList.add(commodityId);//添加商品id到list
			for(int j=0;j<list.size();j++){//列
				String attrValue = list.get(j)[i];
				
			
						if (attrValue == null || "".equals(attrValue)) {

							continue;
						}
						AttrValue attr =new AttrValue();
						attr.setAttrId(Integer.parseInt(attrIds[j]));
						attr.setAttrName(attrNames[j]);
						attr.setAttrValue(attrValue);
						attr.setCommodityId(commodityId);
						attr.setCommodityName(proName);
						attr.setCreaTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
						attr.setEditTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));

						attrValuesBO.addCommodityAttr(attr);// 添加商品属性
			}

			}else{
				commodityList.add(commodityId);//添加商品id到list
			}
			//商品新增ok   (确认是否存在挂盘id)  没有调接口新建挂盘
			//SellPool sell = new SellPool();
			sell.setCommodityId(commodityId);
			sell.setPrice(Double.parseDouble(prices[i]));
			sell.setQuantity(quantitys[i]);
			sell.setItemsid(Integer.parseInt(user.getItemId()));
			sell.setCompanyid(Integer.parseInt(sellId));//卖方企业Id
			sell.setUnit(Integer.parseInt(unit[i]));
			sell.setWareProvince(Integer.parseInt(wareProvince));
			sell.setWareId(Integer.parseInt(wareId));
			sell.setStatus(0);
			sell.setCreatedat(new Date());
			sell.setCreatedby(user.getId());
			sell.setUpdatedat(new Date());
			sell.setUpdatedby(user.getId());
			sell.setDeliverytime(deliveryDate);//交货日期
			sell.setPaytype(Integer.parseInt(payment));//结算方式  1现款现汇 2银行承兑 3有色网代收代付
			sell.setReceipttype(Integer.parseInt(receipttype));//付款方式
			sell.setDelivery(Integer.parseInt(delivery));//交货方式
			
			
//			sell.setMoq("1");//起订量
//			sell.setOutmoq("1");//超出部分按n递增
			sell.setOverflow(poundDiff);//磅差
			sell.setPriority(0);//优先级
			
//			Map sellmap = iSellPoolBO.selMallSaleIdBy(sell);
//			Object mailsaleId=null;
//			Object sellid=null;
//			if(sellmap!=null){
//				mailsaleId=sellmap.get("mailsaleId");//得到挂盘id
//				sellid=sellmap.get("id");//卖盘池id				
//			}
			
//			if(mailsaleId!=null){
				//存在挂盘id
//				mailList.add((Integer)mailsaleId);//存入list
//				sellList.add((Integer)sellid);//存入list
//			}else{
				//新建挂盘
				//同时本库也新增卖盘池记录
				String sellAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(sellId));
				sell.setUsername(sellAccount);
				sell=addsellpoolmall(sell,map);//调用接口  卖盘同步商城 得到挂牌号
				if("1".equals(String.valueOf(sell.getErrno()))||"255".equals(String.valueOf(sell.getErrno()))){
//					map.put("message", "同步卖盘失败");
					return map;
					
				}
				
				sell.setMallUserAccount(sellAccount);//注入商城会员账号
				sell.setSource(1);
				iSellPoolBO.addSellPool(sell, user);
				sellList.add(sell.getId());//新增卖盘池id到list
				mailList.add(sell.getMallSaleId());//新增挂牌号到list
				
				
				
				//如果是撮合交易管理发送过来的则关闭当前报盘,卖盘关闭
				if (poolType == 1 && Integer.parseInt(source) == 0) {
					iBuyOrderBO.sellPoolClose(Integer.valueOf(sourceId));
				}
				//如果是撮合交易管理的报盘，生成订单的时候关闭此报盘
				if (poolType == 2 && Integer.parseInt(source) == 1){
					iBuyOrderBO.updateBuyIsCloseAndIsConfirm(Integer.valueOf(sourceId));
				}
//				if (poolType == 1 && Integer.parseInt(source) == 0){
//					iBuyOrderBO.updateSellIsCloseAndIsConfirm(Integer.valueOf(sourceId));
//				}
				
		}
		
		//循环完毕  组装数据 调用接口 生成订单 返回订单Code
		MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
	     JSONArray array1 = new JSONArray(); 
	     JSONArray array2 = new JSONArray(); 
	     JSONArray array3 = new JSONArray(); 
	     for(int i=0;i<mailList.size();i++){
	    	 array1.add(mailList.get(i));
	    	 array2.add(Float.valueOf(quantitys[i]));
	    	 array3.add(Float.valueOf(prices[i]));
	     }
	     param.add("mallid", array1.toString());
	     param.add("count", array2.toString());
		 param.add("price", array3.toString());
		 param.add("warehouseid", wareId);
		if(confirmStatus!=null){
			if(confirmStatus.equals("0")){
				param.add("type", 1);
			}else if(confirmStatus.equals("1")){
				param.add("type", 2);
			}
		}
		String sellAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(sellId));
		String buyAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(buyId));
		param.add("buyer", StringUtil.doEncoder(buyAccount));
		param.add("seller", StringUtil.doEncoder(sellAccount));
		
		
		/**
		 * 	一.【客户池/卖盘池/买盘池 > 生成订单】界面，增加“是否质押订单”，“质押款”，“服务费”等字段，并通过接口传给商城.
			逻辑：
			1）	“结算方式”选择“安全支付”时出现“是否质押订单”字段，且未必填.
			2）	“是否质押订单”选择“是”的，出现“质押款”,“服务费”字段，且为输入框必填项.（校验输入是否为数字，且数字≥0）
			3）	提交订单时，检查已填写“质押订单”信息是否选择的是“安全支付”方式。如不是，系统提示错误“质押订单的结算方式必须是安全支付”.
		 */
		if (payment.equals("3")) {
			//是否质押订单
			String ispledge = req.getParameter("ispledge");
			//质押款
			String pledgeprice = req.getParameter("pledgeprice");
			//服务费
			String serviceprice = req.getParameter("serviceprice");
			if (ispledge.equals("1")) {
				param.add("ispledge", ispledge);
				param.add("pledgeprice", pledgeprice);
				param.add("serviceprice", serviceprice);
			}else {
				param.add("ispledge", ispledge);
			}
		}else {
			param.add("ispledge", 0);
			param.add("pledgeprice", null);
			param.add("serviceprice", null);
		}
		
		String code = addOrder(param,map);//调用接口  订单同步到商城  返回订单code
		if("".equals(code)||"0".equals(code)){
//			map.put("message", "同步订单失败");
			return map;
		}
		
		
		/**
		 * 1、 需要撮合人员同当前最高还盘用户确认后，撤消该用户（A）最高还盘，并增加C用户还盘（还盘价格与报盘相同）；系统将会判定为成交，由撮合人员生成订单
		 * 2、 需要为当前用户新增一笔与报盘相同价格的还盘；系统将会判定为成交，而后生成订单
		 */
		
		Map<String,Object> maps  = new HashMap<String, Object>();
		maps.put("buyId", buyId);
		maps.put("sellId", sellId);
		maps.put("poolType", poolType.toString());
		if (req.getParameter("poolId").equals("")) {
			maps.put("poolId", sell.getId());
		}else {
			maps.put("poolId", req.getParameter("poolId"));
		}
		
		
		//订单来源 买卖盘
		maps.put("orderSource", "maimaipan");
		
		this.iBusinessBO.additionalCounter(maps);
		
		
		//使用返回的订单Code 入库到本地库 操作结束
		
		Order o = new Order();
		if (req.getParameter("poolId").equals("")) {
			o.setPoolId(sell.getId());
		}else {
			o.setPoolId(Integer.parseInt(req.getParameter("poolId")));
		}
		
		o.setPoolType(Integer.parseInt(req.getParameter("poolType")));
//		String ordercode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		o.setOrderCode(code);
		o.setItemId(Integer.parseInt(user.getItemId()));
		o.setSellId(Integer.parseInt(sellId));
		o.setBuyId(Integer.parseInt(buyId));
		o.setDelivery(Integer.parseInt(delivery));
		o.setPayment(Integer.parseInt(payment));
		o.setReceipttype(Integer.parseInt(receipttype));
		o.setWare_province(Integer.parseInt(wareProvince));
		o.setWareId(Integer.parseInt(wareId));
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		o.setDeliveryDate(sdf.parse(deliveryDate));
		o.setPoundDiff(Float.parseFloat(poundDiff));
		o.setTtype(0);//卖盘生成订单
		o.setSource(Integer.valueOf(source));
		o.setSourceId(Integer.valueOf(sourceId));
		o.setStatus(0);
		o.setOrderStatus(0);
		o.setCreatedAt(new Date());
		o.setCreatedBy(user.getId());
		o.setUpdatedAt(new Date());
		o.setUpdatedBy(user.getId());
		o.setMallBuyerAccount(buyAccount);
		o.setMallSellerAccount(sellAccount);
		
		o.setConfirmStatus(Integer.valueOf(confirmStatus));
		
		/**
		 * 	一.【客户池/卖盘池/买盘池 > 生成订单】界面，增加“是否质押订单”，“质押款”，“服务费”等字段，并通过接口传给商城.
			逻辑：
			1）	“结算方式”选择“安全支付”时出现“是否质押订单”字段，且未必填.
			2）	“是否质押订单”选择“是”的，出现“质押款”,“服务费”字段，且为输入框必填项.（校验输入是否为数字，且数字≥0）
			3）	提交订单时，检查已填写“质押订单”信息是否选择的是“安全支付”方式。如不是，系统提示错误“质押订单的结算方式必须是安全支付”.
		 */
		if (payment.equals("3")) {
			//是否质押订单
			String ispledge = req.getParameter("ispledge");
			//质押款
			String pledgeprice = req.getParameter("pledgeprice");
			//服务费
			String serviceprice = req.getParameter("serviceprice");
			if (ispledge.equals("1")) {
				o.setIspledge(1);
				o.setPledgeprice(pledgeprice);
				o.setServiceprice(serviceprice);
				param.add("ispledge", ispledge);//是否质押订单  ：1 质押  0 不质押
				param.add("pledgeprice", pledgeprice);//质押款
				param.add("serviceprice", serviceprice);//服务费
			}else {
				o.setIspledge(0);
				o.setPledgeprice(null);
				o.setServiceprice(null);
			}
		}else {
			o.setIspledge(null);
			o.setPledgeprice(null);
			o.setServiceprice(null);
		}
		
		//商品ID
		o.setProductid(productid);
		
		o.setPoolType(poolType);
		
		iBuyOrderBO.insertBuyOrder(o);//添加订单
		// 撮合交易管理选择单个报盘生成订单入口：“撮合订单”方式；生成订单后，报盘要关闭。2016年1月11日下午4:13:19
		if (flag.equals("9")) {
			Map<String,Object> params = new HashMap<String, Object>();
			if (poolType == 1) {
				Integer chSellId = null;
				if (!req.getParameter("poolId").equals("")) {
					chSellId = Integer.parseInt(req.getParameter("poolId"));
				}
				params.put("isClose", 1);
				params.put("isConfirm", 1);//订单已经成交
				params.put("poolId", chSellId);
				this.iBusinessBO.closeSellPool(params);
			}else {
				Integer chBuyId = null;
				chBuyId = Integer.parseInt(req.getParameter("poolId"));
				if (!req.getParameter("poolId").equals("")) {
					chBuyId = Integer.parseInt(req.getParameter("poolId"));
				}
				params.put("isClose", 1);
				params.put("poolId", chBuyId);
				this.iBusinessBO.closeBuyPool(params);
				ChBuyPoolEntity buyPool = this.iBusinessBO.getChBuyPoolEntityById(chBuyId);
				//仓库
				ChWareHouse wareHouse = this.iBusinessBO.getChWareHouse(wareId);
				//还盘企业
				ChCustomsEntity company = this.iBusinessBO.getChCustomsEntity(Integer.parseInt(sellId));
				
				buyPool.setLastprice(buyPool.getPrice());
				buyPool.setWareId(wareId);
				buyPool.setWareName(wareHouse.getName());
				buyPool.setLastcustomerid(Integer.parseInt(sellId));
				buyPool.setLastcustomername(company.getCompanyname());
				buyPool.setLastmallaccount(company.getAccount());
				buyPool.setUpdatedat(new Date());
				buyPool.setLasttime(DateUtil.doFormatDate(new Date(), ""));
				
				this.iBusinessBO.updateBuyPoolById(buyPool);
				
				//“撮合订单”方式 买盘生成订单，要添加一条还盘记录
//				ChPoolPriceEntity poolPrice = new ChPoolPriceEntity();
//				poolPrice.setPoolid(chBuyId);
//				poolPrice.setPricetype(buyPool.getPricetype());
//				poolPrice.setCreatedat(new Date());
//				poolPrice.setPrice(buyPool.getPrice());
//				poolPrice.setCustomerid(Integer.parseInt(sellId));
//				poolPrice.setAccount(company.getAccount());
//				poolPrice.setStatus(0);
//				poolPrice.setUpdatedat(new Date());
//				poolPrice.setPooltype(2);
//				this.iBusinessBO.addPoolPrice(poolPrice);
			}
		}
		
		customerBO.updateremindDate(o.getBuyId());//更新客户下次采购时间
		
		//生成订单时更新客户表里的成交时间
    	iOrdersBO.updateLastTransTimeByOrderId(Integer.valueOf(o.getId()));
    	
		
		for(int i=0;i<list.get(0).length;i++){
			//准备订单项数据
			SubOrder so = new SubOrder();
			so.setOrderId(o.getId());//加入订单Id
			so.setUnit(Integer.parseInt(unit[i]));
			so.setQuantity(Double.parseDouble(quantitys[i]));
			so.setPrice(Double.parseDouble(prices[i]));
//			String guid = GUIDUtil.getGUID();
//			so.setExt(guid);
			so.setStatus(0);
			so.setCreatedAt(new Date());
			so.setCreatedBy(user.getId());
			so.setUpdatedAt(new Date());
			so.setUpdatedBy(user.getId());
			
			so.setSellId(sellList.get(i));//卖盘Id
			so.setCommodityId(commodityList.get(i));//商品Id
			so.setMallSaleId(mailList.get(i));//商城挂盘Id
			//调用商城接口 下架报盘
//			iSellPoolBO.dealdemall(mailList.get(i));
			iBuyOrderBO.insertSubOrder(so);
			
		}
		
		map.put("success", "新增成功");
		map.put("oid", o.getId());
		return map;
	}
	/**
	 * 卖盘池同步到商城  
	 * @param sellPool
	 * @return
	 */
	
	public SellPool addsellpoolmall(SellPool sellPool,Map<String, Object> map){
		
//		String str="http://testmall.smm.cn/Interface/publicitem";
		
		MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
		
		param.add("totalmallid", sellPool.getCommodityId());//商品Id
		param.add("price", sellPool.getPrice());
		param.add("areaid", sellPool.getWareProvince());//地区id
		param.add("count", sellPool.getQuantity());
		param.add("warehouseid", sellPool.getWareId());//仓库id
		param.add("paytype", sellPool.getPaytype());
		param.add("receipttype", sellPool.getReceipttype()); 
		param.add("delivery", sellPool.getDelivery());
		param.add("deliverytime", DateUtil.doSFormatDate(sellPool.getDeliverytime(),"yyyy-MM-dd").getTime()/1000);
		param.add("moq", sellPool.getMoq());
		param.add("outmoq", sellPool.getOutmoq()); 
		param.add("overflow", sellPool.getOverflow());
		param.add("username",StringUtil.doEncoder(sellPool.getUsername()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		param.add("producedate",date);
		param.add("instoragedate",date);
		param.add("catid",sellPool.getItemsid());
		param.add("unit",StringUtil.doEncoder(sellPool.getUnit()==0?"吨":"千克"));
		param.add("sheetnum", sellPool.getQuantity());
		
		try {
			logger.info("开始调用：新增卖盘同步到商城接口   参数："+param.toString());
			
			String jsonString=restTemplate.postForObject(publicitem, param, String.class);
			 Map<String, Object> maps=JSONUtil.doConvertJson2Map(jsonString);			
			if(maps!=null){
				logger.info("新增卖盘同步到商城返回数据："+maps.toString());
				
				if("1".equals(String.valueOf(maps.get("errno")))){//参数错误
					sellPool.setErrno(1);
					map.put("message", "卖盘池同步异常"+maps.get("mallid").toString());
				}
				if("255".equals(String.valueOf(maps.get("errno").toString()))){//参数错误
					sellPool.setErrno(1);
					map.put("message", "卖盘池同步异常"+maps.get("mallid").toString());
				}if("0".equals(String.valueOf(maps.get("errno")))){
					sellPool.setErrno(0);
					sellPool.setMallSaleId(Integer.valueOf((String) maps.get("mallid")));
					map.put("message", "同步商城成功");
				}
				
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				sellPool.setErrno(1);
				map.put("message", "同步商城接口调用异常");
			}
		return sellPool;
	}
	/**
	 * 订单同步到商城  
	 * @param sellPool
	 * @return
	 */
	
	public String addOrder(MultiValueMap<String, Object> param,Map<String, Object> map){
		
				
		String code = "";
		try {
			String jsonString=restTemplate.postForObject(order, param, String.class);
			 Map<String, Object> maps=JSONUtil.doConvertJson2Map(jsonString);
			 
			 logger.info("生成订单同步到商城返回数据 ："+maps.toString());
			 
			//System.err.println(maps); 
			 if("0".equals(String.valueOf(maps.get("errno")))){//成功
					code = maps.get("orderno").toString();//返回编号
					map.put("message",maps.get("msg").toString());
				}else{
					code =  "0";
					map.put("message",maps.get("msg").toString());
				}
					
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return code;
	}
	
	/**
	 * 检查商品是否已存在
	 * @param attrValue
	 * @param prodId
	 * @return
	 */
	public  Integer check(List<AttrValue> attrValue  ,Integer prodId ){
		MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
		for (int i = 0; i < attrValue.size(); i++) {
			if(attrValue.get(i).getAttrValue()!=null && !"".equals(attrValue.get(i).getAttrValue())){
			param.add(StringUtil.doEncoder(attrValue.get(i).getAttrName()),StringUtil.doEncoder(attrValue.get(i).getAttrValue()));
			}
			
		}
		Integer totalmallid=-1;
		param.add("pid", prodId);
		try {
			logger.info("开始调用：验证商品是否存在接口   参数："+param.toString());
		
			String jsonString=restTemplate.postForObject(checkSeller, param, String.class);
			Map<String, Object> maps=JSONUtil.doConvertJson2Map(jsonString);	
			Integer errno=(Integer) maps.get("errno");
			if(maps!=null){
				logger.info("验证商品是否存在返回数据："+maps.toString());
				
				if(1==errno){//参数错误
					totalmallid= -1;
				} else if(0==errno){
					Integer msg=(Integer) maps.get("msg");
					if(msg==1){
						 totalmallid=Integer.valueOf(String.valueOf(maps.get("totalmallid")));
						
					}else {
						totalmallid= 0;	
					}
					
					
				}else {
					totalmallid= -1;
				}
				
				}
	
		} catch (Exception e) {
			e.printStackTrace();
			totalmallid= -1;
		}
		
		return  totalmallid;
	}
	
	
	/**
	 * 添加商城商品接口
	 * @param attrValue
	 * @param prodName
	 * @param user
	 * @return
	 */
	@Transactional
	public  Integer producter(List<AttrValue> attrValue  ,String prodName ,User user,String account){
		MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
		
		param.add("catid",user.getItems().getId());
		param.add("catname",StringUtil.doEncoder(user.getItems().getName()));
		param.add("name",StringUtil.doEncoder(prodName));
		param.add("username",StringUtil.doEncoder(account));
		for (int i = 0; i < attrValue.size(); i++) {
			if(attrValue.get(i).getAttrValue()!=null && !"".equals(attrValue.get(i).getAttrValue())){
			param.add(StringUtil.doEncoder(attrValue.get(i).getAttrName()),StringUtil.doEncoder(attrValue.get(i).getAttrValue()));
			}
			
		}
	
		 try {		 
			 logger.info("开始调用：添加商品接口   参数："+param.toString());
			 
				String jsonString=restTemplate.postForObject(producter, param, String.class);
				 Map<String, Object> maps=JSONUtil.doConvertJson2Map(jsonString);
				// System.err.println(StringUtil.doDecoder(maps.toString()));
				 Integer errno=(Integer) maps.get("errno");
			
				 if(maps!=null){
					 logger.info("添加商品返回数据："+maps.toString());
						if(1==errno){//参数错误
							System.err.println("添加商品接口异常："+maps.get("msg"));
							return -1;
						}
						else if(255==errno){//未知错误
							System.err.println("添加商品接口异常："+maps.get("msg"));
							return -1;
						} else if(0==errno){//正常
							String  totalmallid =String.valueOf(maps.get("mallid"));
							return Integer.valueOf(totalmallid);
						}
						
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						return -1;
						
					}
		return null;
	}
	
	/** 撮合交易管理》》买盘池生成订单
	 * @Title: toaddbuyorder 
	 * @Description: TODO
	 * @param ids
	 * @return
	 * @author zhangnan/张楠
	 * @return: ModelAndView
	 * @createTime 2015年12月21日上午10:31:35
	 */
	@RequestMapping(value = "/toaddbuyorder")
	public ModelAndView toaddbuyorder(String ids) {
		ModelAndView view = new ModelAndView("business/addbuyorder");
		int id = Integer.parseInt(ids);
		BuyPoolEntity buypool = buypoolbo.getbuypoolID(id);
		List<ItemAttr> attrs = iBuyOrderBO
				.getMainItemAttr(buypool.getItemsID());
		List<Areas> arealist = iAreasBO.getParentAreas();
		view.addObject("arealist", arealist);
		view.addObject("buypool", buypool);
		for (ItemAttr a : attrs) {
			List<String> strlist = new ArrayList<String>();
			String[] strs = a.getOptions().trim().split(",");
			if (strs.length > 0) {
				for (int i = 0; i < strs.length; i++) {
					strlist.add(strs[i]);
				}
			}
			a.setOptionsValue(strlist);
		}
		List<ProductEntity> prolist = attrValuesBO.getproductitemId(buypool
				.getItemsID());
		List<AttrValue> attrValuesList = buypoolbo.selbuyAttrValueByBuyId(id);
		if (attrValuesList.size() > 0) {
			view.addObject("proId", attrValuesList.get(0).getProdId());
		}
		view.addObject("attrValuesList", attrValuesList);
		view.addObject("prolist", prolist);
		view.addObject("attrs", attrs);
		
		view.addObject("poolId", ids);
		view.addObject("poolType", 2);

		view.addObject("sourceId", id);// 传回id
		view.addObject("source", 1);// 订单来源
		return view;
	}
	
	/** 撮合交易管理》》》准备生成卖盘订单数据
	 * @Title: toaddsellorder 
	 * @Description: TODO
	 * @param sid
	 * @return
	 * @author zhangnan/张楠
	 * @return: ModelAndView
	 * @createTime 2015年12月21日上午10:17:06
	 */
	@RequestMapping(value = "/toaddsellorder")
	public ModelAndView toaddsellorder(String sid) {
		ModelAndView view = new ModelAndView("business/addSellorder");
		int id = Integer.parseInt(sid);
		SellPool sellpool = iSellPoolBO.getSellPoolById(id);
		List<ItemAttr> attrs = iBuyOrderBO.getMainItemAttr(sellpool
				.getItemsid());
		List<Areas> arealist = iAreasBO.getParentAreas();
		List<ProductEntity> prolist = attrValuesBO.getproductitemId(sellpool
				.getItemsid());
		view.addObject("arealist", arealist);
		view.addObject("sellpool", sellpool);
		for (ItemAttr a : attrs) {
			List<String> strlist = new ArrayList<String>();
			String[] strs = a.getOptions().trim().split(",");
			if (strs.length > 0) {
				for (int i = 0; i < strs.length; i++) {
					strlist.add(strs[i]);
				}
			}
			a.setOptionsValue(strlist);
		}
		List<AttrValue> attrValuesList = attrValuesBO
				.getAttrValuesByExtId(sellpool.getCommodityId());
		int proId = attrValuesBO.getProductIdByComId(sellpool.getCommodityId());
		view.addObject("proId", proId);
		view.addObject("attrValuesList", attrValuesList);
		view.addObject("attrs", attrs);
		view.addObject("prolist", prolist);
		
		view.addObject("poolId", sid);
		view.addObject("poolType", 1);
		

		view.addObject("sourceId", id);// 传回id
		view.addObject("source", 0);// 订单来源
		return view;
	}

	/**
	 * 生成撮合订单准备数据
	 * 
	 */
	@RequestMapping(value = "/toOrder")
	public ModelAndView toOrder(String value) {
		String[] ss =value.split("\\$");
		Integer sellId=-1;
		Integer buyId=-1;
		String poolId="";//存放规则   "卖盘ID,买盘id"
		for(int i=0;i<ss.length;i++){
			String[] tm = ss[i].split("\\|");
			if(tm[1].equals("1")){//卖盘
				sellId = Integer.valueOf(tm[0]);
			}else if(tm[1].equals("2")){//买盘
				buyId = Integer.valueOf(tm[0]);
			}
		}
		poolId=sellId+","+buyId;
		ModelAndView view = new ModelAndView("business/addSellorder");
		SellPool sellpool = iSellPoolBO.getSellPoolById(sellId);
		BuyPoolEntity buypool = buypoolbo.getbuypoolID(buyId);
		sellpool.setBuyCompanyId(buypool.getCustomerId());
		sellpool.setBuyCompanyNm(buypool.getCompanyname());
		sellpool.setBuyCompanyNms(buypool.getCompanyname());
		
		List<ItemAttr> attrs = iBuyOrderBO.getMainItemAttr(sellpool
				.getItemsid());
		List<Areas> arealist = iAreasBO.getParentAreas();
		List<ProductEntity> prolist = attrValuesBO.getproductitemId(sellpool
				.getItemsid());
		view.addObject("arealist", arealist);
		view.addObject("sellpool", sellpool);
		for (ItemAttr a : attrs) {
			List<String> strlist = new ArrayList<String>();
			String[] strs = a.getOptions().trim().split(",");
			if (strs.length > 0) {
				for (int i = 0; i < strs.length; i++) {
					strlist.add(strs[i]);
				}
			}
			a.setOptionsValue(strlist);
		}
		List<AttrValue> attrValuesList = attrValuesBO
				.getAttrValuesByExtId(sellpool.getCommodityId());
		int proId = attrValuesBO.getProductIdByComId(sellpool.getCommodityId());
		view.addObject("proId", proId);
		view.addObject("attrValuesList", attrValuesList);
		view.addObject("attrs", attrs);
		view.addObject("prolist", prolist);
		view.addObject("poolId", sellId);
		//买家
		view.addObject("poolId_buyer", buyId);
		view.addObject("poolType", 1);

		view.addObject("sourceId", poolId);// 传回id
		view.addObject("source", 5);// 订单来源
		return view;
	}
	
	
	
	/** 
	 * 新增逻辑： 在还盘有多个的情况下，撮合人员可以帮不是最高还盘企业生成订单 2016年1月11日下午3:40:12 
	 * 还盘直接生成订单准备数据
	 * @Title: toAddOrder 
	 * @Description: TODO
	 * @param id
	 * @param poolId
	 * @param poolType
	 * @return
	 * @author zhangnan/张楠
	 * @return: ModelAndView
	 * @createTime 2016年1月11日下午3:40:12
	 */
	@RequestMapping(value="/toAddOrder")
	public ModelAndView toAddOrder(String id,String poolId,String poolType){
		if(poolType!=null&&poolType.equals("1")){//卖盘
			ModelAndView view = new ModelAndView("business/addSellorder");
			int pid = Integer.parseInt(poolId);
			SellPool sellpool = iSellPoolBO.getSellPoolById(pid);
			Customer cus = iBuyOrderBO.queryCompanyInfo(Integer.valueOf(id));
			sellpool.setBuyCompanyId(cus.getId());
			sellpool.setBuyCompanyNms(cus.getCompanyName());
			
			List<ItemAttr> attrs = iBuyOrderBO.getMainItemAttr(sellpool
					.getItemsid());
			List<Areas> arealist = iAreasBO.getParentAreas();
			List<ProductEntity> prolist = attrValuesBO.getproductitemId(sellpool
					.getItemsid());
			view.addObject("arealist", arealist);
			view.addObject("sellpool", sellpool);
			for (ItemAttr a : attrs) {
				List<String> strlist = new ArrayList<String>();
				String[] strs = a.getOptions().trim().split(",");
				if (strs.length > 0) {
					for (int i = 0; i < strs.length; i++) {
						strlist.add(strs[i]);
					}
				}
				a.setOptionsValue(strlist);
			}
			List<AttrValue> attrValuesList = attrValuesBO
					.getAttrValuesByExtId(sellpool.getCommodityId());
			int proId = attrValuesBO.getProductIdByComId(sellpool.getCommodityId());
			view.addObject("proId", proId);
			view.addObject("attrValuesList", attrValuesList);
			view.addObject("attrs", attrs);
			view.addObject("prolist", prolist);
			view.addObject("poolType", poolType);
			view.addObject("poolId", poolId);

			view.addObject("sourceId", poolId);// 传回报盘id
			view.addObject("source", 0);// 订单来源 0：从卖盘生成
			return view;
		}else{//买盘
			ModelAndView view = new ModelAndView("business/addbuyorder");
			int pid = Integer.parseInt(poolId);
			BuyPoolEntity buypool = buypoolbo.getbuypoolID(pid);
			Customer cus = iBuyOrderBO.queryCompanyInfo(Integer.valueOf(id));
			buypool.setLastCustomerId(cus.getId());
			buypool.setLastCustomerName(cus.getCompanyName());
			buypool.setPrice(cus.getPrice().toString());
			
			List<ItemAttr> attrs = iBuyOrderBO
					.getMainItemAttr(buypool.getItemsID());
			List<Areas> arealist = iAreasBO.getParentAreas();
			view.addObject("arealist", arealist);
			view.addObject("buypool", buypool);
			for (ItemAttr a : attrs) {
				List<String> strlist = new ArrayList<String>();
				String[] strs = a.getOptions().trim().split(",");
				if (strs.length > 0) {
					for (int i = 0; i < strs.length; i++) {
						strlist.add(strs[i]);
					}
				}
				a.setOptionsValue(strlist);
			}
			List<ProductEntity> prolist = attrValuesBO.getproductitemId(buypool
					.getItemsID());
			List<AttrValue> attrValuesList = buypoolbo.selbuyAttrValueByBuyId(pid);
			if (attrValuesList.size() > 0) {
				view.addObject("proId", attrValuesList.get(0).getProdId());
			}
			view.addObject("attrValuesList", attrValuesList);
			view.addObject("prolist", prolist);
			view.addObject("attrs", attrs);
			view.addObject("poolType", poolType);
			view.addObject("poolId", poolId);

			view.addObject("sourceId", poolId);// 传回报盘id
			view.addObject("source", 1);// 订单来源 1:从买盘生成
			return view;
		}
	}
	
	
	/** 撮合交易生成订单
	 * @Title: createOrder 
	 * @Description: TODO
	 * @param req
	 * @return
	 * @throws ParseException
	 * @author zhangnan/张楠
	 * @return: Map<String,Object>
	 * @createTime 2015年12月21日上午10:13:55
	 * 生成订单之前要做是否报盘已经生成了订单的重复校验、买卖双方是否存在黑名单关系的校验
	 */
	@RequestMapping(value="/createOrder")
	@ResponseBody
	public Map<String,Object> createOrder(HttpServletRequest req) throws ParseException{
		
		User user = (User) req.getSession().getAttribute("userInfo");
		
		
		String[] productid = req.getParameterValues("productid");
		String[] unit = req.getParameterValues("unit");
		String[] quantitys = req.getParameterValues("quantity");
		String[] prices = req.getParameterValues("price");
		
		String confirmStatus = req.getParameter("confirmStatus");
		String buyId = req.getParameter("buyId");
		String sellId = req.getParameter("sellId");
		String deliveryDate = req.getParameter("deliveryDate");
		
		String wareProvince = req.getParameter("wareProvince");
		String wareId = req.getParameter("wareId");
		String delivery = req.getParameter("delivery");//交货方式
		String poundDiff = req.getParameter("poundDiff");
		String payment = req.getParameter("payment");//结算方式
		String  receipttype=req.getParameter("receipttype");//付款方式
		
		String  sourceId=req.getParameter("sourceId");//来源id
		String  source=req.getParameter("source");//来源
		
		//Integer poolType_order = Integer.parseInt(req.getParameter("poolType"));
		//Integer poolId_order = Integer.parseInt(req.getParameter("poolId"));
		
		
		
		//报盘ID
		String poolId = req.getParameter("poolId");
		//报盘类型
		String poolType = req.getParameter("poolType");
		
		
		String[] attrNames = req.getParameterValues("attrNames");
		String[] attrIds = req.getParameterValues("attrIds");
		Map<String, Object> map = new HashMap<String, Object>();
		

		//订单是否已经生成
		Integer poolStatus = this.iBusinessBO.CheckPoolStatus(Integer.parseInt(poolId),Integer.parseInt(poolType));
		if (poolStatus == 1) {
			map.put("message", "本报盘已经生成订单，不能再次生成订单！");
			return map;
		}
		
		
		//检验黑名单关系
		Map<String,Object> balckParam = new HashMap<String, Object>();
		
		balckParam.put("customerID", Integer.parseInt(sellId));
		balckParam.put("blackCustomerID", Integer.parseInt(buyId));
		
		String flag1 = this.iBusinessBO.checkOrderListBySell(balckParam);
		
		
		balckParam.put("customerID",Integer.parseInt(buyId));
		balckParam.put("blackCustomerID", Integer.parseInt(sellId));
		
		String flag2 = this.iBusinessBO.checkOrderListBySell(balckParam);
		
		if (flag1.equals("ERROR") || flag1.equals("ERROR") || flag1.equals("ERROR") || flag2.equals("ERROR") || flag1.equals("ERROR") || flag2.equals("ERROR")) {
			map.put("message", "双方存在黑名单关系，不能生成订单");
			return map;
		}
		
		List<String[]> list = new ArrayList<String[]>();//存入属性值
		if(attrIds!=null && attrIds.length>0){
			for(int i=0;i<attrIds.length;i++){
			String[] attrValues = req.getParameterValues("attrValues"+attrIds[i]);
			list.add(attrValues);
			}
		}
		
		List<Integer> mailList = new ArrayList<Integer>();//挂盘id
		List<Integer> commodityList = new ArrayList<Integer>();//商品id
		List<Integer> sellList = new ArrayList<Integer>();//卖盘id
		List<List<AttrValue>> attrllist = new ArrayList<List<AttrValue>>();//存入每条商品属性
		//确认商品是否存在
		Integer commodityId=null;
		for(int i=0;i<productid.length;i++){//行
			List<AttrValue> attrlist = new ArrayList<AttrValue>();
			for(int j=0;j<list.size();j++){//列
				AttrValue a = new AttrValue();
				a.setAttrName(attrNames[j]);
				a.setAttrValue( list.get(j)[i]);
				attrlist.add(a);
			}
			attrllist.add(attrlist);
		}
		for(int i=0;i<productid.length;i++){//行
			
			//整理商品属性 判断本库是否存在   不存在调接口同步给商城新增
			Integer comid =check(attrllist.get(i), Integer.valueOf(productid[i]));
			if(comid==-1){
				map.put("message", "检测商品异常！");
				return map;
			 }else if (comid==0) {
				 commodityId=null;
			}else {
				commodityId=comid;
			}
			
			String proName=iBuyOrderBO.selProNameById(Integer.parseInt(productid[i]));
			//同时本库也新增
			if (commodityId == null) {// 商品不存在
				String sellAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(sellId));
				Integer mallcommodityID=producter(attrllist.get(i),proName,user,sellAccount);
				if(mallcommodityID==-1){
					map.put("message", "商城添加商品失败！");
					return map;
				}
				commodityId=mallcommodityID;
			}
			Integer coid=  attrValuesBO.getProductIdByComId(commodityId);//商城存在本地不存在
			if(coid==null){
				CommodityEntity commodity = new CommodityEntity();
				commodity.setId(commodityId);// 商品标号商城同步
				commodity.setProdId(productid[i]);
				commodity.setCatId(user.getItems().getId());
				commodity.setCatName(user.getItems().getName());
				commodity.setName(proName);
				commodity.setCreateTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
				attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
				
				commodityList.add(commodityId);//添加商品id到list
			for(int j=0;j<list.size();j++){//列
				String attrValue = list.get(j)[i];
				
			
						if (attrValue == null || "".equals(attrValue)) {

							continue;
						}
						AttrValue attr =new AttrValue();
						attr.setAttrId(Integer.parseInt(attrIds[j]));
						attr.setAttrName(attrNames[j]);
						attr.setAttrValue(attrValue);
						attr.setCommodityId(commodityId);
						attr.setCommodityName(proName);
						attr.setCreaTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
						attr.setEditTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));

						attrValuesBO.addCommodityAttr(attr);// 添加商品属性
			}

			}else{
				commodityList.add(commodityId);//添加商品id到list
			}
			//商品新增ok   (确认是否存在挂盘id)  没有调接口新建挂盘
			SellPool sell = new SellPool();
			
			sell.setCommodityId(commodityId);
			sell.setPrice(Double.parseDouble(prices[i]));
			sell.setQuantity(quantitys[i]);
			sell.setItemsid(Integer.parseInt(user.getItemId()));
			sell.setCompanyid(Integer.parseInt(sellId));//卖方企业Id
			sell.setUnit(Integer.parseInt(unit[i]));
			sell.setWareProvince(Integer.parseInt(wareProvince));
			sell.setWareId(Integer.parseInt(wareId));
			sell.setStatus(0);
			sell.setCreatedat(new Date());
			sell.setCreatedby(user.getId());
			sell.setUpdatedat(new Date());
			sell.setUpdatedby(user.getId());
			
			sell.setDeliverytime(deliveryDate);//交货日期
			sell.setPaytype(Integer.parseInt(payment));//结算方式  1现款现汇 2银行承兑 3有色网代收代付
			sell.setReceipttype(Integer.parseInt(receipttype));//付款方式
			sell.setDelivery(Integer.parseInt(delivery));//交货方式
//			sell.setMoq("1");//起订量
//			sell.setOutmoq("1");//超出部分按n递增
			sell.setOverflow(poundDiff);//磅差
			sell.setPriority(0);//优先级
			
			
			//同时本库也新增卖盘池记录
			String sellAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(sellId));
			sell.setUsername(sellAccount);
			
			
//			Map sellmap = iSellPoolBO.selMallSaleIdBy(sell);
			Object mailsaleId=-1;
//			Object sellid=null;
//			if(sellmap!=null){
//				mailsaleId=sellmap.get("mailsaleId");//得到挂盘id
//				sellid=sellmap.get("id");//卖盘池id				
//			}
			
			if(source!=null&&source.trim().equals("0")){
				mailsaleId = iBuyOrderBO.queryMallSaleIdByPoolId(Integer.valueOf(sourceId));//查询卖盘对应挂盘id
				if(mailsaleId == null){//如果挂盘id不存在 则去商城新建一个挂盘
					
					sell=addsellpoolmall(sell,map);//调用接口  卖盘同步商城 得到挂牌号
					if("1".equals(String.valueOf(sell.getErrno()))||"255".equals(String.valueOf(sell.getErrno()))){
						return map;
					}
					mailsaleId = sell.getMallSaleId();
				}
				//修改报盘信息 并同步修改商城报盘信息
				sell.setId(Integer.parseInt(sourceId));
				if(!iBuyOrderBO.updatePool(sell,mailsaleId)){
					map.put("message", "修改报盘信息失败！");
					return map;
				}
				
				//卖盘生成订单  修改卖盘为已关闭
				iBuyOrderBO.updateSellIsClose(Integer.valueOf(sourceId));//修改卖盘的关闭状态
				mailList.add((Integer)mailsaleId);//存入list
				sellList.add(Integer.valueOf(sourceId));//存入list
			}else if(source!=null&&source.trim().equals("1")){
				//新建挂盘
//				//同时本库也新增卖盘池记录
//				String sellAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(sellId));
//				sell.setUsername(sellAccount);
				sell=addsellpoolmall(sell,map);//调用接口  卖盘同步商城 得到挂牌号
				if("1".equals(String.valueOf(sell.getErrno()))||"255".equals(String.valueOf(sell.getErrno()))){
//					map.put("message", "同步卖盘失败");
					return map;
					
				}
				sell.setMallUserAccount(sellAccount);//注入商城会员账号
				sell.setSource(1);
				iSellPoolBO.addSellPool(sell, user);
				//卖盘生成订单  修改卖盘为已关闭
				iBuyOrderBO.updateSellIsClose(sell.getId());//修改卖盘的关闭状态
				iBuyOrderBO.updateBuyIsClose(Integer.valueOf(sourceId));//修改买盘的关闭状态
				sellList.add(sell.getId());//新增卖盘池id到list
				mailList.add(sell.getMallSaleId());//新增挂牌号到list
			}else if(source!=null&&source.trim().equals("5")){
				String[] str=sourceId.split(",");
				sell.setId(Integer.valueOf(str[0].toString()));
				mailsaleId = iBuyOrderBO.queryMallSaleIdByPoolId(Integer.valueOf(sell.getId()));//查询卖盘对应挂盘id
				if(mailsaleId == null){//如果挂盘id不存在 则去商城新建一个挂盘
					sell=addsellpoolmall(sell,map);//调用接口  卖盘同步商城 得到挂牌号
					if("1".equals(String.valueOf(sell.getErrno()))||"255".equals(String.valueOf(sell.getErrno()))){
						return map;
					}
					mailsaleId = sell.getMallSaleId();
				}
				//修改报盘信息 并同步修改商城报盘信息
				if(!iBuyOrderBO.updatePool(sell,mailsaleId)){
					map.put("message", "修改报盘信息失败！");
					return map;
				}
				//卖盘生成订单  修改卖盘为已关闭
				iBuyOrderBO.updateSellIsClose(Integer.parseInt(str[0].toString()));//修改卖盘的关闭状态
				iBuyOrderBO.updateBuyIsClose(Integer.valueOf(str[1].toString()));//修改买盘的关闭状态
				sellList.add(sell.getId());//新增卖盘池id到list
				mailList.add((Integer)mailsaleId);//新增挂牌号到list
			}
		}
		
		//循环完毕  组装数据 调用接口 生成订单 返回订单Code
		MultiValueMap<String, Object> param=new LinkedMultiValueMap<>();
	     JSONArray array1 = new JSONArray(); 
	     JSONArray array2 = new JSONArray(); 
	     JSONArray array3 = new JSONArray(); 
	     for(int i=0;i<mailList.size();i++){
	    	 array1.add(mailList.get(i));
	    	 array2.add(Float.valueOf(quantitys[i]));
	    	 array3.add(Float.valueOf(prices[i]));
	     }
	     param.add("mallid", array1.toString());
	     param.add("count", array2.toString());
		param.add("price", array3.toString());
		param.add("warehouseid", wareId);
		if(confirmStatus!=null){
			if(confirmStatus.equals("0")){
				param.add("type", 1);
			}else if(confirmStatus.equals("1")){
				param.add("type", 2);
			}
		}
		String sellAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(sellId));
		String buyAccount = iSellPoolBO.getcompanyAccount(Integer.parseInt(buyId));
		param.add("buyer", StringUtil.doEncoder(buyAccount));
		param.add("seller", StringUtil.doEncoder(sellAccount));
		
		logger.info("开始调用：生成订单同步到商城  参数："+param.toString());
		String code = addOrder(param,map);//调用接口  订单同步到商城  返回订单code
		if("".equals(code)||"0".equals(code)){
//			map.put("message", "同步订单失败");
			return map;
		}
		
		
		
		/**
		 * 1、 需要撮合人员同当前最高还盘用户确认后，撤消该用户（A）最高还盘，并增加C用户还盘（还盘价格与报盘相同）；系统将会判定为成交，由撮合人员生成订单
		 * 2、 需要为当前用户新增一笔与报盘相同价格的还盘；系统将会判定为成交，而后生成订单
		 */
		
//		//报盘ID
//		String poolId = req.getParameter("poolId");
//		//报盘类型
//		String poolType = req.getParameter("poolType");
//		poolType
		Map<String,Object> params  = new HashMap<String, Object>();
		//报盘为卖盘
		params.put("buyId", buyId);
		params.put("sellId", sellId);
		params.put("poolType", poolType);
		params.put("poolId", poolId);
		//订单来源 撮合交易管理
		params.put("orderSource", "cuohejiaoyi");
		
		//"撮合订单" 选择买卖盘 生成订单  买盘也要加入最后还盘信息
		String poolId_buyer = req.getParameter("poolId_buyer");
		if (poolId_buyer != null && !poolId_buyer.equals("")) {
			params.put("poolId_buyer", req.getParameter("poolId_buyer"));
		}
		
		this.iBusinessBO.additionalCounter(params);
		
		
		//使用返回的订单Code 入库到本地库 操作结束
		
		Order o = new Order();
//		String ordercode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		o.setOrderCode(code);
		o.setItemId(Integer.parseInt(user.getItemId()));
		o.setSellId(Integer.parseInt(sellId));
		o.setBuyId(Integer.parseInt(buyId));
		o.setDelivery(Integer.parseInt(delivery));
		o.setPayment(Integer.parseInt(payment));
		o.setReceipttype(Integer.parseInt(receipttype));
		o.setWare_province(Integer.parseInt(wareProvince));
		o.setWareId(Integer.parseInt(wareId));
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		o.setDeliveryDate(sdf.parse(deliveryDate));
		o.setPoundDiff(Float.parseFloat(poundDiff));
		o.setTtype(0);//卖盘生成订单
		o.setSource(Integer.valueOf(source));
		if(sourceId.indexOf(",") != -1){ 
			String[] str=sourceId.split(",");
		 	o.setSourceId(Integer.valueOf(str[0].toString()));
		} else{
			o.setSourceId(Integer.valueOf(sourceId));
		}
		o.setStatus(0);
		o.setOrderStatus(0);
		o.setCreatedAt(new Date());
		o.setCreatedBy(user.getId());
		o.setUpdatedAt(new Date());
		o.setUpdatedBy(user.getId());
		o.setMallBuyerAccount(buyAccount);
		o.setMallSellerAccount(sellAccount);
		
		o.setConfirmStatus(Integer.valueOf(confirmStatus));
		
		//产品id
		o.setProductid(productid);
		//报盘类型
		//o.setPoolType(poolType_order);
		//报盘ID
		//o.setPoolId(poolId_order);
		if (!poolId.equals("")) {
			o.setPoolId(Integer.parseInt(poolId));
		}
		
		o.setPoolType(Integer.parseInt(poolType));
		iBuyOrderBO.insertBuyOrder(o);//添加订单
		customerBO.updateremindDate(o.getBuyId());//更新客户下次采购时间
		
		//生成订单时更新客户表里的成交时间
    	iOrdersBO.updateLastTransTimeByOrderId(Integer.valueOf(o.getId()));
    	
		
		for(int i=0;i<list.get(0).length;i++){
			//准备订单项数据
			SubOrder so = new SubOrder();
			so.setOrderId(o.getId());//加入订单Id
			so.setUnit(Integer.parseInt(unit[i]));
			so.setQuantity(Double.parseDouble(quantitys[i]));
			so.setPrice(Double.parseDouble(prices[i]));
//			String guid = GUIDUtil.getGUID();
//			so.setExt(guid);
			so.setStatus(0);
			so.setCreatedAt(new Date());
			so.setCreatedBy(user.getId());
			so.setUpdatedAt(new Date());
			so.setUpdatedBy(user.getId());
			
			so.setSellId(sellList.get(i));//卖盘Id
			so.setCommodityId(commodityList.get(i));//商品Id
			so.setMallSaleId(mailList.get(i));//商城挂盘Id
			//调用商城接口 下架报盘
//			iSellPoolBO.dealdemall(mailList.get(i));
			iBuyOrderBO.insertSubOrder(so);
			
		}
		
		map.put("success", "新增成功");
		map.put("oid", o.getId());
		return map;
	}
	
	public String getCheckSeller() {
		return checkSeller;
	}

	public void setCheckSeller(String checkSeller) {
		this.checkSeller = checkSeller;
	}
	
	
	public static void main(String[] args) {
		//连接本地的 Redis 服务
	      Jedis jedis = new Jedis("localhost");
	      System.out.println("Connection to server sucessfully");
	      //存储数据到列表中
	      jedis.lpush("tutorial-list", "Redis");
	      jedis.lpush("tutorial-list", "Mongodb");
	      jedis.lpush("tutorial-list", "Mysql");
	     // 获取存储的数据并输出
	     List<String> list = jedis.lrange("tutorial-list", 0 ,5);
	     for(int i=0; i<list.size(); i++) {
	       System.out.println("Stored string in redis:: "+list.get(i));
	     }
	     
	     //连接本地的 Redis 服务
	      Jedis jedis1 = new Jedis("localhost");
	      System.out.println("Connection to server sucessfully");
	      
	     // 获取数据并输出
	     Set<String> list1 = jedis1.keys("*");
	     for(int i=0; i<list.size(); i++) {
	       System.out.println("List of stored keys:: "+list1.iterator());
	     }
	 }
}
