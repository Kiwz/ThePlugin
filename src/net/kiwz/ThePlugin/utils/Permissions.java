package net.kiwz.ThePlugin.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Permissions {
	
	public ArrayList<String> loadAdmins() {
		ArrayList<String> array = new ArrayList<String>();
		try {
			File file = new File("plugins\\ThePlugin\\admins.txt");
			if (file.createNewFile()) {
				PrintWriter writer = new PrintWriter("plugins\\ThePlugin\\admins.txt");
				writer.println("Kiwz");
				writer.close();
			}
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				array.add(sc.nextLine());
			}
			sc.close();
		}
		catch (IOException e) {
		}
		return array;
	}
	
	public boolean isAdmin(Player player) {
        for (String admin : ThePlugin.admins) {
        	if (player.getName().equals(admin)) {
        		return true;
        	}
        }
        return false;
	}
	
	public void setAdminPermissions(Player player) {
		PermissionAttachment atch = player.addAttachment(Bukkit.getServer().getPluginManager().getPlugin("ThePlugin"));
		atch.setPermission("worldedit.navigation.jumpto.tool", true);
		atch.setPermission("bukkit.command.ban", true);
		atch.setPermission("bukkit.command.ban.list", true);
		atch.setPermission("bukkit.command.unban", true);
		atch.setPermission("bukkit.command.kick", true);
		atch.setPermission("ThePlugin.mute", true);
		atch.setPermission("ThePlugin.unmute", true);
		atch.setPermission("ThePlugin.fly", true);
		atch.setPermission("ThePlugin.openinv", true);
		atch.setPermission("ThePlugin.pvp", true);
		atch.setPermission("ThePlugin.tp", true);
		atch.setPermission("ThePlugin.whois", true);
		if (player.getName().equals("Kiwz") || player.getName().equals("zacker")) {
			atch.setPermission("bukkit.command.op", true);
		}
	}
	
	public void setPermissions() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		for (Plugin plugin : pm.getPlugins()) {
			if (!plugin.getName().equals("WorldEdit") && !plugin.getName().equals("ThePlugin")) {
				for (String cmd : plugin.getDescription().getCommands().keySet()) {
					Object perm = plugin.getDescription().getCommands().get(cmd).get("permission");
					if (perm == null) {
						Bukkit.getServer().getPluginCommand(cmd).setPermission(plugin.getName() + "." + cmd);
					}
				}
				for (Permission perm : plugin.getDescription().getPermissions()) {
					if (!perm.getDefault().equals(PermissionDefault.OP)) {
						perm.setDefault(PermissionDefault.OP);
					}
				}
			}
		}
	}
}
