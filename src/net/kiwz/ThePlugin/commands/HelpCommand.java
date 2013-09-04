package net.kiwz.ThePlugin.commands;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.util.ChatPaginator;

public class HelpCommand {
	
	public void help(CommandSender sender, String[] args) {
        String command;
        int pageNumber;
        int pageHeight;
        int pageWidth;
        
        if (args.length == 0) {
        	command = "";
        	pageNumber = 1;
        }
        else if (NumberUtils.isDigits(args[args.length - 1])) {
        	command = StringUtils.join(ArrayUtils.subarray(args, 0, args.length - 1), " ");
        	try {
        		pageNumber = NumberUtils.createInteger(args[args.length - 1]);
        	}
        	catch (NumberFormatException exception) {
        		pageNumber = 1;
        	}
        	if (pageNumber <= 0) {
        		pageNumber = 1;
        		}
        }
        else {
        	command = StringUtils.join(args, " ");
        	pageNumber = 1;
        }
        
    	if (!(sender instanceof Player)) {
			pageHeight = ChatPaginator.UNBOUNDED_PAGE_HEIGHT;
			pageWidth = ChatPaginator.UNBOUNDED_PAGE_WIDTH;
    	}
    	else {
    		pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1;
    		pageWidth = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
    	}
    	
    	HelpMap helpMap = Bukkit.getServer().getHelpMap();
    	HelpTopic topic = helpMap.getHelpTopic(command);
    	
    	if (topic == null) {
    		topic = helpMap.getHelpTopic("/" + command);
    	}
    	
    	if (topic == null || !topic.canSee(sender)) {
    		sender.sendMessage(ChatColor.RED + "Ingen hjelp for " + command);
    		return;
    	}
    	
        ChatPaginator.ChatPage page = ChatPaginator.paginate(topic.getFullText(sender), pageNumber, pageWidth, pageHeight);
        
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("--------- ");
		header.append(ChatColor.WHITE);
		header.append("Help: ");
		header.append(topic.getName());
		header.append(" ");
		if (page.getTotalPages() > 1) {
			header.append("(");
			header.append(page.getPageNumber());
			header.append("/");
			header.append(page.getTotalPages());
			header.append(") ");
		}
		header.append(ChatColor.YELLOW);
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		sender.sendMessage(page.getLines());
	}
	
	public void customHelp(CommandSender sender, String cmd, String pageN, String msg) {
        int pageNumber;
        try {
        	pageNumber = Integer.parseInt(pageN);
        }
        catch (NumberFormatException e){
        	pageNumber = 1;
        }
        int pageHeight;
        int pageWidth;
        
    	if (!(sender instanceof Player)) {
			pageHeight = ChatPaginator.UNBOUNDED_PAGE_HEIGHT;
			pageWidth = ChatPaginator.UNBOUNDED_PAGE_WIDTH;
    	}
    	else {
    		pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT + 0;
    		pageWidth = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
    	}
    	
        ChatPaginator.ChatPage page = ChatPaginator.paginate(msg, pageNumber, pageWidth, pageHeight);
        
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("--------- ");
		header.append(ChatColor.WHITE);
		header.append("Help: /" + cmd + " ");
		if (page.getTotalPages() > 1) {
			header.append("(");
			header.append(page.getPageNumber());
			header.append("/");
			header.append(page.getTotalPages());
			header.append(") ");
		}
		header.append(ChatColor.YELLOW);
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		sender.sendMessage(page.getLines());
	}
}
