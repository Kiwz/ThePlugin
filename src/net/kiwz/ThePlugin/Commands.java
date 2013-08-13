package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.commands.GameModeCommand;
import net.kiwz.ThePlugin.commands.FeedCommand;
import net.kiwz.ThePlugin.commands.FlyCommand;
import net.kiwz.ThePlugin.commands.GiveCommand;
import net.kiwz.ThePlugin.commands.HealCommand;
import net.kiwz.ThePlugin.commands.HelpCommand;
import net.kiwz.ThePlugin.commands.OpenInvCommand;
import net.kiwz.ThePlugin.commands.TeleportCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("feed")) {
			FeedCommand feed = new FeedCommand();
			feed.feed(sender, cmd, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("fly")) {
			FlyCommand fly = new FlyCommand();
			fly.fly(sender, cmd, args);
			return true;
		}
	    
	    if (cmd.getName().equalsIgnoreCase("give")) {
	    	GiveCommand give = new GiveCommand();
    		give.giveItem(sender, cmd, args);
    		return true;
	    }
		
		if (cmd.getName().equalsIgnoreCase("gm")) {
			GameModeCommand gm = new GameModeCommand();
			gm.gameMode(sender, cmd, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("heal")) {
			HealCommand heal = new HealCommand();
			heal.heal(sender, cmd, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("hjelp")) {
			HelpCommand help = new HelpCommand();
			help.help(sender, cmd, args);
			return true;
		}
		
	    if (cmd.getName().equalsIgnoreCase("openinv")) {
			OpenInvCommand openinv = new OpenInvCommand();
			if (!openinv.openInv(sender, cmd, args)) {
				return false;
			}
			return true;
	    }
		
	    if (cmd.getName().equalsIgnoreCase("teleport")) {
			TeleportCommand tp = new TeleportCommand();
			tp.tp(sender, cmd, args);
			return true;
	    }
	    
		if (cmd.getName().equalsIgnoreCase("test")) {
			sender.sendMessage("Du er OP og dette er test-kommandoen");
			
			
			// Test her:
			
			// Test slutter!
			
			return true;
		}
	    return true;
	}
}
