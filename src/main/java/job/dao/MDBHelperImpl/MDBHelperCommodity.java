package job.dao.MDBHelperImpl;

import job.dao.MDBHelper;

public class MDBHelperCommodity extends MDBHelper {

	public MDBHelperCommodity() {
		super();
	}

	@Override
	public String getselectsql() {
		String sql = " SELECT m.id AS M_ID ,m.name AS M_NAME,m.catid AS M_CATID,m.catname AS M_CATNAME ,m.createdate AS M_CREATETIME ,"
				+ " p.id AS M_PRODID FROM ml_total_mall m INNER JOIN ml_product p ON m.name = p.name ";
		return sql;
	}

	@Override
	public String getproperties() {
		String properties = System.getProperty("smm.cuohe") + "WEB-INF/classes/dsproperties/DS_TotalmallToCommodityDS.properties";
		return properties;
	}

}
