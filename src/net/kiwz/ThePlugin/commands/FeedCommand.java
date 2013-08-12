package net.kiwz.ThePlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand {
	
	public void feed(CommandSender sender, Command cmd, String[] args) {
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
				playerA.setFoodLevel(20);
				sender.sendMessage(gold + "Du ble mett");
			}
			else {
				for (Player player : server.getOnlinePlayers()) {
					if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
						playerB = server.getPlayer(player.getName());
					}
				}
				if (playerB != null) {
					playerB.setFoodLevel(20);
					sender.sendMessage(gold + playerB.getName() + " ble mett");
					playerB.sendMessage(gold + sender.getName() + " gjorde deg mett");
				}
				else {
					sender.sendMessage(red + "Fant ingen spiller som passet dette navnet");
				}
			}
		}
	}
}
