package by.fyodorov.multithreading.entity;

import by.fyodorov.multithreading.util.MapDecorator;

import java.util.HashMap;
import java.util.Set;

/**
 * class of ship. Having storage and controlMap - object for manipulate behaviour of change
 */
public class ShipEntity {
    private StorageEntity productStorage;
    private HashMap<String, Integer> controlMap;
    private static final String PRODUCT_HEAD = "Products:\n";
    private static final String CONTROL_HEAD = "\nControl:\n";

    public ShipEntity(int capacity) {
        productStorage = new StorageEntity(capacity);
        controlMap = new HashMap<>();
    }

    /**
     * getting current capacity of storage
     * @return current capacity
     */
    public int getCapacity() {
        return productStorage.getCapacity();
    }

    /**
     * getting max capacity of storage
     * @return border of storage's count
     */
    public int getBorder() {
        return productStorage.getBorder();
    }

    /**
     * getting Set of control map products
     * @return Set of control map
     */
    public Set<String> getControlKeySet() {
        return controlMap.keySet();
    }

    /**
     * getting Set of storage of products
     * @return Set of storage
     */
    public Set<String> getProductKeySet() {
        return productStorage.getKeySet();
    }

    /**
     * getting count of product by control map
     * @param key - name of product for getting
     * @return value - if product with this name is exist
     *             0 - if product doesn't exist
     */
    public Integer getControlCount(String key) {
        return controlMap.getOrDefault(key, 0);
    }

    /**
     * getting count of product by storage
     * @param key - name of product for getting
     * @return value - if product with this name is exist
     *             0 - if product doesn't exist
     */
    public Integer getProductCount(String key) {
        return productStorage.getCount(key);
    }

    /**
     * adding count of product in storage
     * @param name - name of product
     * @param count - count of product
     */
    public void addProduct(String name, int count) {
        productStorage.addValue(name, count);
    }

    /**
     * adding count of product in control map
     * @param name - name of product
     * @param count - count of product
     */
    public void addControl(String name, int count) {
        controlMap.put(name, count);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != (this.getClass())) {
            return false;
        }
        ShipEntity entity = (ShipEntity) obj;
        return controlMap.equals(entity.controlMap) && productStorage.equals(entity.productStorage);
    }

    @Override
    public int hashCode() {
        return productStorage.hashCode() + controlMap.hashCode();
    }

    @Override
    public String toString() {
        MapDecorator decorator = new MapDecorator();
        return  PRODUCT_HEAD + productStorage +
                CONTROL_HEAD + decorator.hashMapToString(controlMap);
    }
}
