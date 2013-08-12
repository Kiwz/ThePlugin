package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItem {
	
	public void giveItem(CommandSender sender, Command cmd, String[] args) {
		ChatColor dgreen = ChatColor.DARK_GREEN;
		ChatColor red = ChatColor.RED;
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		Player receivingPlayer = null;
		
		if (args.length == 0) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller og ett item!");
			}
			
			else {
				sender.sendMessage(red + "Spesifiser ett item!");
			}
		}
		
		if (args.length == 1) {
			ItemStack item = new ItemStack(Material.getMaterial(args[0].toUpperCase()).getId(), 1, (short) 0);
			player.getInventory().addItem(new ItemStack[] { item });
		}
	}
}