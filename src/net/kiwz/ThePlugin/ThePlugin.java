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
import net.kiwz.ThePlugin.mysql.Players;
import net.kiwz.ThePlugin.mysql.Worlds;
import net.kiwz.ThePlugin.utils.ConsoleFilter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	
	private PluginManager pm = Bukkit.getServer().getPluginManager();
	private Plugin pl = pm.getPlugin("ThePlugin");
	private Logger logServer = Logger.getLogger("Minecraft-Server");
	private MySQL MySQL = new MySQL(pl, "IPstring", "3306", "theplugin", "user", "pass");
	private Connection conn;
	private Homes homes = new Homes();
	private Places places = new Places();
	private Players players = new Players();
	private Worlds worlds = new Worlds();
	public static HashMap<String, Homes> getHomes;
	public static HashMap<Integer, Places> getPlaces;
	public static HashMap<String, Players> getPlayers;
	public static HashMap<String, Worlds> getWorlds;
	
	@Override
	public void onLoad() {
		
		logServer.setFilter(new ConsoleFilter());
		
		this.conn = MySQL.openConnection();
		
		MakeTables makeTables = new MakeTables();
		makeTables.createTables(this.conn);

		ThePlugin.getHomes = homes.getTableHomes(this.conn);
		ThePlugin.getPlaces = places.getTablePlaces(this.conn);
		ThePlugin.getPlayers = players.getTablePlayers(this.conn);
		ThePlugin.getWorlds = worlds.getTableWorlds(this.conn);
		
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	@Override
	public void onDisable() {
		
		this.conn = MySQL.openConnection();

		homes.setTableHomes(this.conn, ThePlugin.getHomes);
		places.setTablePlaces(this.conn, ThePlugin.getPlaces);
		players.setTablePlayers(this.conn, ThePlugin.getPlayers);
		worlds.setTableWorlds(this.conn, ThePlugin.getWorlds);

		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
