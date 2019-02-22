package com.zens.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月14日 下午12:57:54
 */
public class JdbcConnect {
	
	public Connection getConnectionMysql() {
		Connection conn = null;
		String classname = ResourceBundle.getBundle("db").getString("jdbc.driverClassName");;
		String url = ResourceBundle.getBundle("db").getString("jdbc.url");;
		String user = ResourceBundle.getBundle("db").getString("jdbc.username");;
		String pass = ResourceBundle.getBundle("db").getString("jdbc.password");;

		try {
			Class.forName(classname);
			conn = DriverManager.getConnection(url, user, pass);
			return conn;
		} catch (ClassNotFoundException cnf) {
			System.out.println("driver not find:" + cnf);
			return null;
		} catch (SQLException sqle) {
			System.out.println("can't connection db: " + sqle);
			return null;
		} catch (Exception e) {
			System.out.println("Failed to load JDBC/ODBC driver.");
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(new JdbcConnect().getConnectionMysql());;
	}
}
