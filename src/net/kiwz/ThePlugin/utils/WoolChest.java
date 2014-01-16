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
	private short damage;
	private Inventory inventory;

	
	public WoolChest(MyPlayer myPlayer, short damage) {
		this.uuid = myPlayer.getUUID();
		this.damage = damage;
		this.inventory = createInventory(myPlayer, damage);
	}
	
	public WoolChest(String uuid, short damage) {
		this.uuid = uuid;
		this.damage = damage;
		this.inventory = createInventory(MyPlayer.getPlayerById(uuid), damage);
	}
	
	public static WoolChest getWoolChest(MyPlayer myPlayer, short damage) {
		return map.get(myPlayer.getWoolChestOwner().getUUID() + damage);
	}
	
	public static List<WoolChest> getWoolChests() {
		List<WoolChest> list = new ArrayList<WoolChest>();
		for (String key : map.keySet()) {
			list.add(map.get(key));
		}
		return list;
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public short getDamage() {
		return damage;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public String getItems() {
		String items = "";
		for (int i = 0; inventory.getContents().length > i; i++) {
			items += i + " ";
			if (inventory.getItem(i) != null) {
				ItemStack itemStack = inventory.getItem(i);
				items += itemStack.getType().toString() + " ";
				items += itemStack.getAmount() + " ";
				items += itemStack.getDurability() + " ";
				for (Enchantment ench : itemStack.getEnchantments().keySet()) {
					items += ench.getName() + " ";
					items += itemStack.getEnchantments().get(ench) + " ";
				}
			} else {
				items += "0 ";
				items += "0 ";
				items += "0 ";
			}
			items = items.trim();
			items += ":";
		}
		return items;
	}
	
	public void save() {
		map.put(uuid + damage , this);
	}
	
	public void save(String items) {
		ItemStack[] itemStacks = new ItemStack[54];
		for (String slots : items.split(":")) {
			ItemStack itemStack = null;
			String[] slot = slots.split(" ");
			int slotNr = Integer.parseInt(slot[0]);
			Material material = Material.getMaterial(slot[1]);
			int amount = Integer.parseInt(slot[2]);
			short damage = Short.parseShort(slot[3]);
			if (material != null) {
				itemStack = new ItemStack(material, amount, damage);
				for (int i = 4; slot.length > i; i++) {
					Enchantment ench = Enchantment.getByName(slot[i]);
					i++;
					int level = Integer.parseInt(slot[i]);
					itemStack.addEnchantment(ench, level);
				}
			}
			itemStacks[slotNr] = itemStack;
		}
		
		inventory.setContents(itemStacks);
		map.put(uuid + damage , this);
	}
	
	private Inventory createInventory(MyPlayer myPlayer, short damage) {
		return Bukkit.getServer().createInventory(null, 54, Color.INFO + "Ullkiste " + damage + " (" + myPlayer.getName() + ")");
	}
}
