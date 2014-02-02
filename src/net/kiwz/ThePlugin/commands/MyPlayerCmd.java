package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;

public class MyPlayerCmd {
	private String cmd;
	private String[] args;
	private CommandSender sender;
	private MyPlayer mySender = null;
	private String senderName = "";
	private MyPlayer myTarget = null;
	private String targetName = "";
	private String warning = "";
	private String page = "1";
	
	public MyPlayerCmd(CommandSender sender, String cmd, String[] args) {
		this.cmd = cmd;
		this.args = args;
		this.sender = sender;
		mySender = MyPlayer.getPlayer(sender);
		senderName = MyPlayer.getColorName(mySender);
		myTarget = mySender;
		targetName = senderName;
		
		if (mySender == null) {
			warning = Color.COMMAND + "/" + cmd + Color.WARNING + " kan bare brukes av spillere";
		} else if (args.length > 0) {
			page = args[0];
			myTarget = MyPlayer.getPlayer(args[0]);
			targetName = MyPlayer.getColorName(myTarget);
			if (myTarget == null) {
				warning = Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her";
			}
		}
	}
	
	public boolean exec() {
		if (chest() || ignorer() || oe() || oi() || pvp()) return true;
		else return false;
	}
	
	private boolean chest() {
		if (!cmd.equals("chest")) return false;

		if (myTarget == null) {
			sender.sendMessage(warning);
		} else {
			mySender.setWoolChestOwner(myTarget);
			sender.sendMessage(Color.INFO + "Du vil nå se kistene til " + targetName);
		}
		return true;
	}
	
	private boolean ignorer() {
		if (!cmd.equals("ignorer")) return false;
		
		if (myTarget == null) {
			sender.sendMessage(warning);
		} else if (mySender == myTarget) {
			List<String> list = new ArrayList<String>();
			for (MyPlayer myIgnored : mySender.getIgnored()) {
				list.add(MyPlayer.getColorName(myIgnored));
			}
			if (list.size() == 0) {
				list.add(Color.INFO + "Du har ikke ignorert noen enda");
			}
			Util.sendAsPages(sender, page, 10, "Ignorerte spiller(e)", "", list);
		} else if (mySender.remIgnored(myTarget)) {
			sender.sendMessage(Color.INFO + "Du kan nå se hva " + targetName + " skriver");
		} else {
			mySender.addIgnored(myTarget);
			sender.sendMessage(Color.INFO + "Du vil IKKE se hva " + targetName + " skriver");
		}
		return true;
	}
	
	private boolean oe() {
		if (!cmd.equals("oe")) return false;
		
		if (myTarget == null) {
			sender.sendMessage(warning);
		} else {
			Inventory inventory = myTarget.getOfflinePlayer().getEnderChest();
			mySender.getOnlinePlayer().openInventory(inventory);
			sender.sendMessage(targetName + ": EnderChest");
		}
		return true;
	}
	
	private boolean oi() {
		if (!cmd.equals("oi")) return false;
		
		if (myTarget == null) {
			sender.sendMessage(warning);
		} else {
			Inventory inventory = myTarget.getOfflinePlayer().getInventory();
			mySender.getOnlinePlayer().openInventory(inventory);
			sender.sendMessage(targetName + ": Inventory");
		}
		return true;
	}
	
	private boolean pvp() {
		if (!cmd.equals("pvp")) return false;
		
		if (myTarget == null) {
			sender.sendMessage(warning);
		} else {
			myTarget.setPvp(!myTarget.isPvp());
			if (myTarget.isPvp()) sender.sendMessage(Color.UNSAFE + "PvP er PÅ for " + targetName);
			else sender.sendMessage(Color.SAFE + "PvP er AV for " + targetName);
		}
		return true;
	}
}
