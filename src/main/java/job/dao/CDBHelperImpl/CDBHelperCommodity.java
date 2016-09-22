package job.dao.CDBHelperImpl;

import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.smm.cuohe.domain.Commodity;

import job.dao.CDBHelper;

public class CDBHelperCommodity extends CDBHelper {
	private static final Logger logger = Logger.getLogger(CDBHelperCommodity.class);
	private static CDBHelper db2 = null;
	private static String sql = null;

	public CDBHelperCommodity(){
		super();
	}
	
	public CDBHelperCommodity(String sql) {
		super(sql);
	}

	@Override
	public Object getEntity() {
		return new Commodity();
	}

	@Override
	public Object validateDate(Object data, Object obj, String column) throws ParseException {

		Commodity commodity = (Commodity) obj;

		if (column.equalsIgnoreCase("C_ID")) {
			if (String.valueOf(data) == null) {
				logger.info("ID为空");
				return null;
			}
			commodity.setId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_NAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("name为空");
				return null;
			}
			commodity.setName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_PRODID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("prodid为空");
				return null;
			}
			commodity.setProdId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_CATID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("catid为空");
				return null;
			}
			commodity.setCatId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_CATNAME")) {
			if (String.valueOf(data) == null) {
				logger.info("catname为空");
				return null;
			}
			commodity.setCatName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CREATETIME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("createtime为空");
				return null;
			}
			commodity.setCreateTime(String.valueOf(data));
		}
		return commodity;
	}

	@Override
	public void addvalidate(Object obj) {
		Commodity commodity = (Commodity) obj;

		sql = "insert into ch_commodity (id,name,prod_id,cat_id,cat_name,create_time) values(?,?,?,?,?,?)";
		db2 = new CDBHelperCommodity(sql);
		try {
			db2.pst.setInt(1, commodity.getId());
			db2.pst.setString(2, commodity.getName());
			db2.pst.setInt(3, commodity.getProdId());
			db2.pst.setInt(4, commodity.getCatId());
			db2.pst.setString(5, commodity.getCatName());
			db2.pst.setString(6, commodity.getCreateTime());

			db2.pst.execute();
			db2.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void cleanupData() {
		sql = "truncate table ch_commodity ";
		db2 = new CDBHelperCommodity(sql);
		try {
			db2.pst.execute();
			db2.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
