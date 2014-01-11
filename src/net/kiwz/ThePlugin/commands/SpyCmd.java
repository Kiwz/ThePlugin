package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.command.CommandSender;

public class SpyCmd {
	
	public static boolean exec(CommandSender sender) {
		return new SpyCmd().spy(sender);
	}
	
	private boolean spy(CommandSender sender) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/spy " + Color.WARNING + "kan bare brukes av spillere");
		} else if (mySender.isSpy()) {
			mySender.setSpy(false);
			sender.sendMessage(Color.WARNING + "SpyMsg er AV");
		} else {
			mySender.setSpy(true);
			sender.sendMessage(Color.INFO + "SpyMsg er PÅ");
		}
		return true;
	}
}
