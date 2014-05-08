package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCmd {
	
	public static boolean exec(CommandSender sender, String cmd, String[] args) {
		BanCmd banCmd = new BanCmd();
		if (cmd.equals("ban")) return banCmd.ban(sender, args);
		else if (cmd.equals("unban")) return banCmd.unban(sender, args);
		else if (cmd.equals("kick")) return banCmd.kick(sender, args);
		else return false;
	}
	
	public boolean ban(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		List<String> list = new ArrayList<String>();
		if (args.length == 0) {
			for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
				if (myPlayer.isBanned()) {
					String banReason = myPlayer.getBanReason();
					if (banReason.length() > 30) {
						banReason = banReason.substring(0, 27) + "...";
					}
					list.add(MyPlayer.getColorName(myPlayer) + ": " + Color.WARNING + banReason + " "
							+ Color.VARIABLE + Util.getTimeFullDate(myPlayer.getBanExpire() + 60));
				}
			}
			Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
			Util.sendAsPages(sender, "1", 0, "Banned Players:", "", list);
		} else if (args.length == 1 && args[0].matches("[0-9-]+")) {
			for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
				if (myPlayer.isBanned()) {
					String banReason = myPlayer.getBanReason();
					if (banReason.length() > 30) {
						banReason = banReason.substring(0, 27) + "...";
					}
					list.add(MyPlayer.getColorName(myPlayer) + ": " + Color.WARNING + banReason + " "
							+ Color.VARIABLE + Util.getTimeFullDate(myPlayer.getBanExpire() + 60));
				}
			}
			Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
			Util.sendAsPages(sender, args[0], 0, "Banned Players:", "", list);
		} else if (args.length >= 3 && args[1].matches("[0-9-]+")) {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.getName().equals("Kiwz")) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er eieren og kan ikke bannes");
			} else {
				long expire = Util.parseInt(args[1]);
				long banExpire;
				if ((mySender == null || mySender.isAdmin()) && expire < 1) banExpire = 2524608000L;
				else if (expire < 1 || expire > 1440) banExpire = (System.currentTimeMillis() / 1000) + (1440 * 60);
				else banExpire = (System.currentTimeMillis() / 1000) + (expire * 60);
				
				String banReason = "";
				for (int i = 2; i < args.length; i++) {
					banReason += args[i] + " ";
				}
				banReason = banReason.trim();
				
				String bannedBy;
				if (mySender == null) bannedBy = "Server";
				else bannedBy = mySender.getName();
				
				myTarget.setBanned(true, banExpire, banReason, bannedBy);
				
				if (myTarget.getOnlinePlayer() != null) {
					myTarget.getOnlinePlayer().kickPlayer(Color.WARNING + "Du ble bannet av " + MyPlayer.getColorName(mySender)
							+ Color.WARNING + "\nKom tilbake igjen " + Color.VARIABLE + Util.getTimeFullDate(banExpire + 60)
							+ Color.WARNING + "\nBegrunnelse: " + Color.VARIABLE + banReason);
				}
				
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					player.sendMessage(MyPlayer.getColorName(myTarget)
							+ Color.WARNING + " ble bannet av " + MyPlayer.getColorName(mySender));
					player.sendMessage(MyPlayer.getColorName(myTarget)
							+ Color.WARNING + " kan komme tilbake " + Color.VARIABLE + Util.getTimeFullDate(banExpire + 60));
				}
				sender.sendMessage(Color.INFO + "Du bannet " + MyPlayer.getColorName(myTarget)
						+ " frem til " + Color.VARIABLE + Util.getTimeFullDate(banExpire + 60));
			}
		} else {
			sender.sendMessage(Color.WARNING + "For å se hvem som er bannet skriv:");
			sender.sendMessage("/ban <side-nr>");
			sender.sendMessage(Color.WARNING + "For å banne en spiller skriv:");
			sender.sendMessage("/ban <spiller-navn> <minutter> <begrunnelse>");
		}
		return true;
	}
	
	public boolean unban(CommandSender sender, String[] args) {
		
		if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
		} else {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.isBanned()) {
				myTarget.setBanned(false, 0, "", "");
				sender.sendMessage(MyPlayer.getColorName(myTarget) + " er benådet");
			} else {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + " var ikke bannet");
			}
		}
		return true;
	}
	
	public boolean kick(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		if (args.length >= 2) {
			MyPlayer myTarget = MyPlayer.getPlayer(args[0]);
			if (myTarget == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myTarget.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke online");
			} else {
				String reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				reason = reason.trim();
				myTarget.getOnlinePlayer().kickPlayer(Color.WARNING + "Du ble sparket av " + MyPlayer.getColorName(mySender)
						+ Color.WARNING + "\nBegrunnelse: " + Color.VARIABLE + reason);
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					player.sendMessage(MyPlayer.getColorName(myTarget)
							+ Color.WARNING + " ble sparket av " + MyPlayer.getColorName(mySender));
				}
				sender.sendMessage(Color.INFO + "Du kicket " + MyPlayer.getColorName(myTarget));
			}
		} else {
			sender.sendMessage(Color.WARNING + "Kommandoen må inneholde flere argumenter:");
			sender.sendMessage("/kick <spiller-navn> <begrunnelse>");
		}
		return true;
	}
}
