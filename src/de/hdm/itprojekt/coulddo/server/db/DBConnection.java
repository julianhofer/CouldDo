package de.hdm.itprojekt.coulddo.server.db;

import java.sql.*;

public class DBConnection {

	private static Connection con = null;
	 private static String driver = "com.mysql.cj.jdbc.Driver";
	// Connection to Hetzner MySql
	/*
	 * private static String dburl = "jdbc:mysql://sql223.your-server.de"; 
	 * private static String db = "/choulddo_db"; 
	 * private static String user = "admin_db"; 
	 * private static String pass = "fHYWFsqTXB9fd2qE";
	 */
	
	private static String localUrl = "jdbc:mysql://localhost:3306/coulddo?user=root&password=Login2020";

	public static Connection connection() {
		
		if (con == null) {

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e3) {
				e3.printStackTrace();
			}
			try {		
				con = DriverManager.getConnection(localUrl + "&serverTimezone=UTC");
				// con = DriverManager.getConnection(dburl + db, user, pass + "&serverTimezone=UTC");
				System.out.println("MySql connected");
			} catch (SQLException e4) {
				System.err.println("Mysql Connection Error: ");
				e4.printStackTrace();
			}
		}
		return con;
	}
}
