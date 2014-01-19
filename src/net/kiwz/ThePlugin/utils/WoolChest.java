package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class WoolChest {
	private static HashMap<String, WoolChest> woolChests = new HashMap<String, WoolChest>();
	
	private String uuid;
	private short chest;
	private Inventory inventory;

	
	public WoolChest(MyPlayer myPlayer, short chest) {
		this.uuid = myPlayer.getUUID();
		this.chest = chest;
		this.inventory = createInventory(myPlayer, chest);
		woolChests.put(uuid + chest, this);
	}
	
	public static List<WoolChest> getWoolChests() {
		List<WoolChest> list = new ArrayList<WoolChest>();
		for (String key : woolChests.keySet()) {
			list.add(woolChests.get(key));
		}
		return list;
	}
	
	public static WoolChest getWoolChest(MyPlayer myPlayer, short chest) {
		return woolChests.get(myPlayer.getWoolChestOwner().getUUID() + chest);
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public short getChest() {
		return this.chest;
	}
	
	public void setItem(int index, String material, int amount, short damage, String enchants) {
		ItemStack itemStack = new ItemStack(Material.getMaterial(material), amount, damage);
		if (enchants != "") {
			String[] enchant = enchants.split(" ");
			for (int i = 0; enchant.length > i; i++) {
				Enchantment ench = Enchantment.getByName(enchant[i]);
				i++;
				int level = Integer.parseInt(enchant[i]);
				if (itemStack.getType() == Material.ENCHANTED_BOOK) {
					EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
					meta.addEnchant(ench, level, false);
					meta.setDisplayName(ChatColor.YELLOW + "Enchanted Book");
					itemStack.setItemMeta(meta);
				} else {
					itemStack.addEnchantment(ench, level);
				}
			}
		}
		this.inventory.setItem(index, itemStack);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	private Inventory createInventory(MyPlayer myPlayer, short chest) {
		return Bukkit.getServer().createInventory(null, 54, Color.BLACK + "Ullkiste " + chest + " (" + myPlayer.getName() + ")");
	}
}
