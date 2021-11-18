/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package models;

import exceptions.InventoryFullException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.InvalidParameterException;

public class Inventory {

    public static final int CAPACITY_INCLUSIVE = 1024;
    private static final String INVENTORY_FULL_MESSAGE = "Error: the inventory is full at 1024 items.";

    private final ObservableList<Item> inventoryList;

    public Inventory() {
        // initialize the observable list
        inventoryList = FXCollections.observableArrayList();
    }

    public void addItem(String name, String serial, String value) throws InvalidParameterException {
        // try to create the item with these parameters

        Item item = new Item(name, serial, value);
        // if invalid, throw the exception
        // handled automatically ^

        // add item to our observable list
        // if full, throw the exception

        if (inventoryList.size() <= CAPACITY_INCLUSIVE) {
            inventoryList.add(item);
        } else {
            throw new InventoryFullException(INVENTORY_FULL_MESSAGE);
        }
    }

    public void addItem(Item item) throws InvalidParameterException {
        // try to add the already-created item
        // if full, throw the exception

        if (inventoryList.size() <= CAPACITY_INCLUSIVE) {
            // add item to our observable list
            inventoryList.add(item);
        } else {
            throw new InventoryFullException(INVENTORY_FULL_MESSAGE);
        }
    }

    public void removeItem(Item item) {
        // remove the serial
        item.removeSerial(item.getSerial());

        // remove the item from the observable list
        inventoryList.remove(item);
    }

    public void removeItem(ObservableList<Item> items) {
        // remove all the serials
        for (Item item : items) {
            item.removeSerial(item.getSerial());
        }

        // remove all the specified items
        inventoryList.removeAll(items);
    }

    public void removeAllItems() {
        // remove all the serials
        for (Item item : inventoryList) {
            item.removeSerial(item.getSerial());
        }

        // remove all the items in the list
        // clearing inventory
        inventoryList.clear();
    }

    public ObservableList<Item> getInventoryList() {
        // return the inventory observable list
        return inventoryList;
    }

    public String getCounterString() {
        return inventoryList.size() + "/" + CAPACITY_INCLUSIVE;
    }
}
