package net.kiwz.ThePlugin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.SqlConnection;
import net.kiwz.ThePlugin.mysql.SqlQuery;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.fusesource.jansi.Ansi;

public class ServerManager {
	private static BukkitTask stopTask = null;
	
	public static void start() {
		ServerManager sm = new ServerManager();
		sm.unban();
	    sm.save();
	    sm.autoStop();
	    sm.setFilter();
	}
	
	private void unban() {
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(ThePlugin.getPlugin(), new Runnable() {
			public void run() {
				for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
					if (myPlayer.isBanned() && myPlayer.getBanExpire() < System.currentTimeMillis() / 1000) {
						myPlayer.setBanned(false, 0, "", "");
					}
				}
			}
		}, 20, 200);
	}
	
	private void save() {
		Bukkit.getServer().getScheduler().runTaskTimer(ThePlugin.getPlugin(), new Runnable() {
            public void run() {
				long time = System.currentTimeMillis();
				Connection conn = new SqlConnection().getConnection();
				if (conn == null) {
					ThePlugin.getPlugin().getLogger().severe(Ansi.ansi().fg(Ansi.Color.RED) + "Ingen database funnet, restarter serveren!"
							+ Ansi.ansi().fg(Ansi.Color.DEFAULT));
					ThePlugin.error = true;
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
						player.kickPlayer(Color.WARNING + "Det har desverre skjedd noe feil med serveren og den vil restarte!");
					}
					Bukkit.getServer().shutdown();
				} else {
					SqlQuery query = new SqlQuery(conn);
					for (MyWorld myWorld : MyWorld.getWorlds()) query.updateWorld(myWorld);
					for (MyPlayer myPlayer : MyPlayer.getPlayers()) query.updatePlayer(myPlayer);
					for (Place place : Place.getPlaces()) query.updatePlace(place);
					for (Home home : Home.getHomes()) query.updateHome(home);
					for (WoolChest woolChest : WoolChest.getWoolChests()) query.updateWoolChest(woolChest);
					try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
					
					Bukkit.getServer().savePlayers();
					for (World world : Bukkit.getWorlds()) {
						world.save();
					}
					time = System.currentTimeMillis() - time;
					broadcastMsg(Color.SERVER + "Lagring fullført (" + time + "ms)");
				}
			}
		}, 12000, 12000);
	}
	
	private void autoStop() {
		stopTask = Bukkit.getServer().getScheduler().runTaskTimer(ThePlugin.getPlugin(), new Runnable() {
            public void run() {
				int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				int min = Calendar.getInstance().get(Calendar.MINUTE);
				if ((hour == 01 || hour == 13 || hour == 17) && (min >= 55)) {
					broadcastMsg(Color.SERVER + "*** Server restarter om ca 5 minutter ***");
					Bukkit.getServer().getScheduler().runTaskLater(ThePlugin.getPlugin(), new Runnable() {
						public void run() {
							broadcastMsg(Color.SERVER + "*** Server restarter ***");
							Bukkit.getServer().getScheduler().runTaskLater(ThePlugin.getPlugin(), new Runnable() {
								public void run() {
									for (Player player : Bukkit.getServer().getOnlinePlayers()) {
										player.kickPlayer("Serveren restarter, kom tilbake om 1 minutt");
									}
									Bukkit.shutdown();
								}
							}, 60);
						}
					}, 5900);
					stopTask.cancel();
				}
			}
		}, 28800, 20);
	}
	
	public static void logString(String string) {
		int unix = (int) (System.currentTimeMillis() / 1000);
		try {
			File file = new File("player.log");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			bw.write(Util.getTimeLogDate(unix) + " " + string);
			bw.write("\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setFilter() {
		Logger coreLogger = (Logger) LogManager.getRootLogger();
		coreLogger.addFilter(new Filter() {
			@Override
			public Result filter(LogEvent event) {
				for (String filter : Config.getConfig().getFilters()) {
					if (event.getMessage().toString().contains(filter)) {
						return Result.DENY;
					}
				}
				return null;
			}

			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object... arg4) {
				return null;
			}

			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2, Object arg3, Throwable arg4) {
				return null;
			}

			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2, Message arg3, Throwable arg4) {
				return null;
			}

			@Override
			public Result getOnMatch() {
				return null;
			}

			@Override
			public Result getOnMismatch() {
				return null;
			}
		});
	}
    
    private void broadcastMsg(String string) {
    	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
    		player.sendMessage(string);
    	}
    }
}
