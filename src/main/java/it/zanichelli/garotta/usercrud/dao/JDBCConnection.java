package it.zanichelli.garotta.usercrud.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBCConnection {
	Connection connection = null;

	public Connection getConnnection() throws SQLException {

		try {
			InitialContext context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ZanichelliDB");
			connection = dataSource.getConnection();
		} catch (NamingException e) {
			throw new SQLException("datasource non trovato", e);

		}
		return connection;
	}

}