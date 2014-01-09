package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.command.CommandSender;

public class FlyCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new FlyCmd().fly(sender, args);
	}
	
	public boolean fly(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (args.length == 0 && mySender == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else if (args.length == 0) {
			if (mySender.getOnlinePlayer().getAllowFlight()) {
				mySender.getOnlinePlayer().setAllowFlight(false);
				sender.sendMessage(Color.INFO + "Du kan ikke fly");
			} else {
				mySender.getOnlinePlayer().setAllowFlight(true);
				sender.sendMessage(Color.INFO + "Du kan fly");
			}
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
			} else {
				if (myTarget.getOnlinePlayer().getAllowFlight()) {
					myTarget.getOnlinePlayer().setAllowFlight(false);
					myTarget.getOnlinePlayer().sendMessage(MyPlayer.getColorName(mySender) + " skrudde av fly modus");
					sender.sendMessage(MyPlayer.getColorName(myTarget) + " kan ikke fly");
				}
				else {
					myTarget.getOnlinePlayer().setAllowFlight(true);
					myTarget.getOnlinePlayer().sendMessage(MyPlayer.getColorName(mySender) + " skrudde på fly modus");
					sender.sendMessage(MyPlayer.getColorName(myTarget) + " kan fly");
				}
			}
		}
		return true;
	}
}
