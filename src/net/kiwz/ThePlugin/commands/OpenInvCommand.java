package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.OfflinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OpenInvCommand {
	
	public boolean openInv(CommandSender sender, Command cmd, String[] args) {
		ChatColor red = ChatColor.RED;
		Server server = Bukkit.getServer();
		if (sender instanceof Player && args.length == 1) {
			Player player = server.getPlayer(args[0]);
			if (player != null && player.isOnline()) {
				Inventory inventory = player.getInventory();
				Bukkit.getServer().getPlayer(sender.getName()).openInventory(inventory);
			}
			else {
				OfflinePlayer offlinePlayer = new OfflinePlayer();
				player = offlinePlayer.getOfflinePlayer(args[0]);
				if (player != null) {
					Inventory inventory = player.getInventory();
					Bukkit.getServer().getPlayer(sender.getName()).openInventory(inventory);
				}
				else {
					sender.sendMessage(red + "Fant ingen spiller som passet dette navnet");
				}
			}
		}
		else {
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Bare spillere kan bruke /openinv");
			}
			else {
				return false;
			}
		}
		return true;
	}
}
