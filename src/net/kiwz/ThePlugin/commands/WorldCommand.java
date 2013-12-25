package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.ChunkLag;
import net.kiwz.ThePlugin.utils.FillWorld;
import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
		FillWorld fill = new FillWorld();
		ChunkLag chunkLag = new ChunkLag();
		HelpCommand help = new HelpCommand();

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
			else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender);
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
			if (args[0].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[1], "", "", ""));
				return true;
			}
			else if (args[0].equalsIgnoreCase("fill")) {
				fill.generateChunks(sender, args[1], "2");
				return true;
			}
			else if (args[0].equalsIgnoreCase("cancelfill")) {
				fill.cancelGeneration(sender, args[1]);
				return true;
			}
			else if (args[0].equalsIgnoreCase("claimable")) {
				sender.sendMessage(hWorlds.setClaimable(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("firespread")) {
				sender.sendMessage(hWorlds.setFireSpread(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("explosions")) {
				sender.sendMessage(hWorlds.setExplosions(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("monstergrief")) {
				sender.sendMessage(hWorlds.setMonsterGrief(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("trample")) {
				sender.sendMessage(hWorlds.setTrample(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("pvp")) {
				sender.sendMessage(hWorlds.setPvP(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("monsters")) {
				sender.sendMessage(hWorlds.setMonsters(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("animals")) {
				sender.sendMessage(hWorlds.setAnimals(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("keepspawn")) {
				sender.sendMessage(hWorlds.setKeepSpawnInMemory(args[1]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender, args[1]);
				return true;
			}
			else if (args[0].equalsIgnoreCase("delete")) {
				sender.sendMessage(hWorlds.remWorld(args[1]));
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[1], args[2], "", ""));
				return true;
			}
			else if (args[0].equalsIgnoreCase("border") && args[2].matches("[0-9]+")) {
				sender.sendMessage(hWorlds.setBorder(args[1], args[2]));
				return true;
			}
			else if (args[0].equalsIgnoreCase("fill")) {
				fill.generateChunks(sender, args[1], args[2]);
				return true;
			}
			else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender, args[1], args[2]);
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else if (args.length == 4) {
			if (args[0].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[1], args[2], args[3], ""));
				return true;
			}
			else {
				help.customHelp(sender, cmd.getName(), args[0], help());
				return true;
			}
		}
		
		else if (args.length == 5) {
			if (args[0].equalsIgnoreCase("new")) {
				sender.sendMessage(ThePlugin.c1 + "Loading new world....");
				sender.sendMessage(hWorlds.createWorld(args[1], args[2], args[3], args[4]));
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
		help.append(gold + "/world delete <verden-navn>\n");
		help.append(white + "Sletter oppgitte verden (filer vil bli flyttet til egen mappe)\n");
		help.append(gold + "/world new <verden-navn>\n");
		help.append(white + "Lager ny normal verden\n");
		help.append(yellow + "Environment: NORMAL, NETHER, THE_END\n");
		help.append(yellow + "Type: NORMAL, FLAT, LARGE_BIOMES, VERSION1\n");
		help.append(yellow + "Seed: Må være ett tall (kan være negativt)\n");
		help.append(gold + "/world new <verden-navn> [Environment]\n");
		help.append(white + "Lager ny verden med oppgitte omgivelsen\n");
		help.append(gold + "/world new <verden-navn> [Environment] [Type]\n");
		help.append(white + "Lager ny verden med oppgitte omgivelsen og typen\n");
		help.append(gold + "/world new <verden-navn> [Environment] [Type] [Seed]\n");
		help.append(white + "Lager ny verden med oppgitte omgivelse, type og frø\n");
		help.append(gold + "/world border <verden-navn> <radius>\n");
		help.append(white + "Setter ny grense for oppgitte verden\n");
		help.append(gold + "/world fill <verden-navn>\n");
		help.append(white + "Genererer alle chunks t.o.m. 12 chunks utenfor border\n");
		help.append(gold + "/world cancelfill <verden-navn>\n");
		help.append(white + "Avbryter en pågående generering av angitt verden\n");
		help.append(gold + "/world claimable <verden-navn>\n");
		help.append(white + "Skrur på/av mulighet for å lage plasser\n");
		help.append(gold + "/world firespread <verden-navn>\n");
		help.append(white + "Skrur på/av ill spredning\n");
		help.append(gold + "/world explosions <verden-navn>\n");
		help.append(white + "Skrur på/av eksplosjoner\n");
		help.append(gold + "/world monstergrief <verden-navn>\n");
		help.append(white + "Skrur på/av Monster grifing\n");
		help.append(gold + "/world trample <verden-navn>\n");
		help.append(white + "Skrur på/av tråkking\n");
		help.append(gold + "/world pvp <verden-navn>\n");
		help.append(white + "Skrur på/av PvP\n");
		help.append(gold + "/world monsters <verden-navn>\n");
		help.append(white + "Skrur på/av monstre\n");
		help.append(gold + "/world animals <verden-navn>\n");
		help.append(white + "Skrur på/av dyr\n");
		help.append(gold + "/world keepspawn <verden-navn>\n");
		help.append(white + "Skrur på/av beholde spawn i minne\n");
		help.append(gold + "/world lag [dyr, monstre, items, alt] [antall]\n");
		help.append(white + "Informerer om de mest laggete chunksa\n");
		return help.toString();
	}
}
