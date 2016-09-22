package com.smm.cuohe.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.dao.IChatsDAO;
import com.smm.cuohe.dao.ICompanyDAO;
import com.smm.cuohe.dao.IContacterDAO;
import com.smm.cuohe.dao.ICustomerDao;
import com.smm.cuohe.domain.Category;
import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.CompanyPOJO;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.DiscussChatRecord;
import com.smm.cuohe.domain.RemindCustomerEntity;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.URLRequest;

import net.sf.json.JSONObject;

@Service
public class CustomerBO implements ICustomerBO {

	private Logger logger = Logger.getLogger(CustomerBO.class);

	@Resource
	private ICustomerDao icustomerDao;

	@Resource
	private ICompanyDAO iCompanyDAO;

	@Resource
	private IContacterDAO iContacterDAO;

	@Resource
	private ICategoryBO iCategoryBO;

	@Resource
	private RestTemplate restTemplate;

	@Resource
	private IChatsDAO iChatsDAO;
	
	@Value("#{ch['addCompany.URL']}")
	private String addCompany;

	@Value("#{ch['getCompanyByName.URL']}")
	private String getCompanyByNameURL;

	@Value("#{ch['getUserByNameURL.URL']}")
	private String getUserByNameURL;

	@Value("#{ch['addUser.URL']}")
	private String addUserUrl;
	
	@Value("#{ch['addMallUser.URL']}")
	private String addMallUser;

	@Value("#{ch['checkCustom.URL']}")
	private String checkCustom;

	@Value("#{ch['queryCompanyByAccount.URL']}")
	private String queryCompanyByAccount;
	
	@Value("#{ch['getsmmdbusername.URL']}")
	private String getsmmdbusername;

	@Value("#{ch['ifMallExistTel.URL']}")
	private String ifMallExistTel;

	@Transactional
	@Override
	public Integer saveCustomer(Customer customer) throws Exception {

		

		// 检查相同品目下是否有同名客户，如果有，抛出异常。
		boolean exists_same_companyName = this.checkHasSameCompanyName(
				customer.getCompanyName(), customer.getItemId());

		if (exists_same_companyName)
			throw new Exception("该品目下客户已存在!");

		// 获取客户联系人列表
		List<Contacter> list = customer.getContacters();

		int count = 0;
		for (Contacter cont : list) {
			if (cont.getKeyPerson() == 1) {
				cont.setTidentity(0);
				count++;
			}
		}
		if (count == 0) {
			Contacter cont = list.get(0);
			cont.setKeyPerson(1);
			cont.setTidentity(0);
		}
		
		//判断主站是否存在
		String userName = customer.getUser_name();// 主站account
		logger.info("获取主站account:"+userName);
		JSONObject jsonObject = null;
		if(userName == null || userName.equals("")){
			// 判断主站是否存在这个手机号
			Map<String, String> map = new HashMap<>();
			userName=list.get(0).getMobilePhone();
			String url = getsmmdbusername;
			map.put("account", userName);
			try {
				String result = URLRequest.post(url, map);
				jsonObject = JSONObject.fromObject(result);
			} catch (Exception ex) {
				throw new Exception("调用主站接口失败!错误原因：" + ex.getMessage());
			}
			// 判断商城是否存在这个手机号
			Map<String, String> params = new HashMap<String, String>();
			params.put("telephone", list.get(0).getMobilePhone());
			JSONObject jsonresult = null;
			try {
				String result_str = URLRequest.post(ifMallExistTel, params);
				jsonresult = JSONObject.fromObject(result_str);
			} catch (Exception ex) {
				throw new Exception("调用商城校验手机号码接口失败!错误原因：" + ex.getMessage());
			}
			// 主站没有，调用商城接口，使用第一个联系人信息创建商城和主站信息
			if(!jsonObject.get("errno").toString().equals("1")){
				Contacter firstContacter = list.get(0);
				userName = firstContacter.getMobilePhone();
				try {
					this.registMallUser(firstContacter, customer);
				} catch (Exception ex) {
					throw new Exception("商城注册接口调用错误!错误原因：" + ex.getMessage());
				}
				// 商城接口调用完成更新account
				customer.setAccount(userName);
			}else if (jsonObject.get("error").toString().equals("1") && !jsonresult.get("error").toString().equals("1")){
				// 主站存在商城不存在，调用商城接口，使用主站用户名创建商城信息
				Contacter firstContacter = list.get(0);
				customer.setAccount(userName);
				try {
					this.registMallUser_mall(firstContacter, customer);
				} catch (Exception ex) {
					throw new Exception("商城注册接口调用错误!错误原因：" + ex.getMessage());
				}
			}else if(jsonObject.get("error").toString().equals("1") && jsonresult.get("error").toString().equals("1")){
				customer.setAccount(jsonresult.get("account").toString());
			}
		}else{
			// 判断商城是否存在这个用户名
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", userName);
			JSONObject jsonresult = null;
			try {
				String result_str = URLRequest.post(queryCompanyByAccount, params);
				jsonresult = JSONObject.fromObject(result_str);
			} catch (Exception ex) {
				throw new Exception("调用商城校验用户名接口失败!错误原因：" + ex.getMessage());
			}
			// 调用商城接口，使用主站用户名创建商城信息
			if (!jsonresult.get("error").toString().equals("1")) {
				Contacter firstContacter = list.get(0);
				customer.setAccount(userName);
				try {
					this.registMallUser_mall(firstContacter, customer);
				} catch (Exception ex) {
					throw new Exception("商城注册接口调用错误!错误原因：" + ex.getMessage());
				}
			}
		}
		
		// 设置客户的商城会员账号,
		if (StringUtils.isBlank(customer.getAccount()))
			customer.setAccount(userName);

		// 保存客户资料
		icustomerDao.saveCustomer(customer);

		// 设置客户联系人数据
		for (Contacter c : list) {
			c.setCustomerId(customer.getId());
			c.setCreatedBy(customer.getPic());
		}

		// 批量保存客户联系人
		this.iContacterDAO.addContacterBatch(list);

		// 返回客户编号
		return customer.getId();
	}

	@Transactional
	@Override
	public Integer saveCustomerForDealMaker(Customer customer) throws Exception {

		// 检查相同品目下是否有同名客户，如果有，抛出异常。
		boolean exists_same_account = this.checkHasSameCompanyAccount(
				customer.getAccount(), customer.getItemId());

		if (exists_same_account)
			throw new Exception("该品目下客户已存在!");

		boolean exists_same_companyName = this.checkHasSameCompanyName(
				customer.getCompanyName(), customer.getItemId());

		if (exists_same_companyName)
			throw new Exception("该品目下公司名已存在!");

		// 获取客户联系人列表
		List<Contacter> list = customer.getContacters();

		// 保存客户资料
		icustomerDao.saveCustomer(customer);

		// 设置客户联系人数据
		for (Contacter c : list) {
			c.setCustomerId(customer.getId());
			c.setCreatedBy(customer.getPic());
			c.setKeyPerson(1);
		}

		// 批量保存客户联系人
		this.iContacterDAO.addContacterBatch(list);

		// 返回客户编号
		return customer.getId();
	}

	/**
	 * 检查在相同品目下是否存在同样公司名称的客户
	 * 
	 * @param companyName
	 * @param itemId
	 * @return
	 */
	private boolean checkHasSameCompanyAccount(String account, Integer itemId) {

		Integer companyId = this.icustomerDao.queryIdByAccountAndItem(account,
				itemId);

		return companyId != null;

	}

	/**
	 * 检查在相同品目下是否存在同样公司名称的客户
	 * 
	 * @param companyName
	 * @param itemId
	 * @return
	 */
	private boolean checkHasSameCompanyName(String companyName, Integer itemId) {

		Integer companyId = this.icustomerDao.queryIdByNameAndItem(companyName,
				itemId);

		return companyId != null;

	}

	/**
	 * 创建客户业务处理
	 *
	 * @param companyPOJO
	 *            公司对象
	 * @param user
	 *            用户对象
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String saveCompanyPOJO(CompanyPOJO companyPOJO, User user)
			throws Exception {

		String company_name = companyPOJO.getName();

		// 2. 通过企业名称，检查商城中是否有同名企业信息 （调用商城查询企业接口）
		boolean has_same_name_company_in_mall = this
				.isExistsCompanyByNameInMall(company_name);

		// 2.1 有同名企业，获取企业信息，主要是 username 字段
		String mall_user_name = null;

		if (has_same_name_company_in_mall) {

			mall_user_name = this.getMallUserNameByCompanyName(company_name);

		} else {
			// 2.2 无同名企业

			// 3. 使用企业第一联系人手机号码为账号，检查商城是否有同账号会员 （调用商城查询会员接口）
			Contacter contacter = companyPOJO.getContacters().get(0);

			String contacter_phone = contacter.getMobilePhone();

			boolean has_same_account_user_in_mall = this
					.isExistsUserByAccountInMall(contacter_phone);

			// 3.1 商城有相同账号会员，抛出异常，会员账号已存在，不能重复注册。
			if (has_same_account_user_in_mall)
				throw new Exception("会员账号已存在！请修改第一联系人手机号码");

			// 3.2 商城无相同账号会员
			mall_user_name = contacter_phone;

			// 4. 使用企业第一联系人手机号码为账号，注册商城会员，同时注册商城企业
			// （调用商城注册会员接口.注意：商城新增会员接口中会自动新增对应企业信息。所以，调用一次就可以了。）
			// this.registMallUser(contacter,companyPOJO);

		}

		// 6. 在撮合系统中保存公司信息
		companyPOJO.setAccount(mall_user_name);
		this.iCompanyDAO.addCompanyToId(companyPOJO);

		// 7. 保存客户信息，需要设置客户对应品目
		Customer customer = new Customer();
		this.icustomerDao.saveCustomer(customer);

		// 8. 保存联系人信息,需设置联系人对应品目
		this.iContacterDAO.addContacterBatch(companyPOJO.getContacters());

		return null;
	}

	@Override
	public Customer getOneById(int id) {

		return this.icustomerDao.getOneById(id);
	}

	/**
	 * 注册商城和主站用户
	 * 
	 * @param contacter  企业联系人
	 * @param customer 	  企业
	 * @throws Exception
	 */
	private void registMallUser(Contacter contacter, Customer customer)
			throws Exception {

		Map<String, String> params = new HashMap<>();

		// ==============用户信息===============================
		// 企业联系人真实姓名
		params.put("business_truename", contacter.getName());

		String email = contacter.getEmail() == null ? "" : contacter.getEmail();
		params.put("email", email);

		String qq = contacter.getQq() == null ? "" : contacter.getQq();
		params.put("qq", qq);
		// 联系电话
		params.put("tel", contacter.getMobilePhone());
		params.put("ip", "127.0.0.1");

		// ==============企业信息=========================
		// 企业名称
		params.put("business_company", customer.getCompanyName());
		// 企业地区ID
		params.put("business_area", String.valueOf(customer.getAreaId()));
		// 企业地址
		params.put("address", customer.getAddress());
		// 行业
		params.put("industry", customer.getSalesProducts());

		String mode_name = "";

		if (customer.getCompanyProperty() != null)
			mode_name = this.iCategoryBO.getOneById(
					customer.getCompanyProperty()).getName();

		// 性质
		params.put("business_mode", mode_name);

		logger.info("--------------- regist mall user params:"
				+ params.toString());

		logger.info("开始调用：注册客户接口  参数:" + params.toString());

		String result = URLRequest.post(this.addUserUrl, params);

		JSONObject data = JSONObject.fromObject(result);

		logger.info("注册客户接口返回参数" + data.toString());

		String status = data.getString("status");

		String msg = data.getString("msg");

		// 如果信息商城用户失败，抛出异常
		if (status.equals("faild"))
			throw new Exception("有色商城同步异常：" + msg);

	}
	
	/**
	 * 注册商城用户
	 * 
	 * @param contacter  企业联系人
	 * @param customer 	  企业
	 * @throws Exception
	 */
	private void registMallUser_mall(Contacter contacter, Customer customer) throws Exception {

		Map<String, String> params = new HashMap<>();

		// ==============用户信息===============================
		// 企业联系人真实姓名
		params.put("business_truename", contacter.getName());

		String email = contacter.getEmail() == null ? "" : contacter.getEmail();
		params.put("email", email);

		String qq = contacter.getQq() == null ? "" : contacter.getQq();
		params.put("qq", qq);
		// 联系电话
		params.put("tel", contacter.getMobilePhone());
		params.put("ip", "127.0.0.1");

		// ==============企业信息=========================
		// 企业名称
		params.put("business_company", customer.getCompanyName());
		// 企业地区ID
		params.put("business_area", String.valueOf(customer.getAreaId()));
		// 企业地址
		params.put("address", customer.getAddress());
		// 行业
		params.put("industry", customer.getSalesProducts());
		//主站account
		params.put("account", customer.getAccount());

		String mode_name = "";

		if (customer.getCompanyProperty() != null)
			mode_name = this.iCategoryBO.getOneById(
			customer.getCompanyProperty()).getName();

		// 性质
		params.put("business_mode", mode_name);

		logger.info("--------------- regist mall user params:" + params.toString());

		logger.info("开始调用：注册客户接口  参数:" + params.toString());

		String result = URLRequest.post(this.addMallUser, params);

		JSONObject data = JSONObject.fromObject(result);

		logger.info("注册客户接口返回参数" + data.toString());

		String status = data.getString("status");

		String msg = data.getString("msg");

		// 如果信息商城用户失败，抛出异常
		if (status.equals("faild"))
			throw new Exception("有色商城同步异常：" + msg);

	}

	/**
	 * 检查在商城中是否存在相同账号的会员信息，存在返回true ，否则返回false
	 * 
	 * @param user_account
	 * @return
	 */
	private boolean isExistsUserByAccountInMall(String user_account) {

		Map<String, String> params = new HashMap<>();

		params.put("name", user_account);

		try {

			logger.info("开始调用：检查在商城中是否存在相同账号的会员信息接口   参数：" + params.toString());

			String result_str = URLRequest.get(this.getUserByNameURL, params);

			JSONObject result = JSONObject.fromObject(result_str);

			logger.info("检查在商城中是否存在相同账号的会员信息返回数据：" + result.toString());

			int code = result.getInt("code");

			return code == 1;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;
	}

	/**
	 * 通过公司名称在商城中查询公司对应username（商城会员账号）
	 * 
	 * @param company_name
	 *            公司名称
	 * @return
	 */
	private String getMallUserNameByCompanyName(String company_name) {

		Map<String, String> params = new HashMap<String, String>();

		params.put("name", company_name);

		try {

			logger.info("开始调用：通过公司名称在商城中查询公司对应的商城会员账号接口     参数："
					+ params.toString());

			String result_str = URLRequest
					.get(this.getCompanyByNameURL, params);

			JSONObject result = JSONObject.fromObject(result_str);

			logger.info("通过公司名称在商城中查询公司对应的商城会员账号返回数据：" + result.toString());

			int code = result.getInt("code");

			if (code == 0)
				return null;

			return result.getJSONObject("resultData").getString("username");

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 调用商城接口，检查在商城中是否存在同名企业信息。 存在返回true ，否则返回false
	 * 
	 * @param company_name
	 *            企业名称
	 * @return
	 */
	private boolean isExistsCompanyByNameInMall(String company_name) {

		Map<String, String> params = new HashMap<String, String>();

		params.put("name", company_name);

		try {

			String result_str = URLRequest
					.get(this.getCompanyByNameURL, params);

			JSONObject result = JSONObject.fromObject(result_str);

			int code = result.getInt("code");

			return code == 1;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}

	}

	/**
	 * 注册商城用户
	 *
	 * @param companyPOJO
	 * @param keyPerson
	 * @return
	 */
	public String registeredMailUser(CompanyPOJO companyPOJO,
			Contacter keyPerson) throws Exception {

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("truename", keyPerson.getName());
			params.put("email", keyPerson.getEmail());
			params.put("career", keyPerson.getPosition());
			if (null != companyPOJO.getAreaId()) {
				params.put("areaid", companyPOJO.getAreaId() + "");
			}
			if (null != keyPerson.getSex()) {
				params.put("gender", keyPerson.getSex() + "");
			}
			params.put("company", companyPOJO.getName());
			params.put("qq", keyPerson.getQq());
			params.put("mobile", keyPerson.getMobilePhone());
			logger.info("注册用户参数》》》》:" + params.toString());
			String result = URLRequest.post("", params);
			logger.info("调用注册用户之后结果:" + result);
			JSONObject jsonObject = JSONObject.fromObject(result);
			logger.info(">>>>>>>>=" + jsonObject.getString("message"));

			int code = jsonObject.getInt("code");
			String message = jsonObject.getString("message");

			if (code == 0)
				throw new Exception(message);

			return JSONObject.fromObject(jsonObject.getString("resultData"))
					.getString("userid");

			// if (!jsonObject.containsKey("code") ||
			// !"1".equals(jsonObject.getString("code"))) {
			// logger.error("注册商城用户 failed 参数：" + params.toString() + ",原因:" +
			// jsonObject.getString("message"));
			// throw new Exception("add user [addUser] failed");
			// }
			//
			// logger.info("userid=" +
			// JSONObject.fromObject(jsonObject.getString("resultData")).getString("userid"));
			// return keyPerson.getMobilePhone() + "," +
			// JSONObject.fromObject(jsonObject.getString("resultData")).getString("userid");

		} catch (Exception e) {
			logger.error("注册商城用户错误", e);
			throw e;
		}
	}

	/**
	 * 注册撮合系统的用户
	 *
	 * @param companyId
	 * @param itemId
	 * @param companyPOJO
	 * @param userId
	 * @throws Exception
	 */
	public void registeredUser(Integer companyId, String itemId,
			CompanyPOJO companyPOJO, Integer userId) throws Exception {
		try {
			// 创建客户信息
			Customer customer = new Customer();
			// customer.setCompanyId(companyId);
			customer.setItemId(Integer.parseInt(itemId));
			// 检查客户是否重复
			Customer c = icustomerDao.getCustomer(customer);
			if (c != null) {
				throw new Exception("客户已经存在，请勿重复添加");
			}
			customer.setPic(companyPOJO.getPic());
			customer.setCategorySource(companyPOJO.getCategorySource());
			customer.setCreatedBy(userId);
			customer.setUpdatedBy(userId);
			// 新增客户
			int insertCus = this.saveCustomer(customer);
			if (insertCus == 0 || customer.getId() == null) {
				throw new Exception("save customer error");
			}
		} catch (Exception e) {
			logger.error("注册商城用户错误", e);
			throw e;
		}
	}

	/**
	 * 注册商城的公司信息
	 *
	 * @param companyPOJO
	 * @param keyPerson
	 * @throws Exception
	 */
	public void registeredMailConpany(CompanyPOJO companyPOJO,
			Contacter keyPerson, String userId) throws Exception {
		try {
			// 同步公司信息
			String jsonStr = null;
			Map<String, String> map = new HashMap<>();
			map.put("company", companyPOJO.getName());
			map.put("level", companyPOJO.getLevel() + "");
			map.put("buy", companyPOJO.getBuyProducts());
			map.put("sell", companyPOJO.getSalesProducts());
			map.put("business", companyPOJO.getSalesProducts());
			if (companyPOJO.getAreaId() != null) {
				map.put("areaid", companyPOJO.getAreaId() + "");
			}
			map.put("address", companyPOJO.getAddress());
			map.put("size", companyPOJO.getCategoryEmployee() + "");
			map.put("homepage", companyPOJO.getCompanySite());
			map.put("regyear", DateUtil.doFormatDate(
					companyPOJO.getRegisterDate(), "yyyy"));
			map.put("telephone", companyPOJO.getCompanyPhone());
			map.put("username", keyPerson.getMobilePhone());
			map.put("userid", userId);

			logger.info("开始调用接口 ：同步公司信息    参数：  " + map.toString());

			String result = URLRequest.post(addCompany, map);
			// logger.info("调用addCompany接口结果>>" + result);
			JSONObject jsonObject = JSONObject.fromObject(result);

			logger.info("同步公司信息返回数据：" + jsonObject.toString());

			if (jsonObject == null || !jsonObject.containsKey("code")
					|| !"1".equals(jsonObject.getString("code"))) {
				throw new Exception(
						"call interface [updateCompanyInfo] failed1" + jsonStr);
			}
		} catch (Exception e) {
			logger.error("注册商城公司错误", e);
			throw e;
		}

	}

	@Override
	public List<String> serachBrand(String name, int itemId) {
		ArrayList<String> rltArr = new ArrayList<String>();
		String options = icustomerDao.queryBrandFromCommodityAttr(itemId);
		if (options != null && !options.equals("")) {
			List<String> array = Arrays.asList(options.split(","));
			for (String str : array) {
				if (str.contains(name)) {
					rltArr.add(str);
				}
			}
		}
		return rltArr;
	}

	@Override
	public Map<String, Object> restoreCustomer(int userId, int customerId) {

		Map<String, Object> map = new HashMap<String, Object>();

		int count = icustomerDao.restoreCustomer(userId, customerId);

		if (count == 1) {
			map.put("status", "ok");
			map.put("msg", "还原成功");
		} else {
			map.put("status", "faild");
			map.put("msg", "还原失败");
		}
		return map;

	}

	@Override
	public Integer updateremindDate(int customerId) {
		// List<Category> list=iCategoryBO.getByModId(8);
		Customer cus = icustomerDao.getOneById(customerId);
		if (cus != null) {
			Integer categoryfreqid = cus.getCategoryFreq();
			if (categoryfreqid != null) {
				Category category = iCategoryBO.getOneById(categoryfreqid);
				if (category != null) {
					String categoryfreqidName = category.getName();
					String reruStr = this.remindTime(categoryfreqidName);
					if (reruStr != null) {
						RemindCustomerEntity rct = new RemindCustomerEntity();
						rct.setCustomerId(customerId);
						rct.setRemindTime(reruStr);
						icustomerDao.updateremindDate(rct);

					}
				}
			}

		}
		return null;
	}

	public String remindTime(String categoryfreqidName) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(new Date());

		System.out.println(calendar.get(Calendar.DATE));// 加1之后的日期Top
		if (categoryfreqidName == null || "不定时".equals(categoryfreqidName)) {
			return null;
		} else if ("一周两次".equals(categoryfreqidName)) {
			calendar.add(Calendar.DAY_OF_YEAR, 4);
		} else if ("一周一次".equals(categoryfreqidName)) {
			calendar.add(Calendar.DAY_OF_YEAR, 7);// 让日期加7
		} else if ("一月一次".equals(categoryfreqidName)) {
			calendar.add(Calendar.MONTH, 1);// 让日期加一个月

		} else {
			calendar.add(Calendar.DAY_OF_YEAR, 7);// 让日期加7
		}

		return sdf.format(calendar.getTime());

	}

	@Override
	public List<RemindCustomerEntity> queryRemindCustomer(
			RemindCustomerEntity remindCustomerEntity) {

		return icustomerDao.queryRemindCustomer(remindCustomerEntity);
	}

	@Override
	public void tiaoguo(RemindCustomerEntity remindCustomerEntity) {
		icustomerDao.updateremindDate(remindCustomerEntity);

	}

	@Override
	public JSONObject checkCustomer(String phone) throws Exception {
		JSONObject result = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("telephone", phone);
		try {
			logger.info("开始调用：检查联系人电话在商城中状态接口   参数：" + params.toString());
			// String result_str = URLRequest.post(this.checkCustom, params);
			String result_str = URLRequest.get(this.checkCustom, params);
			result = JSONObject.fromObject(result_str);

			if (result != null) {
				logger.info("检查联系人电话在商城中状态返回数据" + result.toString());
			}
		} catch (Exception e) {
			logger.error("调用检查联系人电话在商城中状态接口出错");
			e.printStackTrace();
			result = null;
			throw new Exception("商城联系人接口调用错误!错误原因：" + e.getMessage());
		}
		return result;
	}

	@Override
	public Boolean checkCusByComNameAndItem(Customer customer) {
		Integer companyId = icustomerDao.queryIdByNameAndItem(
				customer.getCompanyName(), customer.getItemId());
		return companyId != null;
	}

	@Override
	public List<Customer> getCustomerByAccountAndItemId(Customer cust) {
		List<Customer> list = null;
		try {
			list = icustomerDao.getCustomerByAccountAndItemId(cust);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> queryCustomerTag(Map<String, Object> param) {
		return this.icustomerDao.queryCustomerTag(param);
	}

	@Override
	public List<Chats> getChatsByemployeeId(Map<String, Object> map) {
		List<Chats> list = null;
		try {
			list = iChatsDAO.getChatsByemployeeId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public DiscussChatRecord getdiscussChatRecordByChatId(int chatID) {
		DiscussChatRecord chatRecord = null;
		try {
			chatRecord = iChatsDAO.getdiscussChatRecordByChatId(chatID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chatRecord;
	}

	@Override
	public User getUserByEmployeeId(int employeeId) {
		User user = null;
		try {
			user = iChatsDAO.getUserByEmployeeId(employeeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void updateById(Map<String, Object> map) {
		try {
			iChatsDAO.updateById(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
