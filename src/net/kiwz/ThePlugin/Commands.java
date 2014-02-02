package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.commands.BanCmd;
import net.kiwz.ThePlugin.commands.ChestCmd;
import net.kiwz.ThePlugin.commands.EntityCmd;
import net.kiwz.ThePlugin.commands.FlyCmd;
import net.kiwz.ThePlugin.commands.GmCmd;
import net.kiwz.ThePlugin.commands.HealCmd;
import net.kiwz.ThePlugin.commands.HelpCmd;
import net.kiwz.ThePlugin.commands.HomeCmd;
import net.kiwz.ThePlugin.commands.IgnoreCmd;
import net.kiwz.ThePlugin.commands.ListCmd;
import net.kiwz.ThePlugin.commands.MemCmd;
import net.kiwz.ThePlugin.commands.MuteCmd;
import net.kiwz.ThePlugin.commands.OiCmd;
import net.kiwz.ThePlugin.commands.PlaceCmd;
import net.kiwz.ThePlugin.commands.PvpCmd;
import net.kiwz.ThePlugin.commands.ReplayCmd;
import net.kiwz.ThePlugin.commands.RulesCmd;
import net.kiwz.ThePlugin.commands.SpawnCmd;
import net.kiwz.ThePlugin.commands.SpyCmd;
import net.kiwz.ThePlugin.commands.TellCmd;
import net.kiwz.ThePlugin.commands.TimeCmd;
import net.kiwz.ThePlugin.commands.TpCmd;
import net.kiwz.ThePlugin.commands.TpchunkCmd;
import net.kiwz.ThePlugin.commands.TpsCmd;
import net.kiwz.ThePlugin.commands.VaerCmd;
import net.kiwz.ThePlugin.commands.WhoisCmd;
import net.kiwz.ThePlugin.commands.WorldCmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String c = cmd.getName().toLowerCase();

		if (c.equals("test")) {
			// Test her:
			
			// Test slutter!
			return true;
		}

		else if (c.equals("ban") || c.equals("unban") || c.equals("kick")) return BanCmd.exec(sender, c, args);
		else if (c.equals("chest")) return ChestCmd.exec(sender, args);
		else if (c.equals("entity")) return EntityCmd.exec(sender, args);
		else if (c.equals("fly")) return FlyCmd.exec(sender, args);
		else if (c.equals("gm")) return GmCmd.exec(sender, args);
		else if (c.equals("heal")) return HealCmd.exec(sender, args);
		else if (c.equals("hjelp")) return HelpCmd.exec(sender, args);
		else if (c.equals("hjem")) return HomeCmd.execGet(sender, args);
		else if (c.equals("setthjem")) return HomeCmd.execSet(sender, args);
		else if (c.equals("ignorer")) return IgnoreCmd.exec(sender, args);
		else if (c.equals("online")) return ListCmd.exec(sender, args);
		else if (c.equals("minne")) return MemCmd.exec(sender);
		else if (c.equals("mute")) return MuteCmd.execSet(sender, args);
		else if (c.equals("unmute")) return MuteCmd.execUnSet(sender, args);
		else if (c.equals("oe")) return OiCmd.execEnder(sender, args);
		else if (c.equals("oi")) return OiCmd.execPlayer(sender, args);
		else if (c.equals("plass")) return PlaceCmd.exec(sender, args);
		else if (c.equals("pvp")) return PvpCmd.exec(sender);
		else if (c.equals("spy")) return SpyCmd.exec(sender);
		else if (c.equals("svar")) return ReplayCmd.exec(sender, args);
		else if (c.equals("regler")) return RulesCmd.exec(sender, args);
		else if (c.equals("spawn")) return SpawnCmd.execGet(sender, args);
		else if (c.equals("setspawn")) return SpawnCmd.execSet(sender);
		else if (c.equals("melde")) return TellCmd.exec(sender, args);
		else if (c.equals("tid")) return TimeCmd.exec(sender, args);
		else if (c.equals("tp")) return TpCmd.exec(sender, args);
		else if (c.equals("tpa")) return TpCmd.exec(sender);
		else if (c.equals("tpchunk")) return TpchunkCmd.exec(sender, args);
		else if (c.equals("ticks")) return TpsCmd.exec(sender, args);
		else if (c.equals("hvem")) return WhoisCmd.exec(sender, args);
		else if (c.equals("world")) return WorldCmd.exec(sender, args);
		else if (c.equals("vaer")) return VaerCmd.exec(sender, args);
		else return false;
	}
}
