package job.dao.MDBHelperImpl;

import job.dao.MDBHelper;

public class MDBHelperItems extends MDBHelper {

	public MDBHelperItems() {
		super();
	}

	@Override
	public String getselectsql() {
		String sql = " SELECT c.catid AS M_ID,c.catname AS M_NAME, c.listorder AS M_LISTORDER FROM de_category c ";
		return sql;
	}

	@Override
	public String getproperties() {
		String properties = System.getProperty("smm.cuohe") + "WEB-INF/classes/dsproperties/DS_CategoryToItemsDS.properties";
		return properties;
	}

}
