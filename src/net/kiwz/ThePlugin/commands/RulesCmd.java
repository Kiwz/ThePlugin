package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.Config;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.command.CommandSender;

public class RulesCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new RulesCmd().rules(sender);
	}
	
	private boolean rules(CommandSender sender) {
		List<String> list = new ArrayList<String>();
		int i = 1;
		for (String rule : Config.getConfig().getRules()) {
			list.add(Color.SAFE + "" + i + ": " + rule);
			i++;
		}
		Util.sendAsPages(sender, "1", 10, "Regler", "", list);
		return true;
	}
}
