package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerCmd {
	private String cmd;
	private String[] args;
	private CommandSender sender;
	private String senderName = "";
	private MyPlayer mySender = null;
	private Player target = null;
	private MyPlayer myTarget = null;
	private String targetName = "";
	private String warning = "";
	
	public ServerCmd(CommandSender sender, String cmd, String[] args) {
		this.cmd = cmd;
		this.args = args;
		this.sender = sender;
		mySender = MyPlayer.getPlayer(sender);
		senderName = MyPlayer.getColorName(mySender);
		myTarget = mySender;
		targetName = senderName;

		if (mySender == null && args.length == 0) {
			warning = Color.WARNING + "Spesifiser en spiller";
		} else if (args.length == 0) {
			target = myTarget.getOnlinePlayer();
		} else {
			myTarget = MyPlayer.getPlayer(args[0]);
			targetName = MyPlayer.getColorName(myTarget);
			if (myTarget == null) {
				warning = Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her";
			} else if (myTarget.getOnlinePlayer() == null) {
				warning = targetName + Color.WARNING + " er ikke online";
			} else {
				target = myTarget.getOnlinePlayer();
			}
		}
	}
	
	public boolean exec() {
		if (fly() || gm() || heal() || melde()) return true;
		else return false;
	}
	

	private boolean fly() {
		if (!cmd.equals("fly")) return false;
		
		if (target == null) {
			sender.sendMessage(warning);
		} else {
			target.setAllowFlight(!target.getAllowFlight());
			if (mySender == myTarget) {
				if (target.getAllowFlight()) target.sendMessage(Color.INFO + "Du kan fly");
				else target.sendMessage(Color.INFO + "Du kan ikke fly");
			} else {
				if (target.getAllowFlight()) {
					target.sendMessage(senderName + " skrudde på fly modus");
					sender.sendMessage(targetName + " kan fly");
				} else {
					target.sendMessage(senderName + " skrudde av fly modus");
					sender.sendMessage(targetName + " kan ikke fly");
				}
			}
		}
		return true;
	}
	
	private boolean gm() {
		if (!cmd.equals("gm")) return false;
		
		if (target == null) {
			sender.sendMessage(warning);
		} else {
			GameMode gm = target.getGameMode();
			GameMode survival = GameMode.SURVIVAL;
			GameMode creative = GameMode.CREATIVE;
			if (mySender == myTarget) {
				if (gm == survival) {
					target.setGameMode(creative);
					target.sendMessage(Color.INFO + "Du er nå i Creative modus");
				}
				else {
					target.setGameMode(survival);
					target.sendMessage(Color.INFO + "Du er nå i Survival modus");
				}
			} else {
				if (gm == survival) {
					target.setGameMode(creative);
					target.sendMessage(senderName + " endret modusen din til Creative");
					sender.sendMessage(targetName + " er nå i Creative modus");
				} else {
					target.setGameMode(survival);
					target.sendMessage(senderName + " endret modusen din til Survival");
					sender.sendMessage(targetName + " er nå i Survival modus");
				}
			}
		}
		return true;
	}
	
	private boolean heal() {
		if (!cmd.equals("heal")) return false;
		
		if (target == null) {
			sender.sendMessage(warning);
		} else {
			target.setHealth(20);
			target.setFoodLevel(20);
			target.setSaturation(20);
			if (mySender == myTarget) {
				target.sendMessage(Color.INFO + "Du fikk fullt liv og masse mat");
			} else {
				target.sendMessage(senderName + " ga deg fullt liv og masse mat");
				sender.sendMessage(targetName + " har fått fullt liv og masse mat");
			}
		}
		return true;
	}
	
	private boolean melde() {
		if (!cmd.equals("melde")) return false;
		
		if (target == null) {
			sender.sendMessage(warning);
		} else {
			String message = senderName + Color.WHISPER + " -> " + targetName + Color.WHISPER + ": ";
			for (int i = 1; args.length > i; i++) {
				message = message + args[i] + " ";
			}
			sender.sendMessage(message);
			target.sendMessage(message);
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				MyPlayer mySpy = MyPlayer.getPlayer(player);
				if (mySpy.isSpy() && (!mySpy.equals(mySender) && !mySpy.equals(myTarget))) {
					player.sendMessage(Color.WARNING + "[SPY] " + message);
				}
			}
			if (mySender != null) {
				mySender.setReplayTo(myTarget);
				myTarget.setReplayTo(mySender);
			}
		}
		return true;
	}
}
