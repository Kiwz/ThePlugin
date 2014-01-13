package net.kiwz.ThePlugin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.ConnectToMySQL;

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

public class ServerManager {
	private static boolean warning = false;
	
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
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(ThePlugin.getPlugin(), new Runnable() {
			@Override
            public void run() {
				new ConnectToMySQL().saveTables();
			}
		}, 200, 200);
		
		Bukkit.getServer().getScheduler().runTaskTimer(ThePlugin.getPlugin(), new Runnable() {
			@Override
            public void run() {
				long time = System.currentTimeMillis();
				broadcastMsg(Color.SERVER + "Lagrer...");
				Bukkit.getServer().savePlayers();
				for (World world : Bukkit.getWorlds()) {
					world.save();
				}
				time = System.currentTimeMillis() - time;
				broadcastMsg(Color.SERVER + "Lagring fullført (" + time + "ms)");
			}
		}, 12000, 12000);
	}
	
	private void autoStop() {
		Bukkit.getServer().getScheduler().runTaskTimer(ThePlugin.getPlugin(), new Runnable() {
            public void run() {
				int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				int min = Calendar.getInstance().get(Calendar.MINUTE);
				int sec = Calendar.getInstance().get(Calendar.SECOND);
				if ((hour == 01 || hour == 13 || hour == 17) && (min == 55 && sec == 00 && !ServerManager.warning)) {
					ServerManager.warning = true;
					broadcastMsg(Color.SERVER + "*** Server restarter om 5 minutter ***");
				}
				if ((hour == 01 || hour == 13 || hour == 17) && (min == 59 && sec == 57 && ServerManager.warning)) {
					broadcastMsg(Color.SERVER + "*** Server restarter ***");
				}
				if ((hour == 02 || hour == 14 || hour == 18) && (min == 00 && sec == 00 && ServerManager.warning)) {
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
						player.kickPlayer("Serveren restarter, kom tilbake om 1 minutt");
					}
					Bukkit.shutdown();
				}
			}
		}, 28800, 10);
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
		org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
		coreLogger.addFilter(new Filter() {
			@Override
			public Result filter(LogEvent event) {
				for (String filter : Config.getFilters()) {
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
