package net.kiwz.ThePlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand {
	
	public void gameMode(CommandSender sender, Command cmd, String[] args) {
		ChatColor gold = ChatColor.GOLD;
		ChatColor red = ChatColor.RED;
		Server server = Bukkit.getServer();
		Player playerA = server.getPlayer(sender.getName());
		Player playerB = null;
		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(red + "Spesifiser en spiller");
		}
		else {
			if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
				String gmStatus = playerA.getGameMode().toString();
				if (gmStatus == "SURVIVAL") {
					playerA.setGameMode(GameMode.CREATIVE);
					sender.sendMessage(gold + "Du er nå i Creative modus");
				}
				if (gmStatus == "CREATIVE") {
					playerA.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage(gold + "Du er nå i Survival modus");
				}
			}
			else {
				for (Player player : server.getOnlinePlayers()) {
					if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
						playerB = server.getPlayer(player.getName());
					}
				}
				if (playerB != null) {
					String gmStatus = playerB.getGameMode().toString();
					if (gmStatus == "SURVIVAL") {
						playerB.setGameMode(GameMode.CREATIVE);
						sender.sendMessage(gold + playerB.getName() + " er nå i Creative modus");
						playerB.sendMessage(gold + sender.getName() + " endret modusen din til Creative");
					}
					if (gmStatus == "CREATIVE") {
						playerB.setGameMode(GameMode.SURVIVAL);
						sender.sendMessage(gold + playerB.getName() + " er nå i Survival modus");
						playerB.sendMessage(gold + sender.getName() + " endret modusen din til Survival");
					}
				}
				else {
					sender.sendMessage(red + "Fant ingen spiller som passet dette navnet");
				}
			}
		}
	}
}
