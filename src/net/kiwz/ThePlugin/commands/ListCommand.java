package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class ListCommand {
	
	public void list(CommandSender sender, String[] args) {
	    Permissions perm = new Permissions();
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		sender.sendMessage(ThePlugin.c1 + "Det er " + players.length + "/" + Bukkit.getServer().getMaxPlayers() + " spillere online");
		StringBuilder header = new StringBuilder();
		for (Player player : players) {
			String playerName = player.getName();
			if (perm.isAdmin(player) || player.isOp()) {
				header.append(ChatColor.RED + playerName + ChatColor.WHITE);
				header.append(", ");
			}
			else {
				header.append(playerName);
				header.append(", ");
			}
		}
		String playerNames = header.toString();
		if (playerNames.length() > 0) {
			playerNames = playerNames.substring(0, header.length() - 2);
		}
		String[] msg = ChatPaginator.wordWrap(playerNames + " ", 55);
		sender.sendMessage(msg);
	}
}
