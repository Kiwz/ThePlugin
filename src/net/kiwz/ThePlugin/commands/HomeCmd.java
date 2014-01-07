package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.Home;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean execGet(CommandSender sender, String[] args) {
		return new HomeCmd().getHome(sender, args);
	}
	
	public static boolean execSet(CommandSender sender, String[] args) {
		return new HomeCmd().setHome(sender, args);
	}
	
	private boolean getHome(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		Home home;
		if (player == null) {
			sender.sendMessage(Color.WARNING + "/home kan bare brukes av spillere");
			return true;
		} else if (args.length == 0) {
			home = Home.getHome(player);
		} else {
			home = Home.getHome(player, player.getWorld());
		}
		
		if (home == null) {
			sender.sendMessage(Color.WARNING + "Fant ikke ditt hjem, bruk " + Color.VARIABLE + "/sethjem" + Color.WARNING + " for å lage nytt hjem");
		} else {
			player.teleport(home.getLocation());
		}
		return true;
	}
	
	private boolean setHome(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (player == null) {
			sender.sendMessage(Color.WARNING + "/sethome kan bare brukes av spillere");
			return true;
		}
		Home home = new Home(player);
		home.save();
		sender.sendMessage(Color.INFO + "Du har satt ditt hjem hit, skriv " + Color.VARIABLE + "/hjem" + Color.INFO + " for å komme hit");
		return true;
	}
}
