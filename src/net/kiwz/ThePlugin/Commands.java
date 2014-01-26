package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.commands.BanCmd;
import net.kiwz.ThePlugin.commands.EntityCmd;
import net.kiwz.ThePlugin.commands.HelpCmd;
import net.kiwz.ThePlugin.commands.HomeCmd;
import net.kiwz.ThePlugin.commands.ListCmd;
import net.kiwz.ThePlugin.commands.MemCmd;
import net.kiwz.ThePlugin.commands.MyPlayerCmd;
import net.kiwz.ThePlugin.commands.PlaceCmd;
import net.kiwz.ThePlugin.commands.PlayerCmd;
import net.kiwz.ThePlugin.commands.ServerCmd;
import net.kiwz.ThePlugin.commands.RulesCmd;
import net.kiwz.ThePlugin.commands.MyServerCmd;
import net.kiwz.ThePlugin.commands.SpawnCmd;
import net.kiwz.ThePlugin.commands.SpyCmd;
import net.kiwz.ThePlugin.commands.TpCmd;
import net.kiwz.ThePlugin.commands.TpchunkCmd;
import net.kiwz.ThePlugin.commands.TpsCmd;
import net.kiwz.ThePlugin.commands.WorldCmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String c = command.getName().toLowerCase();

		// Test her:
		if (c.equals("test")) {
			
			
			
			return true;
		}
		// Test slutter!
		

		if (new ServerCmd(sender, c, args).exec()) return true;
		if (new PlayerCmd(sender, c, args).exec()) return true;
		if (new MyServerCmd(sender, c, args).exec()) return true;
		if (new MyPlayerCmd(sender, c, args).exec()) return true;
		
		
		else if (c.equals("ban") || c.equals("unban") || c.equals("kick")) return BanCmd.exec(sender, c, args);
		else if (c.equals("entity")) return EntityCmd.exec(sender, args);
		else if (c.equals("hjelp")) return HelpCmd.exec(sender, args);
		else if (c.equals("hjem")) return HomeCmd.execGet(sender, args);
		else if (c.equals("setthjem")) return HomeCmd.execSet(sender, args);
		else if (c.equals("online")) return ListCmd.exec(sender, args);
		else if (c.equals("minne")) return MemCmd.exec(sender);
		else if (c.equals("plass")) return PlaceCmd.exec(sender, args);
		else if (c.equals("spy")) return SpyCmd.exec(sender);
		else if (c.equals("regler")) return RulesCmd.exec(sender, args);
		else if (c.equals("spawn")) return SpawnCmd.execGet(sender, args);
		else if (c.equals("setspawn")) return SpawnCmd.execSet(sender);
		else if (c.equals("tp")) return TpCmd.exec(sender, args);
		else if (c.equals("tpa")) return TpCmd.exec(sender);
		else if (c.equals("tpchunk")) return TpchunkCmd.exec(sender, args);
		else if (c.equals("ticks")) return TpsCmd.exec(sender, args);
		else if (c.equals("world")) return WorldCmd.exec(sender, args);
		else return false;
	}
}
