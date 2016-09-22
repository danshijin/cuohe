package com.smm.cuohe.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IBusinessBO;
import com.smm.cuohe.bo.IContacterBo;
import com.smm.cuohe.bo.IPoolPriceBo;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.bo.users.IBuyPoolPBO;
import com.smm.cuohe.domain.BuyPoolPOJO;
import com.smm.cuohe.domain.ChBuyPool;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChCustomsEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.tools.ResultMessage;


/**
 * 还盘Controller
 * @author wanghui
 *
 */

@Controller
@RequestMapping("/poolprice")
public class PoolPriceController {
	
	@Autowired
	private IPoolPriceBo iPoolPriceBo;
	
	@Autowired
	private IContacterBo iContacterBo;
	
	@Autowired
	private ISellPoolBO iSellPoolBO;
	
	@Autowired
	private IBuyPoolPBO iBuyPoolPBO;
	
	@Autowired
	private IBusinessBO iBusinessBO;
	
	private static Logger logger = Logger.getLogger(PoolPriceController.class.getName());
	
	/** 跳转到还盘列表页面
	 * @Title: getPoolPrice 
	 * @Description: TODO
	 * @param request
	 * @return
	 * @author zhangnan/张楠
	 * @return: ModelAndView
	 * @createTime 2015年12月11日
	 */
	@RequestMapping(value="getPoolPrice")
	public ModelAndView getPoolPrice(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/poolprice/poolpriceList");
		String poolType= request.getParameter("poolType").toString();
		String poolId=request.getParameter("poolId");
		List<PoolPrice> list = null;
		try {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("poolType", poolType);
			map.put("poolId", poolId);
			if(poolType.equals("1")){//卖盘
				map.put("tidentity", "coer.tidentity IN (0,2)");
			}else{
				map.put("tidentity", "coer.tidentity IN (1,2)");
			}
			list = iPoolPriceBo.getPoolPrice(map);
			StringBuffer sdf=new StringBuffer();
			for(PoolPrice pool:list){
				if(pool.getPriceType()==0){
					pool.setTypeAmt("实价  "+pool.getPrice());
				}else if(pool.getPriceType()==1){
					pool.setTypeAmt("升  "+pool.getPrice());
				}else if(pool.getPriceType()==2){
					pool.setTypeAmt("贴  "+pool.getPrice());
				}
				if(pool.getLxrName()==null){
					sdf.append(pool.getCustomerId()+",");
				}
			}
			if(sdf.length()>1){
				String sqlId=" coer.customerId in  ("+sdf.substring(0, sdf.length()-1).toString()+")";
				Map<String, Object> maper=new HashMap<String, Object>();
				maper.put("sqlId", sqlId);
				List<Contacter> contlist=iContacterBo.getContacterBycustomerId(maper);
				for(Contacter cont:contlist){
					Integer cusId=cont.getCustomerId();
					for(PoolPrice pool:list){
						if(cusId.compareTo(pool.getCustomerId())==0){
							pool.setLxrName(cont.getName());
							pool.setMobilePhone(cont.getMobilePhone());
						}
					}
				}
			}
			Map<String, Object> maper=new HashMap<String, Object>();
			maper.put("poolId", poolId);
			maper.put("poolType", poolType);
			PoolPrice poolList=iPoolPriceBo.getPoolPriceByPoolId(maper);//根据报盘ID和报盘类型获取报盘最新金额
			double price=0;
			if(poolList !=null){
				price=poolList.getPrice();//获取最新价格
			}
			System.err.println(poolType.equals("1"));
			if (poolType.equals("1")) {
				SellPool sellPool = this.iSellPoolBO.getSellPoolById(Integer.parseInt(poolId));
				mv.addObject("baopanjia", sellPool.getPrice());
			}else if (poolType.equals("2")) {
				BuyPoolPOJO buyPool = this.iBuyPoolPBO.getBuyPool(poolId);
				mv.addObject("baopanjia", buyPool.getPrice());
			}
			
			mv.addObject("list", list);
			mv.addObject("poolType", poolType);
			mv.addObject("poolId", poolId);
			mv.addObject("bigAmt", price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	} 
	
	
    /**
	 * 修改
	 * @param request
	 * @return
	 */
    @RequestMapping("updateById")
    public ModelAndView updateById(HttpServletRequest request) {
    	ModelAndView view = new ModelAndView("poolprice/updatePoolprice");
    	try {
    		 String poolId=request.getParameter("poolId");//报盘ID
    		 String poolType=request.getParameter("poolType");//报盘类型
    		 Map<String, Object> map=new HashMap<String, Object>();
    		 map.put("poolId", poolId);
    		 map.put("poolType", poolType);
    		 PoolPrice poolList=iPoolPriceBo.getPoolPriceByPoolId(map);//根据报盘ID和报盘类型获取报盘最新金额
    		 double price=0;
    		 if(poolList !=null){
    			 price=poolList.getPrice();//获取最新价格
    		 }
	    	 Integer id=Integer.parseInt(request.getParameter("id"));//还盘ID
	    	 PoolPrice poolPrice=iPoolPriceBo.getPoolPriceById(id);
	    	 view.addObject("poolPrice", poolPrice);
	    	 view.addObject("bigAmt", price);
	    	 view.addObject("poolType", poolType);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return view;
    }
    
    
    @RequestMapping(value = "/updatePool", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePool(HttpServletRequest request) {
    	PoolPrice poolPrice=null;
    	poolPrice=iPoolPriceBo.getPoolPriceById(Integer.parseInt(request.getParameter("id")));//根据还盘ID查询还盘数据
    	poolPrice.setPrice(Double.valueOf(request.getParameter("price")));
    	poolPrice.setPriceType(Integer.parseInt(request.getParameter("priceType")));
        	/*//更新买卖盘数据
        	Integer poolId=poolPrice.getPoolId();//报盘ID
        	Integer poolType=poolPrice.getPoolType();//报盘类型
        	if(poolType==1){//卖盘
        		SellPool sellPool=new SellPool();
        		SellPool sell=iSellPoolBO.getSellPoolByPoolId(poolId);//根据卖盘ID获取卖盘数据
        		float  sellPrice=sell.getPrice();//价格
        		String sellPriceType=sell.getPriceType();//价格类型
        		if(sellPriceType.equals(poolPrice.getPriceType().toString())){
        			if(sellPrice==poolPrice.getPrice()){
        				sellPool.setId(poolId);
        				sellPool.setIsConfirm(1);
        				iSellPoolBO.updateSellPoolPrice(sellPool);//更新卖盘数据
        			}
        		}
        	}if(poolType==2){//买盘
        		BuyPoolPOJO buyPool=new BuyPoolPOJO();
        		BuyPoolPOJO buy=iBuyPoolPBO.getBuyPool(poolId.toString());//根据买盘ID获取买盘数据
        		float buyPrice=buy.getPrice();//价格
        		int  buyPriceType=buy.getPriceType();//类型
        		if(buyPriceType==poolPrice.getPriceType()){
        			if(buyPrice==poolPrice.getPrice()){
        				buyPool.setId(poolId);
        				buyPool.setIsConfirm(1);
        				iBuyPoolPBO.updateSellPoolPrice(buyPool);//更新买盘数据
        			}
        		}
        	}
        	//更新还盘数据
		    poolPrice.setUpdatedAt( new Date());
		    iPoolPriceBo.updatePool(poolPrice);*/
    	User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
        Map<String, Object> map = iBusinessBO.addCounter(poolPrice, user.getItems().getId());
        return new ResultMessage((String) map.get("status"), (String) map.get("msg"));
    }
    
	
	/**
	 * 关闭
	 * @param request
	 * @return
	 */
    @RequestMapping("deleteById")
	@ResponseBody
    public Object deleteById(HttpServletRequest request){
    	try {
    		Integer id=Integer.parseInt(request.getParameter("id"));//还盘ID
    		Integer poolId=Integer.parseInt(request.getParameter("poolId"));//报盘ID
    		Integer poolType=Integer.parseInt(request.getParameter("poolType"));//报盘类型 1 卖盘  2 买盘
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", id);
			map.put("poolId", poolId);
			map.put("poolType", poolType);
			iPoolPriceBo.getPoolPriceByMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ResultMessage.DELETE_SUCCESS_RESULT;
    }
    
    /** 修改报盘价格时，实价、升、贴价的校验
     * @Title: updatePoolBlur 
     * @Description: TODO
     * @param price
     * @param priceType
     * @param poolId
     * @return
     * @author zhangnan/张楠
     * @return: String
     * @createTime 2015年12月10日
     */
    @RequestMapping("updatePoolBlur")
    @ResponseBody
    public String updatePoolBlur(String price,Integer priceType,Integer poolId,Integer poolType,Integer id,Integer customerId) {
    	Map<String,Object> param = new HashMap<String, Object>();
    	param.put("price", price);
    	param.put("priceType", priceType);
    	param.put("poolId", poolId);
    	param.put("poolType", poolType);
    	param.put("poolPriceId", id);
    	param.put("customerId", customerId);
    	ChCustomsEntity custom = this.iPoolPriceBo.queryChCustomsEntityById(param);
    	param.put("account", custom.getAccount());
    	String flag = "";
    	try {
    		flag = this.iBusinessBO.updatePoolBlur(param);
    		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
    	return flag;
	}

}
