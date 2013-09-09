package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlayers;
import org.bukkit.command.CommandSender;

public class MuteCommand {
	private HandlePlayers players = new HandlePlayers();
	
	public void mute(CommandSender sender, String[] args) {
		
		if (args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Du må skrive navnet på en spiller");
		}
		
		else {
			if (args[0].equalsIgnoreCase("list")) {
				players.sendMuted(sender);
			}
			else if (!players.hasPlayedBefore(args[0])) {
				sender.sendMessage(ThePlugin.c2 + args[0] + " har aldri spilt her");
			}
			else {
				players.setMute(args[0], 1);
				sender.sendMessage(ThePlugin.c1 + players.getPlayerName(args[0]) + " er muted");
			}
		}
	}
	
	public void unMute(CommandSender sender, String[] args) {
		
		if (args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Du må skrive navnet på en spiller");
		}
		
		else {
			if (args[0].equalsIgnoreCase("all")) {
				players.unMuteALL();
				sender.sendMessage(ThePlugin.c1 + "Alle er unmuted");
			}
			else if (!players.hasPlayedBefore(args[0])) {
				sender.sendMessage(ThePlugin.c2 + args[0] + " har aldri spilt her");
			}
			else {
				if (players.isMuted(args[0])) {
					players.setMute(args[0], 0);
					sender.sendMessage(ThePlugin.c1 + players.getPlayerName(args[0]) + " er unmuted");
				}
				else {
					sender.sendMessage(ThePlugin.c2 + players.getPlayerName(args[0]) + " er ikke muted");
				}
			}
		}
	}
}
