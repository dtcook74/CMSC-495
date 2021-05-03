package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Filename: CsvReader.java
 * Authors: Team Delta - Dustin Cook, Zachary Pesons, David Solan, Nathan Wray
 * Purpose: CsvReader contains methods to read different CSV files into objects
 * Date: April 19, 2021
 */

public class CsvReader {
    public List<MenuItem> getMenuItems(String filename) throws FileNotFoundException, InvalidCsvFormatException {
        List<MenuItem> menuItems = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            MenuItem menuItem = null;
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 0) {
                    //empty line...skip
                    continue;
                } else if (line.length == 1) {
                    //this should be item name
                    if (menuItem != null){
                        menuItems.add(menuItem);
                    }
                    menuItem = new MenuItem();
                    menuItem.setName(line[0]);
                } else if (line.length == 3) {
                    //this should be an ingredient line
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(line[0]);
                    ingredient.setQuantity(line[1]);
                    ingredient.setMeasurementUnit(line[2]);
                    menuItem.getIngredients().add(ingredient);
                } else {
                    throw new InvalidCsvFormatException("Csv file is incorrectly formatted. See User Guide for proper format");
                }
            }
            if (menuItem != null) menuItems.add(menuItem);
            return menuItems;
        }
    }

    public Inventory getInventory(String filename) throws FileNotFoundException, InvalidCsvFormatException {
        Inventory inventory = new Inventory();
        try(Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 0) {
                    //empty line...skip
                    continue;
                } else if (line.length == 3) {
                    try {
                        inventory.addIngredient(line[0], Integer.parseInt(line[1].trim()));
                    } catch (NumberFormatException e) {
                        throw new InvalidCsvFormatException("Ingredient amount must be provided as an integer");
                    }
                } else {
                    throw new InvalidCsvFormatException("Format of each line must be name,amount,measurement");
                }
            }
        }
        return inventory;
    }

    public Sales getSales(File file) throws FileNotFoundException, InvalidCsvFormatException {
        Sales sales = new Sales();
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 0 || line[0].startsWith("*") || (!line[0].contains("=") && line[0].contains(" "))) {
                    //ignore
                    continue;
                } else if (line.length == 1 && line[0].contains("=")) {
                    try {
                        sales.setTotalSales(Integer.parseInt(line[0].split("=")[1].trim()));
                    } catch (NumberFormatException e) {
                        throw new InvalidCsvFormatException("Total sales must be show in the format Total Items = {integer}");
                    }
                } else if (line.length == 2) {
                    try {
                        sales.addSaleItem(line[0], Integer.parseInt(line[1].trim()));
                    } catch (NumberFormatException e) {
                        throw new InvalidCsvFormatException("Sales numbers must be formatted as an integer");
                    }
                }
            }
        }
        return sales;
    }
}
