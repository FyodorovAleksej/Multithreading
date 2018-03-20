package by.fyodorov.multithreading.reader;

import by.fyodorov.multithreading.exception.MultiThreadException;
import by.fyodorov.multithreading.util.StorageUtil;

public interface AbstractStorageBuilder {
    void build(String path) throws MultiThreadException;
    StorageUtil getResult();
}
