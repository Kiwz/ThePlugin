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
			if (args.length != 1) {
				sender.sendMessage(red + "Consolen kan bare bruke /plass liste og /plass <navn>");
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
				sender.sendMessage("Dette er en liste over alle plasser");
				return true;
			}
			else {
				sender.sendMessage("Dette er info om plassen du skrev navnet på");
				sender.sendMessage("Hvis plassen ikke finnes får du beskjed om dette");
				return true;
			}
		}
		
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("ny")) {
				sender.sendMessage(hPlaces.addPlace(args[1], player, "50"));
				return true;
			}
			else if (args[1].equalsIgnoreCase("flytt")) {
				sender.sendMessage("Flytter plassen til hit du står med standard strl");
				return true;
			}
			else if (args[1].equalsIgnoreCase("spawn")) {
				sender.sendMessage("Teleporterer deg til spawn i plassen du skrev");
				return true;
			}
			else if (args[1].equalsIgnoreCase("setspawn")) {
				sender.sendMessage("Setter spawnen der du står i plassen du skrev");
				return true;
			}
			else if (args[1].equalsIgnoreCase("navn")) {
				sender.sendMessage("Du må skrive hvilket navn du vil bytte til");
				return true;
			}
			else if (args[1].equalsIgnoreCase("sett")) {
				sender.sendMessage("Du må skrive pvp/monstre/dyr");
				return true;
			}
			else if (args[1].equalsIgnoreCase("inviter") || args[1].equalsIgnoreCase("fjern") || args[1].equalsIgnoreCase("eier")) {
				sender.sendMessage("Du må skrive navnet på en spiller");
				return true;
			}
			else if (args[0].equalsIgnoreCase("spiller")) {
				sender.sendMessage("Viser hvilke plasser den oppgitte spiller eier");
				sender.sendMessage("Viser hvilke plasser den oppgitte spiller er medlem av");
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
					sender.sendMessage(red + "Oppgitte plass finnes ikke");
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
						sender.sendMessage(red + "Oppgitte plass finnes ikke");
						return true;
					}
				}
				if (args[2].equalsIgnoreCase("monstre")) {
					sender.sendMessage("Toggler monstre");
					return true;
				}
				if (args[2].equalsIgnoreCase("dyr")) {
					sender.sendMessage("Toggler dyr");
					return true;
				}
				else {
					sender.sendMessage("pvp, monstre, dyr");
					return true;
				}
			}
			else if (args[1].equalsIgnoreCase("inviter")) {
				sender.sendMessage("Legger til den oppgitte spiller som medlem av din plass");
				return true;
			}
			else if (args[1].equalsIgnoreCase("fjern")) {
				sender.sendMessage("Fjerner det oppgitte medlem fra din by");
				return true;
			}
			else if (args[1].equalsIgnoreCase("eier")) {
				sender.sendMessage("Du gir bort eier-statusen til den oppgitte spilleren");
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
