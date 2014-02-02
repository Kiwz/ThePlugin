package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.command.CommandSender;

public class ChestCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new ChestCmd().chest(sender, args);
	}
	
	private boolean chest(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/chest " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
			mySender.setWoolChestOwner(mySender);
			sender.sendMessage(Color.INFO + "Du vil nå se dine egne kister");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				mySender.setWoolChestOwner(myTarget);
				sender.sendMessage(Color.INFO + "Du vil nå se kistene til " + MyPlayer.getColorName(myTarget));
			}
		}
		return true;
	}
}
