package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.Config;
import org.bukkit.plugin.Plugin;

public class SqlConnection {
	private Config config = Config.getConfig();
	private final Plugin plugin = ThePlugin.getPlugin();
    private final String hostname = config.getHost();
    private final String port = config.getPort();
    private final String database = config.getDatabase();
    private final String user = config.getUser();
    private final String password = config.getPassword();
    private Connection connection = null;

    public Connection getConnection() {
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
}
