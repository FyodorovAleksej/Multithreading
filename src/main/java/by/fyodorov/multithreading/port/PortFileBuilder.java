package by.fyodorov.multithreading.port;

import by.fyodorov.multithreading.entity.StorageEntity;
import by.fyodorov.multithreading.exception.MultiThreadException;
import by.fyodorov.multithreading.reader.FileValidator;
import by.fyodorov.multithreading.reader.ProductParser;
import by.fyodorov.multithreading.reader.BuilderReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

/**
 * port builder by file. Build port with params and products from file
 */
class PortFileBuilder implements PortBuilder {
    private static final Logger LOGGER = LogManager.getLogger(PortFileBuilder.class);
    private String path;
    private Port result;

    /**
     * creating FileBuilder with path of file
     * @param path - path of file for creating
     */
    PortFileBuilder(String path) {
        this.path = path;
    }

    /**
     * start process for creating Port. Required before calling getResult() for correct work.
     * @throws MultiThreadException - when can't create port by file
     */
    @Override
    public void build() throws MultiThreadException {
        BuilderReader reader = new BuilderReader();
        String[] lines = reader.readLines(path);
        FileValidator validator = new FileValidator();
        if (!validator.validateForPort(lines)) {
            throw new MultiThreadException("invalid content of file = \"" + path + "\", for creating port");
        }

        int bound = Integer.valueOf(lines[0]);
        StorageEntity storage = new StorageEntity(bound);
        int docs = Integer.valueOf(lines[1]);
        LOGGER.info("creating new port with max capacity = " + bound + " and docs = " + docs);
        ProductParser parser = new ProductParser();
        for (int i = 2; i < lines.length; i++) {
            Map.Entry<String, Integer> entry = parser.parse(lines[i]);
            storage.addValue(entry.getKey(), entry.getValue());
        }
        result = new Port(storage, docs);
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
