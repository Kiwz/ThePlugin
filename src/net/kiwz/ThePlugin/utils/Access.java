package net.kiwz.ThePlugin.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Access {
	
	public boolean hasAccess(Player player, Location loc) {
		int x = Integer.parseInt("3");
		int z = Integer.parseInt("3");
		int size = Integer.parseInt("3");
		double locX = loc.getX();
		double locY = loc.getY();
		double locZ = loc.getZ();
		
		if ((x + size) >= locX && (x - size) <= locX && (z + size) >= locZ && (z - size) <= locZ) {
			if (isOwner(player.getName()) || isMember(player.getName()) || player.isOp()) return true;
			else return false;
		}
		else if (locY < 60) return true;
		else if (player.isOp()) return true;
		else return false;
	}
	
	private boolean isMember(String player) {
		String[] members = {"zacker", "Kiwz"};
		boolean isMember = false;
		for (String member : members) {
			if (player.equals(member)) {
				isMember = true;
			}
		}
		return isMember;
	}
	
	private boolean isOwner(String player) {
		String owner = "mereik";
		if (player.equals(owner)) return true;
		else return false;
	}
}
