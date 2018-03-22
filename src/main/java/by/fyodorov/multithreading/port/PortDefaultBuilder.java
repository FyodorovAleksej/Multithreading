package by.fyodorov.multithreading.port;

/**
 * default port builder. Build empty port with default params
 */
class PortDefaultBuilder implements PortBuilder {
    private static final int COUNT_OF_DOCS = 4;
    private static final int DEFAULT_BOUND = 10000;
    private Port result;

    /**
     * start process for creating Port. Required before calling getResult() for correct work.
     */
    @Override
    public void build() {
        result = new Port(DEFAULT_BOUND, COUNT_OF_DOCS);
    }

    /**
     * getting result of building process
     * @return - created port
     */
    @Override
    public Port getResult() {
        return result;
    }
}
