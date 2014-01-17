package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WoolChest {
	private static HashMap<String, WoolChest> map = new HashMap<String, WoolChest>();
	
	private String uuid;
	private short chest;
	private Inventory inventory;

	
	public WoolChest(MyPlayer myPlayer, short chest) {
		this.uuid = myPlayer.getUUID();
		this.chest = chest;
		this.inventory = createInventory(myPlayer, chest);
		map.put(uuid + chest, this);
	}
	
	public static List<WoolChest> getWoolChests() {
		List<WoolChest> list = new ArrayList<WoolChest>();
		for (String key : map.keySet()) {
			list.add(map.get(key));
		}
		return list;
	}
	
	public static WoolChest getWoolChest(MyPlayer myPlayer, short chest) {
		return map.get(myPlayer.getWoolChestOwner().getUUID() + chest);
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public short getChest() {
		return chest;
	}
	
	public void setInventory(int index, String material, int amount, short damage, String enchants) {
		ItemStack item = new ItemStack(Material.getMaterial(material), amount, damage);
		if (enchants != "") {
			String[] enchant = enchants.split(" ");
			for (int i = 0; enchant.length > i; i++) {
				Enchantment ench = Enchantment.getByName(enchant[i]);
				i++;
				int level = Integer.parseInt(enchant[i]);
				item.addEnchantment(ench, level);
			}
		}
		inventory.setItem(index, item);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	private Inventory createInventory(MyPlayer myPlayer, short chest) {
		return Bukkit.getServer().createInventory(null, 54, Color.BLACK + "Ullkiste " + chest + " (" + myPlayer.getName() + ")");
	}
}
