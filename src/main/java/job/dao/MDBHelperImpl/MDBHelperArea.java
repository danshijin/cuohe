package job.dao.MDBHelperImpl;

import job.dao.MDBHelper;

public class MDBHelperArea extends MDBHelper {

	public MDBHelperArea() {
		super();
	}

	@Override
	public String getselectsql() {
		String sql = " SELECT d.areaid AS M_ID ,d.areaname AS M_NAME ,d.parentid AS M_PARENTID ,d.arrchildid AS M_CHILDID ,"
				+ " d.listorder AS M_LISTORDER ,d.parentname  AS M_PARENTNAME FROM de_area d  ";
		return sql;
	}

	@Override
	public String getproperties() {
		String properties = System.getProperty("smm.cuohe") + "WEB-INF/classes/dsproperties/DS_AreaToAreasDS.properties";
		return properties;
	}

}
