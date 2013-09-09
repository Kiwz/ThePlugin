package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Ticks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class TPSCommand {
	
	public void tps(CommandSender sender, String[] args) {
		Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
		World world = Bukkit.getServer().getWorlds().get(0);
		long delay = 1L;
		
		if (args.length > 0 && args[0].matches("[0-9]+")) {
			delay = Long.parseLong(args[0]);
		}
		
		if (delay > 60) {
			delay = 60L;
		}
		
		if (delay < 1) {
			delay = 1L;
		}
		
		if (!sender.isOp()) {
			delay = 10L;
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl,
				new Ticks(sender, delay, world, world.getFullTime(), System.nanoTime()), delay * 20L);
	}
}
