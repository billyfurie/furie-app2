/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import javafx.collections.ObservableList;
import models.Inventory;
import models.Item;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    private static final String TEST_PATH = "src/test/resources/";

    @Test
    void saveLoadInventoryToTSV() {
        // meat and potatoes handled by saveLoadTest
        saveLoadTest("txt");
    }

    @Test
    void saveLoadInventoryToJSON() {
        // meat and potatoes handled by saveLoadTest
        saveLoadTest("json");
    }

    @Test
    void saveLoadInventoryToHTML() {
        // meat and potatoes handled by saveLoadTest
        saveLoadTest("html");
    }

    private void saveLoadTest(String fileType) {
        Inventory inventorySaved = new Inventory();
        Inventory inventoryLoaded = null;
        ObservableList<Item> itemList;

        Item item1 = new Item("Item1", "A-AAA-AAA-AAA", "1111141");
        Item item2 = new Item("Item2", "A-BBB-AAA-AAA", "123");
        Item item3 = new Item("Item3", "A-AAA-BBB-AAA", "88888888888");

        List<Item> testItems = Arrays.asList(item1, item2, item3);

        inventorySaved.addItem(item1);
        inventorySaved.addItem(item2);
        inventorySaved.addItem(item3);

        FileManager manager = new FileManager();

        File file = new File(TEST_PATH + "test." + fileType);

        switch (fileType) {
            case "txt":
                try {
                    manager.saveInventoryToTSV(inventorySaved, file);
                    inventorySaved.removeAllItems();

                    inventoryLoaded = manager.loadInventoryFromTSV(file);

                } catch (IOException e) {
                    System.out.println("Unable to save/load tsv file in test.");
                }
                break;
            case "json":
                try {
                    manager.saveInventoryToJSON(inventorySaved, file);
                    inventorySaved.removeAllItems();

                    inventoryLoaded = manager.loadInventoryFromJSON(file);

                } catch (IOException e) {
                    System.out.println("Unable to save/load json file in test.");
                }
                break;
            case "html":
                try {
                    manager.saveInventoryToHTML(inventorySaved, file);
                    inventorySaved.removeAllItems();

                    inventoryLoaded = manager.loadInventoryFromHTML(file);

                } catch (IOException e) {
                    System.out.println("Unable to save/load html file in test.");
                }
                break;
        }


        if (inventoryLoaded != null) {
            itemList = inventoryLoaded.getInventoryList();

            for (int i = 0; i < itemList.size(); i++) {
                Item actual = itemList.get(i);
                Item expected = testItems.get(i);

                assertEquals(actual.getName(), expected.getName());
                assertEquals(actual.getSerial(), expected.getSerial());
                assertEquals(actual.getValueFormatted(), expected.getValueFormatted());
            }
        }

        assert inventoryLoaded != null;
        inventoryLoaded.removeAllItems();
    }
}