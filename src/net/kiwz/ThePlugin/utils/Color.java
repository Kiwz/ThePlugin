package net.kiwz.ThePlugin.utils;

import org.bukkit.ChatColor;

public enum Color {
	/**
	 * GOLD
	 */
	INFO,
	
	/**
	 * YELLOW
	 */
	HEADER,
	
	/**
	 * DARK_RED
	 */
	WARNING,
	
	/**
	 * GREEN
	 */
	SAFE,
	
	/**
	 * RED
	 */
	UNSAFE,
	
	/**
	 * DARK_GREEN
	 */
	PLAYER,
	
	/**
	 * WHITE
	 */
	VARIABLE,
	
	/**
	 * LIGHT_PURPLE
	 */
	SERVER,
	
	/**
	 * RED
	 */
	ADMIN,
	
	/**
	 * WHITE
	 */
	PLACE,
	
	/**
	 * WHITE
	 */
	COMMAND,
	
	/**
	 * GRAY
	 */
	WHISPER;
	
	public String toString(){
		if(this == INFO) return ChatColor.GOLD.toString();
		else if (this == HEADER) return ChatColor.YELLOW.toString();
		else if (this == WARNING) return ChatColor.DARK_RED.toString();
		else if (this == SAFE) return ChatColor.GREEN.toString();
		else if (this == UNSAFE) return ChatColor.RED.toString();
		else if (this == PLAYER) return ChatColor.DARK_GREEN.toString();
		else if (this == VARIABLE) return ChatColor.WHITE.toString();
		else if (this == SERVER) return ChatColor.LIGHT_PURPLE.toString();
		else if (this == ADMIN) return ChatColor.RED.toString();
		else if (this == PLACE) return ChatColor.WHITE.toString();
		else if (this == COMMAND) return ChatColor.WHITE.toString();
		else if (this == WHISPER) return ChatColor.GRAY.toString();
		else return ChatColor.WHITE.toString();
	}
	
}
