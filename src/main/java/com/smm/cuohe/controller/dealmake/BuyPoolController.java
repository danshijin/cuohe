package com.smm.cuohe.controller.dealmake;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IItemAttrBO;
import com.smm.cuohe.bo.counts.TrialcountBo;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.IBuyOrderBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.BuyPoolAttr;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.BuypublishEntity;
import com.smm.cuohe.domain.dealmake.ProductEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.ToOrder;
import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.ExportUtil;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;

/*
 *atuth tantaigen 
 * 
 * 买盘池
 */
@Controller
@RequestMapping(value = "/buypool")
public class BuyPoolController {

	private static final Logger logger = Logger.getLogger(BuyPoolController.class);

	@Resource
	BuyPoolBo buypoolbo;
	@Resource
	TrialcountBo trialcountBo;
	@Resource
	IAreasBO iAreasBO;
	@Resource
	IBuyOrderBO ibuyOrderBo;

	@Resource
	IItemAttrBO itemAttrBO;
	@Resource
	IAttrValuesBO attrValuesBO;

	@Resource
	private ISellPoolBO sellPollBo;
	@Resource
	private RestTemplate restTemplate;
	@Value("#{ch['publishMall.URL']}")
	private String publishMallUrl;

	@Resource
	private ICompanyBO iCompanyBO;
	@Value("#{ch['adddemall.URL']}")
	private String adddemall;

	@Resource
	private ICustomerBO iCustomerBO;

	/**
	 * ======= 买家盘列表
	 * 
	 */
	@RequestMapping(value = "/buylist")
	public ModelAndView buylist(HttpServletRequest req, Integer pno) {
		User user = (User) req.getSession().getAttribute("userInfo");
		Map<String, Object> map = new HashMap<String, Object>();

		// falg 表示用户查看的是那个列表 全部 falg=0|我的客户 falg=1|部门客户 falg=2
		String falg = req.getParameter("falg");

		if (falg == null)
			falg = "0";

		// 如果查看我的客户，设置当前用户id为查询条件
		if ("1".equals(falg)) {
			map.put("userid", user.getId());
		}

		// 如果查看部门客户，设置查询条件为按部门查询
		if ("2".equals(falg)) {
			map.put("dept", 2);
		}

		map.put("itemId", user.getItems().getId());
		Integer count = buypoolbo.countbuypool(map);

		if (pno == null) {
			pno = 1;
		}

		PageBean page = new PageBean(10, pno, count);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();

		map.put("startNum", startNum);
		map.put("endNum", endNum);

		List<BuyPoolEntity> buypoollist = buypoolbo.buypoollist(map);
		ModelAndView view = new ModelAndView("dealMake/buypool");

		view.addObject("totalRecords", count);// 总条数

		view.addObject("totalPage", page.getTotalPages());// 总页数

		view.addObject("buypoollist", buypoollist);
		view.addObject("falg", falg);

		// 保存价格类型
		view.addObject("productType", user.getItems().getSHFEType().toUpperCase());
		
		Integer itemId = Integer.parseInt(user.getItemId());
		map.put("itemId", itemId);
		//当商品是银的时候，页面价格单位是银
		Items items = this.itemAttrBO.getItemsById(map);
		map.put("itemId", itemId);
		
		view.addObject("items", items);

		return view;

	}

	/**
	 * 准备添加买盘数据
	 * 
	 */
	@RequestMapping(value = "/toaddbuypool")
	public ModelAndView toaddbuypool(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("dealMake/addbuypool");
		User user = (User) req.getSession().getAttribute("userInfo");
		/*
		 * List<TrailEntity> itemslist=trialcountBo.itemslist();
		 * view.addObject("itemslist",itemslist);
		 */
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
		String only=UUID.randomUUID().toString().replace("-", "");
		req.getSession().setAttribute(only,only);
		view.addObject("only", only);
		view.addObject("prodList", prodList);
		view.addObject("arealist", arealist);
		view.addObject("itemAttrList", itemAttrList);
		view.addObject("user", user);
		return view;
	}

	
	/** 添加买盘,入库
	 * @Title: addbuyPool 
	 * @Description: TODO
	 * @param req
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author zhangnan/张楠
	 * @return: Object
	 * @createTime 2016年1月4日下午3:33:11
	 */
	@RequestMapping(value = "/addbuyPool", method = RequestMethod.POST)
	@ResponseBody
	public Object addbuyPool(HttpServletRequest req, HttpServletResponse response)throws ServletException, IOException {

		String only=req.getParameter("only").toString();
		Object obje=req.getSession().getAttribute(only);
		if(obje !=null){
			req.getSession().removeAttribute(only);
			User user = (User) req.getSession().getAttribute("userInfo");
			BuyPoolEntity buy = new BuyPoolEntity();

			String quantitstr = req.getParameter("quantit"); // 数量,库存
			String customerId = req.getParameter("customerId"); // 客户编号

			String title = req.getParameter("title"); // 标题
			String context = req.getParameter("context");// 内容
			String unti = req.getParameter("unit"); // 单位 0 吨 1千克
			//报价员ID
			Integer offerer = Integer.parseInt(req.getParameter("offerer"));
			
			// 通过客户获取对应商城会员账号
			Customer customer = this.iCustomerBO.getOneById(Integer.parseInt(customerId));

			String mall_member_account = customer.getAccount();
			buy.setPriceType(0);
			buy.setPrice(req.getParameter("price"));
			buy.setArea(Integer.valueOf(req.getParameter("area")));
			buy.setItemsID(Integer.valueOf(user.getItemId()));
			buy.setQuantit(Double.valueOf(quantitstr));
			buy.setTitle(title);
			buy.setContext(context);
			buy.setStatus(0);
			buy.setCreatedAt(new Date());
			//buy.setCreatedBy(user.getId());
			buy.setCreatedBy(offerer);
			buy.setMallUserAccount(mall_member_account);
			buy.setProStatus("0");
			buy.setUnit(unti);
			buy.setCustomerId(customer.getId());
			buy.setUpdatedAt(new Date());
			//buy.setUpdatedBy(user.getId());
			buy.setUpdatedBy(offerer);
			String prodName = req.getParameter("prodName");
			String flag = req.getParameter("flag");
			if (flag != null && "fabu".equals(flag)) {

				buy = adddemall(buy, prodName);// 买盘同步商城
				if (buy != null && buy.getErrno() != null && !buy.getErrno().equals("0")) {
					return buy.getMsg();
				}
			}
			int count = buypoolbo.addbuyPool(buy);

			if (count == 1) {// 表示买盘添加成功
				String id[] = req.getParameterValues("ids");
				String proId = req.getParameter("prodId");

				// 获取扩展属性信息
				String attrName[] = req.getParameterValues("arrtName");
				String fillmode[] = req.getParameterValues("fillmode");

				addbuyAttr(req, id, proId, prodName, attrName, fillmode, user, buy);
			}
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	// 买盘同步商城接口调用
	public BuyPoolEntity adddemall(BuyPoolEntity buy, String prodName) {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		//报盘价格类型 ，默认实价
		param.add("pricetype", 0);
		param.add("catid", buy.getItemsID());//采购商品ID
		param.add("title", StringUtil.doEncoder(prodName));//要采购的商品名字
		param.add("introduce", StringUtil.doEncoder(buy.getContext()));//采购内容
		param.add("amount", buy.getQuantit());//采购量
		param.add("price", buy.getPrice());//采购价
		param.add("areaid", buy.getArea());//地区ID
		if (buy != null && buy.getUnit() != null && Integer.valueOf(buy.getUnit()) == 0) {
			param.add("unit", StringUtil.doEncoder("吨"));
		} else {
			param.add("unit", StringUtil.doEncoder("千克"));
		}
		param.add("name", buy.getMallUserAccount());//商城会员账号

		try {
			logger.info("开始调用：新建买盘商城同步接口  参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(adddemall, param, String.class);

			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
				logger.info("新建买盘商城同步返回数据："+maps.toString());
				
				if (2 == errno) {// 参数错误
					buy.setErrno("1");
					buy.setMsg("同步商城接口调用异常：" + String.valueOf(maps.get("success")));
				} else if (255 == errno) {// 参数错误
					buy.setErrno("1");
					buy.setMsg("同步商城接口调用异常：" + String.valueOf(maps.get("success")));
				} else if (0 == errno) {// 参数错误
					buy.setErrno("0");
					Object mallid = maps.get("demallid");
					buy.setMallid(mallid.toString());
					buy.setMsg("同步商城成功");
				}

			}

		} catch (Exception e) {
			logger.error("调用新建买盘商城同步接口出错");
			e.printStackTrace();
			buy.setErrno("1");
			buy.setMsg("同步商城接口调用异常");
		}

		return buy;
	}

	// 暂时保存在买盘属性表
	private void addbuyAttr(HttpServletRequest req, String[] id, String proId, String prodName, String[] attrName,
			String[] fillmode, User user, BuyPoolEntity buy) {

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
					attr.setAttrValue(req.getParameter("attrValue" + id[i]));
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

	/**
	 * 批量删除买盘
	 * 
	 */
	@RequestMapping(value = "/delbuypool")
	@ResponseBody
	public Map<String, Object> delbuypool(HttpServletRequest req, HttpServletResponse res, String ids)
			throws ServletException, IOException {
		String[] id = null;

		if (ids != null && !ids.equals("")) {
			id = ids.split(",");

		}
		for (int i = 0; i < id.length; i++) {
			buypoolbo.delbuypool(Integer.valueOf(id[i]));

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "succ");
		map.put("msg", "系统提示，操作成功");

		return map;
	}

	@RequestMapping(value = "/delbuypoolone")
	public ModelAndView delbuypoolone(HttpServletRequest req, HttpServletResponse res, String ids)
			throws ServletException, IOException {
		String[] id = null;

		if (ids != null && !ids.equals("")) {
			id = ids.split(",");

		}
		for (int i = 0; i < id.length; i++) {
			buypoolbo.delbuypool(Integer.valueOf(id[i]));

		}

		return buylist(req, null);

	}

	@RequestMapping(value = "/probuypool")
	public ModelAndView probuypool(HttpServletRequest req, HttpServletResponse res, Integer id)
			throws ServletException, IOException {

		buypoolbo.probuypool(id);
		return buylist(req, null);
	}

	/**
	 * 
	 * 准备发布
	 * 
	 * 
	 */

	@RequestMapping(value = "/tobuypublish")
	public ModelAndView tobuypublist(String ids, String extId) {
		ModelAndView view = new ModelAndView("dealMake/buypublish");
		view.addObject("ids", ids);
		BuyPoolEntity buypool = buypoolbo.getbuypoolID(Integer.valueOf(ids));

		List<AttrValue> attrvaluelist = buypoolbo.queryAttrvalue(extId);
		view.addObject("attrvaluelist", attrvaluelist);
		view.addObject("buypool", buypool);

		return view;
	}

	/**
	 * 发布
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/buypublish")
	@ResponseBody
	public Object buypublist(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BuypublishEntity pub = new BuypublishEntity();
		String id = req.getParameter("id");
		BuyPoolEntity buypool = buypoolbo.getbuypoolID(Integer.valueOf(id));
		User user = (User) req.getSession().getAttribute("userInfo");

		pub.setBuyPoolID(buypool.getId());
		pub.setAccount(req.getParameter("account"));
		pub.setCreatedAt(new Date());
		pub.setCreatedBy(user.getId());
		String qu = req.getParameter("quantity");
		if (qu != null && !"".equals("")) {
			pub.setQuantity(Double.valueOf(req.getParameter("quantity")));
		}
		buypoolbo.buypublish(pub);
		// 发布到商城

		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

		Map<String, Object> map = new HashMap<String, Object>();
		String validitydate = req.getParameter("validitydate");
		String quantity = req.getParameter("quantity");
		map.put("catid", buypool.getCategoryId());
		map.put("amount", Integer.valueOf(quantity));
		map.put("price", 0);
		map.put("warehouse", 0);
		map.put("username", req.getParameter("contact"));
		map.put("validitydate", validitydate == null ? "1970-01-01" : validitydate);
		map.put("receipt", 0);
		map.put("deliverytime", "1970-01-01");
		map.put("lastdeliverytime", "1970-01-01");
		map.put("delaydate", 0);
		map.put("delivery", 0);
		map.put("loss", 0);
		map.put("moq", 0);
		map.put("outmoq", 0);
		map.put("type", 2);
		map.put("title", user.getItems().getName());

		String jsonMallMap = JSONUtil.doConvertObject2Json(map);

		try {
			param.add("jsonMall", jsonMallMap);
			
			logger.info("开始调用：发布报盘信息到商城接口");
			
			restTemplate.postForObject(publishMallUrl, param, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发布报盘信息到商城接口出现异常");
		}
		return ResultMessage.ADD_SUCCESS_RESULT;

	}

	/**
	 * 准备生成买盘订单数据
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/toaddbuyorder")
	public ModelAndView toaddbuyorder(String ids,Integer poolType,String flag) {
		ModelAndView view = new ModelAndView("dealMake/addbuyorder");
		int id = Integer.parseInt(ids);
		BuyPoolEntity buypool = buypoolbo.getbuypoolID(id);
		List<ItemAttr> attrs = ibuyOrderBo.getMainItemAttr(buypool.getItemsID());
		//去除字符串中的空格、回车、换行符、制表符 
		if (attrs != null && attrs.size() > 0) {
			for (int i = 0; i < attrs.size(); i++) {
				String options = attrs.get(i).getOptions();
				if (options != null ) {
					Pattern p = Pattern.compile("\\s*|\t|\r|\n");
					Matcher m = p.matcher(options);
					options = m.replaceAll("");
					attrs.get(i).setOptions(options);
				}
			}
		}
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
		List<ProductEntity> prolist = attrValuesBO.getproductitemId(buypool.getItemsID());
		List<AttrValue> attrValuesList = buypoolbo.selbuyAttrValueByBuyId(id);
		if (attrValuesList.size() > 0) {
			view.addObject("proId", attrValuesList.get(0).getProdId());
		}
		view.addObject("attrValuesList", attrValuesList);
		view.addObject("prolist", prolist);
		view.addObject("poolId", ids);
		if (poolType != null) {
			view.addObject("poolType", poolType);
		}else {
			view.addObject("poolType", 2);
		}
		
		view.addObject("attrs", attrs);
		view.addObject("url", "buypool/buylist.do");// 返回至买盘列表

		view.addObject("sourceId", id);// 传回id
		view.addObject("source", 1);// 订单来源
		
		view.addObject("flag", flag);//报盘“生成订单来源”
		return view;
	}

	/**
	 * 采购商准备生成买盘订单数据
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/toaddPurchasebuyorder")
	public ModelAndView toaddPurchasebuyorder(String ids) {//ids 客户ID
		ModelAndView view = new ModelAndView("dealMake/addbuyorder");
		// 客户ID
		int id = Integer.parseInt(ids);
		ToOrder t = ibuyOrderBo.selCustomById(id);

		BuyPoolEntity buypool = new BuyPoolEntity();
		buypool.setItemsID(t.getItemId());
		buypool.setCompanyname(t.getCompanyName());
		buypool.setCustomerId(t.getCompanyId());

		List<ItemAttr> attrs = ibuyOrderBo.getMainItemAttr(buypool.getItemsID());
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
		List<ProductEntity> prolist = attrValuesBO.getproductitemId(buypool.getItemsID());
		List<AttrValue> attrValuesList = attrValuesBO.getAttrValuesByExtId(buypool.getCommodityID());
		view.addObject("attrValuesList", attrValuesList);
		view.addObject("prolist", prolist);
		view.addObject("attrs", attrs);
		view.addObject("url", "purchase/getAll.do");// 返回至采购列表

		view.addObject("sourceId", id);// 传回id 客户
		view.addObject("source", 3);// 订单来源
		
		view.addObject("poolType",1);
		return view;
	}

	/**
	 * 准备生成卖盘订单数据
	 * 
	 */
	@RequestMapping(value = "/toaddsellorder")
	public ModelAndView toaddsellorder(String sid,Integer poolType,String flag) {
		ModelAndView view = new ModelAndView("dealMake/addSellorder");
		int id = Integer.parseInt(sid);
		SellPool sellpool = sellPollBo.getSellPoolById(id);
		List<ItemAttr> attrs = ibuyOrderBo.getMainItemAttr(sellpool.getItemsid());
		//去除字符串中的空格、回车、换行符、制表符 
		if (attrs != null && attrs.size() > 0) {
			for (int i = 0; i < attrs.size(); i++) {
				String options = attrs.get(i).getOptions();
				if (options != null ) {
					Pattern p = Pattern.compile("\\s*|\t|\r|\n");
					Matcher m = p.matcher(options);
					options = m.replaceAll("");
					attrs.get(i).setOptions(options);
				}
			}
		}
		List<Areas> arealist = iAreasBO.getParentAreas();
		List<ProductEntity> prolist = attrValuesBO.getproductitemId(sellpool.getItemsid());
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
		List<AttrValue> attrValuesList = attrValuesBO.getAttrValuesByExtId(sellpool.getCommodityId());
		int proId = attrValuesBO.getProductIdByComId(sellpool.getCommodityId());
		view.addObject("proId", proId);
		view.addObject("poolId", sid);
		if (poolType != null) {
			view.addObject("poolType", 1);
		}else {
			view.addObject("poolType", 1);
		}
		
		view.addObject("attrValuesList", attrValuesList);
		view.addObject("attrs", attrs);
		view.addObject("prolist", prolist);
		view.addObject("url", "dealMake/sellOrder.do");// 返回至卖盘列表

		view.addObject("sourceId", id);// 传回id
		view.addObject("source", 0);// 订单来源
		
		view.addObject("flag", flag);//报盘“生成订单来源”
		return view;
	}

	/**
	 * 供应商客户池准备生成卖盘订单数据
	 * 
	 */
	@RequestMapping(value = "/toaddSupplysellorder")
	public ModelAndView toaddSupplysellorder(String sid) {
		ModelAndView view = new ModelAndView("dealMake/addSellorder");
		int id = Integer.parseInt(sid);
		ToOrder t = ibuyOrderBo.selCustomById(id);
		SellPool sellpool = new SellPool();
		sellpool.setItemsid(t.getItemId());
		sellpool.setItemsNm(t.getItemName());
		sellpool.setCompanyid(t.getCompanyId());
		sellpool.setCompanyNm(t.getCompanyName());
		List<ItemAttr> attrs = ibuyOrderBo.getMainItemAttr(sellpool.getItemsid());
		List<Areas> arealist = iAreasBO.getParentAreas();
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
		List<ProductEntity> prolist = attrValuesBO.getproductitemId(sellpool.getItemsid());
		List<AttrValue> attrValuesList = attrValuesBO.getAttrValuesByExtId(sellpool.getCommodityId());
		view.addObject("attrValuesList", attrValuesList);
		view.addObject("prolist", prolist);
		view.addObject("attrs", attrs);
		view.addObject("url", "supply/getAll.do");// 返回至供应商列表

		view.addObject("sourceId", id);// 传回id
		view.addObject("source", 2);// 订单来源
		view.addObject("poolType",1);
		return view;
	}

	/**
	 * 导出
	 * 
	 * 
	 */

	@RequestMapping("buyPoolExportDel")
	public void buyPoolExportDel(String ids, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		// 1.根据传入id获取对应数据
		String[] buyPoolInfo = ids.split(",");

		List<BuyPoolEntity> listBuyPool = this.buypoolbo.getBuyPoolExportData(buyPoolInfo);
		ExportUtil<BuyPoolEntity> excel = new ExportUtil<BuyPoolEntity>();
		String fileName = "买盘池";

		String[] headerNames = new String[] { "公司名", "采购标题", "采购类容", "联系人", "固话", "手机", "地区", "是否客户" };
		String[] header = new String[] { "companyname", "title", "context", "contacterName", "telephone", "mobilePhone",
				"areaName", "isCustomer" };
		String[] comments = new String[] { null, null, null, null, null, null, null, null, null, null, null, null };
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		excel.export("sheet1", headerNames, header, comments, listBuyPool, os, "");

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
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

	@RequestMapping("buyPoolExportDialog")
	public ModelAndView buyPoolExportDialog() {

		ModelAndView mv = new ModelAndView("dealMake/buyPoolExportDialog");

		return mv;

	}

	@RequestMapping("buyPoolExport")
	public void buyPoolExport(HttpServletRequest req, String flag, String ids, HttpServletResponse response) {
		// 1.根据传入id获取对应数据
		// String[] buyPoolInfo = ids.split(",");

		// List<BuyPoolEntity> listBuyPool =
		// this.buypoolbo.getBuyPoolExportData(buyPoolInfo);

		User user = (User) req.getSession().getAttribute("userInfo");
		Map<String, Object> map = new HashMap<String, Object>();

		// falg 表示用户查看的是那个列表 全部 falg=0|我的客户 falg=1|部门客户 falg=2
		if (flag == null)
			flag = "0";

		// 如果查看我的客户，设置当前用户id为查询条件
		if ("1".equals(flag)) {
			map.put("userid", user.getId());
		}

		// 如果查看部门客户，设置查询条件为按部门查询
		if ("2".equals(flag)) {
			map.put("dept", 2);
		}

		map.put("itemId", user.getItems().getId());
		map.put("ids", ids);

		List<BuyPoolEntity> listBuyPool = buypoolbo.buypoollist(map);

		// 2.生成excel文件
		ExportUtil<BuyPoolEntity> excel = new ExportUtil<BuyPoolEntity>();
		String fileName = "买盘池";

		String[] headerNames = new String[] { "公司名", "采购标题", "采购类容", "联系人", "固话", "手机", "地区", "是否客户" };
		String[] header = new String[] { "companyname", "title", "context", "contacterName", "telephone", "mobilePhone",
				"areaName", "isCustomer" };
		String[] comments = new String[] { null, null, null, null, null, null, null, null, null, null, null, null };
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		excel.export("sheet1", headerNames, header, comments, listBuyPool, os, "");

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("checkcommodity2Test")
	public void checkcommodity2Test() {
		List<AttrValue> attrvalueList = new ArrayList<AttrValue>();
		AttrValue attrValue1 = new AttrValue();
		attrValue1.setAttrName("产地");
		attrValue1.setAttrValue("湖南株洲");

		AttrValue attrValue2 = new AttrValue();
		attrValue2.setAttrName("品牌");
		attrValue2.setAttrValue("天路");

		attrvalueList.add(attrValue1);
		attrvalueList.add(attrValue2);
		Integer commodityId = attrValuesBO.checkcommodity2(attrvalueList);
		System.out.println(commodityId);
	}

	
	/** 撮合交易管理新建报盘, 发送到买盘池,并同步到商城
	 * @Title: publishToByPool 
	 * @Description: TODO
	 * @param poolId
	 * @param req
	 * @return
	 * @author zhangnan/张楠
	 * @return: Map<String,Object>
	 * @createTime 2015年12月21日下午4:41:52
	 */
	@RequestMapping("publishToByPool")
	@ResponseBody
	public Map<String,Object> publishToByPool(Integer poolId,HttpServletRequest req) {
		Map<String,Object> map = new HashMap<String, Object>();
		BuyPoolEntity buyPool = this.buypoolbo.getbuypoolById(poolId);
		User user = (User) req.getSession().getAttribute("userInfo");
		Items items = user.getItems();
		//buyPool.setContext("采购"+""+items.getName()+""+buyPool.getWareName()+""+buyPool.getPrice());
		try {
			buyPool = this.adddemall(buyPool,items.getName());
			if (!buyPool.getErrno().equals("0")) {
				map.put("success","ERROR");
				map.put("msg","买盘同步到商城出错");
				return map;
			}
			buyPool.setCreateSource(2);
			this.ibuyOrderBo.updateBuyPoolCreateSource(buyPool);
			map.put("success","OK");
			map.put("msg","买盘已同步到商城和卖盘池");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
