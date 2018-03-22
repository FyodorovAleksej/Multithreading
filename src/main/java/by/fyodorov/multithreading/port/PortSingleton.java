package by.fyodorov.multithreading.port;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.entity.StorageEntity;
import by.fyodorov.multithreading.exception.MultiThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

/**
 * class - wrapper for class Port. Provides Singleton access for Port object
 */
public class PortSingleton {
    private static final Logger LOGGER = LogManager.getLogger(PortSingleton.class);

    private static PortSingleton instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static PortBuilder builder = new PortDefaultBuilder();
    private Port portInstance;

    /**
     * private constructor of singleton
     * @throws MultiThreadException - when can't create Port object
     */
    private PortSingleton() throws MultiThreadException {
        LOGGER.info("creating Port singleton");
        builder.build();
        portInstance = builder.getResult();
    }

    /**
     * thread-safe static singleton's getInstance() with double-check. Use current PortBuilder for creating Port,
     * when it's necessary.
     * @return - single object of PortSingleton
     * @throws MultiThreadException - when can't create Port object
     */
    public static PortSingleton getInstance() throws MultiThreadException {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new PortSingleton();
            }
            lock.unlock();
        }
        return instance;
    }

    /**
     * setting way for creating new Port by getInstance()
     * @param builderName - builder enum of selected PortBuilder {"FILE", "DEFAULT"}
     * @param path - path of file for FileBuilder. Can be null for DefaultBuilder
     */
    public static void setBuilder(PortBuilderEnum builderName, String path) {
        PortBuilderManager manager = new PortBuilderManager();
        builder = manager.findBuilderByEnum(builderName, path);
    }

    /**
     * getting storage for checking results of operations
     * @return - port's storage
     */
    public StorageEntity getStorage() {
        return portInstance.getStorage();
    }

    /**
     * service new ship by port
     * @param ship - ship for service
     */
    public void service(ShipEntity ship) {
        portInstance.service(ship);
    }

    /**
     * adding product in port
     * @param key - name of product
     * @param value - count of product
     */
    public void addProduct(String key, int value) {
        portInstance.addProduct(key, value);
    }

    /**
     * destroy (clear) singleton. Used in tests for independent test's methods
     */
    public void destroy() {
        LOGGER.info("destroy Singleton");
        portInstance.destroy();
        portInstance = null;
        instance = null;
    }
}