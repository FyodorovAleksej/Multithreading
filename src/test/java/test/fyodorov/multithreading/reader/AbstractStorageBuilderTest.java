package test.fyodorov.multithreading.reader;

import by.fyodorov.multithreading.entity.StorageEntity;
import by.fyodorov.multithreading.exception.MultiThreadException;
import by.fyodorov.multithreading.storage.AbstractStorageBuilder;
import by.fyodorov.multithreading.storage.StorageFileBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AbstractStorageBuilderTest {
    private static final String PATH = "input/storage1.txt";
    private static final String FAIL_PATH = "input/st.txt";

    @Test
    public void testBuildPositive() throws Exception {
        AbstractStorageBuilder builder = new StorageFileBuilder(PATH);
        builder.build();
        StorageEntity storage = builder.getResult();

        StorageEntity expected = new StorageEntity(100);
        expected.addValue("wood", 15);
        expected.addValue("milk", 50);
        expected.addValue("react", 10);

        Assert.assertEquals(storage, expected);
    }

    @Test(expectedExceptions = MultiThreadException.class)
    public void testBuildNegative() throws Exception {
        AbstractStorageBuilder builder = new StorageFileBuilder(FAIL_PATH);
        builder.build();
        StorageEntity storage = builder.getResult();
        Assert.fail();
    }
}