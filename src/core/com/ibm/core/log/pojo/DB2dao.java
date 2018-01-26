package com.ibm.core.log.pojo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB2dao {
	
	
	public Connection getConnection() throws SQLException,
	ClassNotFoundException, IllegalAccessException,
	InstantiationException {

         //获得一个驱动的实例
	Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();

         //端口号是50000，千万不要写错啊
	String url = "jdbc:db2://192.168.56.101:50000/MOADB";

         //DB2连接的用户名
	String user = "mydb";

         //DB2连接的用户密码
	String password = "tianzhizi";

         //获得一个连接
	Connection conn = DriverManager.getConnection(url, user, password);

         //成功了！
	System.out.println("Success!");

	return conn;
//
	}
	
	public static void main(String[] args){
		DB2dao DB2dao=new DB2dao();
		try {
			DB2dao.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}