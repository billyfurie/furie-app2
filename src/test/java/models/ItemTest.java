/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package models;

import exceptions.InvalidNameException;
import exceptions.InvalidSerialException;
import exceptions.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void setName() {
        // verify that editing the item name works
        String name = "ItemName";
        String newName = "ItemNameNew";
        Item item = new Item(name, "A-234-161-BBB", "999");

        assertEquals(item.getName(), name);

        item.setName(newName);

        assertEquals(item.getName(), newName);
    }

    @Test
    void setNameInvalid() {
        // verify that editing the item name works
        String name = "ItemName";
        String newName = "A"; // invalid due to being 1 char
        Item item = new Item(name, "A-AAA-234-BBB", "999");

        assertEquals(item.getName(), name);

        // test with invalid parameters and assert that proper exception thrown
        assertThrows(
                InvalidNameException.class,
                () -> item.setName(newName)
        );
    }

    @Test
    void setSerial() {
        // verify that editing the item serial works
        String serial = "A-AAA-AGA-CCC";
        String newSerial = "A-BBB-BBB-BBA";
        Item item = new Item("TEST", serial, "999");

        assertEquals(item.getSerial(), serial);

        item.setSerial(newSerial);

        assertEquals(item.getSerial(), newSerial);
    }

    @Test
    void setSerialInvalid() {
        // verify that editing the item serial works
        String serial = "A-AAA-AAA-CCC";
        String newSerial = "A-BBB-B";
        Item item = new Item("TEST", serial, "999");

        assertEquals(item.getSerial(), serial);

        // test with invalid parameters and assert that proper exception thrown
        assertThrows(
                InvalidSerialException.class,
                () -> item.setSerial(newSerial)
        );
    }

    @Test
    void setValue() {
        // verify that editing the item serial works
        String value = "$10,000.00";
        String newValue = "$9,999,999,999.00";
        Item item = new Item("TEST", "A-AAA-PPP-123", value);

        assertEquals(item.getValueFormatted(), value);

        item.setValue(newValue);

        assertEquals(item.getValueFormatted(), newValue);
    }

    @Test
    void setValueInvalid() {
        // verify that editing the item serial works
        String value = "$10,000.00";
        String newValue = "$abc.00";
        Item item = new Item("TEST", "A-AAA-BAS-123", value);

        assertEquals(item.getValueFormatted(), value);

        // test with invalid parameters and assert that proper exception thrown
        assertThrows(
                InvalidValueException.class,
                () -> item.setValue(newValue)
        );
    }

    @Test
    void isNameValid() {
        // make sure that the validation for name works properly with several entries
        assertTrue(Item.isNameValid("Item"));
        assertTrue(Item.isNameValid("ITEM"));
        assertTrue(Item.isNameValid("1341 134"));
        assertTrue(Item.isNameValid("Walking Cane"));
        assertTrue(Item.isNameValid("SomethingSomething Something Something SOMETHING"));

        // 256 chars valid
        assertTrue(Item.isNameValid("1234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234567891234"));


        assertFalse(Item.isNameValid("A"));
        assertFalse(Item.isNameValid("1"));
        assertFalse(Item.isNameValid(" "));
        assertFalse(Item.isNameValid("^"));
        assertFalse(Item.isNameValid("/"));
        assertFalse(Item.isNameValid("z"));
        // 257 chars invalid
        assertFalse(Item.isNameValid("12345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345678912345"));
    }

    @Test
    void isSerialValid() {
        // make sure that the validation for serial works properly with several entries
        // make sure that the validation for name works properly with several entries
        // A-XXX-XXX-XXX, A is a letter, X is a letter or digit

        assertTrue(Item.isSerialValid("A-AAA-AAA-AAA"));
        assertTrue(Item.isSerialValid("A-AAA-BBB-AAA"));
        assertTrue(Item.isSerialValid("A-BBB-AAA-AAA"));
        assertTrue(Item.isSerialValid("A-AAA-AAA-BBB"));
        assertTrue(Item.isSerialValid("A-BBB-BBB-BBB"));
        assertTrue(Item.isSerialValid("A-123-BBB-BBB"));
        assertTrue(Item.isSerialValid("A-123-456-BBB"));
        assertTrue(Item.isSerialValid("A-123-456-467"));
        assertTrue(Item.isSerialValid("Z-ZZZ-ZZZ-ZZZ"));

        assertFalse(Item.isSerialValid("1-ZZZ-ZZZ-ZZZ")); // number in A
        assertFalse(Item.isSerialValid("A-ZZZ-ZZZ-ZZZ-AAA"));
        assertFalse(Item.isSerialValid("B-ZZZ-123-ZZZ-AAA"));
        assertFalse(Item.isSerialValid("C-ZZZ-123"));
        assertFalse(Item.isSerialValid("C-ZZZ"));
        assertFalse(Item.isSerialValid("C"));
    }

    @Test
    void isSerialDuplicate() {
        // make sure that the validation for serial duplication works properly with several entries
        String serial = "A-AAA-BBB-BBB";
        // make sure it is not registered as taken yet
        assertFalse(Item.isSerialDuplicate(serial));

        Item item = new Item("Apple", serial, "2.50");

        // check that it is taken now
        assertTrue(Item.isSerialDuplicate(serial));

        // remove the serial
        item.removeSerial(serial);

        // see that it has been removed from duplicate map
        assertFalse(Item.isSerialDuplicate(serial));
    }

    @Test
    void isValueValid() {
        // make sure that the validation for value works properly with several entries

        assertTrue(Item.isValueValid(0));
        assertTrue(Item.isValueValid(1124));
        assertTrue(Item.isValueValid(123.123));
        assertTrue(Item.isValueValid(999999999));

        assertFalse(Item.isValueValid(-1));
        assertFalse(Item.isValueValid(-254.243));
        assertFalse(Item.isValueValid(-0.05));
    }
}