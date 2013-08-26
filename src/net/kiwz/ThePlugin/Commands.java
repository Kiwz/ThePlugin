package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.commands.GameModeCommand;
import net.kiwz.ThePlugin.commands.FeedCommand;
import net.kiwz.ThePlugin.commands.FlyCommand;
import net.kiwz.ThePlugin.commands.GiveCommand;
import net.kiwz.ThePlugin.commands.HealCommand;
import net.kiwz.ThePlugin.commands.HelpCommand;
import net.kiwz.ThePlugin.commands.HomeCommand;
import net.kiwz.ThePlugin.commands.OpenInvCommand;
import net.kiwz.ThePlugin.commands.PlaceCommand;
import net.kiwz.ThePlugin.commands.SpawnCommand;
import net.kiwz.ThePlugin.commands.TeleportCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("feed")) {
			FeedCommand feed = new FeedCommand();
			if (!feed.feed(sender, cmd, args)) {
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("fly")) {
			FlyCommand fly = new FlyCommand();
			if (!fly.fly(sender, cmd, args)) {
				return false;
			}
		}
	    
	    if (cmd.getName().equalsIgnoreCase("give")) {
	    	GiveCommand give = new GiveCommand();
	    	if (!give.giveItem(sender, cmd, args)) {
				return false;
			}
	    }
		
		if (cmd.getName().equalsIgnoreCase("gm")) {
			GameModeCommand gm = new GameModeCommand();
			if (!gm.gameMode(sender, cmd, args)) {
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("heal")) {
			HealCommand heal = new HealCommand();
			if (!heal.heal(sender, cmd, args)) {
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("hjelp")) {
			HelpCommand help = new HelpCommand();
			if (!help.help(sender, cmd, args)) {
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("home")) {
			HomeCommand home = new HomeCommand();
			if (!home.home(sender, cmd, args)) {
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("homeset")) {
			HomeCommand home = new HomeCommand();
			if (!home.homeSet(sender, cmd, args)) {
				return false;
			}
		}
		
	    if (cmd.getName().equalsIgnoreCase("openinv")) {
			OpenInvCommand openinv = new OpenInvCommand();
			if (!openinv.openInv(sender, cmd, args)) {
				return false;
			}
	    }
		
		if (cmd.getName().equalsIgnoreCase("plass")) {
			PlaceCommand place = new PlaceCommand();
			if (!place.place(sender, cmd, args)) {
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			SpawnCommand spawn = new SpawnCommand();
			if (!spawn.spawn(sender, cmd, args)) {
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("spawnset")) {
			SpawnCommand spawn = new SpawnCommand();
			if (!spawn.spawnSet(sender, cmd, args)) {
				return false;
			}
		}
		
	    if (cmd.getName().equalsIgnoreCase("teleport")) {
			TeleportCommand tp = new TeleportCommand();
			if (!tp.tp(sender, cmd, args)) {
				return false;
			}
	    }
	    
		if (cmd.getName().equalsIgnoreCase("test")) {
			sender.sendMessage("Du er OP og dette er test-kommandoen");
			
			
			// Test her:
			
			// Test slutter!
			
		}
	    return true;
	}
}
