package net.kiwz.ThePlugin;

import java.sql.Connection;
import java.sql.SQLException;

import net.kiwz.ThePlugin.listeners.BlockListener;
import net.kiwz.ThePlugin.listeners.EntityListener;
import net.kiwz.ThePlugin.listeners.MoveListener;
import net.kiwz.ThePlugin.listeners.PlayerListener;
import net.kiwz.ThePlugin.mysql.BuildTables;
import net.kiwz.ThePlugin.mysql.SqlConnection;
import net.kiwz.ThePlugin.mysql.SqlQuery;
import net.kiwz.ThePlugin.utils.Config;
import net.kiwz.ThePlugin.utils.Dynmap;
import net.kiwz.ThePlugin.utils.FillWorld;
import net.kiwz.ThePlugin.utils.Home;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.MyWorld;
import net.kiwz.ThePlugin.utils.Perm;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.ServerManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;

public class ThePlugin extends JavaPlugin {
	private final static String thisPlugin = "ThePlugin";
	private PluginManager pm = Bukkit.getServer().getPluginManager();
	public static boolean error = false;
	
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin(thisPlugin);
	}
	
	public void onEnable() {
		new Config().getConfig(this);
		
		Connection conn = new SqlConnection().getConnection();
		if (conn == null) {
			this.getLogger().severe(Ansi.ansi().fg(Ansi.Color.RED) + "Ingen database funnet, aktiverer White-List!"
					+ Ansi.ansi().fg(Ansi.Color.DEFAULT));
			Bukkit.getServer().setWhitelist(true);
			error = true;
			pm.disablePlugin(this);
			return;
		} else {
			BuildTables.createTables(conn);
			SqlQuery query = new SqlQuery(conn);
			query.loadWorlds();
			query.loadPlayers();
			query.loadPlaces();
			query.loadHomes();
			query.selectWoolChests();
			try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
    	new Dynmap().markTheMap();
		Perm.setPermissions();
		ServerManager.start();
		FillWorld.restoreFill();
		
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
		if (!error) {
			FillWorld.pauseFill();
			Connection conn = new SqlConnection().getConnection();
			if (conn != null) {
				SqlQuery query = new SqlQuery(conn);
				for (MyWorld myWorld : MyWorld.getWorlds()) query.updateWorld(myWorld);
				for (MyPlayer myPlayer : MyPlayer.getPlayers()) query.updatePlayer(myPlayer);
				for (Place place : Place.getPlaces()) query.updatePlace(place);
				for (Home home : Home.getHomes()) query.updateHome(home);
				query.insertWoolChests();
				try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}
}
