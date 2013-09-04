package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand {
	
	/**
	 * 
	 * @param sender player who issued the command
	 * @param cmd command that was issued
	 * @param args as String[] describing what we want to do with given world
	 * @return true no matter what
	 */
	public boolean world(CommandSender sender, Command cmd, String[] args) {
		HandleWorlds hWorlds = new HandleWorlds();
		HelpCommand help = new HelpCommand();
		Player player = Bukkit.getPlayer(sender.getName());

		for (String arg : args) {
			if (!arg.matches("[a-zA-Z_0-9]+")) {
				sender.sendMessage(ThePlugin.c2 + "Tillatte tegn er a-z A-Z 0-9 _");
				return true;
			}
		}
		
		if (args.length == 0) {
			help.customHelp(sender, cmd.getName(), "1", help());
			return true;
		}
		
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("liste")) {
				hWorlds.sendWorldList(sender);
				return true;
			}
			else if (args[0].length() == 1 && args[0].matches("[0-9]+")){
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
			else {
				hWorlds.sendWorld(sender, args[0]);
				return true;
			}
		}
		
		else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("claimable")) {
				sender.sendMessage(hWorlds.setClaimable(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("firespread")) {
				sender.sendMessage(hWorlds.setFireSpread(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("explosions")) {
				sender.sendMessage(hWorlds.setExplosions(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("monstergrief")) {
				sender.sendMessage(hWorlds.setMonsterGrief(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("trample")) {
				sender.sendMessage(hWorlds.setTrample(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("pvp")) {
				sender.sendMessage(hWorlds.setPvP(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("monsters")) {
				sender.sendMessage(hWorlds.setMonsters(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("animals")) {
				sender.sendMessage(hWorlds.setAnimals(args[0]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("keepspawn")) {
				sender.sendMessage(hWorlds.setKeepSpawnInMemory(args[0]));
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else if (args.length == 3) {
			if (args[1].equalsIgnoreCase("border") && args[2].matches("[0-9]+")) {
				player.sendMessage(hWorlds.setBorder(args[0], args[2]));
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else {
			help.customHelp(sender, cmd.getName(), args[0], help());
			return true;
		}
	}
		
		
		/*
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
			help.customHelp(sender, "1", help());
			return true;
		}
		
		else if (args.length == 1) {
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
			else if (args[0].matches("[0-9]+") && args[0].length() == 1) {
				help.customHelp(sender, args[0], help());
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
			if (args[0].equalsIgnoreCase("spiller")) {
				sender.sendMessage(hPlaces.getOwned(args[1]));
				sender.sendMessage(hPlaces.getMembered(args[1]));
				return true;
			}
			if (args[1].equalsIgnoreCase("ny")) {
				sender.sendMessage(hPlaces.addPlace(player, args[0], "40"));
				return true;
			}
			id = hPlaces.getID(args[0]);
			if (id == 0) {
				sender.sendMessage(ThePlugin.c2 + args[0] + " finnes ikke");
				return true;
			}
			if (args[1].equalsIgnoreCase("flytt")) {
				sender.sendMessage(hPlaces.setPlace(player, id, "40"));
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
			else if (args[1].equalsIgnoreCase("entre")) {
				sender.sendMessage(hPlaces.setEnter(player, id, ""));
				return true;
			}
			else if (args[1].equalsIgnoreCase("forlate")) {
				sender.sendMessage(hPlaces.setLeave(player, id, ""));
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
			else if (args[0].matches("[0-9]+") && args[0].length() == 1) {
				help.customHelp(sender, args[0], help());
				return true;
			}
			else {
				help.customHelp(sender, "1", help());
				return true;
			}
		}
		
		else if (args.length == 3) {
			if (args[1].equalsIgnoreCase("ny")) {
				if (!args[2].matches("[0-9]+")) {
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
				if (!args[2].matches("[0-9]+")) {
					sender.sendMessage(ThePlugin.c2 + "Størrelsen må defineres med tall");
					return true;
				}
				sender.sendMessage(hPlaces.setPlace(player, id, "50"));
				return true;
			}
			else if (args[1].equalsIgnoreCase("entre")) {
				sender.sendMessage(hPlaces.setEnter(player, id, args[2]));
				return true;
			}
			else if (args[1].equalsIgnoreCase("forlate")) {
				sender.sendMessage(hPlaces.setLeave(player, id, args[2]));
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
			else if (args[0].matches("[0-9]+") && args[0].length() == 1) {
				help.customHelp(sender, args[0], help());
				return true;
			}
			else {
				help.customHelp(sender, "1", help());
				return true;
			}
		}
		
		else if (args.length > 3 && (args[1].equalsIgnoreCase("entre") || args[1].equalsIgnoreCase("forlate"))) {
			id = hPlaces.getID(args[0]);
			if (id == 0) {
				sender.sendMessage(ThePlugin.c2 + args[0] + " finnes ikke");
				return true;
			}
			String arg = "";
			for (int i = 2; i < args.length; i++) {
				arg = arg + args[i] + " ";
			}
			arg = arg.trim();
			if (args[1].equalsIgnoreCase("entre")) {
				sender.sendMessage(hPlaces.setEnter(player, id, arg));
				return true;
			}
			else if (args[1].equalsIgnoreCase("forlate")) {
				sender.sendMessage(hPlaces.setLeave(player, id, arg));
				return true;
			}
			else if (args[0].matches("[0-9]+") && args[0].length() == 1) {
				help.customHelp(sender, args[0], help());
				return true;
			}
			else {
				help.customHelp(sender, "1", help());
				return true;
			}
		}
		else if (args[0].matches("[0-9]+") && args[0].length() == 1) {
			help.customHelp(sender, args[0], help());
			return true;
		}
		else {
			help.customHelp(sender, "1", help());
			return true;
		}
	}
	
	/**
	 * 
	 * @return Help entryes for /plass
	 */
	private String help() {
		ChatColor white = ChatColor.WHITE;
		ChatColor gold = ChatColor.GOLD;
		StringBuilder help = new StringBuilder();
		help.append(gold + "/world\n");
		help.append(white + "Viser denne hjelpe menyen\n");
		help.append(gold + "/world liste\n");
		help.append(white + "Viser liste over alle verdener\n");
		help.append(gold + "/world <verden-navn>\n");
		help.append(white + "Viser info om oppgitte verden\n");
		help.append(gold + "/world <verden-navn> border <radius>\n");
		help.append(white + "Setter ny grense for oppgitte verden\n");
		help.append(gold + "/world <verden-navn> claimable\n");
		help.append(white + "Skrur på/av mulighet for å lage plasser\n");
		help.append(gold + "/world <verden-navn> firespread\n");
		help.append(white + "Skrur på/av ill spredning\n");
		help.append(gold + "/world <verden-navn> explosions\n");
		help.append(white + "Skrur på/av eksplosjoner\n");
		help.append(gold + "/world <verden-navn> monstergrief\n");
		help.append(white + "Skrur på/av Monster grifing\n");
		help.append(gold + "/world <verden-navn> trample\n");
		help.append(white + "Skrur på/av tråkking\n");
		help.append(gold + "/world <verden-navn> pvp\n");
		help.append(white + "Skrur på/av PvP\n");
		help.append(gold + "/world <verden-navn> monsters\n");
		help.append(white + "Skrur på/av monstre\n");
		help.append(gold + "/world <verden-navn> animals\n");
		help.append(white + "Skrur på/av dyr\n");
		help.append(gold + "/world <verden-navn> keepspawn\n");
		help.append(white + "Skrur på/av beholde spawn i minne\n");
		return help.toString();
	}
}
