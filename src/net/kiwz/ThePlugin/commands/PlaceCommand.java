package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.HandlePlaces;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceCommand {
	
	public boolean place(CommandSender sender, Command cmd, String[] args) {
		HandlePlaces hPlaces = new HandlePlaces();
		Player player = Bukkit.getPlayer(sender.getName());
		
		if (args.length == 0) {
			sender.sendMessage("Dette er hjelpemenyen for /plass");
			return true;
		}
		
		if (args.length > 2 && args[0].equalsIgnoreCase("ny") && args[1].matches("[a-zA-Z]+") && args[2].matches("[0-9]+")) {
			sender.sendMessage(hPlaces.addPlace(args[1], player, args[2]));
			return true;
		}
		
		if (args[0].equalsIgnoreCase("info")) {
			return true;
		}
		
		if (args[0].equalsIgnoreCase("endre")) {
			return true;
		}
		return false;
	}
}
