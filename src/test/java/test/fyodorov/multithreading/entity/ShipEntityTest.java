package test.fyodorov.multithreading.entity;

import by.fyodorov.multithreading.entity.ShipEntity;
import by.fyodorov.multithreading.util.ShipOperator;
import by.fyodorov.multithreading.entity.StorageEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShipEntityTest {
    private ShipEntity ship;

    @BeforeMethod
    public void beforeMethod() {
        ship = new ShipEntity(100);
        ship.addProduct("milk",20);
        ship.addProduct("wood",45);
    }

    @Test
    public void testControlCheckPositive() {
        StorageEntity outSide = new StorageEntity(100);
        outSide.addValue("milk", 45);
        outSide.addValue("react",20);
        outSide.addValue("wood",10);

        ship.addControl("milk", 0);
        ship.addControl("wood", 50);

        ShipOperator operator = new ShipOperator();

        Assert.assertTrue(operator.controlCheck(ship, outSide));
    }

    @Test
    public void testControlCheckNegative() {
        StorageEntity outSide = new StorageEntity(100);
        outSide.addValue("milk", 45);
        outSide.addValue("react",20);
        outSide.addValue("wood",10);

        ship.addControl("milk", 100);
        ship.addControl("react", 20);

        ShipOperator operator = new ShipOperator();

        Assert.assertFalse(operator.controlCheck(ship, outSide));
    }

    @Test
    public void testControlCheckOverflowNegative() {
        StorageEntity outSide = new StorageEntity(500);
        outSide.addValue("milk", 100);
        outSide.addValue("react",40);
        outSide.addValue("wood",10);

        ship.addControl("milk", 100);
        ship.addControl("react", 40);
        ship.addControl("wood", 40);

        ShipOperator operator = new ShipOperator();

        Assert.assertFalse(operator.controlCheck(ship, outSide));
    }

    @Test
    public void testControlGetStoragePositive() {
        StorageEntity outSide = new StorageEntity(500);
        outSide.addValue("milk", 45);
        outSide.addValue("react",20);
        outSide.addValue("wood",10);

        ship.addControl("milk", 10);
        ship.addControl("react", 20);

        ShipOperator operator = new ShipOperator();
        operator.executeChange(ship, outSide);

        StorageEntity expectedOut = new StorageEntity(500);
        expectedOut.addValue("milk", 55);
        expectedOut.addValue("react",0);
        expectedOut.addValue("wood",55);

        Assert.assertEquals(outSide, expectedOut);
    }

    @Test
    public void testCountOfOperations() {
        StorageEntity outSide = new StorageEntity(500);
        outSide.addValue("milk", 45);
        outSide.addValue("react",20);
        outSide.addValue("wood",10);

        ship.addControl("milk", 10);
        ship.addControl("react", 20);

        ShipOperator operator = new ShipOperator();
        int expected = 20 + 45 + 10;
        Assert.assertEquals(operator.countOfOperations(ship), expected);
    }
}