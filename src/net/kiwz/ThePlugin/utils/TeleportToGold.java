package net.kiwz.ThePlugin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class TeleportToGold {
	
	public Location getToBlockLoc(Block fromBlock, Material toBlock) {
		if (fromBlock.getRelative(0, 0, 2).equals(toBlock)) {
			return fromBlock.getRelative(0, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(1, 0, 2).equals(toBlock)) {
			return fromBlock.getRelative(1, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, 2).equals(toBlock)) {
			return fromBlock.getRelative(2, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, 1).equals(toBlock)) {
			return fromBlock.getRelative(2, 0, 1).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, 0).equals(toBlock)) {
			return fromBlock.getRelative(2, 0, 0).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, -1).equals(toBlock)) {
			return fromBlock.getRelative(2, 0, -1).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, -2).equals(toBlock)) {
			return fromBlock.getRelative(2, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(1, 0, -2).equals(toBlock)) {
			return fromBlock.getRelative(1, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(0, 0, -2).equals(toBlock)) {
			return fromBlock.getRelative(0, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(-1, 0, -2).equals(toBlock)) {
			return fromBlock.getRelative(-1, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, -2).equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, -1).equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, -1).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, 0).equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, 0).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, 1).equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, 1).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, 2).equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(-1, 0, 2).equals(toBlock)) {
			return fromBlock.getRelative(-1, 0, 2).getLocation();
		}
		else {
			return null;
		}
	}
}
