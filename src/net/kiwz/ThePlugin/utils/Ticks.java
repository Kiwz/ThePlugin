package net.kiwz.ThePlugin.utils;

import java.text.DecimalFormat;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Ticks implements Runnable {
	protected CommandSender sender;
	protected long delay;
	protected World world;
	protected long start;
	protected long elapsedTicks;
	protected long oldNanoTime;
	protected long elapsedNanoTime;
	protected double ticksPerSecond;
	protected String tps;
	protected DecimalFormat df = new DecimalFormat("#0.00");
	
	public Ticks(CommandSender sender, long delay, World world, long start, long oldNanoTime) {
		this.sender = sender;
		this.delay = delay;
		this.world = world;
		this.start = start;
		this.oldNanoTime = oldNanoTime;
	}
	
	public void run() {
		elapsedTicks = world.getFullTime() - start;
		elapsedNanoTime = System.nanoTime() - oldNanoTime;
		ticksPerSecond = (elapsedTicks * 1000000000.0) / elapsedNanoTime;
		if (!sender.isOp()) ticksPerSecond = ticksPerSecond + 0.1;
		tps = df.format(ticksPerSecond);
		sender.sendMessage(Color.INFO + "TPS: " + Color.VARIABLE + tps + Color.INFO + " (målt over " + delay + " sek)");
	}
}
