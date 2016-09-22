package com.smm.cuohe.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.Folder;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.IBusinessBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IItemAttrBO;
import com.smm.cuohe.bo.IOrdersBO;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.IBuyOrderBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.BuyPoolAttr;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.PoolApiEntity;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.CommodityEntity;
import com.smm.cuohe.domain.dealmake.ProductEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.Warehouse;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;

@Controller
@RequestMapping(value = "PoolManage")
public class PoolManageController {
	
	private Logger logger= Logger.getLogger(PoolManageController.class);

	@Resource
	IItemAttrBO itemAttrBO;
	@Resource
	IAttrValuesBO attrValuesBO;
	@Resource
	IAreasBO iAreasBO;
	@Resource
	BuyPoolBo buypoolbo;
	@Resource
	private ICustomerBO iCustomerBO;
	@Resource
	private ISellPoolBO sellPollBo;
	@Resource
	private ICompanyBO companyBO;
	@Resource
	private IBuyOrderBO iBuyOrderBO;
	@Resource
	private IOrdersBO iOrdersBO;
	@Resource
	private RestTemplate restTemplate;
	@Resource
	private IBusinessBO iBusinessBo;
	@Value("#{ch['publicitem.URL']}")
	private String publicitem;
	@Value("#{ch['checkSeller.URL']}")
	private String checkSeller;
	@Value("#{ch['producter.URL']}")
	private String producter;

	/** 转到新建报盘页面
	 * @Title: addPool 
	 * @param req
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping(value = "toaddPool")
	public ModelAndView addPool(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("pool/addpool");
		User user = (User) req.getSession().getAttribute("userInfo");
		List<ItemAttr> itemAttrList = itemAttrBO.getItemAttrByItemsId(Integer.valueOf(user.getItemId()));
		// 可选项
		if (itemAttrList != null && itemAttrList.size() > 0) {
			for (int i = 0; i < itemAttrList.size(); i++) {
				if (itemAttrList.get(i).getOptions() != null) {
					String[] option = itemAttrList.get(i).getOptions().split(",");
					List<String> list = new ArrayList<String>();
					for (int j = 0; j < option.length; j++) {
						list.add(option[j]);
					}
					itemAttrList.get(i).setOptionsValue(list);
				}
			}
		}
		List<Areas> arealist = iAreasBO.getParentAreas();
		List<ProductEntity> prodList = attrValuesBO.getproductitemId(user.getItems().getId());
		//每日交易指导价
		ItemPrice itemPrice = this.iBusinessBo.getItemPrice(user);
		view.addObject("itemPrice", itemPrice);
		view.addObject("prodList", prodList);
		String only=UUID.randomUUID().toString().replace("-", "");
		req.getSession().setAttribute(only,only);
		view.addObject("only", only);
		view.addObject("arealist", arealist);
		view.addObject("itemAttrList", itemAttrList);
		view.addObject("futuresMonth", futuresMonth());
		view.addObject("futuresMonthList", futuresMonthList());
		
		//品目
		view.addObject("itemId", user.getItemId());
		view.addObject("user", user);
		return view;

	}
	public  String   futuresMonth(){
		 Calendar c=Calendar.getInstance(); 
		 int date = c.get(Calendar.DATE);	 
		 if(date>15){
			 c.add (Calendar.MONTH, 1);;
		 }	
		SimpleDateFormat sdf=new SimpleDateFormat("yyMM");
		String  dateStr=sdf.format(c.getTime());
		return  dateStr;
	}
	public  List<String>   futuresMonthList(){//获取期货月
		List<String> list=new ArrayList<>();
		 Calendar c=Calendar.getInstance(); 
		 SimpleDateFormat sdf=new SimpleDateFormat("yyMM");	
		 int date = c.get(Calendar.DATE);
		 if(date>15){
			 c.add (Calendar.MONTH, 1);;
		 }
		 list.add(sdf.format(c.getTime()));
		for ( int i=0; i < 11; i++) {
			c.add (Calendar.MONTH, 1);
			list.add(sdf.format(c.getTime()));
		}
		return  list;
	}
	/** 新增买卖盘，入库
	 * @Title: addpool 
	 * @Description: TODO
	 * @param req
	 * @param pool
	 * @return
	 * @return: Map<String,Object>
	 * @createTime 2016年1月4日下午2:43:31
	 */
	@RequestMapping(value = "addPool")
	@ResponseBody
	public Map<String, Object> addpool(HttpServletRequest req, PoolApiEntity pool) {
		Map<String, Object> map = new HashMap<>();
		String  code="";
		String  message="";
		String only=req.getParameter("only").toString();
		Object obje=req.getSession().getAttribute(only);
		if(obje !=null){
			req.getSession().removeAttribute(only);
			Map<String, Object> maper=savetPool(req,pool);
			code=maper.get("code").toString();
			message=maper.get("message").toString();
		}else{
			code="error";
			message="重复提交";
		}
		map.put("code", code);
		map.put("message", message);
		return map;
	}
	
	public Map<String, Object> savetPool(HttpServletRequest req, PoolApiEntity pool){
		Map<String, Object> map = new HashMap<>();
		String  code="success";
		String  message="添加成功!";
		try {
			User user = (User) req.getSession().getAttribute("userInfo");
			String prodId = req.getParameter("prodId");
			String customerId = req.getParameter("customerId");
			String poolType = req.getParameter("poolType");
			String quantit = req.getParameter("quantit");
			String price = req.getParameter("price");
			String area = req.getParameter("area");
			String wareId = req.getParameter("wareId");
			String priceType = req.getParameter("priceType");
			//报价员ID
			Integer offerer = Integer.parseInt(req.getParameter("offerer"));
			//String wareName = req.getParameter("wareName");
			// 获取扩展属性信息
			String ids[] = req.getParameterValues("ids");
			String attrName[] = req.getParameterValues("arrtName");
			String fillmode[] = req.getParameterValues("fillmode");
			// 通过客户获取对应商城会员账号
			Customer customer = this.iCustomerBO.getOneById(Integer.parseInt(customerId));
			String prodName = attrValuesBO.queryproNameProId(Integer.valueOf(prodId));
			String  pingpai=req.getParameter("pingpai");
			String  cangkuId=req.getParameter("cangkuId");
			//String  cangkuName=req.getParameter("cangkuName");
			String wareName = "";
			//if(cangkuId!=null&&!"".equals(cangkuId)&&cangkuName!=null&&!"".equals(cangkuName)){
				wareId=wareId+","+cangkuId;
//				wareName=wareName+","+cangkuName;
				String[] wareIds = wareId.split(",");
				for (int i = 0; i < wareIds.length; i++) {
					Integer warehouseId = Integer.parseInt(wareIds[i]);
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("warehouseId", warehouseId);
					Warehouse warehouse = this.iAreasBO.getWarehouseById(param);
					if (warehouse != null ) {
						wareName += warehouse.getName()+",";
					}
				}
				
				if (wareName != "" && wareName.length() > 0) {
					wareName = wareName.substring(0, wareName.length()-1);
				}
			//}
			
				if (cangkuId==null || "".equals(cangkuId) ) {
					wareId = wareId.substring(0,wareId.length()-1);
				}
			if (poolType != null && Integer.valueOf(poolType) == 2) {// 添加买盘
				BuyPoolEntity buy = new BuyPoolEntity();
				buy.setArea(Integer.valueOf(area));
				buy.setPrice(price);
				buy.setItemsID(Integer.valueOf(user.getItemId()));
				buy.setQuantit(Double.valueOf(quantit));
				buy.setTitle("采购" + prodName);
				buy.setContext(customer.getCompanyName() + "采购" + quantit + "吨" + prodName);
				buy.setStatus(0);
				buy.setCreatedAt(new Date());
				//buy.setCreatedBy(user.getId());
				//报价员
				buy.setCreatedBy(offerer);
				buy.setMallUserAccount(customer.getAccount());
				buy.setProStatus("0");
				buy.setUnit(0 + "");
				buy.setCustomerId(Integer.valueOf(customerId));
				buy.setUpdatedAt(new Date());
				//buy.setUpdatedBy(user.getId());
				buy.setUpdatedBy(offerer);
				buy.setWareId(wareId);
				buy.setWareName(wareName);
				buy.setPriceType(Integer.valueOf(priceType));
				buy.setFuturesMonth(pool.getFuturesMonth());
				buy.setCreateSource(1);//设置订单来源
				int count = buypoolbo.addbuyPool(buy);// 新建买盘
				if (count == 1) {
					addbuyAttr(req, ids, prodId, prodName, attrName, fillmode, user, buy,pingpai);// 保存商品属性
				}
			} else if (poolType != null && Integer.valueOf(poolType) == 1) {// 新建卖盘
				SellPool sell = new SellPool();
				sell.setPrice(Double.parseDouble(price));
				sell.setQuantity(quantit);
				sell.setItemsid(Integer.parseInt(user.getItemId()));
				sell.setCompanyid(Integer.valueOf(customerId));
				sell.setUnit(0);
				sell.setWareProvince(Integer.valueOf(area));
				sell.setWareId(Integer.valueOf(wareId));
				sell.setStatus(0);
				sell.setCreatedat(new Date());
				//sell.setCreatedby(user.getId());
				//报价员
				sell.setCreatedby(offerer);
				sell.setUpdatedat(new Date());
				//sell.setUpdatedby(user.getId());
				sell.setUpdatedby(offerer);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sell.setDeliverytime(sdf.format(new Date()));// 交货日期
				sell.setPaytype(1);// 结算方式 1现款现汇 2银行承兑 3有色网代收代付
				sell.setReceipttype(1);// 付款方式
				sell.setDelivery(2);// 交货方式
				sell.setOverflow("2");// 磅差
				sell.setPriority(2);// 优先级
				sell.setSource(0);
				sell.setMoq("1");
				sell.setOutmoq("1");
				sell.setPriceType(priceType);
				
				String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
				nowTimeTemp = nowTimeTemp.replace("-", "")+""+0;
				
				sell.setTimeSort(nowTimeTemp);
				
				sell.setFuturesMonth(pool.getFuturesMonth());
				sell.setCreateSource(1);//设置订单来源
				String  msg = addSellPool(sell, req);
				if(!msg.equals("true")){
					code="error";
					message=msg;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			code="error";
			message="未知异常";
		}
		map.put("code", code);
		map.put("message", message);
		return map;
	}

	// 暂时保存在买盘属性表
	private void addbuyAttr(HttpServletRequest req, String[] id, String proId, String prodName, String[] attrName,
			String[] fillmode, User user, BuyPoolEntity buy,String pingpai) {

		List<AttrValue> attrvalueList = new ArrayList<>();
		if (id != null && id.length > 0) {
			for (int i = 0; i < id.length; i++) {
				AttrValue attr = new AttrValue();
				if (proId != null && !"".equals(proId)) {
					attr.setProdId(Integer.valueOf(proId));
				}
				if (attrName != null && attrName.length > i) {
					if (attrName[i] == null || "".equals(attrName[i])) {
						continue;
					}
					attr.setAttrName(attrName[i]);

				}
				if (fillmode != null && "2".equals(fillmode[i])) {
					String[] values = req.getParameterValues("attrValue" + id[i]);
					String value = "";
					if (values != null) {
						for (String str : values) {
							value = value + str + ",";
						}
						if (!"".equals(value)) {
							if (attrName[i] == null || "".equals(attrName[i])) {
								continue;
							}
							attr.setAttrValue(value.substring(0, value.length() - 1));
						}
					}

				} else {

					if (attrName[i] == null || "".equals(attrName[i])) {
						continue;
					}
					String value=req.getParameter("attrValue" + id[i]);
					if(pingpai!=null&&!"".equals(pingpai)&&"品牌".equals(attrName[i])){
						value=value+","+pingpai;
					}
					attr.setAttrValue(value);
				}
				attr.setAttrId(Integer.valueOf(id[i]));

				attrvalueList.add(attr);

			}
		}

		if (attrvalueList.size() > 0) {
			for (int i = 0; i < attrvalueList.size(); i++) {
				if (attrvalueList.get(i).getAttrValue() == null || "".equals(attrvalueList.get(i).getAttrValue())) {

					continue;
				}
				AttrValue attrvalue = attrvalueList.get(i);
				if (attrvalue != null) {
					BuyPoolAttr attr = new BuyPoolAttr();

					int buying_id = buypoolbo.getMaxIdfromBuypool();
					attr.setBuying_id(buying_id);
					attr.setItem_id(user.getItemId());
					attr.setAttr_id(attrvalue.getAttrId());
					attr.setAttr_name(attrvalue.getAttrName());
					attr.setAttr_value(attrvalue.getAttrValue());
					attr.setCreate_time(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd hh-mm-ss"));
					attr.setProid(attrvalue.getProdId());
					attr.setPro_name(prodName);

					buypoolbo.addBuyPoolAttr(attr);
				}
			}
		}
	}

	public String addSellPool(SellPool sellPool, HttpServletRequest request) {
		String st="true";
		try {
			// 获取扩展属性信息
			String[] attrName = request.getParameterValues("arrtName");
			String[] fillmode = request.getParameterValues("fillmode");
			String[] id = request.getParameterValues("ids");
			User user = (User) request.getSession().getAttribute("userInfo");
			Warehouse warehouse = this.sellPollBo.queryWarehouseById(sellPool.getWareId());
			sellPool.setWareName(warehouse.getName());
			String proId = request.getParameter("prodId");
			String prodName = attrValuesBO.queryproNameProId(Integer.valueOf(proId));
			List<AttrValue> attrvalueList = new ArrayList<>();
			if (id != null && id.length > 0) {
				for (int i = 0; i < id.length; i++) {
					AttrValue attr = new AttrValue();
					if (proId != null && !"".equals(proId)) {
						attr.setProdId(Integer.valueOf(proId));
					}
					if (attrName != null && attrName.length > i) {
						if (attrName[i] == null || "".equals(attrName[i])) {
							continue;
						}
						attr.setAttrName(attrName[i]);

					}
					if (fillmode != null && "2".equals(fillmode[i])) {
						String[] values = request.getParameterValues("attrValue" + id[i]);
						String value = "";
						if (values != null) {
							for (String str : values) {
								value = value + str + ",";
							}
							if (!"".equals(value)) {
								if (attrName[i] == null || "".equals(attrName[i])) {
									continue;
								}
								attr.setAttrValue(value.substring(0, value.length() - 1));
							}
						}

					} else {

						if (attrName[i] == null || "".equals(attrName[i])) {
							continue;
						}
						attr.setAttrValue(request.getParameter("attrValue" + id[i]));
					}
					attr.setAttrId(Integer.valueOf(id[i]));
					if (attr.getAttrValue() != null && !"".equals(attr.getAttrValue())) {

						attrvalueList.add(attr);
					}

				}
			}
			Integer commodityId = null;

			if (attrvalueList == null || attrvalueList.size() < 1) {
				return "请至少选择一个商品属性";

			}

			Integer totalmallid = check(attrvalueList, Integer.valueOf(proId));
			if (totalmallid == -1) {

				return "检查商品是否存在接口异常";
			} else if (totalmallid == 0) {
				commodityId = null;
			} else {
				commodityId = totalmallid;
			}
			String account = sellPollBo.getcompanyAccount(sellPool.getCompanyid());
			CommodityEntity commodity = new CommodityEntity();
		
			if (commodityId == null) {// 商品不存在

				SellPool sell = producter(attrvalueList, prodName, user, account);

				if (sell.getErrno() == 1) {

					return sell.getMsg();
				}
				commodityId = sell.getTotalmallid();

			}
			Integer comid = attrValuesBO.getProductIdByComId(commodityId);// 商城存在本地不存在
			if (commodityId == null || comid == null) {// 商品不存在或者商城存在本地不存在
				commodity.setId(commodityId);// 商品标号商城同步
				commodity.setProdId(proId);
				commodity.setCatId(user.getItems().getId());
				commodity.setCatName(user.getItems().getName());
				commodity.setName(prodName);
				commodity.setCreateTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
				attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
				commodityId = commodity.getId();
				for (int i = 0; i < attrvalueList.size(); i++) {
					if (attrvalueList.get(i).getAttrValue() == null || "".equals(attrvalueList.get(i).getAttrValue())) {

						continue;
					}
					AttrValue attrvalue = attrvalueList.get(i);
					if (attrvalue != null) {
						attrvalue.setCommodityId(commodityId);
						attrvalue.setCommodityName(prodName);
						attrvalue.setCreaTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
						attrvalue.setEditTime(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
						;

						attrValuesBO.addCommodityAttr(attrvalue);// 添加商品属性
					}

				}

			}
			sellPool.setUsername(user.getName());
			sellPool.setCommodityId(commodityId);
			sellPool.setMallUserAccount(account);
			sellPool.setItemsid(user.getItems().getId());
//			sellPool = addsellpoolmall(sellPool);// 卖盘同步商城 得到挂牌号      撮合交易管理只负责摩擦摩擦
//			if (1 == sellPool.getErrno()) {
//				return sellPool.getMsg();
	//
//			}
			sellPool.setSource(0);
			sellPollBo.addSellPool(sellPool, user);
		} catch (Exception e) {
			e.printStackTrace();
			st="false";
		}
		return st;
	}

	/**
	 * 检查商品是否已存在
	 *
	 * @param attrValue
	 * @param prodId
	 * @return
	 */
	public Integer check(List<AttrValue> attrValue, Integer prodId) {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		for (int i = 0; i < attrValue.size(); i++) {
			if (attrValue.get(i).getAttrValue() != null && !"".equals(attrValue.get(i).getAttrValue())) {
				param.add(StringUtil.doEncoder(attrValue.get(i).getAttrName()),
						StringUtil.doEncoder(attrValue.get(i).getAttrValue()));
			}

		}
		Integer totalmallid = -1;
		param.add("pid", prodId);
		try {
			logger.info("开始调用：验证商品是否存在接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(checkSeller, param, String.class);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
				logger.info("验证商品是否存在返回数据"+maps.toString());
				
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

	/**
	 * 添加商城商品接口
	 *
	 * @param attrValue
	 * @param prodName
	 * @param user
	 * @return
	 */
	public SellPool producter(List<AttrValue> attrValue, String prodName, User user, String account) {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

		param.add("catid", user.getItems().getId());
		param.add("catname", StringUtil.doEncoder(user.getItems().getName()));
		param.add("name", StringUtil.doEncoder(prodName));
		param.add("username", StringUtil.doEncoder(account));
		for (int i = 0; i < attrValue.size(); i++) {
			if (attrValue.get(i).getAttrValue() != null && !"".equals(attrValue.get(i).getAttrValue())) {
				param.add(StringUtil.doEncoder(attrValue.get(i).getAttrName()),
						StringUtil.doEncoder(attrValue.get(i).getAttrValue()));
			}

		}

		SellPool sellPool = new SellPool();

		try {
			logger.info("开始调用：添加商品接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(producter, param, String.class);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);

			//System.err.println(StringUtil.doDecoder(jsonString));
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
	 * 卖盘池同步到商城
	 *
	 * @param sellPool
	 * @return
	 */

	public SellPool addsellpoolmall(SellPool sellPool) {

		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		
		param.add("pricetype", sellPool.getPriceType());
		param.add("totalmallid", sellPool.getCommodityId());
		param.add("warehouseid", sellPool.getWareId());
		param.add("price", sellPool.getPrice());
		param.add("areaid", sellPool.getWareProvince());// 地区id
		param.add("count", sellPool.getQuantity());
		param.add("paytype", sellPool.getPaytype());
		param.add("receipttype", sellPool.getReceipttype());
		param.add("delivery", sellPool.getDelivery());
		param.add("deliverytime", DateUtil.doSFormatDate(sellPool.getDeliverytime(), "yyyy-MM-dd").getTime() / 1000);
		param.add("moq", sellPool.getMoq());
		param.add("outmoq", sellPool.getOutmoq());
		param.add("overflow", sellPool.getOverflow());
		param.add("username", StringUtil.doEncoder(sellPool.getMallUserAccount()));
		param.add("fumonth", sellPool.getFuturesMonth());//期货月

		param.add("producedate", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
		param.add("instoragedate", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
		param.add("catid", sellPool.getItemsid());
		if (sellPool != null && sellPool.getUnit() != null && Integer.valueOf(sellPool.getUnit()) == 0) {
			param.add("unit", StringUtil.doEncoder("吨"));
		} else {
			param.add("unit", StringUtil.doEncoder("千克"));
		}
		param.add("sheetnum", sellPool.getQuantity());

		try {
			logger.info("开始调用：新增卖盘同步到商城接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(publicitem, param, String.class);
			logger.error("接口地址：" + publicitem + "， 参数：" + param.toString() + "， 返回结果：" + jsonString);
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

	@RequestMapping(value = "toupdatePool")
	public ModelAndView toupdatePool(HttpServletRequest req, Integer poolId, Integer poolType,BigDecimal high,BigDecimal low) {
		PoolApiEntity pool = new PoolApiEntity();
		if (poolType != null && poolType == 1) {// 修改卖盘
			SellPool sell = sellPollBo.getSellPoolById(poolId);
			pool.setPoolType(1);
			pool.setPrice(Float.valueOf(String.valueOf(sell.getPrice())));
			pool.setQuantity(sell.getQuantity());
			pool.setPoolId(sell.getId());
			if(sell.getPriceType()!=null){
				pool.setPriceType(Integer.valueOf(sell.getPriceType()));
			}else {
				pool.setPriceType(0);
			}
			
		} else if (poolType != null && poolType == 2) {// 修改买盘
			BuyPoolEntity buy = buypoolbo.getbuypoolById(poolId);
			pool.setPoolType(2);
			if(buy.getPrice()!=null){
			pool.setPrice(Float.valueOf(buy.getPrice()));
			}else {
				pool.setPrice(Float.valueOf("0.0"));	
			}
			pool.setQuantity(String.valueOf(buy.getQuantit()));
			pool.setPoolId(buy.getId());
			pool.setPriceType(buy.getPriceType());
		}
		ModelAndView view = new ModelAndView("pool/updatePool");
		view.addObject("pool", pool);
		view.addObject("priceType", pool.getPriceType());
		view.addObject("high", high);
		view.addObject("low", low);
		
		return view;
	}
	@RequestMapping(value = "updatePool")
	@ResponseBody
	public Map<String, Object> updatePool(HttpServletRequest req, Integer poolId, Integer poolType, double price,
			String quantity,Integer priceType) {
		User user=(User)req.getSession().getAttribute("userInfo");
		Map<String, Object> map = new HashMap<String, Object>();
		if (poolId == null || poolType == null || quantity == null) {
			map.put("code", "error");
			map.put("msg", "参数不能为空");
			return map;
		}
		try {
			if (poolType == 1) {
				SellPool sell=new SellPool();
				SellPool outsell=sellPollBo.getSellPoolById(poolId);
				sell.setId(poolId);
				sell.setPrice(price);
				sell.setQuantity(quantity);//
				sell.setPriceType(priceType.toString());
				if(outsell!=null&&outsell.getMallSaleId()!=null){//如果已经发布到商城  同时修改商场报盘价 （卖盘）
				Map<String, Object> maps=buypoolbo.updateMallPrice(outsell.getSortNum(),price, 2, outsell.getMallSaleId().toString(),quantity);
				if(maps!=null&&"erron".equals(maps.get("code"))){
					map.put("code", "error");
					map.put("msg", maps.get("msg"));
					return map;
				}
				}
				sellPollBo.updateSellPool(sell, user);
//				sellPollBo.updateSellPool_moca(sell, user);
			} else if (poolType == 2) {
				BuyPoolEntity buy=new BuyPoolEntity();
				BuyPoolEntity outbuy=buypoolbo.getbuypoolID(poolId);
				buy.setId(poolId);
				buy.setPrice(String.valueOf(price));
				buy.setQuantit(Double.valueOf(quantity.toString()));
				buy.setPriceType(priceType);
				buy.setUpdatedBy(user.getId());
				if(outbuy!=null&&outbuy.getMallid()!=null){//如果已经发布到商城  同时修改商场报盘价 （买盘）
				//买盘没有“编号”排序的概念，随便添加一个11
				Map<String, Object> maps=buypoolbo.updateMallPrice(11,price, 1,outbuy.getMallid(),quantity);
				
				if(maps!=null&&"erron".equals(maps.get("code"))){
					map.put("code", "error");
					map.put("msg", maps.get("msg"));
				}
				}
				buypoolbo.updateBuyPool(buy);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "error");
			map.put("msg", "修改失败");
			return map;

		}
		map.put("code", "success");
		map.put("msg", "修改成功");
		return map;
	}

}
