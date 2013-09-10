package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Players;

public class HandlePlayers {
	private HashMap<String, Players> players = ThePlugin.getPlayers;
	private TimeFormat tFormat = new TimeFormat();
	
	/**
	 * 
	 * @param playerName as String
	 * 
	 * <p>This method will add a given player to the Players HashMap. Should
	 * only be used once per player.</p>
	 */
	public void addPlayer(String playerName) {
		
		Players player = new Players();
		player.player = playerName;
		player.lastLogin = (int) (System.currentTimeMillis() / 1000);
		player.timePlayed = 0;
		player.mute = 0;
		player.ip = Bukkit.getPlayer(playerName.toUpperCase()).getAddress().toString();
		players.put(playerName.toUpperCase(), player);
	}
	
	/**
	 * 
	 * @param playerName to check
	 * @return {@code true} if given player has played before,
	 * or {@code false} if given player has not played before.
	 */
	public boolean hasPlayedBefore(String playerName) {
		return players.containsKey(playerName.toUpperCase());
	}
	
	/**
	 * 
	 * @param playerName
	 * @return String CaseSensitive PlayerName, or null if given player has not played before.
	 */
	public String getPlayerName(String playerName) {
		return players.get(playerName.toUpperCase()).player;
	}
	
	public int getLastLogin(String playerName) {
		return players.get(playerName.toUpperCase()).lastLogin;
	}
	
	/**
	 * 
	 * @param playerName
	 * @return int time played for given player in seconds,
	 * or null if given player has not played before.
	 */
	public int getTimePlayed(String playerName) {
		return players.get(playerName.toUpperCase()).timePlayed;
	}
	
	public boolean isMuted(String playerName) {
		if (players.get(playerName.toUpperCase()).mute == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public String getIP(String playerName) {
		return players.get(playerName.toUpperCase()).ip.replace("/", "").split(":")[0];
	}
	
	/**
	 * 
	 * @param playerName as String
	 * 
	 * <p>This method will set this time (in seconds) as the Last Login time for given player.</p>
	 */
	public void setLastLogin(String playerName) {
		players.get(playerName.toUpperCase()).lastLogin = (int) (System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 
	 * @param playerName as String
	 * 
	 * <p>This method will set the time played (in seconds) for given player.</p>
	 */
	public void setTimePlayed(String playerName) {
		players.get(playerName.toUpperCase()).timePlayed = (int) (System.currentTimeMillis() / 1000)
				- players.get(playerName.toUpperCase()).lastLogin+ players.get(playerName.toUpperCase()).timePlayed;
	}
	
	/**
	 * 
	 * @param playerName as String
	 * 
	 * <p>This method will set the "mute" status for given player.</p>
	 */
	public void setMute(String playerName, int mute) {
		players.get(playerName.toUpperCase()).mute = mute;
	}
	
	public void unMuteALL() {
		for (String playerName : players.keySet()) {
			if (players.get(playerName).mute == 1) {
				players.get(playerName).mute = 0;
			}
		}
	}
	
	/**
	 * @param playerName
	 * 
	 * <p>This method will set the IP-adress for given player.</p>
	 */
	public void setIP(String playerName) {
		players.get(playerName.toUpperCase()).ip = Bukkit.getPlayer(playerName).getAddress().toString();
	}
	
	public void sendMuted(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + "------ " + ChatColor.WHITE + "Liste over alle spillere som er muted:" + ChatColor.YELLOW + " ------");
		for (String playerName : players.keySet()) {
			if (isMuted(playerName)) {
				sender.sendMessage(ThePlugin.c1 + getPlayerName(playerName));
			}
		}
	}
	
	public void sendPlayer(CommandSender sender, String playerName) {
		if (!hasPlayedBefore(playerName)) {
			sender.sendMessage(ThePlugin.c2 + playerName + " har aldri spilt her");
			return;
		}
		playerName = getPlayerName(playerName);
		Player player = new OfflinePlayer().getPlayer(playerName);
		Location loc = player.getLocation();
		String muted = "Nei";
		if (isMuted(playerName)) muted = "Ja";
		String lastLogin = tFormat.getFullDate(getLastLogin(playerName));
		int timePlayed = tFormat.getHours(getTimePlayed(playerName));
		
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("----- ");
		header.append(ChatColor.WHITE);
		header.append(playerName);
		header.append(ChatColor.GRAY);
		header.append(" (ip: " + getIP(playerName) + ") ");
		header.append(ChatColor.YELLOW);
		header.append(" -----");
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		sender.sendMessage(ThePlugin.c1 + "Siste innlogging: " + ThePlugin.c4 + lastLogin);
		sender.sendMessage(ThePlugin.c1 + "Tid spilt: " + ThePlugin.c4 + timePlayed + " timer");
		sender.sendMessage(ThePlugin.c1 + "Muted: " + ThePlugin.c4 + muted);
		sender.sendMessage(ThePlugin.c1 + "GameMode: " + ThePlugin.c4 + player.getGameMode());
		sender.sendMessage(ThePlugin.c1 + "Experience: " + ThePlugin.c4 + player.getTotalExperience());
		sender.sendMessage(ThePlugin.c1 + "Lokasjon: " + ThePlugin.c4 + loc.getWorld().getName() +
				" X: " + loc.getBlockX() + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ());
	}
}
