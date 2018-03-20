package by.fyodorov.multithreading.entity;

import by.fyodorov.multithreading.util.StorageUtil;

import java.util.HashMap;
import java.util.Set;

public class ShipEntity {
    private StorageUtil productStorage;
    private HashMap<String, Integer> controlStorage;
    private static final String PRODUCT_HEAD = "Products:\n";
    private static final String CONTROL_HEAD = "\nControl:\n";

    public ShipEntity(int capacity) {
        productStorage = new StorageUtil(capacity);
        controlStorage = new HashMap<String, Integer>();
    }

    public int getCapacity() {
        return productStorage.getCapacity();
    }

    public int getBorder() {
        return productStorage.getBorder();
    }

    public Set<String> getControlKeySet() {
        return controlStorage.keySet();
    }

    public Set<String> getProductKeySet() {
        return productStorage.getKeySet();
    }

    public Integer getControlCount(String key) {
        if (controlStorage.containsKey(key)) {
            return controlStorage.get(key);
        }
        return 0;
    }

    public Integer getProductCount(String key) {
        return productStorage.getCount(key);
    }

    public boolean addProduct(String name, int count) {
        return productStorage.addValue(name, count);
    }

    public void addControl(String name, int count) {
        controlStorage.put(name, count);
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
        return controlStorage.equals(entity.controlStorage) && productStorage.equals(entity.productStorage);
    }

    @Override
    public int hashCode() {
        return productStorage.hashCode() + controlStorage.hashCode();
    }

    @Override
    public String toString() {
        return  PRODUCT_HEAD + productStorage +
                CONTROL_HEAD + StorageUtil.hashMapToString(controlStorage);
    }
}
