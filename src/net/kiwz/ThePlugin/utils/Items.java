package net.kiwz.ThePlugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
	
	/**
	 * 
	 * @param type as String
	 * @return ItemStack with given item type
	 */
	@SuppressWarnings("deprecation")
	public ItemStack getItem(String type) {
		int id = search(type);
		if (id == 0) {
			return null;
		}
		return new ItemStack(id);
	}
	
	/**
	 * 
	 * @param type as String
	 * @param amount as String
	 * @return ItemStack with given item type and amount
	 */
	@SuppressWarnings("deprecation")
	public ItemStack getItem(String type, String amount) {
		int id = search(type);
		int x = toInt(amount);
		if (id == 0) {
			return null;
		}
		if (x == 0) {
			x = x + 1;
		}
		return new ItemStack(id, x);
	}
	
	/**
	 * 
	 * @param type as String
	 * @param amount as String
	 * @param damage as String
	 * @return ItemStack with given item type, amount and damage
	 */
	@SuppressWarnings("deprecation")
	public ItemStack getItem(String type, String amount, String damage) {
		int id = search(type);
		int x = toInt(amount);
		short dmg = toShort(damage);
		if (id == 0) {
			return null;
		}
		if (Material.getMaterial(id).getMaxDurability() > 0 && Material.getMaterial(id).getMaxDurability() < dmg) {
			return null;
		}
		if (x == 0) {
			x = x + 1;
		}
		return new ItemStack(id, x, dmg);
	}
	
	/**
	 * 
	 * @param type as String to search for an item id
	 * @return item id as int, if not found int = 0
	 */
	@SuppressWarnings("deprecation")
	private int search(String type) {
		int id = 0;
		if (type.length() < 5 && type.matches("[0-9]+")) {
			id = Integer.parseInt(type);
			if (Material.getMaterial(id) == null) {
				id = 0;
			}
			else {
				id = Material.getMaterial(id).getId();
			}
		}
		else {
			for (Material mat : Material.values()) {
				if (mat.toString().startsWith(type.toUpperCase())) {
					id = mat.getId();
					break;
				}
			}
			for (Material mat : Material.values()) {
				if (mat.toString().equals(type.toUpperCase())) {
					id = mat.getId();
					break;
				}
			}
		}
		return id;
	}
	
	/**
	 * 
	 * @param amount as String
	 * @return int if String contained 0-9
	 */
	private int toInt(String amount) {
		int i = 0;
		if (amount.length() < 8 && amount.matches("[0-9]+")) {
			i = Integer.parseInt(amount);
		}
		return i;
	}
	
	/**
	 * 
	 * @param damage as String
	 * @return short if String contained 0-9
	 */
	private short toShort(String damage) {
		short i = 0;
		if (damage.length() < 5 && damage.matches("[0-9]+")) {
			i = Short.parseShort(damage);
		}
		return i;
	}
}
