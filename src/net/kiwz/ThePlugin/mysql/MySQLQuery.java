package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.kiwz.ThePlugin.ThePlugin;

public class MySQLQuery {
	
	Connection conn = ThePlugin.conn;
	
	public void toDB(String statement) throws SQLException {
		
		PreparedStatement exec = conn.prepareStatement(statement);
		exec.executeUpdate();
		exec.close();
		
	}
	
	public ResultSet fromDB(String queryString) throws SQLException {

		Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(queryString);
		
		return res;
	}
}
