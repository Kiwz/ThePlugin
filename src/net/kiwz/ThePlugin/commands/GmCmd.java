package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new GmCmd().gm(sender, args);
	}
	
	public boolean gm(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (args.length == 0 && player == null) {
			sender.sendMessage(Color.WARNING + "Spesifiser en spiller");
			return true;
		} else if (args.length == 0) {
			if (player.getGameMode().equals(GameMode.SURVIVAL)) {
				player.setGameMode(GameMode.CREATIVE);
				sender.sendMessage(Color.INFO + "Du er nå i Creative modus");
			}
			else {
				player.setGameMode(GameMode.SURVIVAL);
				sender.sendMessage(Color.INFO + "Du er nå i Survival modus");
			}
			return true;
		} else {
			Player target = MyPlayer.getOnlinePlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Color.PLAYER + args[0] + Color.WARNING + " er ikke online");
				return true;
			}
			if (target.getGameMode().equals(GameMode.SURVIVAL)) {
				target.setGameMode(GameMode.CREATIVE);
				target.sendMessage(Color.PLAYER + sender.getName() + Color.INFO + " endret modusen din til Creative");
				sender.sendMessage(Color.PLAYER + target.getName() + Color.INFO + " er nå i Creative modus");
			}
			else {
				target.setGameMode(GameMode.SURVIVAL);
				target.sendMessage(Color.PLAYER + sender.getName() + Color.INFO + " endret modusen din til Survival");
				sender.sendMessage(Color.PLAYER + target.getName() + Color.INFO + " er nå i Survival modus");
			}
			return true;
		}
	}
}
