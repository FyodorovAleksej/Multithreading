package by.fyodorov.multithreading.entity;

import by.fyodorov.multithreading.util.MapDecorator;

import java.util.HashMap;
import java.util.Set;

/**
 * class of storage, that used in Ship and Port
 */
public class StorageEntity {
    private HashMap<String, Integer> storageMap;
    private int border;
    private int capacity = 0;
    private static final String BORDER_HEADER = "border = ";

    public StorageEntity(int border) {
        storageMap = new HashMap<>();
        this.border = border;
    }

    /**
     * adding product in storage
     * @param key - name of product
     * @param count - count of product
     */
    public void addValue(String key, int count) {
        if (capacity + count > border) {
            return;
        }
        int value = 0;
        if (storageMap.containsKey(key)) {
            value = storageMap.get(key);
        }
        storageMap.put(key, value + count);
        capacity += count;
    }

    /**
     * getting max capacity of storage
     * @return border of storage
     */
    public int getBorder() {
        return border;
    }

    /**
     * getting current capacity of storage
     * @return current capacity of storage
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * getting count of product in storage
     * @param key - name of product
     * @return - count of product, if name is exist
     *         - 0, if name isn't exist
     */
    public Integer getCount(String key) {
        return storageMap.getOrDefault(key, 0);
    }

    /**
     * getting Set of products in storage
     * @return Set of all products in storage
     */
    public Set<String> getKeySet() {
        return storageMap.keySet();
    }

    @Override
    public String toString() {
        MapDecorator decorator = new MapDecorator();
        return BORDER_HEADER + border + "\n" + decorator.hashMapToString(storageMap);
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
        StorageEntity storage = (StorageEntity) obj;
        return storageMap.equals(storage.storageMap) && capacity == storage.capacity && border == storage.border;
    }
}
