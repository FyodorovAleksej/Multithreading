package by.fyodorov.multithreading.port;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.exception.MultiThreadException;
import by.fyodorov.multithreading.util.ShipOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * class (Thread) doc of port. Publisher for Port. When become free after job - notify Port.
 */
public class DocThread extends Thread implements Publisher<DocThread> {
    private static final Logger LOGGER = LogManager.getLogger(DocThread.class);
    private static final int SLEEPING_COEFFICIENT = 30;

    private ShipEntity ship;
    private ShipOperator operator;
    private SubmissionPublisher<DocThread> publisher;

    public DocThread() {
        ship = null;
        publisher = new SubmissionPublisher<>();
    }

    /**
     * setting ship for working. Required before running for correct working
     * @param ship - ship for service
     */
    public void setShip(ShipEntity ship) {
        LOGGER.info("Setting new ship in thread - " + Thread.currentThread().getName());
        this.ship = ship;
    }

    /**
     * setting operator for perform operation between storage. Required before running for correct working
     * @param operator - ShipOperator for perform transactions
     */
    public void setOperator(ShipOperator operator) {
        this.operator = operator;
    }

    /**
     * method of thread for service current ship by current operator
     */
    @Override
    public void run() {
        if (ship == null || operator == null) {
            return;
        }
        try {
            boolean valid = operator.controlCheck(ship, PortSingleton.getInstance().getStorage());
            LOGGER.info("controlCheck = " + valid);
            if (valid) {
                int count = operator.countOfOperations(ship);
                LOGGER.info("count of operations = " + count);
                operator.executeChange(ship, PortSingleton.getInstance().getStorage());
                try {
                    TimeUnit.SECONDS.sleep(count / SLEEPING_COEFFICIENT);
                } catch (InterruptedException e) {
                    LOGGER.catching(e);
                    return;
                }
                LOGGER.info("After sleep - " + Thread.currentThread().getName());
            }
            publisher.submit(this);
            ship = null;
        }
        catch (MultiThreadException e) {
            LOGGER.catching(e);
        }
    }

    /**
     * checking status of doc
     * @return - true, if current ship doesn't stated
     */
    public boolean isFree() {
        return ship == null;
    }

    /**
     * subscribe listener for notifying, when doc become free
     * @param subscriber - new listener
     */
    @Override
    public void subscribe(Flow.Subscriber<? super DocThread> subscriber) {
        publisher.subscribe(subscriber);
    }

    /**
     * closing (destroying) thread
     */
    public void close() {
        publisher.close();
    }
}
