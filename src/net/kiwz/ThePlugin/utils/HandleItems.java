package net.kiwz.ThePlugin.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HandleItems {

	public static boolean removeItem(Player player, Material material, int amount) {
		PlayerInventory inventory = player.getInventory();
		if (inventory.containsAtLeast(new ItemStack(material), amount)) {
			inventory.removeItem(new ItemStack(material, amount));
			return true;
		}
		return false;
	}
	
	public static void giveItem(Player player, Material material, int amount) {
		PlayerInventory inventory = player.getInventory();
		ItemStack itemstack = new ItemStack(material, amount);
		inventory.addItem(itemstack);
	}
}
