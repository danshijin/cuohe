package com.smm.cuohe.bo.dealmake.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.IItemAttrBO;
import com.smm.cuohe.bo.dealmake.IBuyOrderBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.bo.notice.NoticeBo;
import com.smm.cuohe.dao.IAttrValuesDao;
import com.smm.cuohe.dao.IBusinessDAO;
import com.smm.cuohe.dao.ICompanyDAO;
import com.smm.cuohe.dao.IItemAttrDao;
import com.smm.cuohe.dao.IPurchaseDao;
import com.smm.cuohe.dao.ISellPublishDAO;
import com.smm.cuohe.dao.base.ChCommodityAttrEntityMapper;
import com.smm.cuohe.dao.base.ChCommodityEntityMapper;
import com.smm.cuohe.dao.base.ChWareHouseMapper;
import com.smm.cuohe.dao.dealmake.ISellPoolDao;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.Product;
import com.smm.cuohe.domain.SellPublish;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChCommodityAttrEntity;
import com.smm.cuohe.domain.base.ChCommodityEntity;
import com.smm.cuohe.domain.base.ChWareHouse;
import com.smm.cuohe.domain.dealmake.CommodityEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.Warehouse;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.MD5Util;
import com.smm.cuohe.util.StringUtil;
import com.smm.cuohe.util.URLRequest;

/**
 * 发布卖盘池
 * 
 * @author zhangnan
 */
@Service
public class SellPoolBOImpl implements ISellPoolBO {

	private Logger logger = Logger.getLogger(SellPoolBOImpl.class);

	@Resource
	IBusinessDAO iBusinessDao;
	
	@Resource
	private ISellPoolDao sellPoolDao;

	@Resource
	private RestTemplate restTemplate;

	@Resource
	private ISellPublishDAO sellPublishDao;

	@Resource
	private IPurchaseDao iPurchaseDao;

	@Resource
	private IItemAttrDao itemAttrDao;

	@Resource
	private IAttrValuesDao attrValuesDao;

	@Resource
	private ICompanyDAO iCompanyDao;

	@Resource
	private ICompanyBO companyBO;

	@Resource
	private IItemAttrBO itemAttrBO;

	@Resource
	private IAreasBO iAreasBO;

	@Resource
	private IBuyOrderBO iBuyOrderBO;

	@Resource
	private IAttrValuesBO attrValuesBO;
	
	@Resource
	private NoticeBo noticeBo;
	
	@Resource
	private ChWareHouseMapper chWareHouseDao;
	
	@Resource
	private ChCommodityAttrEntityMapper commodityAttrDao;

	@Value("#{ch['getSHFEPrice.URL']}")
	private String shfePriceUrl;

	@Value("#{ch['getPriceProduct.URL']}")
	private String priceProductUrl;

	@Value("#{ch['publishMall.URL']}")
	private String publishMallUrl;

	@Value("#{ch['producter.URL']}")
	private String producter;

	@Value("#{ch['checkSeller.URL']}")
	private String checkSeller;

	@Value("#{ch['publicitem.URL']}")
	private String publicitem;

	@Value("#{ch['checkUserPay.URL']}")
	private String checkUserPay;

	@Value("#{ch['dealdemall.URL']}")
	private String dealdemall;

	@Value("#{ch['updatePoolSortNum.URL']}")
	private String updatePoolSortNum;
	
	@Value("#{ch['changeOfferPrice.URL']}")
	private String updateMallSellPool;
	
	@Value("#{ch['updateSellPoolMall.URL']}")
	private String updateSellPoolMall;
	
	@Resource
	private ChCommodityEntityMapper commodityDao;
	
	private void printMessage(String msg){
		logger.info("\n" + msg +"\n");
	}
	
	@Override
	public void addSellPool(SellPool sellPool, User user)
			throws RuntimeException {
		try {
			sellPool.setItemsid(user.getItems().getId());
			sellPool.setStatus(0);

			sellPool.setCreatedat(new Date());
			if (sellPool.getCreatedby() == null) {
				sellPool.setCreatedby(user.getId());
			}

			sellPool.setUpdatedat(new Date());
			if (sellPool.getUpdatedby() == null) {
				sellPool.setUpdatedby(user.getId());
			}
			
			//编号 默认 当前时间+00
			if (sellPool.getTimeSort() == null || sellPool.getTimeSort().equals("")) {
				String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
				nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+0;
				
				sellPool.setTimeSort(nowTimeTemp);
			}
			//服务器时间
			sellPool.setUpdatedat(new Date());
			sellPool.setCreatedat(new Date());
			
			sellPoolDao.addSellPool(sellPool);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<SellPool> queryAllSellPool() throws RuntimeException {
		return sellPoolDao.queryAllSellPool();
	}

	@Override
	public int querySellPoolCount(Map<String, Object> parameters)
			throws RuntimeException {
		int count = 0;
		if (parameters != null && parameters.size() > 0) {
			count = sellPoolDao.querySellPoolCount(parameters);
		}
		return count;
	}

	@Override
	public List<SellPool> querySellPoolList(Map<String, Object> parameters)
			throws RuntimeException {
		List<SellPool> list = null;
		if ((parameters != null) && (parameters.size() > 0)) {
			list = sellPoolDao.querySellPoolList(parameters);
		}
		return list;
	}

	@Override
	public List<SellPool> querySellOrderList(Map<String, Object> parameters)
			throws RuntimeException {
		List<SellPool> list = null;
		if ((parameters != null) && (parameters.size() > 0)) {
			list = sellPoolDao.querySellOrderList(parameters);
		}
		return list;
	}

	@Override
	public void delAll(String ids) throws RuntimeException {
		String[] sellPoolIds = ids.split(",");
		if (sellPoolIds != null && sellPoolIds.length > 0) {
			for (int i = 0; i < sellPoolIds.length; i++) {
				delpool(sellPoolIds[i]);
			}
		}
	}

	@Override
	@Transactional
	public void delpool(String id) {
		// 获取商城挂单id
		logger.error("获取商城挂单PoolId" + id);
		SellPool sellPool = sellPoolDao.getSellPoolByPoolId(Integer.valueOf(id));
		if(sellPool!=null){
			logger.error("获取商城挂单id" + sellPool.getMallSaleId());
			if (sellPool.getMallSaleId() != null) {//证明是推送到商城的 则需要同步商城关闭
				Map<String, String> params = new HashMap<String, String>();
				params.put("mallid", sellPool.getMallSaleId().toString());
				params.put("status", "2");
				JSONObject result = null;
				String result_str = "";
				try {
					logger.info("开始调用：撮合挂单修改提交商城接口   参数： "+params.toString());
					result_str = URLRequest.post(dealdemall, params);
					//logger.error("调用商城接口结果=====》" + result_str);
					result = JSONObject.fromObject(result_str);
					if (result != null) {
						logger.info("撮合挂单修改提交商城返回数据"+result.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("调用撮合挂单修改提交商城接口失败");
					e.printStackTrace();
				}
				if (result != null) {
					int errno = result.getInt("errno");
					String msg = result.getString("msg");
					switch (errno) {
					case 0:
						sellPoolDao.delAll(Integer.parseInt(id));
						logger.error("撮合挂单修改提交商城成功" + msg);
						break;
					case 1:
						logger.error("撮合挂单修改提交商城失败" + msg);
						break;
					default:
						logger.error("撮合挂单修改提交商城失败" + msg);
						break;
					}
				}
				noticeBo.transactionClose(Integer.valueOf(id),1);
			}else{//证明是没有同步到商城的 则只需要本地关闭
				sellPoolDao.delAll(Integer.parseInt(id));
			}
		}
	}

	@Value("#{ch['quoteRecord.URL']}")
	private String quoteRecordUrl;

	@Override
	public Map<String, Object> getPagingOfferRecord(int customerId, User user,
			int start, int len) {
		String companyAccount = iCompanyDao
				.getCompanyAccountByCustomerId(customerId);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categoryID", user.getItems().getCategoryId()); // 用户对应品目的categoryId
			map.put("username", companyAccount); // 公司对应的商城登录名
			map.put("zeroDateTimeBehavior", "convertToNull");
			map.put("pageNo", (start / len + 1) + "");
			map.put("pageSize", len + "");
			String paramMap = JSONUtil.doConvertObject2Json(map);
			param.add("paramMap", paramMap);
			
			logger.info("开始调用：获取报价记录接口   参数："+paramMap);
			
			String jsonStr = restTemplate.postForObject(quoteRecordUrl, param,
					String.class);

			if (StringUtils.isNotBlank(jsonStr)) {
				result.put("msg", "success");
				result.put("result", jsonStr);
			} else {
				result.put("msg", "获取失败");
			}
		} catch (Exception e) {
			logger.info("SHFE报价记录接口调用发生异常");
			result.put("msg", "获取失败");
		}

		return result;
	}

	@Override
	public List<SellPool> querySellOrderList(Map<String, Object> parameters,
			String type, String parameter1, String parameter2, String parameter3) {
		/*
		 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); Calendar ca
		 * = Calendar.getInstance(Locale.CHINA);
		 */
		parameters.put("type", type);

		// 拼接，方便SQL编写
		if (parameter1 != null && parameter1.length() > 0 && parameter2 != null
				&& parameter2.length() > 0) {
			parameters.put("parameter1", parameter1);
			if (parameter2.equals("1")) {// 包含
				parameter2 = "like " + " '%" + parameter3 + "%'";
			} else if (parameter2.equals("2")) {// 不包含
				parameter2 = "not like " + " '%" + parameter3 + "%'";
			} else if (parameter2.equals("3")) {// 等于
				parameter2 = " ='" + parameter3 + "'";
			} else if (parameter2.equals("4")) {// 不等于
				parameter2 = " !='" + parameter3 + "'";
			} else if (parameter2.equals("5")) {// 开始字符
				parameter2 = "like " + " '" + parameter3 + "%'";
			} else if (parameter2.equals("6")) {// 结束字符
				parameter2 = "like " + " '%" + parameter3 + "'";
			} else if (parameter2.equals("7")) {// 为空
				parameter2 = "IS NULL";
			} else if (parameter2.equals("8")) {// 不为空
				parameter2 = "IS NOT NULL";
			}
			parameters.put("parameter2", parameter2);
		}
		List<SellPool> list = null;
		if ((parameters != null) && (parameters.size() > 0)) {
			list = sellPoolDao.querySellOrderList(parameters);
		}
		return list;
	}

	/**
	 * 调用接口，获取实时价格
	 */
	@Override
	public Map<String, Object> getPriceProduct(User user) {

		// 根据当前登录的用户，获取所在品目组

		String type = user.getItems().getSHFEType();
		String productId = user.getItems().getChartId();
		String itemName = user.getItems().getName();

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// SHFE实时价格 参数为TYPE 例如：CU
			String spURL = shfePriceUrl;
			spURL = spURL.replace("{0}", type);
			
			logger.info("开始调用：获取SHFE实时价格接口");
			
			String shfePrice = restTemplate.postForObject(spURL, null,
					String.class);

			if (StringUtils.isNotBlank(shfePrice)) {
				Map<String, Object> spMap = JSONUtil
						.doConvertJson2Map(shfePrice);

				logger.info("获取SHFE实时价格返回结果："+spMap);
				
				if (spMap.containsKey("code")
						&& "success".equals(spMap.get("code"))) {
					Double avg = (Double) spMap.get("resultData");
					result.put("avg", avg);
				} else {
					result.put("avg", "获取失败");
				}

			} else {
				result.put("avg", "获取失败");
			}
		} catch (Exception e) {
			logger.info("SHFE实时价格接口调用发生异常");
			result.put("avg", "获取失败");
		}

		try {
			// 产品实时报价 参数为productId 例如：201102250376
			String ppURL = priceProductUrl;
			ppURL = ppURL.replace("{0}", productId);
			
			logger.info("开始调用：获取产品实时价格接口");
			
			String priceProduct = restTemplate.postForObject(ppURL, null,
					String.class);

			if (StringUtils.isNotBlank(priceProduct)) {
				Map<String, Object> ppMap = JSONUtil
						.doConvertJson2Map(priceProduct);

				logger.info("获取产品实时价格返回结果："+ppMap);
				
				if (ppMap.containsKey("code")
						&& "success".equals(ppMap.get("code"))) {
					Double price = (Double) ppMap.get("resultData");
					result.put("price", price);
				} else {
					result.put("price", "获取失败");
				}

			} else {
				result.put("price", "获取失败");
			}
		} catch (Exception e) {
			logger.info("产品实时报价接口调用发生异常");
			result.put("price", "获取失败");
		}

		result.put("itemName", itemName);

		return result;
	}

	/**
	 * @param sellPoolId
	 * @return
	 * @discription 根据id查询卖盘池主要信息
	 * @author Nancy/张楠
	 * @created 2015年8月14日 下午2:22:40
	 * @see com.smm.cuohe.bo.dealmake.ISellPoolBO#getSellPoolById(java.lang.Integer)
	 */

	@Override
	public SellPool getSellPoolById(Integer sellPoolId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sellPoolId", sellPoolId);
		SellPool sellPool = this.sellPoolDao.getSellPoolById(param);
		return sellPool;
	}

	@Override
	public void addSellPublish(SellPublish sellPublish, User user) {
		try {
			if (user != null) {
				sellPublish.setCreatedby(user.getId());
			}
			// 页面传过来的时间转换成date
			if (sellPublish.getTermdateString() != null) {
				sellPublish.setTermdate(DateUtil.doSFormatDate(
						sellPublish.getTermdateString(), "yyyy-MM-dd"));
			}
			if (sellPublish.getDeliverydateString() != null) {
				sellPublish.setDeliverydate(DateUtil.doSFormatDate(
						sellPublish.getDeliverydateString(), "yyyy-MM-dd"));
			}
			if (sellPublish.getDatebeginString() != null) {
				sellPublish.setDatebegin(DateUtil.doSFormatDate(
						sellPublish.getDatebeginString(), "yyyy-MM-dd"));
			}
			if (sellPublish.getDateendString() != null) {
				sellPublish.setDateend(DateUtil.doSFormatDate(
						sellPublish.getDateendString(), "yyyy-MM-dd"));
			}
			sellPublish.setCreatedat(new Date());
			this.sellPublishDao.addSellPublish(sellPublish);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String sellPoolImport(List<Map<String, String>> dataInfo, User user) {
		String errorInfo = "";
		// 获得导入excel的信息，校验信息准确性
		String excelRowNum = "";
		java.util.Date dtNow = new java.util.Date();
		List<Map<String, Object>> dataList = new ArrayList<>();
		List<Areas> arealist = iAreasBO.getParentAreas();
		// 获得品目属性信息
		List<ItemAttr> itemAttrInfo = itemAttrDao.getItemAttrByItemsId(user
				.getItems().getId());
		for (Map<String, String> item : dataInfo) {
			try {
				List<AttrValue> attrvalueList = new ArrayList<>();// 初始化属性存储信息
				String errorTemp = "";
				// 整理买盘池准备导入的数据
				// SellPoolFull sellPoll = new SellPoolFull();
				DecimalFormat df = new DecimalFormat("######0");
				// item.clear();
				excelRowNum = df
						.format(Double.valueOf(item.containsKey("序号") ? item
								.get("序号").trim() : "0"));// 获得excel导入的行号
				// System.out.println(excelRowNum);

				// 先进行数据校验
				// 准备查询时装载参数
				Map<String, Object> mapParam = new HashMap<>();
				// 序号
				String excelNumStr = item.containsKey("序号") ? item.get("序号")
						.trim() : "";
				if (StringUtils.isBlank(excelNumStr)) {
					errorTemp += "序号不为空；";
				}

				// 品目名称
				String itemNameStr = item.containsKey("品目名称") ? item
						.get("品目名称").trim() : "";
				Integer itemID = 0;
				if (StringUtils.isBlank(itemNameStr)) {
					errorTemp += "品目名称不为空；";
				} else {
					List<Items> listItems = this.iPurchaseDao
							.getItemsInfoByCondition("name='" + itemNameStr
									+ "' and status=0");
					if (listItems != null && listItems.size() > 0) {
						itemID = listItems.get(0).getId();
					} else {
						errorTemp += "品目信息错误；";
					}
				}

				// 判断导入的品目是否符合当前品目数据
				if (user.getItems().getId() != itemID) {
					errorTemp += "导入品目信息和当前操作品目不符；";
				}

				// 品目产品名称
				String productNameStr = item.containsKey("品目产品名称") ? item.get(
						"品目产品名称").trim() : "";
				Integer productId = 0;
				if (StringUtils.isBlank(productNameStr)) {
					errorTemp += "品目产品名称不为空；";
				} else {
					List<Product> listProduc = sellPoolDao
							.getProductByCondition("and cat_id=" + itemID
									+ " and name='" + productNameStr + "'");
					if (listProduc != null && listProduc.size() > 0) {
						productId = listProduc.get(0).getId();
					} else {
						errorTemp += "品目产品名称错误；";
					}
				}

				// 企业名称
				String companyNameStr = item.containsKey("企业名称") ? item.get(
						"企业名称").trim() : "";
				Integer customerId = 0;
				if (StringUtils.isBlank(companyNameStr)) {
					errorTemp += "企业名称不为空；";
				} else {
					mapParam = new HashMap<>();
					mapParam.put("name", companyNameStr);// 企业名称
					mapParam.put("itemId", itemID);// 企业名称
					List<Company> listCompany = companyBO
							.queryCompanyByNm(mapParam);
					if (listCompany != null && listCompany.size() > 0) {
						// customerId = listCompany.get(0).getId();
						// Company temp = listCompany.get(0);
						// customerId = temp.getId();
						for (Company companyItem : listCompany) {
							System.out.print(companyItem.getName() + "\r\n");
							if (companyItem.getName().trim()
									.equals(companyNameStr.trim())) {
								customerId = companyItem.getId();
								break;
							}
						}
						if (customerId == 0) {
							errorTemp += "企业信息不存在；";
						}

					} else {
						errorTemp += "企业信息不存在；";
					}
				}

				// 库存
				String quantityStr = item.containsKey("库存") ? item.get("库存").trim() : "";
				if (StringUtils.isBlank(quantityStr)) {
					errorTemp += "库存不为空；";
				} 

				// 仓库地区名称
				String wareAreaNamStr = item.containsKey("仓库地区名称") ? item.get(
						"仓库地区名称").trim() : "";
				Integer wareAreaNamInt = 0;
				if (StringUtils.isBlank(wareAreaNamStr)) {
					errorTemp += "仓库地区名称不为空；";
				} else {
					for (Areas area : arealist) {
						if (area.getName().equals(wareAreaNamStr)) {
							wareAreaNamInt = area.getId();
							break;
						}
					}
					if (wareAreaNamInt == 0) {
						errorTemp += "仓库地区名称不存在；";
					}
				}

				// 仓库名称
				String wareHouseNameStr = item.containsKey("仓库名称") ? item.get(
						"仓库名称").trim() : "";
				Integer wareHouseNameInt = 0;
				if (StringUtils.isBlank(wareHouseNameStr)) {
					errorTemp += "仓库名称不为空；";
				} else {
					List<Warehouse> listWarehouse = iBuyOrderBO.selWarehouseByArea(null,wareAreaNamInt,user.getItemId());
					for (Warehouse warehouse : listWarehouse) {
						if (warehouse.getName().equals(wareHouseNameStr)) {
							wareHouseNameInt = warehouse.getId();
							break;
						}
					}
					if (wareHouseNameInt == 0) {
						errorTemp += "仓库名称不存在；";
					}
				}

				// 价格
				String priceStr = item.containsKey("价格") ? item.get("价格").trim() : "";
				
				if (StringUtils.isBlank(priceStr)) {
					errorTemp += "价格不为空；";
				} 

				// 起订量
				String moqStr = item.containsKey("起订量") ? item.get("起订量")
						.trim() : "";
				if (StringUtils.isBlank(moqStr)) {
					errorTemp += "起订量不为空；";
				}

				// 超出部分按n递增
				String outmoqStr = item.containsKey("超出部分按n递增") ? item.get(
						"超出部分按n递增").trim() : "";
				if (StringUtils.isBlank(outmoqStr)) {
					errorTemp += "超出部分按n递增不为空；";
				}

				// 磅差
				String overflowStr = item.containsKey("磅差") ? item.get("磅差")
						.trim() : "";
				if (StringUtils.isBlank(overflowStr)) {
					errorTemp += "磅差不为空；";
				} else {
					if (overflowStr.contains(".")) {
						overflowStr = overflowStr.replace(overflowStr
								.substring(overflowStr.indexOf("."),
										overflowStr.length()), "");
					}
				}

				// 交付方式
				String deliveryStr = item.containsKey("交付方式") ? item
						.get("交付方式").trim() : "";
				Integer delivery = 0;
				if (StringUtils.isBlank(deliveryStr)) {
					errorTemp += "交付方式不为空；";
				} else {
					if (deliveryStr.equals("卖方送货")) {
						delivery = 1;
					} else if (deliveryStr.equals("买方自提")) {
						delivery = 2;
					}
					if (delivery == 0) {
						errorTemp += "交付方式信息错误；";
					}
				}

				// 结算方式
				String paytypeStr = item.containsKey("结算方式") ? item.get("结算方式")
						.trim() : "";
				Integer paytypeInt = 0;
				if (StringUtils.isBlank(paytypeStr)) {
					errorTemp += "结算方式不为空；";
				} else {
					if (paytypeStr.equals("现款现汇")) {
						paytypeInt = 1;
					} else if (paytypeStr.equals("银行承兑")) {
						paytypeInt = 2;
					} else if (paytypeStr.equals("有色网代收代付")) {
						paytypeInt = 3;
					}

					if (paytypeInt == 0) {
						errorTemp += "结算方式信息错误；";
					}
				}

				// 付款方式
				String receipttypeStr = item.containsKey("付款方式") ? item.get(
						"付款方式").trim() : "";
				Integer receipttypeInt = 0;
				if (StringUtils.isBlank(receipttypeStr)) {
					errorTemp += "付款方式不为空；";
				} else {
					if (receipttypeStr.equals("款到发货")) {
						receipttypeInt = 1;
					} else if (receipttypeStr.equals("货到付款")) {
						receipttypeInt = 2;
					}

					if (receipttypeInt == 0) {
						errorTemp += "付款方式信息错误；";
					}
				}

				// 交货日期
				String deliverytimeStr = item.containsKey("交货日期") ? item.get(
						"交货日期").trim() : "";
				Date deliverytimeDate = null;
				if (StringUtils.isBlank(deliverytimeStr)) {
					errorTemp += "交货日期不为空；";
				} else {
					deliverytimeDate = DateUtil.doSFormatDate(deliverytimeStr,
							"yyyy-MM-dd");
					if (deliverytimeDate == null) {
						errorTemp += "交货日期信息错误；";
					}
				}

				// 计量单位
				String unitStr = item.containsKey("计量单位") ? item.get("计量单位")
						.trim() : "";
				Integer unitInt = -1;
				if (StringUtils.isBlank(unitStr)) {
					errorTemp += "计量单位不为空；";
				} else {
					if (unitStr.equals("吨")) {
						unitInt = 0;
					} else if (unitStr.equals("千克")) {
						unitInt = 1;
					}

					if (unitInt == -1) {
						errorTemp += "计量单位信息错误；";
					}
				}

				// 优先级
				String priorityStr = item.containsKey("优先级") ? item.get("优先级")
						.trim() : "";
				Integer priorityInt = -1;
				if (StringUtils.isBlank(priorityStr)) {
					errorTemp += "优先级不为空；";
				} else {
					if (priorityStr.equals("高")) {
						priorityInt = 0;
					} else if (priorityStr.equals("中")) {
						priorityInt = 1;
					} else if (priorityStr.equals("低")) {
						priorityInt = 2;
					}

					if (priorityInt == -1) {
						errorTemp += "优先级信息错误；";
					}
				}

				// 判断当前信息中是否有该品目下所需要的所有必须属性
				for (int itemAttrNum = 0; itemAttrNum < itemAttrInfo.size(); itemAttrNum++) {
					ItemAttr itemAttr = itemAttrInfo.get(itemAttrNum);
					String AttrValStr = item.containsKey(itemAttr.getName()) ? item
							.get(itemAttr.getName()).trim() : "";
					if (StringUtils.isBlank(AttrValStr)) {
						if (itemAttr.getMainproperty() == 1) {
							errorTemp += (itemAttr.getName() + "不为空；");
						}
					} else {
						if (itemAttr.getOptions().contains(AttrValStr)) {
							AttrValue attr = new AttrValue();
							attr.setProdId(productId);
							attr.setAttrName(itemAttr.getName());
							attr.setAttrValue(AttrValStr);
							attr.setAttrId(itemAttr.getId());
							attrvalueList.add(attr);
						} else {
							errorTemp += (itemAttr.getName() + "属性错误；");
						}
					}
				}

				/*
				 * // 负责人姓名 String userNameStr = item.get("userName").trim(); if
				 * (StringUtils.isBlank(userNameStr)) { errorTemp +=
				 * "负责人姓名不为空；"; }
				 * 
				 * // 负责人Email String userEmailStr =
				 * item.get("userEmail").trim(); if
				 * (StringUtils.isBlank(userEmailStr)) { errorTemp +=
				 * "负责人Email不为空；"; }
				 * 
				 * Integer userID = 0; // 负责人姓名和email都不为空时校验是否存在 if
				 * ((!StringUtils.isBlank(userEmailStr)) &&
				 * (!StringUtils.isBlank(userNameStr))) { List<User> listUser =
				 * this.iPurchaseDao .getUserInfoByCondition("name='" +
				 * userNameStr + "' and email='" + userEmailStr +
				 * "' and status=0"); if (listUser != null && listUser.size() >
				 * 0) { userID = listUser.get(0).getId(); } else { errorTemp +=
				 * "负责人信息错误；"; } }
				 */

				if (!Objects.equals(errorTemp, "")) {// 返回无法执行错误
					errorInfo += "第" + excelRowNum + "行数据校验错误：" + errorTemp
							+ "\r\n";
					continue;
				}

				// 1.收集品目信息
				Items items = new Items();
				items.setId(itemID);
				items.setName(itemNameStr);

				// 2.收集User信息使用 读取页面登录信息
				/*
				 * user = new User(); user.setId(userID); user.setItems(items);
				 * user.setItemId(itemID.toString()); user.setName(userNameStr);
				 */

				// 3.收集商品属性信息
				/*
				 * List<AttrValue> attrvalueList = new ArrayList<>(); // 3.1
				 * 整理国别属性 AttrValue countryAttr = new AttrValue();
				 * countryAttr.setProdId(productId);
				 * countryAttr.setAttrName("国别");
				 * countryAttr.setAttrValue(countryStr);
				 * countryAttr.setAttrId(countryAttrId);
				 * attrvalueList.add(countryAttr); // 3.2 整理品牌属性 AttrValue
				 * brandAttr = new AttrValue(); brandAttr.setProdId(productId);
				 * brandAttr.setAttrName("品牌");
				 * brandAttr.setAttrValue(brandStr);
				 * brandAttr.setAttrId(brandAttrId);
				 * attrvalueList.add(brandAttr); // 3.3整理产地属性 AttrValue
				 * placeAttr = new AttrValue(); placeAttr.setProdId(productId);
				 * placeAttr.setAttrName("产地");
				 * placeAttr.setAttrValue(placeStr);
				 * placeAttr.setAttrId(placeAttrId);
				 * attrvalueList.add(placeAttr);
				 */

				// 4.整理商品信息，新增商品信息要依据商品属性是否存在
				CommodityEntity commodity = new CommodityEntity();
				commodity.setId(0);
				commodity.setProdId(productId.toString());
				commodity.setCatId(user.getItems().getId());
				commodity.setCatName(user.getItems().getName());
				commodity.setName(productNameStr);
				commodity.setCreateTime(DateUtil.doFormatDate(new Date(),
						"yyyy-MM-dd"));

				// 5.整理卖盘数据 sellPool
				SellPool sellPool = new SellPool();
				sellPool.setCompanyid(customerId);
				sellPool.setDelivery(delivery);
				sellPool.setDeliverytime(DateUtil.doFormatDate(
						deliverytimeDate, "yyyy-MM-dd"));
				sellPool.setItemsid(itemID);
				sellPool.setMoq(moqStr);
				// sellPool.setOrderNum(0);// 待定
				sellPool.setOutmoq(outmoqStr);
				sellPool.setOverflow(overflowStr);
				sellPool.setPaytype(paytypeInt);
				sellPool.setPrice(Double.parseDouble(priceStr));
				sellPool.setPriority(priorityInt);
				sellPool.setQuantity(quantityStr);
				sellPool.setReceipttype(receipttypeInt);
				sellPool.setUnit(unitInt);
				sellPool.setWareId(wareHouseNameInt);
				sellPool.setWareProvince(wareAreaNamInt);

				sellPool.setWareName(wareHouseNameStr);

				// 整理数据到数据持久方法内使用
				Map<String, Object> mapObj = new HashMap<String, Object>();
				mapObj.put("items", items);
				mapObj.put("user", user);
				mapObj.put("attrvalueList", attrvalueList);
				mapObj.put("commodity", commodity);
				mapObj.put("sellPool", sellPool);
				mapObj.put("excelNum", excelRowNum);

				dataList.add(mapObj);

			} catch (Exception e) {
				errorInfo += "第" + excelRowNum + "行数据解析出现异常。" + e.getMessage()
						+ "\r\n";
			}
		}

		if (Objects.equals(errorInfo, "")) {
			// 若导入的excel中没有错误信息，执行导入
			String saveRes = SaveSellPoolData(dataList);
			if (!StringUtils.isBlank(saveRes)) {
				errorInfo = "以下数据存储错误。\r\n" + saveRes;
			} else {
				errorInfo = "数据导入成功！";
			}
		}

		return errorInfo;
	}

	@Transactional
	private String SaveSellPoolData(List<Map<String, Object>> dataList) {
		String errorMsg = "";
		String excelNum = "";
		try {
			for (Map<String, Object> mapData : dataList) {
				String errorTemp = "";
				try {
					Items items = (Items) mapData.get("items");
					User user = (User) mapData.get("user");
					List<AttrValue> attrvalueList = (List<AttrValue>) mapData
							.get("attrvalueList");
					CommodityEntity commodity = (CommodityEntity) mapData
							.get("commodity");
					SellPool sellPool = (SellPool) mapData.get("sellPool");
					excelNum = mapData.get("excelNum").toString();

					// 1.调用接口校验商品是否存在
					Integer commodityId = null;
					Integer totalmallid = check(attrvalueList, attrvalueList
							.get(0).getProdId());
					if (totalmallid == -1) {

						errorTemp += "检查商品是否存在接口异常;";
					} else if (totalmallid == 0) {
						commodityId = null;
					} else {
						commodityId = totalmallid;
					}
					if (StringUtils.isBlank(errorTemp)) {
						// 2.根据ID查询企业会员账号
						String account = getcompanyAccount(sellPool
								.getCompanyid());

						// 3.新增商品
						if (commodityId == null) {// 商品不存在

							SellPool sell = producter(attrvalueList,commodity.getName(), user, account);

							if (sell.getErrno() == 1) {
								// return sell.getMsg();
								errorTemp += (sell.getMsg() + ";");
							}
							if (StringUtils.isBlank(errorTemp)) {
								Integer mallcommodityID = sell.getTotalmallid();
								commodity.setId(mallcommodityID);// 商品标号商城同步
								attrValuesBO.addCommodity(commodity);// 商品不存在添加商品
								commodityId = commodity.getId();
								for (int i = 0; i < attrvalueList.size(); i++) {
									if (attrvalueList.get(i).getAttrValue() == null
											|| "".equals(attrvalueList.get(i)
													.getAttrValue())) {

										continue;
									}
									AttrValue attrvalue = attrvalueList.get(i);
									if (attrvalue != null) {
										attrvalue.setCommodityId(commodityId);
										attrvalue.setCommodityName(commodity
												.getName());
										attrvalue.setCreaTime(DateUtil
												.doFormatDate(new Date(),
														"yyyy-MM-dd"));
										attrvalue.setEditTime(DateUtil
												.doFormatDate(new Date(),
														"yyyy-MM-dd"));
										attrValuesBO
												.addCommodityAttr(attrvalue);// 添加商品属性
									}

								}

							}
						}
						if (StringUtils.isBlank(errorTemp)) {
							sellPool.setUsername(user.getName());
							sellPool.setCommodityId(commodityId);
							sellPool.setMallUserAccount(account);
							// 4.检查卖盘吃是否存在重复记录 根据 卖家 ，商品 库存 价格
							Integer countsell = countSellpool(sellPool);
							if (countsell != null && countsell > 0) {
								// 重复
								// errorTemp += "卖盘池信息重复；"; 不校验了
							}
							if (StringUtils.isBlank(errorTemp)) {
								sellPool = addsellpoolmall(sellPool);// 卖盘同步商城
																		// 得到挂牌号
								if (1 == sellPool.getErrno()) {
									// return sellPool.getMsg();
									errorTemp += (sellPool.getMsg() + ";");
								}
								if (StringUtils.isBlank(errorTemp)) {
									sellPool.setSource(0);
									addSellPool(sellPool, user);
								}
							}
						}
					}
					if (!StringUtils.isBlank(errorTemp)) {
						errorMsg += "第" + excelNum + "行数据导入错误；" + errorTemp
								+ "\r\n";
					}

				} catch (Exception ex) {
					errorMsg += "第" + excelNum + "行数据导入错误；" + errorTemp
							+ "\r\n" + ex.getMessage() + "\r\n";
				}
			}
		} catch (Exception ex) {
			errorMsg += "第" + excelNum + "行数据导入错误；" + ex.getMessage() + "\r\n";
		}

		return errorMsg;
	}

	/**
	 * 卖盘池同步到商城
	 * 
	 * @param sellPool
	 * @return
	 */

	public SellPool addsellpoolmall(SellPool sellPool) {

		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

		param.add("totalmallid", sellPool.getCommodityId());
		param.add("warehouseid", sellPool.getWareId());
		param.add("price", sellPool.getPrice());
		param.add("areaid", sellPool.getWareProvince());// 地区id
		param.add("count", sellPool.getQuantity());
		param.add("paytype", sellPool.getPaytype());
		param.add("receipttype", sellPool.getReceipttype());
		param.add("delivery", sellPool.getDelivery());
		param.add("deliverytime",
				DateUtil.doSFormatDate(sellPool.getDeliverytime(), "yyyyMMdd")
						.getTime());
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
		if (sellPool != null && sellPool.getUnit() != null
				&& Integer.valueOf(sellPool.getUnit()) == 0) {
			param.add("unit", StringUtil.doEncoder("吨"));
		} else {
			param.add("unit", StringUtil.doEncoder("千克"));
		}
		param.add("sheetnum", sellPool.getQuantity());

		try {

			logger.info("开始调用：新增卖盘同步到商城接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(publicitem, param,String.class);

			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
				
				logger.info("新增卖盘同步到商城返回参数"+maps.toString());
				
				if (1 == errno) {// 参数错误
					sellPool.setErrno(1);
					sellPool.setMsg("同步商城接口调用异常："
							+ String.valueOf(maps.get("mallid")));
				} else if (255 == errno) {// 参数错误
					sellPool.setErrno(1);
					sellPool.setMsg("同步商城接口调用异常："
							+ String.valueOf(maps.get("mallid")));
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
			logger.info("开始调用：添加商品接口   参数："+param.toString());
			
			String jsonString = restTemplate.postForObject(producter, param,
					String.class);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			
			logger.info("添加商品返回数据："+maps.toString());
			
			//System.err.println(StringUtil.doDecoder(jsonString));
			Integer errno = (Integer) maps.get("errno");

			if (maps != null) {
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
			
			String jsonString = restTemplate.postForObject(checkSeller, param,
					String.class);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			
			logger.info("验证商品是否存在返回数据："+maps.toString());
			
			Integer errno = (Integer) maps.get("errno");
			if (maps != null) {
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

	@Override
	public Map<String, Object> getListSellPoolById(
			Map<String, Object> parameters, String type, String parameter1,
			String parameter2, String parameter3) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 最大的数据两的LinkedHashMap 由它作为表头处理
		List<String> listHeader = new ArrayList<String>();
		// 定义数据List
		List<LinkedHashMap<String, String>> listData = new ArrayList<LinkedHashMap<String, String>>();
		try {
			// 获得卖盘池数据
			// List<SellPool> listSellPoll =
			// this.sellPoolDao.getListSellPoolById(ids);
			List<SellPool> listSellPoll = querySellOrderList(parameters, type,parameter1, parameter2, parameter3);

			// 2.生成excel文件数据
			listHeader.add("客户");
			listHeader.add("产品");
			for (int i = 0; i < listSellPoll.size(); i++) {
				if (listSellPoll.get(i).getCommodityId() != null) {
					// 订单总量
					if (listSellPoll.get(i).getVolume() == null) {
						// listSellPoll.get(i).setOrderNum(0);
						listSellPoll.get(i).setVolume(0);
					}
					// 库存
					Double Quantity_dou = new Double(listSellPoll.get(i).getQuantity());
					Double Volume_dou = new Double(listSellPoll.get(i).getVolume());
					
					Double quantity_a=Quantity_dou - Volume_dou;
					
					String quantity = quantity_a.toString();
					
					listSellPoll.get(i).setQuantity(quantity);

					// 获得属性数据
					List<AttrValue> listAttrValue = this.attrValuesDao.getAttrValuesByExtId(listSellPoll.get(i).getCommodityId());
					listSellPoll.get(i).setAttrValues(listAttrValue);
				}

				// 生成导出使用的数据源
				LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
				tempMap.put("客户", listSellPoll.get(i).getCompanyNm());
				tempMap.put("产品", listSellPoll.get(i).getItemsNm());
				List<AttrValue> listAttrValue = listSellPoll.get(i).getAttrValues();
				if (listAttrValue != null && listAttrValue.size() > 0) {
					for (int j = 0; j < listAttrValue.size(); j++) {
						if (!listHeader.contains(listAttrValue.get(j).getAttrName())) {// 判断列名不重复
							listHeader.add(listAttrValue.get(j).getAttrName());
						}
						if (!tempMap.containsKey(listAttrValue.get(j).getAttrName())) {
							tempMap.put(listAttrValue.get(j).getAttrName(),listAttrValue.get(j).getAttrValue());
						}
					}
				}
				DecimalFormat df = new DecimalFormat("#.###");
				tempMap.put("价格", df.format(listSellPoll.get(i).getPrice()));
				tempMap.put("库存", df.format(Double.parseDouble(listSellPoll.get(i).getQuantity())));
				tempMap.put("仓库", listSellPoll.get(i).getWareNm());
				tempMap.put("更新时间", DateUtil.doFormatDate(listSellPoll.get(i).getCreatedat(), ""));
				tempMap.put("订单", listSellPoll.get(i).getOrderNum() + "笔");
				tempMap.put("报价人", listSellPoll.get(i).getCreatedNm());
				listData.add(tempMap);
			}
			listHeader.add("价格");
			listHeader.add("库存");
			listHeader.add("仓库");
			listHeader.add("更新时间");
			listHeader.add("订单");
			listHeader.add("报价人");
			// listHeader.add("交易备注");
			map.put("listHeader", listHeader);
			map.put("listData", listData);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	/**
	 * @param sellPublish
	 * @discription 发布卖盘池到商城
	 * @author Nancy/张楠
	 * @created 2015年8月19日 下午1:23:31
	 * @see com.smm.cuohe.bo.dealmake.ISellPoolBO#sellPoolPublish(com.smm.cuohe.domain.SellPublish)
	 */

	@Override
	public String sellPoolPublish(SellPublish sellPublish, User user) {

		// 多参数
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		// 时间如果为null，设置默认时间为 1970-01-01
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("catid", sellPublish.getGatid());
		map.put("amount", sellPublish.getQuantity());
		map.put("price", sellPublish.getPrice());
		map.put("warehouse", sellPublish.getWareId());
		map.put("username", sellPublish.getContact());
		map.put("validitydate",
				sellPublish.getTermdate() == null ? "1970-01-01" : DateUtil
						.doFormatDate(sellPublish.getTermdate(), "yyyy-MM-dd"));
		map.put("delivery", sellPublish.getDeliverytype());
		map.put("deliverytime",
				sellPublish.getDeliverydate() == null ? "1970-01-01" : DateUtil
						.doFormatDate(sellPublish.getDeliverydate(),
								"yyyy-MM-dd"));
		map.put("lastdeliverytime",
				sellPublish.getDateend() == null ? "1970-01-01" : DateUtil
						.doFormatDate(sellPublish.getDateend(), "yyyy-MM-dd"));
		map.put("delaydate",
				sellPublish.getDays() == null ? 0 : sellPublish.getDays());
		map.put("loss", sellPublish.getPounddiff());
		map.put("moq", sellPublish.getBeginordercount());
		map.put("outmoq", sellPublish.getStep());
		map.put("receipt", sellPublish.getSurrogate());
		map.put("type", 1);
		map.put("title", user.getItems().getName());

		String jsonMallMap = JSONUtil.doConvertObject2Json(map);
		param.add("jsonMall", jsonMallMap);

		logger.info("开始调用：发布报盘信息到商城接口");
		
		String jsonStr = restTemplate.postForObject(publishMallUrl, param,
				String.class);

		return jsonStr;

	}

	@Override
	public List<SellPool> getListSellForNoRepeat(SellPool sellPool, User user) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("companyid", sellPool.getCompanyid());
		paramMap.put("createdby", user.getId());
		return this.sellPoolDao.getListSellForNoRepeat(paramMap);
	}

	@Override
	public Warehouse queryWarehouseById(Integer wareId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("wareId", wareId);
		return this.sellPoolDao.queryWarehouseById(param);
	}

	@Override
	public void updateSellPool(SellPool sellPool, User user) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
		if(sellPool.getSortNum()!=null){
			if (sellPool.getSortNum() >= 0 && sellPool.getSortNum() <=9) {
				nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+sellPool.getSortNum();
			}else {
				nowTimeTemp = nowTimeTemp.replace("-", "")+""+sellPool.getSortNum();
			}
		}
		sellPool.setTimeSort(nowTimeTemp);
		
		sellPool.setUpdatedat(new Date());
		sellPool.setUpdatedby(user.getId());
		
		map.put("poolId", sellPool.getId());
		//新增还盘之前价格最合适的一条还盘信息
		List<PoolPrice> list = this.iBusinessDao.queryCurrentMaxPoolPrice(map);
		PoolPrice poolPrice = null ;
		if (list != null && list.size() > 0) {
			poolPrice = list.get(0);
			if (poolPrice.getPrice() >= sellPool.getPrice()) {
				sellPool.setIsConfirm(1);
			}
		}
		
		try {
			this.sellPoolDao.updateSellPool(sellPool);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据商品编号 价格 数量 查询挂盘id是否存在
	 * 
	 * @return
	 */
	@Override
	public Map selMallSaleIdBy(SellPool sellPool) {
		return sellPoolDao.selMallSaleIdBy(sellPool);
	}

	@Override
	public String getcompanyName(Integer id) {
		// TODO Auto-generated method stub
		return sellPoolDao.getcompanyName(id);
	}

	@Override
	public String getcompanyAccount(Integer id) {
		// TODO Auto-generated method stub
		return sellPoolDao.getcompanyAccount(id);
	}

	@Override
	public Integer countSellpool(SellPool sellPool) {
		// TODO Auto-generated method stub
		return sellPoolDao.countSellpool(sellPool);
	}

	@Override
	public SellPool getSellPoolByPoolId(Integer poolId) {
		SellPool sellPool = null;
		try {
			sellPool = sellPoolDao.getSellPoolByPoolId(poolId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sellPool;
	}

	@Override
	public void updateSellPoolPrice(SellPool sellPool) {
		try {
			sellPoolDao.updateSellPoolPrice(sellPool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fabuSellpool(SellPool sellPool) {
		// TODO Auto-generated method stub
		sellPoolDao.fabuSellpool(sellPool);

	}

	@Override
	public Integer checkUserPay(String account) {
		int b = 1;
		try {

			if (account != null) {
				long date = (new Date()).getTime();
				String md = account + date;
				String md5 = MD5Util.md5(md);
//				logger.info("开始 jsonstr:" + "mallUserName=" + account
//						+ "&date=" + date + "md5=" + md5 + "   checkUserPay"
//						+ checkUserPay);
				
				logger.info("开始调用: 检查会员支付账号是否存在接口   参数：" + "mallUserName=" + account
						+ "&date=" + date + "md5=" + md5 + "   checkUserPay"
						+ checkUserPay);
				
				String jsonString = restTemplate.getForObject(checkUserPay
						+ "?mallUserName=" + account + "&date=" + date
						+ "&md5=" + md5, String.class);
				Map<String, Object> maps = JSONUtil
						.doConvertJson2Map(jsonString);
				//logger.info("return jsonstr:" + jsonString);
				logger.info("检查会员支付账号是否存在返回数据:" + jsonString.toString());
				
				String status = maps.get("status").toString();
				if ("000000".equals(status)) {
					return 0;
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			logger.info("调用检查会员支付账号是否存在接口出错");
			e.printStackTrace();
			return 1;
		}

		return b;
	}

	@Override
	public boolean dealdemall(Integer mallId) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("mallid", mallId.toString());
		params.put("status", "1");
		JSONObject result = null;
		String result_str = "";
		try {
			logger.info("开始调用：撮合挂单修改提交商城接口   参数： "+params.toString());
			
			result_str = URLRequest.post(dealdemall, params);
			//logger.error("调用商城接口结果=====》" + result_str);
			result = JSONObject.fromObject(result_str);
			if (result != null) {
				logger.info("撮合挂单修改提交商城返回数据"+result.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("调用撮合挂单修改提交商城接口失败");
			e.printStackTrace();
		}
		if (result != null) {
			int errno = result.getInt("errno");
			String msg = result.getString("msg");
			switch (errno) {
			case 0:
				logger.error("撮合挂单修改提交商城成功" + msg);
				return true;
			default:
				logger.error("撮合挂单修改提交商城失败" + msg);
				return false;
			}
		} else {
			return false;
		}
	}

	/** 
	 * @Title: updateSellPoolCreateSource 
	 * @Description: TODO
	 * @param sellPool
	 * @return
	 * @author zhangnan/张楠
	 * @createTime 2015年12月21日下午3:22:02
	 */
	@Override
	public Integer updateSellPoolCreateSource(SellPool sellPool) {
		return this.sellPoolDao.updateSellPoolCreateSource(sellPool);
	}

	@Override
	public SellPool getSellByMallId(Integer mallId) {
		// TODO Auto-generated method stub
		return sellPoolDao.getSellByMallId(mallId);
	}

	
	/** 给每条卖盘新增一个“编号”字段，便于商城排序
	 * @Title: updateSellPoolSortNum 
	 * @Description: TODO
	 * @param sortNumArray
	 * @param poolIdArray
	 * @param request
	 * @return
	 * @throws RuntimeException
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2016年1月6日下午4:23:37
	 */
	@Override
	public Map<String,Object> updateSellPoolSortNum(String[] sortNumArray,String[] poolIdArray,HttpServletRequest request) throws RuntimeException{
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String flag="";
		User user = (User)request.getSession().getAttribute("userInfo");
		for (int i = 0; i < sortNumArray.length; i++) {
			//编号
			Integer sortNum = Integer.parseInt(sortNumArray[i]);
			//卖盘ID
			Integer poolId = Integer.parseInt(poolIdArray[i]);
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("sortNum", sortNum);
			param.put("sellPoolId", poolId);
			param.put("updatedBy",user.getId());
			
			SellPool sellPool = this.sellPoolDao.getSellPoolById(param);
			
			String nowTimeTemp = DateUtil.doFormatDate(sellPool.getUpdatedat(), "yyyy-MM-dd");
			//2016012001 
			if (sortNum >= 0 && sortNum <=9) {
				nowTimeTemp = nowTimeTemp.replace("-", "")+"0"+sortNum;
			}else {
				nowTimeTemp = nowTimeTemp.replace("-", "")+""+sortNum;
			}
			
			param.put("timeSort", nowTimeTemp);
			
			
			//商城挂盘编号，挂盘编号如果不存在，“编号”只保存到撮合数据库，存在，同步到商城
			String mallSaleId;
			if (sellPool.getMallSaleId() != null) {
				mallSaleId = sellPool.getMallSaleId().toString();
				Map<String, String> mapMall = new HashMap<String, String>();
				
				mapMall.put("mallid", mallSaleId);
				mapMall.put("order",nowTimeTemp);
				
				JSONObject result = null;
				String result_str = "";
				
				if (sellPool.getTimeSort() != null && sellPool.getTimeSort().equals(nowTimeTemp) || sortNum == 0) {
					this.sellPoolDao.updateSellPoolSortNum(param);
					flag="OK";
					resultMap.put("code", flag);
					resultMap.put("msg", "修改编号成功");
				}else {
					try {
						logger.info("开始调用：修改商城排序编号接口");
						result_str = URLRequest.post(updatePoolSortNum, mapMall);
						result = JSONObject.fromObject(result_str);
						if (result != null) {
							logger.info("撮合网页端修改商城排序编号返回数据"+result.toString());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("修改商城排序编号接口出现异常");
						logger.error(e);
						resultMap.put("code", flag);
						resultMap.put("msg", "修改 编号失败：修改商城排序编号接口出现异常");
						return resultMap;
					}
					if (result != null) {
						int errno = result.getInt("errno");
						if(errno == 0){
							this.sellPoolDao.updateSellPoolSortNum(param);
							flag="OK";
							resultMap.put("code", flag);
							resultMap.put("msg", "修改编号成功");
						}else if(errno == 1){
							flag="FALID";
							resultMap.put("code", flag);
							resultMap.put("msg", "修改编号失败：修改商城排序编号接口出现异常");
							return resultMap;
						}else {
							flag="FALID";
							resultMap.put("code", flag);
							resultMap.put("msg", "修改编号失败：修改商城排序编号接口出现异常");
							return resultMap;
						}
					}
				}
			}else {
				this.sellPoolDao.updateSellPoolSortNum(param);
				flag="OK";
				resultMap.put("code", flag);
				resultMap.put("msg", "修改编号成功");
			}
			
		}
		return resultMap;
	}

	/** 商城端修改卖盘编号
	 * @Title: updatePoolsortNum 
	 * @Description: TODO
	 * @param paraMap
	 * @author zhangnan/张楠
	 * @createTime 2016年1月8日上午10:43:50
	 */
	@Override
	public void updatePoolsortNum(Map<String, Object> paraMap) {
		//编号
		Integer sortNum = Integer.valueOf(paraMap.get("sortNum").toString());
		
		String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
		nowTimeTemp = nowTimeTemp.replace("-", "")+""+sortNum;
		paraMap.put("timeSort", nowTimeTemp);
		
		//根据商城段提供的挂盘编号修改报盘
		this.sellPoolDao.updateSellPoolByMallSaleId(paraMap);
		
	}

	@Override
	public ChWareHouse initWareHouse(Integer wareId) {
		ChWareHouse chWareHouse = this.chWareHouseDao.selectByPrimaryKey(wareId);
		return chWareHouse;
	}

	@Override
	public List<ChCommodityAttrEntity> queryCommodityAttrList(
			Map<String, Object> params) {
		List<ChCommodityAttrEntity> commodityAttrList = this.commodityAttrDao.queryCommodityAttrList(params);
		return commodityAttrList;
	}

	/** 修改报盘同步到商城
	 * @Title: updateSellPoolMall 
	 * @Description: TODO
	 * @param sellPool
	 * @return
	 * @author zhangnan/张楠
	 * @createTime 2016年1月26日上午11:15:52
	 */
	@Override
	public Map<String,Object> updateSellPoolMall(SellPool sellPool) {
		Map<String, Object> map=new HashMap<>();
		
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		
		param.add("totalmallid", sellPool.getCommodityId());//商品ID
		param.add("warehouseid", sellPool.getWareId());//仓库
		param.add("price", sellPool.getPrice());//价格
		param.add("areaid", sellPool.getWareProvince());// 地区id
		param.add("count", sellPool.getQuantity());// 库存
		param.add("paytype", sellPool.getPaytype());// 结算方式 1现款现汇 2银行承兑 3有色网代收代付
		param.add("receipttype", sellPool.getReceipttype());// 付款方式 1款到发货 2货到付款
		param.add("delivery", sellPool.getDelivery());// 交货方式 运输方式  1 卖方送货 2 买方自提
		param.add("deliverytime",DateUtil.doSFormatDate(sellPool.getDeliverytime(), "yyyy-MM-dd").getTime() / 1000);// 交货日期(时间戳)
		param.add("moq", sellPool.getMoq());//起订量
		param.add("outmoq", sellPool.getOutmoq());//超出部分按n递增
		param.add("overflow", sellPool.getOverflow());// 磅差
		
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
		param.add("order", nowTimeTemp);//商城排序编号
		param.add("mallid", sellPool.getMallSaleId());//商城ID
		
		if (sellPool != null && sellPool.getUnit() != null
				&& Integer.valueOf(sellPool.getUnit()) == 0) {
			param.add("unit", StringUtil.doEncoder("吨"));
		} else {
			param.add("unit", StringUtil.doEncoder("千克"));
		}
		
		try {
			logger.info("开始调用：修改报盘同步商城接口   参数："+param.toString());
			
			printMessage("接口地址：" + updateSellPoolMall + "， 参数：" + param.toString());
			String jsonString = restTemplate.postForObject(updateSellPoolMall, param,
					String.class);
			printMessage("返回结果：" + jsonString);
			Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
			if (maps != null) {
				logger.info("修改报盘同步商城返回数据"+maps.toString());
				Boolean errno=(Boolean) maps.get("success");
				
				if(errno){
					map.put("code", "ok");
					map.put("msg", "修改成功");
				}else {
					map.put("code", "error");
					map.put("msg", "修改失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "error");
			map.put("msg", "同步商城接口调用异常");
			printMessage("同步商城接口调用异常");
			return map;
		}
		return map;
	}

	@Override
	public ChCommodityEntity queryChCommodityEntityById(Integer commodityId) {
		ChCommodityEntity commodity = this.commodityDao.selectByPrimaryKey(commodityId);
		return commodity;
	}

	@Override
	public void updateSellPool_moca(SellPool sellPool, User user)throws RuntimeException {
		sellPool.setUpdatedat(new Date());
		sellPool.setUpdatedby(user.getId());
		this.sellPoolDao.updateSellPool_moca(sellPool);
		
	}
}
