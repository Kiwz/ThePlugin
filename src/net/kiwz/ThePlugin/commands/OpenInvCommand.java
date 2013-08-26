package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.OfflinePlayer;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OpenInvCommand {
	
	public boolean openInv(CommandSender sender, Command cmd, String[] args) {
		OnlinePlayer onlinePlayer = new OnlinePlayer();
		OfflinePlayer offlinePlayer = new OfflinePlayer();

		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "/openinv kan bare brukes av spillere");
			return true;
		}
		else if (args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Spesifiser en spiller");
			return true;
		}
		else if (onlinePlayer.getPlayer(args[0]) != null) {
			Player target = onlinePlayer.getPlayer(args[0]);
			Inventory inventory = target.getInventory();
			Bukkit.getServer().getPlayer(sender.getName()).openInventory(inventory);
			return true;
		}
		else if (offlinePlayer.getPlayer(args[0]) != null) {
			Player target = offlinePlayer.getPlayer(args[0]);
			Inventory inventory = target.getInventory();
			Bukkit.getServer().getPlayer(sender.getName()).openInventory(inventory);
			return true;
		}
		else {
			sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			return true;
		}
	}
}
