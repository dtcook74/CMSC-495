package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvReader {

    public List<MenuItem> getMenuItems(String filename) throws FileNotFoundException {
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
                    //TODO throw formatting exception
                    break;
                }
            }
            if (menuItem != null) menuItems.add(menuItem);
            return menuItems;
        }
    }
}
