package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.ChatIgnore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCommand {
	
	public void ignore(CommandSender sender, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		ChatIgnore chatIgnore = new ChatIgnore();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "Denne kommandoen er bare for spillere");
		}
		else if (args.length == 0) {
			if (chatIgnore.getSilentPlayer(player) != null) {
				sender.sendMessage(ThePlugin.c1 + "Du har ignorert følgende spiller(e):");
				sender.sendMessage(chatIgnore.getSilentPlayer(player));
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Du har ikke ignorert noen spillere");
			}
		}
		else if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			sender.sendMessage(ThePlugin.c1 + "Du kan ikke ignorere deg selv");
		}
		else {
			if (!chatIgnore.remSilentPlayer(player, args[0])) {
				chatIgnore.addSilentPlayer(player, args[0]);
			}
		}
	}
}
