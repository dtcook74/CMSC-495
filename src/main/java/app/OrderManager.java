package app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Filename: OrderManager.java
 * Authors: Team Delta - Dustin Cook, Zachary Pesons, David Solan, Nathan Wray
 * Purpose: Examines weekly sales data and inventory data to generate a
 *          grocery list of ingredients to stock up on.
 * Date: April 24, 2021
 */
public class OrderManager {
	//private Inventory myInventory = new Inventory();
	private HashMap<String, Integer> averageSales = new HashMap();
	private HashMap<String, List<Ingredient>> menu = new HashMap(); 
	private HashMap<String, Integer> groceryList;
	private HashMap<String, Integer> needsList = new HashMap();
 	//public OrderManager(salesAverage) {}
	
	// Predict order by subtracting inventory from need
	// If the number is zero or more, populate the fourth column
	// If the number is negative, no action needed
	// 4 tomatoes need - 10 tomatoes inventory -> no tomatoes needed
	// 10 onions need - 4 onions inventory -> 6 onions needed
	// Method takes a copy of the inventory(map) as a parameter
	public String predictOrder(Map<Ingredient, Integer> inventory) {
		// Go through each ingredient in grocery list
		// then perform the actions described above ^^^
		groceryList = new HashMap();
		for(Ingredient i : inventory.keySet())
		{
			if(needsList.containsKey(i.getName()))
			{
				// get what we have in inventory
				// and what we need in grocery list
				int have = inventory.get(i);
				int need = needsList.get(i.getName());
				// If need - have is greater than 0
				// then we need to put it on the grocery list
				if(need - have > 0)
				{
					if(groceryList.containsKey(i.getName()))
					{
						int previous = groceryList.get(i.getName());
						groceryList.put(i.getName(), previous + (need-have));
					}
					else
					{
						groceryList.put(i.getName(), need - have);
					}
				}
				else
				{
					// Ingredients we don't need
					// are removed from the list
					groceryList.remove(i.getName());
				}
			}
		}
		// Returns null for now,
		// the getGroceryList method is currently doing the job
		// of outputting what we need
		return null;
	}
	
	// Tally up ingredient needs by going through averageSales
	public void calcIngredientNeeds() {
		// Go through MenuItems (dishName) in averageSales
		for(String dishName : averageSales.keySet())
		{
			// For each dish in the averageSales map,
			// retrieve the list of ingredients
			if(menu.containsKey(dishName))
			{	
				List<Ingredient> ing = menu.get(dishName);
				// Then, for each ingredient in the list of ingredients
				// get the quantity and multiply it by the number of sales
	            // Lastly, add that amount to the grocery list
				for(Ingredient i : ing)
				{
					// Quantity of ingredient in MenuItem
					String amt = i.getQuantity().trim();
					int amount = Integer.parseInt(amt);
					
					// Put ingredient needs in groceryList
					// Two possible cases:
					// 1. Ingredient has already been added to groceryList
					// 2. Ingredient has not yet been added to groceryList
					/*if(needsList.containsKey(i))
					{
						int need = needsList.get(i) + (amount * averageSales.get(dishName));
						needsList.put(i.getName(), need);
					}
					else
					{
						int need = amount * averageSales.get(dishName);
						needsList.put(i.getName(), need);
					} */
					// Code above was commented out since the logic was wrong(oops)
					int need = amount * averageSales.get(dishName);
					needsList.put(i.getName(), need);
				} // end inner for-loop
			} // end if
		} // end outer for-loop
	}
	
	// Return hashmap of ingredient needs calculated
	// using averageSales
	public HashMap<String, Integer> getNeeds() {
		return needsList;
	}
	
	// Receive a copy of averageSales from Main 
	public void setAverageSales(HashMap<String, Integer> averageSales) {
		this.averageSales = averageSales;
	}
	
	// return groceryList map
	// contains what we need for "To Order" column
	public HashMap<String, Integer> getGroceryList() {
		return groceryList;
	}
	
	// Add menu item to menu map
	public void addMenuItem(String name, List<Ingredient> ingredients) {
		menu.put(name, ingredients);
	}
	
	public static void main(String[] args) {
		
	}
}
