package com.smm.cuohe.bo.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import com.smm.cuohe.bo.IBusinessBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.bo.notice.NoticeBo;
import com.smm.cuohe.dao.CustomsBlackListMapper;
import com.smm.cuohe.dao.IBusinessDAO;
import com.smm.cuohe.dao.ICustomerDao;
import com.smm.cuohe.dao.IOrdersDAO;
import com.smm.cuohe.dao.base.ChBuyPoolEntityMapper;
import com.smm.cuohe.dao.base.ChCustomsEntityMapper;
import com.smm.cuohe.dao.base.ChPoolPriceEntityMapper;
import com.smm.cuohe.dao.base.ChUsersEntityMapper;
import com.smm.cuohe.dao.base.ChWareHouseMapper;
import com.smm.cuohe.dao.dealmake.BuypoolDao;
import com.smm.cuohe.dao.dealmake.ISellPoolDao;
import com.smm.cuohe.dao.notice.NoticeDao;
import com.smm.cuohe.domain.BusinessOffer;
import com.smm.cuohe.domain.ChBuyPool;
import com.smm.cuohe.domain.ChSellPool;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.CustomsBlackList;
import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.SerachParam;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChBuyPoolEntity;
import com.smm.cuohe.domain.base.ChCommodityEntity;
import com.smm.cuohe.domain.base.ChCustomsEntity;
import com.smm.cuohe.domain.base.ChPoolPriceEntity;
import com.smm.cuohe.domain.base.ChUsersEntity;
import com.smm.cuohe.domain.base.ChWareHouse;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.enumDef.BusinessQueryEnum;
import com.smm.cuohe.util.DateUtil;

/**
 * @author  zhaoyutao
 * @version 2015年11月16日 下午8:26:54
 * 类说明
 */

@Service
public class BusinessBO implements IBusinessBO{

	@Resource
	IBusinessDAO iBusinessDao;
	
	@Resource
	IOrdersDAO iOrdersDao;
	
	@Resource
	private ISellPoolDao sellPoolDao;
	
	@Resource
	private BuypoolDao buypoolDao;
	
	@Autowired
	private ISellPoolBO sellBo;
	
	@Resource
	private CustomsBlackListMapper CustomsBlackListDao;
	
	@Resource
	private ICustomerDao iCustomerDao;
	
	@Autowired
	private NoticeDao noticeDao2;
	
	@Autowired
	private NoticeBo noticeBo;
	
	@Autowired
	private ChUsersEntityMapper usersMap;
	
	@Autowired
	private ChCustomsEntityMapper chCustomsEntityMapper;
	
	@Autowired
	private ChBuyPoolEntityMapper buyPoolDao;
	
	@Autowired
	private ChWareHouseMapper wareHouseDao;
	
	@Autowired
	private ChCustomsEntityMapper customsDao;
	
	@Autowired
	private ChPoolPriceEntityMapper poolPriceDao;
	
	@Override
	public Map<String, Object> getPoolBusiness(int itemId, SerachParam sp) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<BusinessOffer> list;
		
		if(sp.getAttribute().equals("p6") && "采购".indexOf(sp.getContent())>-1){
			sp.setAttribute("");
			list = iBusinessDao.getBuyPoolBusiness(itemId, sp);
			
		} else if(sp.getAttribute().equals("p6") && "销售".indexOf(sp.getContent())>-1){
			sp.setAttribute("");
			list = iBusinessDao.getSellPoolBusiness(itemId, sp);
			
		}else {
			if(sp.getAttribute() != null && !sp.getAttribute().equals("")){
				String param = sp.getContent();
				if (param != null && !param.equals("")) {
					try {
						param = URLDecoder.decode( param ,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sp.setAttribute(BusinessQueryEnum.valueOf(sp.getAttribute()).getValue()); //转换 attribute
				sp.setContent(param);
			}
			list = iBusinessDao.getSellPoolBusiness(itemId, sp);
			
			list.addAll(iBusinessDao.getBuyPoolBusiness(itemId, sp));
		}
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String lastPriceText = list.get(i).getLastPriceText();
				if (lastPriceText.equals("实价 0.000")) {
					list.get(i).setLastPriceText("");
				}
			}
		}
		
		List<Map<String, Object>> attrView = iOrdersDao.selectItemAttr(itemId);
		
		map.put("rows", list);
		
		map.put("attrView", attrView);
		
		map.put("total", list.size());
		
		//表头搜索条件当然要放进去
		map.put("type", sp.getType());
		
		return map;
	}
	
	public Map<String, Object> closeOffer(int poolId, int poolType, int counterNum){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int updateCount = -1;
		if(counterNum > 0){
			updateCount = iBusinessDao.closeOffer(poolId, poolType);
		} else {
			updateCount = iBusinessDao.closeOfferNoCounter(poolId, poolType);
		}
		sellBo.delpool(String.valueOf(poolId));
		if(updateCount > 0){
			noticeBo.transactionClose(poolId,poolType);//插入日志信息
			map.put("status", "ok");
			map.put("msg", "关闭成功");
		} else {
			map.put("status", "falid");
			map.put("msg", "关闭失败");
		}
		
		return map;
	}
	public Map<String, Object> restoreOffer(int poolId, int poolType, int counterNum){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int updateCount = -1;
		if(counterNum > 0){
			updateCount = iBusinessDao.restoreOffer(poolId, poolType);
			int count = iBusinessDao.restoreOfferPoolPrice(poolId);
			if(updateCount < 0){
				updateCount = count;
			}
		} else {
			updateCount = iBusinessDao.restoreOfferNoCounter(poolId, poolType);
		}
		
		if(updateCount > 0){
			map.put("status", "ok");
			map.put("msg", "还原成功");
		} else {
			map.put("status", "falid");
			map.put("msg", "还原失败");
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> addCounter(PoolPrice poolPrice, int itemId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> rltMap = new HashMap<String, Object>();
		rltMap = iBusinessDao.addCounter(poolPrice, itemId);
		
		int rltStatus = (int) rltMap.get("rltStatus");
		String rltMsg = (String) rltMap.get("rltMsg");
		
		map.put("status", rltStatus > 0 ? "ok" :"faild");
		
		map.put("msg", rltMsg);
		
		return map;
	}
	
	@Override
	public Map<String, Object> addItemPrice(ItemPrice itemPrice) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
//		itemPrice.setCreatedAt(new Date());
		
		int count = iBusinessDao.addItemPrice(itemPrice);
		
		if(count > 0){
			map.put("status", "ok");
			
			map.put("msg", "保存成功");
			
		} else {
			map.put("status", "faild");
			
			map.put("msg", "保存失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> getExportOffer(int itemId, SerachParam sp) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最大的数据两的LinkedHashMap 由它作为表头处理
		List<String> listHeader = new ArrayList<String>();
		// 定义数据List
		List<LinkedHashMap<String, String>> listData = new ArrayList<LinkedHashMap<String, String>>();
		List<BusinessOffer> list=null;
		try {
			if(sp.getAttribute().equals("p6") && sp.getContent().equals("采购")){
				sp.setAttribute("");
				list = iBusinessDao.getBuyPoolBusiness(itemId, sp);
			} else if(sp.getAttribute().equals("p6") && sp.getContent().equals("销售")){
				sp.setAttribute("");
				list = iBusinessDao.getSellPoolBusiness(itemId, sp);
			} else {
				if(sp.getAttribute() != null && !sp.getAttribute().equals("")){
					sp.setAttribute(BusinessQueryEnum.valueOf(sp.getAttribute()).getValue());
				}
				list = iBusinessDao.getSellPoolBusiness(itemId, sp);
				list.addAll(iBusinessDao.getBuyPoolBusiness(itemId, sp));
			}
			List<Map<String, Object>> attrView = iOrdersDao.selectItemAttr(itemId);
			
			for (BusinessOffer busi:list) {
				// 生成导出使用的数据源
				LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
				tempMap.put("采购类型", busi.getPoolTypeText());
				tempMap.put("企业名称",busi.getCompanyName() );
				if(attrView!=null && attrView.size() > 0){
					for(Map<String, Object> maper:attrView){
						if(maper.get("mainProperty") !=null){
							if(maper.get("mainProperty").toString().equals("1")){
								List<CommodityAttr> listAttr = busi.getAttr();
								if (listAttr != null && listAttr.size() > 0) {
									for (CommodityAttr attr:listAttr) {
										if(maper.get("name").toString().equals(attr.getAttrName())){
											tempMap.put(maper.get("name").toString(),attr.getAttrValue());
										}
									}
								}
							}
						}
					}
				}
				tempMap.put("价格",busi.getPriceText());
				tempMap.put("仓库", busi.getWareName());
				tempMap.put("发布时间", DateUtil.doFormatDate(busi.getPublishTime(),""));
				tempMap.put("还盘数", busi.getCounterNum().toString());
				tempMap.put("最新还盘价",busi.getLastPriceText());
				tempMap.put("还盘企业",busi.getLastCustomerName());
				Date data=busi.getLastTime();//还盘时间
				if(data==null){
					tempMap.put("最新还盘时间","");
				}else{
					tempMap.put("最新还盘时间",DateUtil.doFormatDate(data,""));
				}
				listData.add(tempMap);
			}
			listHeader.add("采购类型");
			listHeader.add("企业名称");
			if(attrView!=null && attrView.size() > 0){
				for(Map<String, Object> maper:attrView){
					if(maper.get("mainProperty") !=null){
						if(maper.get("mainProperty").toString().equals("1")){
							listHeader.add(maper.get("name").toString());
						}
					}
				}
			}
			listHeader.add("价格");
			listHeader.add("仓库");
			listHeader.add("发布时间");
			listHeader.add("还盘数");
			listHeader.add("最新还盘价");
			listHeader.add("还盘企业");
			listHeader.add("最新还盘时间");
			map.put("listHeader", listHeader);
			map.put("listData", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/** 黑名单 双向
	 * @Title: checkBlacklist 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 */
	@Override
	public String checkBlacklist(Map<String, Object> map,Integer poolType) {
		
		String flag = "";
		//卖盘
		if (poolType == 1) {
			Integer companyid = this.sellPoolDao.getSellPool(map);
			
			Map<String,Object> param1 = new HashMap<String, Object>();
			param1.put("customerID", companyid);
			param1.put("blackCustomerID", map.get("blackCustomerID"));
			List<CustomsBlackList> list1 = this.CustomsBlackListDao.checkBlacklist(param1);
			
			Map<String,Object> param2 = new HashMap<String, Object>();
			param2.put("customerID",map.get("blackCustomerID"));
			param2.put("blackCustomerID", companyid);
			List<CustomsBlackList> list2 = this.CustomsBlackListDao.checkBlacklist(param2);
			if (list1.size() > 0 && list2.size() == 0) {
				flag = "ERROR1";//卖方把买方拉黑
			}if (list1.size() == 0 && list2.size() > 0) {
				flag = "ERROR2";//买方把卖方拉黑
			}if (list1.size() > 0 && list2.size() > 0) {
				flag = "ERROR3";//双方互相拉黑
			}if (list1.size() == 0 && list2.size() == 0) {
				flag = "OK";
			}
			return flag;
		}else if (poolType == 2) {//买盘
			Integer companyid = this.buypoolDao.getBuyPoolCompanyid(map);
			
			Map<String,Object> param1 = new HashMap<String, Object>();
			param1.put("customerID", companyid);
			param1.put("blackCustomerID", map.get("blackCustomerID"));
			List<CustomsBlackList> list1 = this.CustomsBlackListDao.checkBlacklist(param1);
			
			Map<String,Object> param2 = new HashMap<String, Object>();
			param2.put("customerID",map.get("blackCustomerID"));
			param2.put("blackCustomerID", companyid);
			List<CustomsBlackList> list2 = this.CustomsBlackListDao.checkBlacklist(param2);
			if (list1.size() > 0 && list2.size() == 0) {
				flag = "ERROR1";//卖方把买方拉黑
			}if (list1.size() == 0 && list2.size() > 0) {
				flag = "ERROR2";//买方把卖方拉黑
			}if (list1.size() > 0 && list2.size() > 0) {
				flag = "ERROR3";//双方互相拉黑
			}if (list1.size() == 0 && list2.size() == 0) {
				flag = "OK";
			}
			return flag;
		}else {
			return null;
		}
		
	}

	/** 检验是否存在黑名单关系
	 * @Title: checkOrderList 
	 * @Description: TODO
	 * @param map
	 * @return 
	 * @author zhangnan/张楠
	 * @createTime 2015年12月16日下午5:30:23
	 */
	@Override
	public String checkOrderList(Map<String, Object> map) {
		String flag = "";
		Integer poolId1 = (Integer) map.get("poolId1");
		Integer poolType1 = (Integer) map.get("poolType1");
		Integer poolId2 = (Integer) map.get("poolId2");
//		Integer poolType2 = (Integer) map.get("poolType2");
		if (poolType1 == 1) {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("poolId", poolId2);
			param.put("blackCustomerID", this.buypoolDao.getBuyPoolCompanyid(param));
			//卖盘ID
			param.put("poolId", poolId1);
			flag = this.checkBlacklist(param,poolType1);
		}
		if (poolType1 == 2) {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("poolId", poolId2);
			param.put("blackCustomerID", this.sellPoolDao.getSellPool(param));
			//买盘ID
			param.put("poolId", poolId1);
			flag = this.checkBlacklist(param,poolType1);
		}
		return flag;
	}

	@Override
	public ItemPrice getItemPrice(User user) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dayStart",DateUtil.startOfTodDay(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss")));
		param.put("dayEnd",DateUtil.endOfTodDay(DateUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss")));
		param.put("itemId",Integer.parseInt(user.getItemId()));
		return this.iBusinessDao.getItemPrice(param);
	}
	
	
	@Override
	public Map<String, Object> compare(Map<String, Object> mapper) {
		Map<String, Object> map = new HashMap<>();
		String code = "ok";
		String strprice="";
		try {
			User user=new User();
			user.setItemId(mapper.get("itemId").toString());
			ItemPrice item=this.getItemPrice(user);
			if(item !=null ){
				Float price=Float.parseFloat(mapper.get("price").toString());
		        Float biglow=item.getLow().floatValue();
		        Float bighigh=item.getHigh().floatValue();
				if(biglow <= price && price <= bighigh){
					strprice=mapper.get("price").toString();
				}else{
					code="faild";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("code", code);
		map.put("strprice", strprice);
		return map;
	}

	
	
	/** 修改报盘价格时，实价、升、贴价的校验
	 * @Title: updatePoolBlur 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @author zhangnan/张楠
	 * @createTime 2015年12月11日上午10:26:02
	 */
	@Override
	public String updatePoolBlur(Map<String, Object> param) {
		String msg = "";
		//价格类型 0 实价  1 升水 2 贴水
		
		//报盘类型
		Integer poolType = (Integer) param.get("poolType");
		//价格类型
		Integer priceType = (Integer) param.get("priceType");
		//要修改的价格
		BigDecimal price = new BigDecimal(Double.valueOf((String)param.get("price")).doubleValue());
		
		param.put("price", price);
		// 卖盘
		if (poolType == 1) {
			//价格类型
			if (priceType == 0) {//实价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal maxPrice = new BigDecimal(poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(maxPrice.compareTo(temp) < 0){
							maxPrice = temp;
						}
					}
					if (price.compareTo(maxPrice) <= 0) {
						msg = "已经有还盘实价大于或等于本次要修改的还盘价!";
					}else {
						this.updateLastPrice(param);
						this.iBusinessDao.updPoolPrice(param);
						msg ="OK";
					}
				}else {
					this.updateLastPrice(param);
					this.iBusinessDao.updPoolPrice(param);
					msg ="OK";
				}
				
			}else if(priceType == 1){//升贴价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal maxPrice = new BigDecimal(poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(maxPrice.compareTo(temp) < 0){
							maxPrice = temp;
						}
					}
					if (price.compareTo(maxPrice) <= 0) {
						msg = "已经有还盘升贴价大于或等于本次要修改的升价!";
					}else {
						this.updateLastPrice(param);
						this.iBusinessDao.updPoolPrice(param);
						msg ="OK";
					}
				}else {
					this.updateLastPrice(param);
					this.iBusinessDao.updPoolPrice(param);
					msg ="OK";
				}
			}
//			else if(priceType == 2){//贴价
//				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
//				if(poolList != null && poolList.size() > 0){
//					BigDecimal minPrice = new BigDecimal(poolList.get(0).getPrice());
//					for (int i = 0; i < poolList.size(); i++) {
//						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
//						if(minPrice.compareTo(temp) > 0){
//							minPrice = temp;
//						}
//					}
//					if (price.compareTo(minPrice) >= 0) {
//						msg = "已经有还盘贴价小于或等于本次要修改的贴价!";
//					}else {
//						this.updateLastPrice(param);
//						this.iBusinessDao.updPoolPrice(param);
//						msg ="OK";
//					}
//				}else {
//					this.updateLastPrice(param);
//					this.iBusinessDao.updPoolPrice(param);
//					msg ="OK";
//				}
//			}
		}
		
		
		//买盘
		if(poolType == 2){
			//价格类型
			if (priceType == 0) {//实价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal maxPrice = new BigDecimal(poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(maxPrice.compareTo(temp) > 0){
							maxPrice = temp;
						}
					}
					if (price.compareTo(maxPrice) >= 0) {
						msg = "已经有还盘实价小于或等于本次要修改的实价!";
					}else {
						this.updateLastPrice(param);
						this.iBusinessDao.updPoolPrice(param);
						msg ="OK";
					}
				}else {
					this.updateLastPrice(param);
					this.iBusinessDao.updPoolPrice(param);
					msg ="OK";
				}
			}else if(priceType == 1){//升贴价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal minPrice = new BigDecimal(poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(minPrice.compareTo(temp) > 0){
							minPrice = temp;
						}
					}
					if (price.compareTo(minPrice) >= 0) {
						msg = "已经有还盘升贴价小于或等于本次要修改的升价!";
					}else {
						this.updateLastPrice(param);
						this.iBusinessDao.updPoolPrice(param);
						msg ="OK";
					}
				}else {
					this.updateLastPrice(param);
					this.iBusinessDao.updPoolPrice(param);
					msg ="OK";
				}
			}
//			else if(priceType == 2){//贴价
//				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
//				if(poolList != null && poolList.size() > 0){
//					BigDecimal maxPrice = new BigDecimal(poolList.get(0).getPrice());
//					for (int i = 0; i < poolList.size(); i++) {
//						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
//						if(maxPrice.compareTo(temp) < 0){
//							maxPrice = temp;
//						}
//					}
//					if (price.compareTo(maxPrice) <= 0) {
//						msg = "已经有还盘贴价大于或者等于本次要修改的贴价!";
//					}else {
//						this.updateLastPrice(param);
//						this.iBusinessDao.updPoolPrice(param);
//						msg ="OK";
//					}
//				}
//			}else {
//				this.updateLastPrice(param);
//				this.iBusinessDao.updPoolPrice(param);
//				msg ="OK";
//			}
		}
		return msg;
	}

	/**  新增还盘，撮合第二版加入  升贴价
	 * @Title: addCounterNew 
	 * @Description: TODO
	 * @param poolPrice
	 * @param id
	 * @return
	 * @author zhangnan/张楠
	 * @createTime 2015年12月11日下午4:50:31
	 */
	@Override
	public String addCounterNew(PoolPrice poolPrice, int id) {
		String msg = "";
		//价格类型 0 实价  1 升价 2 贴价
		
		//报盘类型
		Integer poolType = poolPrice.getPoolType();
		//价格类型
		Integer priceType = poolPrice.getPriceType();
		//要修改的价格
		BigDecimal price = new BigDecimal(poolPrice.getPrice());
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		param.put("price", price);
    	param.put("priceType", priceType);
    	param.put("poolType", poolType);
    	param.put("poolId", poolPrice.getPoolId());
    	
    	param.put("customerId", poolPrice.getCustomerId());
//    	
    	ChCustomsEntity customer = this.chCustomsEntityMapper.selectByPrimaryKey(poolPrice.getCustomerId());
    	
    	param.put("lastMallAccount", customer.getAccount());
    	
    	List<PoolPrice> poolPriceList = this.iBusinessDao.getPoolListThisCompany(param);
    	
    	//最后一次还盘企业名称
    	try {
			param.put("lastCustomerName", URLDecoder.decode(poolPrice.getLastCustomerName() ,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//商城
    	String account = this.iBusinessDao.getAccount(param);
    	
    	param.put("account",account);
		
		//新建报盘之卖盘
		if (poolType == 1) {
			if (priceType == 0) {//实价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal maxPrice = new BigDecimal( poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(maxPrice.compareTo(temp) < 0){
							maxPrice = temp;
						}
					}
					if (price.compareTo(maxPrice) < 0) {
						msg = "卖盘中，已经有还盘实价大于或等于本次要新增的的还盘实价!";
					}else {
						this.updateLastPrice(param);
						if (poolPriceList != null && poolPriceList.size() > 0) {
							this.iBusinessDao.updCounter(param);
						}else {
							this.iBusinessDao.addCounterNew(param);
						}
						msg ="OK";
					}
				}else {//本报盘没有任何还盘
					this.updateLastPrice(param);
					this.iBusinessDao.addCounterNew(param);
					msg ="OK";
				}
			}else if (priceType == 1) {//升贴价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal maxPrice = new BigDecimal( poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(maxPrice.compareTo(temp) < 0){
							maxPrice = temp;
						}
					}
					if (price.compareTo(maxPrice) <= 0) {
						msg = "卖盘中，已经有还盘升贴价大于或等于本次要新增的的还盘升贴价!";
					}else {
						this.updateLastPrice(param);
						if (poolPriceList != null && poolPriceList.size() > 0) {
							this.iBusinessDao.updCounter(param);
						}else {
							this.iBusinessDao.addCounterNew(param);
						}
						msg ="OK";
					}
				}else {//本报盘没有任何还盘
					this.updateLastPrice(param);
					this.iBusinessDao.addCounterNew(param);
					msg ="OK";
				}
			}
//			else if (priceType == 2) {//贴价
//				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
//				if(poolList != null && poolList.size() > 0){
//					BigDecimal minPrice =new BigDecimal(poolList.get(0).getPrice());
//					for (int i = 0; i < poolList.size(); i++) {
//						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
//						if(minPrice.compareTo(temp) > 0){
//							minPrice = temp;
//						}
//					}
//					if (price.compareTo(minPrice) >= 0) {
//						msg = "卖盘中，已经有还盘贴价小于或等于本次要新增的的贴价!";
//					}else {
//						this.updateLastPrice(param);
//						this.iBusinessDao.addCounterNew(param);
//						msg ="OK";
//					}
//				}else {
//					this.updateLastPrice(param);
//					this.iBusinessDao.addCounterNew(param);
//					msg ="OK";
//				}
//			}
			
		}
		//新建报盘之买盘
		if (poolType == 2) {
			if (priceType == 0) {//实价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal minPrice = new BigDecimal(poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(minPrice.compareTo(temp) > 0){
							minPrice = temp;
						}
					}
					if (price.compareTo(minPrice) >= 0) {
						msg = "买盘中，已经有还盘实价小于或等于本次要新增的的还盘实价!";
					}else {
						this.updateLastPrice(param);
						if (poolPriceList != null && poolPriceList.size() > 0) {
							this.iBusinessDao.updCounter(param);
						}else {
							this.iBusinessDao.addCounterNew(param);
						}
						msg ="OK";
					}
				}else {//本报盘没有任何还盘
					this.updateLastPrice(param);
					this.iBusinessDao.addCounterNew(param);
					msg ="OK";
				}
			}else if (priceType == 1) {//升贴价
				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
				if(poolList != null && poolList.size() > 0){
					BigDecimal minPrice = new BigDecimal(poolList.get(0).getPrice());
					for (int i = 0; i < poolList.size(); i++) {
						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
						if(minPrice.compareTo(temp) > 0){
							minPrice = temp;
						}
					}
					if (price.compareTo(minPrice) >= 0) {
						msg = "买盘中，已经有还盘升贴价小于或等于本次要新增的的还盘升贴价!";
					}else { 
						this.updateLastPrice(param);
						if (poolPriceList != null && poolPriceList.size() > 0) {
							this.iBusinessDao.updCounter(param);
						}else {
							this.iBusinessDao.addCounterNew(param);
						}
						msg ="OK";
					}
				}else {//本报盘没有任何还盘
					this.updateLastPrice(param);
					this.iBusinessDao.addCounterNew(param);
					msg ="OK";
				}
			}
//			else if (priceType == 2) {
//				List<PoolPrice> poolList = this.iBusinessDao.getPoolList(param);
//				if(poolList != null && poolList.size() > 0){
//					BigDecimal maxPrice = new BigDecimal(poolList.get(0).getPrice());
//					for (int i = 0; i < poolList.size(); i++) {
//						BigDecimal temp = new BigDecimal( poolList.get(i).getPrice());
//						if(maxPrice.compareTo(temp) < 0){
//							maxPrice = temp;
//						}
//					}
//					if (price.compareTo(maxPrice) <= 0) {
//						msg = "买盘中，已经有还盘贴价大于或等于本次要新增的的还盘贴价!";
//					}else {
//						this.updateLastPrice(param);
//						this.iBusinessDao.addCounterNew(param);
//						msg ="OK";
//					}
//				}else {
//					this.updateLastPrice(param);
//					this.iBusinessDao.addCounterNew(param);
//					msg ="OK";
//				}
//			}
			
		}
		return msg;
	}
	
	
	/** 修改最后一次还盘价
	 * @Title: updateLastPrice 
	 * @Description: TODO
	 * @param map
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2015年12月14日上午11:42:46
	 */
	private void updateLastPrice(Map<String,Object> map) throws RuntimeException{
		//报盘类型
		Integer poolType = (Integer) map.get("poolType");
		//价格类型
		Integer priceType = (Integer) map.get("priceType");
		
		BigDecimal priceTemp = (BigDecimal) map.get("price");
		//卖盘
		if(poolType == 1){
			ChSellPool sellPool = this.iBusinessDao.getSellPoolPrice(map);
			BigDecimal price  = sellPool.getPrice();
			if (priceType == 0 || priceType == 1) {
				if (priceTemp.compareTo(price) >= 0 ) {
					map.put("isConfirm", 1);
				}else {
					map.put("isConfirm", 0);
				}
			}
//			else if(priceType == 2){
//				if (priceTemp.compareTo(price) > 0 ) {
//					map.put("isConfirm", 0);
//				}else {
//					map.put("isConfirm", 1);
//				}
//			}
			this.iBusinessDao.updateSellLastPrice(map);
		}
		//买盘
		if (poolType == 2) {
			ChBuyPool buyPool = this.iBusinessDao.getBuyPoolPrice(map);
			BigDecimal price = buyPool.getPrice();
			
			if (priceType == 0 || priceType == 1) {
				if (priceTemp.compareTo(price) > 0 ) {
					map.put("isConfirm", 0);
				}else {
					map.put("isConfirm", 1);
				}
			}
//			else if(priceType == 2){
//				if (priceTemp.compareTo(price) >= 0 ) {
//					map.put("isConfirm", 1);
//				}else {
//					map.put("isConfirm", 0);
//				}
//			}
			
			this.iBusinessDao.updateBuyLastPrice(map);
		}
	}

	@Override
	public String checkOrderListBySell(Map<String, Object> balckParam) {
		String flag = "";
		List<CustomsBlackList> list = this.CustomsBlackListDao.checkBlacklist(balckParam);
		if (list.size() > 0) {
			flag = "ERROR";
		}if (list.size() == 0) {
			flag = "OK";
		}
		return flag;
	}

	@Override
	public Integer CheckPoolStatus(int poolId, int poolType) {
		Map<String,Object> param = new HashMap<String, Object>();
		
		Integer isClose = null;
		//卖盘
		if (poolType == 1) {
			param.put("sellPoolId", poolId);
			SellPool sellPool = this.sellPoolDao.getSellPoolById(param);
			isClose = sellPool.getIsClose();
		}
		//买盘
		if (poolType == 2) {
			BuyPoolEntity buyPool = this.buypoolDao.getbuypoolById(poolId);
			isClose = buyPool.getIsClose();
		}
		return isClose;
	}

	/** 查询某个报盘最新的一条还盘记录，并且添加商品名字
	 * @Title: queryCurrentMaxPoolPrice 
	 * @Description: TODO
	 * @param map
	 * @return
	 * @author zhangnan/张楠
	 * @createTime 2016年1月26日下午2:04:12
	 */
	@Override
	public PoolPrice queryCurrentMaxPoolPrice(Map<String, Object> map) {
		PoolPrice poolPrice = null ;
		try {
			List<PoolPrice> list = this.iBusinessDao.queryCurrentMaxPoolPrice(map);
			if (list != null && list.size() > 0) {
				poolPrice = list.get(0);
				int poolType =Integer.parseInt(map.get("poolType").toString());
				//商品名字
				if (poolType == 1) {
					ChCommodityEntity product = this.iBusinessDao.queryChCommodityEntity(map);
					if (product != null) {
						poolPrice.setProductName(product.getName());
					}else {
						poolPrice.setProductName("");
					}
				}
				if (poolType == 2) {
					ChCommodityEntity product = this.iBusinessDao.queryChCommodityEntity_BuyPool(map);
					if (product != null) {
						poolPrice.setProductName(product.getName());
					}else {
						poolPrice.setProductName("");
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poolPrice;
	}
	
	
	/** 订单成交，通知商城
	 * @Title: orderSubNotify 
	 * @Description: TODO
	 * @param map
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2015年12月31日下午2:33:09
	 */
	private void orderSubNotify(Map<String, Object> map) {
		Integer isConfirm = (Integer) map.get("isConfirm");
		//成交
		if (isConfirm == 1) {
			String account = (String) map.get("account");
			Integer priceType  = (Integer) map.get("priceType");
			ChCommodityEntity custom = new ChCommodityEntity();
			if (priceType == 1) {
				custom = this.iBusinessDao.queryChCommodityEntity(map);
			}
			if (priceType == 2) {
				custom = this.iBusinessDao.queryChCommodityEntity_BuyPool(map);
			}
			
			
			//商城账户
			map.put("account", account);
			
			map.put("noticetext","超价通知:您对报盘 "+custom.getName()+" 的出价已被其他卖家超过，请及时修改价格，以免错失好货！");
			
			map.put("createdat", new Date());
			
			map.put("isread", 0);
			
			map.put("noticetype", 2);//事件类型（ 1 订单成交、2 价格失败、 3 交易失败、 4 交易关闭、5 新出价）
			
			map.put("status", 0);
			
			map.put("updatedat", new Date());
			
			this.noticeDao2.addNotice(map);
			
		}
	}

	@Override
	public List<ChUsersEntity> queryUserList(Map<String, Object> map) {
		return this.usersMap.queryUserList(map);
	}

	/** 撮合交易管理选择单个报盘生成订单入口：“撮合订单”方式；生成订单后，报盘要关闭。2016年1月11日下午4:13:19
	 * @Title: closeSellPool 
	 * @Description: TODO
	 * @param params
	 * @author zhangnan/张楠
	 * @return: void
	 * @createTime 2016年1月11日下午4:32:37
	 */
	@Override
	public void closeSellPool(Map<String, Object> params) {
		this.iBusinessDao.closeSellPool(params);
	}
	
	public void closeBuyPool(Map<String, Object> params) {
		this.iBusinessDao.closeBuyPool(params);
	}
	
	
	/**
	 * 1、 需要撮合人员同当前最高还盘用户确认后，撤消该用户（A）最高还盘，并增加C用户还盘（还盘价格与报盘相同）；系统将会判定为成交，由撮合人员生成订单
	 * 2、 需要为当前用户新增一笔与报盘相同价格的还盘；系统将会判定为成交，而后生成订单
	 * 
	 * 	params.put("buyId", buyId);
		params.put("sellId", sellId);
		params.put("poolType", poolType);
		params.put("poolId", poolId);
	 */
	@Override
	public Map<String,Object> additionalCounter(Map<String,Object> param) {
		Map<String,Object> map = new HashMap<String, Object>();
		Integer poolType = Integer.parseInt((String) param.get("poolType"));
		//报盘类型为卖盘
		if (poolType == 1) {
			Integer buyId = Integer.parseInt((String) param.get("buyId"));
			ChSellPool sellPool = this.iBusinessDao.getSellPoolPrice(param);
			ChCustomsEntity chCustomsEntity = this.chCustomsEntityMapper.selectByPrimaryKey(buyId);
			Integer createSource = sellPool.getCreateSource();
			
			//报盘关闭
			param.put("isClose", 1);
			
			//只有撮合交易管理里面的 报盘 才有  还盘的概念
			if (createSource == 1 || createSource == 2) {
					//此种方式 “生成订单”的入口 只可能是    “卖盘池”
				if (sellPool.getLastcustomerid() == null) {
					param.put("price", sellPool.getPrice());
					param.put("isConfirm", 1);
					param.put("customerId", chCustomsEntity.getId());
					param.put("lastCustomerName", chCustomsEntity.getCompanyname());
					param.put("lastMallAccount", chCustomsEntity.getAccount());
					
					//"撮合订单" 更新卖盘
					this.iBusinessDao.updateSellLastPrice(param);
					
					//"撮合订单" 更新买盘
					if (param.get("poolId_buyer") != null) {
//						ChCustomsEntity chCustomsEntity_sell = this.chCustomsEntityMapper.selectByPrimaryKey(sellPool.getCustomerid());
						
//						param.put("price", sellPool.getPrice());
//						param.put("isConfirm", 1);
//						param.put("customerId", sellPool.getCustomerid());
//						param.put("lastCustomerName", chCustomsEntity_sell.getCompanyname());
//						param.put("lastMallAccount", chCustomsEntity_sell.getAccount());
						
						//通过特殊方式"撮合订单"按钮 生成的订单，买盘什么都不修改，直接关闭(isClose)
						param.put("isClose", 1);
						//"撮合订单"
						this.iBusinessDao.updateBuyLastPrice_buyer(param);
					}
					
					param.put("priceType",sellPool.getPricetype());
					param.put("account", chCustomsEntity.getAccount());
					this.iBusinessDao.addCounterNew(param);
				
					map.put("price", sellPool.getPrice());
				}else{
					//if (!buyId.toString().equals(sellPool.getLastcustomerid().toString()) ) {
						param.put("price", sellPool.getPrice());
						param.put("isConfirm", 1);
						param.put("customerId", chCustomsEntity.getId());
						param.put("lastCustomerName", chCustomsEntity.getCompanyname());
						param.put("lastMallAccount", chCustomsEntity.getAccount());
						
						//"撮合订单" 更新卖盘
						this.iBusinessDao.updateSellLastPrice(param);
						
						//"撮合订单" 更新买盘
						if (param.get("poolId_buyer") != null) {
							
//							ChCustomsEntity chCustomsEntity_sell = this.chCustomsEntityMapper.selectByPrimaryKey(sellPool.getCustomerid());
							
//							param.put("price", sellPool.getPrice());
//							param.put("isConfirm", 1);
//							param.put("customerId", sellPool.getCustomerid());
//							param.put("lastCustomerName", chCustomsEntity_sell.getCompanyname());
//							param.put("lastMallAccount", chCustomsEntity_sell.getAccount());
							
							//通过特殊方式"撮合订单"按钮 生成的订单，买盘什么都不修改，直接关闭(isClose)
							param.put("isClose", 1);
							//"撮合订单"
							this.iBusinessDao.updateBuyLastPrice_buyer(param);
							
						}
						
						
						if (param.get("orderSource").equals("cuohejiaoyi")) {
							this.iBusinessDao.updCounter(param);
						}else if (param.get("orderSource").equals("maimaipan")) {
							List<PoolPrice> list = this.iBusinessDao.getPoolListThisCompany(param);
							if (list!= null && list.size() == 0) {
								param.put("priceType",sellPool.getPricetype());
								param.put("account", chCustomsEntity.getAccount());
								this.iBusinessDao.addCounterNew(param);
							}else {
								this.iBusinessDao.updCounter(param);
							}  
						}
						map.put("price", sellPool.getPrice());
					//}
				}
			}
		}
		//报盘类型为买盘
		else {
			Integer sellId = Integer.parseInt((String) param.get("sellId"));
			ChBuyPool buyPool = this.iBusinessDao.getBuyPoolPrice(param);
			ChCustomsEntity chCustomsEntity = this.chCustomsEntityMapper.selectByPrimaryKey(sellId);
			Integer createSource = buyPool.getCreateSource();
			if (createSource == 1) {
				//此种方式 “生成订单”的入口 只可能是    “买盘池”
				if (buyPool.getLastcustomerid() == null) {
					param.put("price", buyPool.getPrice());
					param.put("isConfirm", 1);
					param.put("customerId", chCustomsEntity.getId());
					param.put("lastCustomerName", chCustomsEntity.getCompanyname());
					param.put("lastMallAccount", chCustomsEntity.getAccount());
					
					this.iBusinessDao.updateSellLastPrice(param);
					param.put("priceType",buyPool.getPricetype());
					param.put("account", chCustomsEntity.getAccount());
					this.iBusinessDao.addCounterNew(param);
					
					map.put("price", buyPool.getPrice());
				}else{
					if (!sellId.toString().equals(buyPool.getLastcustomerid().toString())) {
						param.put("price", buyPool.getPrice());
						param.put("isConfirm", 1);
						param.put("customerId", chCustomsEntity.getId());
						param.put("lastCustomerName", chCustomsEntity.getCompanyname());
						param.put("lastMallAccount", chCustomsEntity.getAccount());
						this.iBusinessDao.updateBuyLastPrice(param);
						if (param.get("orderSource").equals("cuohejiaoyi")) {
							this.iBusinessDao.updCounter(param);
						}else if (param.get("orderSource").equals("maimaipan")) {
							List<PoolPrice> list = this.iBusinessDao.getPoolListThisCompany(param);
							if (list!= null && list.size() == 0) {
								param.put("priceType",buyPool.getPricetype());
								param.put("account", chCustomsEntity.getAccount());
								this.iBusinessDao.addCounterNew(param);
							}else {
								this.iBusinessDao.updCounter(param);
							}
						}
						map.put("price", buyPool.getPrice());
						this.iBusinessDao.updCounter(param);
						map.put("price", buyPool.getPrice());
					}
				}
			}
		}
		return map;
	}

	@Override
	public ChBuyPoolEntity getChBuyPoolEntityById(Integer chBuyId) {
		return this.buyPoolDao.selectByPrimaryKey(chBuyId);
	}

	@Override
	public ChWareHouse getChWareHouse(String wareId) {
		return this.wareHouseDao.selectByPrimaryKey(Integer.parseInt(wareId));
	}

	@Override
	public ChCustomsEntity getChCustomsEntity(int customsId) {
		return this.customsDao.selectByPrimaryKey(customsId);
	}

	@Override
	public void updateBuyPoolById(ChBuyPoolEntity buyPool) {
		this.buyPoolDao.updateByPrimaryKey(buyPool);
	}

	@Override
	public void addPoolPrice(ChPoolPriceEntity poolPrice) {
		this.poolPriceDao.insert(poolPrice);
	}
	
}
