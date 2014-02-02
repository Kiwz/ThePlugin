package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new TimeCmd().time(sender, args);
	}
	
	private boolean time(CommandSender sender, String[] args) {
		Player player = server.getPlayer(sender.getName());
		
		if (player == null) {
			sender.sendMessage(Color.COMMAND + "/tid " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
			player.resetPlayerTime();
			sender.sendMessage(Color.INFO + "Din tid er lik serveren sin tid");
		} else {
			if (args[0].equalsIgnoreCase("dag")) {
				player.setPlayerTime(6000L, false);
				sender.sendMessage(Color.INFO + "Din tid er alltid dag");
			} else if (args[0].equalsIgnoreCase("natt")) {
				player.setPlayerTime(18000L, false);
				sender.sendMessage(Color.INFO + "Din tid er alltid natt");
			} else {
				player.resetPlayerTime();
				sender.sendMessage(Color.INFO + "Din tid er lik serveren sin tid");
			}
		}
		return true;
	}
}
