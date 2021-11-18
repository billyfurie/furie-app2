/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import javafx.stage.FileChooser;
import models.Inventory;
import models.Item;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class FileManager {

    // extensions we support
    public static final FileChooser.ExtensionFilter TSV = new FileChooser.ExtensionFilter("TSV", "*.txt");
    public static final FileChooser.ExtensionFilter HTML = new FileChooser.ExtensionFilter("HTML", "*.html");
    public static final FileChooser.ExtensionFilter JSON = new FileChooser.ExtensionFilter("JSON", "*.json");

    public void saveInventoryToTSV(Inventory inventory, File file) throws IOException {
        StringBuilder fileBuilder = new StringBuilder();

        // add the header row
        fileBuilder.append("Serial Number\tName\tValue\n");

        // get the observable list
        // for each of the items, format a line with
        // A-XXX-XXX-XXX    Xbox    $1499.00
        for (Item item : inventory.getInventoryList()) {
            fileBuilder.append(item.toStringTSV());
        }
        Path p = file.toPath();

        try {
            Files.writeString(p, fileBuilder.toString());
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public Inventory loadInventoryFromTSV(File file) throws InvalidParameterException, FileNotFoundException {
        // takes in the file
        // if invalid, throw exception

        Inventory inventory;
        try (Scanner input = new Scanner(file)) {
            inventory = new Inventory();
            input.useDelimiter("[\t\n]");
            input.nextLine();

            // parses file, examining the items in the line
            while (input.hasNextLine()) {
                // create and build the inventory using these items
                String serial = input.nextLine();
                String[] parameters = serial.split("\t");

                inventory.addItem(new Item(parameters[1], parameters[0], parameters[2].replaceAll("[,$]", "")));
            }
        }

        // return the inventory
        return inventory;
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