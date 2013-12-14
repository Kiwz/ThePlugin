package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class SendAsPages {
	
	public void sendAsPages(CommandSender sender, String pageN, int pageHeight, String about, ArrayList<String> messages) {
        int pageNumber;
        try {
        	pageNumber = Integer.parseInt(pageN);
        }
        catch (NumberFormatException e){
        	pageNumber = 1;
        }
        int pageWidth;
        
    	if (!(sender instanceof Player)) {
			pageHeight = ChatPaginator.UNBOUNDED_PAGE_HEIGHT;
			pageWidth = ChatPaginator.UNBOUNDED_PAGE_WIDTH;
    	}
    	else {
    		pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT + pageHeight;
    		pageWidth = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
    	}
    	StringBuilder msgBuilder = new StringBuilder();
    	for (String message : messages) {
    		msgBuilder.append(message + "\n");
    	}
    	String msg = msgBuilder.toString();    	
        ChatPaginator.ChatPage page = ChatPaginator.paginate(msg, pageNumber, pageWidth, pageHeight);
        
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("--------- ");
		header.append(ChatColor.WHITE);
		header.append(about + " ");
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
