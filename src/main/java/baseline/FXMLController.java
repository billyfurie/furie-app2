/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize the table view
        // initialize columns in the tableview
        // initialize cell factories
        // initialize the search feature
        // initialize editing of the item
    }

    public void addItem() {
        // when the user clicks the add item button
        // we look at the text fields to see what they entered
        // clear text fields
        // try creating an item
        // if invalid display error message saying what went wrong
    }

    private void clearTextField(TextField field) {
        // helper function to clear the contents in a field
    }

    private void removeItems() {
        // go through and look which items are selected, remove those
    }

    private void removeAllItems() {
        // removes all the items; clears inventory
    }

    private void displayAlertMessage(String message) {
        // this will display any alert messages / error messages in the alert space
    }

    private void initializeSearch() {
        // this will initialize our search method, useful for initial load of list
    }
}

