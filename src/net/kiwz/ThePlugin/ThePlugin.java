package net.kiwz.ThePlugin;

import java.sql.Connection;
import java.sql.SQLException;

import net.kiwz.ThePlugin.Commands;
import net.kiwz.ThePlugin.listeners.ChatListener;
import net.kiwz.ThePlugin.listeners.DeathListener;
import net.kiwz.ThePlugin.listeners.PlayerInteractListener;
import net.kiwz.ThePlugin.listeners.CommandListener;
import net.kiwz.ThePlugin.listeners.InventoryListener;
import net.kiwz.ThePlugin.listeners.JoinListener;
import net.kiwz.ThePlugin.listeners.QuitListener;
import net.kiwz.ThePlugin.mysql.CreateTables;
import net.kiwz.ThePlugin.mysql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	
	private static PluginManager pm = Bukkit.getServer().getPluginManager();
	private static Plugin pl = pm.getPlugin("ThePlugin");
	private static MySQL MySQL = new MySQL(pl, "109.247.37.74", "3306", "theplugin", "kiwz", "test");
	//private static MySQL MySQL = new MySQL(pl, "localhost", "3306", "theplugin", "rooty", "booty");
	public static Connection conn = MySQL.openConnection();
	
	@Override
	public void onEnable() {
		
		CreateTables cDB = new CreateTables();
		try {
			cDB.createTables(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Commands cmds = new Commands();
		getCommand("feed").setExecutor(cmds);
		getCommand("fly").setExecutor(cmds);
	    getCommand("give").setExecutor(cmds);
		getCommand("gm").setExecutor(cmds);
		getCommand("heal").setExecutor(cmds);
		getCommand("hjelp").setExecutor(cmds);
		getCommand("home").setExecutor(cmds);
	    getCommand("openinv").setExecutor(cmds);
		getCommand("sethome").setExecutor(cmds);
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
	    
	    PlayerInteractListener BlockChangeL = new PlayerInteractListener();
	    pm.registerEvents(BlockChangeL, this);

	}
	
	@Override
	public void onDisable() {

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
