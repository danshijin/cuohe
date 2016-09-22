package com.smm.cuohe.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.ICommodityBO;
import com.smm.cuohe.dao.ICommodityDAO;
import com.smm.cuohe.domain.Commodity;

/**
 * @author  zhaoyutao
 * @version 2015年9月14日 上午9:34:34
 * 类说明
 */
@Service
public class CommodityBO implements ICommodityBO{
	
	@Resource
	ICommodityDAO iCommodityDAO;
	
	
	
}
