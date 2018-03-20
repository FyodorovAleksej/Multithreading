package by.fyodorov.multithreading.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StorageUtil {
    private HashMap<String, Integer> storageMap;
    private int border;
    private int capacity = 0;
    private static final String BORDER_HEADER = "border = ";
    private static final String SPLITTER = "\t- ";
    private static final String END_LINE = "\n";

    public StorageUtil(int border) {
        storageMap = new HashMap<>();
        this.border = border;
    }

    public static String hashMapToString(HashMap<String, Integer> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            builder.append(entry.getKey()).append(SPLITTER).append(entry.getValue()).append(END_LINE);
        }
        return builder.toString();
    }

    public boolean addValue(String key, int count) {
        if (capacity + count > border) {
            return false;
        }
        int value = 0;
        if (storageMap.containsKey(key)) {
            value = storageMap.get(key);
        }
        storageMap.put(key, value + count);
        capacity += count;
        return true;
    }

    public int getBorder() {
        return border;
    }

    public int getCapacity() {
        return capacity;
    }

    public Integer getCount(String key) {
        return storageMap.getOrDefault(key, 0);
    }

    public Set<String> getKeySet() {
        return storageMap.keySet();
    }

    public Set<Map.Entry<String, Integer>> getEntrySet() {
        return storageMap.entrySet();
    }

    @Override
    public String toString() {
        return BORDER_HEADER + border + END_LINE + hashMapToString(storageMap);
    }

    @Override
    public int hashCode() {
        return storageMap.hashCode() + Integer.hashCode(capacity) + Integer.hashCode(border);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        StorageUtil storage = (StorageUtil) obj;
        return storageMap.equals(storage.storageMap) && capacity == storage.capacity && border == storage.border;
    }
}
