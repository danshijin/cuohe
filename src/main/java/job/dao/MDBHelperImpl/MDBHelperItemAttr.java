package job.dao.MDBHelperImpl;

import job.dao.MDBHelper;

public class MDBHelperItemAttr extends MDBHelper {

	public MDBHelperItemAttr() {
		super();
	}

	@Override
	public String getselectsql() {
		String sql = " SELECT a.id AS M_ID ,a.catid AS M_ITEMID ,a.name AS M_NAME , "
				+ " CASE WHEN a.writetype = 1 then 0 when a.writetype = 2 then 1 when a.writetype = 3 then 2 when a.writetype = 4 then 3 END AS M_FILLMODE , "
				+ " CASE WHEN a.rule = 1 THEN 0 WHEN a.rule = 2 THEN 1 WHEN a.rule = 3 THEN 2 WHEN a.rule = 4 THEN 3 WHEN a.rule = 5 THEN 4  END AS M_DATAREGURAL , "
				+ " a.defaultvalue AS M_DEFAULT ,"
				+ " a.attr AS M_OPTIONS , a.is_main AS M_MAINPROPERTY , a.is_require AS M_REQUIRED ,"
				+ " a.status AS M_STATUS ,a.createdate AS M_CREATEDAT,a.editdate AS M_UPDATEDAT  FROM ml_mall_attr a ";
		return sql;
	}

	@Override
	public String getproperties() {
		String properties = System.getProperty("smm.cuohe") + "WEB-INF/classes/dsproperties/DS_MallattrToItemattrDS.properties";
		return properties;
	}

}
