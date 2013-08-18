package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.utils.Access;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

	private String denyString = ChatColor.RED + "Du har ingen tilgang her";
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getClickedBlock().getLocation();
		
		if (!new Access().hasAccess(player, loc)) {
			event.setUseInteractedBlock(Result.DENY);;
			player.sendMessage(denyString);
		}
    }
}
