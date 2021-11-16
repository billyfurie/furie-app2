/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package exceptions;

import java.security.InvalidParameterException;

public class DuplicateSerialException extends InvalidParameterException {
    public DuplicateSerialException(String duplicateSerialMessage) {
        // allows us to pass a message
        super(duplicateSerialMessage);
    }
}
