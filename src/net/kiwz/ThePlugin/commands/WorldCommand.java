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
			if (!arg.matches("[a-zA-Z-_0-9]+")) {
				sender.sendMessage(ThePlugin.c2 + "Tillatte tegn er a-z A-Z 0-9 _");
				return true;
			}
		}
		
		if (args.length == 0) {
			help.customHelp(sender, cmd.getName(), "1", help());
			return true;
		}
		
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
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
			if (args[1].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[0], "", "", ""));
				return true;
			}
			else if (args[1].equalsIgnoreCase("claimable")) {
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
			else if (args[1].equalsIgnoreCase("delete")) {
				sender.sendMessage(hWorlds.remWorld(args[0]));
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else if (args.length == 3) {
			if (args[1].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[0], args[2], "", ""));
				return true;
			}
			else if (args[1].equalsIgnoreCase("border") && args[2].matches("[0-9]+")) {
				player.sendMessage(hWorlds.setBorder(args[0], args[2]));
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else if (args.length == 4) {
			if (args[1].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[0], args[2], args[3], ""));
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else if (args.length == 5) {
			if (args[1].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[0], args[2], args[3], args[4]));
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
	
	/**
	 * 
	 * @return Help entryes for /plass
	 */
	private String help() {
		ChatColor white = ChatColor.WHITE;
		ChatColor gold = ChatColor.GOLD;
		ChatColor yellow = ChatColor.YELLOW;
		StringBuilder help = new StringBuilder();
		help.append(gold + "/world\n");
		help.append(white + "Viser denne hjelpe menyen\n");
		help.append(gold + "/world liste\n");
		help.append(white + "Viser liste over alle verdener\n");
		help.append(gold + "/world <verden-navn>\n");
		help.append(white + "Viser info om oppgitte verden\n");
		help.append(gold + "/world <verden-navn> delete\n");
		help.append(white + "Sletter oppgitte verden (filer vil bli flyttet til egen mappe)\n");
		help.append(gold + "/world <verden-navn> new\n");
		help.append(white + "Lager ny normal verden\n");
		help.append(yellow + "Environment: NORMAL, NETHER, THE_END\n");
		help.append(yellow + "Type: NORMAL, FLAT, LARGE_BIOMES, VERSION1\n");
		help.append(yellow + "Seed: Må være ett tall (kan være negativt)\n");
		help.append(gold + "/world <verden-navn> new [Environment]\n");
		help.append(white + "Lager ny verden med oppgitte omgivelsen\n");
		help.append(gold + "/world <verden-navn> new [Environment] [Type]\n");
		help.append(white + "Lager ny verden med oppgitte omgivelsen og typen\n");
		help.append(gold + "/world <verden-navn> new [Environment] [Type] [Seed]\n");
		help.append(white + "Lager ny verden med oppgitte omgivelse, type og frø\n");
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
