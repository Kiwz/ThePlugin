package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly {
	
	public void flyMode(CommandSender sender, Command cmd, String[] args) {
		
		ChatColor dgreen = ChatColor.DARK_GREEN;
		ChatColor red = ChatColor.RED;
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		Player receivingPlayer = null;
		
		if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller!");
			}
			
			else {
				if (player.getAllowFlight()) {
					player.setAllowFlight(false);
					sender.sendMessage(dgreen + "Du kan ikke fly!");
				}
				else {
					player.setAllowFlight(true);
					sender.sendMessage(dgreen + "Du kan fly!");
				}
			}
		}
		
		if (args.length >= 1 && !sender.getName().toLowerCase().startsWith(args[0].toLowerCase())){
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					receivingPlayer = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
			if (receivingPlayer != null) {
				if (receivingPlayer.getAllowFlight()) {
					receivingPlayer.setAllowFlight(false);
					sender.sendMessage(dgreen + receivingPlayer.getName() + " kan ikke fly!");
					receivingPlayer.sendMessage(dgreen + sender.getName() + " skrudde av fly modus!");
				}
				else {
					receivingPlayer.setAllowFlight(true);
					sender.sendMessage(dgreen + receivingPlayer.getName() + " kan fly!");
					receivingPlayer.sendMessage(dgreen + sender.getName() + " skrudde på fly modus!");
				}
			}
			
			else sender.sendMessage(red + "Fant ingen spiller som passet dette navnet!");
		}
	}
}
