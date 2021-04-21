package app;

import java.util.ArrayList;
import java.util.List;

/*
 * Filename: MenuItem.java
 * Authors: Team Delta - Dustin Cook, Zachary Pesons, David Solan, Nathan Wray
 * Purpose: MenuItem objects represent a specific item on the menu
 * Date: April 19, 2021
 */

public class MenuItem {
    private String name;
    private List<Ingredient> ingredients;

    public MenuItem() {
        ingredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        String str = "";
        for (Ingredient ingredient : ingredients) {
            str = String.format("%s\n%s", str, ingredient);
        }
        return String.format("%s: %s", name, str);
    }
}
