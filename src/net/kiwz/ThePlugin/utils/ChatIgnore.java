package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

public class ChatIgnore {
	private static HashMap<String, List<String>> silentMap = new HashMap<String, List<String>>();

	public static void addPlayer(Player player, String name) {
		new ChatIgnore().addSilentPlayer(player, name);
	}
	
	public static boolean remPlayer(Player player, String name) {
		return new ChatIgnore().remSilentPlayer(player, name);
	}
	
	public static List<String> getPlayers(Player player) {
		return new ChatIgnore().getSilentPlayers(player);
	}
	
	private void addSilentPlayer(Player player, String name) {
		Player silentPlayer = MyPlayer.getOnlinePlayer(name);
		if (silentPlayer == null) {
			player.sendMessage(Color.WARNING + name + " er ikke online");
			return;
		}
		
		if (ChatIgnore.silentMap.containsKey(player.getName())) {
			ChatIgnore.silentMap.get(player.getName()).add(silentPlayer.getName());
			player.sendMessage(Color.INFO + "Du vil IKKE se hva " + silentPlayer.getName() + " skriver");
		} else {
			List<String> silentName = new ArrayList<String>();
			ChatIgnore.silentMap.put(player.getName(), silentName);
			ChatIgnore.silentMap.get(player.getName()).add(silentPlayer.getName());
			player.sendMessage(Color.INFO + "Du vil IKKE se hva " + silentPlayer.getName() + " skriver");
		}
	}
	
	private boolean remSilentPlayer(Player player, String name) {
		Player silentPlayer = MyPlayer.getOnlinePlayer(name);
		if (silentPlayer != null) {
			name = silentPlayer.getName();
		}
		
		if (ChatIgnore.silentMap.containsKey(player.getName())) {
			if (ChatIgnore.silentMap.get(player.getName()).remove(name)) {
				player.sendMessage(Color.INFO + "Du kan nå se hva " + name + " skriver");
				return true;
			}
		}
		return false;
	}
	
	private List<String> getSilentPlayers(Player player) {
		if (ChatIgnore.silentMap.containsKey(player.getName())) {
			return ChatIgnore.silentMap.get(player.getName());
		}
		return null;
	}
}
