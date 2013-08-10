package net.kiwz.ThePlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener{
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String pName = p.getName();
        p.sendMessage(ChatColor.GOLD + "Velkommen til LarvikGaming");
        p.sendMessage(ChatColor.GOLD + "Besøk vår hjemmeside: http://larvikgaming.net");
        
    	if (pName.length() + 4 > 16) {
    		pName = pName.substring(0, 12);
    	}
    	
        if (p.isOp()) {
            p.setPlayerListName(ChatColor.DARK_RED + pName + ChatColor.WHITE);
        }
        
        else {
        	int roll = (int) (Math.random() * 5);

        	if (roll == 0) p.setPlayerListName(ChatColor.DARK_BLUE + pName + ChatColor.WHITE);
        	if (roll == 1) p.setPlayerListName(ChatColor.DARK_GREEN + pName + ChatColor.WHITE);
        	if (roll == 2) p.setPlayerListName(ChatColor.DARK_AQUA + pName + ChatColor.WHITE);
        	if (roll == 3) p.setPlayerListName(ChatColor.DARK_PURPLE + pName + ChatColor.WHITE);
        	if (roll == 4) p.setPlayerListName(ChatColor.GOLD + pName + ChatColor.WHITE);
        	if (roll == 5) p.setPlayerListName(ChatColor.DARK_GRAY + pName + ChatColor.WHITE);
        	
        }
	}
}
