package job.dao.MDBHelperImpl;

import job.dao.MDBHelper;

public class MDBHelperCommodityAttr extends MDBHelper {

	public MDBHelperCommodityAttr() {
		super();
	}

	@Override
	public String getselectsql() {
		String sql = " SELECT m.id AS M_ID ,m.totalmallid AS M_COMMODITYID ,m.attrid AS M_ATTRID ,m.attrname AS M_ATTRNAME , "
				+ " m.name AS M_ATTRVALUE,t.name AS M_COMMODITYNAME ,m.createdate AS M_CREATETIME ,"
				+ " m.editdate AS M_EDITTIME FROM ml_mall_attr_extend m LEFT JOIN ml_total_mall t ON m.totalmallid = t.id ";
		return sql;
	}

	@Override
	public String getproperties() {
		String properties = System.getProperty("smm.cuohe") + "WEB-INF/classes/dsproperties/DS_MallattrextendToCommodityattrDS.properties";
		return properties;
	}

}
