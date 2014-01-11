package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;

public class OiCmd {
	
	public static boolean execEnder(CommandSender sender, String[] args) {
		return new OiCmd().oe(sender, args);
	}
	
	public static boolean execPlayer(CommandSender sender, String[] args) {
		return new OiCmd().oi(sender, args);
	}
	
	private boolean oe(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/oe " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				Inventory inventory = myTarget.getOfflinePlayer().getEnderChest();
				mySender.getOnlinePlayer().openInventory(inventory);
				sender.sendMessage(MyPlayer.getColorName(myTarget));
			}
		}
		return true;
	}
	
	private boolean oi(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/oi " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				Inventory inventory = myTarget.getOfflinePlayer().getInventory();
				mySender.getOnlinePlayer().openInventory(inventory);
				sender.sendMessage(MyPlayer.getColorName(myTarget));
			}
		}
		return true;
	}
}
