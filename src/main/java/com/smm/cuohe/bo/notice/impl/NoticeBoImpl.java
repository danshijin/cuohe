package com.smm.cuohe.bo.notice.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IBusinessBO;
import com.smm.cuohe.bo.notice.NoticeBo;
import com.smm.cuohe.dao.IBusinessDAO;
import com.smm.cuohe.dao.IPoolPriceDao;
import com.smm.cuohe.dao.base.ChBuyPoolEntityMapper;
import com.smm.cuohe.dao.base.ChSellPoolEntityMapper;
import com.smm.cuohe.dao.base.ChSysNoticeEntityMapper;
import com.smm.cuohe.dao.notice.NoticeDao;
import com.smm.cuohe.domain.PoolPrice;
import com.smm.cuohe.domain.base.ChCommodityEntity;
import com.smm.cuohe.domain.base.ChSysNoticeEntity;
import com.smm.cuohe.util.DateUtil;

@Service
public class NoticeBoImpl implements NoticeBo {
	
	@Autowired
	private ChSysNoticeEntityMapper noticeDao;
	
	@Autowired
	private NoticeDao noticeDao2;
	
	@Autowired 
	private IPoolPriceDao poolPriceDao;
	
	@Autowired
	private IBusinessBO businessBO;
	
	@Autowired
	IBusinessDAO iBusinessDao;
	
	@Autowired
	private ChSellPoolEntityMapper sellDao;
	
	@Autowired
	private ChBuyPoolEntityMapper buyDao;
	
	@Override
	public void orderSubNotify(Integer poolid, Integer poolType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void priceInvalidNotify(Map<String,Object> map) {
		
		map.put("createdat", new Date());
		
		map.put("isread", 1);
		
		map.put("noticetype", 2);//事件类型（ 1 订单成交、2 价格失败、 3 交易失败、 4 交易关闭、5 新出价）
		
		map.put("status", 0);
		
		map.put("updatedat", new Date());
		
		
		
		if (map.get("poolType").toString().equals("1")) {
			//商城账户
			map.put("noticetext","超价通知:您对报盘 "+map.get("productName")+" 的出价已被其他买家超过，请及时修改价格，以免错失好货！");
		}else {
			//商城账户
			map.put("noticetext","超价通知:您对报盘 "+map.get("productName")+" 的出价已被其他卖家超过，请及时修改价格，以免错失好货！");
		}
		
		this.noticeDao2.addNotice(map);
	}

	@Override
	public void transactionFail(Integer poolid, Integer poolType) {
		try {
			//根据报盘类型、报盘ID获取还盘用户
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("poolId", poolid);
			map.put("poolType", poolType);
			String ing="";
			if(poolType.toString().equals("1")){//卖盘
				ChCommodityEntity entity=iBusinessDao.queryChCommodityEntity(map);
				ing="交易失败通知:您参与报盘的"+entity.getName()+"已被其他买家抢先成交,请下次再接再厉!";
			}else{
				ChCommodityEntity entity=iBusinessDao.queryChCommodityEntity_BuyPool(map);
				ing="交易失败通知:您参与报盘的"+entity.getName()+"已被其他卖家抢先成交,请下次再接再厉!";
			}
			List<PoolPrice> list=poolPriceDao.getPoolPriceList(map);
			if(list !=null && list.size()>1){
				StringBuffer sdf=new StringBuffer();
				sdf.append("insert into ch_sys_notice (noticetext,createdAt,isRead,account,noticeType,status) values ");
				for(int i=1;i<list.size();i++){//去掉最后一次成交报价记录,新增其他用户还盘系统日志
					sdf.append("('"+ing+"','");
					sdf.append(DateUtil.doFormatDate(new Date(),"")+"',");
					sdf.append(1+",");
					sdf.append("'"+list.get(i).getAccount()+"',");
					sdf.append(3+",");
					sdf.append(0+"),");
				}
				String str=sdf.substring(0,sdf.toString().length()-1)+"";
				Map<String, Object> maper=new HashMap<String, Object>();
				maper.put("sql", str);
				poolPriceDao.insertnoticeList(maper);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void transactionClose(Integer poolid, Integer poolType) {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("poolId", poolid);
			map.put("poolType", poolType);
			String ing="";
			if(poolType.toString().equals("1")){//卖盘
				ChCommodityEntity entity=iBusinessDao.queryChCommodityEntity(map);
				ing="交易关闭通知:您参与报盘的"+entity.getName()+"已被卖家关闭交易";
			}else{
				ChCommodityEntity entity=iBusinessDao.queryChCommodityEntity_BuyPool(map);
				ing="交易关闭通知:您参与报盘的"+entity.getName()+"已被买家关闭交易";
			}
			//根据报盘类型、报盘ID获取还盘用户
			List<PoolPrice> list=poolPriceDao.getPoolPriceList(map);
			StringBuffer sdf=new StringBuffer();
			if(list !=null && list.size()>0){
				sdf.append("insert into ch_sys_notice (noticetext,createdAt,isRead,account,noticeType,status) values ");
				for(PoolPrice pool:list){
					sdf.append("('"+ing+"','");
					sdf.append(DateUtil.doFormatDate(new Date(),"")+"',");
					sdf.append(1+",");
					sdf.append("'"+pool.getAccount()+"',");
					sdf.append(4+",");
					sdf.append(0+"),");
				}
				String str=sdf.substring(0,sdf.toString().length()-1)+"";
				Map<String, Object> maper=new HashMap<String, Object>();
				maper.put("sql", str);
				poolPriceDao.insertnoticeList(maper);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void newOffer(PoolPrice poolPrice)throws RuntimeException{
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("poolId", poolPrice.getPoolId());
			map.put("poolType", poolPrice.getPoolType());
			//获取报盘的所属企业
			if(poolPrice.getPoolType().toString().equals("1")){//卖盘
				map.put("account",sellDao.selectByPrimaryKey(Integer.valueOf(map.get("poolId").toString())).getMallUserAccount());
			}else{
				map.put("account",buyDao.selectByPrimaryKey(Integer.valueOf(map.get("poolId").toString())).getMallUserAccount());
			}
				String ing="";
					ChCommodityEntity entity = null;
					if(poolPrice.getPoolType().toString().equals("1")){//卖盘
						 entity=iBusinessDao.queryChCommodityEntity(map);
					}else{
						 entity=iBusinessDao.queryChCommodityEntity_BuyPool(map);
					}
					String str="";
					if(poolPrice.getPoolType().toString().equals("1")){//卖盘
						str="有买家出价";
					}else if(poolPrice.getPoolType().toString().equals("2")){//买盘
						str="有卖家出价";
					}
					ing="您发起的报盘"+entity.getName()+str;
					map.put("ing", ing);
					ChSysNoticeEntity record = new ChSysNoticeEntity();
					record.setAccount(map.get("account").toString());
					record.setCreatedat(new Date());
					record.setIsread(1);
					record.setNoticetext(ing);
					record.setNoticetype(5);
					record.setStatus(0);
					record.setUpdatedat(new Date());
					noticeDao.insertSelective(record);
		}
		

}
