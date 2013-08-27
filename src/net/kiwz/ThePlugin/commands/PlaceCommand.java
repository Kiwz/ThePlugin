package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceCommand {
	
	/**
	 * 
	 * @param sender player who issued the command
	 * @param cmd command that was issued
	 * @param args as String[] describing what we want to do with given place
	 * @return true no matter what
	 */
	public boolean place(CommandSender sender, Command cmd, String[] args) {
		ChatColor yellow = ChatColor.YELLOW;
		ChatColor white = ChatColor.WHITE;
		ChatColor gold = ChatColor.GOLD;
		String hjelp1 = yellow + "--------- " + white + "Hjelp for /plass kommandoer " + yellow + "---------";
		String hjelp2 = gold + "/plass: " + white + "Viser denne denne hjelp menyen";
		String hjelp3 = gold + "/plass liste: " + white + "Viser en liste over alle plasser";
		HandlePlaces hPlaces = new HandlePlaces();
		Player player = Bukkit.getPlayer(sender.getName());
		int id;
		
		if (!(sender instanceof Player)) {
			int check = 0;
			if((args.length == 1 || args.length == 2) && args[0].equalsIgnoreCase("spiller")) {
				check = 1;
			}
			if (args.length == 1 && !args[0].equalsIgnoreCase("her")) {
				check = 1;
			}
			if (check == 0) {
				sender.sendMessage(ThePlugin.c2 + "Consolen kan bare bruke /plass liste, /plass spiller <navn> og /plass <navn>");
				return true;
			}
		}
		
		for (String arg : args) {
			if (!arg.matches("[a-zA-Z_0-9]+")) {
				sender.sendMessage(ThePlugin.c2 + "Tillatte tegn er a-z A-Z 0-9 _");
				return true;
			}
		}
		
		if (args.length == 0) {
			sender.sendMessage(hjelp1);
			sender.sendMessage(hjelp2);
			sender.sendMessage(hjelp3);
			return true;
		}
		
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("liste")) {
				hPlaces.sendPlaceList(sender);
				return true;
			}
			else if (args[0].equalsIgnoreCase("spiller")) {
				hPlaces.sendPlayersPlaceList(sender);
				return true;
			}
			else if (args[0].equalsIgnoreCase("her")) {
				hPlaces.sendPlaceHere(sender);
				return true;
			}
			else {
				id = hPlaces.getID(args[0]);
				if (id == 0) {
					sender.sendMessage(ThePlugin.c2 + args[0] + " finnes ikke");
					return true;
				}
				hPlaces.sendPlace(sender, id);
				return true;
			}
		}
		
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("ny")) {
				sender.sendMessage(hPlaces.addPlace(player, args[0], "50"));
				return true;
			}
			id = hPlaces.getID(args[0]);
			if (id == 0) {
				sender.sendMessage(ThePlugin.c2 + args[0] + " finnes ikke");
				return true;
			}
			if (args[1].equalsIgnoreCase("flytt")) {
				sender.sendMessage(hPlaces.setPlace(player, id, "50"));
				return true;
			}
			else if (args[1].equalsIgnoreCase("spawn")) {
				player.teleport(hPlaces.getSpawn(id));
				return true;
			}
			else if (args[1].equalsIgnoreCase("setspawn")) {
				sender.sendMessage(hPlaces.setSpawn(player, id));
				return true;
			}
			else if (args[1].equalsIgnoreCase("slett")) {
				sender.sendMessage(hPlaces.remPlace(player, id));
				return true;
			}
			else if (args[0].equalsIgnoreCase("spiller")) {
				sender.sendMessage(hPlaces.getOwned(args[1]));
				sender.sendMessage(hPlaces.getMembered(args[1]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("navn")) {
				sender.sendMessage(ThePlugin.c2 + "/plass <navn> navn <nytt-navn>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("sett")) {
				sender.sendMessage(ThePlugin.c2 + "/plass <navn> sett <[pvp] [monstre] [dyr]>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("inviter")) {
				sender.sendMessage(ThePlugin.c2 + "/plass <navn> inviter <spiller-navn>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("fjern")) {
				sender.sendMessage(ThePlugin.c2 + "/plass <navn> fjern <spiller-navn>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("eier")) {
				sender.sendMessage(ThePlugin.c2 + "/plass <navn> eier <spiller-navn>");
				return true;
			}
			else {
				sender.sendMessage("Viser hjelpe menyen til /plass");
				return true;
			}
		}
		
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("ny")) {
				if (!args[2].matches("[0-9]")) {
					sender.sendMessage(ThePlugin.c2 + "Størrelsen må defineres med tall");
					return true;
				}
				sender.sendMessage(hPlaces.addPlace(player, args[0], args[2]));
				return true;
			}
			id = hPlaces.getID(args[0]);
			if (id == 0) {
				sender.sendMessage(ThePlugin.c2 + args[0] + " finnes ikke");
				return true;
			}
			if (args[1].equalsIgnoreCase("flytt")) {
				if (!args[2].matches("[0-9]")) {
					sender.sendMessage(ThePlugin.c2 + "Størrelsen må defineres med tall");
					return true;
				}
				sender.sendMessage(hPlaces.setPlace(player, id, "50"));
				return true;
			}
			else if (args[1].equalsIgnoreCase("navn")) {
				sender.sendMessage(hPlaces.setName(player, id, args[2]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("sett")) {
				if (args[2].equalsIgnoreCase("pvp")) {
					sender.sendMessage(hPlaces.setPvP(player, id));
					return true;
				}
				else if (args[2].equalsIgnoreCase("monstre")) {
					sender.sendMessage(hPlaces.setMonsters(player, id));
					return true;
				}
				else if (args[2].equalsIgnoreCase("dyr")) {
					sender.sendMessage(hPlaces.setAnimals(player, id));
					return true;
				}
				else {
					sender.sendMessage(ThePlugin.c2 + "Du må skrive pvp/monstre/dyr");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("inviter")) {
				sender.sendMessage(hPlaces.addMember(player, id, args[2]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("fjern")) {
				sender.sendMessage(hPlaces.remMember(player, id, args[2]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("eier")) {
				sender.sendMessage(hPlaces.setOwner(player, id, args[2]));
				return true;
			}
			else {
				sender.sendMessage("Viser hjelpe menyen til /plass");
				return true;
			}
		}
		
		else {
			sender.sendMessage("Viser hjelpe menyen til /plass");
			return true;
		}
	}
}
