package test.fyodorov.multithreading.reader;

import by.fyodorov.multithreading.reader.AbstractStorageBuilder;
import by.fyodorov.multithreading.reader.StorageFileBuilder;
import by.fyodorov.multithreading.util.StorageUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AbstractStorageBuilderTest {
    private static final String PATH = "input/storage1.txt";

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testBuild() throws Exception {
        AbstractStorageBuilder builder = new StorageFileBuilder();
        builder.build(PATH);
        StorageUtil storage = builder.getResult();

        StorageUtil expected = new StorageUtil(100);
        expected.addValue("wood", 15);
        expected.addValue("milk", 50);
        expected.addValue("react", 10);

        Assert.assertEquals(storage, expected);
    }
}