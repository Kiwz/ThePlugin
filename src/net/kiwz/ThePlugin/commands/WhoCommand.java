package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlayers;
import org.bukkit.command.CommandSender;

public class WhoCommand {
	
	public void whois(CommandSender sender, String[] args) {
		HandlePlayers players = new HandlePlayers();
		
		if (args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Du må skrive navnet på en spiller");
		}
		
		else {
			players.sendPlayer(sender, args[0]);
		}
	}
}
