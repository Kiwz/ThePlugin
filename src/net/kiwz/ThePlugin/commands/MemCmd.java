package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;

public class MemCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new MemCmd().mem(sender, args);
	}
	
	public boolean mem(CommandSender sender, String[] args) {
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory() / 1048576L;
		long allocatedMemory = runtime.totalMemory() / 1048576L;
		long freeMemory = runtime.freeMemory() / 1048576L;
		long usedMemory = allocatedMemory - freeMemory;

		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("----- ");
		header.append(ChatColor.WHITE);
		header.append(usedMemory + "MB/" + maxMemory + "MB");
		header.append(ChatColor.YELLOW);
		header.append(" -----");
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		
		for (World world : server.getWorlds()) {
			int e = world.getEntities().size();
			int c = world.getLoadedChunks().length;
			sender.sendMessage(Color.INFO + world.getName() + ": " + Color.VARIABLE + c + " Chunks, " + e + " Entities");
		}
		return true;
	}
}
