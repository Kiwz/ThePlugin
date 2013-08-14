package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.utils.OfflinePlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener{
	
	@EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inv = event.getView().getTopInventory();
		String invType = inv.getType().toString();
		if (invType == "PLAYER") {
			Player holder = (Player) inv.getHolder();
			if (!holder.isOnline()) {
				ItemStack[] content = inv.getHolder().getInventory().getContents();
				OfflinePlayer offlinePlayer = new OfflinePlayer();
				Player player = offlinePlayer.getOfflinePlayer(holder.getName());
				player.getInventory().setContents(content);
				player.saveData();
			}
		}
	}
}
