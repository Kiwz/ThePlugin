package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.WeatherType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VærCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new VærCmd().vær(sender, args);
	}
	
	private boolean vær(CommandSender sender, String[] args) {
		Player player = server.getPlayer(sender.getName());

		if (player == null) {
			sender.sendMessage(Color.COMMAND + "/vær " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
			player.resetPlayerWeather();
			sender.sendMessage(Color.INFO + "Ditt vær er lik serveren sitt vær");
		} else if (args[0].equalsIgnoreCase("sol")) {
			player.setPlayerWeather(WeatherType.CLEAR);
			sender.sendMessage(Color.INFO + "Ditt vær er alltid opphold");
		} else if (args[0].equalsIgnoreCase("regn")) {
			player.setPlayerWeather(WeatherType.DOWNFALL);
			sender.sendMessage(Color.INFO + "Ditt vær er alltid nedb�r");
		} else {
			player.resetPlayerWeather();
			sender.sendMessage(Color.INFO + "Ditt vær er lik serveren sitt vær");
		}
		return true;
	}
}
