package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;

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
		HandlePlaces hPlaces = new HandlePlaces();
		Player player = Bukkit.getPlayer(sender.getName());
		int id;
		
		if (!(sender instanceof Player)) {
			int check = 0;
			if (args.length == 0) {
				check = 1;
			}
			if((args.length == 1 || args.length == 2) && args[0].equalsIgnoreCase("spiller")) {
				check = 1;
			}
			if (args.length == 1 && !args[0].equalsIgnoreCase("her")) {
				check = 1;
			}
			if (check == 0) {
				sender.sendMessage(ThePlugin.c2 + "Consolen kan bare bruke /plass, /plass liste, /plass spiller <navn> og /plass <navn>");
				return true;
			}
		}
		
		for (String arg : args) {
			if (!arg.matches("[a-zA-Z-_0-9æøåÆØÅ]+")) {
				sender.sendMessage(ThePlugin.c2 + "Tillatte tegn er a-å A-Å 0-9 - _");
				return true;
			}
		}
		
		if (args.length == 0) {
			hPlaces.sendHelp(sender, "1", help());
			return true;
		}
		
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("liste")) {
				hPlaces.sendPlaceList(sender, "1");
				return true;
			}
			else if (args[0].equalsIgnoreCase("spiller")) {
				hPlaces.sendPlayersPlaceList(sender, "1");
				return true;
			}
			else if (args[0].equalsIgnoreCase("her")) {
				hPlaces.sendPlaceHere(sender);
				return true;
			}
			else if (args[0].length() == 1) {
				hPlaces.sendHelp(sender, args[0], help());
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
			if (args[0].equalsIgnoreCase("liste")) {
				hPlaces.sendPlaceList(sender, args[1]);
				return true;
			}
			else if (args[0].equalsIgnoreCase("spiller")) {
				hPlaces.sendPlayersPlaceList(sender, args[1]);
				return true;
			}
			if (args[0].equalsIgnoreCase("spiller")) {
				sender.sendMessage(hPlaces.getOwned(args[1]));
				sender.sendMessage(hPlaces.getMembered(args[1]));
				return true;
			}
			if (args[0].equalsIgnoreCase("ny")) {
				sender.sendMessage(hPlaces.addPlace(player, args[1], "40"));
				return true;
			}
			id = hPlaces.getID(args[1]);
			if (id == 0) {
				sender.sendMessage(ThePlugin.c2 + args[1] + " finnes ikke");
				return true;
			}
			if (args[0].equalsIgnoreCase("flytt")) {
				sender.sendMessage(hPlaces.setPlace(player, id));
				return true;
			}
			else if (args[0].equalsIgnoreCase("spawn")) {
				if (hPlaces.isPriv(id) && !(player.isOp() || hPlaces.isOwner(player.getName(), id) || hPlaces.isMember(player.getName(), id))) {
					sender.sendMessage(ThePlugin.c2 + args[1] + " er en privat plass og du kan ikke teleportere hit");
				}
				else if (!hPlaces.isSpawnSafe(player, id)){
					sender.sendMessage(ThePlugin.c2 + args[1] + " har ingen spawn");
				}
				else {
					player.teleport(hPlaces.getSpawn(id));
				}
				return true;
			}
			else if (args[0].equalsIgnoreCase("setspawn")) {
				sender.sendMessage(hPlaces.setSpawn(player, id));
				return true;
			}
			else if (args[0].equalsIgnoreCase("slett")) {
				sender.sendMessage(hPlaces.remPlace(player, id));
				return true;
			}
			else if (args[0].equalsIgnoreCase("entre")) {
				sender.sendMessage(hPlaces.setEnter(player, id, ""));
				return true;
			}
			else if (args[0].equalsIgnoreCase("forlate")) {
				sender.sendMessage(hPlaces.setLeave(player, id, ""));
				return true;
			}
			else if (args[0].equalsIgnoreCase("navn")) {
				sender.sendMessage(ThePlugin.c2 + "/plass navn <plass-navn> <nytt-plass-navn>");
				return true;
			}
			else if (args[0].equalsIgnoreCase("toggle")) {
				sender.sendMessage(ThePlugin.c2 + "/plass toggle <plass-navn> <[privat] [pvp] [monstre] [dyr]>");
				return true;
			}
			else if (args[0].equalsIgnoreCase("medlem")) {
				sender.sendMessage(ThePlugin.c2 + "/plass medlem <plass-navn> <spiller-navn>");
				return true;
			}
			else if (args[0].equalsIgnoreCase("spark")) {
				sender.sendMessage(ThePlugin.c2 + "/plass spark <plass-navn> <spiller-navn>");
				return true;
			}
			else if (args[0].equalsIgnoreCase("eier")) {
				sender.sendMessage(ThePlugin.c2 + "/plass eier <plass-navn> <spiller-navn>");
				return true;
			}
			else {
				hPlaces.sendHelp(sender, args[0], help());
				return true;
			}
		}
		
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("ny")) {
				if (!args[2].matches("[0-9]+")) {
					sender.sendMessage(ThePlugin.c2 + "Størrelsen må defineres med tall");
					return true;
				}
				sender.sendMessage(hPlaces.addPlace(player, args[1], args[2]));
				return true;
			}
			id = hPlaces.getID(args[1]);
			if (id == 0) {
				sender.sendMessage(ThePlugin.c2 + args[1] + " finnes ikke");
				return true;
			}
			if (args[0].equalsIgnoreCase("endre")) {
				if (!args[2].matches("[0-9]+")) {
					sender.sendMessage(ThePlugin.c2 + "Størrelsen må defineres med tall");
					return true;
				}
				sender.sendMessage(hPlaces.setRadius(player, id, args[2]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("entre")) {
				sender.sendMessage(hPlaces.setEnter(player, id, args[2]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("forlate")) {
				sender.sendMessage(hPlaces.setLeave(player, id, args[2]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("navn")) {
				sender.sendMessage(hPlaces.setName(player, id, args[2]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("toggle")) {
				if (args[2].equalsIgnoreCase("privat")) {
					sender.sendMessage(hPlaces.setPriv(player, id));
					return true;
				}
				else if (args[2].equalsIgnoreCase("pvp")) {
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
			else if (args[0].equalsIgnoreCase("medlem")) {
				sender.sendMessage(hPlaces.addMember(player, id, args[2]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("spark")) {
				sender.sendMessage(hPlaces.remMember(player, id, args[2]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("eier")) {
				sender.sendMessage(hPlaces.setOwner(player, id, args[2]));
				return true;
			}
			else {
				hPlaces.sendHelp(sender, args[0], help());
				return true;
			}
		}
		
		else if (args.length > 3 && (args[0].equalsIgnoreCase("entre") || args[0].equalsIgnoreCase("forlate"))) {
			id = hPlaces.getID(args[1]);
			if (id == 0) {
				sender.sendMessage(ThePlugin.c2 + args[1] + " finnes ikke");
				return true;
			}
			String arg = "";
			for (int i = 2; i < args.length; i++) {
				arg = arg + args[i] + " ";
			}
			arg = arg.trim();
			if (args[0].equalsIgnoreCase("entre")) {
				sender.sendMessage(hPlaces.setEnter(player, id, arg));
				return true;
			}
			else if (args[0].equalsIgnoreCase("forlate")) {
				sender.sendMessage(hPlaces.setLeave(player, id, arg));
				return true;
			}
			else {
				hPlaces.sendHelp(sender, args[0], help());
				return true;
			}
		}
		else {
			hPlaces.sendHelp(sender, args[0], help());
			return true;
		}
	}
	
	/**
	 * 
	 * @return Help entryes for /plass
	 */
	private ArrayList<String> help() {
		ChatColor white = ChatColor.WHITE;
		ChatColor gold = ChatColor.GOLD;
		ArrayList<String> help = new ArrayList<String>();
		help.add(gold + "/plass her");
		help.add(white + "Viser info om plassen du står på");
		help.add(gold + "/plass <plass-navn>");
		help.add(white + "Viser info om angitte plass");
		help.add(gold + "/plass ny <plass-navn>");
		help.add(white + "Lager ny plass der du står (størrelse blir 81x81)");
		help.add(gold + "/plass ny <plass-navn> <radius>");
		help.add(white + "Lager ny plass der du står med ønsket radius");
		help.add(gold + "/plass flytt <plass-navn>");
		help.add(white + "Flytter plassen din til der du står");
		help.add(gold + "/plass endre <plass-navn> <radius>");
		help.add(white + "Endrer størrelsen på angitt plass til angitt radius");
		help.add(gold + "/plass spawn <plass-navn>");
		help.add(white + "Teleporterer deg til spawn i angitt plass");
		help.add(gold + "/plass setspawn <plass-navn>");
		help.add(white + "Setter ny spawn til der du står for angitt plass");
		help.add(gold + "/plass medlem <plass-navn> <spiller-navn>");
		help.add(white + "Inviterer spiller til å bli medlem av angitt plass");
		help.add(gold + "/plass spark <plass-navn> <spiller-navn>");
		help.add(white + "Fjerner spiller som medlem av angitt plass");
		help.add(gold + "/plass eier <plass-navn> <spiller-navn>");
		help.add(white + "Setter ny eier i angitt plass, DU kan IKKE gjøre om dette");
		help.add(gold + "/plass navn <plass-navn> <nytt-plass-navn>");
		help.add(white + "Bytter navn på angitt plass");
		help.add(gold + "/plass toggle <plass-navn> [privat, pvp, monstre, dyr]");
		help.add(white + "Skrur på/av privat, pvp, monstre eller dyr for angitt plass");
		help.add(gold + "/plass entre <plass-navn> <ny velkomst-melding>");
		help.add(white + "Setter ny velkomst-melding for angitt plass");
		help.add(gold + "/plass entre <plass-navn>");
		help.add(white + "Fjerner velkomst-melding for angitt plass");
		help.add(gold + "/plass forlate <plass-navn> <ny forlat-melding>");
		help.add(white + "Setter ny forlat-melding for angitt plass");
		help.add(gold + "/plass forlate <plass-navn>");
		help.add(white + "Fjerner forlat-melding for angitt plass");
		help.add(gold + "/plass slett <plass-navn>");
		help.add(white + "Sletter angitt plass");
		help.add(gold + "/plass liste");
		help.add(white + "Viser en liste over alle plasser");
		help.add(gold + "/plass spiller");
		help.add(white + "Viser en liste over spillere som har plasser");
		help.add(gold + "/plass spiller <spiller-navn>");
		help.add(white + "Viser hvilke plasser spilleren er eier og medlem av");
		return help;
	}
}
