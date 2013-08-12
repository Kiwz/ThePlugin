package net.kiwz.ThePlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand {
	
	public void heal(CommandSender sender, Command cmd, String[] args) {
		
		ChatColor dgreen = ChatColor.DARK_GREEN;
		ChatColor red = ChatColor.RED;
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		Player receivingPlayer = null;
		
		if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller!");
			}
			
			else {
				player.setHealth(20);;
				sender.sendMessage(dgreen + "Du fikk fullt liv!");
			}
		}
		
		if (args.length >= 1 && !sender.getName().toLowerCase().startsWith(args[0].toLowerCase())){
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					receivingPlayer = Bukkit.getServer().getPlayer(playername.getName());
				}
				else player = null;
			}
			if (receivingPlayer != null) {
				receivingPlayer.setHealth(20);;
				sender.sendMessage(dgreen + receivingPlayer.getName() + " har fått fullt liv!");
				receivingPlayer.sendMessage(dgreen + sender.getName() + " ga deg fullt liv!");
			}
			
			else sender.sendMessage(red + "Fant ingen spiller som passet dette navnet!");
		}
	}
}
