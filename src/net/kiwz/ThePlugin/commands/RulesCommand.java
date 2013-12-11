package net.kiwz.ThePlugin.commands;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;

public class RulesCommand {
	
	public void rules(CommandSender sender) {
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
		try {
			File file = new File("plugins\\ThePlugin\\rules.txt");
			if (file.createNewFile()) {
				PrintWriter writer = new PrintWriter("plugins\\ThePlugin\\rules.txt");
				writer.println("One rule on each line");
				writer.close();
			}
			Scanner sc = new Scanner(file);
			int i = 1;
			while (sc.hasNext()) {
				String ii = i + "";
				sender.sendMessage(ThePlugin.c6 + ii + ": " + sc.nextLine());
				i++;
			}
			sc.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
