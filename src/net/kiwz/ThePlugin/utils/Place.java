package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Represents ThePlugin's Place system
 */
public class Place {
	private static HashMap<Integer, Place> places = new HashMap<Integer, Place>();
	private static HashMap<Integer, Place> removedPlaces = new HashMap<Integer, Place>();
	private static Set<Integer> usedIDs = new HashSet<Integer>();
	private static Set<String> usedNames = new HashSet<String>();
	
	private int id;
	private int time;
	private String name;
	private String owner;
	private Set<String> members;
	private Location center;
	private int radius;
	private Location spawn;
	private boolean priv;
	private boolean pvp;
	private boolean monsters;
	private boolean animals;
	private String enter;
	private String leave;
	private boolean isChargeAble;
	
	public Place() {
	}
	
	public Place(Player player, String name, String radius) {
		int id = 1;
		while (usedIDs.contains(id)) id++;
		
		this.id = id;
		this.time = (int) (System.currentTimeMillis() / 1000);
		this.name = name;
		this.owner = player.getUniqueId().toString().replace("-", "");
		this.members = new HashSet<String>();
		this.center = new Location(player.getWorld(), player.getLocation().getBlockX(), 100, player.getLocation().getBlockZ());
		this.radius = Util.parseInt(radius);
		this.spawn = player.getLocation();
		this.priv = false;
		this.pvp = false;
		this.monsters = false;
		this.animals = true;
		this.enter = "Velkommen til " + name;
		this.leave = "Du forlater " + name;
	}
	
	public Place(int id, int time, String name, String owner, String members, String worldName, int x, int z, int radius,
			String spawnCoords, String spawnDirection, boolean priv, boolean pvp, boolean monsters, boolean animals, String enter, String leave) {
		
		World world = Bukkit.getServer().getWorld(worldName);
		
		Set<String> set = new HashSet<String>();
		for (String member : members.split(" ")) {
			set.add(member);
		}
		
		this.id = id;
		this.time = time;
		this.name = name;
		this.owner = owner;
		this.members = set;
		this.center = new Location(world, x, 100, z);
		this.radius = radius;
		this.spawn = Util.parseLocation(world, spawnCoords, spawnDirection);
		this.priv = priv;
		this.pvp = pvp;
		this.monsters = monsters;
		this.animals = animals;
		this.enter = enter;
		this.leave = leave;
	}
	
	public static Place getTempPlace(int id) {
		if (places.get(id) == null) return null;
		Place place = new Place();
		place.id = places.get(id).id;
		place.time = places.get(id).time;
		place.name = places.get(id).name;
		place.owner = places.get(id).owner;
		place.members = places.get(id).members;
		place.center = places.get(id).center;
		place.radius = places.get(id).radius;
		place.spawn = places.get(id).spawn;
		place.priv = places.get(id).priv;
		place.pvp = places.get(id).pvp;
		place.monsters = places.get(id).monsters;
		place.animals = places.get(id).animals;
		place.enter = places.get(id).enter;
		place.leave = places.get(id).leave;
		place.isChargeAble = places.get(id).isChargeAble;
		return place;
	}
	
	public static Place getPlace(int id) {
		return places.get(id);
	}
	
	public static Place getPlace(Player player, String name) {
		for (int key : places.keySet()) {
			if (places.get(key).name.equalsIgnoreCase(name)) {
				return places.get(key);
			}
		}
		if (player != null) {
			for (int key : places.keySet()) {
				if (places.get(key).owner.equals(player.getUniqueId().toString().replace("-", ""))
						&& places.get(key).name.toLowerCase().startsWith(name.toLowerCase())) {
					return places.get(key);
				}
			}
		}
		for (int key : places.keySet()) {
			if (places.get(key).name.toLowerCase().startsWith(name.toLowerCase())) {
				return places.get(key);
			}
		}
		return null;
	}
	
	public static Place getPlace(Location loc) {
		for (Place place : getPlaces()) {
			if (loc.getWorld().equals(place.getCenter().getWorld())
					&& (place.getCenter().getBlockX() + place.getRadius()) >= loc.getBlockX()
					&& (place.getCenter().getBlockX() - place.getRadius()) <= loc.getBlockX()
					&& (place.getCenter().getBlockZ() + place.getRadius()) >= loc.getBlockZ()
					&& (place.getCenter().getBlockZ() - place.getRadius()) <= loc.getBlockZ()) {
				return place;
			}
		}
		return null;
	}
	
	public static List<Place> getPlacesByOwner(Player player) {
		List<Place> list = new ArrayList<Place>();
		for (Place place : getPlaces()) {
			if (place.getOwner().equals(player.getUniqueId().toString().replace("-", ""))) {
				list.add(place);
			}
		}
		return list;
	}
	
	public static List<Place> getPlacesByMember(Player player) {
		List<Place> list = new ArrayList<Place>();
		for (Place place : getPlaces()) {
			if (place.getMembers().contains(player.getUniqueId().toString().replace("-", ""))) {
				list.add(place);
			}
		}
		return list;
	}
	
	public static List<Place> getPlaces() {
		List<Place> list = new ArrayList<Place>();
		for (int key : places.keySet()) {
			list.add(places.get(key));
		}
		return list;
	}
	
	public static List<String> getPlaceList() {
		List<String> list = new ArrayList<String>();
		for (Place place : getPlaces()) {
			list.add(Color.VARIABLE + place.getName() + Color.INFO + " [" + Color.PLAYER + MyPlayer.getPlayerById(place.getOwner()).getName() + Color.INFO + "] ");
		}
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list;
	}
	
	public static List<String> getPlayerList() {
		HashMap<String, String> map = new HashMap<String, String>();
		for (Place place : getPlaces()) {
			if (map.containsKey(place.getOwner())) {
				String name = map.get(place.getOwner());
				name = name + " [" + Color.VARIABLE + place.getName() + Color.INFO + "]";
				map.put(place.getOwner(), name);
			}
			else {
				String name = " [" + Color.VARIABLE + place.getName() + Color.INFO + "]";
				map.put(place.getOwner(), name);
			}
		}
		
		List<String> list = new ArrayList<String>();
		for (String key : map.keySet()) {
			list.add(Color.PLAYER + MyPlayer.getPlayerById(key).getName() + Color.INFO + map.get(key));
		}
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list;
	}
	
	public static List<Place> getRemovedPlaces() {
		List<Place> list = new ArrayList<Place>();
		for (int key : removedPlaces.keySet()) {
			list.add(removedPlaces.get(key));
		}
		return list;
	}
	
	public static void clearRemovedPlaces() {
		removedPlaces.clear();
	}
	
	public boolean hasAccess(Player player) {
		if (player.isOp() || Perm.isAdmin(player)) return true;
		if (this.owner.equals(player.getUniqueId().toString().replace("-", ""))) return true;
		if (this.members.contains(player.getUniqueId().toString().replace("-", ""))) return true;
		return false;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setOwner(Player player) {
		this.members.add(getOwner());
		this.members.remove(player.getUniqueId().toString().replace("-", ""));
		this.owner = player.getUniqueId().toString().replace("-", "");
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public boolean setMember(Player player) {
		return this.members.add(player.getUniqueId().toString().replace("-", ""));
	}
	
	public boolean removeMember(Player player) {
		return this.members.remove(player.getUniqueId().toString().replace("-", ""));
	}
	
	public Set<String> getMembers() {
		return this.members;
	}
	
	public void setCenter(Location center) {
		this.center = new Location(center.getWorld(), center.getBlockX(), 100, center.getZ());
	}
	
	public Location getCenter() {
		return this.center;
	}
	
	public void setRadius(String radiusString) {
		this.radius = Util.parseInt(radiusString);
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
	public Location getSpawn() {
		return this.spawn;
	}
	
	public void setPriv(boolean priv) {
		this.priv = priv;
	}
	
	public boolean getPriv() {
		return this.priv;
	}
	
	public void setPvP(boolean pvp) {
		this.pvp = pvp;
	}
	
	public boolean getPvP() {
		return this.pvp;
	}
	
	public void setMonsters(boolean monsters) {
		this.monsters = monsters;
	}
	
	public boolean getMonsters() {
		return this.monsters;
	}
	
	public void setAnimals(boolean animals) {
		this.animals = animals;
	}
	
	public boolean getAnimals() {
		return this.animals;
	}
	
	public void setEnter(String enter) {
		this.enter = enter;
	}
	
	public String getEnter() {
		return this.enter;
	}
	
	public void setLeave(String leave) {
		this.leave = leave;
	}
	
	public String getLeave() {
		return this.leave;
	}
	
	public void setChargeAble(boolean isChargeAble) {
		this.isChargeAble = isChargeAble;
	}
	
	public boolean deletePlace(Player player) {
		if (player == null) {
			usedNames.remove(this.name.toLowerCase());
			removedPlaces.put(this.id, places.remove(this.id));
			return true;
		}
		if (player.isOp() || this.owner.equals(player.getUniqueId().toString().replace("-", ""))) {
			usedNames.remove(this.name.toLowerCase());
			removedPlaces.put(this.id, places.remove(this.id));
			return true;
		}
		return false;
	}
	
	public void savePlace() {
		usedIDs.add(this.id);
		usedNames.add(this.name.toLowerCase());
		if (this.center.getWorld() == null) return;
		places.put(this.id, this);
	}
	
	public boolean savePlace(Player player) {
		if (error(player)) return false;
		if (getPlace(this.id) != null) usedNames.remove(getPlace(this.id).getName().toLowerCase());
		usedIDs.add(this.id);
		usedNames.add(this.name.toLowerCase());
		places.put(this.id, this);
		return true;
	}
	
	private boolean error(Player player) {
		if (player == null) player = MyPlayer.getPlayerById("b1b6a9e8e077492e85ab22983e4213e7").getBukkitPlayer();
		Place place = getPlace(this.id);
		if (place != null) {
			if (!player.isOp() && !place.getOwner().equals(player.getUniqueId().toString().replace("-", ""))) return true;
			if (!place.getName().equalsIgnoreCase(this.name) && usedNames.contains(this.name.toLowerCase())) return true;
			if (place.getSpawn() != this.spawn && place.getCenter() == this.center) {
				if (getPlace(this.spawn) == null) return true;
				if (getPlace(this.spawn).getId() != (this.id)) return true;
			}
		} else {
			if (!player.isOp() && getPlacesByOwner(player).size() >= 3) return true;
			if (usedNames.contains(this.name.toLowerCase())) return true;
		}
		
		if (this.name.equalsIgnoreCase("liste") || this.name.equalsIgnoreCase("her") || this.name.equalsIgnoreCase("spiller")) return true;
		if (this.name.length() < 2 || this.name.length() > 16) return true;
		if (!player.isOp() && !MyWorld.getWorld(this.getCenter().getWorld()).getClaimable()) return true;
		if (!player.isOp() && this.radius < 10) return true;
		if (!player.isOp() && distanceToSpawn(this.getCenter()) <= 300 && this.radius > 15) return true;
		if (!player.isOp() && this.radius > 70) return true;
		if (!available(this).isEmpty()) return true;
		if (!player.isOp() && this.isChargeAble) {
			if (!HandleItems.removeItem(player, Material.GOLD_INGOT, 5)) return true;
		}
		return false;
	}

	public String getError(Player player) {
		if (player == null) player = MyPlayer.getPlayerById("b1b6a9e8e077492e85ab22983e4213e7").getBukkitPlayer();
		Place place = getPlace(this.id);
		if (place != null) {
			if (!player.isOp() && !place.getOwner().equals(player.getUniqueId().toString().replace("-", ""))) return "Dette er ikke din plass";
			if (!place.getName().equalsIgnoreCase(this.name) && usedNames.contains(this.name.toLowerCase())) return "Dette navnet finnes fra før, prøv ett annet navn";
			if (place.getSpawn() != this.spawn && place.getCenter() == this.center) {
				if (getPlace(this.spawn) == null) return "Du må stå i " + this.name + " når du setter spawn";
				if (getPlace(this.spawn).getId() != (this.id)) return "Du må stå i " + this.name + " når du setter spawn";
			}
		} else {
			if (!player.isOp() && getPlacesByOwner(player).size() >= 3) return "Du kan ikke lage flere enn 3 plasser";
			if (usedNames.contains(this.name.toLowerCase())) return "Dette navnet finnes fra før, prøv ett annet navn";
		}
		
		if (this.name.equalsIgnoreCase("liste") || this.name.equalsIgnoreCase("her") || this.name.equalsIgnoreCase("spiller")) return "Dette navnet er reservert, prøv ett annet navn";
		if (this.name.length() < 2 || this.name.length() > 16) return "Navnet må være mellom 2 og 20 bokstaver";
		if (!player.isOp() && !MyWorld.getWorld(this.getCenter().getWorld()).getClaimable()) return "Du kan ikke lage plass i denne verdenen";
		if (!player.isOp() && this.radius < 10) return "Plassen du lager må ha en radius større enn 9";
		if (!player.isOp() && distanceToSpawn(this.getCenter()) <= 300 && this.radius > 15) return "Plasser nære spawn (innenfor 300 blokker) må ha en radius mindre enn 16";
		if (!player.isOp() && this.radius > 70) return "Plassen du lager må ha en radius mindre enn 71";
		if (!available(this).isEmpty()) {
			String error = "Du er for nærme følgende plasser: ";
			for (Place otherPlace : available(this)) error = error + "[" + Color.VARIABLE + otherPlace.getName() + Color.WARNING + "] ";
			return error;
		}
		if (!player.isOp() && this.isChargeAble) return "Det koster 5 gullbarer for å lage eller flytte en plass";
		return "Det skjedde en feil, henvend deg til en Admin";
	}
	
	private int distanceToSpawn(Location loc) {
		Location spawn = MyWorld.getWorld(loc.getWorld()).getSpawn();
		int locX = loc.getBlockX();
		int locZ = loc.getBlockZ();
		int spawnX = spawn.getBlockX();
		int spawnZ = spawn.getBlockZ();
		
		int x = Math.abs(locX - spawnX);
		int z = Math.abs(locZ - spawnZ);
		return Math.max(x, z);
	}
	
	private List<Place> available(Place place) {
		List<Place> list = new ArrayList<Place>();
		for (Place otherPlace : getPlaces()) {
			if (place.getCenter().getWorld().equals(otherPlace.getCenter().getWorld())
					&& place.getCenter().getBlockX() + place.getRadius() >= otherPlace.getCenter().getBlockX() - otherPlace.getRadius()
					&& place.getCenter().getBlockX() - place.getRadius() <= otherPlace.getCenter().getBlockX() + otherPlace.getRadius()
					&& place.getCenter().getBlockZ() + place.getRadius() >= otherPlace.getCenter().getBlockZ() - otherPlace.getRadius()
					&& place.getCenter().getBlockZ() - place.getRadius() <= otherPlace.getCenter().getBlockZ() + otherPlace.getRadius()) {
				if (place.getId() != otherPlace.getId()) {
					list.add(otherPlace);
				}
	    	}
		}
		return list;
	}
}
