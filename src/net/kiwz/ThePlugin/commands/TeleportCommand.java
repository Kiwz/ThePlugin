package net.kiwz.ThePlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand {
	
	public void tp(CommandSender sender, Command cmd, String[] args) {
		ChatColor dgreen = ChatColor.DARK_GREEN;
		ChatColor red = ChatColor.RED;
		Player playerSender = Bukkit.getServer().getPlayer(sender.getName());
		Player traveler = null;
		Player destination = null;
		
		if (args.length == 0) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser to spillere, hvem som skal bli teleportert til hvem!");
			}
			
			else {
				sender.sendMessage(red + "Spesifiser en spiller!");
			}
		}
		
		if (args.length == 1) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser hvem spilleren skal bli teleportert til!");
			}
			
			else {
				
				for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
					
					if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
						destination = Bukkit.getServer().getPlayer(playername.getName());
					}
				}
				
				if (destination != null) {
					playerSender.teleport(destination);
					playerSender.sendMessage(dgreen + "Du ble teleportert til " + destination.getName());
				}
				
				else {
					sender.sendMessage(red + "Fant ingen spiller som passet dette navnet!");
				}
			}
		}
		
		if (args.length == 2) {
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					traveler = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				
				if (playername.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
					destination = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
		
			if ((traveler != null) || (destination != null)) {
				traveler.teleport(destination);
				sender.sendMessage("Du teleporterte " + traveler.getName() + " til " + destination.getName());
				traveler.sendMessage(dgreen + "Du ble teleportert til " + destination.getName() + " av " + sender.getName());
			}
			
			else {
				sender.sendMessage(red + "Fant ingen spiller som passet en eller begge navnene!");
			}
		}
	}
}
