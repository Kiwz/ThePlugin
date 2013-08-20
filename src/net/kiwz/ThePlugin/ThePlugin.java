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
import net.kiwz.ThePlugin.mysql.BuildTables;
import net.kiwz.ThePlugin.mysql.MySQL;
import net.kiwz.ThePlugin.mysql.Places;
import net.kiwz.ThePlugin.mysql.Players;
import net.kiwz.ThePlugin.mysql.Worlds;
import net.kiwz.ThePlugin.utils.ConsoleFilter;
import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	private String ip = "109.247.37.74";
	private String port = "3306";
	private String db = "theplugin";
	private String user = "kiwz";
	private String pass = "";
	private PluginManager pm = Bukkit.getServer().getPluginManager();
	private Logger logServer = Logger.getLogger("Minecraft-Server");
	
	private Homes homes = new Homes();
	public static HashMap<String, Homes> getHomes;
	private Places places = new Places();
	public static HashMap<Integer, Places> getPlaces;
	private Players players = new Players();
	public static HashMap<String, Players> getPlayers;
	private Worlds worlds = new Worlds();
	public static HashMap<String, Worlds> getWorlds;
	
	@Override
	public void onLoad() {
		logServer.setFilter(new ConsoleFilter());
		
		MySQL MySQL = new MySQL(ip, port, db, user, pass);
		Connection conn = MySQL.openConnection();
		
		BuildTables makeTables = new BuildTables();
		makeTables.createTables(conn);

		ThePlugin.getHomes = homes.getTableHomes(conn);
		ThePlugin.getPlaces = places.getTablePlaces(conn);
		ThePlugin.getPlayers = players.getTablePlayers(conn);
		ThePlugin.getWorlds = worlds.getTableWorlds(conn);
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onEnable() {
		HandleWorlds hWorlds = new HandleWorlds();
		for (World world : Bukkit.getServer().getWorlds()) {
			if (!getWorlds.containsKey(world.getName())) {
				hWorlds.addWorld(Bukkit.getServer().getWorld("world"));
			}
		}
		
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
		MySQL MySQL = new MySQL(ip, port, db, user, pass);
		Connection conn = MySQL.openConnection();

		homes.setTableHomes(conn, ThePlugin.getHomes);
		places.setTablePlaces(conn, ThePlugin.getPlaces);
		players.setTablePlayers(conn, ThePlugin.getPlayers);
		worlds.setTableWorlds(conn, ThePlugin.getWorlds);

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
