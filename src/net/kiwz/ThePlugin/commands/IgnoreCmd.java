package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.command.CommandSender;

public class IgnoreCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new IgnoreCmd().ignore(sender, args);
	}
	
	public boolean ignore(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/ignorer " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
				List<String> list = new ArrayList<String>();
				for (MyPlayer myIgnored : mySender.getIgnored()) {
					list.add(MyPlayer.getColorName(myIgnored));
				}
				if (list.size() == 0) {
					list.add(Color.INFO + "Du har ikke ignorert noen enda");
				}
				Util.sendAsPages(sender, "1", 20, "Ignorerte spiller(e)", "", list);
		} else if (mySender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			sender.sendMessage(Color.WARNING + "Du kan ikke ignorere deg selv");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (mySender.remIgnored(myTarget)) {
				sender.sendMessage(Color.INFO + "Du kan nå se hva " + MyPlayer.getColorName(myTarget) + " skriver");
			} else {
				mySender.addIgnored(myTarget);
				sender.sendMessage(Color.INFO + "Du vil IKKE se hva " + MyPlayer.getColorName(myTarget) + " skriver");
			}
		}
		return true;
	}
}
