package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new FeedCmd().feed(sender, args);
	}
	
	public boolean feed(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (args.length == 0 && player == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
			return true;
		} else if (args.length == 0) {
			player.setFoodLevel(20);
			sender.sendMessage(Color.INFO + "Du ble mett");
			return true;
		} else {
			Player target = MyPlayer.getOnlinePlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke online");
				return true;
			}
			target.setFoodLevel(20);
			target.sendMessage(Color.PLAYER + sender.getName() + Color.INFO + " gjorde deg mett");
			sender.sendMessage(Color.PLAYER + target.getName() + Color.INFO + " ble mett");
			return true;
		}
	}
}
