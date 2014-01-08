package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class PlaceCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new PlaceCmd().place(sender, args);
	}
	
	private boolean place(CommandSender sender, String[] args) {
		MyPlayer myPlayer = null;
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		for (String arg : args) {
			if (!arg.matches("[a-zA-Z-_0-9æøåÆØÅ]+")) {
				sendWarning(sender, "Tillatte tegn er " + Color.VARIABLE + "a-å A-Å 0-9 - _");
				return true;
			}
		}
		
		Place place = null;
		if (args.length > 1) {
			if (!args[0].equalsIgnoreCase("liste") && !args[0].equalsIgnoreCase("spiller") && !args[0].equalsIgnoreCase("ny")) {
				place = Place.getPlace(player, args[1]);
				if (place == null) {
					sendWarning(sender, Color.PLACE + args[1] + Color.WARNING + " finnes ikke");
					return true;
				}
				place = Place.getTempPlace(place.getId());
				place.setChargeAble(false);
			}
		}
		
		if (args.length == 0) {
			Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", help());
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("liste")) {
				Util.sendAsPages(sender, "1", 6, Color.PLACE + "Plass-Navn " + Color.INFO + "[" + Color.PLAYER + "Eier" + Color.INFO + "]", Place.getPlaceList());
			} else if (args[0].equalsIgnoreCase("spiller")) {
				Util.sendAsPages(sender, "1", 6, Color.PLAYER + "Eier " + Color.INFO + "[" + Color.PLACE + "Plass-Navn" + Color.INFO + "]", Place.getPlayerList());
			} else if (args[0].equalsIgnoreCase("her")) {
				if (player == null) {
					sendWarning(sender, "Consolen kan ikke bruke " + Color.COMMAND + "/plass " + args[0]);
				} else {
					if (Place.getPlace(player.getLocation()) == null) {
						sendWarning(sender, "Det finnes ingen plass her");
					} else {
						sendPlaceInfo(sender, Place.getPlace(player.getLocation()));
					}
				}
			} else if (args[0].length() == 1) {
				Util.sendAsPages(sender, args[0], 0, "Hjelp: /plass", help());
			} else {
				if (Place.getPlace(player, args[0]) == null) {
					sendWarning(sender, Color.PLACE + args[0] + Color.WARNING + " finnes ikke");
				} else {
					sendPlaceInfo(sender, Place.getPlace(player, args[0]));
				}
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("liste")) {
				Util.sendAsPages(sender, args[1], 6, Color.PLACE + "Plass-Navn " + Color.INFO + "[" + Color.PLAYER + "Eier" + Color.INFO + "]", Place.getPlaceList());
			} else if (args[0].equalsIgnoreCase("spiller")) {
				if (args[1].matches("[0-9]+") && args[1].length() < 3) {
					Util.sendAsPages(sender, args[1], 6, Color.PLAYER + "Eier " + Color.INFO + "[" + Color.PLACE + "Plass-Navn" + Color.INFO + "]", Place.getPlayerList());
				} else {
					myPlayer = MyPlayer.getPlayer(args[1]);
					if (myPlayer == null) {
						sendWarning(sender, Color.PLAYER + args[1] + Color.WARNING + " er ikke en spiller her");
					} else {
						Player target = myPlayer.getBukkitPlayer();
						String ownerOf = Color.PLAYER + target.getName() + Color.INFO + " eier følgende plasser: ";
						for (Place p : Place.getPlacesByOwner(target)) {
							ownerOf = ownerOf + "[" + Color.PLACE + p.getName() + Color.INFO + "] ";
						}
						String memberOf = Color.PLAYER + target.getName() + Color.INFO + " er medlem i følgende plasser: ";
						for (Place p : Place.getPlacesByMember(target)) {
							memberOf = memberOf + "[" + Color.PLACE + p.getName() + Color.INFO + "] ";
						}
						sendInfo(sender, ownerOf);
						sendInfo(sender, memberOf);
					}
				}
			} else if (args[0].equalsIgnoreCase("ny")) {
				if (player == null) {
					sendWarning(sender, "Consolen kan ikke bruke " + Color.COMMAND + "/plass " + args[0]);
				} else {
					Place newPlace = new Place(player, args[1], "40");
					newPlace.setChargeAble(true);
					if (newPlace.savePlace(player)) {
						sendInfo(sender, "Din nye plass heter " + Color.PLACE + newPlace.getName() + Color.INFO + ", for info skriv: " + Color.COMMAND + "/plass " + newPlace.getName());
					} else {
						sendWarning(sender, newPlace.getError(player));
					}
				}
			} else if (args[0].equalsIgnoreCase("flytt")) {
				if (player == null) {
					sendWarning(sender, "Consolen kan ikke bruke " + Color.COMMAND + "/plass " + args[0]);
				} else {
					place.setCenter(player.getLocation());
					place.setSpawn(player.getLocation());
					place.setChargeAble(true);
					if (place.savePlace(player)) {
						sendInfo(sender, Color.PLACE + place.getName() + Color.INFO + " er flyttet hit");
					} else {
						sendWarning(sender, place.getError(player));
					}
				}
			} else if (args[0].equalsIgnoreCase("spawn")) {
				if (player == null) {
					sendWarning(sender, "Consolen kan ikke bruke " + Color.COMMAND + "/plass " + args[0]);
				} else {
					if (place.hasAccess(player) || (!place.getPriv() && Util.isSpawnSafe(place.getSpawn()))) {
						player.teleport(place.getSpawn());
					} else {
						sendWarning(sender, Color.PLACE + place.getName() + Color.WARNING + " har ingen spawn");
					}
				}
			} else if (args[0].equalsIgnoreCase("setspawn")) {
				if (player == null) {
					sendWarning(sender, "Consolen kan ikke bruke " + Color.COMMAND + "/plass " + args[0]);
				} else {
					place.setSpawn(player.getLocation());
					if (place.savePlace(player)) {
						sendInfo(sender, Color.PLACE + place.getName() + Color.INFO + " sin spawn er flyttet hit");
					} else {
						sendWarning(sender, place.getError(player));
					}
				}
			} else if (args[0].equalsIgnoreCase("slett")) {
				if (place.deletePlace(player)) {
					sendInfo(sender, Color.PLACE + place.getName() + Color.INFO + " er slettet");
				} else {
					sendWarning(sender, place.getName() + " er ikke din plass");
				}
			} else if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter("Velkommen til " + place.getName());
				if (place.savePlace(player)) {
					sendInfo(sender, "Velkomstmelding er satt til standard for " + Color.PLACE + place.getName());
				} else {
					sendWarning(sender, place.getError(player));
				}
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setEnter("Du forlater " + place.getName());
				if (place.savePlace(player)) {
					sendInfo(sender, "Forlatemelding er satt til standard for " + Color.PLACE + place.getName());
				} else {
					sendWarning(sender, place.getError(player));
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
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", help());
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("ny")) {
				if (player == null) {
					sendWarning(sender, "Consolen kan ikke bruke " + Color.COMMAND + "/plass " + args[0]);
				} else {
					Place newPlace = new Place(player, args[1], args[2]);
					newPlace.setChargeAble(true);
					if (newPlace.savePlace(player)) {
						sendInfo(sender, "Din nye plass heter " + Color.PLACE + newPlace.getName() + Color.INFO + ", for info skriv: " + Color.COMMAND + "/plass " + Color.PLACE + newPlace.getName());
					} else {
						sendWarning(sender, newPlace.getError(player));
					}
				}
			} else if (args[0].equalsIgnoreCase("endre")) {
				place.setRadius(args[2]);
				if (place.savePlace(player)) {
					sendInfo(sender, "Du har endret radiusen for " + Color.PLACE + place.getName() + Color.INFO + " til " + Color.VARIABLE + place.getRadius());
				} else {
					sendWarning(sender, place.getError(player));
				}
			} else if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter(args[2]);
				if (place.savePlace(player)) {
					sendInfo(sender, "Ny velkomstmelding for " + Color.PLACE + place.getName() + Color.INFO + " er: " + Color.VARIABLE + place.getEnter());
				} else {
					sendWarning(sender, place.getError(player));
				}
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setLeave(args[2]);
				if (place.savePlace(player)) {
					sendInfo(sender, "Ny forlatemelding for " + Color.PLACE + place.getName() + Color.INFO + " er: " + Color.VARIABLE + place.getLeave());
				} else {
					sendWarning(sender, place.getError(player));
				}
			} else if (args[0].equalsIgnoreCase("navn")) {
				String oldName = place.getName();
				place.setName(args[2]);
				if (place.getEnter().toLowerCase().contains(oldName.toLowerCase()))
					place.setEnter(place.getEnter().replaceAll("(?i)" + oldName, args[2]));
				if (place.getLeave().toLowerCase().contains(oldName.toLowerCase()))
					place.setLeave(place.getLeave().replaceAll("(?i)" + oldName, args[2]));
				if (place.savePlace(player)) {
					sendInfo(sender, Color.PLACE + oldName + Color.INFO + " har byttet navn til " + Color.PLACE + place.getName());
				} else {
					sendWarning(sender, place.getError(player));
				}
			} else if (args[0].equalsIgnoreCase("toggle")) {
				if (args[2].equalsIgnoreCase("privat")) {
					if (place.getPriv()) {
						place.setPriv(false);
						if (place.savePlace(player)) {
							sendInfo(sender, Color.PLACE + place.getName() + Color.INFO + " er nå offentlig");
						} else {
							sendWarning(sender, place.getError(player));
						}
					} else {
						place.setPriv(true);
						if (place.savePlace(player)) {
							sendInfo(sender, Color.PLACE + place.getName() + Color.INFO + " er nå privat");
						} else {
							sendWarning(sender, place.getError(player));
						}
					}
				} else if (args[2].equalsIgnoreCase("pvp")) {
					if (place.getPvP()) {
						place.setPvP(false);
						if (place.savePlace(player)) {
							sendInfo(sender, Color.PLACE + place.getName() + Color.INFO + " er ikke i PvP modus");
						} else {
							sendWarning(sender, place.getError(player));
						}
					} else {
						place.setPvP(true);
						if (place.savePlace(player)) {
							sendInfo(sender, Color.PLACE + place.getName() + Color.INFO + " er nå i PvP modus");
						} else {
							sendWarning(sender, place.getError(player));
						}
					}
				} else if (args[2].equalsIgnoreCase("monstre")) {
					if (place.getMonsters()) {
						place.setMonsters(false);
						if (place.savePlace(player)) {
							sendInfo(sender, "Monstre vil ikke spawne i " + Color.PLACE + place.getName());
						} else {
							sendWarning(sender, place.getError(player));
						}
					} else {
						place.setMonsters(true);
						if (place.savePlace(player)) {
							sendInfo(sender, "Monstre vil nå spawne i " + Color.PLACE + place.getName());
						} else {
							sendWarning(sender, place.getError(player));
						}
					}
				} else if (args[2].equalsIgnoreCase("dyr")) {
					if (place.getAnimals()) {
						place.setAnimals(false);
						if (place.savePlace(player)) {
							sendInfo(sender, "Dyr (og villager) vil ikke spawne i " + Color.PLACE + place.getName());
						} else {
							sendWarning(sender, place.getError(player));
						}
					} else {
						place.setAnimals(true);
						if (place.savePlace(player)) {
							sendInfo(sender, "Dyr (og villager) vil nå spawne i " + Color.PLACE + place.getName());
						} else {
							sendWarning(sender, place.getError(player));
						}
					}
				} else {
					sendWarning(sender, "/plass toggle <plass-navn> <[privat] [pvp] [monstre] [dyr]>");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("medlem")) {
				myPlayer = MyPlayer.getPlayer(args[2]);
				if (myPlayer == null) {
					sendWarning(sender, Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her");
				} else {
					Player target = myPlayer.getBukkitPlayer();
					if (place.setMember(target)) {
						if (place.savePlace(player)) {
							sendInfo(sender, Color.PLAYER + target.getName() + Color.INFO + " er nå medlem av " + Color.PLACE + place.getName());
							if (target.isOnline()) {
								target.sendMessage(Color.INFO + "Du er blitt medlem av " + Color.PLACE + place.getName());
							}
						} else {
							sendWarning(sender, place.getError(player));
						}
					} else {
						if (place.savePlace(player)) {
							sendWarning(sender, Color.PLAYER + target.getName() + Color.WARNING + " er allerede medlem av " + Color.PLACE + place.getName());
						} else {
							sendWarning(sender, place.getError(player));
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("spark")) {
				myPlayer = MyPlayer.getPlayer(args[2]);
				if (myPlayer == null) {
					sendWarning(sender, Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her");
				} else {
					Player target = myPlayer.getBukkitPlayer();
					if (place.removeMember(target)) {
						if (place.savePlace(player)) {
							sendInfo(sender, Color.PLAYER + target.getName() + Color.INFO + " er nå sparket ut av " + Color.PLACE + place.getName());
							if (target.isOnline()) {
								target.sendMessage(Color.WARNING + "Du er sparket fra " + Color.PLACE + place.getName());
							}
						} else {
							sendWarning(sender, place.getError(player));
						}
					} else {
						if (place.savePlace(player)) {
							sendWarning(sender, Color.PLAYER + target.getName() + Color.WARNING + " er ikke medlem av " + Color.PLACE + place.getName());
						} else {
							sendWarning(sender, place.getError(player));
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("eier")) {
				myPlayer = MyPlayer.getPlayer(args[2]);
				if (myPlayer == null) {
					sendWarning(sender, Color.PLAYER + args[2] + Color.WARNING + " er ikke en spiller her");
				} else {
					Player target = myPlayer.getBukkitPlayer();
					if (place.getOwner().equals(myPlayer.getUUID())) {
						sendWarning(sender, Color.PLAYER + target.getName() + Color.WARNING + " er allerede eier av " + Color.PLACE + place.getName());
					} else {
						place.setOwner(target);
						if (place.savePlace(player)) {
							sendInfo(sender, Color.PLAYER + target.getName() + Color.INFO + " er nå eier av " + Color.PLACE + place.getName());
							sendWarning(sender, "Du er dermed degradert til medlem av " + Color.PLACE + place.getName());
							if (target.isOnline()) {
								target.sendMessage(Color.INFO + "Du er blitt eier av " + Color.PLACE + place.getName());
							}
						} else {
							sendWarning(sender, place.getError(player));
						}
					}
				}
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", help());
			}
		} else if (args.length > 3 && (args[0].equalsIgnoreCase("entre") || args[0].equalsIgnoreCase("forlate"))) {
			String arg = "";
			for (int i = 2; i < args.length; i++) {
				arg = arg + args[i] + " ";
			}
			arg = arg.trim();
			if (args[0].equalsIgnoreCase("entre")) {
				place.setEnter(arg);
				if (place.savePlace(player)) {
					sendInfo(sender, "Ny velkomstmelding for " + Color.PLACE + place.getName() + Color.INFO + " er: " + Color.VARIABLE + place.getEnter());
				} else {
					sendWarning(sender, place.getError(player));
				}
			} else if (args[0].equalsIgnoreCase("forlate")) {
				place.setLeave(arg);
				if (place.savePlace(player)) {
					sendInfo(sender, "Ny forlatemelding for " + Color.PLACE + place.getName() + Color.INFO + " er: " + Color.VARIABLE + place.getLeave());
				} else {
					sendWarning(sender, place.getError(player));
				}
			} else {
				Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", help());
			}
		} else {
			Util.sendAsPages(sender, "1", 0, "Hjelp: /plass", help());
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
		String members = "";
		for (String member : place.getMembers()) {
			MyPlayer myPlayer = MyPlayer.getPlayerById(member);
			if (myPlayer != null) members = members + myPlayer.getName() + ", ";
		}
		if (members.length() > 1) {
			members = members.substring(0, members.length() - 2);
		}
		String center = "x:" + place.getCenter().getBlockX() + " z:" + place.getCenter().getBlockZ();
		int size = (place.getRadius() * 2) + 1;
		String spawn = "OFFENTLIG";
		if (place.getPriv()) spawn = "PRIVAT";
		String pvp = "NEI";
		if (place.getPvP()) pvp = "JA";
		String monsters = "NEI";
		if (place.getMonsters()) monsters = "JA";
		String animals = "NEI";
		if (place.getAnimals()) animals = "JA";
		
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("----- ");
		header.append(ChatColor.WHITE);
		header.append("Plass: " + place.getName());
		header.append(ChatColor.YELLOW);
		header.append(" --- ");
		header.append(ChatColor.GRAY);
		header.append(Util.getTimeDate(place.getTime()));
		header.append(ChatColor.YELLOW);
		header.append(" -----");
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		sender.sendMessage(Color.INFO + "Eier: " + Color.VARIABLE + MyPlayer.getPlayerById(place.getOwner()).getName());
		sender.sendMessage(Color.INFO + "Medlemmer: " + Color.VARIABLE + members + " ");
		sender.sendMessage(Color.INFO + "Verden: " + Color.VARIABLE + place.getCenter().getWorld().getName());
		sender.sendMessage(Color.INFO + "Sentrum: " + Color.VARIABLE + center);
		sender.sendMessage(Color.INFO + "Størrelse: " + Color.VARIABLE + size + " x " + size);
		sender.sendMessage(Color.INFO + "Spawn: " + Color.VARIABLE + spawn);
		sender.sendMessage(Color.INFO + "PvP: " + Color.VARIABLE + pvp);
		sender.sendMessage(Color.INFO + "Monstre: " + Color.VARIABLE + monsters);
		sender.sendMessage(Color.INFO + "Dyr: " + Color.VARIABLE + animals);
		sender.sendMessage(Color.INFO + "Entré melding: " + Color.VARIABLE + place.getEnter());
		sender.sendMessage(Color.INFO + "Forlate melding: " + Color.VARIABLE + place.getLeave());
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
