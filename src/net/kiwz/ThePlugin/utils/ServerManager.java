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
	    sm.autoStop();
	    sm.save(10L);
	    sm.setFilter();
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
	
	private void autoStop() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePlugin.getPlugin(), new Runnable() {
				@Override
                public void run() {
					int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
					int min = Calendar.getInstance().get(Calendar.MINUTE);
					int sec = Calendar.getInstance().get(Calendar.SECOND);
					if ((hour == 01 || hour == 13 || hour == 17) && (min == 55 && sec == 00 && !ServerManager.warning)) {
						ServerManager.warning = true;
						MyPlayer.broadcastMsg(Color.SERVER + "*** Server restarter om 5 minutter ***");
					}
					if ((hour == 01 || hour == 13 || hour == 17) && (min == 59 && sec == 57 && ServerManager.warning)) {
						MyPlayer.broadcastMsg(Color.SERVER + "*** Server restarter ***");
					}
					if ((hour == 02 || hour == 14 || hour == 18) && (min == 00 && sec == 00 && ServerManager.warning)) {
						for (Player player : Bukkit.getServer().getOnlinePlayers()) {
							player.kickPlayer("Serveren restarter, kom tilbake om 1 minutt");
						}
						Bukkit.shutdown();
					}
				}
		}, 28800L, 10L);
	}
	
	private void save(long period) {
		period = period * 1200;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePlugin.getPlugin(), new Runnable() {
				@Override
                public void run() {
					long time = System.currentTimeMillis();
					MyPlayer.broadcastMsg(Color.SERVER + "Lagrer...");

					new ConnectToMySQL().saveTables();
					Bukkit.getServer().savePlayers();
					for (World world : Bukkit.getWorlds()) {
						world.save();
					}
					
					time = System.currentTimeMillis() - time;
					MyPlayer.broadcastMsg(Color.SERVER + "Lagring fullf�rt (" + time + "ms)");
				}
		}, period, period);
	}
	
	private void setFilter() {
		org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
		coreLogger.addFilter(new Filter() {
			@Override
			public Result filter(LogEvent event) {
				String msg = event.getMessage().toString();
				String filter[] = { "Server permissions file", "Reached end of stream for", "issued server command:",
						"DSCT: socket closed", "Connection reset", "lost connection", "logged in with entity id", "moved wrongly!",
						"Disconnecting", "achievement", "UUID" };
				for (String thisFilter : filter) {
					if (msg.contains(thisFilter)) {
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
}