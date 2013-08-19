package net.kiwz.ThePlugin.utils;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Access {
	
	public boolean hasAccess(Player player, Location loc) {
		String playerName = player.getName();
		int locX = (int) loc.getX();
		int locY = (int) loc.getY();
		int locZ = (int) loc.getZ();
		int place = getPlaceWithCoords(locX, locZ);
		
		if (place != 0) {
			String owner = ThePlugin.getPlaces.get(place).owner;
			String members = ThePlugin.getPlaces.get(place).members;
			
			if (playerName.equals(owner) || isMember(playerName, members) || player.isOp()) return true;
			else return false;
		}
		else if (locY < 40) return true;
		else if (player.isOp()) return true;
		else return false;
	}
	
	private int getPlaceWithCoords(int locX, int locZ) {
		for (int key : ThePlugin.getPlaces.keySet()) {
			int placeX = ThePlugin.getPlaces.get(key).x;
			int placeZ = ThePlugin.getPlaces.get(key).z;
			int placeSize = ThePlugin.getPlaces.get(key).size;
			if ((placeX + placeSize) >= locX && (placeX - placeSize) <= locX && (placeZ + placeSize) >= locZ && (placeZ - placeSize) <= locZ) {
				return ThePlugin.getPlaces.get(key).id;
			}
		}
		return 0;
	}
	
	private boolean isMember(String player, String membersString) {
		boolean isMember = false;
		if (membersString == null) return isMember;
		String[] members = membersString.split(" ");
		for (String member : members) {
			if (player.equals(member)) {
				isMember = true;
			}
		}
		return isMember;
	}
}
