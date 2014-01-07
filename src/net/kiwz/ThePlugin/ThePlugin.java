package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.listeners.BlockListener;
import net.kiwz.ThePlugin.listeners.EntityListener;
import net.kiwz.ThePlugin.listeners.MoveListener;
import net.kiwz.ThePlugin.listeners.PlayerListener;
import net.kiwz.ThePlugin.mysql.ConnectToMySQL;
import net.kiwz.ThePlugin.utils.Config;
import net.kiwz.ThePlugin.utils.Dynmap;
import net.kiwz.ThePlugin.utils.Perm;
import net.kiwz.ThePlugin.utils.ServerManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	private final static String thisPlugin = "ThePlugin";
	private PluginManager pm = Bukkit.getServer().getPluginManager();
	
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin(thisPlugin);
	}
	
	public void onEnable() {
		new Config().getConfig(this);
		new ConnectToMySQL().loadTables();
    	new Dynmap().markTheMap();
		Perm.setPermissions();
		ServerManager.start();
		
		Commands cmds = new Commands();
		for (String key : getDescription().getCommands().keySet()) {
			getCommand(key).setExecutor(cmds);
		}
		
	    pm.registerEvents(new PlayerListener(), this);
	    pm.registerEvents(new EntityListener(), this);
	    pm.registerEvents(new BlockListener(), this);
	    pm.registerEvents(new MoveListener(), this);
	}
	
	public void onDisable() {
		new ConnectToMySQL().saveTables();
	}
}
