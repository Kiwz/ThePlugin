package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.Config;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;

public class RulesCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new RulesCmd().rules(sender);
	}
	
	private boolean rules(CommandSender sender) {
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("--------- ");
		header.append(ChatColor.WHITE);
		header.append("Regler: ");
		header.append(ChatColor.YELLOW);
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		int i = 1;
		for (String rule : Config.getRules()) {
			sender.sendMessage(Color.SAFE + "" + i + ": " + rule);
			i++;
		}
		return true;
	}
}
