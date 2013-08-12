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
		//Player receivingPlayer = null;
		int id = 0;
		
		if (args.length == 0) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller og ett item!");
			}
			
			else {
				sender.sendMessage(red + "Spesifiser ett item!");
			}
		}
		
		if (args.length == 1) {
			
			for (Material mat : Material.values()) {
				int matId = mat.getId();
				String matName = Material.getMaterial(matId).toString();
				
				if (matName.startsWith(args[0].toUpperCase())) {
					id = matId;
					break;
				}
			}
			
			if (id > 0) {
				String material = Material.getMaterial(id).toString();
				ItemStack item = new ItemStack(id, 1, (short) 0);
				player.getInventory().addItem(new ItemStack[] { item });
				sender.sendMessage(dgreen + "Du fikk 1 " + material);
			}
		}
		
		if (args.length == 2 && args[0].toString().matches("[0-9]+")) {
			
			for (Material mat : Material.values()) {
				int matId = mat.getId();
				String matName = Material.getMaterial(matId).toString();
				
				if (matName.startsWith(args[1].toUpperCase())) {
					id = matId;
					break;
				}
			}
			
			if (id > 0) {
				int amount = Integer.parseInt(args[0]);
				String material = Material.getMaterial(id).toString();
				ItemStack item = new ItemStack(id, amount, (short) 0);
				player.getInventory().addItem(new ItemStack[] { item });
				sender.sendMessage(dgreen + "Du fikk " + args[0] + " " + material);
			}
		}
	}
}
