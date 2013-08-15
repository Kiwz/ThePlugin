package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MySQLQuery {
	
	private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
	MySQL MySQL = new MySQL(plugin, "109.247.37.74", "3306", "theplugin", "kiwz", "test");
	Connection conn = null;
	
	public void toDB(String statement) throws SQLException {
		
		conn = MySQL.openConnection();
		
		PreparedStatement exec = conn.prepareStatement(statement);
		exec.executeUpdate();
		exec.close();
		
	}
	
	public ResultSet fromDB(String queryString) throws SQLException {
		
		conn = MySQL.openConnection();
		
		Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(queryString);
		
		return res;
	}
}
