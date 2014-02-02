package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCmd {
	private String cmd;
	private String[] args;
	private CommandSender sender;
	private Player player = null;
	private String senderName = "";
	private MyPlayer mySender = null;
	private String warning = "";
	
	public PlayerCmd(CommandSender sender, String cmd, String[] args) {
		this.cmd = cmd;
		this.args = args;
		this.sender = sender;
		mySender = MyPlayer.getPlayer(sender);
		senderName = MyPlayer.getColorName(mySender);

		if (mySender == null) {
			warning = Color.COMMAND + "/" + cmd + Color.WARNING + " kan bare brukes av spillere";
		} else {
			player = mySender.getOnlinePlayer();
		}
	}
	
	public boolean exec() {
		if (svar() || tid() || vær()) return true;
		else return false;
	}
	
	private boolean svar() {
		if (!cmd.equals("svar")) return false;
		
		MyPlayer myTarget = mySender.getReplayTo();
		String targetName = MyPlayer.getColorName(myTarget);
		if (myTarget == null) {
			sender.sendMessage(Color.WARNING + "Det er ingen du kan svare");
		} else if (myTarget.getOnlinePlayer() == null) {
			sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
		} else if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Du må skrive en melding");
		} else {
			String message = senderName + Color.WHISPER + " -> " + targetName + Color.WHISPER + ": ";
			for (int i = 0; args.length > i; i++) {
				message = message + args[i] + " ";
			}
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
		return true;
	}
	
	private boolean tid() {
		if (!cmd.equals("tid")) return false;
		
		if (mySender == null) {
			sender.sendMessage(warning);
		} else if (args.length == 0) {
			player.resetPlayerTime();
			sender.sendMessage(Color.INFO + "Din tid er lik serveren sin tid");
		} else if (args[0].equalsIgnoreCase("dag")) {
			player.setPlayerTime(6000L, false);
			sender.sendMessage(Color.INFO + "Din tid er alltid dag");
		} else if (args[0].equalsIgnoreCase("natt")) {
			player.setPlayerTime(18000L, false);
			sender.sendMessage(Color.INFO + "Din tid er alltid natt");
		} else {
			player.resetPlayerTime();
			sender.sendMessage(Color.INFO + "Din tid er lik serveren sin tid");
		}
		return true;
	}
	
	private boolean vær() {
		if (!cmd.equals("vær")) return false;
		
		if (mySender == null) {
			sender.sendMessage(warning);
		} else if (args.length == 0) {
			player.resetPlayerWeather();
			sender.sendMessage(Color.INFO + "Ditt vær er lik serveren sitt vær");
		} else if (args[0].equalsIgnoreCase("sol")) {
			player.setPlayerWeather(WeatherType.CLEAR);
			sender.sendMessage(Color.INFO + "Ditt vær er alltid opphold");
		} else if (args[0].equalsIgnoreCase("regn")) {
			player.setPlayerWeather(WeatherType.DOWNFALL);
			sender.sendMessage(Color.INFO + "Ditt vær er alltid nedbør");
		} else {
			player.resetPlayerTime();
			sender.sendMessage(Color.INFO + "Ditt vær er lik serveren sitt vær");
		}
		return true;
	}
}
