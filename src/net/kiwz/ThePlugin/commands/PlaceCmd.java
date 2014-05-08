package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PlaceCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new PlaceCmd().place(sender, args);
	}
	
	private boolean place(CommandSender sender, String[] args) {
		String info = "";
		String warning = "";
		String target = "";
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		MyPlayer myTarget = null;
		Place place = null;
		
		for (String arg : args) {
			if (!arg.matches("[a-zA-Z-_0-9æøåÆØÅ]+")) {
				sender.sendMessage(Color.WARNING + "Tillatte tegn er " + Color.VARIABLE + "a-å A-Å 0-9 - _");
				return true;
			}
		}
		
		if (args.length > 1) {
			if (!args[0].equalsIgnoreCase("liste") && !args[0].equalsIgnoreCase("spiller") && !args[0].equalsIgnoreCase("ny")) {
				place = Place.getPlace(mySender, args[1]);
				if (place == null) {
					sender.sendMessage(Color.PLACE + args[1] + Color.WARNING + " finnes ikke");
					return true;
				}
				place = Place.getTempPlace(place.getId());
				place.setChargeAble(false);
			}
		}
		
		if (args.length == 0) {
			Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("liste")) {
				Util.sendAsPages(sender, "1", 6, Color.PLACE + "Plass-Navn " + Color.INFO + "[" + Color.PLAYER + "Eier" + Color.INFO + "]", "", Place.getPlaceList());
			} else if (args[0].equalsIgnoreCase("spiller")) {
				Util.sendAsPages(sender, "1", 6, Color.PLAYER + "Eier " + Color.INFO + "[" + Color.PLACE + "Plass-Navn" + Color.INFO + "]", "", Place.getPlayerList());
			} else if (args[0].equalsIgnoreCase("her")) {
				if (mySender == null) {
					warning = Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere";
				} else {
					place = Place.getPlace(mySender.getOnlinePlayer().getLocation());
					if (place == null) {
						warning = "Det finnes ingen plass her";
					} else {
						sendPlaceInfo(sender, place);
					}
				}
			} else if (args[0].length() == 1) {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /plass", "", help());
			} else {
				place = Place.getPlace(mySender, args[0]);
				if (place == null) {
					warning = Color.PLACE + args[0] + Color.WARNING + " finnes ikke";
				} else {
					sendPlaceInfo(sender, place);
				}
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("liste")) {
				Util.sendAsPages(sender, args[1], 6, Color.PLACE + "Plass-Navn " + Color.INFO + "[" + Color.PLAYER + "Eier" + Color.INFO + "]", "", Place.getPlaceList());
			} else if (args[0].equalsIgnoreCase("spiller")) {
				if (args[1].matches("[0-9]+") && args[1].length() < 3) {
					Util.sendAsPages(sender, args[1], 6, Color.PLAYER + "Eier " + Color.INFO + "[" + Color.PLACE + "Plass-Navn" + Color.INFO + "]", "", Place.getPlayerList());
				} else {
					myTarget = MyPlayer.getPlayer(args[1]);
					if (myTarget == null) {
						warning = Color.PLAYER + args[1] + Color.WARNING + " er ikke en spiller her";
					} else {
						String ownerOf = Color.INFO + "Eier: ";
						for (Place p : Place.getPlacesByOwner(myTarget)) {
							ownerOf = ownerOf + "[" + p.getColorName() + "] ";
						}
						String memberOf = Color.INFO + "Medlem av: ";
						for (Place p : Place.getPlacesByMember(myTarget)) {
							memberOf = memberOf + "[" + p.getColorName() + "] ";
						}
						List<String> list = new ArrayList<String>();
						list.add(ownerOf);
						list.add(memberOf);
						Util.sendAsPages(sender, "1", 20, MyPlayer.getColorName(myTarget), "", list);
					}
				}
			} else if (args[0].equalsIgnoreCase("ny")) {
				if (mySender == null) {
					warning = Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere";
				} else {
					place = new Place(mySender, args[1], "40");
					place.setChargeAble(true);
					info = "Din nye plass heter " + place.getColorName() + ", for info skriv: " + Color.COMMAND + "/plass " + place.getColorName();
				}
			} else if (args[0].equalsIgnoreCase("flytt")) {
				if (mySender == null) {
					warning = Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere";
				} else {
					place.setCenter(mySender.getOnlinePlayer().getLocation());
					place.setSpawn(mySender.getOnlinePlayer().getLocation());
					place.setChargeAble(true);
					info = place.getColorName() + " er flyttet hit";
				}
			} else if (args[0].equalsIgnoreCase("spawn")) {
				if (mySender == null) {
					warning = Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere";
				} else if (mySender.isDamaged()) {
					warning = "Du kan ikke teleportere rett etter du har tatt skade";
				} else {
					if (place.hasAccess(mySender) || (!place.getPriv() && place.isSpawnSafe())) {
						mySender.getOnlinePlayer().teleport(place.getSpawn());
					} else {
						warning = "Plassen du ønsker å besøke kan være farlig!\n" + Color.INFO
								+ "Teleportering til " + place.getColorName() + " er avbrutt";
					}
				}
			} else if (args[0].equalsIgnoreCase("setspawn")) {
				if (mySender == null) {
					warning = Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere";
				} else {
					place.setSpawn(mySender.getOnlinePlayer().getLocation());
					info = place.getColorName() + " sin spawn er flyttet hit";
				}
			} else if (args[0].equalsIgnoreCase("slett")) {
				place.setRemoved(mySender, true);
				info = place.getColorName() + " er slettet";
			} else if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter("Velkommen til " + place.getName());
				info = "Velkomstmelding er satt til standard for " + place.getColorName();
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setLeave("Du forlater " + place.getName());
				info = "Forlatemelding er satt til standard for " + place.getColorName();
			} else if (args[0].equalsIgnoreCase("navn")) {
				warning = "/plass navn <plass-navn> <nytt-plass-navn>";
			} else if (args[0].equalsIgnoreCase("endre")) {
				warning = "/plass endre <plass-navn> <radius>";
			} else if (args[0].equalsIgnoreCase("toggle")) {
				warning = "/plass toggle <plass-navn> <[privat] [pvp] [monstre] [dyr]>";
			} else if (args[0].equalsIgnoreCase("medlem")) {
				warning = "/plass medlem <plass-navn> <spiller-navn>";
			} else if (args[0].equalsIgnoreCase("spark")) {
				warning = "/plass spark <plass-navn> <spiller-navn>";
			} else if (args[0].equalsIgnoreCase("eier")) {
				warning = "/plass eier <plass-navn> <spiller-navn>";
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
			}
		} else if (args.length == 3) {
			myTarget = MyPlayer.getPlayer(args[2]);
			if (args[0].equalsIgnoreCase("ny")) {
				if (mySender == null) {
					warning = Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere";
				} else {
					place = new Place(mySender, args[1], args[2]);
					place.setChargeAble(true);
					info = "Din nye plass heter " + place.getColorName() + ", for info skriv: " + Color.COMMAND + "/plass " + place.getColorName();
				}
			} else if (args[0].equalsIgnoreCase("endre")) {
				place.setRadius(args[2]);
				info = "Du har endret radiusen for " + place.getColorName() + " til " + Color.VARIABLE + place.getRadius();
			} else if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter(args[2]);
				info = "Ny velkomstmelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getEnter();
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setLeave(args[2]);
				info = "Ny forlatemelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getLeave();
			} else if (args[0].equalsIgnoreCase("navn")) {
				String oldName = place.getName();
				place.setName(args[2]);
				if (place.getEnter().toLowerCase().contains(oldName.toLowerCase()))
					place.setEnter(place.getEnter().replaceAll("(?i)" + oldName, args[2]));
				if (place.getLeave().toLowerCase().contains(oldName.toLowerCase()))
					place.setLeave(place.getLeave().replaceAll("(?i)" + oldName, args[2]));
				info = Color.PLACE + oldName + Color.INFO + " har byttet navn til " + place.getColorName();
			} else if (args[0].equalsIgnoreCase("toggle")) {
				if (args[2].equalsIgnoreCase("privat")) {
					place.setPriv(!place.getPriv());
					if (place.getPriv()) info = place.getColorName() + " er nå privat";
					else info = place.getColorName() + " er nå offentlig";
				} else if (args[2].equalsIgnoreCase("pvp")) {
					place.setPvP(!place.getPvP());
					if (place.getPvPTaskId() != 0) {
						info = place.getColorName() + " blir ikke satt i PvP modus";
					} else if (!place.getPvP()) {
						info = place.getColorName() + " er ikke i PvP modus";
					} else {
						info = place.getColorName() + " blir satt i PvP modus om ca 2 min";
					}
				} else if (args[2].equalsIgnoreCase("monstre")) {
					place.setMonsters(!place.getMonsters());
					if (place.getMonsters()) info = "Monstre vil nå spawne i " + place.getColorName();
					else info = "Monstre vil ikke spawne i " + place.getColorName();
				} else if (args[2].equalsIgnoreCase("dyr")) {
					place.setAnimals(!place.getAnimals());
					if (place.getAnimals()) info = "Dyr (og villager) vil nå spawne i " + place.getColorName();
					else info = "Dyr (og villager) vil ikke spawne i " + place.getColorName();
				} else {
					warning = "/plass toggle <plass-navn> <[privat] [pvp] [monstre] [dyr]>";
				}
			} else if (args[0].equalsIgnoreCase("medlem")) {
				if (myTarget == null) {
					warning = Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her";
				} else {
					if (place.setMember(myTarget)) {
						info = MyPlayer.getColorName(myTarget) + " er nå medlem av " + place.getColorName();
						target = Color.INFO + "Du er blitt medlem av " + place.getColorName();
					} else {
						info = MyPlayer.getColorName(myTarget) + Color.INFO + " er allerede medlem av " + place.getColorName();
					}
				}
			} else if (args[0].equalsIgnoreCase("spark")) {
				if (myTarget == null) {
					warning = Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her";
				} else {
					if (place.removeMember(myTarget)) {
						info = MyPlayer.getColorName(myTarget) + " er nå sparket ut av " + place.getColorName();
						target = Color.WARNING + "Du er sparket fra " + place.getColorName();
					} else {
						info = MyPlayer.getColorName(myTarget) + Color.INFO + " er ikke medlem av " + place.getColorName();
					}
				}
			} else if (args[0].equalsIgnoreCase("eier")) {
				if (myTarget == null) {
					warning = Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her";
				} else {
					if (place.getOwner().equals(myTarget.getUUID())) {
						info = MyPlayer.getColorName(myTarget) + Color.INFO + " er allerede eier av " + place.getColorName();
					} else {
						place.setOwner(myTarget);
						info = MyPlayer.getColorName(myTarget) + " er nå eier av " + place.getColorName();
						target = Color.INFO + "Du er blitt eier av " + place.getColorName();
					}
				}
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
			}
		} else if (args.length > 3) {
			String arg = "";
			for (int i = 2; i < args.length; i++) {
				arg = arg + args[i] + " ";
			}
			arg = arg.trim();
			
			if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter(arg);
				info = "Ny velkomstmelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getEnter();
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setLeave(arg);
				info = "Ny forlatemelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getLeave();
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
			}
		} else {
			Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
		}

		if (info != "") {
			if (place.save(mySender)) {
				sender.sendMessage(Color.INFO + info);
				if (target != "" && myTarget.getOnlinePlayer() != null) myTarget.getOnlinePlayer().sendMessage(target);
			} else {
				sender.sendMessage(Color.WARNING + place.getError(mySender));
			}
		} else if (warning != "") {
			sender.sendMessage(Color.WARNING + warning);
		}
		return true;
	}
	
	private void sendPlaceInfo(CommandSender sender, Place place) {
		List<String> list = new ArrayList<String>();
		String members = "";
		for (String member : place.getMembers()) {
			MyPlayer myPlayer = MyPlayer.getPlayerById(member);
			if (myPlayer != null) members = members + MyPlayer.getColorName(myPlayer) + ", ";
		}
		if (members.length() > 1) {
			members = members.substring(0, members.length() - 2);
		}
		String center = Color.INFO + "X: " + Color.VARIABLE + place.getCenter().getBlockX() + Color.INFO
				+ " Z: " + Color.VARIABLE + place.getCenter().getBlockZ();
		int size = (place.getRadius() * 2) + 1;
		String spawn = "OFFENTLIG";
		if (place.getPriv()) spawn = "PRIVAT";
		String pvp = "NEI";
		if (place.getPvP()) pvp = "JA";
		String monsters = "NEI";
		if (place.getMonsters()) monsters = "JA";
		String animals = "NEI";
		if (place.getAnimals()) animals = "JA";
		
		list.add(Color.INFO + "Eier: " + Color.VARIABLE + MyPlayer.getColorName(MyPlayer.getPlayerById(place.getOwner())));
		list.add(Color.INFO + "Medlemmer: " + Color.VARIABLE + members + " ");
		list.add(Color.INFO + "Verden: " + Color.VARIABLE + place.getCenter().getWorld().getName() + Color.INFO
				+ " Sentrum: " + Color.VARIABLE + center + Color.INFO
				+ " Størrelse: " + Color.VARIABLE + size + " x " + size);
		list.add(Color.INFO + "Spawn: " + Color.VARIABLE + spawn
				+ Color.INFO + " PvP: " + Color.VARIABLE + pvp
				+ Color.INFO + " Monstre: " + Color.VARIABLE + monsters
				+ Color.INFO + " Dyr: " + Color.VARIABLE + animals);
		list.add(Color.INFO + "Entrè melding: " + Color.VARIABLE + place.getEnter());
		list.add(Color.INFO + "Forlate melding: " + Color.VARIABLE + place.getLeave());
		
		String about = Color.INFO + "[" + place.getColorName() + "]" + ChatColor.GRAY + " (" + Util.getTimeDate(place.getTime()) + ")";
		Util.sendAsPages(sender, "1", 10, about, "", list);
	}
	
	private List<String> help() {
		ChatColor white = ChatColor.WHITE;
		ChatColor gold = ChatColor.GOLD;
		List<String> help = new ArrayList<String>();
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
