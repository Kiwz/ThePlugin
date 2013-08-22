package net.kiwz.ThePlugin.listeners;

import java.util.logging.Logger;

import net.kiwz.ThePlugin.utils.HandlePlayers;
import net.kiwz.ThePlugin.utils.HandleWorlds;
import net.kiwz.ThePlugin.utils.MsgToOthers;
import net.kiwz.ThePlugin.utils.Tablist;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        HandlePlayers players = new HandlePlayers();
		HandleWorlds spawn = new HandleWorlds();
        MsgToOthers msg = new MsgToOthers();
        
        Player player = event.getPlayer();
        String playerName = player.getName();
        
        String ip = player.getAddress().toString();
        String worldName = player.getWorld().getName();
        String coords = player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ();
        String log = "Spiller logget inn";
        String loginMsg = "Noen logget inn";
        
        event.setJoinMessage("");
        new Tablist().setColor(player);
    	
        if (!player.isOp()) {
        	player.setGameMode(GameMode.SURVIVAL);
        	player.setAllowFlight(false);
        }
        
        if (players.hasPlayedBefore(playerName)) {
	        players.setLastLogin(playerName);
	        players.setIP(playerName);
	        log = playerName + " [" + ip + "] logget inn ([" + worldName + "] " + coords + ")";
			loginMsg = ChatColor.GREEN + playerName + " logget inn";
        }
        
        else {
        	players.addPlayer(playerName);
	        log = playerName + " [" + ip + "] logget inn for første gang ([" + worldName + "] " + coords + ")";
			loginMsg = ChatColor.GREEN + playerName + " logget inn for første gang";
			Location loc = spawn.getSpawn(event.getPlayer().getWorld().getName());
			player.teleport(loc);
        }
        
        Logger.getLogger("Minecraft-Server").info(log);
		msg.sendMessage(player, loginMsg);
        player.sendMessage(ChatColor.GOLD + "Velkommen til LarvikGaming");
        player.sendMessage(ChatColor.GOLD + "Besøk vår hjemmeside: http://larvikgaming.net");
	}
}
