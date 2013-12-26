package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPChunkCommand {
	
	public boolean tp(CommandSender sender, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		int x = 0;
		int y = 100;
		int z = 0;
		
		if (!(sender instanceof Player) || args.length != 4) {
			sender.sendMessage(ThePlugin.c2 + "Du må skrive navnet på verdenen, chunk x, koordinat y og chunk z");
			return true;
		}

		String worldName = args[0];
		if (Bukkit.getServer().getWorld(worldName) == null) {
			sender.sendMessage(ThePlugin.c2 + worldName + " finnes ikke");
			return true;
		}
		World world = Bukkit.getServer().getWorld(worldName);
		
		try {
			x = Integer.parseInt(args[1]);
			y = Integer.parseInt(args[2]);
			z = Integer.parseInt(args[3]);
		}
		catch (NumberFormatException e) {
			sender.sendMessage(ThePlugin.c2 + "Du må skrive navnet på verdenen, chunk x, koordinat y og chunk z");
			return true;
		}
		
		Chunk chunk = world.getChunkAt(x, z);
		Location loc = chunk.getBlock(8, y, 8).getLocation();
		player.teleport(loc);
		sender.sendMessage(ThePlugin.c1 + "Du ble teleportert til ønsket chunk");
		
		return true;
	}
}
