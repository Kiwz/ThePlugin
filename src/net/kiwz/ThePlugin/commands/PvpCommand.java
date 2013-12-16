package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.command.CommandSender;

public class PvpCommand {
	private ArrayList<String> pvpPlayers = ThePlugin.pvpPlayers;
	
	public void pvp(CommandSender sender) {
		if (pvpPlayers.contains(sender.getName())) {
			pvpPlayers.remove(sender.getName());
			sender.sendMessage(ThePlugin.c2 + "PvP er PÅ");
		}
		else {
			pvpPlayers.add(sender.getName());
			sender.sendMessage(ThePlugin.c1 + "PvP er AV");
		}
	}
}
