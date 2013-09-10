package net.kiwz.ThePlugin.mysql;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ConnectToMySQL {
	private final Plugin plugin;
    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    private Connection connection;

    /**
     * Creates a new MySQL instance
     * 
     */
    public ConnectToMySQL() {
    	String hName = null;
    	String uName = null;
    	String pass = null;
		try {
			File file = new File("plugins\\ThePlugin\\mysql.txt");
			if (file.createNewFile()) {
				PrintWriter writer = new PrintWriter("plugins\\ThePlugin\\mysql.txt");
				writer.println("127.0.0.1");
				writer.println("rooty");
				writer.println("booty");
				writer.close();
			}
			Scanner sc = new Scanner(file);
			hName = sc.nextLine();
			uName = sc.nextLine();
			pass = sc.nextLine();
			sc.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    	this.plugin = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
        this.hostname = hName;
        this.port = "3306";
        this.database = "theplugin";
        this.user = uName;
        this.password = pass;
        this.connection = null;
    }

    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
        } catch (SQLException e) {
        	plugin.getLogger().log(Level.INFO, "MySQL DB not found, trying to create one");
            try {
	            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port, this.user, this.password);
	            connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS theplugin;");
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
