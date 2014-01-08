package net.kiwz.ThePlugin.utils;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

public class Perm {
	
	public static boolean isAdmin(Player player) {
		if (player.isOp()) return true;
		if (Config.getAdmins().contains(player.getName())) return true;
        return false;
	}
	
	public static boolean isAdmin(String name) {
		if (Bukkit.getServer().getPlayer(name).isOp()) return true;
		if (Config.getAdmins().contains(name)) return true;
        return false;
	}
	
	public static void setOpPerm(Player player) {
		PermissionAttachment atch = player.addAttachment(ThePlugin.getPlugin());
		if (player.getName().equals("Kiwz") || player.getName().equals("zacker")) {
			atch.setPermission("bukkit.command.op", true);
		}
	}
	
	public static void setAdminPermissions(Player player) {
		PermissionAttachment atch = player.addAttachment(ThePlugin.getPlugin());
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
	}
	
	public static void setPermissions() {
		for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
			if (!plugin.getName().equalsIgnoreCase("WorldEdit") && !plugin.getName().equalsIgnoreCase("WorldGuard") && !plugin.equals(ThePlugin.getPlugin())) {
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
