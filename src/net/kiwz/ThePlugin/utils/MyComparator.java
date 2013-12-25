package net.kiwz.ThePlugin.utils;

import java.util.Comparator;
import java.util.Map;

public class MyComparator implements Comparator<Object> {
    Map<Integer, Integer> map;
    
    public MyComparator(Map<Integer, Integer> map) {
        this.map = map;
    }
    
    public int compare(Object o1, Object o2) {
        if (map.get(o2) == map.get(o1)) {
        	return 1;
        }
        else {
        	return (map.get(o2)).compareTo(map.get(o1));
        }
    }
}
