package com.ibm.cdl.manage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

//	public static final String SQLITE_DB_PATH="/apps/tomcat/apache-tomcat-6.0.36/webapps/ZJProjectTest/resources/db/";
//	//public static final String SQLITE_DB_PATH="//d:/asdf/";
//	public static final String BACK_DB_NAME="addressBookBack.db";
//	public static final String SQLITE_DB_NAME="addressBook.db";

	
	/**
	 * 建立db连接
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static Connection getConn(String jdbcUrl) throws ClassNotFoundException, SQLException{
		   Connection conn=null;
	        //连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");
	        //建立一个数据库名addressBook.db的连接
	        conn = DriverManager.getConnection(jdbcUrl);
		  return conn;
		
	}
	
	/**
	 * 创建db表
	 */
	public static void createAddressBookTable(String path){
		
		String moaUserTable=" CREATE TABLE [MOA_USER] ([USER_NAME] VARCHAR(100),[USER_CODE] VARCHAR(150),[USER_PHONE] VARCHAR(50),[TELEPHONE] VARCHAR(50),[USER_MAIL] VARCHAR(150),"+
				" [IDCARDNUM] VARCHAR(50),[SEQ] INTEGER,[PINYIN] VARCHAR(50),[JOBLEVEL] VARCHAR(20),[DEPTCODE] VARCHAR(20),[POSITION] VARCHAR(20),DEPTFULLNAME VARCHAR(500));";
		String moaOrgTable="CREATE TABLE [MOA_ORG] ([ORG_CODE] VARCHAR(20),[ORG_BASE_DEPT] VARCHAR(50),[ORG_SHOW_DEPT] VARCHAR(50),[SEQ] INTEGER);";
		String moaVersionTable=" CREATE TABLE [DB_VERSION] ([VERSIONNUM] VARCHAR(10),[UPDATE_DATE] VARCHAR(10));";
		String moaDeptTable="CREATE TABLE [MOA_DEPT] ([DEPTCODE] VARCHAR2(20),[PARENTCODE] VARCHAR2(20),[DEPTNAME] VARCHAR2(50),[DEPTMANAGER] VARCHAR2(20),[DEPTSTYLE] VARCHAR2(10),[DEPTINDICATOR] VARCHAR2(50),[SEQ] INTEGER);";
		Connection conn = null;
		Statement stement=null;
		try{
			conn = getConn("jdbc:sqlite://"+path);
			stement=conn.createStatement();
			//创建数据表
			stement.executeUpdate(moaUserTable);
			stement.executeUpdate(moaOrgTable);
			stement.executeUpdate(moaVersionTable);
			stement.executeUpdate(moaDeptTable);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(stement!=null){
				try {
					stement.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		
		
	}
	
	public static boolean backFile(File oldFile, String newFile){

		boolean b=false;
		FileInputStream is =null;
		FileOutputStream os=null;
		try{
			
			is = new FileInputStream(oldFile);
			os = new FileOutputStream(new File(newFile));
			
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			os.write(bytes);
			b=true;
		}catch(Exception e){
			b=false;
			e.printStackTrace();
		}finally{
			try{
				if(is!=null){
					is.close();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			try{
				if(os!=null){
					os.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	
		return b;
}
}
