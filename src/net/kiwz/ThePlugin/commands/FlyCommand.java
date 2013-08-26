package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand {
	
	public boolean fly(CommandSender sender, Command cmd, String[] args) {
		OnlinePlayer onlinePlayer = new OnlinePlayer();
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Spesifiser en spiller");
			return true;
		}
		else if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			if (player.getAllowFlight()) {
				player.setAllowFlight(false);
				sender.sendMessage(ThePlugin.c1 + "Du kan ikke fly");
			}
			else {
				player.setAllowFlight(true);
				sender.sendMessage(ThePlugin.c1 + "Du kan fly");
			}
			return true;
		}
		else if (onlinePlayer.getPlayer(args[0]) != null) {
			Player target = onlinePlayer.getPlayer(args[0]);
			if (!target.getAllowFlight()) {
				target.setAllowFlight(true);
				target.sendMessage(ThePlugin.c1 + sender.getName() + " skrudde på fly modus");
				sender.sendMessage(ThePlugin.c1 + target.getName() + " kan fly");
			}
			else {
				target.setAllowFlight(false);
				target.sendMessage(ThePlugin.c1 + sender.getName() + " skrudde av fly modus");
				sender.sendMessage(ThePlugin.c1 + target.getName() + " kan ikke fly");
			}
			return true;
		}
		else {
			sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			return true;
		}
	}
}
