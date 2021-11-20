/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package models;

import exceptions.DuplicateSerialException;
import exceptions.InvalidNameException;
import exceptions.InvalidSerialException;
import exceptions.InvalidValueException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class Item {

    // inclusive
    private static final int MIN_CHAR_COUNT_NAME = 2;
    private static final int MAX_CHAR_COUNT_NAME = 256;
    private static final String SERIAL_FORMAT = "[A-Z]-[A-Z0-9]{3}-[A-Z0-9]{3}-[A-Z0-9]{3}"; // A-XXX-XXX-XXX

    private static final String INVALID_NAME_MESSAGE = "Error: enter a name that is 2-256 characters.";
    private static final String INVALID_SERIAL_MESSAGE = "Error: enter a serial in form A-XXX-XXX-XXX. " +
                                                         "A is a letter; X is a letter or digit.";
    public static final String INVALID_VALUE_MESSAGE = "Error: enter a numeric value >= 0.";
    private static final String DUPLICATE_SERIAL_MESSAGE = "Error: the serial number you are trying to use is already " +
                                                           "in use.";

    // used for determining if serial is duplicate
    private static final HashMap<String, Boolean> serialMap = new HashMap<>();

    private boolean initialSerialization = true;

    private SimpleStringProperty name;
    private SimpleStringProperty serial;
    private SimpleDoubleProperty value;
    private SimpleStringProperty valueFormatted;


    public Item(String name, String serial, String value) throws InvalidParameterException {
        // initialize the variables
        // check that each variable is valid, or we will throw an exception
        initProperties(name, serial, value);
    }

    private void initProperties(String name, String serial, String value) {
        setValue(value);
        setName(name);
        setSerial(serial);
    }

    public String getName() {
        // return the name
        return name.getValue();
    }

    public String getSerial() {
        // return the serial
        return serial.getValue();
    }

    public double getValue() {
        // return the value
        return value.getValue();
    }

    public String getValueFormatted() {
        // format in dollar format $
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        // return as String
        return formatter.format(getValue());
    }

    public void setName(String name) throws InvalidNameException {
        // check if valid, else throw exception
        if (!isNameValid(name)) {
            throw new InvalidNameException(INVALID_NAME_MESSAGE);
        }

        // set the name
        this.name = new SimpleStringProperty(name);
    }

    // could throw InvalidSerialException or DuplicateSerialException
    public void setSerial(String serial) throws InvalidParameterException {
        // Serials are NOT case-sensitive
        // this is to aid the user
        serial = serial.toUpperCase(Locale.ROOT);

        // check if valid, else throw exception
        if (!isSerialValid(serial)) {
            throw new InvalidSerialException(INVALID_SERIAL_MESSAGE);
        } else if (isSerialDuplicate(serial)) {
            throw new DuplicateSerialException(DUPLICATE_SERIAL_MESSAGE);
        }

        // check if this is the first serialization
        if (initialSerialization) {

            // set the serial number
            this.serial = new SimpleStringProperty(serial);
            // store this in the map
            serialMap.put(serial, true);
            // make sure this is no longer the initial serialization
            initialSerialization = false;

            return;
        }

        // remove old serial from map, add new serial to map
        String oldSerial = getSerial();

        // check if we have the old serial, we will need to set this to false

        if (serialMap.containsKey(oldSerial) && Boolean.TRUE.equals(serialMap.get(oldSerial))) {
            // set the old serial to not being used anymore
            serialMap.put(oldSerial, false);
            // mark the new serial as used
            serialMap.put(serial, true);
        }

        // set the serial
        this.serial = new SimpleStringProperty(serial);
    }

    public void removeSerial(String ... serials) {
        for (String s : serials) {
            serialMap.put(s, false);
        }
    }

    public void setValue(String value) throws InvalidValueException {
        // check that it is in double format
        double val;

        try {
            val = Double.parseDouble(value.replaceAll("[,$]",""));
        } catch (NullPointerException | NumberFormatException e) {
            throw new InvalidValueException(INVALID_VALUE_MESSAGE);
        }

        // check if valid, else throw exception
        if (!isValueValid(val)) {
            throw new InvalidValueException(INVALID_VALUE_MESSAGE);
        }

        // set the value
        this.value = new SimpleDoubleProperty(val);
        this.valueFormatted = new SimpleStringProperty(getValueFormatted());
    }

    public static boolean isNameValid(String name) {
        // return whether the name is 2-256 characters (inclusive)
        int length = name.length();
        return length >= MIN_CHAR_COUNT_NAME && length <= MAX_CHAR_COUNT_NAME;
    }

    public static boolean isSerialValid(String serial) {
        // return whether the serial is in the format A-XXX-XXX-XXX
        // where A is a letter, and X is a letter or digit
        return serial.matches(SERIAL_FORMAT);
    }

    public static boolean isSerialDuplicate(String serial) {
        // return whether the serial is a duplicate (from map)
        return serialMap.containsKey(serial) && Boolean.TRUE.equals(serialMap.get(serial));
    }

    public static boolean isValueValid(double value) {
        // return whether the value is >= 0
        return value >= 0;
    }

    public String toStringTSV() {
        // return the item String in the format we will use for TSV file saving

        return String.format("%s\t%s\t%s%n", serial.getValue(), name.getValue(), valueFormatted.getValue());
    }

    @Override
    public String toString() {
        // override the default method to include a toString that we could use
        return "";
    }

    public ItemLight getAsItemLight() {
        return new ItemLight(getName(), getSerial(), getValueFormatted());
    }
}
