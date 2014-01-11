package net.kiwz.ThePlugin.commands;

import java.util.HashMap;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.command.CommandSender;

public class ReplayCmd {
	public static HashMap<MyPlayer, MyPlayer> replays = new HashMap<MyPlayer, MyPlayer>();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new ReplayCmd().replay(sender, args);
	}
	
	private boolean replay(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		MyPlayer myTarget = replays.get(mySender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/replay " + Color.WARNING + "kan bare brukes av spillere");
		} else if (myTarget == null) {
			sender.sendMessage(Color.WARNING + "Det er ingen du kan svare");
		} else if (myTarget.getOnlinePlayer() == null) {
			sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
		} else if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Du må skrive en melding");
		} else {
			String message = "";
			for (String s : args) {
				message = message + s + " ";
			}
			message = MyPlayer.getColorName(mySender) + Color.WHISPER + " -> " + MyPlayer.getColorName(myTarget) + Color.WHISPER + ": " + message;
			myTarget.getOnlinePlayer().sendMessage(message);
			sender.sendMessage(message);
			for (MyPlayer spies : SpyCmd.getSpyPlayers()) {
				if (!mySender.equals(spies) && !myTarget.equals(spies)) {
					spies.getOnlinePlayer().sendMessage(Color.WARNING + "[SPY] " + message);
				}
			}
			replays.put(myTarget, mySender);
		}
		return true;
	}
}
