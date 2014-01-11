package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.command.CommandSender;

public class SpyCmd {
	private static List<MyPlayer> spyPlayers = new ArrayList<MyPlayer>();
	
	public static List<MyPlayer> getSpyPlayers() {
		return spyPlayers;
	}
	
	public static boolean exec(CommandSender sender) {
		return new SpyCmd().spy(sender);
	}
	
	private boolean spy(CommandSender sender) {
		if (spyPlayers.contains(MyPlayer.getPlayer(sender))) {
			spyPlayers.remove(MyPlayer.getPlayer(sender));
			sender.sendMessage(Color.WARNING + "SpyMsg er AV");
		} else {
			spyPlayers.add(MyPlayer.getPlayer(sender));
			sender.sendMessage(Color.INFO + "SpyMsg er PÅ");
		}
		return true;
	}
}
