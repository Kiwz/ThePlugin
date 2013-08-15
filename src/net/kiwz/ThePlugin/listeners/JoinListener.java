package net.kiwz.ThePlugin.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.kiwz.ThePlugin.mysql.MySQLQuery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String pName = player.getName();
        event.setJoinMessage("");
        
    	if (pName.length() + 4 > 16) {
    		pName = pName.substring(0, 12);
    	}
    	
        if (player.isOp()) {
            player.setPlayerListName(ChatColor.DARK_RED + pName + ChatColor.WHITE);
        }
        
        else {
        	player.setGameMode(GameMode.SURVIVAL);
        	player.setAllowFlight(false);
        	int roll = (int) (Math.random() * 5);
        	if (roll == 0) player.setPlayerListName(ChatColor.DARK_BLUE + pName + ChatColor.WHITE);
        	if (roll == 1) player.setPlayerListName(ChatColor.DARK_GREEN + pName + ChatColor.WHITE);
        	if (roll == 2) player.setPlayerListName(ChatColor.DARK_AQUA + pName + ChatColor.WHITE);
        	if (roll == 3) player.setPlayerListName(ChatColor.DARK_PURPLE + pName + ChatColor.WHITE);
        	if (roll == 4) player.setPlayerListName(ChatColor.GOLD + pName + ChatColor.WHITE);
        	if (roll == 5) player.setPlayerListName(ChatColor.DARK_GRAY + pName + ChatColor.WHITE);
        }
        
		MySQLQuery query = new MySQLQuery();
		try {
			ResultSet res = query.fromDB("SELECT * FROM Players WHERE player = '" + pName + "'");
			if (!res.next()) {
				query.toDB("INSERT INTO Players (Player) VALUES ('" + pName + "');");
				for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
					if (!onlinePlayer.getName().equals(pName)) {
						onlinePlayer.sendMessage(ChatColor.GREEN + pName + " logget inn for første gang");
					}
				}
			}
			else {
				for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
					if (!onlinePlayer.getName().equals(pName)) {
						onlinePlayer.sendMessage(ChatColor.GREEN + pName + " logget inn");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        player.sendMessage(ChatColor.GOLD + "Velkommen til LarvikGaming");
        player.sendMessage(ChatColor.GOLD + "Besøk vår hjemmeside: http://larvikgaming.net");
	}
}
