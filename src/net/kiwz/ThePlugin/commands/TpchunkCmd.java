package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyWorld;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpchunkCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new TpchunkCmd().tp(sender, args);
	}
	
	private boolean tp(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (args.length != 4 || player == null) {
			sender.sendMessage(Color.WARNING + "Du m� skrive navnet p� verdenen, chunk x, koordinat y og chunk z");
			return true;
		}
		
		MyWorld myWorld = MyWorld.getWorld(args[0]);
		int x = Util.parseInt(args[1]);
		int y = Util.parseInt(args[2]);
		int z = Util.parseInt(args[3]);
		if (myWorld == null) {
			sender.sendMessage(Color.WARNING + args[0] + " finnes ikke");
		} else {	
			Chunk chunk = server.getWorld(myWorld.getName()).getChunkAt(x, z);
			Location loc = chunk.getBlock(8, y, 8).getLocation();
			player.teleport(loc);
			sender.sendMessage(Color.INFO + "Du ble teleportert til �nsket chunk");
		}
		return true;
	}
}
