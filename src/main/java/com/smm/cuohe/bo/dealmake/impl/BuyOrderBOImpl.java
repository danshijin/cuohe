package com.smm.cuohe.bo.dealmake.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.dealmake.IBuyOrderBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.bo.notice.NoticeBo;
import com.smm.cuohe.dao.IBusinessDAO;
import com.smm.cuohe.dao.base.ChProductEntityMapper;
import com.smm.cuohe.dao.dealmake.IBuyOrderDao;
import com.smm.cuohe.dao.dealmake.ISellPoolDao;
import com.smm.cuohe.dao.notice.NoticeDao;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.base.ChProductEntity;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.domain.dealmake.ToOrder;
import com.smm.cuohe.domain.dealmake.Warehouse;
import com.smm.cuohe.util.URLRequest;

@Service
public class BuyOrderBOImpl  implements IBuyOrderBO{
	
	private Logger logger = Logger.getLogger(BuyOrderBOImpl.class);
	
	@Resource
	private IBuyOrderDao iBuyOrderDao;
	
	@Autowired
	private ISellPoolBO iSellPoolBO;
	
	@Value("#{ch['updatePool.URL']}")
	private String updatePoolUrl;
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired 
	private ChProductEntityMapper chProductEntityMapper;
	
	@Resource
	IBusinessDAO iBusinessDao;
	
	@Autowired
	private NoticeBo noticeBo;

	@Resource
	private ISellPoolDao sellPoolDao;
	
	/** 新增需求，订单生成之后，通知买家“确认订单！”
	 * @Title: insertBuyOrder 
	 * @Description: TODO
	 * @param o
	 * @author zhangnan/张楠
	 * @createTime 2016年1月4日上午10:05:13
	 */
	@Override
	public void insertBuyOrder(Order o)throws RuntimeException{
		iBuyOrderDao.insertBuyOrder(o);
		
		//商品名字
		String productNames = "";
		System.out.println(productNames.length());
		String[] productids = o.getProductid();
		if (productids != null && productids.length >  0) {
			for (int i = 0; i < productids.length; i++) {
				//产品ID
				Integer productid = Integer.parseInt(productids[i]);
				//报盘类型
				//Integer poolType = o.getPoolType();
				ChProductEntity chProductEntity = this.chProductEntityMapper.selectByPrimaryKey(productid);

				productNames = productNames+chProductEntity.getName()+",";
			}
			productNames = productNames.substring(0, productNames.length()-1);
		}
		//订单生成后，通知买家
		Map<String,Object> map = new HashMap<String, Object>();
		//商城账户
		
		map.put("createdat", new Date());
		
		map.put("isread", 1);
		
		map.put("noticetype", 1);//事件类型（ 1 订单成交、2 价格失败、 3 交易失败、 4 交易关闭、5 新出价）
		
		map.put("status", 0);
		
		map.put("updatedat", new Date());
		
		if (o.getPoolType() == 1) {
			map.put("account", o.getMallBuyerAccount());
			map.put("noticetext","成交通知:您对报盘  "+productNames+"  的出价已和卖家相符，请尽快确认订单。订单号： "+o.getOrderCode());
			this.noticeDao.addNotice(map);
			map.put("account", o.getMallSellerAccount());
			map.put("noticetext","成交通知:您发出的报盘  "+productNames+" 已经有买家出价相符，请尽快确认订单。订单号： "+o.getOrderCode());
			this.noticeDao.addNotice(map);
		}else {
			map.put("account", o.getMallSellerAccount());
			map.put("noticetext","成交通知:您对报盘  "+productNames+"  的出价已和买家相符，请尽快确认订单。订单号： "+o.getOrderCode());
			this.noticeDao.addNotice(map);
			map.put("account", o.getMallBuyerAccount());
			map.put("noticetext","成交通知:您发出的报盘  "+productNames+" 已经有卖家出价相符，请尽快确认订单。订单号： "+o.getOrderCode());
			this.noticeDao.addNotice(map);
		}
		Integer poolId = o.getPoolId();
		
		if (poolId != null && !poolId.toString().equals("")) {
			noticeBo.transactionFail(o.getPoolId(),o.getPoolType());
		}
	}
	
	@Override
	public void insertSubOrder(SubOrder so) {
		iBuyOrderDao.insertBuySubOrder(so);
	}
	
	@Override
	public List<Warehouse> selWarehouseByArea(String companyName,Integer areaId,String itemId) {
		List<Warehouse> list = iBuyOrderDao.selWarehouseByArea(companyName,areaId,itemId);
		return list;
	}
	
	@Override
	public Integer getSellPoolIdByChatIdForConversation(Integer chatId){
		return iBuyOrderDao.getSellPoolIdByChatIdForConversation(chatId);
	}

	@Override
	public List<ItemAttr> getMainItemAttr(Integer itemId) {
		List<ItemAttr> list = iBuyOrderDao.getMainItemAttr(itemId);
		return list;
	}

	@Override
	public void insAttrValues(AttrValue a) {
		iBuyOrderDao.insAttrValues(a);
	}

	
	@Override
	public ToOrder selCustomById(Integer cusId) {
		ToOrder t=iBuyOrderDao.selCustomById(cusId);
		return t;
	}
	
	//根据产品Id得到产品名称
	@Override
	public String selProNameById(Integer id) {
		return iBuyOrderDao.selProNameById(id);
	}

	
	
	@Override
	public void updateBuyIsClose(Integer id) {
		iBuyOrderDao.updateBuyIsClose(id);
	}
	
	@Override
	public void updateBuyIsCloseAndIsConfirm(Integer id) {
		iBuyOrderDao.updateBuyIsCloseAndIsConfirm(id);
	}

	@Override
	public void updateSellIsClose(Integer id) {
		iBuyOrderDao.updateSellIsClose(id);
	}
	
	@Override
	public void updateSellIsStatus(Integer id) {
		iBuyOrderDao.updateSellIsStatus(id);
	}

	@Override
	public Integer queryMallSaleIdByPoolId(Integer poolId) {
		return iBuyOrderDao.queryMallSaleIdByPoolId(poolId);
	}

	@Override
	public Customer queryCompanyInfo(Integer id) {
		return iBuyOrderDao.queryCompanyInfo(id);
	}
    //修改报盘
	@Override
	public boolean updatePool(SellPool sell, Object mailsaleId) {
		// TODO Auto-generated method stub
		//调用商城更新接口
		if(updatePoolByMall(sell, mailsaleId)&&updatePoolByLocal(sell))return true;
		else return false;
	}
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean updatePoolByLocal(SellPool sell){
		SellPool sellpool = new SellPool();
		sellpool.setId(sell.getId());
		sellpool.setDeliverytime(sell.getDeliverytime());
		sellpool.setWareProvince(sell.getWareProvince());
		sellpool.setWareId(sell.getWareId());
		sellpool.setReceipttype(sell.getReceipttype());
		sellpool.setOverflow(sell.getOverflow());
		sellpool.setPaytype(sell.getPaytype());
		sellpool.setDelivery(sell.getDelivery());
		sellpool.setMallSaleId((sell.getMallSaleId()==null)?null:sell.getMallSaleId());
		return iBuyOrderDao.updateSell(sellpool)>0;
	}
	
	//调用商城更新接口
	public boolean updatePoolByMall(SellPool sell, Object mailsaleId){
		String unix ="";
		try {
		unix = df.parse(sell.getDeliverytime()).getTime() +"";
		
		} catch (ParseException e) {
		e.printStackTrace();
		logger.error("=========>时间转换异常");
		return false;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("mallid", mailsaleId.toString());
		params.put("deliverytime", unix.substring(0,unix.length()-3));
		params.put("areaid", sell.getWareProvince().toString());
		params.put("warehouseid", sell.getWareId().toString());
		params.put("receipttype", sell.getReceipttype().toString());
		params.put("overflow", sell.getOverflow().toString());
		params.put("paytype", sell.getPaytype().toString());
		params.put("delivery", sell.getDelivery().toString());
		
		JSONObject result = null;
		String result_str = "";
	    
		try {
			logger.info("开始调用：修改商城报盘接口   参数 ："+params.toString());
			
			result_str = URLRequest.post(updatePoolUrl, params);
			logger.info("调用的返回值为===============>"+result_str);
			result = JSONObject.fromObject(result_str);
			if (result != null) {
				logger.info("修改商城报盘返回数据"+result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用修改商城报盘接口错误");
			return false;
		}
		if(result != null){
			Object errNo = result.get("errno");
			if("255".equals(errNo))return false;
			else return true;
		}else{
			logger.error("=====================>返回接口错误");
			return false;
		}
		
	}

	
	public static void main(String[] args) {
		String date = String.valueOf(new Date().getTime());
		System.out.println(date.substring(0,date.length()-3));
	}

	@Override
	public void updateBuyPoolCreateSource(BuyPoolEntity buyPool) {
		this.iBuyOrderDao.updateBuyPoolCreateSource(buyPool);
		
	}

	@Override
	public List<Warehouse> selWarehouseByAreaBlur(Map<String, Object> param) {
		List<Warehouse> list = iBuyOrderDao.selWarehouseByAreaBlur(param);
		return list;
	}
	
	@Override
	public int updateSellPoolToClose(int sellPoolId){
		return iBuyOrderDao.updateSellPoolToClose(sellPoolId);
	}

	@Override
	public void sellPoolClose(int poolid) {
		// TODO Auto-generated method stub
		SellPool sellPool = iSellPoolBO.getSellPoolByPoolId(poolid);
		if(sellPool.getCreateSource().intValue()==2){
			updateSellPoolToClose(sellPool.getId());
		}
	}

	@Override
	public void updateSellIsCloseAndIsConfirm(Integer id) {
		this.sellPoolDao.updateSellIsCloseAndIsConfirm(id);
	}
	
}



