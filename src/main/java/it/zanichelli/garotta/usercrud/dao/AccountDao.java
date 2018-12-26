package it.zanichelli.garotta.usercrud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.zanichelli.garotta.usercrud.model.Account;
import it.zanichelli.garotta.usercrud.model.ListAccount;
import it.zanichelli.garotta.usercrud.model.ListUsername;


public class AccountDao {
	private JDBCConnection jdbcConnection = null;
	private Connection connection = null;
	private static final String LIST_ALL = "select username,password,ruolo from USER";
	private static final String LIST_ALL_USERNAME = "select username from USER";
	private static final String INSERT_PREP_STAT = "insert into USER value(?,?,?)";
	private static final String FIND_BY_USERNAME_PREP_STAT = "select username,password,ruolo from USER where username=?";
	private static final String DELETE_PREP_STAT = "delete from USER where username=?";
	private static final String UPDATE_PREP_STAT = "update USER set password=?,ruolo=? where username=?";

	private Connection getConnection() throws SQLException {
		if (jdbcConnection == null || connection == null) {
			jdbcConnection = new JDBCConnection();
			connection = jdbcConnection.getConnnection();
		}
		return connection;
	}

	public boolean insert(String username, String password, String ruolo) throws SQLException {

		PreparedStatement stat = getConnection().prepareStatement(INSERT_PREP_STAT);
		stat.setString(1, username);
		stat.setString(2, password);
		stat.setString(3, ruolo);
		int rs = stat.executeUpdate();

		return (rs == 1 ? true : false);
	}

	public boolean update(String username, String password, String ruolo) throws SQLException {
		PreparedStatement stat = getConnection().prepareStatement(UPDATE_PREP_STAT);
		stat.setString(3, username);
		stat.setString(1, password);
		stat.setString(2, ruolo);

		int rs = stat.executeUpdate();

		return (rs == 1 ? true : false);
	}

	public ListAccount listAll() throws SQLException {
		ListAccount accountList = new ListAccount();
		ResultSet rs = null;
		try
		{
			Statement stat = getConnection().createStatement();
			List<Account> accounts = new ArrayList<Account>();
			rs = stat.executeQuery(LIST_ALL);
			while (rs.next()) {
				Account account = new Account();
				account.setPassword(rs.getString("password"));
				account.setUsername(rs.getString("username"));
				account.setRuolo(rs.getString("ruolo"));
				accounts.add(account);
			}
			accountList.setAccounts(accounts);
		}finally{
			if (rs!=null)
				rs.close();
		}
		return accountList;
	}
	
	public ListUsername getAllUsername() throws SQLException {
		ListUsername usernameList = new ListUsername();
		ResultSet rs = null;
		try
		{
			Statement stat = getConnection().createStatement();
			List<String> usernames = new ArrayList<String>();
		
			rs = stat.executeQuery(LIST_ALL_USERNAME);
			while (rs.next()) {
				
				usernames.add(rs.getString("username"));
			}
			usernameList.setUsernames(usernames);
		}finally{
			if (rs!=null)
				rs.close();
		}
		return usernameList;
	}

	public boolean delete(String username) throws SQLException {

		PreparedStatement stat = getConnection().prepareStatement(DELETE_PREP_STAT);
		stat.setString(1, username);
		int rs = stat.executeUpdate();
		
		return (rs == 1 ? true : false);
	}

	public Account getByUsername(String username) throws SQLException {
		Account account = new Account();
		ResultSet rs = null;
		try
		{
			PreparedStatement stat = getConnection().prepareStatement(FIND_BY_USERNAME_PREP_STAT);
			stat.setString(1, username);
			rs = stat.executeQuery();
			
			if (rs.next()) {
				account.setPassword(rs.getString("password"));
				account.setUsername(rs.getString("username"));
				account.setRuolo(rs.getString("ruolo"));
			}
		}finally{
			if (rs!=null)
				rs.close();
		}	
		return account;
	}

}
