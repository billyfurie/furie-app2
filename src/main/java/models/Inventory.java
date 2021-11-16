/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package models;

import javafx.collections.ObservableList;

import java.security.InvalidParameterException;

public class Inventory {

    private static final int CAPACITY_INCLUSIVE = 1024;

    private ObservableList<Item> inventory;

    public Inventory() {
        // initialize the observable list
    }

    public void addItem(String name, String serial, double value) throws InvalidParameterException {
        // try to create the item with these parameters
        // if invalid, throw the exception
        // if full, throw the exception
        // add item to our observable list
    }

    public void addItem(Item item) throws InvalidParameterException {
        // try to add the already-created item
        // if full, throw the exception
        // add item to our observable list
    }

    public void removeItem(Item item) {
        // remove the item from the observable list
    }

    public void removeItem(ObservableList<Item> items) {
        // remove all the specified items
    }

    public void removeAllItems() {
        // remove all the items in the list
        // clearing inventory
    }

    public ObservableList<Item> getInventory() {
        // return the inventory observable list
        return null;
    }
}
