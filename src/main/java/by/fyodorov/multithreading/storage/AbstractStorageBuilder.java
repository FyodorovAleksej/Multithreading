package by.fyodorov.multithreading.storage;

import by.fyodorov.multithreading.exception.MultiThreadException;
import by.fyodorov.multithreading.entity.StorageEntity;

/**
 * interface for Storage builder
 */
public interface AbstractStorageBuilder {

    /**
     * start process for creating Storage. Required before calling getResult() for correct work.
     * @throws MultiThreadException - when can't create storage by file
     */
    void build() throws MultiThreadException;

    /**
     * getting result of building process
     * @return - created storage
     */
    StorageEntity getResult();
}
