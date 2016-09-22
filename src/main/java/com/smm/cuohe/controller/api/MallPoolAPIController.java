package com.smm.cuohe.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.CounterOfferBo;
import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.IBusinessBO;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.bo.impl.BusinessBO;
import com.smm.cuohe.bo.notice.NoticeBo;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.BuyPoolAttr;
import com.smm.cuohe.domain.CounterOfferEntity;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.PageParameter;
import com.smm.cuohe.domain.PoolApiEntity;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.SysNotice;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.CommodityEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;

import net.sf.json.JSONArray;

/**
 * 撮合 2.0提供商城接口
 * 
 * @author tantaigen
 *
 */
@Controller
@RequestMapping(value = "mallPoolAPI")
public class MallPoolAPIController {
	private Logger logger = Logger.getLogger(MallPoolAPIController.class);
	
	@Resource
	private ISellPoolBO sellPollBo;
	@Resource
	private BuyPoolBo buyPoolBo;
	@Resource
	private CounterOfferBo counterOfferBo;
	@Resource
	private IAttrValuesBO attrValuesBO;
	@Resource
	private RestTemplate restTemplate;
	@Value("#{ch['publicitem.URL']}")
	private String publicitem;
	@Value("#{ch['checkSeller.URL']}")
	private String checkSeller;
	@Value("#{ch['producter.URL']}")
	private String producter;
	@Resource
	private BusinessBO businessBO ;  
	@Autowired
	private ICustomerBO customerBo;
	@Autowired
	private NoticeBo noticeBo;
	
	@Resource
	IBusinessBO iBusinessBo;
	/**
	 * 发布报盘
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addBuyOrSellPool")
	@ResponseBody
	public Map<String, Object> addPool(@RequestBody Map<String, Object> mapper) {
		Map<String, Object> map = new HashMap<>();
		String code = "ok";
		String msg = "数据插入成功";
		PoolApiEntity pool=new PoolApiEntity();
		try {
			if(mapper.get("poolType")!=null){
				pool.setPoolType(Integer.parseInt(mapper.get("poolType").toString())); 
			}
			if(mapper.get("itemId")!=null){
				pool.setItemId(Integer.parseInt(mapper.get("itemId").toString()));
				if(mapper.get("price")!=null){
					Map<String, Object> mape=businessBO.compare(mapper);
					if(mape.get("code").equals("ok")){
						pool.setPrice(Float.parseFloat(mapper.get("price").toString()));
					}else{
						map.put("code", "faild");
						map.put("msg", "价格参数错误");
						return map;
					}
				}
			}
			if(mapper.get("itemName")!=null){
				pool.setItemName(mapper.get("itemName").toString());
			}
			if(mapper.get("priceType")!=null){
				pool.setPriceType(Integer.parseInt(mapper.get("priceType").toString()));
			}
			if(mapper.get("receipttype")!=null){
				pool.setReceipttype(mapper.get("receipttype").toString());
			}
			if(mapper.get("productId")!=null){
				pool.setProductId(Integer.parseInt(mapper.get("productId").toString()));
			}
			if(mapper.get("productName")!=null){
				pool.setProductName( mapper.get("productName").toString());
			}
			if(mapper.get("count")!=null){
				pool.setCount(Double.valueOf(mapper.get("count").toString()));
			}
			if(mapper.get("unit")!=null){
				pool.setUnit(Integer.parseInt(mapper.get("unit").toString()));
			}
			if(mapper.get("commodityAttr")!=null){
				pool.setAttr(mapper.get("commodityAttr").toString());
			}
			if(mapper.get("areaId")!=null){
				pool.setAreaId(Integer.parseInt( mapper.get("areaId").toString()));
			}
			if(mapper.get("warehouseId")!=null){
				pool.setWarehouseId(mapper.get("warehouseId").toString().replaceAll("\\|", ","));
			}
			if(mapper.get("warehouse")!=null){
				pool.setWarehouse(mapper.get("warehouse").toString().replaceAll("\\|", ","));
			}
			if(mapper.get("payType")!=null){
				 pool.setPayType(mapper.get("payType").toString());
			}
			if(mapper.get("delivery")!=null){
				pool.setDelivery(Integer.parseInt(mapper.get("delivery").toString()));
			}
			if(mapper.get("deliverytime")!=null){
				pool.setDeliverytime(mapper.get("deliverytime").toString());
			}
			if(mapper.get("moq")!=null){
				pool.setMoq(Float.parseFloat(mapper.get("moq").toString()));
			}
			if(mapper.get("outmoq")!=null){
				pool.setOutmoq(Float.parseFloat(mapper.get("outmoq").toString()));
			}
			if(mapper.get("overflow")!=null){
				pool.setOverflow(Float.parseFloat(mapper.get("overflow").toString()));
			}
			if(mapper.get("username")!=null){
				pool.setUsername(mapper.get("username").toString());
			}
			if(mapper.get("title")!=null){
				pool.setTitle( mapper.get("title").toString());
			}
			if(mapper.get("context")!=null){
				pool.setContext( mapper.get("context").toString());
			}
			if(mapper.get("receiptType")!=null){
				 pool.setReceiptType(Integer.parseInt(mapper.get("receiptType").toString()));
			}
			if(mapper.get("FuturesMonth")!=null){
				pool.setFuturesMonth(mapper.get("FuturesMonth").toString());
			}
			Integer  poolType =pool.getPoolType();
			if (poolType != null &&poolType == 1) {
				 map = checkSellPool(pool);
				 code = map.get("code").toString();
				 msg = map.get("message").toString();
			} else if (poolType != null && poolType == 2) {
				 map = checkAddBuyPoll(pool);
				 code = map.get("code").toString();
				 msg = map.get("message").toString();
			} else {
				code = "faild";
				msg = "参数错误";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			msg = "参数错误";
		}
		map.put("msg", msg);
		map.put("code", code);
		return map;

	}


	/**
	 * 检查添加买盘数据
	 * 
	 * @param req
	 * @return
	 */
	public Map<String, Object> checkAddBuyPoll(PoolApiEntity pool)throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		String code = "ok";
		String message = "数据插入成功";
		try {
			Customer customer = new Customer();
	        customer.setItemId(pool.getItemId());
	        customer.setAccount(pool.getUsername());
	        List<Customer> culist = customerBo.getCustomerByAccountAndItemId(customer);
	        if(culist!=null && culist.size()>0){
				BuyPoolEntity buy = new BuyPoolEntity();
				buy.setPrice(pool.getPrice()+"");
				buy.setArea(pool.getAreaId());
				buy.setItemsID(pool.getItemId());
				buy.setQuantit(pool.getCount());
				buy.setTitle(pool.getTitle());
				buy.setContext(pool.getContext());
				buy.setStatus(0);
				buy.setCreatedAt(new Date());
				buy.setCreatedBy(0000);
				buy.setMallUserAccount(pool.getUsername());
				buy.setProStatus("0");
				buy.setUnit(pool.getUnit()+"");
				buy.setCustomerId(culist.get(0).getId());
				buy.setUpdatedAt(new Date());
				buy.setUpdatedBy(0000);
				buy.setWareId(pool.getWarehouseId());
				buy.setWareName(pool.getWarehouse());
				buy.setPriceType(pool.getPriceType());
				buy.setFuturesMonth(pool.getFuturesMonth());
				buy.setCreateSource(1);
				int counts = buyPoolBo.addbuyPool(buy);
				pool.setPoolId(buy.getId());
				if (counts == 1) {// 表示买盘添加成功
					addbuyAttr(pool);
				}
	        }else{
	        	code = "faild";
				message = "参数错误";
	        }
		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			message = "参数错误";
		}
		map.put("message", message);
		map.put("code", code);
		return map;
	}
	
	
	private void addbuyAttr(PoolApiEntity pool)throws Exception {
		try {
			String attre=pool.getAttr();//属性数据
			String[] count=attre.split(",");
			for(int i=0;i<count.length;i++){
				String[] ater = count[i].toString().split("=");
				BuyPoolAttr attr = new BuyPoolAttr();
				attr.setBuying_id(pool.getPoolId());
				attr.setItem_id(pool.getItemId().toString());
				attr.setAttr_id(0);
				attr.setAttr_name(ater[0]);
				attr.setAttr_value(ater[1].replaceAll("\\|", ","));
				attr.setCreate_time(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd hh-mm-ss"));
				attr.setProid(Integer.valueOf(pool.getProductId()));
				attr.setPro_name(pool.getProductName());
				buyPoolBo.addBuyPoolAttr(attr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		        
	}

	public Map<String, Object> checkSellPool(PoolApiEntity pool) {
		Map<String, Object> maps = new HashMap<>();
		String code = "ok";
		String message = "数据插入成功";
		try {
			SellPool sell = new SellPool();
			sell.setItemsid(pool.getItemId());
			sell.setPrice(Double.parseDouble(pool.getPrice().toString()));
			sell.setWareProvince(pool.getAreaId());
			sell.setWareId(Integer.valueOf(pool.getWarehouseId()));
			sell.setWareName(pool.getWarehouse());
			sell.setPaytype(sell.getPaytype());
			sell.setReceipttype(pool.getReceiptType());
			sell.setDelivery(pool.getDelivery());
			sell.setDeliverytime(pool.getDeliverytime());
			sell.setMoq(pool.getMoq()+"");
			sell.setOutmoq(pool.getOutmoq()+"");
			sell.setOverflow(pool.getOverflow()+"");
			sell.setPriceType(pool.getPriceType().toString());
			sell.setMallUserAccount(pool.getUsername());
			sell.setQuantity(String.valueOf(pool.getCount()));
			sell.setPaytype(Integer.parseInt(pool.getPayType()));
			sell.setReceipttype(Integer.parseInt(pool.getReceipttype()));
			sell.setWareId(Integer.parseInt(pool.getWarehouseId()));
			sell.setUnit(pool.getUnit());
			sell.setPriority(2);
			sell.setCode("商城同步数据");
			sell.setFuturesMonth(pool.getFuturesMonth());
			sell.setCreateSource(1);
			Customer customer = new Customer();
	        customer.setItemId(pool.getItemId());
	        customer.setAccount(pool.getUsername());
	        List<Customer> culist = customerBo.getCustomerByAccountAndItemId(customer);
	        if(culist!=null && culist.size()>0){
	        	sell.setCompanyid(Integer.parseInt(culist.get(0).getId().toString()));
	        }
			Integer totalmallid = check(pool.getAttr(),pool.getProductId());// 检查商品是否存在
			if (totalmallid == -1) {
				if (sell.getErrno() == 1) {
					code = "faild";
					message = "商城商品添加失败";
				} 
			} else if(totalmallid==0){
				SellPool sellpool = producter(pool);
				sell.setCommodityId(sellpool.getTotalmallid());
				if (sellpool.getErrno() == 1) {
					code = "faild";
					message = "商城商品添加失败";
				}else{
					sell.setSource(0);
					User user=new User();
					Items item=new Items();
					item.setId(pool.getItemId());
					user.setItems(item);
					sellPollBo.addSellPool(sell,user);
					Integer comid = attrValuesBO.getProductIdByComId(sell.getCommodityId());// 商城存在本地不存在
					if (comid == null) {
						CommodityEntity commodity = new CommodityEntity();
						commodity.setId(sell.getCommodityId());// 商品标号商城同步
						commodity.setProdId(pool.getProductId()+"");
						commodity.setCatId(pool.getItemId());
						commodity.setCatName(pool.getItemName());
						commodity.setName(pool.getProductName());
						commodity.setCreateTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
						addcommodity(commodity,pool.getAttr(),pool.getItemId());
					}
					/*SellPool seol= addsellpoolmall(sell);
					if (seol.getErrno() == 1) {
						code = "faild";
						message = seol.getMsg();
					} else {
						sell.setSource(0);
						User user=new User();
						Items item=new Items();
						item.setId(pool.getItemId());
						user.setItems(item);
						sellPollBo.addSellPool(sell,user);
						Integer comid = attrValuesBO.getProductIdByComId(sell.getCommodityId());// 商城存在本地不存在
						if (comid == null) {
							CommodityEntity commodity = new CommodityEntity();
							commodity.setId(sell.getCommodityId());// 商品标号商城同步
							commodity.setProdId(pool.getProductId()+"");
							commodity.setCatId(pool.getItemId());
							commodity.setCatName(pool.getItemName());
							commodity.setName(pool.getProductName());
							commodity.setCreateTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
							addcommodity(commodity,pool.getAttr(),pool.getItemId());
						}
					}*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			message = "未知异常";
		}
		maps.put("code", code);
		maps.put("message", message);
		return maps;
	}
	/**
	 * 添加商城商品接口
	 *
	 * @param attrValue
	 * @param prodName
	 * @param user
	 * @return
	 */
	public SellPool producter(PoolApiEntity pool) {
		SellPool sellPool = new SellPool();
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		try {
			param.add("catid",  pool.getItemId());
			param.add("catname", pool.getItemName());
			param.add("name", StringUtil.doEncoder(pool.getProductName()));
			param.add("username", StringUtil.doEncoder(pool.getUsername()));
			String attr=pool.getAttr();
			String[] count=attr.split(",");
			for(int i=0;i<count.length;i++){
				String[] ater = count[i].toString().split("=");
				param.add(StringUtil.doEncoder(ater[0].toString()), StringUtil.doEncoder(ater[1].toString()));
			}
			logger.info("开始调用：添加商品接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(producter, param, String.class);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
		//	System.err.println(StringUtil.doDecoder(jsonString));
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
				logger.info("添加商品返回数据："+maps.toString());
				if (1 == errno) {// 参数错误
					sellPool.setErrno(1);
					sellPool.setMsg("添加商品接口异常：" + maps.get("mallid"));
					return sellPool;
				} else if (255 == errno) {// 未知错误
					sellPool.setErrno(1);
					System.err.println("添加商品接口异常：" + maps.get("mallid"));
					return sellPool;
				} else if (0 == errno) {// 正常
					sellPool.setErrno(0);
					String totalmallid = String.valueOf(maps.get("mallid"));
					sellPool.setTotalmallid(Integer.valueOf(totalmallid));
					return sellPool;
				}
			}
		} catch (Exception e) {
			sellPool.setErrno(1);
			sellPool.setMsg("添加商品接口异常：");
			return sellPool;
	
		}
		sellPool.setErrno(1);
		sellPool.setMsg("添加商品接口异常：未知异常");
		return sellPool;
	}

	/**
	 * 检查商品是否已存在
	 *
	 * @param attrValue
	 * @param prodId
	 * @return
	 */
	public Integer check(String attr, Integer prodId) {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		Integer totalmallid = -1;
		String[] count=attr.split(",");
		for(int i=0;i<count.length;i++){
			String[] ater = count[i].toString().split("=");
			param.add(ater[0].toString(), ater[1].toString());
		}
		param.add("pid", prodId);
		try {
			logger.info("开始调用：验证商品是否存在接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(checkSeller, param, String.class);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {

				logger.info("验证商品是否存在返回数据："+maps.toString());
				
				if (1 == errno) {// 参数错误
					totalmallid = -1;
				} else if (0 == errno) {
					Integer msg = (Integer) maps.get("msg");
					if (msg == 1) {
						totalmallid = Integer.valueOf(String.valueOf(maps.get("totalmallid")));
					} else {
						totalmallid = 0;
					}
				} else {
					totalmallid = -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			totalmallid = -1;
		}
		return totalmallid;
	}

	public Integer addcommodity(CommodityEntity commodity,String attr,Integer itemId) {
		try {
			attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
			Integer commodityId = commodity.getId();
			String[] count=attr.split(",");
			for(int i=0;i<count.length;i++){
				String[] ater = count[i].toString().split("=");
				AttrValue attrvalue = new AttrValue();
				attrvalue.setAttrName(ater[0].toString());
				attrvalue.setAttrValue(ater[1].toString());
				attrvalue.setItemId(itemId);
				if (attrvalue != null) {
					attrvalue.setCommodityId(commodityId);
					attrvalue.setAttrId(attrValuesBO.queryAttrId(attrvalue));
					attrvalue.setCommodityName(commodity.getName());
					attrvalue.setCreaTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
					attrvalue.setEditTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
					attrValuesBO.addCommodityAttr(attrvalue);// 添加商品属性
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}


	/**
	 * 卖盘池同步到商城
	 *
	 * @param sellPool
	 * @return
	 */

	public SellPool addsellpoolmall(SellPool sellPool) {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		try {
			param.add("totalmallid", sellPool.getCommodityId());
			param.add("warehouseid", sellPool.getWareId());
			param.add("price", sellPool.getPrice());
			param.add("areaid", sellPool.getWareProvince());// 地区id
			param.add("count", sellPool.getQuantity());
			param.add("paytype", sellPool.getPaytype());
			param.add("receipttype", sellPool.getReceipttype());
			param.add("delivery", sellPool.getDelivery());
			if(sellPool.getDeliverytime()!=null){
				param.add("deliverytime", DateUtil.doSFormatDate(sellPool.getDeliverytime(),"yyyy-MM-dd").getTime() / 1000);
			}else{
				param.add("deliverytime", DateUtil.doSFormatDate(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd").getTime() / 1000);
			}
			param.add("moq", sellPool.getMoq());
			param.add("outmoq", sellPool.getOutmoq());
			param.add("overflow", sellPool.getOverflow());
			param.add("username", StringUtil.doEncoder(sellPool.getMallUserAccount()));
			param.add("producedate", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
			param.add("instoragedate", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
			param.add("catid", sellPool.getItemsid());
			if (sellPool.getUnit() != null && Integer.valueOf(sellPool.getUnit()) == 0) {
				param.add("unit", "吨");
			} else {
				param.add("unit", "千克");
			}
			param.add("sheetnum", sellPool.getQuantity());

			logger.info("开始调用：新增卖盘同步到商城接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(publicitem, param, String.class);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
				
				logger.info("新增卖盘同步到商城返回数据"+maps.toString());
				
				if (1 == errno) {// 参数错误
					sellPool.setErrno(1);
					sellPool.setMsg("同步商城接口调用异常：" + String.valueOf(maps.get("mallid")));
				} else if (255 == errno) {// 参数错误
					sellPool.setErrno(1);
					sellPool.setMsg("同步商城接口调用异常：" + String.valueOf(maps.get("mallid")));
				} else if (0 == errno) {// 参数错误
					sellPool.setErrno(0);
					Object mallid = maps.get("mallid");
					sellPool.setMallSaleId(Integer.valueOf(mallid.toString()));
					sellPool.setMsg("同步商城成功");
				}
			}
		} catch (Exception e) {
			sellPool.setErrno(1);
			sellPool.setMsg("同步商城接口调用异常");
		}
		return sellPool;
	}

	/**
	 * 新建还盘
	 * 
	 * @param req
	 * @param poolId
	 * @param poolType
	 * @param price
	 * @param customerId
	 * @param customerName
	 * @return
	 */
	@RequestMapping(value = "/addCounterOffer")
	@ResponseBody
	public Map<String, Object> addCounterOffer(HttpServletRequest req, @RequestBody Map<String, Object> paraMap ) {
		Map<String, Object> map = new HashMap<>();
		PoolPrice poolPrice=new PoolPrice();
		String code="";
		String  msg="";
		if(paraMap.get("poolId")==null||paraMap.get("poolType")==null||paraMap.get("price")==null||
				paraMap.get("account")==null||paraMap.get("customerName")==null||paraMap.get("priceType")==null||paraMap.get("itemId")==null){
			code = "faild";
			msg = "失败,未知异常";
		}
		try{
			Map<String, Object> mape=businessBO.compare(paraMap);
			if(mape.get("code").equals("ok")){
				 poolPrice.setPrice(Double.valueOf(paraMap.get("price").toString()));
			}else{
				map.put("code", "faild");
				map.put("msg", "价格参数错误");
				return map;
			}
			Integer poolId=Integer.parseInt(paraMap.get("poolId").toString());
			Integer poolType=Integer.parseInt(paraMap.get("poolType").toString());
			String account=paraMap.get("account").toString();
			String customerName=paraMap.get("customerName").toString();
			String priceType=paraMap.get("priceType").toString();
			String itemId=paraMap.get("itemId").toString();
			poolPrice.setAccount(account);
			poolPrice.setCustomerName(customerName);
			poolPrice.setPoolId(poolId);
			poolPrice.setPriceType(Integer.valueOf(priceType));
			poolPrice.setPoolType(poolType);
			if (poolType == 1) {//1 卖盘
				SellPool sellPool=sellPollBo.getSellPoolByPoolId(poolId);//获取当前报盘价格
				if(sellPool !=null){
					if(!sellPool.getIsClose().toString().equals("1") && !sellPool.getIsConfirm().toString().equals("1") && !sellPool.getStatus().toString().equals("1")){//判断当前报盘是否关闭是否成交是否删除
						
						//超价通知
						//新增还盘之前价格最合适的一条还盘信息
						PoolPrice  poolPrice_1 = this.iBusinessBo.queryCurrentMaxPoolPrice(paraMap);
						//通知其他还盘企业 报价失效     （新增需求 2015年12月31日上午9:11:44 ）
						if (poolPrice_1 != null) {
							Map<String,Object> param = new HashMap<String, Object>();
							//产品名
							String productName = poolPrice_1.getProductName();
							//商城账户
							if (!poolPrice_1.getAccount().equals(account)) {
								param.put("account", poolPrice_1.getAccount());
								param.put("productName", productName);
								param.put("poolType", poolType);
								
								this.noticeBo.priceInvalidNotify(param);
							}
						}
						
						Map<String, Object> rltMap=businessBO.addCounter(poolPrice, Integer.valueOf(itemId));
						 code=rltMap.get("status").toString();
						 if(code.equals("ok")){
							 noticeBo.newOffer(poolPrice);
						 }
						 msg=rltMap.get("msg").toString();
					}else{
						code = "faild";
						msg = "卖盘已关闭或已成交或已删除";
					}
				}
			} else if (poolType == 2) {//2 买盘
				BuyPoolEntity buyPoolEntity=buyPoolBo.getbuypoolById(poolId);//获取当前报盘价格
				if(buyPoolEntity !=null){
					if(!buyPoolEntity.getIsClose().toString().equals("1") && !buyPoolEntity.getIsConfirm().toString().equals("1") && !buyPoolEntity.getStatus().toString().equals("1") ){//判断当前报盘是否关闭是否成交是否删除
						
						//超价通知
						//新增还盘之前价格最合适的一条还盘信息
						PoolPrice  poolPrice_1 = this.iBusinessBo.queryCurrentMaxPoolPrice(paraMap);
						//通知其他还盘企业 报价失效     （新增需求 2015年12月31日上午9:11:44 ）
						if (poolPrice_1 != null) {
							Map<String,Object> param = new HashMap<String, Object>();
							//产品名
							String productName = poolPrice_1.getProductName();
							//商城账户
							if (!poolPrice_1.getAccount().equals(account)) {
								param.put("account", poolPrice_1.getAccount());
								param.put("productName", productName);
								param.put("poolType", poolType);
								
								this.noticeBo.priceInvalidNotify(param);
							}
						}
						
						Map<String, Object> rltMap=businessBO.addCounter(poolPrice, Integer.valueOf(itemId));
						 code=rltMap.get("status").toString();
						 if(code.equals("ok")){
							 noticeBo.newOffer(poolPrice);
						 }
						 msg=rltMap.get("msg").toString();
					}else{
						code = "faild";
						msg = "买盘已关闭或已成交或已删除";
					}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			msg = "失败,未知异常";
			map.put("code", code);
			map.put("msg", msg);
			return map;
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;

	}

	/**
	 * 撤销报盘
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/delPool")
	@ResponseBody
	public Map<String, Object> delPool(HttpServletRequest req, @RequestBody Map<String, Object> paraMap ) {
		String code = "";
		String msg = "";
		Map<String, Object> map = new HashMap<>();
		CounterOfferEntity counterOfferEntity = new CounterOfferEntity();
		try {
			Integer poolId=Integer.valueOf(paraMap.get("poolId").toString());
			Integer poolType=Integer.valueOf(paraMap.get("poolType").toString());
			counterOfferEntity.setPoolId(poolId);
			counterOfferEntity.setUpdatedAt(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if (poolId != null && poolType != null) {
				if (poolType == 1) {
					SellPool sellPool=sellPollBo.getSellPoolByPoolId(poolId);//获取当前报盘价格
					if(sellPool !=null){
						if(!sellPool.getIsClose().toString().equals("1") && !sellPool.getIsConfirm().toString().equals("1") && !sellPool.getStatus().toString().equals("1")){//判断当前报盘是否关闭是否成交是否删除
							Float Sellprice = counterOfferBo.isSellpoolId(poolId);
							if (Sellprice == null) {
								code = "faild";
								msg = "失败,报盘不存在或已成交";
							} else {
								counterOfferBo.closeSellPool(counterOfferEntity);
								code = "ok";
								msg = "成功";
							}
						}else{
							code = "faild";
							msg = "卖盘已关闭或已成交";
						}
					}
				} else if (poolType == 2) {
					BuyPoolEntity buyPoolEntity=buyPoolBo.getbuypoolById(poolId);//获取当前报盘价格
					if(buyPoolEntity !=null){
						if(!buyPoolEntity.getIsClose().toString().equals("1") && !buyPoolEntity.getIsConfirm().toString().equals("1") && !buyPoolEntity.getStatus().toString().equals("1") ){//判断当前报盘是否关闭是否成交是否删除
							Float buyprice = counterOfferBo.isBuypoolId(poolId);
							if (buyprice == null) {
								code = "faild";
								msg = "失败,报盘不存在或已成交";
							} else {
								counterOfferBo.closeBuyPool(counterOfferEntity);
								code = "ok";
								msg = "成功";
							}
						}else{
							code = "faild";
							msg = "买盘已关闭或已成交";
						}
					}
				} else {
					code = "faild";
					msg = "失败,参数错误";
				}
			} else {
				code = "faild";
				msg = "失败,参数错误";
			}
			noticeBo.transactionClose(poolId,poolType);
		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			msg = "失败,未知异常";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 查询系统通知
	 * 
	 * @param req
	 * @return
	 */

	@RequestMapping(value = "/querySysNotice")
	@ResponseBody
	public Map<String, Object> querySysNotice( HttpServletRequest req, @RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<>();
		String code = "";
		String msg = "";
		String data = "";
		String  customerId=(String)paraMap.get("customerId");
		String  currentPage = String.valueOf(paraMap.get("currentPage"));
		String  pageSize = String.valueOf(paraMap.get("pageSize"));
		Integer totalCount = 0;
		Integer newCount = 0;
		try {

			SysNotice sysNotice = new SysNotice();
			PageParameter page = new PageParameter(Integer.valueOf(currentPage), Integer.valueOf(pageSize));
			sysNotice.setParameter(page);
			sysNotice.setCustomerId(customerId);
			if (customerId == null || "".equals(customerId)) {
				code = "faild";
				msg = "失败,参数错误";
				data = "";
			} else {
				List<SysNotice> list = counterOfferBo.querySysNotice(sysNotice);
				
				JSONArray jsonArray = new JSONArray();
				jsonArray.addAll(list);
				code = "ok";
				msg = "成功";
				data = jsonArray.toString();
				totalCount = sysNotice.getParameter().getTotalCount();
				newCount = counterOfferBo.querySysNoticeNotRead(sysNotice);
			}
		} catch (Exception e) {
			code = "faild";
			msg = "失败,未知错误";
			data = "";
			e.printStackTrace();
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", data);
		map.put("totalCount", totalCount);
		map.put("newCount", newCount);
		return map;

	}
	
	/**
	 * 消息已读
	 * @param req
	 * @param paraMap
	 * @return
	 */
	@RequestMapping(value = "/readSysNotice")
	@ResponseBody
	public Map<String, Object> readSysNotice( HttpServletRequest req, @RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<>();
		String  noticeId = (String)paraMap.get("noticeId");
		String[] ids = noticeId.split(",");
		int array[] = new int[ids.length];
		for(int i=0;i<ids.length;i++){  
			array[i]=Integer.parseInt(ids[i]);
		}
		if(counterOfferBo.updateNoticeIsRead(array)){
			map.put("code", "ok");
			map.put("msg", "成功");
		}else{
			map.put("code", "faild");
			map.put("msg", "失败");
		}
		return map;
		
	}

	/**
	 * 删除指定系统通知
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/delSysNotice")
	@ResponseBody
	public Map<String, Object> delSysNotice(HttpServletRequest req, @RequestBody Map<String, Object> paraMap  ) {
		Map<String, Object> map = new HashMap<>();
		String code = "";
		String msg = "";
		Integer noticeId=Integer.valueOf((String)paraMap.get("noticeId"));
		try {
			if (noticeId != null) {
				counterOfferBo.delSysNotice(noticeId);
				code = "ok";
				msg = "成功";
			} else {
				code = "faild";
				msg = "失败,参数错误";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			msg = "失败,未知错误";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;

	}

	/**
	 * 修改报盘价格
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updatePoolPrice")
	@ResponseBody
	public Map<String, Object> updatePoolPrice(@RequestBody Map<String, Object> paraMap ) {
		Map<String, Object> map = new HashMap<>();
		String code = "ok";
		String msg = "成功";
		try {
			Integer poolId=Integer.valueOf(paraMap.get("poolId").toString()); 
			Integer poolType=Integer.valueOf( paraMap.get("poolType").toString());
			Float price=Float.valueOf( paraMap.get("price").toString());
			CounterOfferEntity counterOfferEntity = new CounterOfferEntity();
			counterOfferEntity.setPoolId(poolId);
			counterOfferEntity.setPrice("" + price);
			counterOfferEntity.setUpdatedAt(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if (poolId != null && poolType != null && price != null) {
				if (poolType == 1) {//1 卖盘
					String maxPriceStr = counterOfferBo.querySellMaxPrice(poolId);
					if (maxPriceStr != null && Float.valueOf(maxPriceStr) >= price) {
						counterOfferEntity.setIsConfirm(1);
					}
					SellPool sellPool=sellPollBo.getSellPoolByPoolId(poolId);//获取当前报盘价格
					if(sellPool !=null){
						if(!sellPool.getIsClose().toString().equals("1") && !sellPool.getIsConfirm().toString().equals("1") && !sellPool.getStatus().toString().equals("1")){//判断当前报盘是否关闭是否成交是否删除
							double fle=sellPool.getPrice();
							if(fle>price){
								counterOfferBo.updateSellPrice(counterOfferEntity);
							}else{
								code = "faild";
								msg = "卖盘不允许涨价";
							}
						}else{
							code = "faild";
							msg = "卖盘已关闭或已成交";
						}
					}
				} else if (poolType == 2) {//2 买盘
					String maxPriceStr = counterOfferBo.queryBuyMinPrice(poolId);
					if (maxPriceStr != null && Float.valueOf(maxPriceStr) <= price) {
						counterOfferEntity.setIsConfirm(1);
					}
					BuyPoolEntity buyPoolEntity=buyPoolBo.getbuypoolById(poolId);//获取当前报盘价格
					if(buyPoolEntity !=null){
						if(!buyPoolEntity.getIsClose().toString().equals("1") && !buyPoolEntity.getIsConfirm().toString().equals("1") && !buyPoolEntity.getStatus().toString().equals("1") ){//判断当前报盘是否关闭是否成交是否删除
							String stri=buyPoolEntity.getPrice();
							if(Float.valueOf(stri) < price){
								counterOfferBo.updateBuyPrice(counterOfferEntity);
							}else{
								code = "faild";
								msg = "买盘不允许降价";
							}
						}else{
							code = "faild";
							msg = "买盘已关闭或已成交";
						}
					}
				} else {
					code = "faild";
					msg = "失败,报盘类型错误";
				}
				
			} else {
				code = "faild";
				msg = "失败,参数错误";
			}

		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			msg = "失败,未知错误";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;

	}
	
	/** 商城端修改卖盘编号接口
	 * @Title: updatePoolsortNum 
	 * @Description: TODO
	 * @param paraMap
	 * @return map
	 * @author zhangnan/张楠
	 * @return: Map<String,Object>
	 * @createTime 2016年1月8日上午10:37:21
	 */
	@RequestMapping(value = "/updatePoolsortNum")
	@ResponseBody
	public Map<String, Object> updatePoolsortNum(@RequestBody Map<String, Object> paraMap ) {
		Map<String, Object> map = new HashMap<>();
		String code = "";
		String msg = "";
		try {
			//Integer mallSaleId = Integer.valueOf(paraMap.get("mallSaleId").toString());
			Integer sortNum = Integer.valueOf(paraMap.get("sortNum").toString());
			if (sortNum >= 0 && sortNum <= 99) {
				this.sellPollBo.updatePoolsortNum(paraMap);
				code = "ok";
				msg = "编号修改成功";
			}else {
				code = "faild";
				msg = "编号只能为0~99的正整数";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			code = "faild";
			msg = "失败,未知错误";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;

	}
}
