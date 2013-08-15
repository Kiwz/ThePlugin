package net.kiwz.ThePlugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setKeepLevel(true);
		event.setDeathMessage("");
		String world = event.getEntity().getLocation().getWorld().getName();
		String x = Double.toString(event.getEntity().getLocation().getX()).replaceAll("\\..*","");
		String y = Double.toString(event.getEntity().getLocation().getY()).replaceAll("\\..*","");
		String z = Double.toString(event.getEntity().getLocation().getZ()).replaceAll("\\..*","");
		event.getEntity().getPlayer().sendMessage(ChatColor.RED + "Du døde i " + world
				+ " x:" + x + " y:" + y + " z:" + z);
	}
}
