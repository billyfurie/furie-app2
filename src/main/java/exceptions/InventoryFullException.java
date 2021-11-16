package exceptions;

import java.security.InvalidParameterException;

public class InventoryFullException extends InvalidParameterException {
    public InventoryFullException(String inventoryFullMessage) {
        // allows us to pass a message
        super(inventoryFullMessage);
    }
}
