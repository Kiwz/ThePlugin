package net.kiwz.ThePlugin.commands;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class TpCmd {
	public static HashMap<MyPlayer, Integer> schedules = new HashMap<MyPlayer, Integer>();
	public static HashMap<MyPlayer, MyPlayer> map = new HashMap<MyPlayer, MyPlayer>();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new TpCmd().tp(sender, args);
	}
	
	public static boolean exec(CommandSender sender) {
		return new TpCmd().tpa(sender);
	}
	
	private boolean tp(CommandSender sender, String[] args) {
		final MyPlayer mySender = MyPlayer.getPlayer(sender);
		final MyPlayer myDestination;
		MyPlayer myTarget;
		
		if (args.length != 2 && mySender == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser to spillere, hvem som skal bli teleportert til hvem");
		} else if (mySender.isDamaged()) {
			sender.sendMessage(Color.WARNING + "Du kan ikke teleportere rett etter du har tatt skade");
		} else if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else if (args.length == 1) {
			myDestination = MyPlayer.getPlayer(args[0]);
			if (myDestination == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myDestination.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myDestination) + Color.WARNING + " er ikke online");
			} else if (mySender.isAdmin()) {
				mySender.getOnlinePlayer().teleport(myDestination.getOnlinePlayer());
				sender.sendMessage(Color.INFO + "Du ble teleportert til " + MyPlayer.getColorName(myDestination));
			} else if (mySender.canBeCharged(1)){
				int scheduleID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThePlugin.getPlugin(), new Runnable() {
					public void run() {
						TpCmd.map.remove(myDestination);
						TpCmd.schedules.remove(myDestination);
						if (mySender.getOnlinePlayer() != null) {
							mySender.getOnlinePlayer().sendMessage(MyPlayer.getColorName(myDestination) + " ønsker ikke besøk av deg");
						}
					}
				}, 60 * 20);
				schedules.put(myDestination, scheduleID);
				map.put(myDestination, mySender);
				myDestination.getOnlinePlayer().sendMessage(MyPlayer.getColorName(mySender) + " ønsker å bli teleportert til deg");
				myDestination.getOnlinePlayer().sendMessage(Color.INFO + "For å hente "
						+ MyPlayer.getColorName(mySender) + " skriv: " + Color.COMMAND + "/tpa");
				sender.sendMessage(Color.INFO + "Venter på aksept fra " + MyPlayer.getColorName(myDestination));
			} else {
				sender.sendMessage(Color.WARNING + "Det koster 1 gullbar for å teleportere til " + MyPlayer.getColorName(myDestination));
			}
		} else if (!mySender.isAdmin()) {
			sender.sendMessage(Color.WARNING + "/tp <spiller-navn>");
		} else if (args.length == 2) {
			myTarget = MyPlayer.getPlayer(args[0]);
			myDestination = MyPlayer.getPlayer(args[1]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
			} else if (myDestination == null) {
				sender.sendMessage(Color.PLAYER + args[1] + Color.WARNING + " er ikke en spiller her");
			} else if (myDestination.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myDestination) + Color.WARNING + " er ikke online");
			} else {
				myTarget.getOnlinePlayer().teleport(myDestination.getOnlinePlayer());
				myTarget.getOnlinePlayer().sendMessage(Color.INFO + "Du ble teleportert til " + MyPlayer.getColorName(myDestination)
						+ " av " + sender.getName());
				sender.sendMessage(Color.INFO + "Du teleporterte " + MyPlayer.getColorName(myTarget)
						+ " til " + MyPlayer.getColorName(myDestination));
			}
		} else if (args.length == 3 && args[0].matches("[0-9-]+") && args[1].matches("[0-9-]+") && args[2].matches("[0-9-]+")) {
			int x = Util.parseInt(args[0]);
			int y = Util.parseInt(args[1]);
			int z = Util.parseInt(args[2]);
			Location loc = new Location(mySender.getOnlinePlayer().getWorld(), x, y, z);
			mySender.getOnlinePlayer().teleport(loc);
			sender.sendMessage(Color.INFO + "Du ble teleportert til"
				+ " X: " + Color.VARIABLE + x + Color.INFO
				+ " Y: " + Color.VARIABLE + y + Color.INFO
				+ " Z: " + Color.VARIABLE + z);
		} else {
			return false;
		}
		return true;
	}
	
	private boolean tpa(CommandSender sender) {
		MyPlayer myDestination = MyPlayer.getPlayer(sender);
		if (map.containsKey(myDestination)) {
			MyPlayer myTarget = map.get(myDestination);
			if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online lengre");
			} else if (myTarget.charge(1)) {
				myTarget.getOnlinePlayer().teleport(myDestination.getOnlinePlayer());
				myTarget.getOnlinePlayer().sendMessage(Color.INFO + "Du ble teleportert til " + MyPlayer.getColorName(myDestination));
				sender.sendMessage(Color.INFO + "Du teleporterte " + MyPlayer.getColorName(myTarget) + " til deg");
				Bukkit.getScheduler().cancelTask(schedules.get(myDestination));
				TpCmd.map.remove(myDestination);
				TpCmd.schedules.remove(myDestination);
			} else {
				myTarget.getOnlinePlayer().sendMessage(MyPlayer.getColorName(myDestination) + Color.WARNING
						+ " forsøkte å teleportere deg, men du hadde ikke 1 gullbar");
				sender.sendMessage(MyPlayer.getColorName(myTarget) + " kunne ikke betale for teleporteringen");
			}
		} else {
			sender.sendMessage(Color.WARNING + "Det er ingen som har sendt teleport forespørsel til deg");
		}
		return true;
	}
}
