package com.smm.cuohe.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.IContacterBo;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IUserBO;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.Category;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.CustomerTag;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.tools.MallInterfaceCacheSingleton;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.Json;
import com.smm.cuohe.util.StringUtil;
import com.smm.cuohe.util.URLRequest;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * @author lirubin
 */
/** 客户池
 * @ClassName: CustomerController 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年12月15日 上午9:22:07  
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
	private Logger logger = Logger.getLogger(CustomerController.class);
	@Resource
	private IAreasBO iAreasBO;

	@Resource
	private ICustomerBO iCustomerBO;

	@Resource
	private IUserBO iuserBO;

	@Resource
	private ICategoryBO iCategoryBO;

	@Resource
	private ICompanyBO iCompanyBO;

	@Resource
	private IContacterBo iContacterBo;

	@Resource
	private RestTemplate restTemplate;

	@Value("#{ch['queryByName.URL']}")
	private String queryByName;

	@Value("#{ch['getsmmdbusername.URL']}")
	private String queryByHone;

	@Value("#{ch['getCompanyInfo.URL']}")
	private String getCompanyInfo;

	@Value("#{ch['getusercount.URL']}")
	private String userCount;

	@Value("#{ch['getsmmdbusername.URL']}")
	private String dbUserName;

	// 新增页面
	@RequestMapping(value = "new")
	public ModelAndView mvNew(HttpServletRequest request) {
		Object user = request.getSession().getAttribute("userInfo");
		ModelAndView mv = null;
		mv = new ModelAndView("customer/addcustomer");
		if (user != null) {
			List<Category> list = null;
			mv.addObject("userId", ((User) user).getId());
			mv.addObject("parentAreas", iAreasBO.getParentAreas());
			mv.addObject("pics",
					iuserBO.getUsersByItemId(((User) user).getItemId()));
			mv.addObject("categorySource", iCategoryBO.getByModId(7));
			list = iCategoryBO.getByModId(5);
			mv.addObject("level", list);
			String only=UUID.randomUUID().toString().replace("-", "");
			request.getSession().setAttribute(only,only);
			mv.addObject("only", only);
			mv.addObject("levelString", JSONArray.fromObject(list).toString());
			mv.addObject("entTypes", iCategoryBO.getByModId(1));
			mv.addObject("salesvolume", iCategoryBO.getByModId(2));
			mv.addObject("categoryemployee", iCategoryBO.getByModId(3));
			mv.addObject("categoryfreq", iCategoryBO.getByModId(8));
			mv.addObject("categorybusiness", iCategoryBO.getByModId(4));
			mv.addObject("companyproperty", iCategoryBO.getByModId(6));
			mv.addObject("categoryCredit", iCategoryBO.getByModId(9));
		}
		return mv;
	}

	/**
	 * 新增客户
	 * 
	 * @param customer
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, String> save(@RequestBody Customer customer,HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		/*String only=customer.getOnly();
		Object obje=request.getSession().getAttribute(only);
		if(obje !=null){
			request.getSession().removeAttribute(only);
			User user = (User) request.getSession().getAttribute("userInfo");
			customer.setItemId(Integer.parseInt(user.getItemId()));
			customer.setCreatedBy(user.getId());
			if (iCustomerBO.checkCusByComNameAndItem(customer)) {
				map.put("success", "0");
				map.put("msg", "该公司已存在");
			} else {
				try {
					this.iCustomerBO.saveCustomer(customer);
					map.put("success", "1");
					map.put("msg", "新增客户成功");

				} catch (Exception e) {
					e.printStackTrace();
					map.put("success", "0");
					map.put("msg", "新增客户失败！" + e.getMessage());
				}
			}
		}else{
			map.put("success", "0");
			map.put("msg", "重复新增客户信息!");
		}*/
		try {
			User user = (User) request.getSession().getAttribute("userInfo");
			customer.setItemId(Integer.parseInt(user.getItemId()));
			customer.setCreatedBy(user.getId());
			if (iCustomerBO.checkCusByComNameAndItem(customer)) {
				map.put("success", "0");
				map.put("msg", "该公司已存在");
			} else {
				try {
					this.iCustomerBO.saveCustomer(customer);
					map.put("success", "1");
					map.put("msg", "新增客户成功");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("success", "0");
					map.put("msg", "新增客户失败！" + e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return map;
	}

	@RequestMapping(value = "gotoGetCompanyFromMallPage")
	public ModelAndView gotoGetCompanyFromMallPage(String searchString) throws UnsupportedEncodingException {
		//解码
		searchString = URLDecoder.decode( searchString ,"utf-8");
		ModelAndView mv = new ModelAndView("customer/company");
		mv.addObject("searchString", searchString);
		return mv;
	}

	@RequestMapping(value = "gotoGetCompanyCellphone")
	public ModelAndView gotoGetCompanyCellphone(String searchString) {
		ModelAndView mv = new ModelAndView("customer/cellphone");
		mv.addObject("searchString", searchString);
		return mv;
	}

	/**
	 * 按公司名称模糊搜索
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "company")
	@ResponseBody
	public Map<String, Object> company(Integer start, Integer len, String searchString, HttpServletRequest request) throws UnsupportedEncodingException {

		if(!searchString.equals("")){
			searchString = URLDecoder.decode( searchString ,"utf-8");
		}
		Map<String, Object> rltMap = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(searchString)) {
			try {
				searchString = URLDecoder.decode(searchString, "utf8");
				Map<String, String> map = new HashMap<>();
				String url = queryByName;
				map.put("companyName", searchString);
				map.put("page", ((start / len) + 1) + "");
				
				logger.info("开始调用：根据公司名模糊查询公司接口  参数： "+map.toString());
				
				String result = URLRequest.post(url, map);
				
				logger.info("调用查询queryByName结果>>>" + result);

				if (StringUtils.isNotBlank(result)) {

					JSONObject jsonObject = JSONObject.fromObject(result);
					
					logger.info("根据公司名模糊查询公司返回结果："+jsonObject.toString());

					if (jsonObject != null && jsonObject.containsKey("success") && "true".equals(jsonObject.get("success").toString())) {
						JSONObject dataJson = (JSONObject) jsonObject.get("result");
						if (dataJson.containsKey("data") && JSONUtils.isArray(dataJson.get("data"))) {

							JSONArray jsonArray = dataJson.getJSONArray("data");
							
							rltMap.put("total", dataJson.get("Count").toString());
							rltMap.put("rows", jsonArray);//返回数据值
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rltMap;

	}

	/**
	 * 根据手机号查询数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cellphone")
	@ResponseBody
	public Map<String, Object> cellphone(Integer start, Integer len, String searchString, HttpServletRequest request) {

		Map<String, Object> rltMap = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(searchString)) {
			try {
				searchString = URLDecoder.decode(searchString, "utf8");
				Map<String, String> map = new HashMap<>();
				String url = queryByHone;
				map.put("name", searchString);
				map.put("page", ((start / len) + 1) + "");
				
				logger.info("开始调用：根据手机号查询数据接口  参数： "+map.toString());
				
				String result = URLRequest.post(url, map);
				logger.info("调用查询queryByName结果>>>" + result);

				if (StringUtils.isNotBlank(result)) {

					JSONObject jsonObject = JSONObject.fromObject(result);

					logger.info("根据手机号查询数据返回结果："+jsonObject.toString());
					
					if (jsonObject != null && jsonObject.containsKey("code")
							&& "1".equals(jsonObject.get("code").toString())) {

						if (jsonObject.containsKey("resultData")&& JSONUtils.isArray(jsonObject.get("resultData"))) {

							JSONArray jsonArray = jsonObject.getJSONArray("resultData");

							JSONObject jsonCompanyObject = jsonArray.getJSONObject(0);

							JSONObject jsonPageObject = jsonArray.getJSONObject(1);

							JSONArray jsonCompanyArray = jsonCompanyObject.getJSONArray("companylist");
							
							Integer total = jsonPageObject.getInt("allrows");

							rltMap.put("total", total);
							rltMap.put("rows", jsonCompanyArray);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rltMap;
	}

	@RequestMapping(value = "companyByName", method = { RequestMethod.POST })
	@ResponseBody
	public String companyByName(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		if (StringUtils.isNotBlank(searchString)) {
			try {
				searchString = URLDecoder.decode(searchString, "utf8");
				Company temp = iCompanyBO.getCompanyByCompanyName(searchString);
				if (null != temp && !"".equals(temp)) {
					return "1";
				} else {
					return "0";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 通过商城会员id，从商城中获取对应公司详情
	 * 
	 * @param request
	 *            请求对象
	 * @return 返回jison数据
	 */
	@RequestMapping(value = "companyDetail")
	@ResponseBody
	public String companyDetail(HttpServletRequest request) {

		String companyId =request.getParameter("companyId");
		String userName = request.getParameter("userName");

		if (StringUtils.isNotBlank(companyId)&&StringUtils.isNotBlank(userName)) {

			String url = getCompanyInfo.replace("{0}", StringUtil.doEncoder(companyId));

			Map<String, String> map = new HashMap<>();
			map.put("companyId", companyId);

			try {
				logger.info("开始调用：获取企业详细信息接口");
				
				String jsonStr = URLRequest.post(url, map);
				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				
				logger.info("调用查询getCompanyInfoByUserId结果>>>" + jsonObject);
				
				if (jsonObject != null && jsonObject.containsKey("success") && "true".equals(jsonObject.get("success").toString())) {
					JSONObject company = jsonObject.getJSONObject("result");
					if (company != null) {
						if (company.containsKey("areaid") && StringUtils.isNumeric(company.getString("areaid"))) {
							Areas area = iAreasBO.getAreaById(company.getString("areaid"));
							company.element("area", area);
						}
						return company.toString();
					}
				}
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		return "{}";
	}

	@Value("#{ch['getCategory.URL']}")
	private String getCategoryUrl;

	@RequestMapping(value = "/brandlist")
	public ModelAndView Brand(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("customer/brandList");
		List<String> valuelist = new ArrayList<String>();
		String value = req.getParameter("values");

		JSON branlit = null;
		try {

			if (value != null && !"".equals(value)) {

				String str = URLDecoder.decode(value, "utf8");
				String[] values = str.split(",");
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						valuelist.add(values[i]);
					}
					view.addObject("valuelist", valuelist);
				}
			}
			String salesP = req.getParameter("salesP");
			if (StringUtils.isNotBlank(salesP))
				view.addObject("salesP",
						"," + URLDecoder.decode(salesP, "utf8") + ",");

			String key = "mallInterfaceGetCategory";

			String categoryStr = MallInterfaceCacheSingleton.get(key);

			if (categoryStr == null || categoryStr.equals("")) {

				logger.info("开始调用：获取行业品牌接口接口 ");
				categoryStr = restTemplate.postForObject(getCategoryUrl, "",String.class);
				MallInterfaceCacheSingleton.put(key, categoryStr);
				MallInterfaceCacheSingleton.setExpire(key, 604800); // 一周后过期
																	// 3600 * 24
																	// * 7 =
																	// 604800
			}

			Map<String, Object> spMap = JSONUtil.doConvertJson2Map(categoryStr);
			
			logger.info("获取行业品牌接口接口返回数据："+spMap);
			
			branlit = (JSON) spMap.get("data");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取行业和品牌,接口调用异常");
		}
		view.addObject("branlit", branlit);
		view.addObject("typeId", req.getParameter("typeId"));
		return view;
	}

	@Value("#{ch['searchCategory.URL']}")
	private String searchCategory;

	// 商机分类按名称查询接口
	@RequestMapping(value = "searchCategory")
	@ResponseBody
	public Map<String, Object> searchCategory(String name) {

		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();

		param.add("name", name);

		String jsonString = "{}";
		try {

			if (name == null || name.equals("")) {
				String key = "mallInterfaceSearchCategoryWhenNameIsEmpty";

				jsonString = MallInterfaceCacheSingleton.get(key);

				if (jsonString == null || jsonString.equals("")) {

					jsonString = restTemplate.postForObject(searchCategory,
							param, String.class);
					MallInterfaceCacheSingleton.put(key, jsonString);
					MallInterfaceCacheSingleton.setExpire(key, 604800); // 一周后过期
																		// 3600
																		// * 24
																		// * 7 =
																		// 604800
				}
			} else {
				logger.info("开始调用：商机分类按名称查询接口  参数："+param.toString());
				jsonString = restTemplate.postForObject(searchCategory, param,String.class);
				if (jsonString != null) {
					logger.info("商机分类按名称查询返回数据："+JSONUtil.doConvertJson2Map(jsonString).toString());
				}
			}

		} catch (Exception ex) {
			logger.error("调用商机分类按名称查询接口出错  "+ex);
			System.out.println(ex.getMessage());

		}

		return JSONUtil.doConvertJson2Map(jsonString);

	}

	// 查询品牌
	@RequestMapping(value = "searchBrand")
	@ResponseBody
	public List<String> searchBrand(String name, HttpServletRequest request) {
		User u = (User) request.getSession(Boolean.FALSE).getAttribute(
				"userInfo");
		return iCustomerBO.serachBrand(name, Integer.parseInt(u.getItemId()));
	}

	/**
	 * 将已删除（误操作删除）的客户还原回客户池
	 * 
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "restoreCustomer")
	@ResponseBody
	public Map<String, Object> restoreCustomer(Integer customerId,
			HttpServletRequest request) {
		User user = (User) request.getSession(Boolean.FALSE).getAttribute(
				"userInfo");
		return iCustomerBO.restoreCustomer(user.getId(), customerId);
	}

	/**
	 * 根据手机号判断主站是否存在
	 * 
	 * @param tellPhone
	 * @return
	 */
	@RequestMapping(value = "getSmmdbUsername")
	@ResponseBody
	public Json getSmmdbUsername(String tellPhone) {
		Json json = new Json();

		if (!StringUtils.isEmpty(tellPhone)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("telephone", tellPhone);
			JSONObject result = null;
			String result_str = "";
			try {
				logger.info("开始调用：根据电话号码获取公司信息接口   参数："+params.toString());
				
				result_str = URLRequest.post(dbUserName, params);
				
				logger.info("smm主站通过手机号查询公司名和用户名接口返回:======>" + result_str);
				result = JSONObject.fromObject(result_str);
				
				if (result != null) {
					logger.info("根据电话号码获取公司信息返回数据"+result.toString());
				}
			} catch (Exception e) {
				logger.error("调用根据电话号码获取公司信息接口出错");
				e.printStackTrace();
				json.setReturnCode("1");
				json.setReturnMsg("调用商城接口异常");
				logger.error("调用商城接口异常");
			}
			if (result != null) {
				int code = result.getInt("errno");
				if(code == 0){
					json.setReturnCode("0");
					json.setReturnMsg("存在");
					json.setData(result.get("data"));
				}else if(code == 1){
					json.setReturnCode("1");
					json.setReturnMsg("不存在");
				}
			} else {
				json.setReturnCode("3");
				json.setReturnMsg("错误");
			}
		} else {
			json.setReturnCode("2");
			json.setReturnMsg("参数不能为空");

		}
		return json;
	}
	
	
	/**
	 * 查询手机号是否是登录名
	 * getUserCount
	 */
	@RequestMapping(value = "getUserCount")
	@ResponseBody
	public Json getUserCount(String tellPhone){
		Json json = new Json();
		if (!StringUtils.isEmpty(tellPhone)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("telephone", tellPhone);
			JSONObject result = null;
			String result_str = "";
			try {
				result_str = URLRequest.post(dbUserName, params);
				logger.info("smm主站通过手机号查询公司名和用户名接口返回:======>" + result_str);
				result = JSONObject.fromObject(result_str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.setReturnCode("1");
				json.setReturnMsg("调用商城接口异常");
				logger.error("调用商城接口异常");
			}
			if (result != null) {
				int code = result.getInt("errno");
				switch (code) {
				case 0:
					json.setReturnCode("0");
					json.setReturnMsg("存在");
					json.setData(result.get("count"));
					break;
				case 1:
					json.setReturnCode("1");
					json.setReturnMsg("不存在");
					break;
				case 2:
					json.setReturnCode("4");
					json.setReturnMsg("输入手机号不合法");
					break;
				default:
					json.setReturnCode("3");
					json.setReturnMsg("错误");
					break;
				}
			} else {
				json.setReturnCode("3");
				json.setReturnMsg("错误");
			}
			
			
		}else{
			json.setReturnCode("2");
			json.setReturnMsg("参数不能为空");
		}
		
		return json;
	}

	/** 用户标签
	 * @Title: queryCustomerTag 
	 * @Description: TODO
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<CustomerTag>
	 * @createTime 2015年12月15日上午9:34:04
	 */
	@RequestMapping("queryCustomerTag")
	@ResponseBody
	public List<CustomerTag> queryCustomerTag(String tagName) {
		Map<String,Object> param = new HashMap<String, Object>();
//		if (tagName == null) {
//			tagName = "";
//		}
		param.put("tag", tagName);
		List<CustomerTag> list = new ArrayList<CustomerTag>();
		try {
			List<Customer> customerList = this.iCustomerBO.queryCustomerTag(param);
			if (customerList != null && customerList.size() > 0) {
				for (int i = 0; i < customerList.size(); i++) {
					String tagNames = customerList.get(i).getTag();
					String[] tags = tagNames.split(",");
					for (int j = 0; j < tags.length; j++) {
						CustomerTag customerTag = new CustomerTag();
						customerTag.setTagname(tags[j]);
						list.add(customerTag);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
}
