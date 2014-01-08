package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;

import net.kiwz.ThePlugin.utils.Color;

import org.bukkit.command.CommandSender;

public class PvpCmd {
	private static ArrayList<String> pvpPlayers = new ArrayList<String>();
	
	public static ArrayList<String> getPvpPlayers() {
		return pvpPlayers;
	}
	
	public static boolean exec(CommandSender sender) {
		return new PvpCmd().pvp(sender);
	}
	
	private boolean pvp(CommandSender sender) {
		if (PvpCmd.pvpPlayers.contains(sender.getName())) {
			PvpCmd.pvpPlayers.remove(sender.getName());
			sender.sendMessage(Color.UNSAFE + "PvP er P�");
		}
		else {
			PvpCmd.pvpPlayers.add(sender.getName());
			sender.sendMessage(Color.SAFE + "PvP er AV");
		}
		return true;
	}
}