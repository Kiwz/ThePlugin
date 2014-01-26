package net.kiwz.ThePlugin.commands;

import java.util.ArrayList;
import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Util;

import org.bukkit.command.CommandSender;

public class MyServerCmd {
	private String cmd;
	private String[] args;
	private CommandSender sender;
	private MyPlayer mySender = null;
	private String senderName = "";
	private MyPlayer myTarget = null;
	private String targetName = "";
	private String warning = "";
	private String page = "1";
	
	public MyServerCmd(CommandSender sender, String cmd, String[] args) {
		this.cmd = cmd;
		this.args = args;
		this.sender = sender;
		mySender = MyPlayer.getPlayer(sender);
		senderName = MyPlayer.getColorName(mySender);
		myTarget = mySender;
		targetName = senderName;
		
		if (mySender == null && args.length == 0) {
			warning = Color.WARNING + "Spesifiser en spiller";
		} else if (args.length > 0) {
			page = args[0];
			myTarget = MyPlayer.getPlayer(args[0]);
			targetName = MyPlayer.getColorName(myTarget);
			if (myTarget == null) {
				warning = Color.PLAYER + args[0] + Color.WARNING + " er ikke en spiller her";
			}
		}
	}
	
	public boolean exec() {
		if (hvem() || mute() || muteList() || unMute() || unMuteAll()) return true;
		else return false;
	}
	
	private boolean hvem() {
		if (!cmd.equals("hvem")) return false;
		
		if (myTarget == null) {
			sender.sendMessage(warning);
		} else {
			Util.sendAsPages(sender, "1", 10, targetName, "ip: " + myTarget.getIp(), myTarget.getInfo(mySender));
		}
		return true;
	}
	
	private boolean mute() {
		if (!cmd.equals("mute")) return false;
		
		if (myTarget == null) {
			sender.sendMessage(warning);
		} else {
			myTarget.setMuted(true);
			sender.sendMessage(MyPlayer.getColorName(myTarget) + " er muted");
		}
		return true;
	}
	
	private boolean muteList() {
		if (!cmd.equals("mutelist")) return false;
		
		List<String> list = new ArrayList<String>();
		for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
			if (myPlayer.isMuted()) list.add(MyPlayer.getColorName(myPlayer));
		}
		Util.sendAsPages(sender, page, 0, "Liste over alle spillere som er muted:", "", list);
		return true;
	}
	
	private boolean unMute() {
		if (!cmd.equals("unmute")) return false;

		if (myTarget == null) {
			sender.sendMessage(warning);
		} else {
			myTarget.setMuted(false);
			sender.sendMessage(MyPlayer.getColorName(myTarget) + " er unmuted");
		}
		return true;
	}
	
	private boolean unMuteAll() {
		if (!cmd.equals("unmuteall")) return false;

		for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
			myPlayer.setMuted(false);
		}
		sender.sendMessage(Color.INFO + "Alle spillere er unmuted");
		return true;
	}
}
