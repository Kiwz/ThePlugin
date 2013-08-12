package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.Commands;
import net.kiwz.ThePlugin.listeners.CommandListener;
import net.kiwz.ThePlugin.listeners.LoginListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	
	private PluginManager pm = Bukkit.getServer().getPluginManager();
	
	@Override
	public void onEnable() {
		
		Commands cmds = new Commands();
		getCommand("feed").setExecutor(cmds);
		getCommand("fly").setExecutor(cmds);
	    getCommand("give").setExecutor(cmds);
		getCommand("gm").setExecutor(cmds);
		getCommand("heal").setExecutor(cmds);
		getCommand("hjelp").setExecutor(cmds);
	    getCommand("teleport").setExecutor(cmds);
		getCommand("test").setExecutor(cmds);
		
	    LoginListener lListener = new LoginListener();
	    pm.registerEvents(lListener, this);
	    
	    CommandListener cmdListener = new CommandListener();
	    pm.registerEvents(cmdListener, this);

	}
	
	@Override
	public void onDisable() {
		// Todo some crazy shit!
	}
}
