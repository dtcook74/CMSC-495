package app;

/*
 * Filename: SaleItem.java
 * Authors: Team Delta - Dustin Cook, Zachary Pesons, David Solan, Nathan Wray
 * Purpose: SaleItem objects are used to record sales numbers for individual items on the menu
 * Date: April 19, 2021
 */

public class SaleItem {
    private String name;
    private int sales;

    public SaleItem (String itemName, int numSales) {
        name = itemName;
        sales = numSales;
    }

    public String getName() {
        return name;
    }

    public int getSales() {
        return sales;
    }

}
