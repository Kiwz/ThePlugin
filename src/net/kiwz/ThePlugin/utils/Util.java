package net.kiwz.ThePlugin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class Util {
	
	public static Environment getEnvironment(String envString) {
		Environment env = Environment.NORMAL;
		if (envString.equalsIgnoreCase("NETHER")) env = Environment.NETHER;
		if (envString.equalsIgnoreCase("THE_END")) env = Environment.THE_END;
		return env;
	}
	
	public static WorldType getWorldType(String typeString) {
		WorldType type = WorldType.NORMAL;
		if (typeString.equalsIgnoreCase("AMPLIFIED")) type = WorldType.AMPLIFIED;
		if (typeString.equalsIgnoreCase("FLAT")) type = WorldType.FLAT;
		if (typeString.equalsIgnoreCase("LARGE_BIOMES")) type = WorldType.LARGE_BIOMES;
		if (typeString.equalsIgnoreCase("VERSION_1_1")) type = WorldType.VERSION_1_1;
		return type;
	}
	
	public static long getSeed(String seedString) {
		if (seedString == "") seedString = "a" + System.currentTimeMillis() + "z";
		long seed = 0;
		try {
			seed = Long.parseLong(seedString);
		} catch (NumberFormatException e) {
			seed = (long) seedString.hashCode();
		}
		return seed;
	}
	
	public static String getTimeLogDate(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public static String getTimeFullDate(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
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
        if (player.isOp() || Perm.isAdmin(player)) {
            player.setPlayerListName(ChatColor.RED + playerName + ChatColor.WHITE);
        }
        else {
        	int roll = (int) (Math.random() * 5);
        	if (roll == 0) player.setPlayerListName(ChatColor.DARK_BLUE + playerName + ChatColor.WHITE);
        	if (roll == 1) player.setPlayerListName(ChatColor.DARK_GREEN + playerName + ChatColor.WHITE);
        	if (roll == 2) player.setPlayerListName(ChatColor.DARK_AQUA + playerName + ChatColor.WHITE);
        	if (roll == 3) player.setPlayerListName(ChatColor.DARK_PURPLE + playerName + ChatColor.WHITE);
        	if (roll == 4) player.setPlayerListName(ChatColor.GOLD + playerName + ChatColor.WHITE);
        	if (roll == 5) player.setPlayerListName(ChatColor.DARK_GRAY + playerName + ChatColor.WHITE);
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
		
		for (String spotBlock : Config.getSpotBlocks()) {
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
					for (String areaBlock : Config.getAreaBlocks()) {
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
	
	public static void sendAsPages(CommandSender sender, String pageN, int pageHeight, String about, List<String> messages) {
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
    	for (String message : messages) {
    		stringBuilder.append(message + "\n");
    	}
    	String msg = stringBuilder.toString();
		msg = msg.trim();
        ChatPaginator.ChatPage page = ChatPaginator.paginate(msg, pageNumber, pageWidth, pageHeight);
        
		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("--------- ");
		header.append(ChatColor.WHITE);
		header.append(about + " ");
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
	 * @param world
	 * @param coordsString "xx.xx yy.yy zz.zz"
	 * @param directionString "pi.pi ya.ya"
	 * @return Location
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
	 * @return numbers as Int (1 if string is no number)
	 */
	public static int parseInt(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			i = 1;
		}
		return i;
	}
	
	/**
	 * 
	 * @param i as int to parse
	 * @return true if int equals 1, else return false
	 */
	public static boolean parseBoolean(int i) {
		if (i == 1) return true;
		else return false;
	}
}
