/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package models;

import exceptions.InvalidNameException;
import exceptions.InvalidValueException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class Item {

    // inclusive
    private static final int MIN_CHAR_COUNT_NAME = 2;
    private static final int MAX_CHAR_COUNT_NAME = 256;
    private static final String SERIAL_FORMAT = "A-XXX-XXX-XXX";

    // used for determining if serial is duplicate
    private static HashMap<String, Boolean> serialMap;

    private SimpleStringProperty name;
    private SimpleStringProperty serial;
    private SimpleDoubleProperty value;

    public Item(String name, String serial, double value) throws InvalidParameterException {
        // initialize the variables
        // check that each variable is valid, or we will throw an exception
        // if valid, add serial to the map
    }

    public String getName() {
        // return the name
        return null;
    }

    public String getSerial() {
        // return the serial
        return null;
    }

    public double getValue() {
        // return the value
        return 0;
    }

    public String getValueString() {
        // format in dollar format $
        // return as String
        return null;
    }

    public void setName(String name) throws InvalidNameException {
        // check if valid, else throw exception
        // set the name
    }

    // could throw InvalidSerialException or DuplicateSerialException
    public void setSerial(String serial) throws InvalidParameterException {
        // check if valid, else throw exception
        // remove old serial from map, add new serial to map
        // set the serial
    }

    public void setValue(double value) throws InvalidValueException {
        // check if valid, else throw exception
        // set the value
    }

    public boolean isNameValid(String name) {
        // return whether the name is 2-256 characters (inclusive)
        return false;
    }

    public boolean isSerialValid(String serial) {
        // return whether the serial is in the format A-XXX-XXX-XXX
        // where A is a letter, and X is a letter or digit
        return false;
    }

    public boolean isSerialDuplicate(String serial) {
        // return whether the serial is a duplicate (from map)
        return false;
    }

    public boolean isValueValid(double value) {
        // return whether the value is >= 0
        return false;
    }

    public String toStringTSV() {
        // return the item String in the format we will use for TSV file saving
        return null;
    }

    @Override
    public String toString() {
        // override the default method to include a toString that we could use
        return null;
    }
}
