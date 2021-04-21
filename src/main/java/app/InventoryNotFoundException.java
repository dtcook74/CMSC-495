package app;

/*
 * Authors: Team Delta: Zachary Pesons, Nathan Wray, Dustin Cook, David Solan
 * Purpose: Thrown when inventory data is not found
 * Date: April 19, 2021
 */
public class InventoryNotFoundException extends Exception{
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
