package com.smm.cuohe.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IBusinessBO;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.bo.notice.NoticeBo;
import com.smm.cuohe.domain.BusinessOffer;
import com.smm.cuohe.domain.ChBuyPool;
import com.smm.cuohe.domain.ItemPrice;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.SerachParam;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChPoolPriceEntity;
import com.smm.cuohe.domain.base.ChUsersEntity;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.util.ExportUtil;

/**
 * @author  zhaoyutao
 * @version 2015年11月16日 下午1:31:16
 * 撮合交易管理
 */

@Controller
@RequestMapping(value="business")
public class BusinessController {
	
	@Resource
	IBusinessBO iBusinessBo;
	@Resource
	private ISellPoolBO iSellPoolBO;
	@Resource
	private BuyPoolBo buyPoolbo;
	@Resource
	private NoticeBo noticeBo;
	@Resource
	private ISellPoolBO sellPollBo;
	

	private static Logger logger = Logger.getLogger(BusinessController.class.getName());
	
	/** 跳转到报盘主页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("/business/index");
		User user = (User) request.getSession().getAttribute("userInfo");
		//品目价格,每天的交易指导价
		ItemPrice itemPrice = this.iBusinessBo.getItemPrice(user);
		view.addObject("itemPrice", itemPrice);
		//保存价格类型
        view.addObject("productType",user.getItems().getSHFEType().toUpperCase());
		return view;
	}
	
	/** 获取所有报盘信息
	 * @param request
	 * @param sp
	 * @return
	 */
	@RequestMapping(value="getBusinessInfo")
	@ResponseBody
	public Map<String, Object> getBusinessInfo(HttpServletRequest request, SerachParam sp){
		if (sp.getType() == null) {
			sp.setType(0);
		}
		
		User user = (User) request.getSession().getAttribute("userInfo");
		sp.setUserId(user.getId());
		return iBusinessBo.getPoolBusiness(Integer.parseInt(user.getItemId()), sp);
	}
	
	
	/** 获取所有报盘信息并导出
	 * @param request
	 * @param sp
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="exportOffer")
	public void exportOffer(HttpServletRequest request, SerachParam sp,HttpServletResponse response) throws IOException{
		User user = (User) request.getSession().getAttribute("userInfo");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			sp.setAttribute(request.getParameter("attribute"));
			sp.setOperator(request.getParameter("operator"));
			sp.setContent(request.getParameter("content"));
			Map<String, Object> map=iBusinessBo.getExportOffer(Integer.parseInt(user.getItemId()), sp);
			// 2.生成excel文件
			ExportUtil<BusinessOffer> excel = new ExportUtil<BusinessOffer>();
			String fileName = "还盘数据";
			List<String> listHeader = (List<String>) map.get("listHeader");
			List<LinkedHashMap<String, String>> listData = (ArrayList<LinkedHashMap<String, String>>) map.get("listData");
			// 获得数据
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			excel.export("sheet1", listHeader, listData, os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	
	
	/** 关闭报盘
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	@RequestMapping(value="closeOffer")
	@ResponseBody
	public Map<String, Object> closeOffer(Integer poolId, Integer poolType, Integer counterNum){
		return iBusinessBo.closeOffer(poolId, poolType, counterNum);
	}
	
	
	/** 关闭报盘
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	@RequestMapping(value="closeOfferAll")
	@ResponseBody
	public Map<String, Object> closeOfferAll(String ids){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
		
		if(ids!=null&&!"".equals(ids)){
			String data[]=ids.split(",");
			for (int i = 0; i < data.length; i++) {
				String[] onedata=data[i].split("\\|");
				Integer counterNum=0;
				if(onedata[2]!=null && !onedata[2].equals("")){
					 counterNum=Integer.valueOf(onedata[2]);
				}	
				iBusinessBo.closeOffer(Integer.valueOf(onedata[0]), Integer.valueOf(onedata[1]),counterNum );
			}
			
			
		}} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "erron");
			map.put("msg", "系统提示，操作失败");
			return  map;
		}
		map.put("code", "succ");
		map.put("msg", "系统提示，操作成功");
		return map;
	}
	/** 还原报盘
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	@RequestMapping(value="restoreOffer")
	@ResponseBody
	public Map<String, Object> restoreOffer(Integer poolId, Integer poolType, Integer counterNum){
		return iBusinessBo.restoreOffer(poolId, poolType, 1);
	}
	
	/** 跳转到添加还盘页面（撮合员添加）
	 * @param poolId
	 * @param poolType
	 * @return
	 */
	@RequestMapping(value="addCounterView")
	public ModelAndView addOfferView(Integer poolId, Integer poolType, Double quantity,Integer priceType,BigDecimal low){
		ModelAndView mv = new ModelAndView("business/addCounter");
		
		SellPool sellPool = new SellPool();
		
		BuyPoolEntity buyPoll = new BuyPoolEntity();
		
		if (poolType == 1) {
			sellPool = this.iSellPoolBO.getSellPoolById(poolId);
			mv.addObject("customerId", sellPool.getCustomerId());
		}
		
		if (poolType == 2) {
			buyPoll = this.buyPoolbo.getbuypoolById(poolId);
			mv.addObject("customerId", buyPoll.getCustomerId());
		}
		
		
		
		mv.addObject("poolId", poolId);
		
		mv.addObject("poolType", poolType);
		
		mv.addObject("quantity", quantity);
		
		mv.addObject("priceType", priceType);
		
		mv.addObject("low", low);
		
		return mv;
	}
	
	@RequestMapping(value="addCounter")
	@ResponseBody
	public Map<String, Object> addOffer(PoolPrice poolPrice, HttpServletRequest request){
		
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
		
		return iBusinessBo.addCounter(poolPrice, user.getItems().getId());
	}
	
	
	/** 新增每日交易指导价
	 * @Title: addOfferPrice 
	 * @Description: TODO
	 * @param itemPrice
	 * @param request
	 * @return
	 * @author zhangnan/张楠
	 * @return: Map<String,Object>
	 * @createTime 2015年12月30日上午9:52:35
	 * BigDecimal 类型数据 特殊处理  变成负数
	 */
	@RequestMapping(value="addItemPrice")
	@ResponseBody
	public Map<String, Object> addOfferPrice(ItemPrice itemPrice, HttpServletRequest request){
		
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
				
		itemPrice.setItemId(user.getItems().getId());
		
		itemPrice.setItemName(user.getItems().getName());
		
		itemPrice.setCreatedBy(user.getId());
		
		setPricetype(itemPrice);
//		System.err.println(itemPrice.getHigh().signum());
//		//判断前台传过来的数据是否是负数
//		if (itemPrice.getHigh().signum() == -1) {
//			BigDecimal high = itemPrice.getHigh();
//			high = high.multiply(new BigDecimal(-1));
//			itemPrice.setHigh(high);
//		}
//		//判断前台传过来的数据是否是负数
//		if (itemPrice.getLow().signum() == -1) {
//			BigDecimal low = itemPrice.getLow();
//			low = low.multiply(new BigDecimal(-1));
//			itemPrice.setLow(low);
//		}
//		//判断前台传过来的数据是否是负数
//		if (itemPrice.getRecommend().signum() == -1) {
//			BigDecimal recommend = itemPrice.getRecommend();
//			recommend = recommend.multiply(new BigDecimal(-1));
//			itemPrice.setRecommend(recommend);
//		}
		
		return iBusinessBo.addItemPrice(itemPrice);
		
	}
	
	private void setPricetype(ItemPrice itemPrice){
		
		//价格类型 0 实价 1 升水 2 贴水  电解铜、锌 都是升贴水，其他实价
		
		//价格类型   舍弃"贴水"的概念! 0 实价 1 升贴水  电解铜、锌 都是升贴水，其他实价  2016年1月4日下午3:44:50
		//铜 银 锌是只能是升贴水的  2015年12月30日上午10:19:29  负数就保存负数
		if(itemPrice.getItemId() == 26276 || itemPrice.getItemId() == 26280  || itemPrice.getItemId() == 26288){
			
//			if(itemPrice.getHigh().compareTo(BigDecimal.ZERO) > 0){
//				itemPrice.setHighType(1);
//			} else {
//				itemPrice.setHighType(2);
//			}
//			//itemPrice.setHigh(itemPrice.getHigh().abs());
//			itemPrice.setHigh(itemPrice.getHigh());
//			if(itemPrice.getLow().compareTo(BigDecimal.ZERO) > 0){
//				itemPrice.setLowType(1);
//			} else {
//				itemPrice.setLowType(2);
//			}
//			//itemPrice.setLow(itemPrice.getLow().abs());
//			itemPrice.setLow(itemPrice.getLow());
//			if(itemPrice.getRecommend().compareTo(BigDecimal.ZERO) > 0){
//				itemPrice.setRecommendType(1);
//			} else {
//				itemPrice.setRecommendType(2);
//			}
			//itemPrice.setRecommend(itemPrice.getRecommend().abs());
			itemPrice.setHighType(1);
			itemPrice.setLowType(1);
			itemPrice.setRecommendType(1);
			
			itemPrice.setRecommend(itemPrice.getRecommend());
		} else {
			itemPrice.setHighType(0);
			itemPrice.setLowType(0);
			itemPrice.setRecommendType(0);
		}
	}
	
	/**订单检验黑名单关系
	 * @Title: checkBlacklist 
	 * @Description: TODO
	 * @param poolId
	 * @param buyId
	 * @return
	 * @return: String
	 */
	@RequestMapping("checkBlacklist")
	@ResponseBody
	public String checkBlacklist(Integer poolId,Integer buyId,Integer poolType) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("poolId", poolId);
		map.put("blackCustomerID", buyId);
		String flag = "";
		try {
			flag = this.iBusinessBo.checkBlacklist(map,poolType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/** 销售、采购生成“撮合订单”的时候检验黑名单关系
	 * @Title: checkOrderList 
	 * @Description: TODO
	 * @param poolId1
	 * @param poolType1
	 * @param poolId2
	 * @param poolType2
	 * @return
	 * @return: String
	 */
	@RequestMapping("checkOrderList")
	@ResponseBody
	public String checkOrderList(Integer poolId1,Integer poolType1,Integer poolId2,Integer poolType2) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("poolId1", poolId1);
		map.put("poolType1", poolType1);
		map.put("poolId2", poolId2);
		map.put("poolType2", poolType2);
		String flag = "";
		try {
			flag = this.iBusinessBo.checkOrderList(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}
	
	/** 新增还盘，撮合第二版加入  升贴价(同一个报盘，同一家公司第一次还盘是新增，后面的还盘都是更新)
	 * @Title: addCounterNew 
	 * @Description: TODO
	 * @param poolPrice
	 * @param request
	 * @return
	 * @author zhangnan/张楠
	 * @return: String
	 * @createTime 2015年12月11日下午3:20:12
	 */
	@RequestMapping("addCounterNew")
	@ResponseBody
	public String addCounterNew(PoolPrice poolPrice, HttpServletRequest request) {
		String flag = "";
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
//		return iBusinessBo.addCounter(poolPrice, user.getItems().getId());
		try {
			//通知其他还盘企业 报价失效     （新增需求 2015年12月31日上午9:11:44 ）
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("poolId", poolPrice.getPoolId());
			map.put("poolType", poolPrice.getPoolType());
			
			String current_account = sellPollBo.getcompanyAccount(poolPrice.getCustomerId());
			//新增还盘之前价格最合适的一条还盘信息
			PoolPrice  poolPrice_1 = this.iBusinessBo.queryCurrentMaxPoolPrice(map);
			
			flag = iBusinessBo.addCounterNew(poolPrice, user.getItems().getId());
			
			//通知其他还盘企业 报价失效     （新增需求 2015年12月31日上午9:11:44 ）
			if (flag.equals("OK")) {
				if (poolPrice_1 != null) {
					Map<String,Object> param = new HashMap<String, Object>();
					
					String account = poolPrice_1.getAccount();
					//产品名
					String productName = poolPrice_1.getProductName();
					//商城账户
					param.put("account", account);
					param.put("productName", productName);
					param.put("poolType", poolPrice.getPoolType());
					if (!current_account.equals(account)) {
						this.noticeBo.priceInvalidNotify(param);
					}
				}
				//给用户发布新出价通知
				noticeBo.newOffer(poolPrice);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return flag;
	}
	
	/** 模糊查询报价员(撮合人员)
	 * @Title: offererList 
	 * @Description: TODO
	 * @param userName
	 * @return
	 * @author zhangnan/张楠
	 * @return: List<ChUsersEntity>
	 * @createTime 2016年1月4日下午1:50:39
	 */
	@RequestMapping("offererList")
	@ResponseBody
	public List<ChUsersEntity> offererList(String userName) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", userName);
		List<ChUsersEntity> list = null;
		try {
			list = this.iBusinessBo.queryUserList(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
		}
		return list;
	}
	
	
	/** 新建报盘，"报价人" 默认显示当前登录的撮合员
	 * @Title: initOfferer 
	 * @Description: TODO
	 * @param request
	 * @return
	 * @author zhangnan/张楠
	 * @return: User
	 * @createTime 2016年1月7日下午5:13:44
	 */
	@RequestMapping("initOfferer")
	@ResponseBody
	public User initOfferer(HttpServletRequest request) {
		User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
		return user;
	}
}

