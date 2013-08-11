package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mat {
	
	public void mat(CommandSender sender, Command cmd, String[] args) {
		
		ChatColor dgreen = ChatColor.DARK_GREEN;
		ChatColor red = ChatColor.RED;
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (args.length >= 1 && !sender.getName().toLowerCase().startsWith(args[0].toLowerCase())){
			Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
			for (Player playername : onlinePlayers) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					player = Bukkit.getServer().getPlayer(playername.getName());
				}
				else player = null;
			}
			if (player != null) {
				player.setFoodLevel(20);;;
				sender.sendMessage(dgreen + player.getName() + " har blitt mett!");
				player.sendMessage(dgreen + sender.getName() + " ga deg masse mat!");
			}
			
			else sender.sendMessage(red + "Fant ingen spiller som passet dette navnet!");
		}
		
		if (args.length < 1 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller!");
			}
			
			else {
				player.setFoodLevel(20);;
				sender.sendMessage(dgreen + "Du ble mett!");
			}
		}
	}
}