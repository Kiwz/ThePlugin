package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;

import org.bukkit.command.CommandSender;

public class PvpCmd {
	private static List<String> pvpPlayers = new ArrayList<String>();
	
	public static List<String> getPvpPlayers() {
		return pvpPlayers;
	}
	
	public static boolean exec(CommandSender sender) {
		return new PvpCmd().pvp(sender);
	}
	
	private boolean pvp(CommandSender sender) {
		if (PvpCmd.pvpPlayers.contains(sender.getName())) {
			PvpCmd.pvpPlayers.remove(sender.getName());
			sender.sendMessage(Color.UNSAFE + "PvP er PÅ");
		} else {
			PvpCmd.pvpPlayers.add(sender.getName());
			sender.sendMessage(Color.SAFE + "PvP er AV");
		}
		return true;
	}
}
