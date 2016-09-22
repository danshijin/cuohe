package job.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

public abstract class CDBHelper {

	public Connection conn = null;
	public PreparedStatement pst = null;
	
	public static String url = "";
	public static String user = "read";
	public static String password = "password";
	
	public CDBHelper(){
		
		FileInputStream fis = null;
		try {
			String path = System.getProperty("smm.cuohe") + "/WEB-INF/classes/config.properties";

			Properties pros = new Properties();
			fis = new FileInputStream(path);
			pros.load(fis);
			
			url = pros.getProperty("url");
			user = pros.getProperty("username");
			password = pros.getProperty("password");
			
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
		
	}
	
	public CDBHelper(String sql) {
		
		FileInputStream fis = null;
		try {
			String path = System.getProperty("smm.cuohe") + "/WEB-INF/classes/config.properties";

			Properties pros = new Properties();
			fis = new FileInputStream(path);
			pros.load(fis);
			
			url = pros.getProperty("url");
			user = pros.getProperty("username");
			password = pros.getProperty("password");
			String jdbcType = pros.getProperty("jdbcType");
			
			Class.forName(jdbcType);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
			/*try {
				conn.setAutoCommit(false);
				pst = conn.prepareStatement(sql);// 准备执行语句
				pst.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}finally{
				conn.setAutoCommit(true);
			}*/
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
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public abstract Object getEntity();

	public abstract Object validateDate(Object object, Object obj, String c_COLUMN) throws ParseException;

	public abstract void addvalidate(Object obj);

	public abstract void cleanupData();

}
