package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OiCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new OiCmd().oi(sender, args);
	}
	
	private boolean oi(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (player == null) {
			sender.sendMessage(Color.WARNING + "/oi kan bare brukes av spillere");
		} else if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else if (MyPlayer.getPlayer(args[0]) != null) {
			MyPlayer myPlayer = MyPlayer.getPlayer(args[0]);
			Inventory inventory = myPlayer.getBukkitPlayer().getInventory();
			player.openInventory(inventory);
		} else {
			sender.sendMessage(Color.WARNING + args[0] + " har aldri spilt her");
		}
		return true;
	}
}
