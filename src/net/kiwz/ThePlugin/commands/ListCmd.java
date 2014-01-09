package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class ListCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new ListCmd().list(sender, args);
	}
	
	private boolean list(CommandSender sender, String[] args) {
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		sender.sendMessage(Color.INFO + "Det er " + players.length + "/" + server.getMaxPlayers() + " spillere online");
		StringBuilder header = new StringBuilder();
		for (Player player : players) {
			header.append(MyPlayer.getColorName(MyPlayer.getPlayer(player)) + ", ");
		}
		String playerNames = header.toString();
		if (playerNames.length() > 0) {
			playerNames = playerNames.substring(0, playerNames.length() - 2);
		}
		String[] msg = ChatPaginator.wordWrap(playerNames + " ", 55);
		sender.sendMessage(msg);
		return true;
	}
}
