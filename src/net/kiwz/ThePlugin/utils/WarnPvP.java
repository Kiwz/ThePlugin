package net.kiwz.ThePlugin.utils;

import org.bukkit.World;
import org.bukkit.entity.Player;

import net.kiwz.ThePlugin.ThePlugin;

public class WarnPvP {
	HandlePlaces hPlaces = new HandlePlaces();
	HandleWorlds hWorlds = new HandleWorlds();
	
	public void warnPvP (Player player, World fromWorld, World toWorld, int fromID, int toID) {
		if (fromWorld != toWorld) {
			if (toID != 0) {
				if (hPlaces.isPvP(toID)) {
					player.sendMessage(ThePlugin.c2 + "PvP er aktivert, pass deg for andre spillere");
				}
				else {
					player.sendMessage(ThePlugin.c6 + "PvP er deaktivert, slapp av");
				}
			}
			else {
				if (hWorlds.isPvP(toWorld)) {
					player.sendMessage(ThePlugin.c2 + "PvP er aktivert, pass deg for andre spillere");
				}
				else {
					player.sendMessage(ThePlugin.c6 + "PvP er deaktivert, slapp av");
				}
			}
		}
		else if (fromID != toID) {
			if (toID != 0) {
				if (hPlaces.isPvP(toID)) {
					player.sendMessage(ThePlugin.c2 + "PvP er aktivert, pass deg for andre spillere");
				}
				else {
					player.sendMessage(ThePlugin.c6 + "PvP er deaktivert, slapp av");
				}
			}
			else {
				if (hWorlds.isPvP(toWorld)) {
					player.sendMessage(ThePlugin.c2 + "PvP er aktivert, pass deg for andre spillere");
				}
				else {
					player.sendMessage(ThePlugin.c6 + "PvP er deaktivert, slapp av");
				}
			}
		}
	}
}
