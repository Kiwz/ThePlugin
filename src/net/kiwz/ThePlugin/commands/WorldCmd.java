package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kiwz.ThePlugin.utils.ChunkLag;
import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.FillWorld;
import net.kiwz.ThePlugin.utils.MultiWorld;
import net.kiwz.ThePlugin.utils.MyWorld;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WorldCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, Command cmd, String[] args) {
		return new WorldCmd().world(sender, cmd, args);
	}
	
	public boolean world(CommandSender sender, Command cmd, String[] args) {
		ChunkLag chunkLag = new ChunkLag();

		for (String arg : args) {
			if (!arg.matches("[a-zA-Z-_0-9]+")) {
				sendWarning(sender, "Tillatte tegn er a-z A-Z 0-9 _");
				return true;
			}
		}
		
		MyWorld myWorld = null;
		if (args.length > 1) {
			if (!args[0].equalsIgnoreCase("new")) {
				myWorld = MyWorld.getWorld(args[1]);
				if (myWorld == null) {
					sendWarning(sender, args[1] + " finnes ikke");
					return true;
				}
			}
		}
		
		if (args.length == 0) {
			Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("liste")) {
				sendWorldList(sender);
			} else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender);
			} else if (args[0].length() == 1 && args[0].matches("[0-9]+")) {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /plass", "", help());
			} else {
				if (MyWorld.getWorld(args[0]) == null) {
					sendWarning(sender, args[0] + " finnes ikke");
				} else {
					sendWorld(sender, MyWorld.getWorld(args[0]));
				}
			}
		} else if (args[0].equalsIgnoreCase("new")) {
			if (args.length == 2) myWorld = new MyWorld(args[1]);
			if (args.length == 3) myWorld = new MyWorld(args[1], args[2]);
			if (args.length == 4) myWorld = new MyWorld(args[1], args[2], args[3]);
			if (args.length == 5) myWorld = new MyWorld(args[1], args[2], args[3], args[4]);
			
			if (myWorld.save()) {
				sendInfo(sender, "Starter generering av ny verden...");
				new MultiWorld().loadWorlds();
				String msg = Color.INFO + "World: " + Color.VARIABLE + myWorld.getName()
						+ Color.INFO + " Environment: " + Color.VARIABLE + myWorld.getEnv().toString()
						+ Color.INFO + " Type: " + Color.VARIABLE + myWorld.getType().toString()
						+ Color.INFO + " Seed: " + Color.VARIABLE + myWorld.getSeed();
				sendInfo(sender, msg);
			} else {
				sendWarning(sender, args[1] + " er allerede loaded");
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("fill")) {
				FillWorld fillWorld = FillWorld.getFillWorld(myWorld);
				if (fillWorld != null) {
					sendWarning(sender, "Fylling av " + Color.VARIABLE + myWorld.getName() + Color.WARNING + " er allerede startet");
				} else {
					new FillWorld(myWorld).start();
					sendInfo(sender, "Fylling av " + Color.VARIABLE + myWorld.getName() + Color.INFO + " er startet, se consolen for info");
				}
			} else if (args[0].equalsIgnoreCase("cancelfill")) {
				FillWorld fillWorld = FillWorld.getFillWorld(myWorld);
				if (fillWorld == null) {
					sendWarning(sender, "Det er ikke startet noen fylling av " + Color.VARIABLE + myWorld.getName());
				} else {
					fillWorld.cancelTask();
					sendInfo(sender, "Fylling av " + Color.VARIABLE + myWorld.getName() + Color.INFO + " er avbrutt");
				}
			} else if (args[0].equalsIgnoreCase("claimable")) {
				myWorld.setClaimable(!myWorld.getClaimable());
				if (myWorld.getClaimable()) sendInfo(sender, "Plasser er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Plasser er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("firespread")) {
				myWorld.setFireSpread(!myWorld.getFireSpread());
				if (myWorld.getFireSpread()) sendInfo(sender, "Ill spredning er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Ill spredning er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("explosions")) {
				myWorld.setExplosions(!myWorld.getExplosions());
				if (myWorld.getFireSpread()) sendInfo(sender, "Eksplosjoner er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Eksplosjoner er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("monstergrief")) {
				myWorld.setMonsterGrief(!myWorld.getMonsterGrief());
				if (myWorld.getMonsterGrief()) sendInfo(sender, "Monster grifing er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Monster grifing er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("trample")) {
				myWorld.setTrample(!myWorld.getTrample());
				if (myWorld.getTrample()) sendInfo(sender, "Tråkking er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Tråkking er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("pvp")) {
				myWorld.setPvp(!myWorld.getPvp());
				if (myWorld.getPvp()) sendInfo(sender, "PvP er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "PvP er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("monsters")) {
				myWorld.setMonsters(!myWorld.getMonsters());
				if (myWorld.getMonsters()) sendInfo(sender, "Monstre er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Monstre er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("animals")) {
				myWorld.setAnimals(!myWorld.getAnimals());
				if (myWorld.getAnimals()) sendInfo(sender, "Dyr er aktivert for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Dyr er deaktivert for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("keepspawn")) {
				myWorld.setKeepSpawn(!myWorld.getKeepSpawn());
				if (myWorld.getKeepSpawn()) sendInfo(sender, "Beholder spawn i minne for " + Color.VARIABLE + myWorld.getName());
				else sendInfo(sender, "Beholder ikke spawn i minne for " + Color.VARIABLE + myWorld.getName());
			} else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender, args[1]);
			} else if (args[0].equalsIgnoreCase("delete")) {
				FillWorld.getFillWorld(myWorld).cancelTask();
				if (myWorld.delete()) sendInfo(sender, Color.VARIABLE + myWorld.getName() + Color.INFO + " er nå slettet");
				else sendWarning(sender, Color.VARIABLE + myWorld.getName() + Color.WARNING + " kunne ikke slettes");
			} else {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /plass", "", help());
			}
			new MultiWorld().loadWorlds();
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("border")) {
				myWorld.setBorder(Util.parseInt(args[2]));
				int size = myWorld.getBorder() * 2;
				sendInfo(sender, "Ny størrelse for " + Color.VARIABLE + myWorld.getName() + " er " + Color.VARIABLE + size);
			} else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender, args[1], args[2]);
			} else {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /plass", "", help());
			}
		} else {
			Util.sendAsPages(sender, args[0], 0, "Hjelp: /plass", "", help());
		}
		return true;
	}
	
	private void sendInfo(CommandSender sender, String string) {
		sender.sendMessage(Color.INFO + string);
	}
	
	private void sendWarning(CommandSender sender, String string) {
		sender.sendMessage(Color.WARNING + string);
	}
	
	private void sendWorld(CommandSender sender, MyWorld myWorld) {
		List<String> list = new ArrayList<String>();
		int size = myWorld.getBorder() * 2;
		String claimable = "Nei";
		if (myWorld.getClaimable()) claimable = "Ja";
		String fireSpread = "Nei";
		if (myWorld.getFireSpread()) fireSpread = "Ja";
		String explosions = "Nei";
		if (myWorld.getExplosions()) explosions = "Ja";
		String monsterGrief = "Nei";
		if (myWorld.getMonsterGrief()) monsterGrief = "Ja";
		String trample = "Nei";
		if (myWorld.getTrample()) trample = "Ja";
		String pvp = "Nei";
		if (myWorld.getPvp()) pvp = "Ja";
		String monsters = "Nei";
		if (myWorld.getMonsters()) monsters = "Ja";
		String animals = "Nei";
		if (myWorld.getAnimals()) animals = "Ja";
		String keepSpawn = "Nei";
		if (myWorld.getKeepSpawn()) keepSpawn = "Ja";
		String environment = myWorld.getEnv().toString();
		String type = myWorld.getType().toString();
		String seed = myWorld.getSeed() + "";
		
		list.add(Color.INFO + "Størrelse: " + Color.VARIABLE + size);
		list.add(Color.INFO + "Plasser: " + Color.VARIABLE + claimable);
		list.add(Color.INFO + "Ill spredning: " + Color.VARIABLE + fireSpread);
		list.add(Color.INFO + "Eksplosjoner: " + Color.VARIABLE + explosions);
		list.add(Color.INFO + "Monster grifing: " + Color.VARIABLE + monsterGrief);
		list.add(Color.INFO + "Tråkking: " + Color.VARIABLE + trample);
		list.add(Color.INFO + "PvP: " + Color.VARIABLE + pvp);
		list.add(Color.INFO + "Monstre: " + Color.VARIABLE + monsters);
		list.add(Color.INFO + "Dyr: " + Color.VARIABLE + animals);
		list.add(Color.INFO + "Behold spawn i minne: " + Color.VARIABLE + keepSpawn);
		list.add(Color.INFO + "Frø: " + Color.VARIABLE + seed);

		String about = (myWorld.getName().toUpperCase());
		String adminInfo = (environment + ", " + type);
		Util.sendAsPages(sender, "1", 10, about, adminInfo, list);
	}
	
	private void sendWorldList(CommandSender sender) {
		List<String> list = new ArrayList<String>();
		for (World world : server.getWorlds()) {
			list.add(Color.VARIABLE + world.getName().toUpperCase());
		}
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		String about = ("Liste over alle verdener på denne server");
		Util.sendAsPages(sender, "1", 10, about, "", list);
	}
	
	private List<String> help() {
		ChatColor white = ChatColor.WHITE;
		ChatColor gold = ChatColor.GOLD;
		ChatColor yellow = ChatColor.YELLOW;
		List<String> help = new ArrayList<String>();
		help.add(gold + "/world");
		help.add(white + "Viser denne hjelpe menyen");
		help.add(gold + "/world liste");
		help.add(white + "Viser liste over alle verdener");
		help.add(gold + "/world <verden-navn>");
		help.add(white + "Viser info om oppgitte verden");
		help.add(gold + "/world delete <verden-navn>");
		help.add(white + "Sletter oppgitte verden (filer vil bli flyttet til egen mappe)");
		help.add(gold + "/world new <verden-navn>");
		help.add(white + "Lager ny normal verden");
		help.add(yellow + "Environment: NORMAL, NETHER, THE_END");
		help.add(yellow + "Type: NORMAL, FLAT, LARGE_BIOMES, VERSION1");
		help.add(yellow + "Seed: Må være ett tall (kan være negativt)");
		help.add(gold + "/world new <verden-navn> [Environment]");
		help.add(white + "Lager ny verden med oppgitte omgivelsen");
		help.add(gold + "/world new <verden-navn> [Environment] [Type]");
		help.add(white + "Lager ny verden med oppgitte omgivelsen og typen");
		help.add(gold + "/world new <verden-navn> [Environment] [Type] [Seed]");
		help.add(white + "Lager ny verden med oppgitte omgivelse, type og frø");
		help.add(gold + "/world border <verden-navn> <radius>");
		help.add(white + "Setter ny grense for oppgitte verden");
		help.add(gold + "/world fill <verden-navn>");
		help.add(white + "Genererer alle chunks t.o.m. 12 chunks utenfor border");
		help.add(gold + "/world cancelfill <verden-navn>");
		help.add(white + "Avbryter en pågående generering av angitt verden");
		help.add(gold + "/world claimable <verden-navn>");
		help.add(white + "Skrur på/av mulighet for å lage plasser");
		help.add(gold + "/world firespread <verden-navn>");
		help.add(white + "Skrur på/av ill spredning");
		help.add(gold + "/world explosions <verden-navn>");
		help.add(white + "Skrur på/av eksplosjoner");
		help.add(gold + "/world monstergrief <verden-navn>");
		help.add(white + "Skrur på/av Monster grifing");
		help.add(gold + "/world trample <verden-navn>");
		help.add(white + "Skrur på/av tråkking");
		help.add(gold + "/world pvp <verden-navn>");
		help.add(white + "Skrur på/av PvP");
		help.add(gold + "/world monsters <verden-navn>");
		help.add(white + "Skrur på/av monstre");
		help.add(gold + "/world animals <verden-navn>");
		help.add(white + "Skrur på/av dyr");
		help.add(gold + "/world keepspawn <verden-navn>");
		help.add(white + "Skrur på/av beholde spawn i minne");
		help.add(gold + "/world lag [dyr, monstre, items, alt] [antall]");
		help.add(white + "Informerer om de mest laggete chunksa");
		return help;
	}
}
