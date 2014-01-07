package net.kiwz.ThePlugin.commands;

import java.util.HashMap;

import net.kiwz.ThePlugin.utils.Color;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplayCmd {
	private Server server = Bukkit.getServer();
	public static HashMap<String, String> replays = new HashMap<String, String>();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new ReplayCmd().replay(sender, args);
	}
	
	private boolean replay(CommandSender sender, String[] args) {
		String targetName = ReplayCmd.replays.get(sender.getName());
		
		if (targetName == null) {
			sender.sendMessage(Color.WARNING + "Det er ingen du kan svare");
			return true;
		}

		Player target = server.getPlayer(targetName);
		if (target == null) {
			sender.sendMessage(Color.WARNING + targetName + " er ikke online");
		} else if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Du må skrive en melding");
		} else {
			String message = "";
			for (String s : args) {
				message = message + s + " ";
			}
			message = Color.WHISPER + sender.getName() + " -> " + target.getName() + ": " + message;
			sender.sendMessage(message);
			target.sendMessage(message);
			ReplayCmd.replays.put(target.getName(), sender.getName());
		}
		return true;
	}
}
