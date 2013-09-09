package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoisCommand {
	
	public void whois(CommandSender sender, String[] args) {
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		sender.sendMessage(ThePlugin.c1 + "Det er " + players.length + "/" + Bukkit.getServer().getMaxPlayers() + " spillere online");
		StringBuilder header = new StringBuilder();
		for (Player player : players) {
			header.append(player.getName() + ", ");
		}
		try {
			header.substring(0, 1);
			sender.sendMessage(header.toString());
		}
		catch (StringIndexOutOfBoundsException e) {
		}
	}
}
