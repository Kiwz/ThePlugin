package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.Home;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.command.CommandSender;

public class HomeCmd {
	
	public static boolean execGet(CommandSender sender, String[] args) {
		return new HomeCmd().getHome(sender, args);
	}
	
	public static boolean execSet(CommandSender sender, String[] args) {
		return new HomeCmd().setHome(sender, args);
	}
	
	private boolean getHome(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		Home home = null;
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/hjem " + Color.WARNING + "kan bare brukes av spillere");
			return true;
		} else if (args.length == 0) {
			home = Home.getHome(mySender);
		} else {
			home = Home.getHome(mySender, mySender.getOnlinePlayer().getWorld());
		}
		
		if (home == null) {
			sender.sendMessage(Color.WARNING + "Fant ikke ditt hjem, bruk " + Color.VARIABLE + "/setthjem" + Color.WARNING + " for å lage nytt hjem");
		} else {
			mySender.getOnlinePlayer().teleport(home.getLocation());
		}
		return true;
	}
	
	private boolean setHome(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (mySender == null) {
			sender.sendMessage(Color.COMMAND + "/setthjem " + Color.WARNING + "kan bare brukes av spillere");
		} else {
			Home home = new Home(mySender);
			home.save();
			sender.sendMessage(Color.INFO + "Du har satt ditt hjem hit, skriv " + Color.VARIABLE + "/hjem" + Color.INFO + " for å komme hit");
		}
		return true;
	}
}
