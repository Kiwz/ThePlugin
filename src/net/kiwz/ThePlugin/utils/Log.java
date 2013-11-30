package net.kiwz.ThePlugin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Log {
	int unix = (int) (System.currentTimeMillis() / 1000);
	TimeFormat tForm = new TimeFormat();
	String time = tForm.getLogDate(unix);
	
	public void logString(String string) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("player.log"), true), "UTF-8"));
			bw.write(time + string);
			bw.write("\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
