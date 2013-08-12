package net.kiwz.ThePlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {
	
	public void giveItem(CommandSender sender, Command cmd, String[] args) {
		ChatColor dgreen = ChatColor.DARK_GREEN;
		ChatColor red = ChatColor.RED;
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		Player receivingPlayer = null;
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

			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller og ett item!");
			}
			
			else {
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
		}
		
		if (args.length == 2 && args[0].toString().matches("[0-9]+")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(red + "Spesifiser en spiller!");
			}
			
			else {
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
		
		if (args.length == 2 && !args[0].toString().matches("[0-9]+")) {
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					receivingPlayer = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
			
			if (receivingPlayer != null) {
				for (Material mat : Material.values()) {
					int matId = mat.getId();
					String matName = Material.getMaterial(matId).toString();
					
					if (matName.startsWith(args[1].toUpperCase())) {
						id = matId;
						break;
					}
				}
				
				if (id > 0) {
					String material = Material.getMaterial(id).toString();
					ItemStack item = new ItemStack(id, 1, (short) 0);
					receivingPlayer.getInventory().addItem(new ItemStack[] { item });
					sender.sendMessage(dgreen + "Du ga 1 " + material + " til " + receivingPlayer.getName());
					receivingPlayer.sendMessage(dgreen + "Du fikk 1 " + material + " av " + sender.getName());
				}
			}
		}
		
		if (args.length == 3 && args[1].toString().matches("[0-9]+")) {
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					receivingPlayer = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
			
			if (receivingPlayer != null) {
				for (Material mat : Material.values()) {
					int matId = mat.getId();
					String matName = Material.getMaterial(matId).toString();
					
					if (matName.startsWith(args[2].toUpperCase())) {
						id = matId;
						break;
					}
				}
				
				if (id > 0) {
					int amount = Integer.parseInt(args[1]);
					String material = Material.getMaterial(id).toString();
					ItemStack item = new ItemStack(id, amount, (short) 0);
					receivingPlayer.getInventory().addItem(new ItemStack[] { item });
					sender.sendMessage(dgreen + "Du ga " + args[1] + " " + material + " til " + receivingPlayer.getName());
					receivingPlayer.sendMessage(dgreen + "Du fikk " + args[1] + " " + material + " av " + sender.getName());
				}
			}
		}
		
		if (args.length == 4 && args[1].toString().matches("[0-9]+") && args[3].toString().matches("[0-9]+")) {
			
			for (Player playername : Bukkit.getServer().getOnlinePlayers()) {
				if (playername.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					receivingPlayer = Bukkit.getServer().getPlayer(playername.getName());
				}
			}
			
			if (receivingPlayer != null) {
				for (Material mat : Material.values()) {
					int matId = mat.getId();
					String matName = Material.getMaterial(matId).toString();
					
					if (matName.startsWith(args[2].toUpperCase())) {
						id = matId;
						break;
					}
				}
				
				if (id > 0) {
					int amount = Integer.parseInt(args[1]);
					short dmg = (short) Integer.parseInt(args[3]);
					String material = Material.getMaterial(id).toString();
					ItemStack item = new ItemStack(id, amount, dmg);
					receivingPlayer.getInventory().addItem(new ItemStack[] { item });
					sender.sendMessage(dgreen + "Du ga " + args[1] + " " + material + " til " + receivingPlayer.getName());
					receivingPlayer.sendMessage(dgreen + "Du fikk " + args[1] + " " + material + " av " + sender.getName());
				}
			}
		}
	}
}
