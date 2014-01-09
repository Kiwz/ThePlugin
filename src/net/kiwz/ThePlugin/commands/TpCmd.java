package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new TpCmd().tp(sender, args);
	}
	
	private boolean tp(CommandSender sender, String[] args) {
		Player player = server.getPlayer(sender.getName());
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		MyPlayer myTarget;
		MyPlayer myDestination;
		
		if (args.length != 2 && mySender == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser to spillere, hvem som skal bli teleportert til hvem");
		} else if (args.length == 0) {
			return false;
		} else if (args.length == 1) {
			myDestination = MyPlayer.getPlayer(args[0]);
			if (myDestination == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her");
			} else if (myDestination.getOnlinePlayer() == null) {
				sender.sendMessage(MyPlayer.getColorName(myDestination) + Color.WARNING + " er ikke online");
			} else {
				player.teleport(myDestination.getOnlinePlayer());
				sender.sendMessage(Color.INFO + "Du ble teleportert til " + MyPlayer.getColorName(myDestination));
			}
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
		} else if (args.length > 2 && args[0].matches("[0-9-]+") && args[1].matches("[0-9-]+") && args[2].matches("[0-9-]+")) {
			int x = Util.parseInt(args[0]);
			int y = Util.parseInt(args[1]);
			int z = Util.parseInt(args[2]);
			Location loc = new Location(player.getWorld(), x, y, z);
			player.teleport(loc);
			sender.sendMessage(Color.INFO + "Du ble teleportert til"
				+ " X: " + Color.VARIABLE + x + Color.INFO
				+ " Y: " + Color.VARIABLE + y + Color.INFO
				+ " Z: " + Color.VARIABLE + z);
		} else {
			return false;
		}
		return true;
	}
}
