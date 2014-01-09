package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class MemCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender) {
		return new MemCmd().mem(sender);
	}
	
	public boolean mem(CommandSender sender) {
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory() / 1048576L;
		long allocatedMemory = runtime.totalMemory() / 1048576L;
		long freeMemory = runtime.freeMemory() / 1048576L;
		long usedMemory = allocatedMemory - freeMemory;
		
		List<String> list = new ArrayList<String>();
		for (World world : server.getWorlds()) {
			int e = world.getEntities().size();
			int c = world.getLoadedChunks().length;
			list.add(Color.INFO + world.getName() + ": " + Color.VARIABLE + c + " Chunks, " + e + " Entities");
		}
		Util.sendAsPages(sender, "1", 10, usedMemory + "MB/" + maxMemory + "MB", "", list);
		return true;
	}
}
