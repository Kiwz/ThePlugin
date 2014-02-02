package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class InfoCmd {
	private Server server = Bukkit.getServer();
	private String cmd;
	private CommandSender sender;
	private String page = "1";
	
	public InfoCmd(CommandSender sender, String cmd, String[] args) {
		this.cmd = cmd;
		this.sender = sender;
		if(args.length != 0) this.page = args[0];
	}
	
	public boolean exec() {
		if (hjelp() || minne() || regler() || online()) return true;
		else return false;
	}
	
	private boolean online(CommandSender sender, String[] args) {
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		sender.sendMessage(Color.INFO + "Det er " + Color.VARIABLE + players.length + "/" + server.getMaxPlayers() + Color.INFO + " spillere online");
		StringBuilder header = new StringBuilder();
		for (Player player : players) {
			header.append(MyPlayer.getColorName(MyPlayer.getPlayer(player)) + ", ");
		}
		String playerNames = header.toString();
		if (playerNames.length() > 0) {
			playerNames = playerNames.substring(0, playerNames.length() - 2);
		}
		String[] msg = ChatPaginator.wordWrap(playerNames + " ", 55);
		sender.sendMessage(msg);
		return true;
	}
}
