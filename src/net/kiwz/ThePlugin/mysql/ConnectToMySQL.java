package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.Config;
import net.kiwz.ThePlugin.utils.MultiWorld;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.fusesource.jansi.Ansi;

public class ConnectToMySQL {
	private final Plugin plugin = ThePlugin.getPlugin();
    private final String hostname = Config.getHost();
    private final String port = Config.getPort();
    private final String database = Config.getDatabase();
    private final String user = Config.getUser();
    private final String password = Config.getPassword();
    private Connection connection = null;
    
    public void loadTables() {
		Connection conn = openConnection();
		if (conn == null) {
			plugin.getLogger().severe(Ansi.ansi().fg(Ansi.Color.RED) + "Ingen database funnet, aktiverer White-List!"
					+ Ansi.ansi().fg(Ansi.Color.DEFAULT));
			Bukkit.getServer().setWhitelist(true);
			return;
		}
		BuildTables.createTables(conn);
		SqlQuery query = new SqlQuery(conn);
		query.selectWorlds();
		new MultiWorld().loadWorlds();
		query.selectPlayers();
		query.selectHomes();
		query.selectPlaces();
		closeConnection(conn);
    }
    
    public void saveTables() {
		Connection conn = openConnection();
		if (conn == null) return;
		SqlQuery query = new SqlQuery(conn);
		query.insertPlayers();
		query.insertWorlds();
		query.insertHomes();
		query.insertPlaces();
		closeConnection(conn);
    }

    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
        } catch (SQLException e) {
        	plugin.getLogger().log(Level.INFO, "MySQL DB not found, trying to create one");
            try {
	            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port, this.user, this.password);
	            connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS " + this.database + ";");
	            connection.close();
	            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
	            plugin.getLogger().log(Level.INFO, "MySQL DB created (DB name: " + this.database + ")");
			} catch (SQLException e1) {
	            plugin.getLogger().log(Level.SEVERE, "Could not connect to MySQL server! because: " + e1.getMessage());
			}
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
        }
        return connection;
    }
    
    public void closeConnection(Connection conn) {
    	try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
