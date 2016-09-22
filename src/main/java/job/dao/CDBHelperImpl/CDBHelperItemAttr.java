package job.dao.CDBHelperImpl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.smm.cuohe.domain.ItemAttr;

import job.dao.CDBHelper;

public class CDBHelperItemAttr extends CDBHelper {
	private static final Logger logger = Logger.getLogger(CDBHelperItemAttr.class);
	private static CDBHelper db2 = null;
	private static String sql = null;

	public CDBHelperItemAttr() {
		super();
	}

	public CDBHelperItemAttr(String sql) {
		super(sql);
	}

	@Override
	public Object getEntity() {
		return new ItemAttr();
	}

	@Override
	public Object validateDate(Object data, Object obj, String column) throws ParseException {

		ItemAttr itemAttr = (ItemAttr) obj;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (column.equalsIgnoreCase("C_ID")) {
			if (String.valueOf(data) == null) {
				logger.info("ID为空");
				return null;
			}
			itemAttr.setId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_ITEMID")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("itemid为空");
				return null;
			}
			itemAttr.setItemid(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_NAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("name为空");
				return null;
			}
			itemAttr.setName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_FILLMODE")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("fillmode为空");
			}
			itemAttr.setFillmode(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_DATAREGURAL")) {
			if (String.valueOf(data) == null) {
				logger.info("dataregural为空");
			}
			itemAttr.setDataregural(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_DEFAULT")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("default为空");
			} 
			if(String.valueOf(data).equals("null")){
				itemAttr.setDefaulta("");
			}else{
				itemAttr.setDefaulta(String.valueOf(data));
			}
		} else if (column.equalsIgnoreCase("C_OPTIONS")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("options为空 ");
				return null;
			}
			itemAttr.setOptions(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_MAINPROPERTY")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("") || String.valueOf(data).equalsIgnoreCase("null")) {
				logger.info("mainproperty为空 ");
				itemAttr.setMainproperty(2);
			}else{
				if (String.valueOf(data).equals("true")) {
					itemAttr.setMainproperty(1);
				} else if (String.valueOf(data).equals("false")) {
					itemAttr.setMainproperty(0);
				} 
			}
		} else if (column.equalsIgnoreCase("C_REQUIRED")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("required为空");
				return null;
			}else{
				if (String.valueOf(data).equals("true")) {
					itemAttr.setRequired(1);
				} else if (String.valueOf(data).equals("false")) {
					itemAttr.setRequired(0);
				} 
			} 
		} else if (column.equalsIgnoreCase("C_STATUS")) { //两张表的属性值是反的
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("status为空");
				return null;
			}else{
				if(String.valueOf(data).equals("true")){
					itemAttr.setStatus(0);
				}
				if(String.valueOf(data).equals("false")){
					itemAttr.setStatus(1);
				}
			}
			
		} else if (column.equalsIgnoreCase("C_LISTORDER")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("listorder为空");
			}
			itemAttr.setListorder(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_CREATEDAT")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("createdat为空");
				return null;
			}
			itemAttr.setCreatedat(sdf.parse(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_CREATEDBY")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("createdby为空");
				return null;
			} 
			itemAttr.setCreatedby(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_UPDATEDAT")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("updatedat为空");
				return null;
			} 
			itemAttr.setUpdatedat(sdf.parse(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_UPDATEDBY")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("updatedby为空");
				return null;
			} 
			itemAttr.setUpdatedby(Integer.parseInt(String.valueOf(data)));
		} 
		return itemAttr;
	}

	@Override
	public void addvalidate(Object obj) {
		ItemAttr itemAttr = (ItemAttr) obj;
		sql = "insert into ch_item_attr (id,itemID,name,fillMode,dataRegural,default_value,options,mainProperty,required,status,listOrder,createdAt,createdBy,updatedAt,UpdatedBy) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ;
		db2 = new CDBHelperItemAttr(sql);
		try {
			db2.pst.setInt(1, itemAttr.getId());
			db2.pst.setInt(2, itemAttr.getItemid());
			db2.pst.setString(3, itemAttr.getName());
			db2.pst.setInt(4, itemAttr.getFillmode());
			db2.pst.setInt(5, itemAttr.getDataregural()==null?0:itemAttr.getDataregural());
			db2.pst.setString(6, itemAttr.getDefaulta());
			db2.pst.setString(7, itemAttr.getOptions());
			db2.pst.setObject(8, itemAttr.getMainproperty()==2?null:itemAttr.getMainproperty());
			db2.pst.setInt(9, itemAttr.getRequired());
			db2.pst.setInt(10, itemAttr.getStatus());
			db2.pst.setInt(11, itemAttr.getListorder()==null?0:itemAttr.getListorder());
			db2.pst.setDate(12, new java.sql.Date(itemAttr.getCreatedat().getTime()));
			db2.pst.setInt(13, itemAttr.getCreatedby()==null?0:itemAttr.getCreatedby());
			db2.pst.setDate(14, new java.sql.Date(itemAttr.getUpdatedat().getTime()));
			db2.pst.setInt(15, itemAttr.getUpdatedby()==null?0:itemAttr.getUpdatedby());
			
			db2.pst.execute();
			db2.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void cleanupData() {
		sql = "truncate table ch_item_attr ";
		db2 = new CDBHelperItemAttr(sql);
		try {
			db2.pst.execute();
			db2.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
