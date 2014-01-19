package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;

public class HelpCmd {
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new HelpCmd().help(sender, args);
	}
	
	public boolean help(CommandSender sender, String[] args) {
        String command;
        int pageNumber;
        
        if (args.length == 0) {
        	command = "";
        	pageNumber = 1;
        } else if (NumberUtils.isDigits(args[args.length - 1])) {
        	command = StringUtils.join(ArrayUtils.subarray(args, 0, args.length - 1), " ");
        	try {
        		pageNumber = NumberUtils.createInteger(args[args.length - 1]);
        	} catch (NumberFormatException exception) {
        		pageNumber = 1;
        	}
        	
        	if (pageNumber < 1) {
        		pageNumber = 1;
    		}
        } else {
        	command = StringUtils.join(args, " ");
        	pageNumber = 1;
        }
    	
    	HelpMap helpMap = Bukkit.getServer().getHelpMap();
    	HelpTopic topic = helpMap.getHelpTopic(command);
    	
    	if (topic == null) {
    		topic = helpMap.getHelpTopic("/" + command);
    	}
    	
    	if (topic == null || !topic.canSee(sender)) {
    		sender.sendMessage(Color.WARNING + "Ingen hjelp for " + command);
    		return true;
    	}

    	List<String> list = new ArrayList<String>();
		for (String line : topic.getFullText(sender).split("\n")) {
			if (!line.contains("/op:") && !line.contains("/deop:")
					&& !line.contains("/plugins") && !line.contains("/version")) list.add(line);
		}
		
		String name = topic.getName();
    	if (!MyPlayer.getPlayer(sender).isAdmin()) {
    		if (name == "Index") {
	    		list.set(0, ChatColor.GRAY + "Det finnes flere sider, skriv f.eks: /hjelp 2");
	    		for (int index = 1; list.size() > index; index++) {
	    			String line = list.get(index);
	    			if (!line.contains("/") || line.contains("//") || line.contains("/none")) {
	    				list.remove(index);
	    				index--;
	    			}
	    		}
    		} else if (name == "Aliases") {
	    		list.set(0, ChatColor.GRAY + "Liste over alle Aliaser:");
	    		for (int index = 1; list.size() > index; index++) {
	    			String line = list.get(index);
	    			if (line.contains("//")) {
	    				list.remove(index);
	    				index--;
	    			}
	    		}
    		} else if (name.equals("WorldEdit") || name.equals("Bukkit") || list.size() < 2) {
    			list.clear();
    			list.add(Color.WARNING + "Ingen hjelp for " + name);
    		} else {
	    		list.set(0, ChatColor.GRAY + "Liste over alle " + name + " kommandoer:");
			}
    	}
    	
    	Util.sendAsPages(sender, pageNumber + "", 0, "Hjelp: " + name, "", list);
		return true;
	}
}
