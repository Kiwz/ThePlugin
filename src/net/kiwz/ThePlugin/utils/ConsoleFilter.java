package net.kiwz.ThePlugin.utils;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ConsoleFilter implements Filter {
	
	public boolean isLoggable(LogRecord record) {
		String msg = record.getMessage();
		String filter[] = { "Server permissions file", "Reached end of stream for", "issued server command:",
				"DSCT: socket closed", "Connection reset", "lost connection", "logged in with entity id", "moved wrongly!",
				"Disconnecting", "achievement", "UUID"};
		for (String thisFilter : filter) {
			if (msg.contains(thisFilter)) {
				return false;
			}
		}
		if (msg.contains("§c")) {
			record.setMessage(msg.replace("§c", ""));
		}
		return true;
	}
}
