package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplayCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new ReplayCmd().replay(sender, args);
	}
	
	private boolean replay(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/replay " + Color.WARNING + "kan bare brukes av spillere");
		} else {
			MyPlayer myTarget = mySender.getReplayTo();
			if (myTarget == null) {
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
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					MyPlayer mySpy = MyPlayer.getPlayer(player);
					if (mySpy.isSpy() && (!mySpy.equals(mySender) && !mySpy.equals(myTarget))) {
						player.sendMessage(Color.WARNING + "[SPY] " + message);
					}
				}
				myTarget.setReplayTo(mySender);
			}
		}
		return true;
	}
}
