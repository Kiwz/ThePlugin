package net.kiwz.ThePlugin.utils;

import java.util.HashMap;
import java.util.Map;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Places;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

public class Dynmap {
	private HashMap<Integer, Places> places = ThePlugin.getPlaces;
	private PluginManager pm = Bukkit.getPluginManager();
	private Plugin dynmap = pm.getPlugin("dynmap");
	private Plugin thePlugin = pm.getPlugin("ThePlugin");
	private DynmapAPI api;
	private MarkerAPI markerApi;
	private MarkerSet set;
    private Map<String, AreaMarker> areaMarkers = new HashMap<String, AreaMarker>();
    private Map<String, Marker> markers = new HashMap<String, Marker>();
    private String markerSetID = "ThePlugin";
    private String markerSetLabel = "Plasser";

    public void markTheMap() {
        if (dynmap == null) {
            return;
        }
        
    	api = (DynmapAPI) dynmap;
    	markerApi = api.getMarkerAPI();
        if (markerApi == null) {
            return;
        }
        
        set = markerApi.getMarkerSet(markerSetID);
        if (set == null) {
        	set = markerApi.createMarkerSet(markerSetID, markerSetLabel, null, false);
        }
        else {
        	set.setMarkerSetLabel(markerSetLabel);
        }
        
        if (set == null) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(thePlugin, new Runnable() {
			@Override
            public void run() {
				handlePlaces();
			}
		}, 40L, 300L);
    }
    
    private void handlePlaces() {
        Map<String, AreaMarker> newAreaMarkers = new HashMap<String, AreaMarker>();
        Map<String, Marker> newMarkers = new HashMap<String, Marker>();
        for (int id : places.keySet()) {
        	String markerID = "ThePlugin_" + id;
        	String label = places.get(id).name + " (" + places.get(id).owner + ")";
        	String world = places.get(id).world;
        	String name = places.get(id).name;
        	String owner = places.get(id).owner;
        	String members = places.get(id).members.replace(" ", ", ");
        	if (!members.isEmpty()) {
        		members = members.substring(0, members.length() - 2);
        	}
        	double[] x = getX(places.get(id).x, places.get(id).size + 1);
        	double[] z = getZ(places.get(id).z, places.get(id).size + 1);
        	boolean pvp = true;
        	if (places.get(id).pvp == 0) pvp = false;
	        StringBuilder str = new StringBuilder();
	        str.append("<b>");
	        str.append("Plass: ");
	        str.append(name);
	        str.append("</b>");
	        str.append("<br>");
	        str.append("<b>Eier: </b>");
	        str.append(owner);
	        str.append("<br>");
	        str.append("<b>Medlemmer: </b>");
	        str.append(members);
	        String desc = str.toString();
	        setAreaMarker(markerID, label, world, desc, x, z, pvp, newAreaMarkers);
	        
	        String[] spawn = places.get(id).spawnCoords.split(" ");
	        double SpawnX = Double.parseDouble(spawn[0]);
	        double SpawnY = Double.parseDouble(spawn[1]);
	        double SpawnZ = Double.parseDouble(spawn[2]);
	        setMarker(markerID, label, world, desc, SpawnX, SpawnY, SpawnZ, newMarkers);
        }
        
    	for (AreaMarker oldAreaMarker : areaMarkers.values()) {
    		oldAreaMarker.deleteMarker();
		}
        areaMarkers = newAreaMarkers;
        
    	for (Marker oldMarker : markers.values()) {
    		oldMarker.deleteMarker();
		}
        markers = newMarkers;
    }
    
    private void setAreaMarker(String markerID, String label, String world, String desc, double[] x, double[] z, boolean pvp, Map<String, AreaMarker> newAreaMarkers){
        markerID = markerID + "_area";
        AreaMarker area = areaMarkers.remove(markerID);
        if (area == null) {
            area = set.createAreaMarker(markerID, label, false, world, x, z, false);
            if (area == null) {
                return;
            }
        }
        else {
            area.setCornerLocations(x, z);
            area.setLabel(label);
        }
        area.setDescription(desc);
    	area.setFillStyle(0, 0xFF0000);
        if (pvp) {
            area.setLineStyle(3, 1, 0xFF0000);
        }
        else {
            area.setLineStyle(3, 1, 0x00FF00);
        }
        newAreaMarkers.put(markerID, area);
    }
    
    private void setMarker(String markerID, String label, String world, String desc, double x, double y, double z, Map<String, Marker> newMarkers) {
        markerID = markerID + "_icon";
    	Marker marker = markers.remove(markerID);
        if (marker == null) {
        	marker = set.createMarker(markerID, label, world, x, y, z, markerApi.getMarkerIcon("house"), false);
            if (marker == null) {
                return;
            }
        }
        else {
            marker.setLocation(world, x, y, z);;
            marker.setLabel(label);
        }
    	marker.setDescription(desc);
    	newMarkers.put(markerID, marker);
    }
    
    private double[] getX(int centerX, int radius) {
        double[] x = new double[4];
        x[0] = centerX + radius;
        x[1] = centerX + radius;
        x[2] = centerX - radius + 1;
        x[3] = centerX - radius + 1;
        return x;
    }
    
    private double[] getZ(int centerZ, int radius) {
        double[] z = new double[4];
        z[0] = centerZ + radius;
        z[1] = centerZ - radius + 1;
        z[2] = centerZ - radius + 1;
        z[3] = centerZ + radius;
        return z;
    }
}
