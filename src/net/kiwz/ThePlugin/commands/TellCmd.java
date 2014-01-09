package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.command.CommandSender;

public class TellCmd {
	public static boolean exec(CommandSender sender, String[] args) {
		return new TellCmd().tell(sender, args);
	}
	
	private boolean tell(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Du må skrive navnet på en spiller");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
			} else if (args.length == 1) {
				sender.sendMessage(Color.WARNING + "Du må skrive en melding til " + MyPlayer.getColorName(myTarget));
			} else {
				String message = "";
				args[0] = "";
				for (String s : args) {
					message = message + s + " ";
				}
				message = MyPlayer.getColorName(mySender) + Color.WHISPER + " -> " + MyPlayer.getColorName(myTarget) + Color.WHISPER + ": " + message;
				myTarget.getOnlinePlayer().sendMessage(message);
				sender.sendMessage(message);
				ReplayCmd.replays.put(myTarget, mySender);
				ReplayCmd.replays.put(mySender, myTarget);
			}
		}
		return true;
	}
}
