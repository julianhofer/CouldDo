package de.hdm.itprojekt.coulddo.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection con = null;
	public static String url = "jdbc:mysql://sql601.your-server.de";
	public static String db = "coulddo";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String user = "admin_db";
	public static String pass = "gNRkUYeeLY8BA4Pq";

	public static Connection connection() {
		if (con == null) {
			String url = null;

			try {
				Class.forName(driver).newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e3) {
				e3.printStackTrace();
			}
			try {

				con = DriverManager.getConnection(url + db, user, pass);
				System.out.println("MySql connected");
			} catch (SQLException e4) {
				System.err.println("Mysql Connection Error: ");
				e4.printStackTrace();
			}
		}
		return con;
	}
}
