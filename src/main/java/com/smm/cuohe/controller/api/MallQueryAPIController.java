package com.smm.cuohe.controller.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smm.cuohe.bo.IMallQueryAPIBO;
import com.smm.cuohe.bo.IPoolPriceBo;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.dao.IPoolPriceDao;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.MallPoolInfo;
import com.smm.cuohe.domain.MallQueryOrder;
import com.smm.cuohe.domain.MallQueryPar;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author haokang 撮合2.0提供数据查询接口
 */

@Controller
@RequestMapping("/mallQueryAPI")
public class MallQueryAPIController {

	@Resource
	IMallQueryAPIBO mallQueryAPIBO;
	
	@Autowired 
	private IPoolPriceBo iPoolPriceBo;
	
	@Autowired
	private IPoolPriceDao iPoolPriceDao;
	
	@Resource
	private ISellPoolBO sellPollBo;
	
	@Resource
	private BuyPoolBo buyPoolBo;
	
	/**
	 * 指定客户所有买盘列表
	 * 
	 * @return
	 */
	@RequestMapping("/queryAllBuyPoolBy")
	@ResponseBody  
	public Map<String, Object> queryAllBuyPoolBy(@RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray json = new JSONArray();
		String code="ok";
		String msg="查询成功";
		try {
			MallQueryPar model=queryCondition(paraMap);
			List<MallPoolInfo> list = mallQueryAPIBO.queryAllBuyPoolBy(model);
			List<MallPoolInfo> listinfo=new ArrayList<MallPoolInfo>();
			List<MallPoolInfo> poolList=new ArrayList<MallPoolInfo>();
			if(list !=null && list.size()>0){
				for(MallPoolInfo info:list){
					MallQueryPar mallpar = new MallQueryPar();
					mallpar.setPoolId(info.getId().toString());
					mallpar.setPoolType(info.getPoolType().toString());
					mallpar.setAccount(info.getAccount().toString());
					String listMall = mallQueryAPIBO.queryPoolPriceInfo2(mallpar);
					info.setListMall(listMall);
					Map<String, Object> maper=new HashMap<String, Object>();
					maper.put("id", info.getId());
					maper.put("commodity_id", info.getCommodity_id());
					if(model.getProName()!=null){
						maper.put("productName", model.getProName());
					}
					if(!model.getItemId().toString().equals("26278")){
						maper.put("attr_name","品牌");
					}
					List<CommodityAttr> listBuyPoolAttr=mallQueryAPIBO.getBuyingAttrsList(maper);
					getAttrList(paraMap,info,listBuyPoolAttr);
				}
				for(MallPoolInfo pool:list){
					if(pool.getCommodityAttrList() != null){
						listinfo.add(pool);
					}
				}
			}
			if(listinfo !=null && listinfo.size()>0){
				List<MallPoolInfo> mallList=setPoolList(listinfo,paraMap);
				poolList=mallList;
			}
			json.addAll(poolList);
		} catch (Exception e) {
			e.printStackTrace();
			code="faild";
			msg="查询失败";
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", json.toString());
		return map;
	}
	
	/**
	 * 指定客户所有卖盘列表
	 * 
	 * @return
	 */
	@RequestMapping("/queryAllSellPoolBy")
	@ResponseBody
	public Map<String, Object> queryAllSellPoolBy(@RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray json = new JSONArray();
		String code="ok";
		String msg="查询成功";
		try {
			MallQueryPar model=queryCondition(paraMap);
			List<MallPoolInfo> list = mallQueryAPIBO.queryAllSellPoolBy(model);
			List<MallPoolInfo> listinfo=new ArrayList<MallPoolInfo>();
			List<MallPoolInfo> poolList=new ArrayList<MallPoolInfo>();
			if(list !=null && list.size()>0){
				for(MallPoolInfo info:list){
					MallQueryPar mallpar = new MallQueryPar();
					mallpar.setPoolId(info.getId().toString());
					mallpar.setPoolType(info.getPoolType().toString());
					mallpar.setAccount(info.getAccount().toString());
					String listMall = mallQueryAPIBO.queryPoolPriceInfo2(mallpar);
					info.setListMall(listMall);
					Map<String, Object> maper=new HashMap<String, Object>();
					maper.put("id", info.getId());
					maper.put("commodity_id", info.getCommodity_id());
					if(model.getProName()!=null){
						maper.put("productName", model.getProName());
					}
					if(!model.getItemId().toString().equals("26278")){
						maper.put("attr_name","品牌");
					}
					List<CommodityAttr> listCommodityAttr=mallQueryAPIBO.getCommodityAttrList(maper);
					getAttrList(paraMap,info,listCommodityAttr);
				}
				for(MallPoolInfo pool:list){
					if(pool.getCommodityAttrList() != null){
						listinfo.add(pool);
					}
				}
			}
			if(listinfo !=null && listinfo.size()>0){
				List<MallPoolInfo> mallList=setPoolList(listinfo,paraMap);
				poolList=mallList;
			}
			json.addAll(poolList);
		} catch (Exception e) {
			e.printStackTrace();
			code="faild";
			msg="查询失败";
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", json.toString());
		return map;
	}

	/**
	 * 指定客户参与的买盘列表
	 * 
	 * @return
	 */
	@RequestMapping("/queryInBuyPoolBy")
	@ResponseBody
	public Map<String, Object> queryInBuyPoolBy(@RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray json = new JSONArray();
		String code="ok";
		String msg="查询成功";
		try {
			MallQueryPar model = queryCondition(paraMap);
			List<MallPoolInfo> list = mallQueryAPIBO.queryInBuyPoolBy(model);
			List<MallPoolInfo> listinfo=new ArrayList<MallPoolInfo>();
			List<MallPoolInfo> poolList=new ArrayList<MallPoolInfo>();
			if(list !=null && list.size()>0){
				for(MallPoolInfo info:list){
					MallQueryPar mallpar = new MallQueryPar();
					mallpar.setPoolId(info.getId().toString());
					mallpar.setPoolType(info.getPoolType().toString());
					mallpar.setAccount(info.getAccount().toString());
					String listMall = mallQueryAPIBO.queryPoolPriceInfo2(mallpar);
					info.setListMall(listMall);
					Map<String, Object> maper=new HashMap<String, Object>();
					maper.put("id", info.getId());
					maper.put("commodity_id", info.getCommodity_id());
					if(model.getProName()!=null){
						maper.put("productName", model.getProName());
					}
					if(!model.getItemId().toString().equals("26278")){
						maper.put("attr_name","品牌");
					}
					List<CommodityAttr> listBuyPoolAttr=mallQueryAPIBO.getBuyingAttrsList(maper);
					getAttrList(paraMap,info,listBuyPoolAttr);
				}
				for(MallPoolInfo pool:list){
					if(pool.getCommodityAttrList() != null){
						listinfo.add(pool);
					}
				}
			}
			if(listinfo !=null && listinfo.size()>0){
				List<MallPoolInfo> mallList=setPoolList(listinfo,paraMap);
				poolList=mallList;
			}
			json.addAll(poolList);
		} catch (Exception e) {
			e.printStackTrace();
			code="faild";
			msg="查询失败";
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", json.toString());

		return map;
	}

	/**
	 * 指定客户参与的卖盘列表
	 * 
	 * @return
	 */
	@RequestMapping("/queryInSellPoolBy")
	@ResponseBody
	public Map<String, Object> queryInSellPoolBy(@RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray json = new JSONArray();
		String code="ok";
		String msg="查询成功";
		try {
			MallQueryPar model =  queryCondition(paraMap);
			List<MallPoolInfo> list = mallQueryAPIBO.queryInSellPoolBy(model);
			List<MallPoolInfo> listinfo=new ArrayList<MallPoolInfo>();
			List<MallPoolInfo> poolList=new ArrayList<MallPoolInfo>();
			if(list !=null && list.size()>0){
				for(MallPoolInfo info:list){
					MallQueryPar mallpar = new MallQueryPar();
					mallpar.setPoolId(info.getId().toString());
					mallpar.setPoolType(info.getPoolType().toString());
					mallpar.setAccount(info.getAccount().toString());
					String listMall = mallQueryAPIBO.queryPoolPriceInfo2(mallpar);
					info.setListMall(listMall);
					Map<String, Object> maper=new HashMap<String, Object>();
					maper.put("id", info.getId());
					maper.put("commodity_id", info.getCommodity_id());
					if(model.getProName()!=null){
						maper.put("productName", model.getProName());
					}
					if(!model.getItemId().toString().equals("26278")){
						maper.put("attr_name","品牌");
					}
					List<CommodityAttr> listCommodityAttr=mallQueryAPIBO.getCommodityAttrList(maper);
					getAttrList(paraMap,info,listCommodityAttr);
				}
				for(MallPoolInfo pool:list){
					if(pool.getCommodityAttrList() != null){
						listinfo.add(pool);
					}
				}
			}
			if(listinfo !=null && listinfo.size()>0){
				List<MallPoolInfo> mallList=setPoolList(listinfo,paraMap);
				poolList=mallList;
			}
			json.addAll(poolList);
		} catch (Exception e) {
			e.printStackTrace();
			code="faild";
			msg="查询失败";
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", json.toString());
		return map;
	}

	/**
	 * 当天所有的报盘（卖盘、买盘）列表。
	 * 
	 * @return
	 */
	@RequestMapping("/queryTodayPoolBy")
	@ResponseBody
	public Map<String, Object> queryTodayPoolBy(@RequestBody Map<String, Object> paraMap) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONArray json = new JSONArray();
			try {
				MallQueryPar model = queryCondition(paraMap);
				List<MallPoolInfo> listinfo=new ArrayList<MallPoolInfo>();
				List<MallPoolInfo> poolList=new ArrayList<MallPoolInfo>();
				List<MallPoolInfo> list = mallQueryAPIBO.queryTodayPoolBy(model);
				if(list !=null && list.size()>0){
					for(MallPoolInfo info:list){
						Map<String, Object> maper=new HashMap<String, Object>();
						maper.put("id", info.getId());
						maper.put("commodity_id", info.getCommodity_id());
						if(model.getProName()!=null){
							maper.put("productName", model.getProName());
						}
						if(!model.getItemId().toString().equals("26278")){
							maper.put("attr_name","品牌");
						}
						if(info.getPoolType().toString().equals("1")){
							List<CommodityAttr> listCommodityAttr=mallQueryAPIBO.getCommodityAttrList(maper);
							getAttrList(paraMap,info,listCommodityAttr);
						}else{
							List<CommodityAttr> listCommodityAttr=mallQueryAPIBO.getBuyingAttrsList(maper);
							getAttrList(paraMap,info,listCommodityAttr);
						}
					}
					for(MallPoolInfo pool:list){
						Map<String, Object> mapper=new HashMap<String, Object>();
						mapper.put("id", pool.getId());//报盘ID
						mapper.put("poolType", pool.getPoolType());//报盘类型1 卖盘  2 买盘
						mapper.put("account",  paraMap.get("account").toString());//用户account
						List<PoolPrice> listPool=mallQueryAPIBO.getPoolPrice(mapper);//获取还盘数据
						if(listPool ==null || listPool.size()==0){
							if(pool.getCommodityAttrList() != null){
								listinfo.add(pool);
							}
						}
					}
				}
				if(listinfo !=null && listinfo.size()>0){
					List<MallPoolInfo> mallList=setPoolList(listinfo,paraMap);
					poolList=mallList;
				}
				json.addAll(poolList);
				map.put("data", json.toString());
				map.put("count", poolList.size());
				map.put("code", "ok");
				map.put("msg", "成功");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", "faild");
				map.put("msg", "未知异常");
				map.put("data", "");
				return map;
		}
		return map;
	}
	
	public List<MallPoolInfo> setPoolList(List<MallPoolInfo> listinfo,Map<String, Object> paraMap){
		List<MallPoolInfo> poolList=new ArrayList<MallPoolInfo>();
		try {
			for(MallPoolInfo in:listinfo){
				int count=0;
				String wareId=in.getWareId();//获取数据库值
				System.out.println("获取数据库值="+wareId);
				if(paraMap.get("warehouseId")!=null){
					String warehouseId=paraMap.get("warehouseId").toString();//获取页面值
					System.out.println("获取页面值="+warehouseId);
					if(warehouseId.indexOf("|")!=-1){//页面值有多个
						String[] wer=warehouseId.split("\\|");
						for (int i = 0; i < wer.length; i++) {
							String war=wer[i].toString();//获取多个里一个值
							if(wareId.indexOf(",")!=-1){//数据值有多个
								String[] wid=wareId.split(",");
								for (int j = 0; j < wid.length; j++) {
									String wed=wid[j].toString();
									if(wed.equals(war)){
										count++;
									}
								}
							}else{//数据库值只有一个
								if(war.equals(wareId)){
									count++;
								}
							}
						}
					}else{//页面值只有一个
						if(wareId.indexOf(",")!=-1){//数据值有多个
							String[] wid=wareId.split(",");
							for (int j = 0; j < wid.length; j++) {
								String wed=wid[j].toString();
								if(wed.equals(warehouseId)){
									count++;
								}
							}
						}else{
							if(wareId.equals(warehouseId)){
								count++;
							}
						}
				  }
				  if(count>0){
					poolList.add(in);
				  }
				}else{//没有这个 参数
					poolList=listinfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poolList;
	}
	
	public  void getAttrList(Map<String, Object> paraMap,MallPoolInfo info,List<CommodityAttr> listCommodityAttr){
		int count=0;
		int counters=0;
		StringBuffer sdf=new StringBuffer();
		try {
			for(CommodityAttr attr:listCommodityAttr){
				sdf.append(attr.getAttrName()+"="+attr.getAttrValue()+"-");
			}
			if(sdf.length()>0){
				String str=sdf.substring(0,sdf.length()-1).toString();
				System.out.println(str);
				String[] cts=str.split("-");
				if(paraMap.get("brand")!=null){
					if(!paraMap.get("brand").toString().equals("")){
						String brand=paraMap.get("brand").toString();
						String[] con=brand.split(",");
						counters=con.length;
						for(int j=0;j<cts.length;j++){
							String ing=cts[j].toString();
							String[] ater = ing.split("=");
							String ter=ater[0].toString();//对应数据库key
							String tme=ater[1].toString();//对应数据库value
							for(int i=0;i<con.length;i++){
								String tin=con[i].toString();
								String[] atre=tin.split("=");
								String att=atre[0].toString();//对应获取key
								String ayy=atre[1].toString();//对应获取value
								if(att.equals(ter)){
									if(ayy.indexOf("|")!=-1){
										String[] spl=ayy.split("\\|");
										for(int t=0;t<spl.length;t++){
											String war=spl[t].toString();//对应获取分割value
											if(tme.indexOf(",")!=-1){
												String[]  ple=tme.split(",");
												for(int n=0;n<ple.length;n++){
													String wer=ple[n].toString();//对应数据库分割value
													if(war.equals(wer)){
														count++;
													}
												}
											}else{
												if(war.equals(tme)){
													count++;
												}
											}
										}
									}else{
										if(tme.indexOf(",")!=-1){
											String[]  ple=tme.split(",");
											for(int n=0;n<ple.length;n++){
												String wer=ple[n].toString();//对应数据库分割value
												if(ayy.equals(wer)){
													count++;
												}
											}
										}else{
											if(tme.equals(ayy)){
												count++;
											}
										}
									}
								}
							}
						}
					}
				}
				if(count>=counters){
					if(listCommodityAttr !=null && listCommodityAttr.size()>0){
						for(CommodityAttr attr:listCommodityAttr){
							info.setProductName(attr.getCommodityName());
						}
						info.setCommodityAttrList(listCommodityAttr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 每日报价接口： 参考价格、 最高价、最低价
	 * 
	 * @return
	 */
	@RequestMapping("/queryTodayPriceBy")
	@ResponseBody
	public Map<String, Object> queryTodayPriceBy(@RequestBody Map<String, Object> paraMap) {
		String itemId = (String) paraMap.get("itemId");
		String date = (String) paraMap.get("date");
		Map<String, Object> map = new HashMap<String, Object>();
		if (itemId == null || itemId.equals("") || date == null || date.equals("")) {
			map.put("code", "faild");
			map.put("msg", "参数有误");
			map.put("data", "");
			return map;
		}
		MallQueryPar model = new MallQueryPar();
		model.setItemId(Integer.valueOf(itemId));
		model.setDate(date);
		ItemPrice query = mallQueryAPIBO.queryTodayPriceBy(model);

		map.put("code", "ok");
		map.put("msg", "查询成功");
		map.put("data", JSONUtil.doConvertObject2Json(query));

		return map;
	}

	/**
	 * 每个报盘对应查询： 1，首次还盘记录。 2，最新2条还盘记录， 3，当前用户的还盘记录
	 * 
	 * @return
	 */
	@RequestMapping("/queryPoolPriceInfo")
	@ResponseBody
	public Map<String, Object> queryPoolPriceInfo(@RequestBody Map<String, Object> paraMap) {
		String poolId = paraMap.get("poolId").toString();
		String poolType = paraMap.get("poolType").toString();
		String account = null;
		if(paraMap.get("account")!=null){
		 account = paraMap.get("account").toString();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (poolId == null || poolType == null ||  poolId.equals("") || poolType.equals("")) {
			map.put("code", "faild");
			map.put("msg", "参数有误");
			map.put("data", "");
			return map;
		}
		
		String[] poolIds = poolId.split(",");
		Map<String, Object> imap = new HashMap<String, Object>();
		for (String id : poolIds) {
		MallQueryPar model = new MallQueryPar();
		model.setPoolId(id);
		model.setPoolType(poolType);
		model.setAccount(account);
		String newmap = mallQueryAPIBO.queryPoolPriceInfo2(model);
//		JSONArray json = new JSONArray();
//		json.addAll(list);
		imap.put(id, newmap);
		}
		JSONObject jsonObj = JSONObject.fromObject(imap);
		map.put("code", "ok");
		map.put("msg", "查询成功");
		map.put("data", jsonObj);
		return map;
	}

	/**
	 * 查询报盘还盘数
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryPriceCount")
	@ResponseBody
	public Map<String, Object> queryPriceCount(@RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String poolId = paraMap.get("poolId").toString();
			String poolType = paraMap.get("poolType").toString();
			if (poolId == null || "".equals(poolId)) {
				map.put("code", "faild");
				map.put("msg", "参数有误");
				return map;
			} else {
			    String[] ids = poolId.split(",");
			    String[] types = poolType.split(",");
			    Map<String, String> imap = new HashMap<String, String>();
			   for (int i = 0; i < ids.length; i++) {
			    	MallQueryPar par = new MallQueryPar();
			    	par.setPoolId(ids[i]);
			    	par.setPoolType(types[i]);
			    	Integer count = mallQueryAPIBO.queryCountPool(par);
			    	imap.put(ids[i], String.valueOf(count));
					map.put("code", "ok");
					map.put("msg", "查询成功");
					map.put("data", imap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "faild");
			map.put("msg", "未知异常");
			return map;
		}
		return map;
	}
	
	
	
   

	@RequestMapping("/queryOrder")
	@ResponseBody
	public Map<String, Object> queryOrder(@RequestBody Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		MallQueryPar model = new MallQueryPar();
		JSONArray json = new JSONArray();
		String code="ok";
		String msg="查询成功";
		int count=0;
		try {
			String account = paraMap.get("account").toString();
			String itemId = paraMap.get("itemId").toString();
			if(paraMap.get("startNum")!=null){
				model.setStartNum(Integer.valueOf(paraMap.get("startNum").toString()));
			}else{
				model.setStartNum(0);
			}
			if(paraMap.get("endNum")!=null){
				model.setEndNum(Integer.valueOf(paraMap.get("endNum").toString()));
			}else{
				model.setEndNum(10);
			}
			if (account == null || itemId == null || account.equals("") || itemId.equals("")) {
				code="faild";
				msg="参数有误";
			} else {
				model.setItemId(Integer.valueOf(itemId));
				model.setAccount(account);
				List<MallQueryOrder> list = mallQueryAPIBO.queryOrder(model);
				for(MallQueryOrder order:list){
					if(order.getPoolType().toString().equals("1")){
						List<MallQueryOrder> listcommdity=mallQueryAPIBO.getCommodityAttrByBuyingId(order.getCommodityId());
						String ing="";
						if(listcommdity.size()>0){
							StringBuffer sdf=new StringBuffer();
							for(MallQueryOrder mall:listcommdity){
								sdf.append(mall.getAttrName()+"="+mall.getAttrValue()+",");
							}
							ing=sdf.substring(0,sdf.length()-1).toString();
						}
						order.setAttr(ing);
					} 
					if(order.getPoolType().toString().equals("2")){
						List<MallQueryOrder> listbuying=mallQueryAPIBO.getBuyingAttrsByBuyingId(order.getId());
						String str="";
						if(listbuying.size()>0){
							StringBuffer sdf=new StringBuffer();
							for(MallQueryOrder mall:listbuying){
								sdf.append(mall.getAttrName()+"="+mall.getAttrValue()+",");
							}
							str=sdf.substring(0, sdf.length()-1).toString();
						}
						order.setAttr(str);
					}
				}
				List<MallQueryOrder> listSize = mallQueryAPIBO.queryOrderCount(model);
				count=listSize.size();
				json.addAll(list);
				map.put("data", json.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			code="faild";
			msg="未知异常";
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("count",count);
		return map;
	}

	@RequestMapping(value = "getAllItemPrice")
	@ResponseBody
	public Map<String, Object> getAllItemPrice() {

		return mallQueryAPIBO.getAllItemPrice();
	}
	
	/**
	 * 判断商城用户是否已存在
	 * @return
	 */
	@RequestMapping("/isExistAccount")
	@ResponseBody
	public Map<String,Object> isExistAccount(@RequestBody Map<String, Object> paraMap){
		Map<String, Object> map = new HashMap<String, Object>();
		Object account = paraMap.get("account");
		Object itemId = paraMap.get("itemId");
		if (account == null || itemId == null || account.equals("") || itemId.equals("")) {
			map.put("status", "faild");
			map.put("msg", "参数有误");
			return map;
		} else {
			MallQueryPar model = new MallQueryPar();
			model.setAccount(account.toString());
			model.setItemId(Integer.valueOf(itemId.toString()));
			Integer count = mallQueryAPIBO.isExistAccount(model);
			if(count==null || count==0){
				map.put("status", "faild");
				map.put("msg", "用户不存在");
				return map;
			}
			map.put("status", "ok");
			map.put("msg", "用户已存在");
		}
		return map;
	}	
	
	
	/**
	 * 撤销还盘记录
	 */
	@RequestMapping("/delPoolPrice")
	@ResponseBody
	public Map<String,Object> delPoolPrice(@RequestBody Map<String, Object> paraMap){
		Map<String, Object> map = new HashMap<String, Object>();
		String status="ok";
		String msg="撤销成功";
		try {
			if(paraMap.get("poolId")==null || paraMap.get("poolType")==null || paraMap.get("account")==null){
				status="faild";
				msg="参数错误";
			}else{
	    		Map<String, Object> mapper=new HashMap<String, Object>();
	    		Integer poolId=Integer.parseInt(paraMap.get("poolId").toString());//报盘ID
	    		Integer poolType=Integer.parseInt(paraMap.get("poolType").toString());//报盘类型 1 卖盘  2 买盘
	    		mapper.put("poolId", paraMap.get("poolId").toString());
	    		mapper.put("poolType", paraMap.get("poolType").toString());
	    		mapper.put("account", paraMap.get("account").toString());//商城会员账号
	    		if (poolType == 1) {//1 卖盘
					SellPool sellPool=sellPollBo.getSellPoolByPoolId(poolId);//获取当前报盘价格
					if(sellPool !=null){
						if(!sellPool.getIsClose().toString().equals("1") && !sellPool.getIsConfirm().toString().equals("1") && !sellPool.getStatus().toString().equals("1")){//判断当前报盘是否关闭是否成交是否删除
							PoolPrice poolP=iPoolPriceDao.getPoolPrice(mapper);//获取还盘记录数据
				    		if(poolP!=null){
				    			mapper.put("id", poolP.getId());//还盘ID
					    		iPoolPriceBo.getPoolPriceByMap(mapper);
				    		}else{
				    			msg="不存在记录";
				    		}
						}else{
							status = "faild";
							msg = "卖盘已关闭或已成交";
						}
					}
				} else if (poolType == 2) {//2 买盘
					BuyPoolEntity buyPoolEntity=buyPoolBo.getbuypoolById(poolId);//获取当前报盘价格
					if(buyPoolEntity !=null){
						if(!buyPoolEntity.getIsClose().toString().equals("1") && !buyPoolEntity.getIsConfirm().toString().equals("1") && !buyPoolEntity.getStatus().toString().equals("1") ){//判断当前报盘是否关闭是否成交是否删除
							PoolPrice poolP=iPoolPriceDao.getPoolPrice(mapper);//获取还盘记录数据
				    		if(poolP!=null){
				    			mapper.put("id", poolP.getId());//还盘ID
					    		iPoolPriceBo.getPoolPriceByMap(mapper);
				    		}else{
				    			msg="不存在记录";
				    		}
						}else{
							status = "faild";
							msg = "买盘已关闭或已成交";
						}
					}
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			status="faild";
			msg="撤销失败";
		}
		map.put("status", status);
		map.put("msg", msg);
		return map;
	}	
	
	
	/**
	 * 查询条件
	 * @return
	 * @throws Exception 
	 */
	public MallQueryPar queryCondition(Map<String, Object> paraMap) throws Exception{
		MallQueryPar model = new MallQueryPar();
		try {
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String itemId =paraMap.get("itemId").toString();
			model.setItemId(Integer.valueOf(itemId));
			if(paraMap.get("account")!=null){
				 model.setAccount( paraMap.get("account").toString());
			}
			if(paraMap.get("dateOrderby")!=null){
				model.setDateOrderby(Integer.valueOf(paraMap.get("dateOrderby").toString()));
			}
			if(paraMap.get("priceOrderBy")!=null){
			 model.setPriceOrderBy(Integer.valueOf(paraMap.get("priceOrderBy").toString()));
			}
			if(paraMap.get("startNum")!=null){
				model.setStartNum(Integer.valueOf(paraMap.get("startNum").toString()));
			}
			if(paraMap.get("endNum")!=null){
				model.setEndNum(Integer.valueOf(paraMap.get("endNum").toString()));
			}
			if(paraMap.get("minPrice")!=null){
				model.setMinPrice(paraMap.get("minPrice").toString());
			}
			if(paraMap.get("maxPrice")!=null){
				model.setMaxPrice(paraMap.get("maxPrice").toString());
			}
			if(paraMap.get("maxQuantity")!=null){
				model.setMaxQuantity(paraMap.get("maxQuantity").toString());
			}
			if(paraMap.get("minQuantity")!=null){
				model.setMinQuantity(paraMap.get("minQuantity").toString());
			}
			if(paraMap.get("endTime")!=null){
				model.setEndTime(dateFm.parse(paraMap.get("endTime").toString()));
			}
			if(paraMap.get("startTime")!=null){
				model.setStartTime(dateFm.parse(paraMap.get("startTime").toString()));
			}
			if(paraMap.get("startNum") != null){
				model.setStartNum(Integer.valueOf(paraMap.get("startNum").toString()));
			}
			if(paraMap.get("sellStartNum") != null){
				model.setSellStartNum(Integer.valueOf(paraMap.get("sellStartNum").toString()));
			}
			if(paraMap.get("buyStartNum") != null){
				model.setBuyStartNum(Integer.valueOf(paraMap.get("buyStartNum").toString()));
			}
			/*if(paraMap.get("warehouseId")!=null){
				String str=paraMap.get("warehouseId").toString();
				StringBuffer sdf=new StringBuffer();
				if(str.indexOf("|")!=-1){
					String[] ing=str.split("\\|");
					for(int j=0;j<ing.length;j++){
						sdf.append("'"+ing[j].toString()+"',");
					}
				model.setWarehouseId("("+sdf.substring(0,sdf.length()-1).toString()+")");
				}else{
					model.setWarehouseId("('"+str+"')");
				}
			}*/
			if(paraMap.get("productName")!=null){
				String str=paraMap.get("productName").toString();
				StringBuffer sdf=new StringBuffer();
				if(str.indexOf("|")!=-1){
					String[] ing=str.split("\\|");
					for(int j=0;j<ing.length;j++){
						sdf.append("'"+ing[j].toString()+"',");
					}
				model.setProName("("+sdf.substring(0,sdf.length()-1).toString()+")");
				}else{
				  model.setProName("('"+str+"')");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return model;
	}
	
	public static void main(String[] args) {
		//String brand="品牌=上冶|铁峰|晶晶,产地=上海,国别=中国,是否通氧=通氧";
		/*String[] counts=brand.split(",");
		StringBuffer sdf=new StringBuffer();
		StringBuffer dfr=new StringBuffer();
		StringBuffer fer=new StringBuffer();
		for(int i=0;i<counts.length;i++){
			String[] ater = counts[i].toString().split("=");
			sdf.append("att.attr_name = '"+ater[0].toString()+"' AND ");
			String str=ater[1].toString();
			if(str.indexOf("|")!=-1){
				String[] ing=str.split("\\|");
				fer.append("(");
				for(int j=0;j<ing.length;j++){
					fer.append("att.attr_value like '%"+ing[j].toString()+"%' or ");
				}
				String fe=fer.substring(0,fer.length()-3).toString()+") AND ";
				dfr.append(fe);
			}else{
				dfr.append("att.attr_value = '"+ater[1].toString()+"' AND ");
			}
		}
		String st=sdf.substring(0,sdf.length()-4).toString();
		String in=dfr.substring(0,dfr.length()-4).toString();*/
		
		/*String[] counts=brand.split(",");
		StringBuffer sdf=new StringBuffer();
		StringBuffer dfr=new StringBuffer();
		StringBuffer fer=new StringBuffer();
		String in="and";
		for(int i=0;i<counts.length;i++){
			String[] ater = counts[i].toString().split("=");
			sdf.append("attr_name = '"+ater[0].toString()+"' or ");
			if(ater.length>1){
				String str=ater[1].toString();
				if(str.indexOf("|")!=-1){
					String[] ing=str.split("\\|");
					fer.append("(");
					for(int j=0;j<ing.length;j++){
						fer.append("attr_value like '%"+ing[j].toString()+"%' or ");
					}
					String fe=fer.substring(0,fer.length()-3).toString()+") or ";
					dfr.append(fe);
				}else if(ater[0].toString().equals("品牌")){
					dfr.append("attr_value like '%"+ater[1].toString()+"%' or ");
				}else{
					dfr.append("attr_value = '"+ater[1].toString()+"' or ");
				}
				in=dfr.substring(0,dfr.length()-4).toString();
			}
		}
		String st=sdf.substring(0,sdf.length()-4).toString();
		if(in.equals("and")){
			System.out.println(st);
		}else{
			System.out.println(st +" AND "+ in);
		}*/
		
		
		String str="是否通氧=不通氧-产地=新疆";//数据库获取值
		String[] cts=str.split("-");
		String brand="是否通氧=不通氧,产地=新疆|安徽";//获取参数值
		String[] con=brand.split(",");
		for(int j=0;j<cts.length;j++){
			String ing=cts[j].toString();
			String[] ater = ing.split("=");
			String ter=ater[0].toString();//对应数据库key
			String tme=ater[1].toString();//对应数据库value
			for(int i=0;i<con.length;i++){
				String tin=con[i].toString();
				String[] atre=tin.split("=");
				String att=atre[0].toString();//对应获取key
				String ayy=atre[1].toString();//对应获取value
				if(att.equals(ter)){
					if(ayy.indexOf("|")!=-1){
						String[] spl=ayy.split("\\|");
						for(int t=0;t<spl.length;t++){
							String war=spl[t].toString();//对应获取分割value
							if(tme.indexOf(",")!=-1){
								String[]  ple=str.split(",");
								for(int n=0;n<ple.length;n++){
									String wer=ple[n].toString();//对应数据库分割value
									if(war.equals(wer)){
										System.out.println(war=wer);
									}
								}
							}else{
								if(war.equals(tme)){
									System.out.println(war=tme);
								}
							}
						}
					}else{
						if(tme.indexOf(",")!=-1){
							String[]  ple=str.split(",");
							for(int n=0;n<ple.length;n++){
								String wer=ple[n].toString();//对应数据库分割value
								if(ayy.equals(wer)){
									System.out.println(ayy=wer);
								}
							}
						}else{
							if(tme.equals(ayy)){
								System.out.println(tme=ayy);
							}
						}
					}
				}
			}
		}
	
		

		/*String brand="品牌=铜冠|铁峰|大江大板,产地=上海,国别=中国,是否通氧=通氧";
		String[] counts=brand.split(",");
		StringBuffer sdf=new StringBuffer();
		StringBuffer bfe=new StringBuffer();
		for(int i=0;i<counts.length;i++){
			String[] ater = counts[i].toString().split("=");
			sdf.append("'"+ater[0].toString()+"',");
			bfe.append("'"+ater[1].toString().replaceAll("\\|", ",")+"',");
			String str=ater[1].toString();
			if(str.indexOf("|")!=-1){
				String[] ing=str.split("\\|");
				for(int j=0;j<ing.length;j++){
					bfe.append("'"+ing[j].toString()+"',");
				}
			}else{
				bfe.append("'"+ater[1].toString()+"',");
			}
		}
		System.out.println("("+sdf.substring(0,sdf.length()-1).toString()+")");
		System.out.println("("+bfe.substring(0,bfe.length()-1).toString()+")");*/
	}
	
}
