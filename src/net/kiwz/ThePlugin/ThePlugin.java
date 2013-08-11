package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.Commands;
import net.kiwz.ThePlugin.LoginListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePlugin extends JavaPlugin {
	
	private PluginManager pm = Bukkit.getServer().getPluginManager();
	
	@Override
	public void onEnable() {
		Commands cmds = new Commands();
		getCommand("gm").setExecutor(cmds);
		getCommand("fly").setExecutor(cmds);
		getCommand("heal").setExecutor(cmds);
		getCommand("mat").setExecutor(cmds);
		getCommand("test").setExecutor(cmds);
		
	    LoginListener ll = new LoginListener();
	    pm.registerEvents(ll, this);
	}
	
	@Override
	public void onDisable() {
		// Todo some crazy shit!
	}
}