package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.RemoveEntities;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EntityCommand {
	
	public void entity(CommandSender sender, String[] args) {
		RemoveEntities e = new RemoveEntities();
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Du må velge a (animals), m (monsters) eller d (drops)");
			sender.sendMessage(ThePlugin.c2 + "Eksempel: /entity m");
		}
		
		else if (!(sender instanceof Player) && args.length == 2) {
			sender.sendMessage(ThePlugin.c2 + "Du kan ikke velge avstand, skriv /entity <a, m, o>");
		}
		
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("a")) {
				sender.sendMessage(e.killAnimals());
			}
			else if (args[0].equalsIgnoreCase("m")) {
				sender.sendMessage(e.killMonsters());
			}
			else if (args[0].equalsIgnoreCase("d")) {
				sender.sendMessage(e.killDrops());
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Du må velge a (animals), m (monsters) eller d (drops)");
				sender.sendMessage(ThePlugin.c2 + "Eksempel: /entity m");
			}
		}
		
		else if (args.length > 1) {
			int distance = 50;
			if (args[1].matches("[0-9]+")) {
				distance = Integer.parseInt(args[1]);
			}
			if (args[0].equalsIgnoreCase("a")) {
				sender.sendMessage(e.killAnimals(player, distance));
			}
			else if (args[0].equalsIgnoreCase("m")) {
				sender.sendMessage(e.killMonsters(player, distance));
			}
			else if (args[0].equalsIgnoreCase("d")) {
				sender.sendMessage(e.killDrops(player, distance));
			}
			else {
				sender.sendMessage(ThePlugin.c2 + "Du må velge a (animals), m (monsters) eller d (drops)");
				sender.sendMessage(ThePlugin.c2 + "Eksempel: /entity m");
			}
		}
	}
}
