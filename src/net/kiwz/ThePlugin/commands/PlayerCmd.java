package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCmd {
	private String cmd = "";
	private CommandSender sender = null;
	private Player target = null;
	private MyPlayer mySender = null;
	private MyPlayer myTarget = null;
	private String senderName = "";
	private String targetName = "";
	
	public PlayerCmd(CommandSender sender, String cmd, String[] args) {
		this.cmd = cmd;
		this.sender = sender;
		mySender = MyPlayer.getPlayer(sender);
		senderName = MyPlayer.getColorName(mySender);
		if (args.length == 0 && mySender == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else if (args.length == 0) {
			target = mySender.getOnlinePlayer();
			myTarget = mySender;
			targetName = senderName;
		} else {
			myTarget = MyPlayer.getPlayer(args[0]);
			targetName = MyPlayer.getColorName(myTarget);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (cmd.equals("hvem")) {
				target = myTarget.getOfflinePlayer();
			} else if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
			} else {
				target = myTarget.getOnlinePlayer();
			}
		}
	}
	
	public void exec() {
		if (target == null);
		else if (cmd.equals("fly")) fly();
		else if (cmd.equals("gm")) gm();
		else if (cmd.equals("heal")) heal();
		else if (cmd.equals("hvem")) hvem();
	}
	

	private void fly() {
		target.setAllowFlight(!target.getAllowFlight());
		if (sender.getName() == target.getName()) {
			if (target.getAllowFlight()) target.sendMessage(Color.INFO + "Du kan fly");
			else target.sendMessage(Color.INFO + "Du kan ikke fly");
		} else {
			if (target.getAllowFlight()) {
				target.sendMessage(senderName + " skrudde på fly modus");
				sender.sendMessage(targetName + " kan fly");
			} else {
				target.sendMessage(senderName + " skrudde av fly modus");
				sender.sendMessage(targetName + " kan ikke fly");
			}
		}
	}
	
	private void gm() {
		GameMode gm = target.getGameMode();
		GameMode survival = GameMode.SURVIVAL;
		GameMode creative = GameMode.CREATIVE;
		if (sender.getName() == target.getName()) {
			if (gm == survival) {
				target.setGameMode(creative);
				target.sendMessage(Color.INFO + "Du er nå i Creative modus");
			}
			else {
				target.setGameMode(survival);
				target.sendMessage(Color.INFO + "Du er nå i Survival modus");
			}
		} else {
			if (gm == survival) {
				target.setGameMode(creative);
				target.sendMessage(senderName + " endret modusen din til Creative");
				sender.sendMessage(targetName + " er nå i Creative modus");
			} else {
				target.setGameMode(survival);
				target.sendMessage(senderName + " endret modusen din til Survival");
				sender.sendMessage(targetName + " er nå i Survival modus");
			}
		}
	}
	
	private void heal() {
		target.setHealth(20);
		target.setFoodLevel(20);
		target.setSaturation(20);
		if (sender.getName() == target.getName()) {
			target.sendMessage(Color.INFO + "Du fikk fullt liv og masse mat");
		} else {
			target.sendMessage(senderName + " ga deg fullt liv og masse mat");
			sender.sendMessage(targetName + " har fått fullt liv og masse mat");
		}
	}
	
	private void hvem() {
		List<String> list = new ArrayList<String>();
		Location loc = target.getLocation();
		String lastLogin = Util.getTimeFullDate(myTarget.getLastPlayed());
		long timePlayed = Util.getTimeHours(myTarget.getTimePlayed());
		String plasser = "";
		for (Place p : Place.getPlacesByOwner(myTarget)) {
			plasser = plasser + "[" + p.getColorName() + "] ";
		}
		String fly = "Nei";
		if (target.getAllowFlight()) fly = "Ja";
		String muted = "Nei";
		if (myTarget.isMuted()) muted = "Ja";
		String spy = "Nei";
		if (myTarget.isSpy()) spy = "Ja";
		String pvp = "Nei";
		if (myTarget.isPvp()) pvp = "Ja";
		Place place = Place.getPlace(loc);
		String placeName = "";
		if (place != null) placeName = " Plass: [" + place.getColorName() + "]";
		
		if (myTarget.isBanned()) {
			MyPlayer myBannedBy = MyPlayer.getPlayer(myTarget.getBannedBy());
			list.add(Color.WARNING + "Bannet av: " + MyPlayer.getColorName(myBannedBy)
					+ Color.WARNING + " Benådes: " + Color.VARIABLE + Util.getTimeFullDate(myTarget.getBanExpire() + 60));
			list.add(Color.WARNING + "Årsak: " + Color.VARIABLE + myTarget.getBanReason());
		}
		list.add(Color.INFO + "Siste innlogging: " + Color.VARIABLE + lastLogin + Color.INFO
				+ " Tid spilt: " + Color.VARIABLE + timePlayed + " timer");
		list.add(Color.INFO + "Eier av: " + plasser);
		if (mySender == null || mySender.isAdmin()) {
			list.add(Color.INFO + "Muted: " + Color.VARIABLE + muted + Color.INFO
					+ " PvP: " + Color.VARIABLE + pvp + Color.INFO
					+ " Spionering: " + Color.VARIABLE + spy);
			list.add(Color.INFO + "GameMode: " + Color.VARIABLE + target.getGameMode() + Color.INFO
					+ " Flying: " + Color.VARIABLE + fly);
		} else {
			list.add(Color.INFO + "Muted: " + Color.VARIABLE + muted);
		}
		list.add(Color.INFO + "Level: " + Color.VARIABLE + target.getLevel() +Color.INFO
				+ " Experience: " + Color.VARIABLE + target.getTotalExperience());
		list.add(Color.INFO + "Helse: " + Color.VARIABLE + (int) target.getHealth() +  Color.INFO
				+ " Sult: " + Color.VARIABLE + target.getFoodLevel() +  Color.INFO
				+ " Metning: " + Color.VARIABLE + (int) target.getSaturation());
		if (mySender == null || mySender.isAdmin() || mySender.equals(myTarget) || loc.getWorld().equals(Bukkit.getServer().getWorlds().get(0))) {
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
		Util.sendAsPages(sender, "1", 10, targetName, "ip: " + myTarget.getIp(), list);
	}
}
