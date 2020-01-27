package de.hdm.itprojekt.coulddo.server.db;

import java.sql.*;

public class DBConnection {

	private static Connection con = null;
	private static String dburl = "jdbc:mysql://sql601.your-server.de";
	private static String db = "/choulddo";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String user = "admin_db";
	private static String pass = "gNRkUYeeLY8BA4Pq";

	public static Connection connection() {
		if (con == null) {
			String url = null;

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e3) {
				e3.printStackTrace();
			}
			try {
				url = dburl;
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
