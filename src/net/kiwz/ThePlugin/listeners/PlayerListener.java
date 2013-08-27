package net.kiwz.ThePlugin.listeners;

import java.util.logging.Logger;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import net.kiwz.ThePlugin.utils.HandlePlayers;
import net.kiwz.ThePlugin.utils.HandleWorlds;
import net.kiwz.ThePlugin.utils.MsgToOthers;
import net.kiwz.ThePlugin.utils.OfflinePlayer;
import net.kiwz.ThePlugin.utils.Tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	private HandlePlayers players = new HandlePlayers();
	private HandleWorlds worlds = new HandleWorlds();
    private MsgToOthers msg = new MsgToOthers();
	private String denyString = ThePlugin.c2 + "Du har ingen tilgang her";
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
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
			loginMsg = ThePlugin.c3 + playerName + " logget inn";
        }
        
        else {
        	players.addPlayer(playerName);
	        log = playerName + " [" + ip + "] logget inn for første gang ([" + worldName + "] " + coords + ")";
			loginMsg = ThePlugin.c3 + playerName + " logget inn for første gang";
			Location loc = worlds.getSpawn(player, player.getWorld().getName());
			player.teleport(loc);
        }
        
        Logger.getLogger("Minecraft-Server").info(log);
		msg.sendMessage(player, loginMsg);
        player.sendMessage(ThePlugin.c1 + "Velkommen til LarvikGaming");
        player.sendMessage(ThePlugin.c1 + "Besøk vår hjemmeside: http://larvikgaming.net");
	}
	
	@EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inv = event.getView().getTopInventory();
		String invType = inv.getType().toString();
		if (invType == "PLAYER") {
			Player holder = (Player) inv.getHolder();
			if (!holder.isOnline()) {
				ItemStack[] content = inv.getHolder().getInventory().getContents();
				OfflinePlayer offlinePlayer = new OfflinePlayer();
				Player player = offlinePlayer.getPlayer(holder.getName());
				player.getInventory().setContents(content);
				player.saveData();
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getClickedBlock().getLocation();
		if (!places.hasAccess(player, loc)) {
			event.setUseInteractedBlock(Result.DENY);;
			player.sendMessage(denyString);
		}
    }
	
	@EventHandler
	public void onPlayerChat (AsyncPlayerChatEvent event) {
		//event.setFormat("%s: %s");
		Player player = event.getPlayer();
		String msg = player.getName() + ": " + event.getMessage();
		
		if (event.getPlayer().isOp()) {
			//event.setFormat(ChatColor.RED + "%s: " + ChatColor.WHITE + "%s");
			msg = ChatColor.RED + player.getName() + ": " + ChatColor.WHITE + event.getMessage();
		}
		for (Player thisPlayer : Bukkit.getServer().getOnlinePlayers()) {
			thisPlayer.sendMessage(msg);
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    }

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setKeepLevel(true);
		event.setDeathMessage("");
		Location loc = event.getEntity().getLocation();
		String world = loc.getWorld().getName();
		String x = Double.toString(loc.getX()).replaceAll("\\..*","");
		String y = Double.toString(loc.getY()).replaceAll("\\..*","");
		String z = Double.toString(loc.getZ()).replaceAll("\\..*","");
		event.getEntity().getPlayer().sendMessage(ThePlugin.c2 + "Du døde i " + world
				+ " x:" + x + " y:" + y + " z:" + z);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
		final Player player = event.getPlayer();
		final Location loc = worlds.getSpawn(player, player.getWorld().getName());
		if (player.getBedSpawnLocation() == null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
					@Override
	                public void run() {
					player.teleport(loc);
				}
			}, 1);
		}
	}
	
	@EventHandler
	public void OnPlayerQuit(PlayerQuitEvent event) {
        players.setTimePlayed(event.getPlayer().getName());
		event.setQuitMessage(ThePlugin.c2 + event.getPlayer().getName() + " logget ut");
		for (Player players : Bukkit.getServer().getOnlinePlayers()) {
			players.getLocation().getWorld().strikeLightningEffect(players.getLocation());
		}
	}
}
