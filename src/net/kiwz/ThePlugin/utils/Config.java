package net.kiwz.ThePlugin.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	private static Config config = new Config();
	private String host = "127.0.0.1";
	private String port = "3306";
	private String database = "theplugin";
	private String user = "rooty";
	private String password = "booty";
	private List<String> filters = new ArrayList<String>();
	private List<String> admins = new ArrayList<String>();
	private List<String> rules = new ArrayList<String>();
	private List<String> areaBlocks = new ArrayList<String>();
	private List<String> spotBlocks = new ArrayList<String>();
	
	public static Config getConfig() {
		return config;
	}
	
	public String getHost() {
		return host;
	}
	public String getPort() {
		return port;
	}
	public String getDatabase() {
		return database;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	public List<String> getFilters() {
		return filters;
	}
	public List<String> getAdmins() {
		return admins;
	}
	public List<String> getRules() {
		return rules;
	}
	public List<String> getAreaBlocks() {
		return areaBlocks;
	}
	public List<String> getSpotBlocks() {
		return spotBlocks;
	}
	
	private String hostPath = "MySQL.Host";
	private String portPath = "MySQL.Port";
	private String databasePath = "MySQL.Database";
	private String userPath = "MySQL.User";
	private String passwordPath = "MySQL.Password";
	private String filtersPath = "Filters";
	private String adminsPath = "Admins";
	private String rulesPath = "Rules";
	private String areaBlocksPath = "Place.Spawn.AreaBlocks";
	private String spotBlocksPath = "Place.Spawn.SpotBlocks";
	
	public void loadConfig() {
		File file = new File(ThePlugin.getPlugin().getDataFolder(), "config.yml");
		
		YamlConfiguration conf = new YamlConfiguration();
		conf.addDefault(hostPath, host);
		conf.addDefault(portPath, port);
		conf.addDefault(databasePath, database);
		conf.addDefault(userPath, user);
		conf.addDefault(passwordPath, password);
		filters.add("Server permissions file");
		filters.add("UUID");
		filters.add("logged in with entity id");
		filters.add("Disconnecting");
		filters.add("lost connection");
		filters.add("Connection reset");
		filters.add("DSCT: socket closed");
		filters.add("Reached end of stream for");
		filters.add("moved wrongly!");
		filters.add("moved too quickly!");
		filters.add("issued server command:");
		filters.add("achievement");
		filters.add("[WorldEdit] No compatible nms block class found.");
		filters.add("logget");
		conf.addDefault(filtersPath, filters);
		admins.add("Kiwz");
		conf.addDefault(adminsPath, admins);
		rules.add("Regler får nummer foran seg i chatten");
		conf.addDefault(rulesPath, rules);
		areaBlocks.add("ENUM navn til blokken");
		conf.addDefault(areaBlocksPath, areaBlocks);
		spotBlocks.add("ENUM navn til blokken");
		conf.addDefault(spotBlocksPath, spotBlocks);
		conf.options().copyDefaults(true);
		
		try { conf.load(file); } catch (IOException | InvalidConfigurationException e) { }
		try { conf.save(file); } catch (IOException e) { }
		
		host = conf.getString(hostPath, host);
		port = conf.getString(portPath, port);
		database = conf.getString(databasePath, database);
		user = conf.getString(userPath, user);
		password = conf.getString(passwordPath, password);
		filters = conf.getStringList(filtersPath);
		admins = conf.getStringList(adminsPath);
		rules = conf.getStringList(rulesPath);
		areaBlocks = conf.getStringList(areaBlocksPath);
		spotBlocks = conf.getStringList(spotBlocksPath);
	}
}
