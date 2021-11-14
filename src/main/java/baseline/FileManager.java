/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import models.Inventory;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

public class FileManager {

    public void saveInventoryToTSV(Inventory inventory, File file) throws IOException {
        // get the observable list
        // for each of the items, format a line with
        // A-XXX-XXX-XXX    Xbox    $1499.00
        // write to the file
    }

    public Inventory loadInventoryFromTSV(File file) throws InvalidParameterException {
        // takes in the file
        // if invalid, throw exception
        // parses file, examining the items in the line
        // create and build the inventory using these items
        // return the inventory
        return null;
    }

    public void saveInventoryToJSON(Inventory inventory, File file) throws IOException {
        // use GSON to save the file
        // go to the location the user specified and save as JSON
    }

    public Inventory loadInventoryFromJSON(File file) throws InvalidParameterException {
        // use GSON to load the file
        // go to the location the user specified and load as JSON
        // if invalid, throw exception
        return null;
    }

    public void saveInventoryToHTML(Inventory inventory, File file) throws IOException {
        // format as html with basic wrap
        // go item by item, adding them to a String for HTML table
        // go to location and save the HTML
    }

    public Inventory loadInventoryFromHTML(File file) throws InvalidParameterException {
        // go to the location the user specified and load as HTML
        // parse through the HTML table, adding items to an Inventory
        // return the inventory
        return null;
    }
}