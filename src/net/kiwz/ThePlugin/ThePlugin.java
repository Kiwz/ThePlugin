package net.kiwz.ThePlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import net.kiwz.ThePlugin.Commands;
import net.kiwz.ThePlugin.listeners.BlockListener;
import net.kiwz.ThePlugin.listeners.ChatListener;
import net.kiwz.ThePlugin.listeners.DeathListener;
import net.kiwz.ThePlugin.listeners.CommandListener;
import net.kiwz.ThePlugin.listeners.InventoryListener;
import net.kiwz.ThePlugin.listeners.JoinListener;
import net.kiwz.ThePlugin.listeners.QuitListener;
import net.kiwz.ThePlugin.mysql.Homes;
import net.kiwz.ThePlugin.mysql.MakeTables;
import net.kiwz.ThePlugin.mysql.MySQL;
import net.kiwz.ThePlugin.mysql.Places;
import net.kiwz.ThePlugin.utils.ConsoleFilter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	
	private static PluginManager pm = Bukkit.getServer().getPluginManager();
	private static Plugin pl = pm.getPlugin("ThePlugin");
	private Logger logServer = Logger.getLogger("Minecraft-Server");
	public static Connection conn;
	public static HashMap<String, Homes> getHomes;
	private Homes homes = new Homes();
	private Places places = new Places();
	public static HashMap<Integer, Places> getPlaces;
	
	@SuppressWarnings("static-access")
	@Override
	public void onLoad() {
		
		logServer.setFilter(new ConsoleFilter());
		
		MySQL MySQL = new MySQL(pl, "109.247.37.74", "3306", "theplugin", "kiwz", "test");
		//MySQL MySQL = new MySQL(pl, "127.0.0.1", "3306", "theplugin", "rooty", "booty");
		this.conn = MySQL.openConnection();
		
		MakeTables makeTables = new MakeTables();
		makeTables.createTables();
		
		this.getHomes = homes.getTableHomes();
		this.getPlaces = places.getTablePlaces();
	}
	
	@Override
	public void onEnable() {
		
		Commands cmds = new Commands();
		getCommand("feed").setExecutor(cmds);
		getCommand("fly").setExecutor(cmds);
	    getCommand("give").setExecutor(cmds);
		getCommand("gm").setExecutor(cmds);
		getCommand("heal").setExecutor(cmds);
		getCommand("hjelp").setExecutor(cmds);
		getCommand("home").setExecutor(cmds);
		getCommand("homeset").setExecutor(cmds);
	    getCommand("openinv").setExecutor(cmds);
	    getCommand("plass").setExecutor(cmds);
		getCommand("spawn").setExecutor(cmds);
		getCommand("spawnset").setExecutor(cmds);
	    getCommand("teleport").setExecutor(cmds);
		getCommand("test").setExecutor(cmds);
		
	    JoinListener loginL = new JoinListener();
	    pm.registerEvents(loginL, this);
		
	    QuitListener QuitL = new QuitListener();
	    pm.registerEvents(QuitL, this);
		
	    DeathListener DeathL = new DeathListener();
	    pm.registerEvents(DeathL, this);
		
	    ChatListener ChatL = new ChatListener();
	    pm.registerEvents(ChatL, this);
	    
	    CommandListener commandL = new CommandListener();
	    pm.registerEvents(commandL, this);
	    
	    InventoryListener InventoryL = new InventoryListener();
	    pm.registerEvents(InventoryL, this);
	    
	    //PlayerListener playerL = new PlayerListener();
	    //pm.registerEvents(playerL, this);
	    
	    BlockListener blockL = new BlockListener();
	    pm.registerEvents(blockL, this);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onDisable() {
		
		homes.setTableHomes(this.getHomes);
		places.setTablePlaces(this.getPlaces);

		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
