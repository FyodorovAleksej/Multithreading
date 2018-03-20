package by.fyodorov.multithreading.storage;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.util.StorageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class PortSingleton implements DocObservable {
    private static final Logger LOGGER = LogManager.getLogger(PortSingleton.class);
    private static PortSingleton instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static int COUNT_OF_DOCS = 4;
    private static int DEFAULT_BOUND = 10000;

    private ReentrantLock operationLock;
    private LinkedList<Doc> docs;
    private LinkedList<ShipEntity> queue;

    private StorageUtil storage;

    private PortSingleton(int bound) {
        storage = new StorageUtil(bound);
        operationLock = new ReentrantLock();
        docs = new LinkedList<>();
        for (int i = 0; i < COUNT_OF_DOCS; i++) {
            Doc doc = new Doc();
            docs.add(doc);
            doc.addSubscriber(this);

        }
        queue = new LinkedList<>();
    }

    public static PortSingleton getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new PortSingleton(DEFAULT_BOUND);
            }
            lock.unlock();
        }
        return instance;
    }


    public StorageUtil getStorage() {
        return storage;
    }

    public void service(ShipEntity ship) {
        operationLock.lock();
        for (Doc doc : docs) {
            if (doc.isFree()) {
                LOGGER.info("find free doc");
                doc.setShip(ship);
                doc.start();
                operationLock.unlock();
                return;
            }
        }
        queue.push(ship);
        operationLock.unlock();
    }

    public void addProduct(String key, int value) {
        operationLock.lock();
        storage.addValue(key, value);
        operationLock.unlock();
    }

    @Override
    public void operation(Doc doc) {
        operationLock.lock();
        docs.remove(doc);
        LOGGER.info("observe in " + Thread.currentThread().getName());
        Doc emptyDoc = new Doc();
        if (!queue.isEmpty()) {
            docs.add(emptyDoc);
            emptyDoc.setShip(queue.pop());
            emptyDoc.start();
        }
        else {
            docs.add(emptyDoc);
        }
        operationLock.unlock();
    }
}