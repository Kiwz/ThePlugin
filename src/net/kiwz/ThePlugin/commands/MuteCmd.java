package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.command.CommandSender;

public class MuteCmd {
	
	public static boolean execSet(CommandSender sender, String[] args) {
		return new MuteCmd().mute(sender, args);
	}
	
	public static boolean execUnSet(CommandSender sender, String[] args) {
		return new MuteCmd().unMute(sender, args);
	}
	
	private boolean mute(CommandSender sender, String[] args) {
		List<String> list = new ArrayList<String>();
		for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
			if (myPlayer.isMuted()) list.add(MyPlayer.getColorName(myPlayer));
		}
		if (args.length == 0) {
			Util.sendAsPages(sender, "1", 0, "Liste over alle spillere som er muted:", "", list);
		} else if (args[0].length() <= 1) {
			Util.sendAsPages(sender, args[0], 0, "Liste over alle spillere som er muted:", "", list);
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				myTarget.setMuted(true);
				sender.sendMessage(MyPlayer.getColorName(myTarget) + " er muted");
			}
		}
		return true;
	}
	
	private boolean unMute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Du må skrive navnet på en spiller");
			sender.sendMessage(Color.WARNING + "Eller " + Color.VARIABLE + "/unmute all" + Color.WARNING + " for å unmute alle");
		} else if (args[0].equalsIgnoreCase("all")) {
			for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
				myPlayer.setMuted(false);
			}
			sender.sendMessage(Color.INFO + "Alle spillere er unmuted");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				myTarget.setMuted(false);
				sender.sendMessage(MyPlayer.getColorName(myTarget) + " er unmuted");
			}
		}
		return true;
	}
}
