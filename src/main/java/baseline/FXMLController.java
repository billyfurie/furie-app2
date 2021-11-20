/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import exceptions.InvalidNameException;
import javafx.animation.PauseTransition;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import models.Inventory;
import models.Item;

public class FXMLController implements Initializable {


    @FXML private TableView<Item> table;

    @FXML private BorderPane mainPane;

    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, String> serialCol;
    @FXML private TableColumn<Item, String> valueCol;

    @FXML private TextField searchField;

    @FXML private TextField enterNameField;
    @FXML private TextField enterSerialField;
    @FXML private TextField enterValueField;

    @FXML private Label alertMessageLabel;
    @FXML private Label itemCountLabel;


    private ObservableList<Item> data;

    private Inventory inventory = new Inventory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize the table view

        // initialize columns in the tableview
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("valueFormatted")); // gets $ formatted

        // initialize cell factories
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serialCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // initialize editing
        initializeEditing();
        // initialize the search feature
        // bind counter to size of observable list

        setActiveInventory(inventory);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        initializeSearch();
        initializeMainPane();
        initializeEditorFields();

        // set the comparator for the value sort
        // since we treat the values as strings
        valueCol.setComparator((o1, o2) -> {
            // get rid of the $ , .
            o1 = o1.replaceAll("[,$]","");
            o2 = o2.replaceAll("[,$]","");

            // compare using big integer
            return new BigDecimal(o1).compareTo(new BigDecimal(o2));
        });
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

            // update our counter
            updateCounter();
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
        // update our counter
        inventory.removeItem(table.getSelectionModel().getSelectedItems());
        updateCounter();
    }

    public void removeAllItems() {
        // removes all the items; clears inventory
        // update our counter
        inventory.removeAllItems();
        updateCounter();
    }

    public void displayAlertMessage(String message) {
        // this will display any alert messages / error messages in the alert space

        if (message != null) {
            alertMessageLabel.setText(message);
        }

        // displays the message for 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> alertMessageLabel.setText(null));
        pause.play();
    }

    public void createNewInventory() {
        // create a new inventory and make it the active one
        inventory.removeAllItems();
        inventory = new Inventory();
        // update our counter
        updateCounter();
    }

    public void saveInventory() {
        // save the current inventory using a FileChooser and our FileManager
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        FileManager manager = new FileManager();

        // add supported file types
        fileChooser.getExtensionFilters().addAll(FileManager.TSV, FileManager.HTML, FileManager.JSON);

        File selected = fileChooser.showSaveDialog(null);

        // handles exceptions
        // see which file type the user selected
        if (selected != null) {
            if (fileChooser.getSelectedExtensionFilter().equals(FileManager.TSV)) {
                try {
                    manager.saveInventoryToTSV(inventory, selected);
                } catch (IOException e) {
                    displayAlertMessage("Unable to save inventory to TSV.");
                }
            } else if (fileChooser.getSelectedExtensionFilter().equals(FileManager.HTML)) {
                try {
                    manager.saveInventoryToHTML(inventory, selected);
                } catch (IOException e) {
                    displayAlertMessage("Unable to save inventory to HTML.");
                }
            } else if (fileChooser.getSelectedExtensionFilter().equals(FileManager.JSON)) {
                try {
                    manager.saveInventoryToJSON(inventory, selected);
                } catch (IOException e) {
                    displayAlertMessage("Unable to save inventory to JSON.");
                }
            }
        }
    }

    public void loadInventory() {
        // load the current inventory using a FileChooser and our FileManager

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load");
        FileManager manager = new FileManager();

        // add supported file types
        fileChooser.getExtensionFilters().addAll(FileManager.TSV, FileManager.HTML, FileManager.JSON);

        File selected = fileChooser.showOpenDialog(null);


        // handles exceptions
        // see which file type the user selected
        if (selected != null) {

            // clear current inventory
            inventory.removeAllItems();

            if (fileChooser.getSelectedExtensionFilter().equals(FileManager.TSV)) {
                // load from tsv
                try {
                    setActiveInventory(manager.loadInventoryFromTSV(selected));
                } catch (Exception e) {
                    displayAlertMessage("Unable to load from " + selected.toPath());
                }

            } else if (fileChooser.getSelectedExtensionFilter().equals(FileManager.HTML)) {
                // load from html
                setActiveInventory(manager.loadInventoryFromHTML(selected));

            } else if (fileChooser.getSelectedExtensionFilter().equals(FileManager.JSON)) {
                // load from json
                setActiveInventory(manager.loadInventoryFromJSON(selected));
            }
        }

        // update our counter
        updateCounter();
        initializeSearch();
    }

    public void initializeSearch() {
        // this will initialize our search method, useful for initial load of list
        // display all the data in the original filtered data
        // found in tutorial

        // Wrap the FilteredList in a SortedList.
        SortedList<Item> sortedData = getSearchData(data, searchField.textProperty());

        // Add sorted (and filtered) data to the table.

        // Bind the SortedList comparator to the TableView comparator.
        // Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }

    public SortedList<Item> getSearchData(ObservableList<Item> observableList, StringProperty stringProperty) {

        // Wrap
        FilteredList<Item> filteredData = new FilteredList<>(observableList, b -> true);

        stringProperty.addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(item -> {

                    // If filter text is empty, display all items.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Does not match.
                    if (item.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches name.
                    }
                    else return item.getSerial().toLowerCase().contains(lowerCaseFilter);
                }));

        // Wrap the FilteredList in a SortedList.
        return new SortedList<>(filteredData);
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

        // displays text at full length
        nameCol.setOnEditCancel(event -> {
            Item item = event.getRowValue();

            if (item != null) {
                displayAlertMessage(item.getName());
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

        // displays text at full length
        serialCol.setOnEditCancel(event -> {
            Item item = event.getRowValue();

            if (item != null) {
                displayAlertMessage(item.getSerial());
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

        // displays text at full length
        valueCol.setOnEditCancel(event -> {
            Item item = event.getRowValue();

            if (item != null) {
                displayAlertMessage(item.getValueFormatted());
            }
        });
    }

    private void initializeMainPane() {
        // this is so that when the user clicks anywhere, it will de-focus from the text fields
        mainPane.setOnMouseClicked(event -> mainPane.requestFocus());
    }

    private void initializeEditorFields() {
        // when user presses enter, item will attempt to be created
        enterNameField.setOnAction(event -> addItem());
        enterSerialField.setOnAction(event -> addItem());

        // when user presses enter at last field, item will be created, then go back to first field
        // this allows for quicker creation of items
        enterValueField.setOnAction(event -> {
            addItem();
            enterNameField.requestFocus();
        });
    }

    private void updateCounter() {
        itemCountLabel.setText(inventory.getCounterString());
    }

    private void setActiveInventory(Inventory inventory) {
        this.inventory.removeAllItems();
        this.inventory = inventory;
        data = inventory.getInventoryList();
        table.setItems(data);
    }
}

