package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.entity.Player;

public class ChatIgnore {
	private HashMap<String, List<String>> silentMap = ThePlugin.chatIgnore;
	
	public void addSilentPlayer(Player player, String name) {
		Player silentPlayer = new OnlinePlayer().getPlayer(name);
		if (silentPlayer == null) {
			player.sendMessage(ThePlugin.c2 + name + " er ikke online");
			return;
		}
		
		if (silentMap.containsKey(player.getName())) {
			silentMap.get(player.getName()).add(silentPlayer.getName());
			player.sendMessage(ThePlugin.c1 + "Du vil IKKE se hva " + silentPlayer.getName() + " skriver");
		}
		else {
			List<String> silentName = new ArrayList<String>();
			silentMap.put(player.getName(), silentName);
			silentMap.get(player.getName()).add(silentPlayer.getName());
			player.sendMessage(ThePlugin.c1 + "Du vil IKKE se hva " + silentPlayer.getName() + " skriver");
		}
	}
	
	public boolean remSilentPlayer(Player player, String name) {
		Player silentPlayer = new OnlinePlayer().getPlayer(name);
		if (silentPlayer != null) {
			name = silentPlayer.getName();
		}
		
		if (silentMap.containsKey(player.getName())) {
			if (silentMap.get(player.getName()).remove(name)) {
				player.sendMessage(ThePlugin.c1 + "Du kan nå se hva " + name + " skriver");
				return true;
			}
		}
		return false;
	}
	
	public String[] getSilentPlayer(Player player) {
		String[] silentNames = null;
		if (silentMap.containsKey(player.getName())) {
			silentNames = new String[silentMap.get(player.getName()).size()];
			silentNames = silentMap.get(player.getName()).toArray(silentNames);
		}
		return silentNames;
	}
}
