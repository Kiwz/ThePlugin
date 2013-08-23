package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.HandlePlaces;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceCommand {
	
	public boolean place(CommandSender sender, Command cmd, String[] args) {
		ChatColor gold = ChatColor.GOLD;
		ChatColor yellow = ChatColor.YELLOW;
		ChatColor white = ChatColor.WHITE;
		ChatColor red = ChatColor.RED;
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
				sender.sendMessage(red + "Consolen kan bare bruke /plass liste, /plass spiller <navn> og /plass <navn>");
				return true;
			}
		}
		
		for (String arg : args) {
			if (!arg.matches("[a-zA-Z_0-9]+")) {
				sender.sendMessage(red + "Tillatte tegn er a-z A-Z 0-9 _");
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
			else if (args[0].equalsIgnoreCase("her")) {
				int locX = (int) player.getLocation().getX();
				int locZ = (int) player.getLocation().getZ();
				hPlaces.sendPlaceHere(sender, locX, locZ);
				return true;
			}
			else if (args[0].equalsIgnoreCase("spiller")) {
				hPlaces.sendPlayersPlaceList(sender);
				return true;
			}
			else {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					hPlaces.sendPlace(id, sender);
					return true;
				}
				else {
					sender.sendMessage(red + args[0] + " finnes ikke");
					return true;
				}
			}
		}
		
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("ny")) {
				sender.sendMessage(hPlaces.addPlace(args[0], player, "50"));
				return true;
			}
			else if (args[1].equalsIgnoreCase("flytt")) {
				// IKKE FERDIG!!!!
				sender.sendMessage("Flytter plassen til hit du står med standard strl");
				return true;
			}
			else if (args[1].equalsIgnoreCase("spawn")) {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					if (!player.teleport(hPlaces.getSpawn(id))) {
						sender.sendMessage(red + "Beklager, det skjedde en feil");
						return true;
					}
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("setspawn")) {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					sender.sendMessage(hPlaces.setSpawn(player, id));
					return true;
				}
				else {
					sender.sendMessage(red + args[0] + " finnes ikke");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("slett")) {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					sender.sendMessage(hPlaces.remPlace(id, player));
					return true;
				}
				else {
					sender.sendMessage(red + args[0] + " finnes ikke");
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("spiller")) {
				sender.sendMessage(hPlaces.getOwned(args[1]));
				sender.sendMessage(hPlaces.getMembered(args[1]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("navn")) {
				sender.sendMessage(red + "/plass <navn> navn <nytt-navn>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("sett")) {
				sender.sendMessage(red + "/plass <navn> sett <[pvp] [monstre] [dyr]>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("inviter")) {
				sender.sendMessage("/plass <navn> inviter <spiller-navn>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("fjern")) {
				sender.sendMessage("/plass <navn> fjern <spiller-navn>");
				return true;
			}
			else if (args[1].equalsIgnoreCase("eier")) {
				sender.sendMessage("/plass <navn> eier <spiller-navn>");
				return true;
			}
			else {
				sender.sendMessage("Viser hjelpe menyen til /plass");
			}
		}
		
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("ny")) {
				if (args[2].matches("[0-9]")) {
					sender.sendMessage(hPlaces.addPlace(args[0], player, args[2]));
					return true;
				}
				else {
					sender.sendMessage("Størrelsen må defineres med tall");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("flytt")) {
				if (args[2].matches("[0-9]")) {
					// IKKE FERDIG!!!!
					sender.sendMessage("Flytter plassen til hit du står med oppgitte strl");
					return true;
				}
				else {
					sender.sendMessage("Størrelsen må defineres med tall");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("navn")) {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					sender.sendMessage(hPlaces.setName(player, id, args[2]));
					return true;
				}
				else {
					sender.sendMessage(red + args[0] + " finnes ikke");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("sett")) {
				if (args[2].equalsIgnoreCase("pvp")) {
					id = hPlaces.getID(args[0]);
					if (id != 0) {
						sender.sendMessage(hPlaces.setPvP(player, id));
						return true;
					}
					else {
						sender.sendMessage(red + args[0] + " finnes ikke");
						return true;
					}
				}
				if (args[2].equalsIgnoreCase("monstre")) {
					id = hPlaces.getID(args[0]);
					if (id != 0) {
						sender.sendMessage(hPlaces.setMonsters(player, id));
						return true;
					}
					else {
						sender.sendMessage(red + args[0] + " finnes ikke");
						return true;
					}
				}
				if (args[2].equalsIgnoreCase("dyr")) {
					id = hPlaces.getID(args[0]);
					if (id != 0) {
						sender.sendMessage(hPlaces.setAnimals(player, id));
						return true;
					}
					else {
						sender.sendMessage(red + args[0] + " finnes ikke");
						return true;
					}
				}
				else {
					sender.sendMessage("Du må skrive pvp/monstre/dyr");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("inviter")) {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					sender.sendMessage(hPlaces.addMember(player, id, args[2]));
					return true;
				}
				else {
					sender.sendMessage(red + args[0] + " finnes ikke");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("fjern")) {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					sender.sendMessage(hPlaces.remMember(player, id, args[2]));
					return true;
				}
				else {
					sender.sendMessage(red + args[0] + " finnes ikke");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("eier")) {
				id = hPlaces.getID(args[0]);
				if (id != 0) {
					sender.sendMessage(hPlaces.setOwner(player, id, args[2]));
					return true;
				}
				else {
					sender.sendMessage(red + args[0] + " finnes ikke");
					return true;
				}
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
