package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPCommand {
	
	public boolean tp(CommandSender sender, Command cmd, String[] args) {
		OnlinePlayer onlinePlayer = new OnlinePlayer();
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player) || args.length != 2) {
			sender.sendMessage(ThePlugin.c2 + "Spesifiser to spillere, hvem som skal bli teleportert til hvem");
			return true;
		}
		else if (args.length == 0) {
			return false;
		}
		else if (args.length == 1) {
			if (onlinePlayer.getPlayer(args[0]) != null) {
				Player target = onlinePlayer.getPlayer(args[0]);
				player.teleport(target);
				sender.sendMessage(ThePlugin.c1 + "Du ble teleportert til " + target.getName());
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet dette navnet");
			}
			return true;
		}
		else if (args.length == 2) {
			if ((onlinePlayer.getPlayer(args[0]) != null) || (onlinePlayer.getPlayer(args[1]) != null)) {
				Player target = onlinePlayer.getPlayer(args[0]);
				Player destination = onlinePlayer.getPlayer(args[1]);
				target.teleport(destination);
				target.sendMessage(ThePlugin.c1 + "Du ble teleportert til " + destination.getName() + " av " + sender.getName());
				sender.sendMessage(ThePlugin.c1 + "Du teleporterte " + target.getName() + " til " + destination.getName());
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Fant ingen spiller som passet en eller begge navnene!");
			}
			return true;
		}
		else if (args.length == 3) {
			sender.sendMessage("koordinat-teleportering kommer snart");
			return true;
		}
		else {
			return false;
		}
	}
}
