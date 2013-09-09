package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.commands.EntityCommand;
import net.kiwz.ThePlugin.commands.GameModeCommand;
import net.kiwz.ThePlugin.commands.FeedCommand;
import net.kiwz.ThePlugin.commands.FlyCommand;
import net.kiwz.ThePlugin.commands.ItemCommand;
import net.kiwz.ThePlugin.commands.HealCommand;
import net.kiwz.ThePlugin.commands.HelpCommand;
import net.kiwz.ThePlugin.commands.HomeCommand;
import net.kiwz.ThePlugin.commands.OpenInvCommand;
import net.kiwz.ThePlugin.commands.PlaceCommand;
import net.kiwz.ThePlugin.commands.SpawnCommand;
import net.kiwz.ThePlugin.commands.TPCommand;
import net.kiwz.ThePlugin.commands.WorldCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String cmdName = cmd.getName();
		
		if (cmdName.equalsIgnoreCase("entity")) {
			EntityCommand entity = new EntityCommand();
			entity.entity(sender, args);
			return true;
		}
		
		if (cmdName.equalsIgnoreCase("feed")) {
			FeedCommand feed = new FeedCommand();
			feed.feed(sender, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("fly")) {
			FlyCommand fly = new FlyCommand();
			fly.fly(sender, cmd, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("gm")) {
			GameModeCommand gm = new GameModeCommand();
			gm.gameMode(sender, cmd, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("heal")) {
			HealCommand heal = new HealCommand();
			heal.heal(sender, cmd, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("hjelp")) {
			HelpCommand help = new HelpCommand();
			help.help(sender, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("home")) {
			HomeCommand home = new HomeCommand();
			home.home(sender, cmd, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("sethome")) {
			HomeCommand home = new HomeCommand();
			home.setHome(sender, cmd, args);
			return true;
		}
	    
		else if (cmdName.equalsIgnoreCase("item")) {
	    	ItemCommand item = new ItemCommand();
	    	item.giveItem(sender, cmd, args);
			return true;
	    }
		
		else if (cmdName.equalsIgnoreCase("openinv")) {
			OpenInvCommand openinv = new OpenInvCommand();
			openinv.openInv(sender, cmd, args);
			return true;
	    }
		
		else if (cmdName.equalsIgnoreCase("plass")) {
			PlaceCommand place = new PlaceCommand();
			place.place(sender, cmd, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("spawn")) {
			SpawnCommand spawn = new SpawnCommand();
			spawn.spawn(sender, cmd, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("setspawn")) {
			SpawnCommand spawn = new SpawnCommand();
			spawn.setSpawn(sender, cmd, args);
			return true;
		}
		
		else if (cmdName.equalsIgnoreCase("tp")) {
			TPCommand tp = new TPCommand();
			tp.tp(sender, cmd, args);
			return true;
	    }
		
		else if (cmdName.equalsIgnoreCase("world")) {
			WorldCommand world = new WorldCommand();
			world.world(sender, cmd, args);
			return true;
	    }
	    
		else if (cmdName.equalsIgnoreCase("test")) {
			
			// Test her:
			// Test slutter!

			return true;
		}
		else {
			sender.sendMessage(ThePlugin.c2 + "Noe fryktelig galt skjedde, kontakt en Admin");
			return false;
		}
	}
}
