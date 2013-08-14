package net.kiwz.ThePlugin;

import java.sql.SQLException;

import net.kiwz.ThePlugin.Commands;
import net.kiwz.ThePlugin.listeners.PlayerInteractListener;
import net.kiwz.ThePlugin.listeners.CommandListener;
import net.kiwz.ThePlugin.listeners.InventoryListener;
import net.kiwz.ThePlugin.listeners.LoginListener;
import net.kiwz.ThePlugin.mysql.CreateTables;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	
	private PluginManager pm = Bukkit.getServer().getPluginManager();
	
	@Override
	public void onEnable() {
		
		CreateTables cDB = new CreateTables();
		try {
			cDB.createTables();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Commands cmds = new Commands();
		getCommand("feed").setExecutor(cmds);
		getCommand("fly").setExecutor(cmds);
	    getCommand("give").setExecutor(cmds);
		getCommand("gm").setExecutor(cmds);
		getCommand("heal").setExecutor(cmds);
		getCommand("hjelp").setExecutor(cmds);
	    getCommand("openinv").setExecutor(cmds);
	    getCommand("teleport").setExecutor(cmds);
		getCommand("test").setExecutor(cmds);
		
	    LoginListener loginL = new LoginListener();
	    pm.registerEvents(loginL, this);
	    
	    CommandListener commandL = new CommandListener();
	    pm.registerEvents(commandL, this);
	    
	    InventoryListener InventoryL = new InventoryListener();
	    pm.registerEvents(InventoryL, this);
	    
	    PlayerInteractListener BlockChangeL = new PlayerInteractListener();
	    pm.registerEvents(BlockChangeL, this);

	}
	
	@Override
	public void onDisable() {
		
		
	}
}
