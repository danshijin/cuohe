package job.dao.CDBHelperImpl;

import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.smm.cuohe.domain.CommodityAttr;

import job.dao.CDBHelper;

public class CDBHelperCommodityAttr extends CDBHelper {
	private static final Logger logger = Logger.getLogger(CDBHelperCommodityAttr.class);
	private static CDBHelper db2 = null;
	private static String sql = null;

	public CDBHelperCommodityAttr() {
		super();
	}

	public CDBHelperCommodityAttr(String sql) {
		super(sql);
	}

	@Override
	public Object getEntity() {
		return new CommodityAttr();
	}

	@Override
	public Object validateDate(Object data, Object obj, String column) throws ParseException {

		CommodityAttr commodityAttr = (CommodityAttr) obj;

		if (column.equalsIgnoreCase("C_ID")) {
			if (String.valueOf(data) == null) {
				logger.info("ID为空");
				return null;
			}
			commodityAttr.setId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_COMMODITYID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("commodityid为空");
				return null;
			}
			commodityAttr.setCommodityId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_ATTRID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("attrid为空");
				return null;
			}
			commodityAttr.setAttrId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_ATTRNAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("attrname为空");
				return null;
			}
			commodityAttr.setAttrName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_ATTRVALUE")) {
			if (String.valueOf(data) == null) {
				logger.info("attrvalue为空");
				return null;
			}
			commodityAttr.setAttrValue(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_COMMODITYNAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("commodityname为空");
				return null;
			} 
			commodityAttr.setCommodityName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CREATETIME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("createtime为空");
				return null;
			} 
			commodityAttr.setCreateTime(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_EDITTIME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("edittime为空");
			} 
			commodityAttr.setEditTime(String.valueOf(data));
		}
		
		return commodityAttr;
	}

	@Override
	public void addvalidate(Object obj) {
		CommodityAttr commodityAttr = (CommodityAttr) obj;

		sql = "insert into ch_commodity_attr (id,commodity_id,attr_id,attr_name,attr_value,commodity_name,create_time,edit_time) values(?,?,?,?,?,?,?,?)";
		db2 = new CDBHelperCommodityAttr(sql);
		try {
			db2.pst.setInt(1, commodityAttr.getId());
			db2.pst.setInt(2, commodityAttr.getCommodityId());
			db2.pst.setInt(3, commodityAttr.getAttrId());
			db2.pst.setString(4, commodityAttr.getAttrName());
			db2.pst.setString(5, commodityAttr.getAttrValue());
			db2.pst.setString(6, commodityAttr.getCommodityName());
			db2.pst.setString(7, commodityAttr.getCreateTime());
			db2.pst.setString(8, commodityAttr.getEditTime());

			db2.pst.execute();
			db2.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void cleanupData() {
		sql = "truncate table ch_commodity_attr ";
		db2 = new CDBHelperCommodityAttr(sql);
		try {
			db2.pst.execute();
			db2.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
