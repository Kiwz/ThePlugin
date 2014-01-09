package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoisCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new WhoisCmd().whois(sender, args);
	}
	
	private boolean whois(CommandSender sender, String[] args) {
		MyPlayer myPlayer = MyPlayer.getPlayer(sender);
		
		if (args.length == 0 && myPlayer == null) {
			sender.sendMessage(Color.WARNING + "Du må skrive navnet på en spiller");
		} else if (args.length == 0) {
			String about = MyPlayer.getColorName(myPlayer);
			String adminInfo = "ip: " + myPlayer.getIp();
			List<String> list = getPlayerInfo(myPlayer);
			Util.sendAsPages(sender, "1", 10, about, adminInfo, list);
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				String about = MyPlayer.getColorName(myTarget);
				String adminInfo = "ip: " + myTarget.getIp();
				List<String> list = getPlayerInfo(myTarget);
				Util.sendAsPages(sender, "1", 10, about, adminInfo, list);
			}
		}
		return true;
	}
	
	private List<String> getPlayerInfo(MyPlayer myPlayer) {
		List<String> list = new ArrayList<String>();
		Player player = myPlayer.getOfflinePlayer();
		Location loc = player.getLocation();
		String lastLogin = Util.getTimeFullDate(myPlayer.getLastPlayed());
		long timePlayed = Util.getTimeHours(myPlayer.getTimePlayed());
		String plasser = "";
		for (Place p : Place.getPlacesByOwner(myPlayer)) {
			plasser = plasser + "[" + p.getColorName() + "] ";
		}
		String muted = "Nei";
		if (myPlayer.isMuted()) muted = "Ja";
		
		list.add(Color.INFO + "Siste innlogging: " + Color.VARIABLE + lastLogin);
		list.add(Color.INFO + "Tid spilt: " + Color.VARIABLE + timePlayed + " timer");
		list.add(Color.INFO + "Eier av: " + plasser);
		list.add(Color.INFO + "Muted: " + Color.VARIABLE + muted);
		list.add(Color.INFO + "GameMode: " + Color.VARIABLE + player.getGameMode());
		list.add(Color.INFO + "Level: " + Color.VARIABLE + player.getLevel() +Color.INFO
				+ " Experience: " + Color.VARIABLE + player.getTotalExperience());
		list.add(Color.INFO + "Helse: " + Color.VARIABLE + player.getHealth() +  Color.INFO
				+ " Sult: " + Color.VARIABLE + player.getFoodLevel() +  Color.INFO
				+ " Metning: " + Color.VARIABLE + player.getSaturation());
		list.add(Color.INFO + "Lokasjon: " + Color.VARIABLE + loc.getWorld().getName() + Color.INFO
				+ " X: " + Color.VARIABLE + loc.getBlockX() + Color.INFO
				+ " Y: " + Color.VARIABLE + loc.getBlockY() + Color.INFO
				+ " Z: " + Color.VARIABLE + loc.getBlockZ());
		return list;
	}
}
