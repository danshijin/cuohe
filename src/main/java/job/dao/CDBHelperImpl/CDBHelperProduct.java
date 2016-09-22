package job.dao.CDBHelperImpl;

import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.smm.cuohe.domain.dealmake.ProductEntity;

import job.dao.CDBHelper;

public class CDBHelperProduct extends CDBHelper {
	private static final Logger logger = Logger.getLogger(CDBHelperProduct.class);
	private static CDBHelper db2 = null;
	private static String sql = null;

	public CDBHelperProduct() {
		super();
	}

	public CDBHelperProduct(String sql) {
		super(sql);
	}

	@Override
	public Object getEntity() {
		return new ProductEntity();
	}

	@Override
	public Object validateDate(Object data, Object obj, String column) throws ParseException {

		ProductEntity product = (ProductEntity) obj;

		if (column.equalsIgnoreCase("C_ID")) {
			if (String.valueOf(data) == null) {
				logger.info("ID为空");
				return null;
			}
			product.setId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_NAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("name为空");
				return null;
			}
			product.setName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CATID")) {
			if (String.valueOf(data) == null) {
				logger.info("catid为空");
				return null;
			}
			product.setCatId(Integer.parseInt(String.valueOf(data)));
		} else if (column.equalsIgnoreCase("C_CATNAME")) {
			if (String.valueOf(data) == null || String.valueOf(data).equals("")) {
				logger.info("catname为空");
				return null;
			}
			product.setCatName(String.valueOf(data));
		} else if (column.equalsIgnoreCase("C_CREATETIME")) {
			if (String.valueOf(data) == null) {
				logger.info("createtime为空");
				return null;
			}
			product.setCreateTime(String.valueOf(data));
		}
		return product;
	}

	@Override
	public void addvalidate(Object obj) {
		ProductEntity product = (ProductEntity) obj;

		sql = "insert into ch_product (id,name,cat_id,cat_name,create_time) values(?,?,?,?,?)";
		db2 = new CDBHelperProduct(sql);
		try {
			db2.pst.setInt(1, product.getId());
			db2.pst.setString(2, product.getName());
			db2.pst.setInt(3, product.getCatId());
			db2.pst.setString(4, product.getCatName());
			db2.pst.setString(5, product.getCreateTime());

			db2.pst.execute();
			db2.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void cleanupData() {
		sql = "truncate table ch_product";
		db2 = new CDBHelperProduct(sql);
		try {
			db2.pst.execute();
			db2.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
