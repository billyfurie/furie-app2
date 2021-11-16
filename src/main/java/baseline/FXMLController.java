/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import exceptions.InvalidNameException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import models.Inventory;
import models.Item;

public class FXMLController implements Initializable {


    @FXML private TableView<Item> table;

    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, String> serialCol;
    @FXML private TableColumn<Item, String> valueCol;

    @FXML private Button addItemButton;

    @FXML private TextField searchField;

    @FXML private TextField enterNameField;
    @FXML private TextField enterSerialField;
    @FXML private TextField enterValueField;

    @FXML private Label alertMessageLabel;

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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        // initialize cell factories
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serialCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn());


        // initialize editing
        initializeEditing();
        // initialize the search feature
        // bind counter to size of observable list

        data = inventory.getInventoryList();
        table.setItems(data);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void addItem() {
        // when the user clicks the add item button
        // we look at the text fields to see what they entered
        String name = enterNameField.getText();
        String serial = enterSerialField.getText();
        String value = enterValueField.getText();

        // try creating an item
        try {
            Item item = new Item(name, serial, value);

            inventory.addItem(item);
            // clear text fields if successful
            // otherwise leave it since the user will probably want to edit some info

            clearTextFields(enterNameField, enterSerialField, enterValueField);
        } catch (InvalidParameterException e) {
            // if invalid display error message saying what went wrong
            displayAlertMessage(e.getMessage());
        }
    }

    public void clearTextFields(TextField... fields) {
        // helper function to clear the contents in a field

        for (TextField field : fields) {
            field.clear();
        }
    }

    public void removeSelectedItems() {
        // go through and look which items are selected, remove those
    }

    public void removeAllItems() {
        // removes all the items; clears inventory
    }

    public void displayAlertMessage(String message) {
        // this will display any alert messages / error messages in the alert space

        if (message != null) {
            alertMessageLabel.setText(message);
        }
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

    private void initializeEditing() {
        // initialize editing of the item name
        nameCol.setOnEditCommit(event -> {
            try {
                // get the item we are trying to edit
                Item item = event.getRowValue();
                // try to test the new edited name
                item.setName(event.getNewValue());
            } catch (InvalidNameException e) {
                // if it's invalid, we show an error message
                displayAlertMessage(e.getMessage());
            } finally {
                // refresh the table
                table.refresh();
            }
        });

        // initialize editing of the item serial
        serialCol.setOnEditCommit(event -> {
            try {
                // get the item we are trying to edit
                Item item = event.getRowValue();
                // try to test the new edited name
                item.setSerial(event.getNewValue());
            } catch (InvalidParameterException e) {
                // if it's invalid, we show an error message
                displayAlertMessage(e.getMessage());
            } finally {
                // refresh the table
                table.refresh();
            }
        });

        // initialize editing of the item value
        valueCol.setOnEditCommit(event -> {
            try {
                // get the item we are trying to edit
                Item item = event.getRowValue();
                // try to test the new edited name
                item.setValue(event.getNewValue());
            } catch (InvalidParameterException e) {
                // if it's invalid, we show an error message
                displayAlertMessage(e.getMessage());
            } finally {
                // refresh the table
                table.refresh();
            }
        });
    }
}

