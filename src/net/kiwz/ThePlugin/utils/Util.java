package net.kiwz.ThePlugin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class Util {
	
	public static String getTimeLogDate(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public static String getTimeFullDate(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public static String getTimeDate(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public static String getTime(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public static long getTimeHours(long unix) {
		return unix / 3600;
	}
	
	public static void setTabColor(Player player) {
		String playerName = player.getName();
		
    	if (playerName.length() + 4 > 16) {
    		playerName = playerName.substring(0, 12);
    	}
        if (MyPlayer.getPlayer(player).isAdmin()) {
            player.setPlayerListName(Color.ADMIN + playerName + ChatColor.WHITE);
        } else {
        	player.setPlayerListName(Color.PLAYER + playerName + ChatColor.WHITE);
        }
	}
	
	public static boolean isSpawnSafe(Location loc) {
		Block block = loc.getBlock();
		Material a = block.getRelative(0, 1, 0).getType();
		Material b = block.getRelative(0, 0, 0).getType();
		Material c = block.getRelative(0, -1, 0).getType();
		Material d = block.getRelative(0, -2, 0).getType();
		Material air = Material.AIR;
		
		if (!a.equals(air) || !b.equals(air) || (c.equals(air) && d.equals(air))) return false;
		
		for (String spotBlock : Config.getConfig().getSpotBlocks()) {
			Material mat = Material.getMaterial(spotBlock);
			if (c.equals(mat)) return false;
			if (c.equals(air) && d.equals(mat)) return false;
		}
		
		World world = loc.getWorld();
		int locMaxX = loc.getBlockX() + 2;
		int locMaxY = loc.getBlockY() + 3;
		int locMaxZ = loc.getBlockZ() + 2;
		int locX = loc.getBlockX() - 2;
		while (locMaxX >= locX) {
			int locY = loc.getBlockY() - 2;
			while (locMaxY >= locY) {
				int locZ = loc.getBlockZ() - 2;
				while (locMaxZ >= locZ) {
					Material mat = world.getBlockAt(locX, locY, locZ).getType();
					for (String areaBlock : Config.getConfig().getAreaBlocks()) {
						Material m = Material.getMaterial(areaBlock);
						if (mat.equals(m)) return false;
					}
					locZ++;
				}
				locY++;
			}
			locX++;
		}
		return true;
	}
	
	public static void sendAsPages(CommandSender sender, String pageN, int pageHeight, String about, String adminInfo, List<String> list) {
		MyPlayer mySender = MyPlayer.getPlayer(sender);
		if ((mySender == null || mySender.isAdmin()) && adminInfo != "") adminInfo = "(" + adminInfo + ") ";
		else adminInfo = "";
		
        int pageNumber;
        try {
        	pageNumber = Integer.parseInt(pageN);
        }
        catch (NumberFormatException e){
        	pageNumber = 1;
        }
        if (pageNumber == 0) {
        	pageNumber = 1;
        }
        int pageWidth;
        
    	if (!(sender instanceof Player)) {
			pageHeight = ChatPaginator.UNBOUNDED_PAGE_HEIGHT;
			pageWidth = ChatPaginator.UNBOUNDED_PAGE_WIDTH;
    	}
    	else {
    		pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT + pageHeight - 1;
    		pageWidth = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
    	}
    	StringBuilder stringBuilder = new StringBuilder();
    	for (String message : list) {
    		stringBuilder.append(message + "\n");
    	}
    	String msg = stringBuilder.toString();
		msg = msg.trim();
        ChatPaginator.ChatPage page = ChatPaginator.paginate(msg, pageNumber, pageWidth, pageHeight);
        
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("----- ");
		header.append(ChatColor.WHITE);
		header.append(about + " ");
		header.append(ChatColor.GRAY);
		header.append(adminInfo);
		header.append(ChatColor.WHITE);
		if (page.getTotalPages() > 1) {
			header.append("(");
			header.append(page.getPageNumber());
			header.append("/");
			header.append(page.getTotalPages());
			header.append(") ");
		}
		header.append(ChatColor.YELLOW);
		for (int i = header.length(); i < ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH; i++) {
			header.append("-");
		}
		sender.sendMessage(header.toString());
		sender.sendMessage(page.getLines());
	}
	
	/**
	 * 
	 * @param world as World
	 * @param coordsString "xx.xx yy.yy zz.zz"
	 * @param directionString "pi.pi ya.ya"
	 * @return <b>Location</b>, null if <b>world</b> is null
	 */
	public static Location parseLocation(World world, String coordsString, String directionString) {
		String[] coords = coordsString.split(" ");
		String[] direction = directionString.split(" ");
		
		double x = Double.parseDouble(coords[0]);
		double y = Double.parseDouble(coords[1]);
		double z = Double.parseDouble(coords[2]);
		float pitch = Float.parseFloat(direction[0]);
		float yaw = Float.parseFloat(direction[1]);
		
		Location loc = new Location(world, x, y, z);
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		return loc;
	}
	
	/**
	 * 
	 * @param loc as Location
	 * @return String Array. [0] is name of world, [1] is whitespace-separated coordinates and [2] is whitespace-separated Pitch/Yaw
	 */
	public static String[] convertLocation(Location loc) {
		String[] locString = new String[3];
		locString[0] = loc.getWorld().getName();
		locString[1] = loc.getX() + " " + loc.getY() + " " + loc.getZ();
		locString[2] = loc.getPitch() + " " + loc.getYaw();
		return locString;
	}
	
	/**
	 * 
	 * @param s numbers as String
	 * @return numbers as Int (0 if string is no number)
	 */
	public static int parseInt(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			i = 0;
		}
		return i;
	}
}
