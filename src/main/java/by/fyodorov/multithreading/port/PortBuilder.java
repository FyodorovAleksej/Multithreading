package by.fyodorov.multithreading.port;

import by.fyodorov.multithreading.exception.MultiThreadException;

/**
 * interface of port builder
 */
public interface PortBuilder {

    /**
     * start process for creating Port. Required before calling getResult() for correct work.
     * @throws MultiThreadException - when can't create port by file
     */
    void build() throws MultiThreadException;

    /**
     * getting result of building process
     * @return - created port
     */
    Port getResult();
}
