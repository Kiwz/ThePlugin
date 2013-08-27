package net.kiwz.ThePlugin.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Logger;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.ConnectToMySQL;
import net.kiwz.ThePlugin.mysql.Homes;
import net.kiwz.ThePlugin.mysql.Places;
import net.kiwz.ThePlugin.mysql.Players;
import net.kiwz.ThePlugin.mysql.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ServerManagement {
	static boolean warning = false;
	
	/**
	 * <p>This will run forever and checking if it is time for a restart</p>
	 */
	public void autoStop() {
		final Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
				@Override
                public void run() {
					ChatColor pink = ChatColor.LIGHT_PURPLE;
					int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
					int min = Calendar.getInstance().get(Calendar.MINUTE);
					int sec = Calendar.getInstance().get(Calendar.SECOND);
					if ((hour == 01 || hour == 13 || hour == 17) && (min == 55 && sec == 00 && !ServerManagement.warning)) {
						ServerManagement.warning = true;
						pl.getLogger().warning(pink + "*** Server restarter om 5 minutter ***");
					}
					if ((hour == 01 || hour == 13 || hour == 17) && (min == 59 && sec == 57 && ServerManagement.warning)) {
						pl.getLogger().warning(pink + "*** Server restarter ***");
					}
					if ((hour == 02 || hour == 14 || hour == 18) && (min == 00 && sec == 00 && ServerManagement.warning)) {
						for (Player player : Bukkit.getServer().getOnlinePlayers()) {
							player.kickPlayer("Serveren restarter, kom tilbake om 1 minutt");
						}
						Bukkit.shutdown();
					}
				}
		}, 28800L, 10L);
	}
	
	/**
	 * 
	 * @param period as long
	 * 
	 * <p>This will run forever and save world, players and update MySQL tables</p>
	 */
	public void save(long period) {
		final Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
				@Override
                public void run() {
					Logger log = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin").getLogger();
					ChatColor pink = ChatColor.LIGHT_PURPLE;
					long time = System.currentTimeMillis();
					Bukkit.getServer().broadcastMessage(pink + "Lagrer serveren");
					
					ConnectToMySQL MySQL = new ConnectToMySQL();
					Connection conn = MySQL.openConnection();
					
					Homes homes = new Homes();
					Places places = new Places();
					Players players = new Players();
					Worlds worlds = new Worlds();

					time = System.currentTimeMillis();
					homes.setTableHomes(conn, ThePlugin.getHomes);
					time = System.currentTimeMillis() - time;
					log.info(ThePlugin.c2 + "Lagring av Homes (" + time + "ms)");
					time = System.currentTimeMillis();
					places.setTablePlaces(conn, ThePlugin.getPlaces);
					time = System.currentTimeMillis() - time;
					log.info(ThePlugin.c2 + "Lagring av Places (" + time + "ms)");
					time = System.currentTimeMillis();
					players.setTablePlayers(conn, ThePlugin.getPlayers);
					time = System.currentTimeMillis() - time;
					log.info(ThePlugin.c2 + "Lagring av Players (" + time + "ms)");
					time = System.currentTimeMillis();
					worlds.setTableWorlds(conn, ThePlugin.getWorlds);
					time = System.currentTimeMillis() - time;
					log.info(ThePlugin.c2 + "Lagring av Worlds (" + time + "ms)");
					time = System.currentTimeMillis();
					
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					Bukkit.getServer().savePlayers();
					for (World world : Bukkit.getWorlds()) {
						world.save();
					}
					
					time = System.currentTimeMillis() - time;
					Bukkit.getServer().broadcastMessage(pink + "Lagring fullført (" + time + "ms)");
				}
		}, period, period);
	}
}
