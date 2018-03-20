package test.fyodorov.multithreading.storage;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.storage.PortSingleton;
import by.fyodorov.multithreading.util.StorageUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class PortTest {
    private static final String PATH = "input/storage1.txt";

    @Test
    public void portSingletonTest() throws Exception {
        PortSingleton port = PortSingleton.getInstance();

        port.addProduct("wood", 1000);
        port.addProduct("milk", 1000);
        port.addProduct("react", 1000);

        ShipEntity ship1 = new ShipEntity(200);
        ship1.addProduct("wood", 20);
        ship1.addControl("wood", 100);


        ShipEntity ship2 = new ShipEntity(200);
        ship2.addProduct("wood", 20);
        ship2.addControl("wood", 100);

        ShipEntity ship3 = new ShipEntity(200);
        ship3.addProduct("wood", 20);
        ship3.addControl("wood", 100);


        ShipEntity ship4 = new ShipEntity(200);
        ship4.addProduct("wood", 20);
        ship4.addControl("wood", 100);

        ShipEntity ship5 = new ShipEntity(200);
        ship5.addProduct("wood", 20);
        ship5.addControl("wood", 100);


        ShipEntity ship6 = new ShipEntity(200);
        ship6.addProduct("wood", 20);
        ship6.addControl("wood", 100);

        port.service(ship1);
        port.service(ship2);
        port.service(ship3);
        port.service(ship4);

        TimeUnit.SECONDS.sleep(10);

        port.service(ship5);
        port.service(ship6);

        TimeUnit.SECONDS.sleep(10);

        StorageUtil expected = new StorageUtil(10000);
        expected.addValue("milk", 1000);
        expected.addValue("wood", 520);
        expected.addValue("react", 1000);
        Assert.assertEquals(PortSingleton.getInstance().getStorage(), expected);
    }
}
