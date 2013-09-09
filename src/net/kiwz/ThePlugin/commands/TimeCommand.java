package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand {
	
	public void time(CommandSender sender, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "/tid kan bare brukes av spillere");
		}
		
		else if (args.length > 0) {
			if (args[0].equalsIgnoreCase("dag")) {
				player.setPlayerTime(6000L, false);
				sender.sendMessage(ThePlugin.c1 + "Din tid er dag");
			}
			else if (args[0].equalsIgnoreCase("natt")) {
				player.setPlayerTime(18000L, false);
				sender.sendMessage(ThePlugin.c1 + "Din tid er natt");
			}
			else {
				player.resetPlayerTime();
				sender.sendMessage(ThePlugin.c1 + "Din tid er lik serveren sin tid");
			}
		}
		
		else {
			player.resetPlayerTime();
			sender.sendMessage(ThePlugin.c1 + "Din tid er lik serveren sin tid");
		}
	}
}
