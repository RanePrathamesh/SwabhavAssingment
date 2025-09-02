package com.aurionpro.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSource {

	private static DatabaseSource instance = null;
	private Connection connection = null;

	private DatabaseSource() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payment_management", "root", "root");
			System.out.println("connected");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("failed");
			e.printStackTrace();
		}

	}

	public static DatabaseSource getinstance() {
		if (instance == null) {
			instance = new DatabaseSource();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
