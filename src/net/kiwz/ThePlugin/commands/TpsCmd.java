package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.Perm;
import net.kiwz.ThePlugin.utils.Ticks;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class TpsCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new TpsCmd().tps(sender, args);
	}
	
	private boolean tps(CommandSender sender, String[] args) {
		World world = server.getWorlds().get(0);
		int delay = 1;
		
		if (args.length > 0) {
			delay = Util.parseInt(args[0]);
		}
		
		if (delay > 60) {
			delay = 60;
		}
		
		if (delay < 1) {
			delay = 1;
		}
		
		if (!Perm.isAdmin(sender.getName())) {
			delay = 10;
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThePlugin.getPlugin(),
				new Ticks(sender, delay, world, world.getFullTime(), System.nanoTime()), delay * 20);
		return true;
	}
}
