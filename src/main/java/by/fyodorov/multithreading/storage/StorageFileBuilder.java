package by.fyodorov.multithreading.storage;

import by.fyodorov.multithreading.entity.StorageEntity;
import by.fyodorov.multithreading.exception.MultiThreadException;
import by.fyodorov.multithreading.reader.FileValidator;
import by.fyodorov.multithreading.reader.ProductParser;
import by.fyodorov.multithreading.reader.BuilderReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map.Entry;

/**
 * storage builder by file. Build Storage by bound and products from file
 */
public class StorageFileBuilder implements AbstractStorageBuilder {
    private static final Logger LOGGER = LogManager.getLogger(StorageFileBuilder.class);
    private StorageEntity result;
    private String path;

    /**
     * create builder for building from file
     * @param path - path of file
     */
    public StorageFileBuilder(String path) {
        this.path = path;
    }

    /**
     * start process for creating Storage. Required before calling getResult() for correct work.
     * @throws MultiThreadException - when can't create storage by file
     */
    @Override
    public void build() throws MultiThreadException {
        BuilderReader reader = new BuilderReader();
        String[] lines = reader.readLines(path);
        ProductParser parser = new ProductParser();

        FileValidator validator = new FileValidator();
        if (!validator.validateForStorage(lines)) {
            throw new MultiThreadException("invalid content of file = \"" + path + "\" for creating storage");
        }
        int bound = Integer.valueOf(lines[0]);
        result = new StorageEntity(bound);

        LOGGER.info("creating new storage with max capacity = " + bound);

        for (int i = 1; i < lines.length; i++) {
            Entry<String, Integer> entry = parser.parse(lines[i]);
            result.addValue(entry.getKey(), entry.getValue());
        }
    }

    /**
     * getting result of building process
     * @return - created storage
     */
    @Override
    public StorageEntity getResult() {
        return result;
    }
}
