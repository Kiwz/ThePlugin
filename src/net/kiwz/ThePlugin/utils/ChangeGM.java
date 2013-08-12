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
		Player receivingPlayer = null;
		
		if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			
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
		
		if (args.length >= 1 && !sender.getName().toLowerCase().startsWith(args[0].toLowerCase())){
			Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
			for (Player playername : onlinePlayers) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					receivingPlayer = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
			
			if (receivingPlayer != null) {
				String gmStatus = receivingPlayer.getGameMode().toString();
				if (gmStatus == "SURVIVAL") {
					receivingPlayer.setGameMode(GameMode.CREATIVE);
					sender.sendMessage(dgreen + receivingPlayer.getName() + " er nå i Creative modus!");
					receivingPlayer.sendMessage(dgreen + sender.getName() + " endret modusen din til Creative!");
				}
				if (gmStatus == "CREATIVE") {
					receivingPlayer.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage(dgreen + receivingPlayer.getName() + " er nå i Survival modus!");
					receivingPlayer.sendMessage(dgreen + sender.getName() + " endret modusen din til Survival!");
				}
			}
			
			else sender.sendMessage(red + "Fant ingen spiller som passet dette navnet!");
		}
	}
}
