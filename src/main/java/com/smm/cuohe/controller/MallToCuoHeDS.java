package com.smm.cuohe.controller;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import job.dao.CDBHelper;
import job.dao.MDBHelper;
import job.dao.CDBHelperImpl.CDBHelperArea;
import job.dao.CDBHelperImpl.CDBHelperCommodity;
import job.dao.CDBHelperImpl.CDBHelperCommodityAttr;
import job.dao.CDBHelperImpl.CDBHelperItemAttr;
import job.dao.CDBHelperImpl.CDBHelperItems;
import job.dao.CDBHelperImpl.CDBHelperProduct;
import job.dao.CDBHelperImpl.CDBHelperWare;
import job.dao.MDBHelperImpl.MDBHelperArea;
import job.dao.MDBHelperImpl.MDBHelperCommodity;
import job.dao.MDBHelperImpl.MDBHelperCommodityAttr;
import job.dao.MDBHelperImpl.MDBHelperItemAttr;
import job.dao.MDBHelperImpl.MDBHelperItems;
import job.dao.MDBHelperImpl.MDBHelperProduct;
import job.dao.MDBHelperImpl.MDBHelperWare;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.util.PropertiseUtil;

@Controller
@RequestMapping(value="/mallTo")
public class MallToCuoHeDS {

	private static final Logger logger = Logger.getLogger(MallToCuoHeDS.class);

	private static PreparedStatement pst = null;
	private static ResultSet ret = null;

	// 商城仓库表列名
	private static String M_COLUMN = null;
	// 对应撮合仓库表列名
	private static String C_COLUMN = null;

	public static void MallToCuoHe(MDBHelper mdbHelper, CDBHelper cdbHelper){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		logger.info("商城撮合系统同步处理开始");
		try {
			cdbHelper.cleanupData();
			pst = MDBHelper.getselect(mdbHelper.getselectsql());
			maps = getMaps(maps);
			if (maps == null) {
				logger.info("查询商城表数据异常");
				return;
			}
			for (Map<String, Object> m : maps) {
				Object obj = cdbHelper.getEntity();
				for (Map.Entry<String, Object> entry : m.entrySet()) {
					// 商城仓库表列名
					M_COLUMN = entry.getKey();
					// 对应撮合仓库表列名
					C_COLUMN = PropertiseUtil.getDataFromPropertiseFile(mdbHelper.getproperties(), M_COLUMN);
					obj = cdbHelper.validateDate(m.get(M_COLUMN), obj, C_COLUMN);
					if (obj == null) {
						logger.info("数据异常");
						break;
					}
				}
				if (obj == null) {
					logger.info("数据异常");
					continue;
				}
				cdbHelper.addvalidate(obj);
			}
			logger.info("商城撮合系统同步处理结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Map<String, Object>> getMaps(List<Map<String, Object>> maps) throws SQLException {
		try {
			ret = pst.executeQuery();
			ResultSetMetaData resultsetmetadata = ret.getMetaData();
			int columnCount = resultsetmetadata.getColumnCount();
			while (ret.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					M_COLUMN = resultsetmetadata.getColumnLabel(i);
					map.put(M_COLUMN, ret.getObject(M_COLUMN));
				}
				maps.add(map);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ret.close();
			pst.close();
		}
		return maps;
	}
	
	/**
	 * 数据同步页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getSynchro")
	public ModelAndView getPoolPrice(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/poolprice/allocation");
		return mv;
	} 
	
	/**
	 * 同步仓库表
	 * @param request
	 * @return
	 */
    @RequestMapping("synchroWare")
	@ResponseBody
    public Object synchroWare(HttpServletRequest request){
    	try {
    		MallToCuoHeDS.MallToCuoHe(new MDBHelperWare(), new CDBHelperWare());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SYNCHRO_FAIL_RESULT;
		}
    	return ResultMessage.SYNCHRO_SUCCESS_RESULT;
    }
    
    /**
	 * 同步地区表
	 * @param request
	 * @return
	 */
    @RequestMapping("synchroArea")
	@ResponseBody
    public Object synchroArea(HttpServletRequest request){
    	try {
    		MallToCuoHeDS.MallToCuoHe(new MDBHelperArea(), new CDBHelperArea());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SYNCHRO_FAIL_RESULT;
		}
    	return ResultMessage.SYNCHRO_SUCCESS_RESULT;
    }
    
    /**
	 * 同步产品表
	 * @param request
	 * @return
	 */
    @RequestMapping("synchroProduct")
	@ResponseBody
    public Object synchroProduct(HttpServletRequest request){
    	try {
    		MallToCuoHeDS.MallToCuoHe(new MDBHelperProduct(), new CDBHelperProduct());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SYNCHRO_FAIL_RESULT;
		}
    	return ResultMessage.SYNCHRO_SUCCESS_RESULT;
    }
    
    /**
	 * 同步商品表
	 * @param request
	 * @return
	 */
    @RequestMapping("synchroCommodity")
	@ResponseBody
    public Object synchroCommodity(HttpServletRequest request){
    	try {
    		MallToCuoHeDS.MallToCuoHe(new MDBHelperCommodity(), new CDBHelperCommodity());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SYNCHRO_FAIL_RESULT;
		}
    	return ResultMessage.SYNCHRO_SUCCESS_RESULT;
    }
    
    /**
	 * 同步商品属性表
	 * @param request
	 * @return
	 */
    @RequestMapping("synchroCommodityAttr")
	@ResponseBody
    public Object synchroCommodityAttr(HttpServletRequest request){
    	try {
    		MallToCuoHeDS.MallToCuoHe(new MDBHelperCommodityAttr(), new CDBHelperCommodityAttr());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SYNCHRO_FAIL_RESULT;
		}
    	return ResultMessage.SYNCHRO_SUCCESS_RESULT;
    }
    
    /**
	 * 同步品目属性表
	 * @param request
	 * @return
	 */
    @RequestMapping("synchroItemAttr")
	@ResponseBody
    public Object synchroItemAttr(HttpServletRequest request){
    	try {
    		MallToCuoHeDS.MallToCuoHe(new MDBHelperItemAttr(), new CDBHelperItemAttr());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SYNCHRO_FAIL_RESULT;
		}
    	return ResultMessage.SYNCHRO_SUCCESS_RESULT;
    }
    
    /**
	 * 同步品目表 （暂 不 支 持 功 能）
	 * @param request
	 * @return
	 */
    @RequestMapping("synchroItems")
	@ResponseBody
    public Object synchroItems(HttpServletRequest request){
    	try {
    		MallToCuoHeDS.MallToCuoHe(new MDBHelperItems(), new CDBHelperItems());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SYNCHRO_FAIL_RESULT;
		}
    	return ResultMessage.SYNCHRO_SUCCESS_RESULT;
    }

}
