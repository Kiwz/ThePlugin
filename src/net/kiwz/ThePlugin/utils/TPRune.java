package net.kiwz.ThePlugin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TPRune {
	
	public void teleportRune1(Player player, Block fromBlock, Material toBlock) {
		if (tpTo1(fromBlock, toBlock) != null) {
			Location loc = tpTo1(fromBlock, toBlock);
			loc.setPitch(player.getLocation().getPitch());
			loc.setYaw(player.getLocation().getYaw());
			player.teleport(loc);
			player.sendMessage(Color.INFO + "Du ble på magisk vis teleportet");
		}
	}
	
	public void teleportRune2(Player player, Block fromBlock, Material toBlock) {
		if (tpTo2(fromBlock, toBlock) != null) {
			Location loc = tpTo2(fromBlock, toBlock);
			loc.setPitch(player.getLocation().getPitch());
			loc.setYaw(player.getLocation().getYaw());
			player.teleport(loc);
			player.sendMessage(Color.INFO + "Du ble på magisk vis teleportet");
		}
	}
	
	private Location tpTo1(Block fromBlock, Material toBlock) {
		Location loc = getToBlockLoc(fromBlock, toBlock);
		if (loc == null) {
			return null;
		}
		if (!loc.getBlock().getRelative(0, 2, 0).getType().equals(Material.AIR) &&
				!loc.getBlock().getRelative(0, 3, 0).getType().equals(Material.AIR)) {
			return null;
		}
		else {
			loc = loc.add(0.5, 2, 0.5);
			return loc;
		}
	}
	
	private Location tpTo2(Block fromBlock, Material toBlock) {
		Location loc = getToBlockLoc(fromBlock, toBlock);
		if (loc == null) {
			return null;
		}
		if (!loc.getBlock().getRelative(0, 3, 0).getType().equals(Material.AIR) &&
				!loc.getBlock().getRelative(0, 4, 0).getType().equals(Material.AIR)) {
			return null;
		}
		else {
			loc = loc.add(0.5, 3, 0.5);
			return loc;
		}
	}
	
	private Location getToBlockLoc(Block fromBlock, Material toBlock) {
		if (fromBlock.getRelative(0, 0, 2).getType().equals(toBlock)) {
			return fromBlock.getRelative(0, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(1, 0, 2).getType().equals(toBlock)) {
			return fromBlock.getRelative(1, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, 2).getType().equals(toBlock)) {
			return fromBlock.getRelative(2, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, 1).getType().equals(toBlock)) {
			return fromBlock.getRelative(2, 0, 1).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, 0).getType().equals(toBlock)) {
			return fromBlock.getRelative(2, 0, 0).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, -1).getType().equals(toBlock)) {
			return fromBlock.getRelative(2, 0, -1).getLocation();
		}
		else if (fromBlock.getRelative(2, 0, -2).getType().equals(toBlock)) {
			return fromBlock.getRelative(2, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(1, 0, -2).getType().equals(toBlock)) {
			return fromBlock.getRelative(1, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(0, 0, -2).getType().equals(toBlock)) {
			return fromBlock.getRelative(0, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(-1, 0, -2).getType().equals(toBlock)) {
			return fromBlock.getRelative(-1, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, -2).getType().equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, -2).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, -1).getType().equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, -1).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, 0).getType().equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, 0).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, 1).getType().equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, 1).getLocation();
		}
		else if (fromBlock.getRelative(-2, 0, 2).getType().equals(toBlock)) {
			return fromBlock.getRelative(-2, 0, 2).getLocation();
		}
		else if (fromBlock.getRelative(-1, 0, 2).getType().equals(toBlock)) {
			return fromBlock.getRelative(-1, 0, 2).getLocation();
		}
		else {
			return null;
		}
	}
}
