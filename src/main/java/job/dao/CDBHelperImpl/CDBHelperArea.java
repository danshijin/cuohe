package job.dao.CDBHelperImpl;

import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.smm.cuohe.domain.Areas;

import job.dao.CDBHelper;

public class CDBHelperArea extends CDBHelper {
	private static final Logger logger = Logger.getLogger(CDBHelperArea.class);
	private static CDBHelper db2 = null;
	private static String sql = null;

	public CDBHelperArea() {
		super();
	}

	public CDBHelperArea(String sql) {
		super(sql);
	}

	@Override
	public Object getEntity() {
		return new Areas();
	}

	@Override
	public Object validateDate(Object data, Object obj, String column) throws ParseException {

		Areas area = (Areas) obj;

		if (column.equalsIgnoreCase("C_ID")) {
			if (String.valueOf(data) == null) {
				logger.info("ID为空");
				return null;
			}
			area.setId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_NAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("name为空");
				return null;
			}
			area.setName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_PARENTID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("parentid为空");
				return null;
			}
			area.setParentID(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CHILDID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("childid为空");
				return null;
			}
			area.setChildID(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_LISTORDER")) {
			if (String.valueOf(data) == null) {
				logger.info("listorder为空");
				return null;
			}
			area.setListOrder(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_PARENTNAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("parentname为空  == 设置默认值 0");
				area.setParentName("0");
			} else {
				area.setParentName(String.valueOf(data));
			}
		}
		return area;
	}

	@Override
	public void addvalidate(Object obj) {
		Areas areas = (Areas) obj;

		sql = "insert into ch_areas (id,name,parentID,childID,listOrder,parentName) values(?,?,?,?,?,?)";
		db2 = new CDBHelperArea(sql);
		try {
			db2.pst.setInt(1, areas.getId());
			db2.pst.setString(2, areas.getName());
			db2.pst.setString(3, areas.getParentID());
			db2.pst.setString(4, areas.getChildID());
			db2.pst.setInt(5, areas.getListOrder());
			db2.pst.setString(6, areas.getParentName());

			db2.pst.execute();
			db2.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void cleanupData() {
		sql = "truncate table ch_areas";
		db2 = new CDBHelperArea(sql);
		try {
			db2.pst.execute();
			db2.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
