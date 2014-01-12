package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoisCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new WhoisCmd().whois(sender, args);
	}
	
	private boolean whois(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (args.length == 0 && mySender == null) {
			sender.sendMessage(Color.WARNING + "Du må skrive navnet på en spiller");
		} else if (args.length == 0) {
			String about = MyPlayer.getColorName(mySender);
			String adminInfo = "ip: " + mySender.getIp();
			List<String> list = getPlayerInfo(mySender, mySender);
			Util.sendAsPages(sender, "1", 10, about, adminInfo, list);
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else {
				String about = MyPlayer.getColorName(myTarget);
				String adminInfo = "ip: " + myTarget.getIp();
				List<String> list = getPlayerInfo(mySender, myTarget);
				Util.sendAsPages(sender, "1", 10, about, adminInfo, list);
			}
		}
		return true;
	}
	
	private List<String> getPlayerInfo(MyPlayer mySender, MyPlayer myTarget) {
		List<String> list = new ArrayList<String>();
		Player player = myTarget.getOfflinePlayer();
		Location loc = player.getLocation();
		String lastLogin = Util.getTimeFullDate(myTarget.getLastPlayed());
		long timePlayed = Util.getTimeHours(myTarget.getTimePlayed());
		String plasser = "";
		for (Place p : Place.getPlacesByOwner(myTarget)) {
			plasser = plasser + "[" + p.getColorName() + "] ";
		}
		String fly = "Nei";
		if (player.getAllowFlight()) fly = "Ja";
		String muted = "Nei";
		if (myTarget.isMuted()) muted = "Ja";
		String spy = "Nei";
		if (myTarget.isSpy()) spy = "Ja";
		String pvp = "Nei";
		if (myTarget.isPvp()) pvp = "Ja";
		Place place = Place.getPlace(loc);
		String placeName = "";
		if (place != null) placeName = " Plass: [" + place.getColorName() + "]";
		
		if (player.isBanned()) list.add(Color.WARNING + "BANNED!!!     BANNED!!!     BANNED!!!");
		list.add(Color.INFO + "Siste innlogging: " + Color.VARIABLE + lastLogin + Color.INFO
				+ " Tid spilt: " + Color.VARIABLE + timePlayed + " timer");
		list.add(Color.INFO + "Eier av: " + plasser);
		if (mySender == null || mySender.isAdmin()) {
			list.add(Color.INFO + "Muted: " + Color.VARIABLE + muted + Color.INFO
					+ " PvP: " + Color.VARIABLE + pvp + Color.INFO
					+ " Spionering: " + Color.VARIABLE + spy);
			list.add(Color.INFO + "GameMode: " + Color.VARIABLE + player.getGameMode() + Color.INFO
					+ " Flying: " + Color.VARIABLE + fly);
		} else {
			list.add(Color.INFO + "Muted: " + Color.VARIABLE + muted);
		}
		list.add(Color.INFO + "Level: " + Color.VARIABLE + player.getLevel() +Color.INFO
				+ " Experience: " + Color.VARIABLE + player.getTotalExperience());
		list.add(Color.INFO + "Helse: " + Color.VARIABLE + (int) player.getHealth() +  Color.INFO
				+ " Sult: " + Color.VARIABLE + player.getFoodLevel() +  Color.INFO
				+ " Metning: " + Color.VARIABLE + (int) player.getSaturation());
		if (mySender == null || mySender.equals(myTarget) || mySender.isAdmin() || loc.getWorld().equals(Bukkit.getServer().getWorlds().get(0))) {
			list.add(Color.INFO + "Lokasjon: " + Color.VARIABLE + loc.getWorld().getName() + Color.INFO
					+ " X: " + Color.VARIABLE + loc.getBlockX() + Color.INFO
					+ " Y: " + Color.VARIABLE + loc.getBlockY() + Color.INFO
					+ " Z: " + Color.VARIABLE + loc.getBlockZ() + Color.INFO + placeName);
		} else {
			list.add(Color.INFO + "Lokasjon: " + Color.VARIABLE + loc.getWorld().getName() + Color.INFO
					+ " X: " + Color.VARIABLE + "??" + Color.INFO
					+ " Y: " + Color.VARIABLE + "??" + Color.INFO
					+ " Z: " + Color.VARIABLE + "??" + Color.INFO);
		}
		return list;
	}
}
