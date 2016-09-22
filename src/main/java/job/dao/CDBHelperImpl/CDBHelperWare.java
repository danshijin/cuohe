package job.dao.CDBHelperImpl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.smm.cuohe.domain.dealmake.Warehouse;

import job.dao.CDBHelper;

public class CDBHelperWare extends CDBHelper {
	private static final Logger logger = Logger.getLogger(CDBHelperWare.class);
	private static CDBHelper db2 = null;
	private static String sql = null;

	public CDBHelperWare() {
		super();
	}

	public CDBHelperWare(String sql) {
		super(sql);
	}

	@Override
	public Object getEntity() {
		return new Warehouse();
	}

	@Override
	public Object validateDate(Object data, Object obj, String column) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Warehouse warehouse = (Warehouse) obj;
		if (column.equalsIgnoreCase("C_ID")) {
			if (String.valueOf(data) == null) {
				logger.info("ID为空");
				return null;
			}
			warehouse.setId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_NAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("name为空");
				return null;
			}
			warehouse.setName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CATID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("catid为空");
				return null;
			}
			warehouse.setCatid(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_ITEMID")) {
			if (String.valueOf(data) == null) {
				logger.info("ItemId为空");
			}
			warehouse.setItemId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_COMPANY")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("company为空");
			}
			warehouse.setCompany(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_AREAID")) {
			if (String.valueOf(data) == null) {
				logger.info("areaid为空");
				return null;
			}
			warehouse.setAreaid(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_ADDRESS")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("address为空");
			}
			warehouse.setAddress(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_STATUS")) {
			if (String.valueOf(data) != null && String.valueOf(data).equals("true")) {
				warehouse.setStatus(1);
			} else if (String.valueOf(data) != null && String.valueOf(data).equals("false")) {
				warehouse.setStatus(0);
			} else {
				logger.info("status为空");
			}
		} else if (column.equalsIgnoreCase("C_CREATEDAT")) {
			if (data == null) {
				logger.info("createdAt为空");
				return null;
			}
			warehouse.setCreatedAt(sdf.parse(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_CREATEDBY")) {
			if (String.valueOf(data) == null) {
				logger.info("createdBy为空");
			}
			warehouse.setCreatedBy(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_UPDATEAT")) {
			if (String.valueOf(data) == null || String.valueOf(data).equalsIgnoreCase("null")) {
				logger.info("updateAt为空 设置默认值 ：0000-00-00");
				warehouse.setUpdateAt(sdf.parse(String.valueOf("0000-00-00")));
			}else{
				warehouse.setUpdateAt(sdf.parse(String.valueOf(data)));
			}
		} else if (column.equalsIgnoreCase("C_UPDATEBY")) {
			if (String.valueOf(data) == null) {
				logger.info("updateBy为空");
			}
			warehouse.setUpdateBy(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_ISSUPERVISE")) {
			if (String.valueOf(data) != null && String.valueOf(data).equals("true")) {
				warehouse.setIssupervise(1);
			} else if (String.valueOf(data) != null && String.valueOf(data).equals("false")) {
				warehouse.setIssupervise(0);
			} else {
				logger.info("C_ISSUPERVISE为空");
			}
		} else if (column.equalsIgnoreCase("C_ISWHOLE")) {
			if (String.valueOf(data) != null && String.valueOf(data).equals("true")) {
				warehouse.setIswhole(1);
			} else if (String.valueOf(data) != null && String.valueOf(data).equals("false")) {
				warehouse.setIswhole(0);
			} else {
				logger.info("C_ISWHOLE为空");
			}
		} else if (column.equalsIgnoreCase("C_ISCOOPERATE")) {
			if (String.valueOf(data) != null && String.valueOf(data).equals("true")) {
				warehouse.setIscooperate(1);
			} else if (String.valueOf(data) != null && String.valueOf(data).equals("false")) {
				warehouse.setIscooperate(0);
			} else {
				logger.info("C_ISCOOPERATE为空");
			}
		} else if (column.equalsIgnoreCase("C_CREATEUSER")) {
			if (String.valueOf(data) == null) {
				logger.info("C_CREATEUSER为空");
			}
			warehouse.setCreateuser(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_ISSELF")) {
			if (String.valueOf(data) != null && String.valueOf(data).equals("true")) {
				warehouse.setIsself(1);
			} else if (String.valueOf(data) != null && String.valueOf(data).equals("false")) {
				warehouse.setIsself(0);
			} else {
				logger.info("C_ISSELF为空");
			}
		}
		return warehouse;
	}

	@Override
	public void addvalidate(Object obj) {
		Warehouse warehouse = (Warehouse) obj;

		sql = "insert into ch_warehouse (id,ItemId,name,catid,company,address,areaid,status,createdAt,createdBy,updateAt,updateBy,issupervise,iswhole,iscooperate,createuser,isself) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db2 = new CDBHelperWare(sql);
		try {
			db2.pst.setInt(1, warehouse.getId());
			db2.pst.setInt(2, warehouse.getItemId() != null ? warehouse.getItemId() : 0);
			db2.pst.setString(3, warehouse.getName());
			db2.pst.setString(4, warehouse.getCatid());
			db2.pst.setString(5, warehouse.getCompany());
			db2.pst.setString(6, warehouse.getAddress());
			db2.pst.setInt(7, warehouse.getAreaid());
			db2.pst.setInt(8, warehouse.getStatus());
			db2.pst.setDate(9, new java.sql.Date(warehouse.getCreatedAt().getTime()));
			db2.pst.setInt(10, warehouse.getCreatedBy() != null ? warehouse.getCreatedBy() : 0);
			db2.pst.setDate(11, new java.sql.Date(warehouse.getUpdateAt().getTime()));
			db2.pst.setInt(12, warehouse.getUpdateBy() != null ? warehouse.getCreatedBy() : 0);
			db2.pst.setInt(13, warehouse.getIssupervise() != null ? warehouse.getIssupervise() : 0);
			db2.pst.setInt(14, warehouse.getIswhole() != null ? warehouse.getIswhole() : 0);
			db2.pst.setInt(15, warehouse.getIscooperate() != null ? warehouse.getIscooperate() : 0);
			db2.pst.setString(16, warehouse.getCreateuser());
			db2.pst.setInt(17, warehouse.getIsself() != null ? warehouse.getIsself() : 0);
			
			db2.pst.execute();
			db2.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void cleanupData() {
		sql = "truncate table ch_warehouse";
		db2 = new CDBHelperWare(sql);
		try {
			db2.pst.execute();
			db2.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
