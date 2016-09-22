package job.dao.CDBHelperImpl;

import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.smm.cuohe.domain.Items;

import job.dao.CDBHelper;

public class CDBHelperItems extends CDBHelper {
	private static final Logger logger = Logger.getLogger(CDBHelperItems.class);
	private static CDBHelper db2 = null;
	private static String sql = null;

	public CDBHelperItems() {
		super();
	}

	public CDBHelperItems(String sql) {
		super(sql);
	}

	@Override
	public Object getEntity() {
		return new Items();
	}

	@Override
	public Object validateDate(Object data, Object obj, String column) throws ParseException {

		Items items = (Items) obj;
		if (column.equalsIgnoreCase("C_ID")) {
			if (String.valueOf(data) == null) {
				logger.info("ID为空");
				return null;
			}
			items.setId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_NAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("name为空");
				return null;
			}
			items.setName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CHARTID")) {
			if (String.valueOf(data) == null) {
				logger.info("chartid为空");
				return null;
			}
			items.setChartId(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CATEGORYID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("categoryid为空");
				return null;
			}
			items.setCategoryId(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_SHFETYPE")) {
			if (String.valueOf(data) == null) {
				logger.info("shfetype为空");
			}
			items.setSHFEType(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_LISTORDER")) {
			if (String.valueOf(data) == null) {
				logger.info("listorder为空");
				return null;
			}
			items.setListOrder(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_STATUS")) {
			if (String.valueOf(data) == null) {
				logger.info("status为空");
				return null;
			}
			items.setStatus(Integer.parseInt(String.valueOf(data)));
		}
		return items;
	}

	@Override
	public void addvalidate(Object obj) {
		Items items = (Items) obj;

		sql = "insert into ch_items (id,name,chartId,categoryId,SHFEType,listOrder,status) values(?,?,?,?,?,?,?)";
		db2 = new CDBHelperItems(sql);
		try {
			db2.pst.setInt(1, items.getId());
			db2.pst.setString(2, items.getName());
			db2.pst.setString(3, items.getChartId());
			db2.pst.setString(4, items.getCategoryId());
			db2.pst.setString(5, items.getSHFEType());
			db2.pst.setInt(6, items.getListOrder());
			db2.pst.setInt(7, items.getStatus());

			//db2.pst.execute();
			db2.close();// 关闭连接

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void cleanupData() {
		sql = "truncate table ch_items";
		db2 = new CDBHelperItems(sql);
		try {
			//db2.pst.execute();
			db2.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
