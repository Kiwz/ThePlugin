package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand {
	
	public boolean gameMode(CommandSender sender, Command cmd, String[] args) {
		OnlinePlayer onlinePlayer = new OnlinePlayer();
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Spesifiser en spiller");
			return true;
		}
		else if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			if (player.getGameMode().toString().equalsIgnoreCase("SURVIVAL")) {
				player.setGameMode(GameMode.CREATIVE);
				sender.sendMessage(ThePlugin.c1 + "Du er nå i Creative modus");
			}
			else {
				player.setGameMode(GameMode.SURVIVAL);
				sender.sendMessage(ThePlugin.c1 + "Du er nå i Survival modus");
			}
			return true;
		}
		else if (onlinePlayer.getPlayer(args[0]) != null) {
			Player target = onlinePlayer.getPlayer(args[0]);
			if (target.getGameMode().toString().equalsIgnoreCase("SURVIVAL")) {
				target.setGameMode(GameMode.CREATIVE);
				target.sendMessage(ThePlugin.c1 + sender.getName() + " endret modusen din til Creative");
				sender.sendMessage(ThePlugin.c1 + target.getName() + " er nå i Creative modus");
			}
			else {
				target.setGameMode(GameMode.SURVIVAL);
				target.sendMessage(ThePlugin.c1 + sender.getName() + " endret modusen din til Survival");
				sender.sendMessage(ThePlugin.c1 + target.getName() + " er nå i Survival modus");
			}
			return true;
		}
		else {
			sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			return true;
		}
	}
}
