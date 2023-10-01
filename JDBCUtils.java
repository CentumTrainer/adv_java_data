package com.centum.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDate;

public class JDBCUtils {
private static String jdbcURL="jdbc:mysql://localhost:3306/myplannerdb";
private static String jdbcUsername="root";
private static String jdbcPassword="root";

	public static Connection getConnection() {
		Connection con=null;
		try {
			System.out.println("here.........!");
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
			System.out.println("con established.....!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public static Date getSQLDate(LocalDate date) {
		return java.sql.Date.valueOf(date);
	}

	public static LocalDate getUtilDate(Date sqlDate) {
		return sqlDate.toLocalDate();
	}
}
