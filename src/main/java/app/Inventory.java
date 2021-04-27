package app;

import java.util.HashMap;
import java.util.Map;

/*
 * Authors: Team Delta: Zachary Pesons, Nathan Wray, Dustin Cook, David Solan
 * Purpose: Inventory keeps track of restaurant inventory by ingredient and amount
 * Date: April 19, 2021
 */
public class Inventory {
    private Map<Ingredient, Integer> ingredients = new HashMap<>();

    public Inventory() {}

    // Constructor for Inventory class
    // filename will be the name of the csv file to be read in
	/*
	   public Inventory(File filename) {
		// Read in inventory file that contains
		// current restaurant inventory info
		// then populate or update Ingredients map
	}
	 */

    // amount parameter is int for now but we might
    // change this later to a double or float?
    public void addIngredient(String name, int amount) {
        ingredients.put(new Ingredient(name, amount), amount);
    }

    // Returns the amount of the ingredient currently in
    // the inventory
    public int getIngredient(Ingredient name) {
        return ingredients.get(name);
    }

    // Returns a string containing a list
    // of all the items in the inventory
    // and their amounts
    public String getInventory() throws InventoryNotFoundException {
        if(ingredients == null)
        {
            throw new InventoryNotFoundException("Inventory is empty.");
        }

        String output = "";
        // Pairs are printed on separate lines
        // in this form:
        // name: amount
        // name2: amount2
        for(Ingredient name : ingredients.keySet()) {
            output += name.getName() + ": " + ingredients.get(name) + "\n";
        }
        return output;
    }
    
//Dave made a bad lazy method and he should feel bad
    public Map<Ingredient,Integer> getInventoryMap(){
        return ingredients;
    }

    // Remove ingredient name and value pair from
    // ingredients map. Return true if successful, false if not
    public boolean removeIngredient(Ingredient name) {
        if(ingredients.containsKey(name))
        {
            ingredients.remove(name);
            return true;
        }
        else
        {
            // Ingredient was not found and/or removed
            return false;
        }
    }

    // Method changes the amount for a given ingredient in inventory
    // name: ingredient name
    // amount: new amount for ingredient
    // Might be redundant since it is essentially using the same
    // command used in addIngredients method
    // However, this method checks first if the ingredient exists
    // Returns true if operation was successful, false if not
    public boolean setAmount(Ingredient name, int amount)
    {
        if(ingredients.containsKey(name))
        {
            ingredients.put(name, amount);
            return true;
        }
        else
        {
            // Ingredient was not found and/or removed
            return false;
        }
    }
}
