package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyWorld;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCmd {
	private Server server = Bukkit.getServer();

	public static boolean execGet(CommandSender sender, String[] args) {
		return new SpawnCmd().spawn(sender, args);
	}
	
	public static boolean execSet(CommandSender sender) {
		return new SpawnCmd().setSpawn(sender);
	}
	
	private boolean spawn(CommandSender sender, String[] args) {
		Player player = server.getPlayer(sender.getName());
		
		if (player == null) {
			sender.sendMessage(Color.COMMAND + "/spawn " + Color.WARNING + "kan bare brukes av spillere");
		} else if (args.length == 0) {
			player.teleport(MyWorld.getWorld(player.getWorld()).getSpawn());
		} else {
			MyWorld myWorld = MyWorld.getWorld(args[0]);
			if (myWorld == null) {
				sender.sendMessage(Color.WARNING + args[0] + " finnes ikke");
			} else {
				player.teleport(myWorld.getSpawn());
			}
		}
		return true;
	}
	
	private boolean setSpawn(CommandSender sender) {
		Player player = server.getPlayer(sender.getName());
		
		if (player == null) {
			sender.sendMessage(Color.COMMAND + "/setspawn " + Color.WARNING + "kan bare brukes av spillere");
		} else {
			MyWorld.getWorld(player.getWorld()).setSpawn(player.getLocation());;
			sender.sendMessage(Color.INFO + "Ny spawn for " + Color.VARIABLE + player.getWorld().getName() + Color.INFO + " er satt her");
			return true;
		}
		return true;
	}
}
