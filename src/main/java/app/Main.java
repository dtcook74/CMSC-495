package app;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //TODO
        CsvReader reader = new CsvReader();
        try {
            List<MenuItem> menuItems = reader.getMenuItems("MenuItemsSample.csv");
            System.out.println("\n\n\n\n\nMENU ITEMS:\n");
            for (MenuItem item : menuItems) {
                System.out.println(item.toString());
            }
            JFrame frame = new JFrame("parent");
            JOptionPane.showMessageDialog(frame, "PROGRAM SUCCESS: CHECK CONSOLE FOR MENU ITEMS");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}