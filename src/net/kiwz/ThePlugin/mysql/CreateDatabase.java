package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.PreparedStatement;

public class CreateDatabase {
	
	private String createTables = "DROP TABLE Persons";

	private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
	MySQL MySQL = new MySQL(plugin, "109.247.37.74", "3306", "theplugin", "kiwz", "test");
	Connection conn = null;
	
	public void createDB() throws SQLException {
		conn = MySQL.openConnection();
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement(createTables);
		statement.executeUpdate();
		statement.close();
		conn.close();
	}
}
