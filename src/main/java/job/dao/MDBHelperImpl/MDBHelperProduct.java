package job.dao.MDBHelperImpl;

import job.dao.MDBHelper;

public class MDBHelperProduct extends MDBHelper {

	public MDBHelperProduct() {
		super();
	}

	@Override
	public String getselectsql() {
		String sql = "SELECT p.id AS M_ID ,p.name AS M_NAME ,p.catid AS M_CATID , p.catname AS M_CATNAME ,p.createdate AS M_CREATETIME FROM ml_product p ";
		return sql;
	}

	@Override
	public String getproperties() {
		String properties = System.getProperty("smm.cuohe") + "WEB-INF/classes/dsproperties/DS_ProductToProductDS.properties";
		return properties;
	}

}
