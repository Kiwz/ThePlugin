package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.command.CommandSender;

public class HealCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new HealCmd().heal(sender, args);
	}
	
	public boolean heal(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (args.length == 0 && mySender == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else if (args.length == 0) {
			mySender.getOnlinePlayer().setHealth(20);
			mySender.getOnlinePlayer().setFoodLevel(20);
			mySender.getOnlinePlayer().setSaturation(20);
			sender.sendMessage(Color.INFO + "Du fikk fullt liv og masse mat");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
			} else {
				myTarget.getOnlinePlayer().setHealth(20);
				myTarget.getOnlinePlayer().setFoodLevel(20);
				myTarget.getOnlinePlayer().setSaturation(20);
				myTarget.getOnlinePlayer().sendMessage(MyPlayer.getColorName(mySender) + " ga deg fullt liv og masse mat");
				sender.sendMessage(MyPlayer.getColorName(myTarget) + " har fått fullt liv og masse mat");
			}
		}
		return true;
	}
}
