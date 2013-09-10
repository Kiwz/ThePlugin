package net.kiwz.ThePlugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Tablist {
    private Permissions perm = new Permissions();
	
	/**
	 * 
	 * @param player as Object
	 * 
	 * <p>This will set the tabcolor for given player</p>
	 */
	public void setColor(Player player) {
		String playerName = player.getName();
		
    	if (playerName.length() + 4 > 16) {
    		playerName = playerName.substring(0, 12);
    	}
        if (player.isOp() || perm.isAdmin(player)) {
            player.setPlayerListName(ChatColor.RED + playerName + ChatColor.WHITE);
        }
        else {
        	int roll = (int) (Math.random() * 5);
        	if (roll == 0) player.setPlayerListName(ChatColor.DARK_BLUE + playerName + ChatColor.WHITE);
        	if (roll == 1) player.setPlayerListName(ChatColor.DARK_GREEN + playerName + ChatColor.WHITE);
        	if (roll == 2) player.setPlayerListName(ChatColor.DARK_AQUA + playerName + ChatColor.WHITE);
        	if (roll == 3) player.setPlayerListName(ChatColor.DARK_PURPLE + playerName + ChatColor.WHITE);
        	if (roll == 4) player.setPlayerListName(ChatColor.GOLD + playerName + ChatColor.WHITE);
        	if (roll == 5) player.setPlayerListName(ChatColor.DARK_GRAY + playerName + ChatColor.WHITE);
        }
	}
}
