package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.command.CommandSender;

public class PvpCmd {
	
	public static boolean exec(CommandSender sender) {
		return new PvpCmd().pvp(sender);
	}
	
	private boolean pvp(CommandSender sender) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/pvp " + Color.WARNING + "kan bare brukes av spillere");
		} else if (mySender.isPvp()) {
			mySender.setPvp(false);
			sender.sendMessage(Color.SAFE + "PvP er AV");
		} else {
			mySender.setPvp(true);
			sender.sendMessage(Color.UNSAFE + "PvP er PÅ");
		}
		return true;
	}
}
