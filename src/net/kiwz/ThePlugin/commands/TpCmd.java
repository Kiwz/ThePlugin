package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
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
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (args.length != 2 && player == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser to spillere, hvem som skal bli teleportert til hvem");
			return true;
		} else if (args.length == 0) {
			return false;
		} else if (args.length == 1) {
			Player target = MyPlayer.getOnlinePlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Color.WARNING + "Fant ingen spiller som passet dette navnet");
			} else {
				player.teleport(target);
				sender.sendMessage(Color.INFO + "Du ble teleportert til " + target.getName());
			}
		} else if (args.length == 2) {
			Player target = MyPlayer.getOnlinePlayer(args[0]);
			Player destination = MyPlayer.getOnlinePlayer(args[1]);
			if ((target == null) || (destination == null)) {
				sender.sendMessage(Color.WARNING + "Fant ingen spiller som passet en eller begge navnene");
			}else {
				target.teleport(destination);
				target.sendMessage(Color.INFO + "Du ble teleportert til " + destination.getName() + " av " + sender.getName());
				sender.sendMessage(Color.INFO + "Du teleporterte " + target.getName() + " til " + destination.getName());
			}
		} else if (args.length == 3 && args[0].matches("[0-9-]+") && args[1].matches("[0-9-]+") && args[2].matches("[0-9-]+")) {
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			double z = Double.parseDouble(args[2]);
			Location loc = new Location(player.getWorld(), x, y, z);
			player.teleport(loc);
			sender.sendMessage(Color.INFO + "Du ble teleportert til X: " + (int) x + " Y: " + (int) y + " Z: " + (int) z);
		}
		return true;
	}
}
