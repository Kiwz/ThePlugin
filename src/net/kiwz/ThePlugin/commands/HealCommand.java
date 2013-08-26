package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand {
	
	public boolean heal(CommandSender sender, Command cmd, String[] args) {
		OnlinePlayer onlinePlayer = new OnlinePlayer();
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Spesifiser en spiller");
			return true;
		}
		else if (args.length == 0 || sender.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			player.setHealth(20);
			sender.sendMessage(ThePlugin.c1 + "Du fikk fullt liv");
			return true;
		}
		else if (onlinePlayer.getPlayer(args[0]) != null) {
			Player target = onlinePlayer.getPlayer(args[0]);
			target.setHealth(20);
			target.sendMessage(ThePlugin.c1 + sender.getName() + " ga deg fullt liv");
			sender.sendMessage(ThePlugin.c1 + target.getName() + " har fått fullt liv");
			return true;
		}
		else {
			sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			return true;
		}
	}
}
