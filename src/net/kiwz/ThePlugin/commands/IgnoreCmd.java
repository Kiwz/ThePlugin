package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.command.CommandSender;

public class IgnoreCmd {
	private static HashMap<String, List<MyPlayer>> map = new HashMap<String, List<MyPlayer>>();
	
	public static List<MyPlayer> getPlayers(MyPlayer myPlayer) {
		if (map.containsKey(myPlayer.getUUID())) return map.get(myPlayer.getUUID());
		return null;
	}
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new IgnoreCmd().ignore(sender, args);
	}
	
	public boolean ignore(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/ignorer " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
			if (getSilentPlayer(mySender) != null) {
				sender.sendMessage(Color.INFO + "Du har ignorert følgende spiller(e):");
				for (MyPlayer myPlayer : getSilentPlayer(mySender)) {
					sender.sendMessage(MyPlayer.getColorName(myPlayer));
				}
			} else {
				sender.sendMessage(Color.WARNING + "Du har ikke ignorert noen spillere");
			}
		} else if (mySender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			sender.sendMessage(Color.WARNING + "Du kan ikke ignorere deg selv");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				if (!remSilentPlayer(mySender, myTarget)) {
					setSilentPlayer(mySender, myTarget);
					sender.sendMessage(Color.INFO + "Du vil IKKE se hva " + MyPlayer.getColorName(myTarget) + " skriver");
				}
				else {
					sender.sendMessage(Color.INFO + "Du kan nå se hva " + MyPlayer.getColorName(myTarget) + " skriver");
				}
			}
		}
		return true;
	}
	
	private void setSilentPlayer(MyPlayer mySender, MyPlayer myTarget) {
		if (map.containsKey(mySender.getUUID())) {
			map.get(mySender.getUUID()).add(myTarget);
		} else {
			List<MyPlayer> myTargets = new ArrayList<MyPlayer>();
			map.put(mySender.getUUID(), myTargets);
			map.get(mySender.getUUID()).add(myTarget);
		}
	}
	
	private List<MyPlayer> getSilentPlayer(MyPlayer myPlayer) {
		if (map.containsKey(myPlayer.getUUID())) return map.get(myPlayer.getUUID());
		return null;
	}
	
	private boolean remSilentPlayer(MyPlayer mySender, MyPlayer myTarget) {
		if (map.containsKey(mySender.getUUID())) {
			if (map.get(mySender.getUUID()).remove(myTarget)) return true;
		}
		return false;
	}
}
