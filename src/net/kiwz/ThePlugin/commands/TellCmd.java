package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TellCmd {
	public static boolean exec(CommandSender sender, String[] args) {
		return new TellCmd().tell(sender, args);
	}
	
	private boolean tell(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Color.WARNING + "Du må skrive navnet på en spiller");
		} else if (MyPlayer.getOnlinePlayer(args[0]) == null) {
			sender.sendMessage(Color.WARNING + args[0] + " er ikke online");
		} else if (args.length == 1) {
			sender.sendMessage(Color.WARNING + "Du må skrive en melding til " + args[0]);
		} else {
			Player target = MyPlayer.getOnlinePlayer(args[0]);
			String message = "";
			args[0] = "";
			for (String s : args) {
				message = message + s + " ";
			}
			message = Color.WHISPER + sender.getName() + " -> " + target.getName() + ": " + message;
			sender.sendMessage(message);
			target.sendMessage(message);
			ReplayCmd.replays.put(sender.getName(), target.getName());
			ReplayCmd.replays.put(target.getName(), sender.getName());
		}
		return true;
	}
}
