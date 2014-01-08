package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Perm;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class WhoisCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new WhoisCmd().whois(sender, args);
	}
	
	private boolean whois(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (args.length == 0 && player == null) {
			sender.sendMessage(Color.WARNING + "spesifiser en spiller");
		} else if (args.length == 0) {
			sendMessage(sender, MyPlayer.getPlayer(player));
		} else {
			MyPlayer myPlayer = MyPlayer.getPlayer(args[0]);
			if (myPlayer == null) {
				sender.sendMessage(Color.WARNING + args[0] + " har aldri spilt her");
			} else {
				sendMessage(sender, myPlayer);
			}
		}
		return true;
	}
	
	private void sendMessage(CommandSender sender, MyPlayer myPlayer) {
		Player player = myPlayer.getBukkitPlayer();
		Location loc = player.getLocation();
		String lastLogin = Util.getTimeFullDate(myPlayer.getLastPlayed());
		long timePlayed = Util.getTimeHours(myPlayer.getTimePlayed());
		String plasser = "";
		for (Place p : Place.getPlacesByOwner(player)) {
			plasser = plasser + "[" + p.getColorName() + "] ";
		}
		String muted = "Nei";
		if (myPlayer.isMuted()) muted = "Ja";
		
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("----- ");
		header.append(myPlayer.getColorName());
		if (Perm.isAdmin(sender.getName())) {
			header.append(ChatColor.GRAY);
			header.append(" (ip: " + myPlayer.getIp() + ")");
		}
		header.append(ChatColor.YELLOW);
		header.append(" -----");
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		sender.sendMessage(Color.INFO + "Siste innlogging: " + Color.VARIABLE + lastLogin);
		sender.sendMessage(Color.INFO + "Tid spilt: " + Color.VARIABLE + timePlayed + " timer");
		sender.sendMessage(Color.INFO + "Eier av: " + plasser);
		sender.sendMessage(Color.INFO + "Muted: " + Color.VARIABLE + muted);
		sender.sendMessage(Color.INFO + "GameMode: " + Color.VARIABLE + player.getGameMode());
		sender.sendMessage(Color.INFO + "Level: " + Color.VARIABLE + player.getLevel());
		sender.sendMessage(Color.INFO + "Experience: " + Color.VARIABLE + player.getTotalExperience());
		sender.sendMessage(Color.INFO + "Saturation: " + Color.VARIABLE + player.getSaturation());
		sender.sendMessage(Color.INFO + "Lokasjon: " + Color.VARIABLE + loc.getWorld().getName() +
				" X: " + loc.getBlockX() + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ());
	}
}
