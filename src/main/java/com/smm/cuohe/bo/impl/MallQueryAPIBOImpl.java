package com.smm.cuohe.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IMallQueryAPIBO;
import com.smm.cuohe.dao.IMallQueryDao;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.MallPoolInfo;
import com.smm.cuohe.domain.MallQueryOrder;
import com.smm.cuohe.domain.MallQueryPar;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.QueryPriceCount;

/**
 * 
 * @author haokang
 *
 */
@Service
public class MallQueryAPIBOImpl implements IMallQueryAPIBO {
	@Resource IMallQueryDao mallQueryDao;
	
	/**
	 * 指定客户所有买盘列表
	 * @return
	 */
	@Override
	public List<MallPoolInfo> queryAllBuyPoolBy(MallQueryPar model) {
		List<MallPoolInfo> infoList=new ArrayList<MallPoolInfo>();
		try {
			List<MallPoolInfo> list = mallQueryDao.queryAllBuyPoolBy(model);
			if(list.size()>0){
				if(model.getBuyStartNum() != null){
					for(MallPoolInfo info:list){
						if(info.getId()>model.getBuyStartNum()){
							infoList.add(info);
						}
					}
				}else{
					infoList=list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}

	/**
	 * 指定客户参与的买盘列表
	 * @return
	 */
	@Override
	public List<MallPoolInfo> queryInBuyPoolBy(MallQueryPar model) {
		List<MallPoolInfo> infoList=new ArrayList<MallPoolInfo>();
		try {
			List<MallPoolInfo> list = mallQueryDao.queryInBuyPoolBy(model);
			if(list.size()>0){
				if(model.getBuyStartNum() != null){
					for(MallPoolInfo info:list){
						if(info.getId()>model.getBuyStartNum()){
							infoList.add(info);
						}
					}
				}else{
					infoList=list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}

	/**
	 * 每日报价接口： 参考价格、 最高价、最低价
	 * @return
	 */
	@Override
	public ItemPrice queryTodayPriceBy(MallQueryPar model) {
		ItemPrice ipr = mallQueryDao.queryTodayPriceBy(model);
		return ipr;
	}

	/**
	 * 指定客户所有卖盘列表
	 * @return
	 */
	@Override
	public List<MallPoolInfo> queryAllSellPoolBy(MallQueryPar model) {
		List<MallPoolInfo> infoList=new ArrayList<MallPoolInfo>();
		try {
			List<MallPoolInfo> list = mallQueryDao.queryAllSellPoolBy(model);
			if(list.size()>0){
				if(model.getSellStartNum() != null){
					for(MallPoolInfo info:list){
						if(info.getId()>model.getSellStartNum()){
							infoList.add(info);
						}
					}
				}else{
					infoList=list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}
	
	/**
	 * 指定客户参与的卖盘列表
	 * @return
	 */
	@Override
	public List<MallPoolInfo> queryInSellPoolBy(MallQueryPar model) {
		List<MallPoolInfo> infoList=new ArrayList<MallPoolInfo>();
		try {
			List<MallPoolInfo> list = mallQueryDao.queryInSellPoolBy(model);
			if(list.size()>0){
				if(model.getSellStartNum() != null){
					for(MallPoolInfo info:list){
						if(info.getId()>model.getSellStartNum()){
							infoList.add(info);
						}
					}
				}else{
					infoList=list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}

	/**
	 * 当天除指定客户外所有的报盘（卖盘、买盘）列表。
	 * @return
	 */
	@Override
	public List<MallPoolInfo> queryTodayPoolBy(MallQueryPar model) {
		List<MallPoolInfo> infoList=new ArrayList<MallPoolInfo>();
		try {
			List<MallPoolInfo> list = mallQueryDao.queryTodayPoolBy(model);
			if(list.size()>0){
				for(MallPoolInfo info:list){
					if(info.getPoolType()==2){//买盘
						if(model.getBuyStartNum()!=null){
							if(info.getId()>model.getBuyStartNum()){
								infoList.add(info);
							}
						}else{
							infoList.add(info);
						}
					}else if(info.getPoolType()==1){
						if(model.getSellStartNum()!=null){
							if(info.getId()>model.getSellStartNum()){
								infoList.add(info);
							}
						}else{
							infoList.add(info);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}

	/**
	 * 每个报盘对应查询： 1，首次还盘记录。 2，最新2条还盘记录， 3，当前用户的还盘记录
	 * @return
	 */
	@Override
	public List<MallPoolInfo> queryPoolPriceInfo(MallQueryPar model) {
		
		
		List<MallPoolInfo> list = mallQueryDao.queryPoolPriceInfo(model);
		return list;
	}

	@Override
	public List<MallPoolInfo> queryTodayPoolByAll(MallQueryPar model) {
		
		return mallQueryDao.queryTodayPoolByAll(model);
	}

	@Override
	public Integer queryTodayPoolBycount(MallQueryPar model) {
		// TODO Auto-generated method stub
		return mallQueryDao.queryTodayPoolBycount(model);
	}

	@Override
	public List<QueryPriceCount> queryPriceCount(String[] array) {
		// TODO Auto-generated method stub
		return mallQueryDao.queryPriceCount(array);
	}

	@Override
	public List<MallQueryOrder> queryOrder(MallQueryPar model) {
		return mallQueryDao.queryOrder(model);
	}
	
	@Override
	public List<MallQueryOrder> queryOrderCount(MallQueryPar model) {
		return mallQueryDao.queryOrderCount(model);
	}
	
	@Override
	public Map<String, Object> getAllItemPrice() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", "ok");
		
		map.put("msg", "获取成功");
		
		map.put("data", mallQueryDao.getAllItemPrice());
		
		return map;
	}
	
	/**
	 * 判断商城用户是否已存在
	 * @param model
	 * @return
	 */
	@Override
	public Integer isExistAccount(MallQueryPar model) {
		Integer count = mallQueryDao.isExistAccount(model);
		return count;
	}

	
	@Override
	public String queryPoolPriceInfo2(MallQueryPar model) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		//当前盘的信息
		MallQueryPar returnPar =  mallQueryDao.queryPoolById(model);
		//1首次还盘记录
		List<MallPoolInfo> firstlist = mallQueryDao.queryPoolPriceFirst(model);
		//2最新还盘记录
		List<MallPoolInfo> new2 = mallQueryDao.queryPoolPriceNew2(model);
		//3当前报盘的还盘记录
		Integer count = mallQueryDao.queryCountPool(model);
		List<MallPoolInfo> currentlist = null;
		//4查看当前报盘的还盘笔数
		
		if(model.getAccount()==null||"".equals(model.getAccount())){
			map.put("current", null);
		}else{
			JSONArray currentJson = new JSONArray();
			currentlist = mallQueryDao.queryPoolPriceCurrentCustomer(model);
			currentJson.addAll(currentlist);
			if(currentlist!=null)map.put("current", currentJson.toString());
			else map.put("current", null);
		}
		JSONArray firstJson = new JSONArray();
		firstJson.addAll(firstlist);
		JSONArray new2Json = new JSONArray();
		new2Json.addAll(new2);
		map.put("first", firstJson.toString());
		map.put("new2", new2Json.toString());
		map.put("orderCode", returnPar.getOrderCode());
		map.put("mallBuyAccount",returnPar.getMallBuyAccount());
		map.put("mallSellAccount", returnPar.getMallSellAccount());
		map.put("isConfirm", returnPar.getIsConfirm());
		map.put("isClose", returnPar.getIsClose());
		map.put("count", count);
		map.put("price",returnPar.getPrice());
		map.put("priceType", returnPar.getPriceType());
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}

	@Override
	public List<MallQueryOrder> getBuyingAttrsByBuyingId(int id) {
		List<MallQueryOrder> list =null;
		try {
			list=mallQueryDao.getBuyingAttrsByBuyingId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<MallQueryOrder> getCommodityAttrByBuyingId(int id) {
		List<MallQueryOrder> list =null;
		try {
			list=mallQueryDao.getCommodityAttrByBuyingId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer queryCountPool(MallQueryPar model) {
		return mallQueryDao.queryCountPool(model);
	}

	@Override
	public List<CommodityAttr> getCommodityAttrList(Map<String, Object> map) {
		List<CommodityAttr> list=null;
		try {
			list=mallQueryDao.getCommodityAttrList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CommodityAttr> getBuyingAttrsList(Map<String, Object> map) {
		List<CommodityAttr> list=null;
		try {
			list=mallQueryDao.getBuyingAttrsList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PoolPrice> getPoolPrice(Map<String, Object> map) {
		List<PoolPrice> list=null;
		try {
			list=mallQueryDao.getPoolPrice(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



}
