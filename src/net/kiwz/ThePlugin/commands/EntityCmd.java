package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;

public class EntityCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new EntityCmd().entity(sender, args);
	}
	
	private boolean entity(CommandSender sender, String[] args) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		
		int amount = 0;
		if (args.length == 0) {
			amount = remEntities(mySender, "", "");
		} else if (args.length == 1) {
			amount = remEntities(mySender, args[0], "");
		} else if (args.length == 2) {
			amount = remEntities(mySender, args[0], args[1]);
		}
		sender.sendMessage(Color.INFO + "Du fjernet " + Color.VARIABLE + amount + Color.INFO + " entities");
		return true;
	}
	
	private int remEntities(MyPlayer myPlayer, String type, String distString) {
		int dist = Util.parseInt(distString);
		int amount = 0;
		
		Location loc = server.getWorlds().get(0).getSpawnLocation();
		if (myPlayer != null) {
			loc = myPlayer.getOnlinePlayer().getLocation();
		}
		
		for (Entity e : loc.getWorld().getEntities()) {
			if (dist == 0) {
				if (e instanceof Ageable && type.equals("a")) {
					e.remove();
					amount++;
				} else if (e instanceof Monster && type.equals("m")) {
					e.remove();
					amount++;
				} else if (e instanceof Item && type.equals("i")) {
					e.remove();
					amount++;
				} else if (type.equals("") && (e instanceof Ageable || e instanceof Monster || e instanceof Item)) {
					e.remove();
					amount++;
				}
			} else if (e.getLocation().distance(loc) < dist) {
				if (e instanceof Ageable && type.equals("a")) {
					e.remove();
					amount++;
				} else if (e instanceof Monster && type.equals("m")) {
					e.remove();
					amount++;
				} else if (e instanceof Item && type.equals("i")) {
					e.remove();
					amount++;
				} else if (type.equals("") && (e instanceof Ageable || e instanceof Monster || e instanceof Item)) {
					e.remove();
					amount++;
				}
			}
		}
		return amount;
	}
}
