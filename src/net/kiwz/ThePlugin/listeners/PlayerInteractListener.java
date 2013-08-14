package net.kiwz.ThePlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		ChatColor red = ChatColor.RED;
		Player player = Bukkit.getServer().getPlayer(event.getPlayer().getName());
		double locX = event.getClickedBlock().getLocation().getX();
		double locZ = event.getClickedBlock().getLocation().getZ();
		int except = event.getClickedBlock().getTypeId();
		Action action = event.getAction();
		boolean deny = true;
		if (except == 64 && action.toString().equalsIgnoreCase("RIGHT_CLICK_BLOCK") || player.isOp()) {
			deny = false;
		}
		if (locX >= 198 && locX <= 200 && locZ >= 198 && locZ <= 200 && deny) {
			event.setUseInteractedBlock(Result.DENY);
			player.sendMessage(red + "Du har ingen tilgang her");
		}
    }
}
