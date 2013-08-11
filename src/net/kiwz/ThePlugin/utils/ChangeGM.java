package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeGM {
	
	public void gameMode(CommandSender sender, Command cmd, String[] args) {
		
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
				String gmStatus = player.getGameMode().toString();
				if (gmStatus == "SURVIVAL") {
					player.setGameMode(GameMode.CREATIVE);
					sender.sendMessage(dgreen + player.getName() + " er nå i Creative modus!");
					player.sendMessage(dgreen + sender.getName() + " endret modusen din til Creative!");
				}
				if (gmStatus == "CREATIVE") {
					player.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage(dgreen + player.getName() + " er nå i Survival modus!");
					player.sendMessage(dgreen + sender.getName() + " endret modusen din til Survival!");
				}
			}
			
			else sender.sendMessage(red + "Fant ingen spiller som passet dette navnet!");
		}
		
		if (args.length < 1 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller!");
			}
			
			else {
				String gmStatus = player.getGameMode().toString();
				if (gmStatus == "SURVIVAL") {
					player.setGameMode(GameMode.CREATIVE);
					sender.sendMessage(dgreen + "Du er nå i Creative modus!");
				}
				if (gmStatus == "CREATIVE") {
					player.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage(dgreen + "Du er nå i Survival modus!");
				}
			}
		}
	}
}
