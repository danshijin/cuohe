package com.smm.cuohe.bo.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IPoolPriceBo;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.bo.users.IBuyPoolPBO;
import com.smm.cuohe.dao.IPoolPriceDao;
import com.smm.cuohe.dao.base.ChCustomsEntityMapper;
import com.smm.cuohe.domain.BuyPoolPOJO;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.base.ChCustomsEntity;
import com.smm.cuohe.domain.dealmake.SellPool;



@Service
public class IPoolPriceBoImpl implements IPoolPriceBo{
	
	Logger logger = Logger.getLogger(IPoolPriceBoImpl.class);
	
	@Autowired 
	private IPoolPriceDao iPoolPriceDao;
	
	@Autowired
	private ISellPoolBO iSellPoolBO;

	@Autowired
	private IBuyPoolPBO iBuyPoolPBO;
	
	@Autowired
	private ChCustomsEntityMapper customsBo;
	
	@Override
	public List<PoolPrice> getPoolPrice(Map<String, Object>map) {
		List<PoolPrice> list=null;
		try {
			list=iPoolPriceDao.queryIPoolPrice(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return list;
	}

	@Override
	public void deleteById(Integer id) {
		try {
			iPoolPriceDao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PoolPrice getPoolPriceById(Integer id) {
		PoolPrice poolPrice=null;
		try {
			poolPrice=iPoolPriceDao.getPoolPriceById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poolPrice;
	}

	@Override
	public void updatePool(PoolPrice poolPrice) {
		try {
			iPoolPriceDao.updatePool(poolPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void getPoolPriceByMap(Map<String, Object> map) {
		try {
			Integer id=Integer.parseInt(map.get("id").toString());//还盘ID
    		Integer poolId=Integer.parseInt(map.get("poolId").toString());//报盘ID
    		Integer poolType=Integer.parseInt(map.get("poolType").toString());//报盘类型 1 卖盘  2 买盘
    		PoolPrice poolPrice=iPoolPriceDao.getPoolPriceByMap(map);
			PoolPrice pool=iPoolPriceDao.getPoolPriceByMap_one(map);//获取删除后的最新数据
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		if(poolType==1){//报盘类型为卖盘
    			SellPool sell=iSellPoolBO.getSellPoolByPoolId(poolId);//根据卖盘ID获取卖盘数据
    			SellPool sellPool=new SellPool();
				sellPool.setId(poolId);//set主键值
    			if(id.toString().equals(poolPrice.getId().toString())){
    				if(pool!=null){
    					sellPool.setLastPriceType(pool.getPriceType());//价格类型 0 实价  1 升水 2 贴水
    					sellPool.setLastPrice(pool.getPrice());//最后还盘报价
    					sellPool.setLastCustomerId(pool.getCustomerId());//最后报价客户编号
    					sellPool.setLastMallAccount(pool.getAccount());//最后还价商城客户编号
    					sellPool.setLastCustomerName(pool.getCustomerName());//客户名称
    					sellPool.setLastTime(formatter.format(pool.getUpdatedAt())); //最后还盘时间
    				}else{
    					sellPool.setLastPriceType(0);//价格类型 0 实价  1 升水 2 贴水
    					sellPool.setLastPrice(0.0);//最后还盘报价
    					sellPool.setLastCustomerId(null);//最后报价客户编号
    					sellPool.setLastMallAccount("");//最后还价商城客户编号
    					sellPool.setLastCustomerName("");//客户名称
    					sellPool.setLastTime(null); //最后还盘时间
    				}     
    				sellPool.setPoolPriceCount(sell.getPoolPriceCount()-1);//还盘总数量-1
        			sellPool.setIsConfirm(0);//是否已成交 0 未成交 1 已成交
        			iSellPoolBO.updateSellPoolPrice(sellPool);//更新数据
    			}
    		}else if(poolType==2){//报盘类型为买盘
    			BuyPoolPOJO buy=iBuyPoolPBO.getBuyPool(poolId.toString());//获取买盘ID获取买盘数据
    			BuyPoolPOJO buyPool=new BuyPoolPOJO();
    			buyPool.setId(poolId);
    			if(id.toString().equals(poolPrice.getId().toString())){
    				if(pool != null){
    					buyPool.setLastPriceType(pool.getPriceType());//价格类型 0 实价  1 升水 2 贴水
    					buyPool.setLastPrice(pool.getPrice());//最后还盘报价
    					buyPool.setLastCustomerId(pool.getCustomerId());//最后报价客户编号
    					buyPool.setLastMallAccount(pool.getAccount());//最后还价商城客户编号
    					buyPool.setLastCustomerName(pool.getCustomerName());//客户名称
    					buyPool.setLastTime(formatter.format(pool.getUpdatedAt())); //最后还盘时间
    				}else{
    					buyPool.setLastPriceType(0);//价格类型 0 实价  1 升水 2 贴水
    					buyPool.setLastPrice(0.0);//最后还盘报价
    					buyPool.setLastCustomerId(null);//最后报价客户编号
    					buyPool.setLastMallAccount("");//最后还价商城客户编号
    					buyPool.setLastCustomerName("");//客户名称
    					buyPool.setLastTime(null); //最后还盘时间
    				}
    				buyPool.setPoolPriceCount(buy.getPoolPriceCount()-1);//还盘总数量-1
        			buyPool.setIsConfirm(0);//是否已成交 0 未成交 1 已成交
        			iBuyPoolPBO.updateSellPoolPrice(buyPool);//更新数据
    			}
    		}
    		iPoolPriceDao.deleteById(id);//删除还盘数据
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PoolPrice getPoolPriceByMap_one(Map<String, Object> map) {
		PoolPrice poolPrice=null;
		try {
			poolPrice=iPoolPriceDao.getPoolPriceByMap_one(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poolPrice;
	}

	@Override
	public PoolPrice getPoolPriceByPoolId(Map<String, Object> map) {
		PoolPrice poolPrice=null;
		try {
			poolPrice=iPoolPriceDao.getPoolPriceByPoolId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poolPrice;
	}

	@Override
	public ChCustomsEntity queryChCustomsEntityById(
			Map<String, Object> param) {
		return this.customsBo.selectByPrimaryKey((Integer)param.get("customerId"));
	}
	
}
