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
		String info = "";
		String warning = "";
		ChunkLag chunkLag = new ChunkLag();

		for (String arg : args) {
			if (!arg.matches("[a-zA-Z-_0-9]+")) {
				sender.sendMessage(Color.WARNING + "Tillatte tegn er a-z A-Z 0-9 _");
				return true;
			}
		}
		
		MyWorld myWorld = null;
		if (args.length > 1) {
			if (!args[0].equalsIgnoreCase("new")) {
				myWorld = MyWorld.getWorld(args[1]);
				if (myWorld == null || myWorld.isRemoved()) {
					sender.sendMessage(Color.VARIABLE + args[1] + Color.WARNING + " finnes ikke");
					return true;
				}
			}
		}
		
		if (args.length == 0) {
			Util.sendAsPages(sender, "1", 0, "Hjelp: /world", "", help());
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("liste")) {
				sendWorldList(sender);
			} else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender);
			} else if (args[0].length() == 1 && args[0].matches("[0-9]+")) {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /world", "", help());
			} else {
				if (MyWorld.getWorld(args[0]) == null) {
					warning = Color.VARIABLE + args[0] + Color.WARNING + " finnes ikke";
				} else {
					sendWorld(sender, MyWorld.getWorld(args[0]));
				}
			}
		} else if (args[0].equalsIgnoreCase("new")) {
			if (Bukkit.getServer().getWorld(args[1].toLowerCase()) != null) {
				warning = Color.VARIABLE + args[1] + Color.WARNING + " eksisterer allerede";
			} else {
				if (args.length == 2) myWorld = new MyWorld(args[1]);
				if (args.length == 3) myWorld = new MyWorld(args[1], args[2]);
				if (args.length == 4) myWorld = new MyWorld(args[1], args[2], args[3]);
				if (args.length >= 5) myWorld = new MyWorld(args[1], args[2], args[3], args[4]);
				myWorld.save();
				sender.sendMessage(Color.INFO + "Starter generering av ny verden...");
				MultiWorld.loadMyWorld(myWorld);
				MultiWorld.setWorldOptions(myWorld);
				info = "World: " + Color.VARIABLE + myWorld.getName()
						+ Color.INFO + " Environment: " + Color.VARIABLE + myWorld.getEnv().toString()
						+ Color.INFO + " Type: " + Color.VARIABLE + myWorld.getType().toString()
						+ Color.INFO + " Seed: " + Color.VARIABLE + myWorld.getSeed();
			}
		} else if (args.length == 2) {
			FillWorld fillWorld = FillWorld.getFillWorld(myWorld);
			if (args[0].equalsIgnoreCase("fill")) {
				if (fillWorld != null) {
					warning = "Fylling av " + Color.VARIABLE + myWorld.getName() + Color.WARNING + " er allerede startet";
				} else {
					new FillWorld(myWorld).start();
					info = "Fylling av " + Color.VARIABLE + myWorld.getName() + Color.INFO + " er startet, se consolen for info";
				}
			} else if (args[0].equalsIgnoreCase("cancelfill")) {
				if (fillWorld == null) {
					warning = "Det er ikke startet noen fylling av " + Color.VARIABLE + myWorld.getName();
				} else {
					fillWorld.cancelTask();
					info = "Fylling av " + Color.VARIABLE + myWorld.getName() + Color.INFO + " er avbrutt";
				}
			} else if (args[0].equalsIgnoreCase("claimable")) {
				myWorld.setClaimable(!myWorld.getClaimable());
				if (myWorld.getClaimable()) info = "Plasser er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "Plasser er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("firespread")) {
				myWorld.setFireSpread(!myWorld.getFireSpread());
				if (myWorld.getFireSpread()) info = "Ill spredning er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "Ill spredning er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("explosions")) {
				myWorld.setExplosions(!myWorld.getExplosions());
				if (myWorld.getExplosions()) info = "Eksplosjoner er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "Eksplosjoner er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("monstergrief")) {
				myWorld.setMonsterGrief(!myWorld.getMonsterGrief());
				if (myWorld.getMonsterGrief()) info = "Monster grifing er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "Monster grifing er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("trample")) {
				myWorld.setTrample(!myWorld.getTrample());
				if (myWorld.getTrample()) info = "Tråkking er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "Tråkking er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("pvp")) {
				myWorld.setPvp(!myWorld.getPvp());
				if (myWorld.getPvp()) info = "PvP er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "PvP er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("monsters")) {
				myWorld.setMonsters(!myWorld.getMonsters());
				if (myWorld.getMonsters()) info = "Monstre er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "Monstre er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("animals")) {
				myWorld.setAnimals(!myWorld.getAnimals());
				if (myWorld.getAnimals()) info = "Dyr er aktivert for " + Color.VARIABLE + myWorld.getName();
				else info = "Dyr er deaktivert for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("keepspawn")) {
				myWorld.setKeepSpawn(!myWorld.getKeepSpawn());
				if (myWorld.getKeepSpawn()) info = "Beholder spawn i minne for " + Color.VARIABLE + myWorld.getName();
				else info = "Beholder ikke spawn i minne for " + Color.VARIABLE + myWorld.getName();
			} else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender, args[1]);
			} else if (args[0].equalsIgnoreCase("delete")) {
				MultiWorld.unloadWorld(myWorld);
				info = Color.VARIABLE + myWorld.getName() + Color.INFO + " er nå slettet";
			} else {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /world", "", help());
			}
			MultiWorld.setWorldOptions(myWorld);
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("border")) {
				myWorld.setBorder(Util.parseInt(args[2]));
				int size = myWorld.getBorder() * 2;
				info = "Ny størrelse for " + Color.VARIABLE + myWorld.getName() + " er " + Color.VARIABLE + size;
			} else if (args[0].equalsIgnoreCase("lag") || args[0].equalsIgnoreCase("lagg")) {
				chunkLag.sendChunks(sender, args[1], args[2]);
			} else {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /world", "", help());
			}
		} else {
			Util.sendAsPages(sender, args[0], 0, "Hjelp: /world", "", help());
		}
		
		if (warning != "") sender.sendMessage(Color.WARNING + warning);
		else if (info != "") sender.sendMessage(Color.INFO + info);
		return true;
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
		
		list.add(Color.INFO + "Størrelse: " + Color.VARIABLE + size
				+ Color.INFO + " Plasser: " + Color.VARIABLE + claimable);
		list.add(Color.INFO + "Ill spredning: " + Color.VARIABLE + fireSpread
				+ Color.INFO + " Eksplosjoner: " + Color.VARIABLE + explosions);
		list.add(Color.INFO + "Monster grifing: " + Color.VARIABLE + monsterGrief
				+ Color.INFO + " Tråkking: " + Color.VARIABLE + trample);
		list.add(Color.INFO + "Monstre: " + Color.VARIABLE + monsters
				+ Color.INFO + " Dyr: " + Color.VARIABLE + animals);
		list.add(Color.INFO + "PvP: " + Color.VARIABLE + pvp
				+ Color.INFO + " Behold spawn i minne: " + Color.VARIABLE + keepSpawn);
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
		Util.sendAsPages(sender, "1", 10, "Våre verdener", "", list);
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
