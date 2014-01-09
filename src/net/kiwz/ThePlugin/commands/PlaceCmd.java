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
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		MyPlayer myTarget;
		Place place = null;
		
		for (String arg : args) {
			if (!arg.matches("[a-zA-Z-_0-9æøåÆØÅ]+")) {
				sendWarning(sender, "Tillatte tegn er " + Color.VARIABLE + "a-å A-Å 0-9 - _");
				return true;
			}
		}
		
		if (args.length > 1) {
			if (!args[0].equalsIgnoreCase("liste") && !args[0].equalsIgnoreCase("spiller") && !args[0].equalsIgnoreCase("ny")) {
				place = Place.getPlace(mySender, args[1]);
				if (place == null) {
					sendWarning(sender, Color.PLACE + args[1] + Color.WARNING + " finnes ikke");
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
					sendWarning(sender, Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere");
				} else {
					if (Place.getPlace(mySender.getOnlinePlayer().getLocation()) == null) {
						sendWarning(sender, "Det finnes ingen plass her");
					} else {
						sendPlaceInfo(sender, Place.getPlace(mySender.getOnlinePlayer().getLocation()));
					}
				}
			} else if (args[0].length() == 1) {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /plass", "", help());
			} else {
				if (Place.getPlace(mySender, args[0]) == null) {
					sendWarning(sender, Color.PLACE + args[0] + Color.WARNING + " finnes ikke");
				} else {
					sendPlaceInfo(sender, Place.getPlace(mySender, args[0]));
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
					if (mySender == null) {
						sendWarning(sender, Color.PLAYER + args[1] + Color.WARNING + " er ikke en spiller her");
					} else {
						String ownerOf = MyPlayer.getColorName(myTarget) + " eier følgende plasser: ";
						for (Place p : Place.getPlacesByOwner(myTarget)) {
							ownerOf = ownerOf + "[" + p.getColorName() + "] ";
						}
						String memberOf = MyPlayer.getColorName(myTarget) + " er medlem i følgende plasser: ";
						for (Place p : Place.getPlacesByMember(myTarget)) {
							memberOf = memberOf + "[" + p.getColorName() + "] ";
						}
						sendInfo(sender, ownerOf);
						sendInfo(sender, memberOf);
					}
				}
			} else if (args[0].equalsIgnoreCase("ny")) {
				if (mySender == null) {
					sendWarning(sender, Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere");
				} else {
					Place newPlace = new Place(mySender, args[1], "40");
					newPlace.setChargeAble(true);
					if (newPlace.savePlace(mySender)) {
						sendInfo(sender, "Din nye plass heter " + newPlace.getColorName() + ", for info skriv: " + Color.COMMAND + "/plass " + newPlace.getColorName());
					} else {
						sendWarning(sender, newPlace.getError(mySender));
					}
				}
			} else if (args[0].equalsIgnoreCase("flytt")) {
				if (mySender == null) {
					sendWarning(sender, Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere");
				} else {
					place.setCenter(mySender.getOnlinePlayer().getLocation());
					place.setSpawn(mySender.getOnlinePlayer().getLocation());
					place.setChargeAble(true);
					if (place.savePlace(mySender)) {
						sendInfo(sender, place.getColorName() + " er flyttet hit");
					} else {
						sendWarning(sender, place.getError(mySender));
					}
				}
			} else if (args[0].equalsIgnoreCase("spawn")) {
				if (mySender == null) {
					sendWarning(sender, Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere");
				} else {
					if (place.hasAccess(mySender) || (!place.getPriv() && Util.isSpawnSafe(place.getSpawn()))) {
						mySender.getOnlinePlayer().teleport(place.getSpawn());
					} else {
						sendWarning(sender, place.getColorName() + Color.WARNING + " har ingen spawn");
					}
				}
			} else if (args[0].equalsIgnoreCase("setspawn")) {
				if (mySender == null) {
					sendWarning(sender, Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere");
				} else {
					place.setSpawn(mySender.getOnlinePlayer().getLocation());
					if (place.savePlace(mySender)) {
						sendInfo(sender, place.getColorName() + " sin spawn er flyttet hit");
					} else {
						sendWarning(sender, place.getError(mySender));
					}
				}
			} else if (args[0].equalsIgnoreCase("slett")) {
				if (place.deletePlace(mySender)) {
					sendInfo(sender, place.getColorName() + " er slettet");
				} else {
					sendWarning(sender, place.getColorName() + Color.WARNING + " er ikke din plass");
				}
			} else if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter("Velkommen til " + place.getName());
				if (place.savePlace(mySender)) {
					sendInfo(sender, "Velkomstmelding er satt til standard for " + place.getColorName());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setEnter("Du forlater " + place.getName());
				if (place.savePlace(mySender)) {
					sendInfo(sender, "Forlatemelding er satt til standard for " + place.getColorName());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else if (args[0].equalsIgnoreCase("navn")) {
				sendWarning(sender, "/plass navn <plass-navn> <nytt-plass-navn>");
			} else if (args[0].equalsIgnoreCase("toggle")) {
				sendWarning(sender, "/plass toggle <plass-navn> <[privat] [pvp] [monstre] [dyr]>");
			} else if (args[0].equalsIgnoreCase("medlem")) {
				sendWarning(sender, "/plass medlem <plass-navn> <spiller-navn>");
			} else if (args[0].equalsIgnoreCase("spark")) {
				sendWarning(sender, "/plass spark <plass-navn> <spiller-navn>");
			} else if (args[0].equalsIgnoreCase("eier")) {
				sendWarning(sender, "/plass eier <plass-navn> <spiller-navn>");
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("ny")) {
				if (mySender == null) {
					sendWarning(sender, Color.COMMAND + "/plass " + args[0] + Color.WARNING + " kan bare brukes av spillere");
				} else {
					Place newPlace = new Place(mySender, args[1], args[2]);
					newPlace.setChargeAble(true);
					if (newPlace.savePlace(mySender)) {
						sendInfo(sender, "Din nye plass heter " + newPlace.getColorName() + ", for info skriv: " + Color.COMMAND + "/plass " + newPlace.getColorName());
					} else {
						sendWarning(sender, newPlace.getError(mySender));
					}
				}
			} else if (args[0].equalsIgnoreCase("endre")) {
				place.setRadius(args[2]);
				if (place.savePlace(mySender)) {
					sendInfo(sender, "Du har endret radiusen for " + place.getColorName() + " til " + Color.VARIABLE + place.getRadius());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter(args[2]);
				if (place.savePlace(mySender)) {
					sendInfo(sender, "Ny velkomstmelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getEnter());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setLeave(args[2]);
				if (place.savePlace(mySender)) {
					sendInfo(sender, "Ny forlatemelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getLeave());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else if (args[0].equalsIgnoreCase("navn")) {
				String oldName = place.getName();
				place.setName(args[2]);
				if (place.getEnter().toLowerCase().contains(oldName.toLowerCase()))
					place.setEnter(place.getEnter().replaceAll("(?i)" + oldName, args[2]));
				if (place.getLeave().toLowerCase().contains(oldName.toLowerCase()))
					place.setLeave(place.getLeave().replaceAll("(?i)" + oldName, args[2]));
				if (place.savePlace(mySender)) {
					sendInfo(sender, Color.PLACE + oldName + Color.INFO + " har byttet navn til " + place.getColorName());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else if (args[0].equalsIgnoreCase("toggle")) {
				if (args[2].equalsIgnoreCase("privat")) {
					if (place.getPriv()) {
						place.setPriv(false);
						if (place.savePlace(mySender)) {
							sendInfo(sender, place.getColorName() + " er nå offentlig");
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					} else {
						place.setPriv(true);
						if (place.savePlace(mySender)) {
							sendInfo(sender, place.getColorName() + " er nå privat");
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					}
				} else if (args[2].equalsIgnoreCase("pvp")) {
					if (place.getPvP()) {
						place.setPvP(false);
						if (place.savePlace(mySender)) {
							sendInfo(sender, place.getColorName() + " er ikke i PvP modus");
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					} else {
						place.setPvP(true);
						if (place.savePlace(mySender)) {
							sendInfo(sender, place.getColorName() + " er nå i PvP modus");
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					}
				} else if (args[2].equalsIgnoreCase("monstre")) {
					if (place.getMonsters()) {
						place.setMonsters(false);
						if (place.savePlace(mySender)) {
							sendInfo(sender, "Monstre vil ikke spawne i " + place.getColorName());
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					} else {
						place.setMonsters(true);
						if (place.savePlace(mySender)) {
							sendInfo(sender, "Monstre vil nå spawne i " + place.getColorName());
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					}
				} else if (args[2].equalsIgnoreCase("dyr")) {
					if (place.getAnimals()) {
						place.setAnimals(false);
						if (place.savePlace(mySender)) {
							sendInfo(sender, "Dyr (og villager) vil ikke spawne i " + place.getColorName());
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					} else {
						place.setAnimals(true);
						if (place.savePlace(mySender)) {
							sendInfo(sender, "Dyr (og villager) vil nå spawne i " + place.getColorName());
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					}
				} else {
					sendWarning(sender, "/plass toggle <plass-navn> <[privat] [pvp] [monstre] [dyr]>");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("medlem")) {
				myTarget = MyPlayer.getPlayer(args[2]);
				if (myTarget == null) {
					sendWarning(sender, Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her");
				} else {
					if (place.setMember(myTarget)) {
						if (place.savePlace(mySender)) {
							sendInfo(sender, MyPlayer.getColorName(myTarget) + " er nå medlem av " + place.getColorName());
							if (myTarget.getOnlinePlayer() != null) {
								myTarget.getOnlinePlayer().sendMessage(Color.INFO + "Du er blitt medlem av " + place.getColorName());
							}
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					} else {
						if (place.savePlace(mySender)) {
							sendWarning(sender, MyPlayer.getColorName(myTarget) + Color.WARNING + " er allerede medlem av " + place.getColorName());
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("spark")) {
				myTarget = MyPlayer.getPlayer(args[2]);
				if (myTarget == null) {
					sendWarning(sender, Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her");
				} else {
					if (place.removeMember(myTarget)) {
						if (place.savePlace(mySender)) {
							sendInfo(sender, MyPlayer.getColorName(myTarget) + " er nå sparket ut av " + place.getColorName());
							if (myTarget.getOnlinePlayer() != null) {
								myTarget.getOnlinePlayer().sendMessage(Color.WARNING + "Du er sparket fra " + place.getColorName());
							}
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					} else {
						if (place.savePlace(mySender)) {
							sendWarning(sender, MyPlayer.getColorName(myTarget) + Color.WARNING + " er ikke medlem av " + place.getColorName());
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("eier")) {
				myTarget = MyPlayer.getPlayer(args[2]);
				if (myTarget == null) {
					sendWarning(sender, Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her");
				} else {
					if (place.getOwner().equals(myTarget.getUUID())) {
						sendWarning(sender, MyPlayer.getColorName(myTarget) + Color.WARNING + " er allerede eier av " + place.getColorName());
					} else {
						place.setOwner(myTarget);
						if (place.savePlace(mySender)) {
							sendInfo(sender, MyPlayer.getColorName(myTarget) + " er nå eier av " + place.getColorName());
							sendWarning(sender, "Du er dermed degradert til medlem av " + place.getColorName());
							if (myTarget.getOnlinePlayer() != null) {
								myTarget.getOnlinePlayer().sendMessage(Color.INFO + "Du er blitt eier av " + place.getColorName());
							}
						} else {
							sendWarning(sender, place.getError(mySender));
						}
					}
				}
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
			}
		} else if (args.length > 3 && (args[0].equalsIgnoreCase("entre") || args[0].equalsIgnoreCase("forlate"))) {
			String arg = "";
			for (int i = 2; i < args.length; i++) {
				arg = arg + args[i] + " ";
			}
			arg = arg.trim();
			if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter(arg);
				if (place.savePlace(mySender)) {
					sendInfo(sender, "Ny velkomstmelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getEnter());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setLeave(arg);
				if (place.savePlace(mySender)) {
					sendInfo(sender, "Ny forlatemelding for " + place.getColorName() + " er: " + Color.VARIABLE + place.getLeave());
				} else {
					sendWarning(sender, place.getError(mySender));
				}
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
			}
		} else {
			Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", "", help());
		}
		return true;
	}
	
	private void sendInfo(CommandSender sender, String string) {
		sender.sendMessage(Color.INFO + string);
	}
	
	private void sendWarning(CommandSender sender, String string) {
		sender.sendMessage(Color.WARNING + string);
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
		
		list.add(Color.INFO + "Eier: " + Color.VARIABLE + MyPlayer.getPlayerById(place.getOwner()).getName());
		list.add(Color.INFO + "Medlemmer: " + Color.VARIABLE + members + " ");
		list.add(Color.INFO + "Verden: " + Color.VARIABLE + place.getCenter().getWorld().getName());
		list.add(Color.INFO + "Sentrum: " + Color.VARIABLE + center);
		list.add(Color.INFO + "Størrelse: " + Color.VARIABLE + size + " x " + size);
		list.add(Color.INFO + "Spawn: " + Color.VARIABLE + spawn);
		list.add(Color.INFO + "PvP: " + Color.VARIABLE + pvp);
		list.add(Color.INFO + "Monstre: " + Color.VARIABLE + monsters);
		list.add(Color.INFO + "Dyr: " + Color.VARIABLE + animals);
		list.add(Color.INFO + "Entré melding: " + Color.VARIABLE + place.getEnter());
		list.add(Color.INFO + "Forlate melding: " + Color.VARIABLE + place.getLeave());
		
		String about = "Plass: " + place.getName() + ChatColor.GRAY + " (" + Util.getTimeDate(place.getTime()) + ")";
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
