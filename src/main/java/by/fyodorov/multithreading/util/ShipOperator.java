package by.fyodorov.multithreading.util;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.entity.StorageEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * util class of operators between storage.
 */
public class ShipOperator {
    private static final Logger LOGGER = LogManager.getLogger(ShipOperator.class);
    private ReentrantReadWriteLock operationLock;

    public ShipOperator() {
        operationLock = new ReentrantReadWriteLock();
    }

    /**
     * method for checking allow sending products between 2 storage by 1 control List
     * @param storage - storage, that was putting delays of control Storage
     * @return true - if all delays are possible
     *        false - if not all delays are possible
     */
    public boolean controlCheck(ShipEntity ship, StorageEntity storage) {
        LOGGER.info("control checking");

        operationLock.readLock().lock();

        int freeCapacityShip = ship.getBorder() - ship.getCapacity();
        int freeCapacityStorage = storage.getBorder() - storage.getCapacity();

        Set<String> keys = new HashSet<>();
        keys.addAll(ship.getControlKeySet());
        keys.addAll(ship.getProductKeySet());

        for (String key : keys) {
            int delta = ship.getProductCount(key) - ship.getControlCount(key);

            if (delta < 0 && storage.getCount(key) < -delta) {
                operationLock.readLock().unlock();
                return false;
            }
            freeCapacityShip += delta;
            freeCapacityStorage -= delta;
        }
        operationLock.readLock().unlock();
        return freeCapacityShip >= 0 && freeCapacityStorage >= 0;
    }

    /**
     * method for sending products between 2 storage by 1 control List
     * @param storage - storage, that was putting delays of control Storage
     */
    public void executeChange(ShipEntity ship, StorageEntity storage) {
        LOGGER.info("execute operation");

        operationLock.writeLock().lock();
        for (String i : ship.getControlKeySet()) {
            Integer count = ship.getProductCount(i);
            Integer control = ship.getControlCount(i);
            if (!count.equals(control)) {
                storage.addValue(i, count - control);
                ship.addProduct(i, control - count);
            }
        }
        for (String i : ship.getProductKeySet()) {
            if (!ship.getControlKeySet().contains(i)) {
                Integer count = ship.getProductCount(i);
                Integer control = ship.getControlCount(i);
                storage.addValue(i, count - control);
                ship.addProduct(i, control - count);
            }
        }
        operationLock.writeLock().unlock();
    }

    /**
     * method for getting summary all delays between 2 storage by 1 control List
     * @return - sum of all delays, if it possible
     *                          0 - if it impossible
     */
    public int countOfOperations(ShipEntity ship) {
        LOGGER.info("calculate count of operations");

        int result = 0;
        operationLock.readLock().lock();
        for (String i : ship.getControlKeySet()) {
            Integer count = ship.getProductCount(i);
            Integer control = ship.getControlCount(i);
            result += Math.abs(count - control);
        }
        for (String i : ship.getProductKeySet()) {
            if (!ship.getControlKeySet().contains(i)) {
                Integer count = ship.getProductCount(i);
                Integer control = ship.getControlCount(i);
                result += count - control;
            }
        }
        operationLock.readLock().unlock();
        return result;
    }
}
