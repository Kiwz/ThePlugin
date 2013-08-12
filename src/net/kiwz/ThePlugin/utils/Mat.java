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
		Player receivingPlayer = null;
		
		if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller!");
			}
			
			else {
				player.setFoodLevel(20);;
				sender.sendMessage(dgreen + "Du ble mett!");
			}
		}
		
		if (args.length >= 1 && !sender.getName().toLowerCase().startsWith(args[0].toLowerCase())){
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					receivingPlayer = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
			
			if (receivingPlayer != null) {
				receivingPlayer.setFoodLevel(20);;;
				sender.sendMessage(dgreen + receivingPlayer.getName() + " har blitt mett!");
				receivingPlayer.sendMessage(dgreen + sender.getName() + " ga deg masse mat!");
			}
			
			else sender.sendMessage(red + "Fant ingen spiller som passet dette navnet!");
		}
	}
}
