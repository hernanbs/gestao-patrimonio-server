package br.com.gestao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static String url = "jdbc:postgresql://localhost:5432/db_gestao_patrimonio";
    private static String username ="postgres";
    private static String password = "postgres";
    
	public static Connection openConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");     
		Connection conn = DriverManager.getConnection(url, username, password);
		System.out.println("Connection is valid: " + conn.isValid(10));
		return conn;
	}
}
