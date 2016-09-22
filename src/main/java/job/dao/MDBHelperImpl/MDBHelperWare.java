package job.dao.MDBHelperImpl;

import job.dao.MDBHelper;

public class MDBHelperWare extends MDBHelper {

	public MDBHelperWare() {
		super();
	}

	@Override
	public String getselectsql() {
		String sql = "SELECT m.id AS M_ID , m.name AS M_NAME, m.catid AS M_CATID , m.company AS M_COMPANY , m.address AS M_ADDRESS ,"
				+"m.areaid AS M_AREAID , m.status AS M_STATUS , m.createdate AS M_CREATEDATE , m.createuser AS M_CREATEUSER ,"
				+"m.editdate AS M_EDITDATE , m.issupervise AS M_ISSUPERVISE, m.iswhole AS M_ISWHOLE, "
				+"m.iscooperate AS M_ISCOOPERATE, m.isself AS M_ISSELF  FROM  ml_warehouse m";
		return sql;
	}

	@Override
	public String getproperties() {
		String properties = System.getProperty("smm.cuohe") + "WEB-INF/classes/dsproperties/DS_WarehouseToWarehouseDS.properties";
		return properties;
	}
	
}
