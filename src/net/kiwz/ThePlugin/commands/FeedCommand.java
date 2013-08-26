package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand {
	
	public boolean feed(CommandSender sender, Command cmd, String[] args) {
		OnlinePlayer onlinePlayer = new OnlinePlayer();
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Spesifiser en spiller");
			return true;
		}
		else if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			player.setFoodLevel(20);
			sender.sendMessage(ThePlugin.c1 + "Du ble mett");
			return true;
		}
		else if (onlinePlayer.getPlayer(args[0]) != null) {
			Player target = onlinePlayer.getPlayer(args[0]);
			target.setFoodLevel(20);
			target.sendMessage(ThePlugin.c1 + sender.getName() + " gjorde deg mett");
			sender.sendMessage(ThePlugin.c1 + target.getName() + " ble mett");
			return true;
		}
		else {
			sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			return true;
		}
	}
}
