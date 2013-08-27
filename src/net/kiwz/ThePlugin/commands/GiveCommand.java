package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.Items;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {
	
	public boolean giveItem(CommandSender sender, Command cmd, String[] args) {
		OnlinePlayer onlinePlayer = new OnlinePlayer();
		Items item = new Items();
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (args.length == 0 || (args.length == 1 && !(sender instanceof Player)) ||
				(args.length == 2 && args[0].matches("[0-9]+") && !(sender instanceof Player)) ||
				(args.length == 3 && args[0].matches("[0-9]+") && !(sender instanceof Player))) {
			return false;
		}
		else if (args.length == 1) {
			ItemStack itemStack = item.getItem(args[0]);
			if (itemStack != null) {
				player.getInventory().addItem(itemStack);
				String itemName = itemStack.getType().toString();
				sender.sendMessage(ThePlugin.c1 + "Du fikk 1 " + itemName);
			}
			else {
				sender.sendMessage(ThePlugin.c2 + args[0] + " finnes ikke");
			}
			return true;
		}
		else if (args.length == 2 && args[0].matches("[0-9]+")) {
			ItemStack itemStack = item.getItem(args[1], args[0]);
			if (itemStack != null) {
				player.getInventory().addItem(itemStack);
				String itemName = itemStack.getType().toString();
				sender.sendMessage(ThePlugin.c1 + "Du fikk " + args[0] + " " + itemName);
			}
			else {
				sender.sendMessage(ThePlugin.c2 + args[1] + " finnes ikke");
			}
			return true;
		}
		else if (args.length == 2) {
			Player target = onlinePlayer.getPlayer(args[0]);
			ItemStack itemStack = item.getItem(args[1]);
			if (target != null) {
				if (itemStack != null) {
					target.getInventory().addItem(itemStack);
					String itemName = itemStack.getType().toString();
					target.sendMessage(ThePlugin.c1 + "Du fikk 1 " + itemName + " av " + sender.getName());
					sender.sendMessage(ThePlugin.c1 + "Du ga 1 " + itemName + " til " + target.getName());
				}
				else {
					sender.sendMessage(ThePlugin.c2 + args[1] + " finnes ikke");
				}
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			}
			return true;
		}
		else if (args.length == 3 && args[0].matches("[0-9]+") && args[2].matches("[0-9]+")) {
			ItemStack itemStack = item.getItem(args[1], args[0], args[2]);
			if (itemStack != null) {
				player.getInventory().addItem(itemStack);
				String itemName = itemStack.getType().toString();
				String itemDamage = Short.toString(itemStack.getDurability());
				sender.sendMessage(ThePlugin.c1 + "Du fikk " + args[0] + " " + itemName + ":" + itemDamage);
			}
			else {
				sender.sendMessage(ThePlugin.c2 + args[1] + ":" + args[2] + " finnes ikke");
			}
			return true;
		}
		else if (args.length == 3 && args[1].matches("[0-9]+")) {
			Player target = onlinePlayer.getPlayer(args[0]);
			ItemStack itemStack = item.getItem(args[2], args[1]);
			if (target != null) {
				if (itemStack != null) {
					target.getInventory().addItem(itemStack);
					String itemName = itemStack.getType().toString();
					target.sendMessage(ThePlugin.c1 + "Du fikk " + args[1] + " " + itemName + " av " + sender.getName());
					sender.sendMessage(ThePlugin.c1 + "Du ga " + args[1] + " " + itemName + " til " + target.getName());
				}
				else {
					sender.sendMessage(ThePlugin.c2 + args[2] + " finnes ikke");
				}
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			}
			return true;
		}
		else if (args.length == 4 && args[1].matches("[0-9]+") && args[3].matches("[0-9]+")) {
			Player target = onlinePlayer.getPlayer(args[0]);
			ItemStack itemStack = item.getItem(args[2], args[1], args[3]);
			if (target != null) {
				if (itemStack != null) {
					target.getInventory().addItem(itemStack);
					String itemName = itemStack.getType().toString();
					String itemDamage = Short.toString(itemStack.getDurability());
					target.sendMessage(ThePlugin.c1 + "Du fikk " + args[1] + " " + itemName + ":" + itemDamage + " av " + sender.getName());
					sender.sendMessage(ThePlugin.c1 + "Du ga " + args[1] + " " + itemName + ":" + itemDamage + " til " + target.getName());
				}
				else {
					sender.sendMessage(ThePlugin.c2 + args[2] + ":" + args[3] + " finnes ikke");
				}
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			}
			return true;
		}
		else {
			return false;
		}
	}
}
