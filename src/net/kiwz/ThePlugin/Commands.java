package net.kiwz.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		ChatColor dgreen = ChatColor.DARK_GREEN;
		ChatColor red = ChatColor.RED;
		Player p = Bukkit.getServer().getPlayer(sender.getName());

		if (cmd.getName().equalsIgnoreCase("gm") && sender.isOp()) {
			String gmStatus = p.getGameMode().toString();
			if (gmStatus == "SURVIVAL") {
				p.setGameMode(GameMode.CREATIVE);
				sender.sendMessage(dgreen + "Du er nå i Creative modus!");
			}
			if (gmStatus == "CREATIVE") {
				p.setGameMode(GameMode.SURVIVAL);
				sender.sendMessage(dgreen + "Du er nå i Survival modus!");
			}
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("test") && sender.isOp()) {
			sender.sendMessage(dgreen + "Du er OP og dette er test-kommandoen");
			return true;
		}
		
		else {
			sender.sendMessage(red + "Du må være Admin for å bruke kommandoen /" + cmd.getName());
			return true;
		}
	}
}
