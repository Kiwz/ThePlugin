package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new FlyCmd().fly(sender, args);
	}
	
	public boolean fly(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (args.length == 0 && player == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
			return true;
		} else if (args.length == 0) {
			if (player.getAllowFlight()) {
				player.setAllowFlight(false);
				sender.sendMessage(Color.INFO + "Du kan ikke fly");
			}
			else {
				player.setAllowFlight(true);
				sender.sendMessage(Color.INFO + "Du kan fly");
			}
			return true;
		} else {
			Player target = MyPlayer.getOnlinePlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke online");
				return true;
			}
			if (target.getAllowFlight()) {
				target.setAllowFlight(false);
				target.sendMessage(Color.PLAYER + sender.getName() + Color.INFO + " skrudde av fly modus");
				sender.sendMessage(Color.PLAYER + target.getName() + Color.INFO + " kan ikke fly");
			}
			else {
				target.setAllowFlight(true);
				target.sendMessage(Color.PLAYER + sender.getName() + Color.INFO + " skrudde på fly modus");
				sender.sendMessage(Color.PLAYER + target.getName() + Color.INFO + " kan fly");
			}
			return true;
		}
	}
}
