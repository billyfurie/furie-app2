package exceptions;

import java.security.InvalidParameterException;

public class InventoryFullException extends InvalidParameterException {
    /*
        This method is empty because we do not need anything else for this exception implementation
        This exception is for when a user tries to add an item to an inventory that is full (1024 is the capacity)
     */
}
