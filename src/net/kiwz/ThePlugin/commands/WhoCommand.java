package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoCommand {
	
	public void who(CommandSender sender, String[] args) {
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		sender.sendMessage(ThePlugin.c1 + "Det er " + players.length + "/" + Bukkit.getServer().getMaxPlayers() + " spillere online");
		StringBuilder header = new StringBuilder();
		boolean comma = false;
		for (Player player : players) {
			if (comma) {
				header.append(", ");
			}
			header.append(player.getName());
			comma = true;
		}
		try {
			header.substring(2, 2);
			sender.sendMessage(header.toString());
		}
		catch (StringIndexOutOfBoundsException e) {
		}
	}
}
