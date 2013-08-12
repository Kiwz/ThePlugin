package net.kiwz.ThePlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand {
	
	public void fly(CommandSender sender, Command cmd, String[] args) {
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
				if (playerA.getAllowFlight()) {
					playerA.setAllowFlight(false);
					sender.sendMessage(gold + "Du kan ikke fly");
				}
				else {
					playerA.setAllowFlight(true);
					sender.sendMessage(gold + "Du kan fly");
				}
			}
			else {
				for (Player player : server.getOnlinePlayers()) {
					if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
						playerB = server.getPlayer(player.getName());
					}
				}
				if (playerB != null) {
					if (playerB.getAllowFlight()) {
						playerB.setAllowFlight(false);
						sender.sendMessage(gold + playerB.getName() + " kan ikke fly");
						playerB.sendMessage(gold + sender.getName() + " skrudde av fly modus");
					}
					else {
						playerB.setAllowFlight(true);
						sender.sendMessage(gold + playerB.getName() + " kan fly");
						playerB.sendMessage(gold + sender.getName() + " skrudde på fly modus");
					}
				}
				else {
					sender.sendMessage(red + "Fant ingen spiller som passet dette navnet");
				}
			}
		}
	}
}
