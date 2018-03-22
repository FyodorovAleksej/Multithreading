package test.fyodorov.multithreading.storage;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.entity.StorageEntity;
import by.fyodorov.multithreading.port.PortBuilderEnum;
import by.fyodorov.multithreading.port.PortSingleton;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class PortTest {
    private static final String PATH = "input/port/portInput1.txt";
    private PortSingleton port;

    @AfterMethod
    public void afterMethod() {
        if (port != null) {
            port.destroy();
            PortSingleton.setBuilder(PortBuilderEnum.DEFAULT, null);
        }
    }

    @Test
    public void testPortSingleton() throws Exception {
        port = PortSingleton.getInstance();
        port.addProduct("wood", 1000);
        port.addProduct("milk", 1000);
        port.addProduct("react", 1000);

        final int ALL = 10;
        final int START = 4;

        ShipEntity[] ships = new ShipEntity[ALL];

        for (int i = 0; i < ALL; i++) {
            ShipEntity ship = new ShipEntity(200);
            ship.addProduct("wood", 20);
            ship.addControl("wood", 100);
            ships[i] = ship;
        }

        for (int i = 0; i < START; i++) {
            port.service(ships[i]);
        }
        TimeUnit.SECONDS.sleep(10);

        for (int i = START; i < ALL; i++) {
            port.service(ships[i]);
        }
        TimeUnit.SECONDS.sleep(20);

        StorageEntity expected = new StorageEntity(10000);
        expected.addValue("milk", 1000);
        expected.addValue("wood", 200);
        expected.addValue("react", 1000);
        Assert.assertEquals(PortSingleton.getInstance().getStorage(), expected);
    }

    @Test
    public void testFilePort() throws Exception {
        PortSingleton.setBuilder(PortBuilderEnum.FILE, PATH);
        port = PortSingleton.getInstance();

        StorageEntity expected = new StorageEntity(100);
        expected.addValue("wood", 15);
        expected.addValue("milk", 50);
        expected.addValue("react", 10);

        Assert.assertEquals(port.getStorage(), expected);
    }
}
