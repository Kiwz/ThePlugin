package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.ChatIgnore;
import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.HandleItems;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.MyWorld;
import net.kiwz.ThePlugin.utils.Perm;
import net.kiwz.ThePlugin.utils.ServerManager;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.help.HelpTopic;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
	private String denyString = Color.WARNING + "Du har ingen tilgang her";
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Material soil = Material.SOIL;
		Material crops = Material.CROPS;
		Material wDoor = Material.WOODEN_DOOR;
		Material boat = Material.BOAT;
		Material cart = Material.MINECART;
		Material sCart = Material.STORAGE_MINECART;
		Material pCart = Material.POWERED_MINECART;
		Material hCart = Material.HOPPER_MINECART;
		Material wPlate = Material.WOOD_PLATE;
		Material sPlate = Material.STONE_PLATE;
		Material iPlate = Material.IRON_PLATE;
		Material gPlate = Material.GOLD_PLATE;
		
		if (!MyWorld.getWorld(block.getWorld()).getTrample() && event.getAction().equals(Action.PHYSICAL)) {
			if ((block.getType().equals(soil)) || (block.getType().equals(crops))) {
				event.setCancelled(true);
				return;
			}
		}
		
		if (event.getClickedBlock() != null) {
			Location clickedBlockLoc = event.getClickedBlock().getLocation();
			Material clickedBlock = event.getClickedBlock().getType();
			Material heldItem = player.getItemInHand().getType();
			Place place = Place.getPlace(clickedBlockLoc);
			
			if (clickedBlock.equals(wDoor)) {
				return;
			}
			
			if (place != null) {
				if (!place.hasAccess(player)) {
					event.setCancelled(true);
					/**
					 * TODO iPlate og gPlate kaller ikke på denne eventen!
					 */
					if (clickedBlock.equals(wPlate) || clickedBlock.equals(sPlate) ||
							clickedBlock.equals(iPlate) || clickedBlock.equals(gPlate)) {
					} else {
						player.updateInventory();
						player.sendMessage(denyString);
					}
				}
			} else if (!player.isOp() && MyWorld.getWorld(clickedBlockLoc.getWorld()).getClaimable() && clickedBlockLoc.getBlockY() > 40) {
				if (!(heldItem.equals(boat) || heldItem.equals(cart) || heldItem.equals(sCart) ||
						heldItem.equals(pCart) || heldItem.equals(hCart))) {
					event.setCancelled(true);
					/// Må fikse iPlate og gPlate
					if (clickedBlock.equals(wPlate) || clickedBlock.equals(sPlate) ||
							clickedBlock.equals(iPlate) || clickedBlock.equals(gPlate)) {
						return;
					}
					player.updateInventory();
					player.sendMessage(denyString);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (event.getRightClicked() != null) {
			Location loc = event.getRightClicked().getLocation();
			Place place = Place.getPlace(loc);
			if (place != null) {
				if (!place.hasAccess(player)) {
					event.setCancelled(true);
					player.sendMessage(denyString);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inv = event.getView().getTopInventory();
		String invType = inv.getType().toString();
		if (invType == "PLAYER") {
			Player holder = (Player) inv.getHolder();
			if (!holder.isOnline()) {
				ItemStack[] content = inv.getHolder().getInventory().getContents();
				Player player = MyPlayer.getPlayer(holder.getName()).getBukkitPlayer();
				player.getInventory().setContents(content);
				player.saveData();
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (!event.isCancelled()) {
			Player player = event.getPlayer();
			String cmd = event.getMessage().split(" ")[0];
	    	HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
	    	if (topic != null) {
	    		ServerManager.logString("[COMMAND] " + player.getName() + ": " + event.getMessage());
	    	}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		//event.setFormat("%s: %s");
		Player player = event.getPlayer();
		String msg = player.getName() + ": " + event.getMessage();
		
		if (MyPlayer.getPlayer(player).isMuted()) {
			player.sendMessage(Color.WARNING + "En admin har bestemt at du ikke får snakke av gode grunner");
			event.setCancelled(true);
			return;
		}
		
		if (player.isOp() || Perm.isAdmin(player)) {
			//event.setFormat(ChatColor.RED + "%s: " + ChatColor.WHITE + "%s");
			msg = ChatColor.RED + player.getName() + ": " + ChatColor.WHITE + event.getMessage();
		}
		
		for (Player thisPlayer : Bukkit.getServer().getOnlinePlayers()) {
			if (ChatIgnore.getPlayers(thisPlayer) != null) {
				if (!ChatIgnore.getPlayers(thisPlayer).contains(player.getName())) {
					thisPlayer.sendMessage(msg);
				}
			} else {
				thisPlayer.sendMessage(msg);
			}
		}
		
		ServerManager.logString("[CHAT] " + player.getName() + ": " + event.getMessage());
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setKeepLevel(true);
		event.setDroppedExp(0);
		event.setDeathMessage("");
		Location loc = event.getEntity().getLocation();
		String world = loc.getWorld().getName();
		String x = Double.toString(loc.getX()).replaceAll("\\..*","");
		String y = Double.toString(loc.getY()).replaceAll("\\..*","");
		String z = Double.toString(loc.getZ()).replaceAll("\\..*","");
		event.getEntity().getPlayer().sendMessage(Color.WARNING + "Du døde i " + world
				+ " X: " + x + " Y: " + y + " Z: " + z);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		final Location loc = MyWorld.getWorld(player.getWorld()).getSpawn();
		if (player.getBedSpawnLocation() == null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(ThePlugin.getPlugin(), new Runnable() {
					@Override
	                public void run() {
					player.teleport(loc);
				}
			}, 1);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        String playerName = player.getName();
        String ip = player.getAddress().getAddress().toString().replace("/", "");
        String worldName = player.getWorld().getName();
        String coords = player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ();
        String log;
        
        Perm.setOpPerm(player);
    	if (Perm.isAdmin(player)) {
    		Perm.setAdminPermissions(player);
    	}
    	
        if (!player.isOp()) {
        	player.setGameMode(GameMode.SURVIVAL);
        	player.setAllowFlight(false);
        }
        
        MyPlayer myPlayer = new MyPlayer(player);
        
        if (!myPlayer.save()) {
        	myPlayer = MyPlayer.getPlayer(player);
        	myPlayer.setName(playerName);
        	myPlayer.setIp(ip);
        	myPlayer.setLastPlayed(System.currentTimeMillis() / 1000);
	        log = playerName + " logget inn (" + worldName + " " + coords + ") [" + ip + "]";
			event.setJoinMessage(Color.HEADER + playerName + " logget inn");
			player.sendMessage(Color.INFO + "############################################");
	        player.sendMessage(Color.INFO + "Velkommen til LarvikGaming");
	        player.sendMessage(Color.INFO + "Hjemmeside:" + Color.HEADER + " http://larvikgaming.net");
	        player.sendMessage(Color.INFO + "Kommandoer:" + Color.HEADER + " http://larvikgaming.net/kommandoer.php");
	        player.sendMessage(Color.INFO + "Kart:" + Color.HEADER + " http://larvikgaming.net/dynmap.php");
	        player.sendMessage(Color.INFO + "Forum:" + Color.HEADER + " http://larvikgaming.net/forum");
	        player.sendMessage(Color.INFO + "Mumble:" + Color.HEADER + " mumble.larvikgaming.net:60000");
			player.sendMessage(Color.INFO + "############################################");
        } else {
	        log = playerName + " logget inn for første gang (" + worldName + " " + coords + ") [" + ip + "]";
			event.setJoinMessage(Color.HEADER + playerName + " logget inn for første gang");
			player.teleport(MyWorld.getWorld(player.getWorld()).getSpawn());
			Bukkit.getScheduler().scheduleSyncDelayedTask(ThePlugin.getPlugin(), new Runnable() {
				public void run() {
					player.teleport(MyWorld.getWorld(player.getWorld()).getSpawn());
				}
			}, 10L);
			HandleItems.giveItem(player, Material.IRON_PICKAXE, 1);
			HandleItems.giveItem(player, Material.IRON_AXE, 1);
			HandleItems.giveItem(player, Material.IRON_PICKAXE, 1);
			HandleItems.giveItem(player, Material.IRON_AXE, 1);
			HandleItems.giveItem(player, Material.COOKED_BEEF, 20);
			HandleItems.giveItem(player, Material.GOLD_INGOT, 5);
			HandleItems.giveItem(player, Material.WOOD, 64);
			HandleItems.giveItem(player, Material.IRON_HELMET, 1);
			HandleItems.giveItem(player, Material.IRON_CHESTPLATE, 1);
			player.sendMessage(Color.INFO + "############################################");
			player.sendMessage(Color.INFO + "Velkommen som ny spiller på LarvikGaming.net");
			player.sendMessage(Color.INFO + "Kjekt om du vil lese Info-Tavlen i spawnen og " + Color.HEADER + "/regler");
			player.sendMessage(Color.INFO + "Skriv " + Color.HEADER + "/hjelp" + Color.INFO +
					" for hjelp, skriv " + Color.HEADER + "/plass" + Color.INFO + " for beskyttelse");
			player.sendMessage(Color.INFO + "Skriv " + Color.HEADER + "/spawn farm" + Color.INFO +" for å skaffe materialer");
			player.sendMessage(Color.INFO + "Skriv " + Color.HEADER + "/spawn world " + Color.INFO + "for å finne ett sted du vil bygge");
			player.sendMessage(Color.INFO + "Skriv " + Color.HEADER + "/plass ny <plass-navn>" + Color.INFO + " for å lage plass");
			player.sendMessage(Color.INFO + "Kostnad for å lage eller flytte plass er 5 gullbarer");
			player.sendMessage(Color.INFO + "Du kan eie 3 plasser og invitere hvem du ønsker til din plass");
			player.sendMessage(Color.INFO + "############################################");
        }
        
        Util.setTabColor(player);
		ServerManager.logString("[CONN] " + log);
        Bukkit.getServer().getLogger().info(log);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void OnPlayerQuit(PlayerQuitEvent event) {
		MyPlayer myPlayer = MyPlayer.getPlayer(event.getPlayer());
		long curr = (System.currentTimeMillis() / 1000);
		long login = myPlayer.getLastPlayed();
		long tot = myPlayer.getTimePlayed();
		myPlayer.setTimePlayed(curr - login + tot);
		event.setQuitMessage(Color.UNSAFE + event.getPlayer().getName() + " logget ut");
		ServerManager.logString("[CONN] " + event.getPlayer().getName() + " logget ut");
		/*for (Player players : Bukkit.getServer().getOnlinePlayers()) {
			players.getLocation().getWorld().strikeLightningEffect(players.getLocation());
		}*/
	}
}
