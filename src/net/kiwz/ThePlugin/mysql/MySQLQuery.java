package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.kiwz.ThePlugin.ThePlugin;

public class MySQLQuery {
	
	//Connection conn = ThePlugin.conn;
	
	public void update(Connection conn, String queryString) throws SQLException {
		
		conn.createStatement().executeUpdate(queryString);
	}
	
	public ResultSet query(Connection conn, String queryString) throws SQLException {
		
		ResultSet res = conn.createStatement().executeQuery(queryString);
		
		return res;
	}
}
