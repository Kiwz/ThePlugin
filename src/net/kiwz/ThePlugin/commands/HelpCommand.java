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
	/*private Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
	private ChatColor yellow = ChatColor.YELLOW;
	private ChatColor gray = ChatColor.GRAY;
	private ChatColor white = ChatColor.WHITE;
	private ChatColor gold = ChatColor.GOLD;*/
	
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
	
	public void customHelp(CommandSender sender, String pageN, String msg) {
        int pageNumber = Integer.parseInt(pageN);
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
		header.append("Help: /plass ");
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
    /*
	
	public void help1(CommandSender sender, String[] args) {
		
		if (args.length == 0) {
			for (String msg : mainHelp(sender)) {
				sender.sendMessage(msg);
			}
			return;
		}
		
		for (Plugin plugin : plugins) {
			if (args[0].equalsIgnoreCase(plugin.getName())) {
				for (String msg : pluginHelp(sender, plugin)) {
					sender.sendMessage(msg);
				}
				return;
			}
			for (String cmd : commandList(sender, plugin)) {
				if (args[0].equalsIgnoreCase(cmd)) {
					for (String msg : commandHelp(sender, plugin, cmd)) {
						sender.sendMessage(msg);
					}
					return;
				}
			}
		}
		if (!(sender instanceof Player)) {
			
		}
		else if (sender.isOp()) {
			if (args.length == 0) ((Player) sender).performCommand("help");
			if (args.length == 1) ((Player) sender).performCommand("help " + args[0]);
			if (args.length == 2) ((Player) sender).performCommand("help " + args[0] + " " + args[1]);
			return;
		}
		else {
			sender.sendMessage(ThePlugin.c2 + "Ingen hjelp for " + args[0]);
		}
	}
	
	/**
	 * 
	 * @param player searcher
	 * @param plugin to search for
	 * @return List of commands for given plugin
	 *//*
	private ArrayList<String> commandList(CommandSender sender, Plugin plugin) {
		Map<String, Map<String, Object>> pluginCommands = plugin.getDescription().getCommands();
		ArrayList<String> cmd = new ArrayList<String>();
		for (String commandName : pluginCommands.keySet()) {
			for (String commandNode : pluginCommands.get(commandName).keySet()) {
				if (commandNode.equals("permission")) {
					if (sender.hasPermission(pluginCommands.get(commandName).get("permission").toString())) {
						cmd.add(commandName);
					}
				}
			}
		}
		return cmd;
	}
	
	/**
	 * 
	 * @param player searcher
	 * @param plugin to search for
	 * @param cmd to search for
	 * @return help for given command
	 *//*
	public ArrayList<String> commandHelp(CommandSender sender, Plugin plugin, String cmd) {
		Map<String, Object> pluginCommand = plugin.getDescription().getCommands().get(cmd);
		StringBuilder header = new StringBuilder();
        header.append(yellow + "--------- " + white + "Hjelp: /"+ cmd + yellow + " ");
        for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
            header.append("-");
        }
		ArrayList<String> help = new ArrayList<String>();
        help.add(header.toString());
		if (sender.hasPermission(pluginCommand.get("permission").toString())) {
			if (plugin.getName().equals("ThePlugin")) {
				for (String helpTopic : getThePluginCmdHelp(cmd)) {
					help.add(helpTopic);
				}
			}
			else {
				if (pluginCommand.get("usage") != null) {
					help.add(gold + "Beskrivelse: " + white + pluginCommand.get("description").toString());
				}
				else {
					help.add(gold + "Beskrivelse: " + white);
				}
				if (pluginCommand.get("usage") != null) {
					help.add(gold + "Brukermåte:\n" + white + pluginCommand.get("usage").toString().trim());
				}
				else {
					help.add(gold + "Brukermåte: " + white);
				}
			}
			if (pluginCommand.get("aliases") != null) {
				help.add(gold + "Aliaser: " + white + pluginCommand.get("aliases").toString().replace("[", "").replace("]", ""));
			}
			else {
				help.add(gold + "Aliaser: " + white);
			}
		}
		return help;
	}
	
	/**
	 * 
	 * @param cmd to search for
	 * @return help for given command
	 *//*
	private ArrayList<String> getThePluginCmdHelp(String cmd) {
		ArrayList<String> help = new ArrayList<String>();
		if (cmd.equals("plass")) {
			help = (new PlaceCommand().help());
		}
		else {
			help.add(ThePlugin.c2 + "Ingen hjelp for denne kommandoen");
		}
		return help;
	}
	
	/**
	 * 
	 * @param player searcher
	 * @param plugin to search for
	 * @return Help for given plugin
	 *//*
	private ArrayList<String> pluginHelp(CommandSender sender, Plugin plugin) {
		Map<String, Map<String, Object>> pluginCommands = plugin.getDescription().getCommands();
		StringBuilder header = new StringBuilder();
        header.append(yellow + "--------- " + white + "Hjelp: "+ plugin.getName() + yellow + " ");
        for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
            header.append("-");
        }
		ArrayList<String> help = new ArrayList<String>();
		help.add(header.toString());
		help.add(gray + "Liste over alle " + plugin.getName() + " kommandoer:");
		for (String commandName : pluginCommands.keySet()) {
			for (String commandNode : pluginCommands.get(commandName).keySet()) {
				if (commandNode.equals("permission")) {
					if (sender.hasPermission(pluginCommands.get(commandName).get("permission").toString())) {
						help.add(gold + "/" + commandName + ": " + white + pluginCommands.get(commandName).get("description").toString());
					}
				}
			}
		}
		return help;
	}
	
	/**
	 * 
	 * @return The main help menu
	 *//*
	private ArrayList<String> mainHelp(CommandSender sender) {
		StringBuilder header = new StringBuilder();
        header.append(yellow + "--------- " + white + "Hjelp: Indeks" + yellow + " ");
        for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
            header.append("-");
        }
		ArrayList<String> help = new ArrayList<String>();
		help.add(header.toString());
		help.add(gray + "Liste over alle Plugins du har tilgang til:");
		if (sender.isOp()) {
			help.add(gold + "Bukkit: " + white + "/hjelp Bukkit");
		}
		for (Plugin plugin : plugins) {
			help.add(gold + plugin.getName() + ": " + white + "/hjelp " + plugin.getName());
		}
		return help;
	}*/
}
