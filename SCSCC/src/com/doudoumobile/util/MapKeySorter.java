package com.doudoumobile.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapKeySorter {

	public static Map sort(Map map) {
        Map<Object, Object> mapVK = new TreeMap<Object, Object>(
            new Comparator<Object>() {
                public int compare(Object obj1, Object obj2) {
                    String v1 = (String)obj1;
                    String v2 = (String)obj2;
                    int s = v1.compareTo(v2);
                    return s;
                }
            }
        );

        Set col = map.keySet();
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            List value = (List) map.get(key);
            mapVK.put(key, value);
        }
        return mapVK;
    }
}
