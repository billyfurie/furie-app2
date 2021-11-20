/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import models.Inventory;
import models.Item;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FXMLControllerTest {

    @Test
    void searchByName() {
        // verify that searching by name results in a list with expected results
        FXMLController fxmlController = new FXMLController();
        Inventory inventory = new Inventory();
        ObservableList<Item> list = inventory.getInventoryList();
        StringProperty searchText = new SimpleStringProperty();

        Item item1 = new Item("Item1", "A-AAA-AAA-AAA", "10000");
        Item item2 = new Item("Item2", "A-AAA-BBB-AAA", "999");
        Item item3 = new Item("NotValid", "A-CCC-BBB-AAA", "123456789");

        // create a list of all our test items
        List<Item> itemsList = Arrays.asList(item1, item2, item3);

        List<Item> expectedSearchResults = Arrays.asList(item1, item2);

        // add all items to our inventory
        for (Item i : itemsList) {
            inventory.addItem(i);
        }

        // "search" for our items with our search text
        SortedList<Item> actualSearchResults = fxmlController.getSearchData(list, searchText);

        // actual search for something (name)
        searchText.set("Item");

        // make sure the sizes are the same
        assertEquals(expectedSearchResults.size(), actualSearchResults.size());

        // make sure we get expected search results by checking search items are
        for (Item i : actualSearchResults) {
            assertTrue(expectedSearchResults.contains(i));
        }
    }

    @Test
    void searchBySerialNumber() {
        // verify that searching by name results in a list with expected results
        FXMLController fxmlController = new FXMLController();
        Inventory inventory = new Inventory();
        ObservableList<Item> list = inventory.getInventoryList();
        StringProperty searchText = new SimpleStringProperty();

        Item item1 = new Item("Item1", "A-AAA-ZZA-AAA", "10000");
        Item item2 = new Item("Item2", "A-AAA-BBB-ABA", "999");
        Item item3 = new Item("NotValid", "A-AAB-ZZZ-ZZZ", "123456789");

        // create a list of all our test items
        List<Item> itemsList = Arrays.asList(item1, item2, item3);

        List<Item> expectedSearchResults = Arrays.asList(item1, item2);

        // add all items to our inventory
        for (Item i : itemsList) {
            inventory.addItem(i);
        }

        // "search" for our items with our search text
        SortedList<Item> actualSearchResults = fxmlController.getSearchData(list, searchText);

        // actual search for something (serial)
        searchText.set("A-AAA");

        // make sure the sizes are the same
        assertEquals(expectedSearchResults.size(), actualSearchResults.size());

        // make sure we get expected search results by checking search items are
        for (Item i : actualSearchResults) {
            assertTrue(expectedSearchResults.contains(i));
        }
    }
}