package app;

/*
 * Authors: Team Delta: Zachary Pesons, Nathan Wray, Dustin Cook, David Solan
 * Purpose: Thrown when menu data is not found
 * Date: April 19, 2021
 */
public class MenuNotFoundException extends Exception{
    public MenuNotFoundException(String message) {
        super(message);
    }
}
