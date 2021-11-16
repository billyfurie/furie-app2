/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package exceptions;

import java.security.InvalidParameterException;

public class InvalidValueException extends InvalidParameterException {
    public InvalidValueException(String invalidValueMessage) {
        // allows us to pass a message
        super(invalidValueMessage);
    }
}
