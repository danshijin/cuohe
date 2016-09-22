package job.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public abstract class MDBHelper {
	
	public static String url = "";
	public static String user = "read";
	public static String password = "password";

	public static Connection conn = null;
	public static PreparedStatement pst = null;

	public abstract String getselectsql();

	public abstract String getproperties();

	public static PreparedStatement getselect(String sql) {
		
		FileInputStream fis = null;
		try {
			String path = System.getProperty("smm.cuohe") + "/WEB-INF/classes/config.properties";

			Properties pros = new Properties();
			fis = new FileInputStream(path);
			pros.load(fis);
			
			url = pros.getProperty("mallUrl");
			user = pros.getProperty("mallUsername");
			password = pros.getProperty("mallPassword");
			String jdbcType = pros.getProperty("mallJdbcType");
			
			Class.forName(jdbcType);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pst;
	}

	public void close() {
		try {
			conn.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
