package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

public class GmCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new GmCmd().gm(sender, args);
	}
	
	public boolean gm(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		GameMode survival = GameMode.SURVIVAL;
		GameMode creative = GameMode.CREATIVE;
		
		if (args.length == 0 && mySender == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else if (args.length == 0) {
			if (mySender.getOnlinePlayer().getGameMode().equals(survival)) {
				mySender.getOnlinePlayer().setGameMode(creative);
				sender.sendMessage(Color.INFO + "Du er nå i Creative modus");
			} else {
				mySender.getOnlinePlayer().setGameMode(survival);
				sender.sendMessage(Color.INFO + "Du er nå i Survival modus");
			}
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
			} else {
				if (myTarget.getOnlinePlayer().getGameMode().equals(survival)) {
					myTarget.getOnlinePlayer().setGameMode(creative);
					myTarget.getOnlinePlayer().sendMessage(MyPlayer.getColorName(mySender) + " endret modusen din til Creative");
					sender.sendMessage(MyPlayer.getColorName(myTarget) + " er nå i Creative modus");
				} else {
					myTarget.getOnlinePlayer().setGameMode(survival);
					myTarget.getOnlinePlayer().sendMessage(MyPlayer.getColorName(myTarget) + " endret modusen din til Survival");
					sender.sendMessage(MyPlayer.getColorName(myTarget) + " er nå i Survival modus");
				}
			}
		}
		return true;
	}
}
