package com.smm.cuohe.bo.dealmake.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.dao.IBusinessDAO;
import com.smm.cuohe.dao.dealmake.BuypoolDao;
import com.smm.cuohe.domain.AttrValue;
import com.smm.cuohe.domain.BuyPoolAttr;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.dealmake.BuyPoolEntity;
import com.smm.cuohe.domain.dealmake.BuypublishEntity;
import com.smm.cuohe.domain.dealmake.ItemsArrtEntity;
import com.smm.cuohe.domain.dealmake.SellPool;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.JSONUtil;


/*
 * auth tantaigen
 * 
 */
@Service
public class Buypoolboimpl implements BuyPoolBo  {

	private Logger logger = Logger.getLogger(Buypoolboimpl.class);
	
	@Resource BuypoolDao buypoolDao;
	@Value("#{ch['changeOfferPrice.URL']}")
	private String changeOfferPrice;
	@Resource
	private RestTemplate restTemplate;
	
	@Resource
	IBusinessDAO iBusinessDao;

	@Override
	public List<BuyPoolEntity> buypoollist(Map<String, Object> map) {

		List<BuyPoolEntity> list=buypoolDao.buypoollist(map);
		return list;
	}

	@Override
	public List<Customer> companylist() {
		List<Customer> list=buypoolDao.companylist();
		return list;
	}

	@Override
	public int delbuypool(Integer id) {
		int b=buypoolDao.delbuypool(id);
		return b;
	}

	@Override
	public int addbuyPool(BuyPoolEntity buy) {
		return buypoolDao.addbuyPool(buy);
		
	}

	@Override
	public void buypublish(BuypublishEntity buypub) {
		buypoolDao.buypublish(buypub);
		
	}

	@Override
	public List<ItemsArrtEntity> queryItemsArrt(Map<String, String> map) {
		List<ItemsArrtEntity> list=buypoolDao.queryItemsArrt(map);
		return list;
	}

	@Override
	public BuyPoolEntity getbuypoolID(Integer id) {
		
		return buypoolDao.getbuypoolID(id);
	}


	@Override
	public List<BuyPoolEntity> getBuyPoolExportData(String[] array) {
		
		List<BuyPoolEntity> list=this.buypoolDao.getBuyPoolExportData(array);
		
		for (BuyPoolEntity c : list) {
			if(c.getCustomerId() != null)
				c.setIsCustomer("是");
			else
				c.setIsCustomer("否");
		}
		
		return list;
	}

	@Override
	public void addArrtvalue(AttrValue attr) {
		buypoolDao.addArrtvalue(attr);
		
	}
	
	@Override
	public void addBuyPoolAttr(BuyPoolAttr attr) {
		buypoolDao.addBuyPoolAttr(attr);
	}

	@Override
	public List<AttrValue> queryAttrvalue(String guid) {
		// TODO Auto-generated method stub
		return buypoolDao.queryAttrvalue(guid);
	}

	@Override
	public Integer countbuypool( Map<String , Object> map) {
		// TODO Auto-generated method stub
		return buypoolDao.countbuypool(map);
	}

	@Override
	public int probuypool(Integer id) {
		// TODO Auto-generated method stub
		return buypoolDao.probuypool(id);
	}
	
	@Override
	public int getMaxIdfromBuypool() {
		return buypoolDao.getMaxIdfromBuypool();
	}

	@Override
	public List<AttrValue> selbuyAttrValueByBuyId(Integer buyId) {
		return buypoolDao.selbuyAttrValueByBuyId(buyId);
	}

	@Override
	public void updateBuyPool(BuyPoolEntity buy)throws RuntimeException {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("poolId", buy.getId());
		List<PoolPrice> list = this.iBusinessDao.queryCurrentMaxPoolPrice(map);
		PoolPrice poolPrice = null ;
		Double buyPrice = Double.parseDouble(buy.getPrice());
		if (list != null && list.size() > 0) {
			poolPrice = list.get(0);
			if (poolPrice.getPrice() >= buyPrice) {
				buy.setIsConfirm(1);
			}
		}
		buypoolDao.updateBuyPool(buy);
		
	}

	@Override
	public Map<String, Object> updateMallPrice(Integer sortNum,double price ,Integer poolType ,String mallid,String quantity) {
		   Map<String, Object> map=new HashMap<>();
		   MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

			param.add("mallid", mallid);
			param.add("price", price);
			param.add("type", poolType);
			param.add("amount", quantity);
			
			String nowTimeTemp = DateUtil.doFormatDate(new Date(), "yyyy-MM-dd");
			
			nowTimeTemp = nowTimeTemp.replace("-", "")+""+sortNum;
			
			param.add("matchorder", nowTimeTemp);

			try {
				logger.info("开始调用：修改报盘同步商城接口  参数 ："+param.toString());
				
				String jsonString = restTemplate.postForObject(changeOfferPrice, param, String.class);
				Map<String, Object> maps = JSONUtil.doConvertJson2Map(jsonString);
				if (maps != null) {
					logger.info("修改报盘同步商城返回数据"+maps.toString());
					String errno=maps.get("errno").toString();
					String msg=maps.get("msg").toString();
					if(Integer.valueOf(errno)==1){
						map.put("code", "ok");
						map.put("msg", "成功");
					}else {
						map.put("code", "erron");
						map.put("msg", msg);
					}
				}
			}catch(Exception e){
				logger.error("调用修改报盘同步商城接口出错");
				e.printStackTrace();
				map.put("code", "erron");
				map.put("msg", "同步商城接口异常");
			}
			 return map;
	}

	@Override
	public BuyPoolEntity getbuypoolById(Integer id) {
		// TODO Auto-generated method stub
		return buypoolDao.getbuypoolById(id);
	}
}
