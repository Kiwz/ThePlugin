package net.kiwz.ThePlugin.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class SpawnSafe {
	
	public boolean isSpawnSafe(Location loc) {
		Block block = loc.getBlock();
		Material a = block.getRelative(0, 1, 0).getType();
		Material b = block.getRelative(0, 0, 0).getType();
		Material c = block.getRelative(0, -1, 0).getType();
		Material d = block.getRelative(0, -2, 0).getType();
		Material air = Material.AIR;
		
		if (!a.equals(air) || !b.equals(air) || (c.equals(air) && d.equals(air))) return false;
		
		for (String checkBlock : ThePlugin.blocks) {
			Material mat = Material.getMaterial(checkBlock);
			if (c.equals(mat)) return false;
			if (c.equals(air) && d.equals(mat)) return false;
		}
		
		World world = loc.getWorld();
		int locMaxX = loc.getBlockX() + 2;
		int locMaxY = loc.getBlockY() + 3;
		int locMaxZ = loc.getBlockZ() + 2;
		int locX = loc.getBlockX() - 2;
		while (locMaxX >= locX) {
			int locY = loc.getBlockY() - 2;
			while (locMaxY >= locY) {
				int locZ = loc.getBlockZ() - 2;
				while (locMaxZ >= locZ) {
					Material mat = world.getBlockAt(locX, locY, locZ).getType();
					if (mat.equals(Material.CACTUS)) return false;
					if (mat.equals(Material.PISTON_BASE)) return false;
					if (mat.equals(Material.PISTON_EXTENSION)) return false;
					if (mat.equals(Material.PISTON_MOVING_PIECE)) return false;
					if (mat.equals(Material.PISTON_STICKY_BASE)) return false;
					locZ++;
				}
				locY++;
			}
			locX++;
		}
		return true;
	}
	
	public ArrayList<String> getBlocks() {
		ArrayList<String> blocks = new ArrayList<String>();
		try {
			File file = new File("plugins\\ThePlugin\\blocks.txt");
			if (file.createNewFile()) {
				PrintWriter writer = new PrintWriter("plugins\\ThePlugin\\blocks.txt");
				writer.println("One block ENUM on each line! (Remove this line!!!) Example:");
				writer.println("FIRE");
				writer.close();
			}
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				blocks.add(sc.nextLine());
			}
			sc.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return blocks;
	}
}
