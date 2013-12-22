package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplayCommand {
	
	public void replay(CommandSender sender, String[] args) {
		String sendTo = ThePlugin.replayMsg.get(sender.getName());
		
		if (sendTo == null) {
			sender.sendMessage(ThePlugin.c2 + "Det er ingen du kan svare");
			return;
		}

		Player sendToPlayer = Bukkit.getServer().getPlayer(sendTo);
		if (sendToPlayer == null) {
			sender.sendMessage(ThePlugin.c2 + sendTo + " er ikke online");
		}
		
		else if (args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Du må skrive en melding");
		}
		
		else {
			String message = "";
			for (String s : args) {
				message = message + s + " ";
			}
			message = ThePlugin.c5 + sender.getName() + " -> " + sendToPlayer.getName() + ": " + message;
			sender.sendMessage(message);
			sendToPlayer.sendMessage(message);
			ThePlugin.replayMsg.put(sendToPlayer.getName(), sender.getName());
		}
	}
}
