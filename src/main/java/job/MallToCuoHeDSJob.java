package job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import job.dao.CDBHelper;
import job.dao.MDBHelper;
import job.dao.CDBHelperImpl.CDBHelperWare;
import job.dao.MDBHelperImpl.MDBHelperWare;

import org.apache.log4j.Logger;

import com.smm.cuohe.util.PropertiseUtil;

public class MallToCuoHeDSJob {

	private static final Logger logger = Logger.getLogger(MallToCuoHeDSJob.class);

	private static PreparedStatement pst = null;
	private static ResultSet ret = null;
	private static List<HashMap<String, Object>> maps = new ArrayList<>();

	// 商城仓库表列名
	private static String M_COLUMN = null;
	// 对应撮合仓库表列名
	private static String C_COLUMN = null;

	public static void MallToCuoHeDS(MDBHelper mdbHelper, CDBHelper cdbHelper) throws ParseException {

		logger.info("商城撮合系统同步处理开始");

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
	}

	public static List<HashMap<String, Object>> getMaps(List<HashMap<String, Object>> maps) {
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
			} // 显示数据
			ret.close();
			pst.close();// 关闭连接

			return maps;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		try {
			// 同步仓库表
			MallToCuoHeDSJob.MallToCuoHeDS(new MDBHelperWare(), new CDBHelperWare());

			// 同步地区表
			//MallToCuoHeDSJob.MallToCuoHeDS(new MDBHelperArea(), new CDBHelperArea());
			
			// 同步产品表
			//MallToCuoHeDSJob.MallToCuoHeDS(new MDBHelperProduct(), new CDBHelperProduct());
			
			// 同步商品表
			//MallToCuoHeDSJob.MallToCuoHeDS(new MDBHelperCommodity(), new CDBHelperCommodity());
			
			// 同步商品属性表
			//MallToCuoHeDSJob.MallToCuoHeDS(new MDBHelperCommodityAttr(), new CDBHelperCommodityAttr());
			
			// 同步品目属性表
			//MallToCuoHeDSJob.MallToCuoHeDS(new MDBHelperItemAttr(), new CDBHelperItemAttr());
			
			
			// 同步品目表 （暂 不 支 持 功 能）
			//MallToCuoHeDSJob.MallToCuoHeDS(new MDBHelperItems(), new CDBHelperItems());
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
