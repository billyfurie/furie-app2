/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package models;

import exceptions.InventoryFullException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void addItem() {
        // create inventory
        Inventory inventory = new Inventory();
        // create item
        Item item = new Item("Item", "A-AAA-ZZZ-AAA", "123456789");

        // verify that adding the item works
        // item not in their yet
        assertFalse(inventory.getInventoryList().contains(item));
        // add the item
        inventory.addItem(item);
        // verify item added
        assertTrue(inventory.getInventoryList().contains(item));
    }

    @Test
    void addItems1024() {
        // create inventory
        Inventory inventory = new Inventory();

        // check we can add 1024 items
        for (int i = 0; i <= 1024; i++) {
            String block1 = String.valueOf((100 + i) % 999);
            String block2 = "999";
            char letter = 'A';

            if (i > 867) {
                block1 = String.valueOf(i - 400);
                letter = 'B';
            }

            String serial = String.format("%c-%s-%s-123", letter, block1, block2);
            inventory.addItem("Item", serial, "100");
        }

        inventory.removeAllItems();

        // check we can't add 1025 items
        for (int i = 0; i <= 1024; i++) {
            String block1 = String.valueOf((100 + i) % 999);
            String block2 = "999";
            char letter = 'A';

            if (i > 867) {
                block1 = String.valueOf(i - 400);
                letter = 'B';
            }

            String serial = String.format("%c-%s-%s-123", letter, block1, block2);
            inventory.addItem("Item", serial, "100");
        }

        // test with invalid parameters and assert that proper exception thrown
        assertThrows(
                InventoryFullException.class,
                () -> inventory.addItem("Sorry", "Z-123-123-123", "1234")
        );
    }

    @Test
    void removeItem() {
        // create inventory
        Inventory inventory = new Inventory();
        // create item
        Item item = new Item("Item", "B-ASD-VVV-ERA", "999");

        // add the item
        inventory.addItem(item);
        // verify item added
        assertTrue(inventory.getInventoryList().contains(item));

        // remove item
        inventory.removeItem(item);

        // verify item is removed
        assertFalse(inventory.getInventoryList().contains(item));
    }

    @Test
    void removeAllItems() {
        // create inventory
        Inventory inventory = new Inventory();

        // add items
        Item item1 = new Item("Item1", "A-123-123-141", "11101");
        Item item2 = new Item("Item2", "A-123-345-452", "234134");
        Item item3 = new Item("Item3", "A-123-456-674", "614365");
        Item item4 = new Item("Item4", "A-123-678-252", "411");
        Item item5 = new Item("Item5", "A-123-879-568", "143565124614");

        List<Item> items = Arrays.asList(item1, item2, item3, item4, item5);

        // add all the items, verify they are in the inventory
        for (Item i : items) {
            inventory.addItem(i);
            assertTrue(inventory.getInventoryList().contains(i));
        }

        // verify that removing all the items results in empty inventory
        inventory.removeAllItems();

        // verify empty
        assertEquals(0, inventory.getInventoryList().size());
    }
}