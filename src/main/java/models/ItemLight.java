/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package models;

public class ItemLight {
    private final String name;
    private final String serial;
    private final String value;

    public ItemLight(String name, String serial, String value) {
        this.name = name;
        this.serial = serial;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getSerial() {
        return serial;
    }

    public String getValue() {
        return value;
    }
}
