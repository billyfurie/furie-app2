/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Inventory;
import models.Item;

public class FXMLController implements Initializable {


    @FXML private TableView<Item> table;

    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, String> idCol;
    @FXML private TableColumn<Item, String> valueCol;

    @FXML private Button addItemButton;

    @FXML private TextField searchField;

    // File menu tabs
    @FXML private MenuItem createInventoryMenuItem;
    @FXML private MenuItem saveInventoryMenuItem;
    @FXML private MenuItem loadInventoryMenuItem;

    // Items menu tabs
    @FXML private MenuItem removeSelectedMenuItem;
    @FXML private MenuItem removeAllMenuItem;

    private ObservableList<Item> data;

    private Inventory inventory = new Inventory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize the table view
        // initialize columns in the tableview
        // initialize cell factories
        // initialize the search feature
        // initialize editing of the item (add error message responses)
        // bind counter to size of observable list
    }

    public void addItem() {
        // when the user clicks the add item button
        // we look at the text fields to see what they entered
        // clear text fields
        // try creating an item
        // if invalid display error message saying what went wrong
    }

    public void clearTextField(TextField field) {
        // helper function to clear the contents in a field
    }

    public void removeSelectedItems() {
        // go through and look which items are selected, remove those
    }

    public void removeAllItems() {
        // removes all the items; clears inventory
    }

    public void displayAlertMessage(String message) {
        // this will display any alert messages / error messages in the alert space
    }

    public void initializeSearch() {
        // this will initialize our search method, useful for initial load of list
    }

    public void createNewInventory() {
        // create a new inventory and make it the active one
    }

    public void saveInventory() {
        // save the current inventory using a FileChooser and our FileManager
    }

    public void loadInventory() {
        // load an inventory using a FileChooser and our FileManager
    }
}

