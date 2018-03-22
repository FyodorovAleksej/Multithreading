package by.fyodorov.multithreading.port;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.entity.StorageEntity;
import by.fyodorov.multithreading.util.ShipOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.concurrent.Flow;
import java.util.concurrent.locks.ReentrantLock;

/**
 * class of Port for service ships
 */
public class Port implements Flow.Subscriber<DocThread> {
    private static final Logger LOGGER = LogManager.getLogger(Port.class);

    private ReentrantLock operationLock;
    private DocThread[] docThreads;
    private ArrayDeque<ShipEntity> queue;
    private ShipOperator operator;
    private StorageEntity storage;

    /**
     * package-private constructor
     * @param bound - bound of port's storage
     * @param docsCount - count of docs
     */
    Port(int bound, int docsCount) {
        this(new StorageEntity(bound), docsCount);
    }

    /**
     * package-private constructor
     * @param storage - port's storage
     * @param docsCount - count of docs
     */
    Port(StorageEntity storage, int docsCount) {
        this.storage = storage;
        operationLock = new ReentrantLock();
        docThreads = new DocThread[docsCount];
        for (int i = 0; i < docsCount; i++) {
            DocThread docThread = new DocThread();
            docThreads[i] = docThread;
            docThread.subscribe(this);

        }
        queue = new ArrayDeque<>();
        operator = new ShipOperator();
    }

    /**
     * package-private method for getting port's storage
     * @return port's storage
     */
    StorageEntity getStorage() {
        return storage;
    }

    /**
     * package-private method for service new ship. Checking docs and push into queue, if all docs are busy
     * @param ship - ship for service
     */
    void service(ShipEntity ship) {
        operationLock.lock();
        for (DocThread docThread : docThreads) {
            if (docThread.isFree()) {
                LOGGER.info("find free docThread");
                startDoc(docThread, ship);
                operationLock.unlock();
                return;
            }
        }
        queue.push(ship);
        operationLock.unlock();
    }

    /**
     * package-private method for adding product into port's storage
     * @param key - name of the product
     * @param value - count of product
     */
    void addProduct(String key, int value) {
        operationLock.lock();
        storage.addValue(key, value);
        operationLock.unlock();
    }

    /**
     * package-private method for destroying port. Interrupt all docs
     */
    void destroy() {
        for (DocThread doc : docThreads) {
            if (!doc.isFree()) {
                doc.interrupt();
            }
        }
    }

    /**
     * slot of subscribing to doc
     * @param subscription - subscription to doc
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        LOGGER.info("ON SUBSCRIBE");
        subscription.request(1);
    }

    /**
     * slot of changing state of doc
     * @param item - doc, that become free
     */
    @Override
    public void onNext(DocThread item) {
        LOGGER.info("ON NEXT");
        item.close();
        operationLock.lock();
        for (int  i = 0; i < docThreads.length; i++) {
            if (docThreads[i] == item) {
                DocThread emptyDocThread = new DocThread();
                emptyDocThread.subscribe(this);
                if (!queue.isEmpty()) {
                    docThreads[i] = emptyDocThread;
                    startDoc(emptyDocThread, queue.pop());
                }
                else {
                    docThreads[i] = emptyDocThread;
                }
            }
        }
        LOGGER.info("observe in " + Thread.currentThread().getName());
        operationLock.unlock();
    }

    /**
     * error slot
     * @param throwable - error
     */
    @Override
    public void onError(Throwable throwable) {
        LOGGER.catching(throwable);
        throw new RuntimeException("Error in subscribe", throwable);
    }

    /**
     * slot of closing publisher
     */
    @Override
    public void onComplete() {
        LOGGER.info("ON COMPLETE");
    }


    private void startDoc(DocThread doc, ShipEntity ship) {
        doc.setOperator(operator);
        doc.setShip(ship);
        doc.start();
    }
}
