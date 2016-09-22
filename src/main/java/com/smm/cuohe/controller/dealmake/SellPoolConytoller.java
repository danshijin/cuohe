package com.smm.cuohe.controller.dealmake;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.IItemAttrBO;
import com.smm.cuohe.bo.IOrdersBO;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.IBuyOrderBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.Purchase;
import com.smm.cuohe.domain.SellPublish;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChCommodityAttrEntity;
import com.smm.cuohe.domain.base.ChCommodityEntity;
import com.smm.cuohe.domain.base.ChWareHouse;
import com.smm.cuohe.domain.dealmake.CommodityEntity;
import com.smm.cuohe.domain.dealmake.ProductEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.Warehouse;
import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.ExcelUtil;
import com.smm.cuohe.util.ExportUtil;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;

/**
 * Title: DealMakeConytoller.java Description: 撮合管理
 * 
 * @author Nancy/张楠
 * @created 2015年8月4日 上午9:34:20
 */

@Controller
@RequestMapping("/dealMake")
public class SellPoolConytoller {
	private static Logger logger = Logger.getLogger(SellPoolConytoller.class
			.getName());
	@Resource
	private ISellPoolBO sellPollBo;
	@Resource
	private ICompanyBO companyBO;
	@Resource
	private IItemAttrBO itemAttrBO;
	@Resource
	private IAttrValuesBO attrValuesBO;
	@Resource
	private IAreasBO iAreasBO;
	@Resource
	private BuyPoolBo buypoolbo;
	@Resource
	private IBuyOrderBO iBuyOrderBO;
	@Resource
	private IOrdersBO iOrdersBO;
	@Resource
	private RestTemplate restTemplate;
	@Value("#{ch['publicitem.URL']}")
	private String publicitem;
	@Value("#{ch['checkSeller.URL']}")
	private String checkSeller;
	@Value("#{ch['producter.URL']}")
	private String producter;

	/**
	 * @discription 跳转到卖盘池页面(附带卖盘池数据查询)
	 * @author Nancy/张楠
	 * @created 2015年8月4日 上午9:37:06
	 */

	/**
	 * @return
	 * @discription 新增卖盘池页面
	 * @author Nancy/张楠
	 * @created 2015年8月4日 下午2:07:22
	 */
	@RequestMapping("/newSellPool")
	public ModelAndView newSellPool(HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute("userInfo");
		List<Areas> arealist = iAreasBO.getParentAreas();
		ModelAndView view = new ModelAndView("dealMake/newSellPool");

		List<ProductEntity> prodList = attrValuesBO.getproductitemId(user
				.getItems().getId());

		List<ItemAttr> itemAttrList = itemAttrBO.getItemAttrByItemsId(user
				.getItems().getId());
		// 可选项
		if (itemAttrList != null && itemAttrList.size() > 0) {
			for (int i = 0; i < itemAttrList.size(); i++) {
				if (itemAttrList.get(i).getOptions() != null) {
					String[] option = itemAttrList.get(i).getOptions()
							.split(",");
					List<String> list = new ArrayList<String>();
					for (int j = 0; j < option.length; j++) {
						list.add(option[j]);
					}
					itemAttrList.get(i).setOptionsValue(list);
				}
			}
		}
		String only=UUID.randomUUID().toString().replace("-", "");
		req.getSession().setAttribute(only,only);
		view.addObject("only",only);
		view.addObject("prodList", prodList);
		view.addObject("itemAttrList", itemAttrList);
		view.addObject("arealist", arealist);
		view.addObject("user", user);
		view.addObject("itemId", user.getItems().getId());
		return view;
	}

	/**
	 * 商品
	 * 
	 * @param req
	 * @param prodId
	 * @return
	 */
	@RequestMapping(value = "/querycommodity")
	public ModelAndView querycommodity(HttpServletRequest req, Integer prodId) {
		User user = (User) req.getSession().getAttribute("userInfo");
		List<ItemAttr> attrnamelist = itemAttrBO.getItemAttrByItemsId(user
				.getItems().getId());

		String name = attrnamelist.get(0).getName();
		System.out.println(name);
		List<CommodityEntity> commlist = attrValuesBO
				.getcommodityproductId(prodId);
		List<AttrValue> attrvaluelist = null;
		if (commlist != null) {
			for (int i = 0; i < commlist.size(); i++) {

				attrvaluelist = attrValuesBO.getcommodityattr(commlist.get(i)
						.getId());
				commlist.get(i).setAttrValuelist(attrvaluelist);

			}
		}
		ModelAndView view = new ModelAndView("dealMake/commodityList");
		view.addObject("attrnamelist", attrnamelist);
		view.addObject("commlist", commlist);
		return view;
	}

	/**
	 * @param companyName
	 * @return
	 * @throws UnsupportedEncodingException
	 * @discription 查询公司, 调用接口
	 * @author Nancy/张楠
	 * @created 2015年8月10日 上午8:53:46
	 */
	@RequestMapping(value = "/queryCompany", produces = "application/json")
	@ResponseBody
	public List<Company> queryCompany(String companyName) {

		List<Company> result = new ArrayList<Company>();
		result = companyBO.getCompanyByParam(companyName);
		return result;
	}

	/**
	 * @param companyName
	 * @return
	 * @throws UnsupportedEncodingException
	 * @discription 查询公司，不调用接口
	 * @author Nancy/张楠
	 * @created 2015年8月19日 下午4:41:50
	 */
	@RequestMapping(value = "/queryCompanyByNm", produces = "application/json")
	@ResponseBody
	public List<Company> queryCompanyByNm(String companyName,
			HttpServletRequest req) throws UnsupportedEncodingException {
		User user = (User) req.getSession().getAttribute("userInfo");
		Map<String, Object> map = new HashMap<>();
		map.put("name", companyName);// 企业名称
		map.put("itemId", user.getItems().getId());// 企业名称
		List<Company> companyList = companyBO.queryCompanyByNm(map);
		ModelAndView view = new ModelAndView("dealMake/newSellPool");
		view.addObject("companyList", companyList);
		return companyList;
	}

	/**
	 * @return
	 * @discription 新增卖盘池到数据库
	 * @author Nancy/张楠 
	 * @created 2015年8月4日 下午4:17:59
	 */
	@RequestMapping(value = "/addSellPool", method = RequestMethod.POST)
	@ResponseBody
	public Object addSellPool(SellPool sellPool, HttpServletRequest request) {
		String only=request.getParameter("only").toString();
		Object onje=request.getSession().getAttribute(only);
		if(onje !=null){
			request.getSession().removeAttribute(only);
			// 获取扩展属性信息
			String[] attrName = request.getParameterValues("arrtName");
			String[] fillmode = request.getParameterValues("fillmode");

			String[] id = request.getParameterValues("ids");
			User user = (User) request.getSession().getAttribute("userInfo");
			Warehouse warehouse = this.sellPollBo.queryWarehouseById(sellPool.getWareId());
			
			sellPool.setWareName(warehouse.getName());
			sellPool.setPriceType("0");
			String proId = request.getParameter("prodId");
			String prodName = request.getParameter("prodName");
			//报价员ID
			Integer offerer = Integer.parseInt(request.getParameter("offerer"));
			List<AttrValue> attrvalueList = new ArrayList<>();
			if (id != null && id.length > 0) {
				for (int i = 0; i < id.length; i++) {
					AttrValue attr = new AttrValue();
					if (proId != null && !"".equals(proId)) {
						attr.setProdId(Integer.valueOf(proId));//产品编号
					}
					if (attrName != null && attrName.length > i) {
						if (attrName[i] == null || "".equals(attrName[i])) {
							continue;
						}
						attr.setAttrName(attrName[i]);

					}
					if (fillmode != null && "2".equals(fillmode[i])) {
						String[] values = request.getParameterValues("attrValue"
								+ id[i]);
						String value = "";
						if (values != null) {
							for (String str : values) {
								value = value + str + ",";
							}
							if (!"".equals(value)) {
								if (attrName[i] == null || "".equals(attrName[i])) {
									continue;
								}
								attr.setAttrValue(value.substring(0,value.length() - 1));
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

				//商城不存在商品，把撮合端商品同步到商城
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
				commodity.setCreateTime(DateUtil.doFormatDate(new Date(),
						"yyyy-MM-dd"));
				attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
				commodityId = commodity.getId();
				for (int i = 0; i < attrvalueList.size(); i++) {
					if (attrvalueList.get(i).getAttrValue() == null
							|| "".equals(attrvalueList.get(i).getAttrValue())) {
						continue;
					}
					AttrValue attrvalue = attrvalueList.get(i);
					if (attrvalue != null) {
						attrvalue.setCommodityId(commodityId);
						attrvalue.setCommodityName(prodName);
						attrvalue.setCreaTime(DateUtil.doFormatDate(new Date(),
								"yyyy-MM-dd"));
						attrvalue.setEditTime(DateUtil.doFormatDate(new Date(),
								"yyyy-MM-dd"));
						attrValuesBO.addCommodityAttr(attrvalue);// 添加商品属性
					}
				}
			}
			sellPool.setUsername(user.getName());
			sellPool.setCommodityId(commodityId);
			sellPool.setMallUserAccount(account);
			sellPool.setItemsid(user.getItems().getId());
			String flag = request.getParameter("flag");
			//报盘价格类型 ，默认实价
			sellPool.setPriceType("0");
			if (flag != null && "fabu".equals(flag)) { // 判断是否发布
				sellPool = addsellpoolmall(sellPool);// 卖盘同步商城 得到挂牌号
				if (1 == sellPool.getErrno()) {
					return sellPool.getMsg();
				}
			}
			sellPool.setSource(0);
			sellPool.setCreatedby(offerer);
			sellPool.setUpdatedby(offerer);
			
			String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
			
			if (sellPool.getSortNum() == null) {
				nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+0;
			}else {
				if (sellPool.getSortNum() >= 0 && sellPool.getSortNum() <=9) {
					nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+sellPool.getSortNum();
				}else {
					nowTimeTemp = nowTimeTemp.replace("-", "")+""+sellPool.getSortNum();
				}
			}
			sellPool.setTimeSort(nowTimeTemp);
			
			sellPollBo.addSellPool(sellPool, user);
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
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
			if (attrValue.get(i).getAttrValue() != null
					&& !"".equals(attrValue.get(i).getAttrValue())) {
				param.add(StringUtil.doEncoder(attrValue.get(i).getAttrName()),
						StringUtil.doEncoder(attrValue.get(i).getAttrValue()));
			}

		}
		Integer totalmallid = -1;
		param.add("pid", prodId);
		try {
			logger.info("开始调用：验证商品是否存在接口   参数："+param.toString());
			
			printMessage("接口地址：" + checkSeller + "， 参数：" + param.toString());
			String jsonString = restTemplate.postForObject(checkSeller, param,
					String.class);
			printMessage(jsonString);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
				logger.info("验证商品是否存在返回数据"+maps.toString());
				if (1 == errno) {// 参数错误

					totalmallid = -1;
				} else if (0 == errno) {
					Integer msg = (Integer) maps.get("msg");
					if (msg == 1) {
						totalmallid = Integer.valueOf(String.valueOf(maps
								.get("totalmallid")));

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
	public SellPool producter(List<AttrValue> attrValue, String prodName,
			User user, String account) {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

		param.add("catid", user.getItems().getId());
		param.add("catname", StringUtil.doEncoder(user.getItems().getName()));
		param.add("name", StringUtil.doEncoder(prodName));
		param.add("username", StringUtil.doEncoder(account));
		for (int i = 0; i < attrValue.size(); i++) {
			if (attrValue.get(i).getAttrValue() != null
					&& !"".equals(attrValue.get(i).getAttrValue())) {
				param.add(StringUtil.doEncoder(attrValue.get(i).getAttrName()),
						StringUtil.doEncoder(attrValue.get(i).getAttrValue()));
			}

		}

		SellPool sellPool = new SellPool();

		try {
			//printMessage("接口地址："+ producter +"， 接口参数：" + param.toString());
			
			logger.info("开始调用：添加商品接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(producter, param,
					String.class);
			printMessage(jsonString);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			
			System.err.println(StringUtil.doDecoder(jsonString));
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
		
		
		
		//期货月
		param.add("fumonth", sellPool.getFuturesMonth());
		
		//报盘价格类型 ，默认实价
		param.add("pricetype", (sellPool.getPriceType()!=null)?sellPool.getPriceType():0);//不存在默认为0
		param.add("totalmallid", sellPool.getCommodityId());
		param.add("warehouseid", sellPool.getWareId());
		param.add("price", sellPool.getPrice());
		param.add("areaid", sellPool.getWareProvince());// 地区id
		param.add("count", sellPool.getQuantity());
		param.add("paytype", sellPool.getPaytype());
		param.add("receipttype", sellPool.getReceipttype());
		param.add("delivery", sellPool.getDelivery());
		param.add(
				"deliverytime",
				DateUtil.doSFormatDate(sellPool.getDeliverytime(), "yyyy-MM-dd")
						.getTime() / 1000);
		param.add("moq", sellPool.getMoq());
		param.add("outmoq", sellPool.getOutmoq());
		param.add("overflow", sellPool.getOverflow());
		param.add("username",
				StringUtil.doEncoder(sellPool.getMallUserAccount()));

		param.add("producedate",
				DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
		param.add("instoragedate",
				DateUtil.doFormatDate(new Date(), "yyyy-MM-dd"));
		param.add("catid", sellPool.getItemsid());
		
		
		String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
		
		//排序“编号” 更新时间+编号
		if (sellPool.getSortNum() == null ) {
			nowTimeTemp = nowTimeTemp.replace("-", "")+"00";
		}else {
			if (sellPool.getSortNum() >= 0 && sellPool.getSortNum() <=9) {
				nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+sellPool.getSortNum();
			}else {
				nowTimeTemp = nowTimeTemp.replace("-", "")+""+sellPool.getSortNum();
			}
		}
		param.add("order", nowTimeTemp);
		
		if (sellPool != null && sellPool.getUnit() != null
				&& Integer.valueOf(sellPool.getUnit()) == 0) {
			param.add("unit", StringUtil.doEncoder("吨"));
		} else {
			param.add("unit", StringUtil.doEncoder("千克"));
		}

		param.add("sheetnum", sellPool.getQuantity());

		try {
			logger.info("开始调用：新增卖盘同步到商城接口   参数："+param.toString());
			
			printMessage("接口地址：" + publicitem + "， 参数：" + param.toString());
			String jsonString = restTemplate.postForObject(publicitem, param,
					String.class);
			printMessage("返回结果：" + jsonString);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
				logger.info("新增卖盘同步到商城返回数据"+maps.toString());
				
				if (1 == errno) {// 参数错误2
					sellPool.setErrno(1);
					sellPool.setMsg("同步商城接口调用异常："
							+ String.valueOf(maps.get("mallid")));
				} else if (255 == errno) {// 参数错误
					sellPool.setErrno(1);
					sellPool.setMsg("同步商城接口调用异常："
							+ String.valueOf(maps.get("mallid")));
				} else if (0 == errno) {//
					sellPool.setErrno(0);
					Object mallid = maps.get("mallid");
					sellPool.setMallSaleId(Integer.valueOf(mallid.toString()));
					sellPool.setMsg("发布商城成功");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			sellPool.setErrno(1);
			sellPool.setMsg("同步商城接口调用异常");
		}
		return sellPool;
	}

	/**
	 * @return
	 * @discription 返回卖盘池页面
	 * @author Nancy/张楠
	 * @created 2015年8月6日 下午4:19:59
	 */
	@RequestMapping(value = "/returnSellOrder")
	@ResponseBody
	public Object returnSellOrder() {
		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	/**
	 * @param type
	 *            创建人员（0 全部 1 本人创建 2部门创建）
	 * @param pno
	 *            当前页
	 * @return
	 * @discription
	 * @author Nancy/张楠
	 * @throws UnsupportedEncodingException 
	 * @created 2015年8月10日 下午1:32:15
	 */
	@RequestMapping("/sellOrder")
	public ModelAndView querySellOrderList(String type, Integer pno,
			String parameter1, String parameter2, String parameter3,
			HttpServletRequest request) throws UnsupportedEncodingException {

		
		if (parameter3 != null) {
			parameter3 = URLDecoder.decode( parameter3 ,"utf-8");
		}
		
		User user = (User) request.getSession().getAttribute("userInfo");
		Map<String, Object> parameters = new HashMap<String, Object>();

		if (pno == null) {
			pno = 1;
		}
		int totalRecords = 0;
		ModelAndView view = new ModelAndView("dealMake/sellOrder");
		int itemsID = user.getItems().getId();
		// 商品属性
		List<ItemAttr> itemAttrList = itemAttrBO.getItemAttrByItemsId(itemsID);
		// 查询条件
		parameters.put("type", type);
		parameters.put("pno", pno);
		parameters.put("userID", user.getId());
		parameters.put("itemsID", itemsID);
		List<SellPool> sellPoolListCount = sellPollBo.querySellOrderList(
				parameters, type, parameter1, parameter2, parameter3);

		if (sellPoolListCount != null && !sellPoolListCount.isEmpty()) {
			totalRecords = sellPoolListCount.size();
		}
		// 分页
		PageBean page = new PageBean(10, pno, totalRecords);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		parameters.put("startNum", startNum);
		parameters.put("endNum", endNum);

		List<SellPool> sellPoolList = sellPollBo.querySellOrderList(parameters,
				type, parameter1, parameter2, parameter3);
		for (int i = 0; i < sellPoolList.size(); i++) {
			if (sellPoolList.get(i).getCommodityId() != null) {

				List<AttrValue> attrValuesList = attrValuesBO
						.getAttrValuesByExtId(sellPoolList.get(i)
								.getCommodityId());

				sellPoolList.get(i).setAttrValues(attrValuesList);
			}

			if (sellPoolList.get(i).getOrderNum() != 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("sellId", sellPoolList.get(i).getCompanyid());
			}
		}
		if (parameter1 == null) {
			parameter1 = "0";
		}
		if (parameter2 == null) {
			parameter2 = "0";
		}
		view.addObject("itemAttrList", itemAttrList);
		view.addObject("sellPoolList", sellPoolList);
		view.addObject("totalRecords", totalRecords);// 总条数
		view.addObject("totalPage", page.getTotalPages());// 总页数
		view.addObject("parameter1", parameter1);
		view.addObject("parameter2", parameter2);
		view.addObject("parameter3", parameter3);

		// 保存价格类型
		view.addObject("productType", user.getItems().getSHFEType()
				.toUpperCase());

		
		Integer itemId = Integer.parseInt(user.getItemId());
		parameters.put("itemId", itemId);
		//当商品是银的时候，页面价格单位是银
		Items items = this.itemAttrBO.getItemsById(parameters);
		parameters.put("itemId", itemId);
		
		view.addObject("items", items);
		return view;
	}

	/**
	 * @param ids
	 * @discription 批量处理
	 * @author Nancy/张楠
	 * @created 2015年8月10日 下午4:16:54
	 */
	@RequestMapping("/delAll")
	@ResponseBody
	public Map<String, Object> delAll(String ids) {
		sellPollBo.delAll(ids);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "succ");
		map.put("msg", "系统提示，操作成功");
		return map;
	}

	/**
	 * 卖盘池发布到商城,跳转页面
	 * 
	 * @return
	 * @discription
	 * @author Nancy/张楠
	 * @created 2015年8月13日 下午4:00:32
	 */
	@RequestMapping("/publishSellPool")
	@ResponseBody
	public Map<String, Object> publishSellPool(Integer sellPoolId,Integer sortNum) {
		//
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SellPool sellPool = this.sellPollBo.getSellPoolById(sellPoolId);
			sellPool.setSortNum(sortNum);
			// 卖盘池相关数据
			sellPool= addsellpoolmall(sellPool);
			if (1 == sellPool.getErrno()) {
				result.put("code", "erron");
				result.put("msg", sellPool.getMsg());
				return result;

			}
			
			String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
			
			if (sortNum >= 0 && sortNum <=9) {
				nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+sellPool.getSortNum();
			}else {
				nowTimeTemp = nowTimeTemp.replace("-", "")+""+sellPool.getSortNum();
			}
			sellPool.setTimeSort(nowTimeTemp);
			//更新时间
			sellPool.setUpdatedat(new Date());
			
			sellPollBo.fabuSellpool(sellPool);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "erron");
			result.put("msg", "未知异常");
		}
		result.put("code", "ok");
		result.put("msg", "发布成功");
		return result;
	}

	/**
	 * 获取SHFE实时价格
	 * 
	 * @return
	 */
	@RequestMapping("/getPriceProduct")
	@ResponseBody
	public Map<String, Object> getPriceProduct(HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		if (request.getSession().getAttribute("userInfo").equals(null)) {
			// TODO 请先登录
			result.put("code", "login");
		} else {
			User user = (User) request.getSession().getAttribute("userInfo");
			result = this.sellPollBo.getPriceProduct(user);
			result.put("code", "succ");
		}
		// 更新时间
		result.put("updateTime", DateUtil.getDateWidthFormat(null));
		result.put("updateTime", DateUtil.getDateWidthFormat(null));
		return result;
	}

	/**
	 * 卖盘池发布到商城
	 * 
	 * @param sellPublish
	 * @param request
	 * @discription
	 * @author Nancy/张楠
	 * @created 2015年8月14日 下午4:06:25
	 * @retur
	 */
	@RequestMapping(value = "/addSellPoolPublish", method = RequestMethod.POST)
	@ResponseBody
	public Object addPublish(HttpServletRequest request, SellPublish sellPublish) {
		Map<String, Object> param = null;
		try {
			User user = (User) request.getSession().getAttribute("userInfo");
			// 入库
			sellPollBo.addSellPublish(sellPublish, user);
			// 发布到商城
			String jsonStr = sellPollBo.sellPoolPublish(sellPublish, user);
			param = JSONUtil.doConvertJson2Map(jsonStr);
			
			logger.info("发布报盘信息到商城返回结果："+param);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		if (param.get("code").equals("success")) {
			return ResultMessage.ADD_SUCCESS_RESULT;
		} else {
			return ResultMessage.PUBLISH_FAIL_RESULT;
		}

	}

	// 导入excel
	@RequestMapping(value = "/importExcel")
	@ResponseBody
	public String importExcel(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException {
		InputStream stream = null;
		String fileName = "";
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					fileName = file.getOriginalFilename().substring(
							file.getOriginalFilename().lastIndexOf("."));
					stream = file.getInputStream();
					break;
				}
			}
		}
		Map<String, String> dataExcelMapping = new HashMap<>();
		dataExcelMapping.put("序号", "excelNum");
		dataExcelMapping.put("品目名称", "itemName");
		dataExcelMapping.put("品目产品名称", "productName");
		dataExcelMapping.put("企业名称", "companyName");
		dataExcelMapping.put("国别", "country");
		dataExcelMapping.put("品牌", "brand");
		dataExcelMapping.put("产地", "place");
		dataExcelMapping.put("库存", "quantity");
		dataExcelMapping.put("仓库地区名称", "wareAreaNam");
		dataExcelMapping.put("仓库名称", "wareHouseName");
		dataExcelMapping.put("价格", "price");
		dataExcelMapping.put("起订量", "moq");
		dataExcelMapping.put("超出部分按n递增", "outmoq");
		dataExcelMapping.put("磅差", "overflow");
		dataExcelMapping.put("交付方式", "delivery");
		dataExcelMapping.put("结算方式", "paytype");
		dataExcelMapping.put("付款方式", "receipttype");
		dataExcelMapping.put("交货日期", "deliverytime");
		dataExcelMapping.put("计量单位", "unit");
		dataExcelMapping.put("优先级", "priority");
		// dataExcelMapping.put("负责人姓名", "userName");
		// dataExcelMapping.put("负责人Email", "userEmail");
		ExcelUtil excelUtil = new ExcelUtil();
		List<Map<String, String>> dataInfo = null;
		String result = "";
		if (fileName == ".xls") {
			// 2003
			try {
				//dataInfo = excelUtil.parseExcel2003(stream, dataExcelMapping);
				dataInfo = excelUtil.parseExcel2003(stream);//暂时不需要预先设置好的mapping
			} catch (Exception e) {
				e.printStackTrace();
				result = "文件导入格式不正确，请确认。\r\n";
			}
		} else {
			// 2007
			try {
				//dataInfo = excelUtil.parseExcel2007(stream, dataExcelMapping);
				dataInfo = excelUtil.parseExcel2007(stream);//暂时不需要预先设置好的mapping
			} catch (Exception e) {
				e.printStackTrace();
				result = "文件导入格式不正确，请确认。\r\n";
			}
		}

		User user = (User) request.getSession().getAttribute("userInfo");
		if (user == null) {
			result += "请重新登录系统。\r\n";
		}
		if (dataInfo != null)
			result = this.sellPollBo.sellPoolImport(dataInfo, user);
		return result;
	}

	@RequestMapping("sellPoolExport")
	public void sellPoolExport(String type, String parameter1,
			String parameter2, String parameter3, HttpServletRequest request,
			String ids, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		// 1.根据传入id获取对应数据
		/*
		 * String[] sellPoolInfo = ids.split(","); User user = (User)
		 * request.getSession().getAttribute("userInfo"); Map<String, Object>
		 * mapData = this.sellPollBo.getListSellPoolById( sellPoolInfo, user);
		 */

		User user = (User) request.getSession().getAttribute("userInfo");
		Map<String, Object> parameters = new HashMap<String, Object>();

		int itemsID = user.getItems().getId();
		// 查询条件
		parameters.put("type", type);
		parameters.put("userID", user.getId());
		parameters.put("itemsID", itemsID);
		parameters.put("ids", ids);

		if (StringUtils.isNoneBlank(parameter3)) {

			parameter3 = StringUtil.doDecoder(parameter3);
		}

		Map<String, Object> mapData = this.sellPollBo.getListSellPoolById(
				parameters, type, parameter1, parameter2, parameter3);

		// 2.生成excel文件
		ExportUtil<Purchase> excel = new ExportUtil<Purchase>();
		String fileName = "卖盘池";

		List<String> listHeader = (List<String>) mapData.get("listHeader");
		List<LinkedHashMap<String, String>> listData = (ArrayList<LinkedHashMap<String, String>>) mapData
				.get("listData");

		// 获得数据

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		excel.export("sheet1", listHeader, listData, os);

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}

	}

	
	/** 根据省份来查询仓库
	 * @Title: selWarehouseByArea 
	 * @Description: TODO
	 * @param areaId
	 * @param req
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<Warehouse>
	 */
	@RequestMapping(value = "/selWarehouseByArea")
	@ResponseBody
	public List<Warehouse> selWarehouseByArea(Integer areaId,HttpServletRequest req,String companyName) {
		User user = (User) req.getSession().getAttribute("userInfo");
		List<Warehouse> list = iBuyOrderBO.selWarehouseByArea(companyName,areaId,user.getItemId());
		return list;
	}
	
	/** 根据省份来查询仓库用于搜索下拉框 select2
	 * @Title: selWarehouseByAreaBlur 
	 * @Description: TODO
	 * @param areaId
	 * @param req
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<Warehouse>
	 */
	@RequestMapping(value = "/selWarehouseByAreaBlur")
	@ResponseBody
	public List<Warehouse> selWarehouseByAreaBlur(Integer areaId,HttpServletRequest req,String wareName,String companyName) {
		User user = (User) req.getSession().getAttribute("userInfo");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("areaId",areaId);
		param.put("wareName",wareName);
		param.put("companyName",companyName);
		param.put("itemId",Integer.parseInt(user.getItemId()));
		List<Warehouse> list = iBuyOrderBO.selWarehouseByAreaBlur(param);
		return list;
	}

	/**
	 * 隐藏
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws IOException
	 * @discription
	 * @author Nancy/张楠
	 * @created 2015年8月25日 下午5:01:49
	 */
	@RequestMapping(value = "/delSellPool")
	public void delSellPool(String id, HttpServletRequest req,
			HttpServletResponse response) throws IOException {

		this.sellPollBo.delAll(id);

		String redirectUrl = req.getContextPath() + "/dealMake/sellOrder.do";

		response.sendRedirect(redirectUrl);

	}

	/**
	 * 查看订单
	 * 
	 * @return
	 * @discription
	 * @author Nancy/张楠
	 * @created 2015年8月25日 下午5:04:22
	 */
	@RequestMapping(value = "/viewOrder")
	public ModelAndView viewOrder(Integer sellPoolId) {
		ModelAndView view = new ModelAndView("dealMake/viewOrder");
		List<Order> list = iOrdersBO.getOrderListBySellPoolId(sellPoolId);
		view.addObject("list", list);
		return view;
	}

	
	
	/** 
	 * @Title: toupdateSellPool 
	 * @Description: TODO
	 * @param sellPoolId
	 * @param req
	 * @return
	 * @author zhangnan/张楠
	 * @return: ModelAndView
	 * @createTime 2016年1月21日下午4:22:57  修改内容：
											修改报盘的时候除了“产品”“企业”“报价人”其它都可以修改
											修改报盘时可修改首页排序
											卖盘池列表页排序规则为：更新日期+编号+更新时间 倒排
	 */
	@RequestMapping(value = "/toupdateSellPool")
	public ModelAndView toupdateSellPool(Integer sellPoolId,
			HttpServletRequest req) {
		SellPool sellPool = this.sellPollBo.getSellPoolById(sellPoolId);
		User user = (User) req.getSession().getAttribute("userInfo");
		List<Areas> arealist = iAreasBO.getParentAreas();
		// 商品属性
		List<ItemAttr> itemAttrList = this.itemAttrBO.getItemAttrByItemsId(user.getItems().getId());
		
		//本报盘选定的产品属性  
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("commodity_id", sellPool.getCommodityId());
		List<ChCommodityAttrEntity> commodityAttrList = this.sellPollBo.queryCommodityAttrList(params);
		
		ChCommodityEntity commodity = this.sellPollBo.queryChCommodityEntityById(sellPool.getCommodityId());
		
		// 可选项
		if (itemAttrList != null && itemAttrList.size() > 0) {
			for (int i = 0; i < itemAttrList.size(); i++) {
				if (itemAttrList.get(i).getOptions() != null) {
					String[] option = itemAttrList.get(i).getOptions()
							.split(",");
					List<String> list = new ArrayList<String>();
					for (int j = 0; j < option.length; j++) {
						list.add(option[j]);//
					}
					itemAttrList.get(i).setOptionsValue(list);
				}
			}
		}
		
		// 商品属性值
		List<AttrValue> attrValue = this.attrValuesBO
				.getAttrValuesByExtId(sellPool.getCommodityId());
//		ModelAndView view = new ModelAndView("dealMake/updateSellPool");
		ModelAndView view = new ModelAndView("dealMake/updateSellPool_new");
		//修改的时候，判断是否发布
		if (sellPool.getMallSaleId() != null) {
			view.addObject("fabu", "yes");
		}else {
			view.addObject("fabu", "no");
		}
		
		view.addObject("prodId", commodity.getProdId());
		view.addObject("prodName", commodity.getName());
		view.addObject("sellPool", sellPool);
		view.addObject("itemAttrList", itemAttrList);
		view.addObject("attrValue", attrValue);
		view.addObject("arealist", arealist);
		
		view.addObject("itemId", user.getItems().getId());
		
		view.addObject("commodityAttrList", commodityAttrList);
		//报盘企业
		view.addObject("companyid", sellPool.getCustomerId());
		
		return view;
	}

	@RequestMapping(value = "/updateSellPool", method = RequestMethod.POST)
	@ResponseBody
	public Object updateSellPool(SellPool sellPool, HttpServletRequest request) {

		try {
			User user = (User) request.getSession().getAttribute("userInfo");
			SellPool outsell=sellPollBo.getSellPoolById(sellPool.getId());
			if(outsell!=null&&outsell.getMallSaleId()!=null){//如果已经发布到商城  同时修改商场报盘价 （卖盘）
				Map<String, Object> maps=buypoolbo.updateMallPrice(outsell.getSortNum(),sellPool.getPrice(), 2, outsell.getMallSaleId().toString(),sellPool.getQuantity());
				if(maps!=null&&"erron".equals(maps.get("code"))){
					return maps.get("msg"); 
				}
			
			}
			
			sellPool.setSortNum(outsell.getSortNum());
			
			sellPollBo.updateSellPool(sellPool, user);
			return ResultMessage.ADD_SUCCESS_RESULT;

		} catch (Exception e) {
			e.printStackTrace();
			return "未知异常";
		}

	}

	/**
	 * 根据ID查询企业信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getcompanyName", method = RequestMethod.POST)
	@ResponseBody
	public String getcompanyName(Integer id) {

		String companyName = sellPollBo.getcompanyName(id);
		return companyName;
	}
	
	private void printMessage(String msg){
		logger.info("\n" + msg +"\n");
	}
	
	@RequestMapping(value = "/checkUserPay", method = RequestMethod.POST)
	@ResponseBody
	public Integer checkUserPay(Integer customerId) {
		String account = sellPollBo.getcompanyAccount(customerId);
		Integer st=sellPollBo.checkUserPay(account);//检查 会员支付账号是否存在  0 存在  1 不存在
		
		return st;
	}
	
	/**撮合交易管理新建报盘, 发送到卖盘池,并同步到商城
	 * @Title: publishToSellPool 
	 * @Description: TODO
	 * @param poolId
	 * @return
	 * @author zhangnan/张楠
	 * @return: Map<String,Object>
	 * @createTime 2015年12月21日下午3:10:58
	 */
	@RequestMapping("publishToSellPool")
	@ResponseBody
	public Map<String,Object> publishToSellPool(Integer poolId,HttpServletRequest req) {
		Map<String,Object> map = new HashMap<String, Object>();
		SellPool sellPool = this.sellPollBo.getSellPoolById(poolId);
//		User user = (User) req.getSession().getAttribute("userInfo");
		try {
			sellPool = this.addsellpoolmall(sellPool);
			if (sellPool.getErrno() != 0) {
				map.put("success","ERROR");
				map.put("msg",sellPool.getMsg());
				return map;
			}
			sellPool.setCreateSource(2);
			this.sellPollBo.updateSellPoolCreateSource(sellPool);
			map.put("success","OK");
			map.put("msg","卖盘已同步到商城和卖盘池");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	/** 
	 * @Title: updateSellPoolSortNum 
	 * @Description: TODO
	 * @param sortNums 卖盘编号 字符串
	 * @param poolIds 卖盘ID 字符串
	 * @param request
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2016年1月6日下午4:14:21
	 */
	@RequestMapping("updateSellPoolSortNum")
	@ResponseBody
	public Map<String,Object> updateSellPoolSortNum(String sortNums,String poolIds,HttpServletRequest request) {
		Map<String,Object> param = new HashMap<String, Object>();
		String[] sortNumArray = sortNums.split(",");
		String[] poolIdArray = poolIds.split(",");
		try {
			param = this.sellPollBo.updateSellPoolSortNum(sortNumArray,poolIdArray,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;

	}
	
	
	/** 初始化仓库
	 * @Title: initWareHouse 
	 * @Description: TODO
	 * @return
	 * @author zhangnan/张楠
	 * @return: ChWareHouse
	 * @createTime 2016年1月21日下午6:04:29
	 */
	@RequestMapping("initWareHouse")
	@ResponseBody
	public ChWareHouse initWareHouse(Integer wareId) {
		ChWareHouse chWareHouse = this.sellPollBo.initWareHouse(wareId);
		return chWareHouse;
	}
	
	/** 
	 * @Title: updateSellPool_new 
	 * @Description: TODO
	 * @param sellPool
	 * @param request
	 * @return
	 * @author zhangnan/张楠
	 * @return: Object
	 * @createTime 2016年1月22日下午4:04:13
	 * 修改内容：
			修改报盘的时候除了“产品”“企业”“报价人”其它都可以修改
			修改报盘时可修改首页排序
			卖盘池列表页排序规则为：更新日期+编号+更新时间 倒排
	 * 
	 */
	@RequestMapping(value = "/updateSellPool_new", method = RequestMethod.POST)
	@ResponseBody
	public Object updateSellPool_new(SellPool sellPool, HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			User user = (User) request.getSession().getAttribute("userInfo");
			SellPool outsell=sellPollBo.getSellPoolById(sellPool.getId());
			
			// 获取扩展属性信息
			String[] attrName = request.getParameterValues("arrtName");
			String[] fillmode = request.getParameterValues("fillmode");

			String[] id = request.getParameterValues("ids");
			Warehouse warehouse = this.sellPollBo.queryWarehouseById(sellPool.getWareId());
			
			String proId = request.getParameter("prodId");
			String prodName = request.getParameter("prodName");
			
			sellPool.setWareName(warehouse.getName());
			List<AttrValue> attrvalueList = new ArrayList<>();
			if (id != null && id.length > 0) {
				for (int i = 0; i < id.length; i++) {
					AttrValue attr = new AttrValue();
					if (proId != null && !"".equals(proId)) {
						attr.setProdId(Integer.valueOf(proId));//产品编号
					}
					if (attrName != null && attrName.length > i) {
						if (attrName[i] == null || "".equals(attrName[i])) {
							continue;
						}
						attr.setAttrName(attrName[i]);

					}
					if (fillmode != null && "2".equals(fillmode[i])) {
						String[] values = request.getParameterValues("attrValue"
								+ id[i]);
						String value = "";
						if (values != null) {
							for (String str : values) {
								value = value + str + ",";
							}
							if (!"".equals(value)) {
								if (attrName[i] == null || "".equals(attrName[i])) {
									continue;
								}
								attr.setAttrValue(value.substring(0,value.length() - 1));
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
			String account = sellPollBo.getcompanyAccount(outsell.getCompanyid());
			CommodityEntity commodity = new CommodityEntity();
			
			
			if (commodityId == null) {// 商品不存在

				//商城商品不存在，撮合端商品同步到商城
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
				commodity.setCreateTime(DateUtil.doFormatDate(new Date(),
						"yyyy-MM-dd"));
				attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
				commodityId = commodity.getId();
				for (int i = 0; i < attrvalueList.size(); i++) {
					if (attrvalueList.get(i).getAttrValue() == null
							|| "".equals(attrvalueList.get(i).getAttrValue())) {
						continue;
					}
					AttrValue attrvalue = attrvalueList.get(i);
					if (attrvalue != null) {
						attrvalue.setCommodityId(commodityId);
						attrvalue.setCommodityName(prodName);
						attrvalue.setCreaTime(DateUtil.doFormatDate(new Date(),
								"yyyy-MM-dd"));
						attrvalue.setEditTime(DateUtil.doFormatDate(new Date(),
								"yyyy-MM-dd"));
						attrValuesBO.addCommodityAttr(attrvalue);// 添加商品属性
					}
				}
			}
			
			
			sellPool.setCommodityId(commodityId);
			sellPool.setMallUserAccount(account);
			sellPool.setItemsid(user.getItems().getId());
			//排序编号
			String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
			if (sellPool.getSortNum() == null) {
				nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+0;
			}else {
				if (sellPool.getSortNum() >= 0 && sellPool.getSortNum() <=9) {
					nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+sellPool.getSortNum();
				}else {
					nowTimeTemp = nowTimeTemp.replace("-", "")+""+sellPool.getSortNum();
				}
			}
			sellPool.setTimeSort(nowTimeTemp);
			
			
			String fabu = request.getParameter("fabu");
			if (fabu.equals("yes")) { // 判断是否发布
				sellPool.setMallSaleId(outsell.getMallSaleId());
				resultMap = this.sellPollBo.updateSellPoolMall(sellPool);
				if (resultMap.get("code").equals("ok")) {//修改同步商城成功
					this.sellPollBo.updateSellPool(sellPool, user);
				}else {//修改同步商城失败
					return resultMap;
				}
			}else  {
				this.sellPollBo.updateSellPool(sellPool, user);
				resultMap.put("code", "ok");
				resultMap.put("msg", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			resultMap.put("code", "error");
			resultMap.put("msg", "未知异常，请联系管理员");
			return resultMap;
		}
		
		return resultMap;
	}
}