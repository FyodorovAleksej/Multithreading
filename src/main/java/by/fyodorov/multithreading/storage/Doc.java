package by.fyodorov.multithreading.storage;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.util.ShipOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Doc extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(Doc.class);
    private ShipEntity ship;
    private LinkedList<DocObservable> subscribers;
    private boolean isStart;

    public Doc() {
        ship = null;
        subscribers = new LinkedList<>();
        isStart = false;
    }

    public void setShip(ShipEntity ship) {
        LOGGER.info("Setting new ship in thread - " + Thread.currentThread().getName());
        this.ship = ship;
    }

    @Override
    public void run() {
        isStart = true;
        if (ship == null) {
            return;
        }
        ShipOperator operator = ShipOperator.getInstance();
        LOGGER.info("before locking - " + Thread.currentThread().getName());
        operator.lock();
        LOGGER.info("after locking - " + Thread.currentThread().getName());
        boolean valid = operator.controlCheck(ship, PortSingleton.getInstance().getStorage());
        LOGGER.info("controlCheck = " + valid);
        if (valid) {
            int count = operator.countOfOperations(ship, PortSingleton.getInstance().getStorage());
            LOGGER.info("count of operations = " + count);
            operator.controlGetStorage(ship, PortSingleton.getInstance().getStorage());
            LOGGER.info("unlocking");
            operator.unlock();
            try {
                TimeUnit.SECONDS.sleep(count / 10);
            }
            catch (InterruptedException e) {

            }
            LOGGER.info("After sleep - " + Thread.currentThread().getName());
        }
        else {
            LOGGER.info("unlocking not valid");
            operator.unlock();
        }
        notifySubscribers();
        ship = null;
    }

    public boolean isFree() {
        return ship == null;
    }

    public void notifySubscribers() {
        for (DocObservable subscriber : subscribers) {
            subscriber.operation(this);
        }
    }

    public void addSubscriber(DocObservable subscriber) {
        subscribers.add(subscriber);
    }

    public boolean isStart() {
        return isStart;
    }
}
