package com.smm.cuohe.bo.impl;

import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IPurchaseBO;
import com.smm.cuohe.dao.IPurchaseDao;
import com.smm.cuohe.dao.users.CustomsPOJODAO;
import com.smm.cuohe.domain.*;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.IntegerUtil;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PurchaseBO implements IPurchaseBO {

	@Resource
	private IPurchaseDao iPurchaseDao;
	@Resource
	private CustomsPOJODAO pDao;
	@Resource
	private CategoryBO categoryBo;
	@Resource
	private ICustomerBO customerBO;

	@Override
	public List<Purchase> getAll(Map<String, String> map) {
		String condition = map.get("condition");
		String column = map.get("column");
		String context = map.get("context");
		String s = "";
		if (column != null && !column.equals("0")) {
			if (column.equals("1")) {
				s += "  cu.companyName ";
			} else if (column.equals("2")) {
				s += " re.updatedAt ";
			} else if (column.equals("3")) {
				s += " cu.entTypes ";
			} else if (column.equals("4")) {
				s += "  a.name ";
			} else if (column.equals("5")) {
				s += " con.name ";
			} else if (column.equals("6")) {
				s += " ar.name ";
			} else if (column.equals("7")) {
				s += " re.updatedAt ";
			} else if (column.equals("8")) {
				s += " c.name  ";
			} else if (column.equals("9")) {
				s += " cu.buybrand ";
			} else if (column.equals("10")) {
				s += " u.name ";
			} else if (column.equals("11")) {
				s += " cu.createdAt ";
			} else if (column.equals("12")) {
				s += " cu.tag ";
			}

			if (condition.equals("1")) {
				s += " like '%" + context + "%'";
			} else if (condition.equals("2")) {
				s += " not like '%" + context + "%'";
			} else if (condition.equals("3")) {
				s += " = '" + context + "'";
			} else if (condition.equals("4")) {
				s += " != '" + context + "'";
			} else if (condition.equals("5")) {
				s += " like '" + context + "%'";
			} else if (condition.equals("6")) {
				s += " like '%" + context + "'";
			} else if (condition.equals("7")) {
				s += "  is null ";
			} else if (condition.equals("8")) {
				s += " IS NOT NULL ";
			}
			map.put("str", s);
		}
		List<Purchase> list = this.iPurchaseDao.getAll(map);
		for (Purchase c : list) {
			if (StringUtils.isNotBlank(c.getEntTypes())) {
				List<Map<String, Object>> listMap = pDao.getCategList(c
						.getEntTypes());
				String enType = "";
				for (int i = 0; i < listMap.size(); i++) {
					enType = enType + listMap.get(i).get("name") + ",";
				}

				c.setEntTypes(enType.substring(0, enType.length() - 1));
			} else {
				c.setEntTypes("");
			}
		}
		return list;
	}

	@Override
	public Integer getAllCount(Map<String, String> map) {
		String condition = map.get("condition");
		String column = map.get("column");
		String context = map.get("context");
		String s = "";
		if (column != null && !column.equals("0")) {
			if (column.equals("1")) {
				s += "  cu.companyName ";
			} else if (column.equals("2")) {
				s += " re.updatedAt ";
			} else if (column.equals("3")) {
				s += " cu.entTypes ";
			} else if (column.equals("4")) {
				s += "  a.name ";
			} else if (column.equals("5")) {
				s += " con.name ";
			} else if (column.equals("6")) {
				s += " ar.name ";
			} else if (column.equals("7")) {
				s += " re.updatedAt ";
			} else if (column.equals("8")) {
				s += " c.name  ";
			} else if (column.equals("9")) {
				s += " cu.buybrand ";
			} else if (column.equals("10")) {
				s += " u.name ";
			} else if (column.equals("11")) {
				s += " cu.createdAt ";
			} else if (column.equals("12")) {
				s += " cu.tag ";
			}

			if (condition.equals("1")) {
				s += " like '%" + context + "%'";
			} else if (condition.equals("2")) {
				s += " not like '%" + context + "%'";
			} else if (condition.equals("3")) {
				s += " = '" + context + "'";
			} else if (condition.equals("4")) {
				s += " != '" + context + "'";
			} else if (condition.equals("5")) {
				s += " like '" + context + "%'";
			} else if (condition.equals("6")) {
				s += " like '%" + context + "'";
			} else if (condition.equals("7")) {
				s += "  is null ";
			} else if (condition.equals("8")) {
				s += " IS NOT NULL ";
			}
			map.put("str", s);
		}
		int count = -1;
		try {
			count = this.iPurchaseDao.getAllCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteByIds(String ids) {
		return this.iPurchaseDao.deleteByIds(ids);
	}

	@Override
	public List<String> selPhoneBycusId(String ids) {
		List<String> list = this.iPurchaseDao.selPhoneBycusId(ids);
		return list;
	}

	@Override
	public String importExcel(List<Map<String, String>> dataInfo, User loginUser) {
		String errorInfo = "";
		// 获得导入excel的信息，校验信息准确性
		String excelRowNum = "";
		java.util.Date dtNow = new java.util.Date();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, List<Category>> mapCategory = categoryBo
				.getCategoryByModuleName();
		for (Map<String, String> item : dataInfo) {
			try {
				String errorTemp = "";
				DecimalFormat df = new DecimalFormat("######0");

				if ((!item.containsKey("excelNum"))
						|| StringUtils.isBlank(item.get("excelNum"))) {
					excelRowNum = "未知";
					errorTemp += "序号不为空；";
				} else {
					excelRowNum = df
							.format(Double.valueOf(item.get("excelNum")));// 获得excel导入的行号
				}

				// 品目名称
				String itemNameStr = item.containsKey("itemName") ? item.get(
						"itemName").trim() : "";
				Integer itemID = 0;
				if (StringUtils.isBlank(itemNameStr)) {
					errorTemp += "品目信息不为空；";
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

				// 校验品目信息和导入信息是否相同
				if (loginUser.getItems().getId() != itemID) {
					errorTemp += "导入品目信息和当前操作品目不符；";
				}

				// 公司名称
				String companyNameStr = item.containsKey("companyName") ? item
						.get("companyName").trim() : "";
				if (StringUtils.isBlank(companyNameStr)) {
					errorTemp += "公司名称不为空；";
				} else {
					List<Customer> listCustoms = this.iPurchaseDao
							.getCustomsInfoByCondition("a.itemID=" + itemID
									+ " and a.companyName='" + companyNameStr
									+ "' and a.status=0");
					if (listCustoms != null && listCustoms.size() > 0) {
						errorTemp += "客户信息已存在；";
					}
				}

				// 企业地址
				String companyAddressStr = item.containsKey("companyAddress") ? item
						.get("companyAddress").trim() : "";
				if (StringUtils.isBlank(companyAddressStr)) {
					errorTemp += "企业地址不为空；";
				}

				// 企业类型
				String companyEntTypesStr = item.containsKey("companyEntTypes") ? item
						.get("companyEntTypes").trim() : "";
				String entTypesRes = "";
				if (StringUtils.isBlank(companyEntTypesStr)) {
					errorTemp += "企业类型不为空；";
				} /*
				 * else if (!companyEntTypesStr.contains("|")) { errorTemp +=
				 * "企业类型文本格式不正确；"; }
				 */else {
					List<Category> listEntTypes = new ArrayList<Category>();
					if (mapCategory.containsKey("企业类型"))
						listEntTypes = mapCategory.get("企业类型");
					String[] entTypesColl = companyEntTypesStr.split("[|]");

					if (listEntTypes != null && listEntTypes.size() > 0) {
						for (Integer i = 0; i < entTypesColl.length; i++) {
							for (Category entTypesItem : listEntTypes) {
								if (entTypesItem.getName().equals(
										entTypesColl[i])) {
									entTypesRes += entTypesItem.getId() + ",";
									break;
								}
							}
						}
					}
					if (StringUtils.isNotBlank(entTypesRes)) {
						entTypesRes = entTypesRes.substring(0,
								entTypesRes.length() - 1);
					} else {
						errorTemp += "企业类型信息错误；";
					}
				}

				// 行业信息
				String salesProductsStr = item.containsKey("salesProducts") ? item
						.get("salesProducts").trim() : "";
				if (StringUtils.isBlank(salesProductsStr)) {
					errorTemp += "行业信息不为空；";
				}

				// 年销售额
				String companySalesVolumeStr = item
						.containsKey("companySalesVolume") ? item.get(
						"companySalesVolume").trim() : "";
				String salesvolumeRes = "";
				if (StringUtils.isBlank(companySalesVolumeStr)) {
					errorTemp += "年销售额不为空；";
				} else {
					List<Category> listSalesVolume = new ArrayList<Category>();
					if (mapCategory.containsKey("年销售额"))
						listSalesVolume = mapCategory.get("年销售额");
					for (Category salesvolumeItem : listSalesVolume) {
						if (salesvolumeItem.getName().equals(
								companySalesVolumeStr)) {
							salesvolumeRes = salesvolumeItem.getId().toString();
							break;
						}
					}
					if (!StringUtils.isNotBlank(salesvolumeRes)) {
						errorTemp += "年销售额信息错误；";
					}
				}

				// 采购量
				String companyBuyVolumeStr = item
						.containsKey("companyBuyVolume") ? item.get(
						"companyBuyVolume").trim() : "";
				Integer companyBuyVolumeInt = 0;
				if (StringUtils.isBlank(companyBuyVolumeStr)) {
					errorTemp += "采购量不为空；";
				} else {
					companyBuyVolumeInt = IntegerUtil
							.StringToInt(companyBuyVolumeStr);
					if (companyBuyVolumeInt == 0) {
						errorTemp += "采购量信息错误；";
					}
				}

				// 上下家(类别)
				String CategoryBusinessStr = item
						.containsKey("CategoryBusiness") ? item.get(
						"CategoryBusiness").trim() : "";
				Integer categoryBusinessInt = 0;
				if (StringUtils.isBlank(CategoryBusinessStr)) {
					errorTemp += "上下家(类别)不为空；";
				} else {
					String[] categoryBusinessColl = CategoryBusinessStr
							.split("[|]");
					for (Integer i = 0; i < categoryBusinessColl.length; i++) {
						if (categoryBusinessColl[i].equals("采购商")) {
							categoryBusinessInt += 1;
						}
						if (categoryBusinessColl[i].equals("供应商")) {
							categoryBusinessInt += 2;
						}
					}
					if (categoryBusinessInt == 0)
						errorTemp += "上下家信息错误；";
				}

				// 企业联系人
				String contacterNameStr = item.containsKey("contacterName") ? item
						.get("contacterName").trim() : "";
				if (StringUtils.isBlank(contacterNameStr)) {
					errorTemp += "企业联系人不为空；";
				}

				// 联系人电话
				String contacterMobilePhoneStr = item
						.containsKey("contacterMobilePhone") ? item
						.get("contacterMobilePhone") : "";
				if (StringUtils.isBlank(contacterMobilePhoneStr)) {
					errorTemp += "联系人电话不为空；";
				} else {
					Pattern p = Pattern
							.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
					Matcher m = p.matcher(contacterMobilePhoneStr);
					if (!m.find()) {
						errorTemp += "联系人电话格式错误；";
					}
				}

				// 企业联系人性别
				String contacterSexStr = item.containsKey("contacterSex") ? item
						.get("contacterSex").trim() : "";
				Integer contacterSexInt = -1;
				if (StringUtils.isBlank(contacterSexStr)) {
					errorTemp += "企业联系人性别不为空；";
				} else {
					if (contacterSexStr.length() == 1) {
						if (contacterSexStr.equals("男")) {
							contacterSexInt = 0;
						} else if (contacterSexStr.equals("女")) {
							contacterSexInt = 1;
						}
					}
					if (contacterSexInt == -1) {
						errorTemp += "联系人性别错误；";
					}
				}

				// 联系人职位
				String contacterPositionStr = item
						.containsKey("contacterPosition") ? item.get(
						"contacterPosition").trim() : "";
				if (StringUtils.isBlank(contacterPositionStr)) {
					errorTemp += "联系人职位不为空；";
				}

				// 企业地区名称
				String areaNameStr = item.containsKey("areaName") ? item.get(
						"areaName").trim() : "";
				Integer areaID = 0;
				if (StringUtils.isBlank(areaNameStr)) {
					errorTemp += "企业地区名称不为空；";
				}
				/*
				 * else if (!areaNameStr.contains("|")) { errorTemp +=
				 * "企业地区名称文本信息错误；"; }
				 */
				else {
					List<Areas> listAreasP = this.iPurchaseDao// 先获得省级
							.getAreasInfoByCondition("name='"
									+ areaNameStr.split("[|]")[0]
									+ "' and parentID=0");
					if (listAreasP != null && listAreasP.size() > 0) {
						Integer areaPID = listAreasP.get(0).getId();
						/*
						 * List<Areas> listAreasC = this.iPurchaseDao// 再获得市级
						 * .getAreasInfoByCondition("name='" +
						 * areaNameStr.split("[|]")[1] + "' and parentID=" +
						 * areaPID.toString()); if (listAreasC != null &&
						 * listAreasC.size() > 0) { areaID =
						 * listAreasC.get(0).getId(); } else { errorTemp +=
						 * "地区信息错误；"; }
						 */
						areaID = areaPID;// 去掉了城市信息
					} else {
						errorTemp += "地区信息错误；";
					}
				}

				// 企业员工人数
				String categoryEmployeeStr = item
						.containsKey("categoryEmployee") ? item.get(
						"categoryEmployee").trim() : "";
				Integer categoryEmployeeInt = 0;
				if (StringUtils.isBlank(categoryEmployeeStr)) {
					errorTemp += "企业员工人数不为空；";
				} else {
					List<Category> listCategoryEmployee = new ArrayList<Category>();
					if (mapCategory.containsKey("员工人数"))
						listCategoryEmployee = mapCategory.get("员工人数");
					for (Category categoryEmployeeItem : listCategoryEmployee) {
						if (categoryEmployeeItem.getName().equals(
								categoryEmployeeStr)) {
							categoryEmployeeInt = categoryEmployeeItem.getId();
							break;
						}
					}
					if (categoryEmployeeInt == 0) {
						errorTemp += "员工人数信息错误；";
					}
				}

				/*
				 * // 负责人姓名 String userNameStr = item.containsKey("userName") ?
				 * item.get( "userName").trim() : ""; if
				 * (StringUtils.isBlank(userNameStr)) { errorTemp +=
				 * "负责人姓名不为空；"; }
				 * 
				 * // 负责人Email String userEmailStr =
				 * item.containsKey("userEmail") ? item.get( "userEmail").trim()
				 * : ""; if (StringUtils.isBlank(userEmailStr)) { errorTemp +=
				 * "负责人Email不为空；"; }
				 * 
				 * Integer userID = 0; // 负责人姓名和负责人Email都不为空时 if
				 * ((!StringUtils.isBlank(userEmailStr)) &&
				 * (!StringUtils.isBlank(userNameStr))) { List<User> listUser =
				 * this.iPurchaseDao .getUserInfoByCondition("name='" +
				 * userNameStr + "' and email='" + userEmailStr +
				 * "' and status=0"); if (listUser != null && listUser.size() >
				 * 0) { userID = listUser.get(0).getId(); } else { errorTemp +=
				 * "负责人信息错误；"; } }
				 */

				// 采购品牌
				String companyBuyBrandStr = item.containsKey("companyBuyBrand") ? item
						.get("companyBuyBrand").trim() : "";
				if (StringUtils.isBlank(companyBuyBrandStr)) {
					errorTemp += "采购品牌不为空；";
				}

				// 采购周期
				String companyCategoryFreqStr = item
						.containsKey("companyCategoryFreq") ? item.get(
						"companyCategoryFreq").trim() : "";
				Integer categoryfreqInt = 0;
				if (StringUtils.isBlank(companyCategoryFreqStr)) {
					errorTemp += "采购周期不为空；";
				} else {
					List<Category> listCategoryfreq = new ArrayList<Category>();
					if (mapCategory.containsKey("采购周期"))
						listCategoryfreq = mapCategory.get("采购周期");
					for (Category categoryfreqItem : listCategoryfreq) {
						if (categoryfreqItem.getName().equals(
								companyCategoryFreqStr)) {
							categoryfreqInt = categoryfreqItem.getId();
							break;
						}
					}
					if (categoryfreqInt == 0) {
						errorTemp += "采购周期信息错误；";
					}
				}

				// errorTemp="test";
				if (errorTemp != "") {// 返回无法执行错误
					errorInfo += "第" + excelRowNum + "行数据校验错误：" + errorTemp
							+ "\r\n";
					continue;
				}

				// 1.整理客户信息
				companyBuyBrandStr = companyBuyBrandStr.replace('|', ',');
				salesProductsStr = salesProductsStr.replace('|', ',');

				Customer customInfo = new Customer();
				customInfo.setId(0);
				customInfo.setItemId(itemID);
				customInfo.setPic(loginUser.getId());
				customInfo.setAreaId(areaID);
				customInfo.setCompanyName(companyNameStr);
				customInfo.setAddress(companyAddressStr);
				customInfo.setCategoryBusiness(categoryBusinessInt);
				customInfo.setEntTypes(entTypesRes);
				customInfo.setBuyVolume(companyBuyVolumeInt);
				customInfo.setSalesProducts(salesProductsStr);
				customInfo.setSalesVolume(salesvolumeRes);
				customInfo.setCategoryFreq(categoryfreqInt);
				customInfo.setBuyBrand(companyBuyBrandStr);
				customInfo.setCategoryEmployee(categoryEmployeeInt);
				customInfo.setStatus(0);
				customInfo.setCreatedAt(dtNow);
				customInfo.setCreatedBy(loginUser.getId());
				customInfo.setUpdatedAt(dtNow);
				customInfo.setUpdatedBy(loginUser.getId());

				// 2.整理联系人信息
				Contacter contacterInfo = new Contacter();
				contacterInfo.setId(0);
				contacterInfo.setCustomerId(0);
				contacterInfo.setName(contacterNameStr);
				contacterInfo.setMobilePhone(contacterMobilePhoneStr);
				contacterInfo.setSex(contacterSexInt);
				contacterInfo.setPosition(contacterPositionStr);
				contacterInfo.setKeyPerson(1);
				contacterInfo.setStatus(0);
				contacterInfo.setCreatedAt(dtNow);
				contacterInfo.setCreatedBy(loginUser.getId());
				contacterInfo.setUpdatedAt(dtNow);
				contacterInfo.setUpdatedBy(loginUser.getId());

				// 需要更新2张表的数据
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("contacterMap", contacterInfo);
				dataMap.put("customMap", customInfo);
				dataMap.put("excelNOMap", excelRowNum);

				dataList.add(dataMap);

			} catch (Exception e) {
				errorInfo += "第" + excelRowNum + "行数据解析出现异常。" + e.getMessage()
						+ "\r\n";
			}
		}

		if (errorInfo == "") {
			// 若导入的excel中没有错误信息，执行导入
			String res = SaveCustomData(dataList);
			if (StringUtils.isBlank(res)) {
				errorInfo = "数据导入成功！";
			} else {
				errorInfo = res;
			}
		}

		return errorInfo;
	}

	@Transactional
	private String SaveCustomData(List<Map<String, Object>> dataList) {
		String res = "";
		String excelNO = "0";
		try {
			for (Map<String, Object> item : dataList) {
				Contacter contacterInfo = (Contacter) item.get("contacterMap");
				Customer customsInfo = (Customer) item.get("customMap");
				excelNO = item.get("excelNOMap").toString();

				List<Contacter> listContacters = new ArrayList<Contacter>();
				listContacters.add(contacterInfo);
				customsInfo.setContacters(listContacters);
				customsInfo.setCreateMallAccount(-1);// 初始化新增客户状态，excel导入无用，只需给定初始值

				try {
					/*// 调用新增用户前，先检查联系人电话在商城中状态
					// 1.不存在直接调用
					// 2.存在一条 更新customer中的account和商城做关联
					// 3.其他 返回错误信息，提示用户在商城中已存在
					JSONObject result = customerBO.checkCustomer(contacterInfo
							.getMobilePhone());
					Integer errno = (result != null && result
							.containsKey("errno")) ? result.getInt("errno")
							: -1;
					if (errno == -1) {
						res += ("第" + excelNO + "行数据错误:调用商城接口失败。\r\n");
					} else if (errno == 2) {
						res += ("第" + excelNO + "行数据错误:输入手机号不合法。\r\n");
					} else if (errno == 0 || errno == 1) {// 手机号码合法
						Integer count = (result != null && result
								.containsKey("count")) ? result.getInt("count")
								: -1;
						if (errno == 1 || count < 2) {// 手机号码不存在，或存在且数量小于两条
							if (count == 1) {// 商城中存在该手机号,并且只有一个
								customsInfo.setAccount(contacterInfo
										.getMobilePhone());
							}
							Integer customerCode = 0;
							customerCode = customerBO.saveCustomer(customsInfo);
							if (customerCode == 0) {
								res += ("第" + excelNO + "行数据同步商城失败。\r\n");
							}
						} else {
							res += ("第" + excelNO + "行数据错误:该用户在商城中已存在。\r\n");
						}

					}*/
					//20160127 新增客户修改创建规则 统一调用customerBo中save方法
					Integer customerCode = 0;
					customerCode = customerBO.saveCustomer(customsInfo);
					if (customerCode == 0) {
						res += ("第" + excelNO + "行数据同步商城失败。\r\n");
					}
				} catch (Exception ex) {
					res += ("第" + excelNO + "行数据错误:" + ex.getMessage() + "\r\n");
				}
			}
		} catch (Exception ex) {
			res += "第" + excelNO + "行数据保存错误" + ex.getMessage() + "。\r\n";
		}

		return res;
	}

	@Override
	public List<Purchase> getExpotDataByID(String[] array) {

		List<Purchase> list = this.iPurchaseDao.getExpotDataByID(array);
		for (Purchase c : list) {
			if (StringUtils.isNotBlank(c.getEntTypes())) {
				List<Map<String, Object>> listMap = pDao.getCategList(c
						.getEntTypes());
				String enType = "";
				for (int i = 0; i < listMap.size(); i++) {
					enType = enType + listMap.get(i).get("name") + ",";
				}

				c.setEntTypes(enType.substring(0, enType.length() - 1));
			} else {
				c.setEntTypes("");
			}
		}
		return list;
	}

	@Override
	public List<Items> getItemsInfoByCondition(String condition) {
		return iPurchaseDao.getItemsInfoByCondition(condition);
	}

}
