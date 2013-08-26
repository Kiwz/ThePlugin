package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Places;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandlePlaces {
	private ChatColor gold = ChatColor.GOLD;
	private ChatColor red = ChatColor.RED;
	private HandlePlayers hPlayers = new HandlePlayers();
	private HashMap<Integer, Places> places = ThePlugin.getPlaces;
	
	public boolean isOwner(int id, String player) {
		if (getOwner(id).equalsIgnoreCase(player)) {
			return true;
		}
		return false;
	}
	
	public boolean isMember(int id, String player) {
		for (String member : getMembers(id)) {
			if (member.equalsIgnoreCase(player)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAccess(Player player, Location loc) {
		int locX = (int) loc.getX();
		int locY = (int) loc.getY();
		int locZ = (int) loc.getZ();
		if (locX < 0) locX = locX + 1;
		if (locZ < 0) locZ = locZ + 1;
		int id = getIDWithCoords(locX, locZ);
		
		if (id != 0) {
			if (isOwner(id, player.getName()) || isMember(id, player.getName()) || player.isOp()) return true;
			else return false;
		}
		else if (locY < 40) return true;
		else if (player.isOp()) return true;
		else return false;
	}
	
	public int getIDWithCoords(int locX, int locZ) {
		for (int key : places.keySet()) {
			int placeX = places.get(key).x;
			int placeZ = places.get(key).z;
			int placeSize = places.get(key).size;
			if ((placeX + placeSize) >= locX && (placeX - placeSize) <= locX &&
					(placeZ + placeSize) >= locZ && (placeZ - placeSize) <= locZ) {
				return places.get(key).id;
			}
		}
		return 0;
	}
	
	public ArrayList<Integer> getIDsWithOwner(String owner) {
		owner = hPlayers.getPlayerName(owner);
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int key : places.keySet()) {
			if (places.get(key).owner.equals(owner)) {
				ids.add(places.get(key).id);
			}
		}
		return ids;
	}
	
	public ArrayList<Integer> getIDsWithMember(String member) {
		member = hPlayers.getPlayerName(member);
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int key : places.keySet()) {
			for (String thisMember : getMembers(key)) {
				if (thisMember.equals(member)) {
					ids.add(places.get(key).id);
				}
			}
		}
		return ids;
	}
	
	public int getID(String name) {
		int id = 0;
		for (int key : places.keySet()) {
			if (places.get(key).name.equalsIgnoreCase(name)) {
				id = places.get(key).id;
			}
		}
		return id;
	}
	
	public int getTime(int id) {
		return places.get(id).time;
	}
	
	public String getName(int id) {
		return places.get(id).name;
	}
	
	public String getOwner(int id) {
		return places.get(id).owner;
	}
	
	public String getOwned(String owner) {
		if (!hPlayers.hasPlayedBefore(owner)) {
			return red + owner + " er ikke en spiller her";
		}
		owner = hPlayers.getPlayerName(owner);
		String owned = "";
		for (int id : getIDsWithOwner(owner)) {
			owned = owned + "[" + places.get(id).name + "] ";
		}
		if (owned == "") {
			return red + owner + " eier ingen plasser";
		}
		owned = gold + owner + " eier følgende plasser: " + owned;
		return owned;
	}
	
	public String[] getMembers(int id) {
		return places.get(id).members.split(" ");
	}
	
	public String getMembered(String member) {
		if (!hPlayers.hasPlayedBefore(member)) {
			return red + member + " er ikke en spiller her";
		}
		member = hPlayers.getPlayerName(member);
		String membered = "";
		for (int id : getIDsWithMember(member)) {
			membered = membered + "[" + places.get(id).name + "] ";
		}
		if (membered == "") {
			return red + member + " er ikke medlem av noen plasser";
		}
		membered = gold + member + " er medlem i følgende plasser: " + membered;
		return membered;
	}
	
	public String getWorld(int id) {
		return places.get(id).world;
	}
	
	public String getCoords(int id) {
		return "Plassen befinner seg på X:" + places.get(id).x + " Z:" + places.get(id).z;
	}
	
	public String getSize(int id) {
		int size = (places.get(id).size * 2) + 1;
		return "Plassen er " + size + " x " + size + " blokker stor";
	}
	
	public String getSpawnCoords(int id) {
		return places.get(id).spawnCoords;
	}
	
	public String getSpawnPitch(int id) {
		return places.get(id).spawnPitch;
	}
	
	public Location getSpawn(int id) {
		String world = getWorld(id);
		String[] stringCoords = getSpawnCoords(id).split(" ");
		String[] stringPitch = getSpawnPitch(id).split(" ");
		double x = Double.parseDouble(stringCoords[0]);
		double y = Double.parseDouble(stringCoords[1]);
		double z = Double.parseDouble(stringCoords[2]);
		float pitch = Float.parseFloat(stringPitch[0]);
		float yaw = Float.parseFloat(stringPitch[1]);
		Location loc = new Location(Bukkit.getServer().getWorld(world), x, y, z);
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		return loc;
	}
	
	public int getPvP(int id) {
		return places.get(id).pvp;
	}
	
	public int getMonsters(int id) {
		return places.get(id).monsters;
	}
	
	public int getAnimals(int id) {
		return places.get(id).animals;
	}
	
	public void sendPlace(int id, CommandSender sender) {
		String members = "";
		for (String member : getMembers(id)) {
			members = members + member + ", ";
		}
		if (members.length() > 0) {
		members = members.substring(0, members.length()-2);
		}
		sender.sendMessage("Plass navn: " + getName(id));
		sender.sendMessage("Eier: " + getOwner(id));
		sender.sendMessage("Medlemmer: " + members);
		sender.sendMessage("PvP: " + getPvP(id));
		sender.sendMessage("Monstre: " + getMonsters(id));
		sender.sendMessage("Dyr: " + getAnimals(id));
	}
	
	public void sendPlaceHere(CommandSender sender, int locX, int locZ) {
		int id = getIDWithCoords(locX, locZ);
		if (id != 0) {
			String members = "";
			for (String member : getMembers(id)) {
				members = members + member + ", ";
			}
			if (members.length() > 0) {
			members = members.substring(0, members.length()-2);
			}
			sender.sendMessage("Plass navn: " + getName(id));
			sender.sendMessage("Eier: " + getOwner(id));
			sender.sendMessage("Medlemmer: " + members);
			sender.sendMessage("PvP: " + getPvP(id));
			sender.sendMessage("Monstre: " + getMonsters(id));
			sender.sendMessage("Dyr: " + getAnimals(id));
		}
		else sender.sendMessage(red + "Ingen plass funnet");
	}
	
	public void sendPlaceList(CommandSender sender) {
		ArrayList<String> placeList = new ArrayList<String>();
		for (int key : places.keySet()) {
			placeList.add(gold + places.get(key).name + " [" + places.get(key).owner + "] ");
		}
		Collections.sort(placeList, String.CASE_INSENSITIVE_ORDER);
		sender.sendMessage(gold + "Plass-Navn [Eier]");
		for (String place : placeList) {
			sender.sendMessage(place);
		}
	}
	
	public void sendPlayersPlaceList(CommandSender sender) {
		HashMap<String, String> playersPlaceList = new HashMap<String, String>();
		ArrayList<String> playersPlacesList = new ArrayList<String>();
		for (int key : places.keySet()) {
			String place = "";
			if (playersPlaceList.get(places.get(key).owner) != null) {
				place = playersPlaceList.get(places.get(key).owner);
			}
			place = place + "[" + places.get(key).name + "] ";
			String owner = places.get(key).owner;
			playersPlaceList.put(owner, place);
		}
		for (String key : playersPlaceList.keySet()) {
			playersPlacesList.add(key + ": " + playersPlaceList.get(key));
		}
		Collections.sort(playersPlacesList, String.CASE_INSENSITIVE_ORDER);
		sender.sendMessage(gold + "Eier [Plass-navn]");
		for (String playerPlaces : playersPlacesList) {
			sender.sendMessage(gold + playerPlaces);
		}
	}
	
	public String addPlace(String name, Player player, String radius) {
		String playerName = player.getName();
		World world = player.getWorld();
		String worldName = world.getName();
		
		Location loc = player.getLocation();
		int x = (int) loc.getX();
		int z = (int) loc.getZ();
		int size = Integer.parseInt(radius);
		String spawnCoords = Double.toString(loc.getX()) + " " + Double.toString(loc.getY()) + " " + Double.toString(loc.getZ());
		String spawnPitch = Float.toString(loc.getPitch()) + " " + Float.toString(loc.getYaw());
		
		if(getIDsWithOwner(playerName).size() >= 3) {
			//return red + "Du eier 3 plasser og kan ikke lage flere";
		}
		
		if (name.equalsIgnoreCase("liste") || name.equalsIgnoreCase("her") || name.equalsIgnoreCase("spiller")) {
			return red + name + " er reservert og kan ikke brukes";
		}
		
		for (int key : places.keySet()) {
			if (getName(key).equalsIgnoreCase(name)) {
				return red + "Dette navnet finnes fra før";
			}
		}
		
		for (int key : places.keySet()) {
			int otherX = places.get(key).x;
			int otherZ = places.get(key).z;
			int otherSize = places.get(key).size;
			if (x + size >= otherX - otherSize && x - size <= otherX + otherSize &&
					z + size >= otherZ - otherSize && z - size <= otherZ + otherSize) {
				return red + "Du er for nærme en annen plass (" + places.get(key).name + ")";
	    	}
		}
		
		int id = 1;
		while (places.containsKey(id)) {
			id++;
		}
		Places place = new Places();
		place.id = id;
		place.time = (int) (System.currentTimeMillis() / 1000);
		place.name = name;
		place.owner = playerName;
		place.members = "";
		place.world = worldName;
		place.x = x;
		place.z = z;
		place.size = size;
		place.spawnCoords = spawnCoords;
		place.spawnPitch = spawnPitch;
		place.pvp = 0;
		place.monsters = 0;
		place.animals = 1;
		places.put(id, place);
		for (String placeName : ThePlugin.remPlaces.keySet()) {
			if (ThePlugin.remPlaces.get(placeName) == id) {
				ThePlugin.remPlaces.remove(placeName);
			}
		}
		int totSize = (Integer.parseInt(radius) * 2) + 1;
		return gold + "Din nye plass heter \"" + name + "\" og er " + totSize + " x " + size + " blokker stor";
	}
	
	public String setName(Player player, int id, String name) {
		if (isOwner(id, player.getName())) {
			for (int key : places.keySet()) {
				if (getName(key).equalsIgnoreCase(name)) {
					return red + "Dette navnet finnes fra før";
				}
			}
			if (name.equalsIgnoreCase("liste") || name.equalsIgnoreCase("her") || name.equalsIgnoreCase("spiller")) {
				return red + name + " er reservert og kan ikke brukes";
			}
			places.get(id).name = name;
			for (String placeName : ThePlugin.remPlaces.keySet()) {
				if (ThePlugin.remPlaces.get(placeName) == id) {
					ThePlugin.remPlaces.remove(placeName);
				}
			}
			return gold + "Du har byttet navn på plassen din til: " + name;
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String setOwner(Player player, int id, String owner) {
		if (isOwner(id, player.getName())) {
			if (!hPlayers.hasPlayedBefore(owner)) {
				return red + owner + " er ikke en spiller her";
			}
			owner = hPlayers.getPlayerName(owner);
			places.get(id).owner = owner;
			return gold + owner + " er nå den nye eieren av " + getName(id);
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String addMember(Player player, int id, String member) {
		if (isOwner(id, player.getName())) {
			if (!hPlayers.hasPlayedBefore(member)) {
				return red + member + " er ikke en spiller her";
			}
			if (isMember(id, member)) {
				return red + member + " er allerede medlem";
			}
			member = hPlayers.getPlayerName(member);
			places.get(id).members = places.get(id).members + member + " ";
			return gold + member + " er nå medlem av " + getName(id);
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String remMember(Player player, int id, String member) {
		if (isOwner(id, player.getName())) {
			if (!isMember(id, member)) {
				return red + member + " er ikke ett medlem";
			}
			member = hPlayers.getPlayerName(member);
			places.get(id).members = places.get(id).members.replaceAll(member + " ", "");
			return gold + member + " er nå fjernet som medlem av " + getName(id);
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String setSpawn(Player player, int id) {
		if (isOwner(id, player.getName())) {
			Location loc = player.getLocation();

			int locX = (int) loc.getX();
			int locZ = (int) loc.getZ();
			int placeX = places.get(id).x;
			int placeZ = places.get(id).z;
			int placeSize = places.get(id).size;
			if ((placeX + placeSize) >= locX && (placeX - placeSize) <= locX &&
					(placeZ + placeSize) >= locZ && (placeZ - placeSize) <= locZ) {
				String spawnCoords = Double.toString(loc.getX()) + " " + Double.toString(loc.getY()) + " " + Double.toString(loc.getZ());
				String spawnPitch = Float.toString(loc.getPitch()) + " " + Float.toString(loc.getYaw());
				places.get(id).spawnCoords = spawnCoords;
				places.get(id).spawnPitch = spawnPitch;
				return gold + "Du har satt ny spawn i denne plassen";
			}
			else {
				return red + "Du må stå i " + places.get(id).name;
			}
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String setPvP(Player player, int id) {
		if (isOwner(id, player.getName())) {
			String returnString;
			if (getPvP(id) == 0) {
				places.get(id).pvp = 1;
				returnString = gold + "PvP er AKTIVERT";
			}
			else {
				places.get(id).pvp = 0;
				returnString = gold + "PvP er DEAKTIVERT";
			}
			return returnString;
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String setMonsters(Player player, int id) {
		if (isOwner(id, player.getName())) {
			String returnString;
			if (getMonsters(id) == 0) {
				places.get(id).monsters = 1;
				returnString = gold + "Monstre er AKTIVERT";
			}
			else {
				places.get(id).monsters = 0;
				returnString = gold + "Monstre er DEAKTIVERT";
			}
			return returnString;
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String setAnimals(Player player, int id) {
		if (isOwner(id, player.getName())) {
			String returnString;
			if (getPvP(id) == 0) {
				places.get(id).animals = 1;
				returnString = gold + "Dyr er AKTIVERT";
			}
			else {
				places.get(id).animals = 0;
				returnString = gold + "Dyr er DEAKTIVERT";
			}
			return returnString;
		}
		else {
			return red + getName(id) + " er ikke din plass";
		}
	}
	
	public String remPlace(int id, Player player) {
		if (isOwner(id, player.getName())) {
			String placeName = places.get(id).name;
			places.remove(id);
			ThePlugin.remPlaces.put(placeName, id);
			return gold + placeName + " er slettet";
		}
		return red + places.get(id).name + " er ikke din plass";
	}
}
