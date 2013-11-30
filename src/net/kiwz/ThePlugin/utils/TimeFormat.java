package net.kiwz.ThePlugin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {
	
	public String getLogDate(int unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public String getFullDate(int unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public String getDate(int unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public String getTime(int unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public int getHours(int unix) {
		return unix / 3600;
	}
}
