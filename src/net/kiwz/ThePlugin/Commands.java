package net.kiwz.ThePlugin;

import net.kiwz.ThePlugin.utils.ChangeGM;
import net.kiwz.ThePlugin.utils.Fly;
import net.kiwz.ThePlugin.utils.GiveItem;
import net.kiwz.ThePlugin.utils.Heal;
import net.kiwz.ThePlugin.utils.Mat;
import net.kiwz.ThePlugin.utils.Teleport;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("gm") && sender.isOp()) {
			ChangeGM gm = new ChangeGM();
			gm.gameMode(sender, cmd, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("fly") && sender.isOp()) {
			Fly fly = new Fly();
			fly.flyMode(sender, cmd, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("heal") && sender.isOp()) {
			Heal heal = new Heal();
			heal.heal(sender, cmd, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("mat") && sender.isOp()) {
			Mat mat = new Mat();
			mat.mat(sender, cmd, args);
			return true;
		}
		
	    if ((cmd.getName().equalsIgnoreCase("teleport")) && (sender.isOp())) {
			Teleport tp = new Teleport();
			tp.tp(sender, cmd, args);
			return true;
	    }
	    
	    if ((cmd.getName().equalsIgnoreCase("gi")) && (sender.isOp())) {
	    	GiveItem item = new GiveItem();
    		item.giveItem(sender, cmd, args);
    		return true;
	    }
	    
		else if (cmd.getName().equalsIgnoreCase("test") && sender.isOp()) {
			sender.sendMessage("Du er OP og dette er test-kommandoen");
			
			// Test her:
			
			
			
			// Test slutter!
			
			return true;
		}
		
		else {
			sender.sendMessage(ChatColor.RED + "Du må være Admin for å bruke kommandoen /" + cmd.getName());
			return true;
		}
	}
}